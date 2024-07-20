package org.example.jr_mod3_finalmentor.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.utils.Utils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

public class TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherService.class);

    public void addTeacher(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException {
        //создание преобразователя json -> java-объект
        JsonMapper mapper = new JsonMapper();
        //создание объекта Teacher
        Teacher teacher = new Teacher();
        //получение тела запроса
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            //чтение json в объект
            teacher = mapper.readValue(requestBody, Teacher.class);
        } catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            logger.error(JSON_PARSING_ERROR);
            resp.setStatus(400);
        }
        //валидация распарсенных данных объекта Teacher
        if (isValidTeacherData(teacher, resp)) {
            //поиск и установка уникального ID для объекта Teacher
            teacher.setId(Utils.findUniqueId(db.getTeachers()));
            //добавление объекта в общий список
            db.getTeachers().add(teacher);
            //установка статуса http ответа
            logger.info(ADD_TEACHER_MSG, teacher.getFullName());
            resp.setStatus(201);
        }
    }

    public void getTeachers(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException, ServletException {
        //валидация и парсинг URL - не требуется
        //получение списка всех преподавателей и отправка http ответа
        List<Teacher> teachers = db.getTeachers();
        logger.info(GET_TEACHER_MSG);
        if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, teachers.toString());
        else {
            req.setAttribute(TEACHERS, teachers);
            req.getRequestDispatcher(TEACHERS_JSP).forward(req, resp);
        }
    }

    public void editTeacher(HttpServletRequest req, HttpServletResponse resp, LocalDB db) {
        //валидация и парсинг URL
        int teacherId = Utils.getId(req, resp);
        String subjectAsString = req.getParameter(SUBJECT);
        List<Teacher> teachers = db.getTeachers();
        //если преподаватель под заданным ID существует
        if (Utils.isExistId(teachers, teacherId)) {
            Teacher filteredTeacher = (Teacher) Utils.findListElementById(teachers, teacherId);
            try {filteredTeacher.getSubjects().add(Subject.valueOf(subjectAsString.toUpperCase()));}
            catch (IllegalArgumentException e) {
                logger.error(SUBJECT_ERROR);
                resp.setStatus(400);
            }
            logger.info(EDIT_TEACHER_MSG, filteredTeacher.getFullName());
        //если преподавателя под заданным ID не существует
        } else {
            logger.error(TEACHER_FIND_ERROR);
            resp.setStatus(400);
        }
    }

    //валидация данных объекта Teacher
    private boolean isValidTeacherData(Teacher teacher, HttpServletResponse resp) {
        return Utils.isValidData(teacher.getFullName(), NAME_REGEX, resp);
    }
}
