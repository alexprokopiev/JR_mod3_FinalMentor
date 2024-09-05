package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.GroupServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;
import java.security.InvalidParameterException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
@Getter
@AllArgsConstructor
public class GroupService {

    private final LocalDB db;
    private final JsonMapper mapper;

    public void addGroup(GroupServlet groupServlet, String requestBody) {
        //регистрация преобразователя json -> java-объект
        mapper.registerModule(new JavaTimeModule());
        //создание объекта Group
        Group group;
        try {
            group = mapper.readValue(requestBody, Group.class);
        } catch (Exception e) {
            groupServlet.setErrorMessage(JSON_PARSING_ERROR);
            throw new InvalidParameterException();
        }
        //если данные объекта Group соответствуют значениям переменных, установленных в properties
        if (group.getStudents().size() >= Integer.parseInt(db.getProps().getProperty(MIN_GROUP_SIZE))
                && group.getStudents().size() <= Integer.parseInt(db.getProps().getProperty(MAX_GROUP_SIZE))) {
            //поиск и установка уникального ID для объекта Group
            group.setId(Utils.findUniqueId(db.getGroups()));
            //добавление объекта в общий список
            db.getGroups().add(group);
            log.info(ADD_GROUP_MSG, group.getGroupNumber());
        //если данные объекта Group не соответствуют значениям переменных, установленных в properties
        } else {
            groupServlet.setErrorMessage(STUDENTS_NUMBER_ERROR);
            throw new InvalidParameterException();
        }
    }

    public List<Group> getGroups(String lastName, String groupNumber) {
        //получение списка всех групп
        List<Group> groups = db.getGroups();
        List<Group> filteredGroups = new ArrayList<>();
        //если в URL передан параметр с фамилией студента
        if (lastName != null) {
            for (Group group : groups) {
                List<Student> filteredStudents = Utils.findListElementsByParameter(group.getStudents(), student -> student.getLastName().equals(lastName));
                if (!filteredStudents.isEmpty()) filteredGroups.add(group);
            }
        //если в URL передан параметр с номером группы
        } else if (groupNumber != null) {
            filteredGroups = Utils.findListElementsByParameter(groups, group -> group.getGroupNumber().equals(groupNumber));
        //если в URL только /groups
        } else {
            filteredGroups = groups;
        }
        log.info(GET_GROUP_MSG);
        return filteredGroups;
    }

    public void editGroup(GroupServlet groupServlet, String requestUrl, String[] studentsId) {
        //валидация и парсинг URL
        int groupId = Utils.getId(requestUrl);
        if (groupId == -1) {
            groupServlet.setErrorMessage(ID_EXTRACT_ERROR);
            throw new NumberFormatException();
        }
        List<Student> students = db.getStudents();
        List<Group> groups = db.getGroups();
        //если группа под заданным ID существует
        if (Utils.isExistId(groups, groupId)) {
            //поиск группы по переданному ID
            Group filteredGroup = Utils.findListElementsByParameter(groups, group -> group.getId() == groupId).getFirst();
            //для каждого ID студента из переданных параметров
            for (String idAsString : studentsId) {
                //парсинг ID из параметра
                int studentId;
                try {
                    studentId = Integer.parseInt(idAsString);
                } catch (NumberFormatException e) {
                    groupServlet.setErrorMessage(ID_EXTRACT_ERROR);
                    throw new NumberFormatException();
                }
                //если студент с заданным ID существует
                if (Utils.isExistId(students, studentId)) {
                    //поиск студента в "базе данных" и добавление в заданную группу
                    Student fiteredStudent = Utils.findListElementsByParameter(students, student -> student.getId() == studentId).getFirst();
                    filteredGroup.getStudents().add(fiteredStudent);
                    log.info(EDIT_GROUP_MSG, filteredGroup.getGroupNumber(), fiteredStudent.getLastName(), fiteredStudent.getName(), fiteredStudent.getPatronymic());
                //если студента с заданным ID не существует
                } else {
                    groupServlet.setErrorMessage(STUDENT_FIND_ERROR);
                    throw new InvalidParameterException();
                }
            }
        //если группа под заданным ID не существует
        } else {
            groupServlet.setErrorMessage(GROUP_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }
}
