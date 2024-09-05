package org.example.jr_mod3_finalmentor.services;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.servlets.TeacherServlet;

import java.util.List;
import java.security.InvalidParameterException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
@Getter
@AllArgsConstructor
public class TeacherService {

    private final LocalDB db;
    private final JsonMapper mapper;

    public void addTeacher(TeacherServlet teacherServlet, String requestBody) {
        //создание объекта Teacher
        Teacher teacher;
        try {
            //чтение json в объект
            teacher = mapper.readValue(requestBody, Teacher.class);
        } catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            teacherServlet.setErrorMessage(JSON_PARSING_ERROR);
            throw new InvalidParameterException();
        }
        //валидация распарсенных данных объекта Teacher
        if (Utils.isValidData(teacher.getFullName(), NAME_REGEX)) {
            //поиск и установка уникального ID для объекта Teacher
            teacher.setId(Utils.findUniqueId(db.getTeachers()));
            //добавление объекта в общий список
            db.getTeachers().add(teacher);
            //установка статуса http ответа
            log.info(ADD_TEACHER_MSG, teacher.getFullName());
        } else {
            teacherServlet.setErrorMessage(STUDENT_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }

    public List<Teacher> getTeachers() {
        //валидация и парсинг URL - не требуется
        //получение списка всех преподавателей и отправка http ответа
        List<Teacher> teachers = db.getTeachers();
        log.info(GET_TEACHER_MSG);
        return teachers;
    }

    public void editTeacher(TeacherServlet teacherServlet, String requestUrl, String subjectAsString) {
        //валидация и парсинг URL
        int teacherId = Utils.getId(requestUrl);
        if (teacherId == -1) {
            teacherServlet.setErrorMessage(ID_EXTRACT_ERROR);
            throw new NumberFormatException();
        }
        List<Teacher> teachers = db.getTeachers();
        //если преподаватель под заданным ID существует
        if (Utils.isExistId(teachers, teacherId)) {
            Teacher filteredTeacher = Utils.findListElementsByParameter(teachers, student -> student.getId() == teacherId).getFirst();
            try {
                filteredTeacher.getSubjects().add(Subject.valueOf(subjectAsString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                teacherServlet.setErrorMessage(SUBJECT_ERROR);
                throw new InvalidParameterException();
            }
            log.info(EDIT_TEACHER_MSG, filteredTeacher.getFullName());
        //если преподавателя под заданным ID не существует
        } else {
            teacherServlet.setErrorMessage(TEACHER_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }
}
