package org.example.jr_mod3_finalmentor.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.utils.Utils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

public class GroupService {

    private static final Logger logger = LogManager.getLogger(GroupService.class);

    public void addGroup(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException {
        //создание преобразователя json -> java-объект
        JsonMapper mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        //создание объекта Group
        Group group = new Group();
        //получение тела запроса
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            //чтение json в объект
            group = mapper.readValue(requestBody, Group.class);
        } catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            logger.error(JSON_PARSING_ERROR);
            resp.setStatus(400);
        }
        //если данные объекта Group соответствуют значениям переменных, установленных в properties
        if (group.getStudents().size() >= Integer.parseInt(db.getProps().getProperty(MIN_GROUP_SIZE))
                && group.getStudents().size() <= Integer.parseInt(db.getProps().getProperty(MAX_GROUP_SIZE))) {
            //поиск и установка уникального ID для объекта Group
            group.setId(Utils.findUniqueId(db.getGroups()));
            //добавление объекта в общий список
            db.getGroups().add(group);
            logger.info(ADD_GROUP_MSG, group.getGroupNumber());
            //установка статуса http ответа
            resp.setStatus(201);
        //если данные объекта Group не соответствуют значениям переменных, установленных в properties
        } else {
            logger.error(STUDENTS_NUMBER_ERROR);
            resp.setStatus(400);
        }
    }

    public void getGroups(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException, ServletException {
        //парсинг URL (валидация не требуется)
        String lastName = req.getParameter(LAST_NAME);
        String groupNumber = req.getParameter(GROUP_NUMBER);
        //получение списка всех групп
        List<Group> groups = db.getGroups();
        //если в URL передан параметр с фамилией студента
        if (lastName != null) {
            List<Group> filteredGroups = new ArrayList<>();
            for (Group group : groups) {
                List<Student> filteredStudents = Utils.findListElementsByParameter(group.getStudents(), student -> student.getLastName().equals(lastName));
                if (!filteredStudents.isEmpty()) filteredGroups.add(group);
            }
            logger.info(GET_GROUP_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredGroups.toString());
            else {
                req.setAttribute(LAST_NAME, lastName);
                req.setAttribute(GROUPS, filteredGroups);
                req.getRequestDispatcher(GROUPS_JSP).forward(req, resp);
            }
            //если в URL передан параметр с номером группы
        } else if (groupNumber != null) {
            List<Group> filteredGroups = Utils.findListElementsByParameter(groups, group -> group.getGroupNumber().equals(groupNumber));
            logger.info(GET_GROUP_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredGroups.toString());
            else {
                req.setAttribute(GROUP_NUMBER, groupNumber);
                req.setAttribute(GROUPS, filteredGroups);
                req.getRequestDispatcher(GROUPS_JSP).forward(req, resp);
            }
            //если в URL только /groups
        } else {
            logger.info(GET_GROUP_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, groups.toString());
            else {
                req.setAttribute(GROUPS, groups);
                req.getRequestDispatcher(GROUPS_JSP).forward(req, resp);
            }
        }
    }

    public void editGroup(HttpServletRequest req, HttpServletResponse resp, LocalDB db) {
        //валидация и парсинг URL
        int groupId = Utils.getId(req, resp);
        String[] studentsId = req.getParameterValues(STUDENT_ID);
        List<Student> students = db.getStudents();
        List<Group> groups = db.getGroups();
        //если группа под заданным ID существует
        if (Utils.isExistId(groups, groupId)) {
            //поиск группы по переданному ID
            Group filteredGroup = (Group) Utils.findListElementById(groups, groupId);
            //для каждого ID студента из переданных параметров
            for (String idAsString : studentsId) {
                //парсинг ID из параметра
                int studentId;
                try {studentId = Integer.parseInt(idAsString);}
                catch (NumberFormatException e) {
                    logger.error(ID_EXTRACT_ERROR);
                    resp.setStatus(400);
                    return;
                }
                //если студент с заданным ID существует
                if (Utils.isExistId(students, studentId)) {
                    //поиск студента в "базе данных" и добавление в заданную группу
                    Student fiteredStudent = (Student) Utils.findListElementById(students, studentId);
                    filteredGroup.getStudents().add(fiteredStudent);
                    logger.info(EDIT_GROUP_MSG, filteredGroup.getGroupNumber(), fiteredStudent.getLastName(), fiteredStudent.getName(), fiteredStudent.getPatronymic());
                //если студента с заданным ID не существует
                } else {
                    logger.error(STUDENT_FIND_ERROR);
                    resp.setStatus(400);
                }
            }
        //если группа под заданным ID не существует
        } else {
            logger.error(GROUP_FIND_ERROR);
            resp.setStatus(400);
        }
    }
}
