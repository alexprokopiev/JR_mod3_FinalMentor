package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.servlets.StudentServlet;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.time.LocalDate;
import java.security.InvalidParameterException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
@Getter
@AllArgsConstructor
public class StudentService {

    private final LocalDB db;
    private final JsonMapper mapper;

    public void addStudent(StudentServlet studentServlet, String requestBody) {
        //регистрация преобразователя json -> java-объект
        mapper.registerModule(new JavaTimeModule());
        Student student;
        //чтение json в объект
        try {
            student = mapper.readValue(requestBody, Student.class);
        } catch (Exception e) {
            studentServlet.setErrorMessage(JSON_PARSING_ERROR);
            throw new InvalidParameterException();
        }
        //валидация распарсенных данных объекта Student
        if (isValidStudentData(student)) {
            //поиск и установка уникального ID для объекта Student
            student.setId(Utils.findUniqueId(db.getStudents()));
            //добавление объекта в общий список
            db.getStudents().add(student);
            //установка статуса http ответа
            log.info(ADD_STUDENT_MSG, student.getLastName(), student.getName(), student.getPatronymic());
        } else {
            studentServlet.setErrorMessage(DATA_FORMAT_ERROR);
            throw new InvalidParameterException();
        }
    }

    public List<Student> getStudents(StudentServlet studentServlet, String requestUrl, String lastName) {
        //валидация и парсинг URL
        int studentId = Utils.getId(requestUrl);
        if (studentId == -1) {
            studentServlet.setErrorMessage(ID_EXTRACT_ERROR);
            throw new NumberFormatException();
        }
        //получение списка всех студентов
        List<Student> students = db.getStudents();
        List<Student> filteredStudents;
        //если в URL передан ID студента
        if (Utils.isExistId(students, studentId)) {
            filteredStudents = Utils.findListElementsByParameter(students, student -> student.getId() == studentId);
        //если в URL передан параметр с фамилией студента
        } else if (lastName != null) {
            filteredStudents = Utils.findListElementsByParameter(students, student -> student.getLastName().equals(lastName));
        //если в URL только /students
        } else if (studentId == 0) {
            filteredStudents = students;
        } else {
            studentServlet.setErrorMessage(DATA_FORMAT_ERROR);
            throw new InvalidParameterException();
        }
        log.info(GET_STUDENT_MSG);
        return filteredStudents;
    }

    public void editStudent(StudentServlet studentServlet, String requestUrl, String[] parameters) {
        //валидация ID, переданного в URL
        int studentId = Utils.getId(requestUrl);
        if (studentId == -1) {
            studentServlet.setErrorMessage(ID_EXTRACT_ERROR);
            throw new NumberFormatException();
        }
        List<Student> students = db.getStudents();
        //если студент под заданным ID существует
        if (Utils.isExistId(students, studentId)) {
            Student filteredStudent = Utils.findListElementsByParameter(students, student -> student.getId() == studentId).getFirst();
            //парсинг, валидация и запись в объект Student переданных параметров
            if (parameters[0] != null && Utils.isValidData(parameters[0], NAME_REGEX)) filteredStudent.setName(parameters[0]);
            if (parameters[1] != null && Utils.isValidData(parameters[1], NAME_REGEX)) filteredStudent.setPatronymic(parameters[1]);
            if (parameters[2] != null && Utils.isValidData(parameters[2], NAME_REGEX)) filteredStudent.setLastName(parameters[2]);
            if (parameters[3] != null) {
                try {
                    filteredStudent.setBirthDate(LocalDate.parse(parameters[3]));
                } catch (Exception e) {
                    log.error(DATE_PARSING_ERROR);
                }
            }
            if (parameters[4] != null && Utils.isValidData(parameters[4], PHONE_NUMBER_REGEX)) filteredStudent.setPhoneNumber(parameters[4]);
            log.info(EDIT_STUDENT_MSG, filteredStudent.getLastName(), filteredStudent.getName(), filteredStudent.getPatronymic());
        //если студента под заданным ID не существует
        } else {
            studentServlet.setErrorMessage(STUDENT_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }

    public void deleteStudent(StudentServlet studentServlet, String requestUrl) {
        //валидация ID, переданного в URL
        int studentId = Utils.getId(requestUrl);
        if (studentId == -1) {
            studentServlet.setErrorMessage(ID_EXTRACT_ERROR);
            throw new NumberFormatException();
        }
        List<Student> students = db.getStudents();
        //если студент под заданным ID существует
        if (Utils.isExistId(students, studentId)) {
            Student filteredStudent = Utils.findListElementsByParameter(students, student -> student.getId() == studentId).getFirst();
            //удаление найденного по ID объекта Student
            students.remove(filteredStudent);
            log.info(DELETE_STUDENT_MSG, filteredStudent.getLastName(), filteredStudent.getName(), filteredStudent.getPatronymic());
        //если студента под заданным ID не существует
        } else {
            studentServlet.setErrorMessage(STUDENT_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }

    //валидация данных объекта Student
    private boolean isValidStudentData(Student student) {
        if (!Utils.isValidData(student.getName(), NAME_REGEX)) return false;
        if (!Utils.isValidData(student.getPatronymic(), NAME_REGEX)) return false;
        if (!Utils.isValidData(student.getLastName(), NAME_REGEX)) return false;
        if (!Utils.isValidData(student.getBirthDate().toString(), DATE_REGEX)) return false;
        return Utils.isValidData(student.getPhoneNumber(), PHONE_NUMBER_REGEX);
    }
}
