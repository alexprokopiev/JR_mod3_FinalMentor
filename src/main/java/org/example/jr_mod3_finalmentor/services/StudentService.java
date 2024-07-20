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
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    public void addStudent(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException {
        //создание преобразователя json -> java-объект
        JsonMapper mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        //создание объекта Student
        Student student = new Student();
        //получение тела запроса
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            //чтение json в объект
            student = mapper.readValue(requestBody, Student.class);
        } catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            logger.error(JSON_PARSING_ERROR);
            resp.setStatus(400);
        }
        //валидация распарсенных данных объекта Student
        if (isValidStudentData(student, resp)) {
            //поиск и установка уникального ID для объекта Student
            student.setId(Utils.findUniqueId(db.getStudents()));
            //добавление объекта в общий список
            db.getStudents().add(student);
            //установка статуса http ответа
            logger.info(ADD_STUDENT_MSG, student.getLastName(), student.getName(), student.getPatronymic());
            resp.setStatus(201);
        }
    }

    public void getStudents(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException, ServletException {
        //валидация и парсинг URL
        int studentId = Utils.getId(req, resp);
        String lastName = req.getParameter(LAST_NAME);
        //получение списка всех студентов
        List<Student> students = db.getStudents();
        //если в URL передан ID студента
        if (Utils.isExistId(students, studentId)) {
            Student filteredStudent = (Student) Utils.findListElementById(students, studentId);
            logger.info(GET_STUDENT_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredStudent.toString());
            else {
                req.setAttribute(STUDENT, filteredStudent);
                req.setAttribute(STUDENT_ID, studentId);
                req.getRequestDispatcher(STUDENTS_JSP).forward(req, resp);
            }
        //если в URL передан параметр с фамилией студента
        } else if (lastName != null) {
            List<Student> filteredStudents = Utils.findListElementsByParameter(students, student -> student.getLastName().equals(lastName));
            logger.info(GET_STUDENT_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredStudents.toString());
            else {
                req.setAttribute(STUDENTS, filteredStudents);
                req.setAttribute(LAST_NAME, lastName);
                req.getRequestDispatcher(STUDENTS_JSP).forward(req, resp);
            }
        //если в URL только /students
        } else if (studentId == 0) {
            logger.info(GET_STUDENT_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, students.toString());
            else {
                req.setAttribute(STUDENTS, students);
                req.getRequestDispatcher(STUDENTS_JSP).forward(req, resp);
            }
        } else {
            logger.error(STUDENT_FIND_ERROR);
            resp.setStatus(400);
        }
    }

    public void editStudent(HttpServletRequest req, HttpServletResponse resp, LocalDB db) {
        //валидация ID, переданного в URL
        int studentId = Utils.getId(req, resp);
        List<Student> students = db.getStudents();
        //если студент под заданным ID существует
        if (Utils.isExistId(students, studentId)) {
            Student filteredStudent = (Student) Utils.findListElementById(students, studentId);
            //парсинг, валидация и запись в объект Student переданных параметров
            String name = req.getParameter(NAME);
            if (name != null && Utils.isValidData(name, NAME_REGEX, resp)) filteredStudent.setName(name);
            String patronymic = req.getParameter(PATRONYMIC);
            if (patronymic != null && Utils.isValidData(patronymic, NAME_REGEX, resp)) filteredStudent.setPatronymic(patronymic);
            String lastName = req.getParameter(LAST_NAME);
            if (lastName != null && Utils.isValidData(lastName, NAME_REGEX, resp)) filteredStudent.setLastName(lastName);
            String birthDateAsString = req.getParameter(BIRTH_DATE);
            if (birthDateAsString != null) filteredStudent.setBirthDate(parseBirthDate(filteredStudent,birthDateAsString, resp));
            String phoneNumber = req.getParameter(PHONE_NUMBER);
            if (phoneNumber != null  && Utils.isValidData(phoneNumber, PHONE_NUMBER_REGEX, resp)) filteredStudent.setPhoneNumber(phoneNumber);
            logger.info(EDIT_STUDENT_MSG, filteredStudent.getLastName(), filteredStudent.getName(), filteredStudent.getPatronymic());
        //если студента под заданным ID не существует
        } else {
            logger.error(STUDENT_FIND_ERROR);
            resp.setStatus(400);
        }
    }

    public void deleteStudent(HttpServletRequest req, HttpServletResponse resp, LocalDB db) {
        //валидация ID, переданного в URL
        int studentId = Utils.getId(req, resp);
        List<Student> students = db.getStudents();
        //если студент под заданным ID существует
        if (Utils.isExistId(students, studentId)) {
            Student filteredStudent = (Student) Utils.findListElementById(students, studentId);
            //удаление найденного по ID объекта Student
            students.remove(filteredStudent);
            logger.info(DELETE_STUDENT_MSG, filteredStudent.getLastName(), filteredStudent.getName(), filteredStudent.getPatronymic());
            resp.setStatus(204);
        //если студента под заданным ID не существует
        } else {
            logger.error(STUDENT_FIND_ERROR);
            resp.setStatus(400);
        }
    }

    //парсинг birthDate
    private LocalDate parseBirthDate(Student student, String birthDateAsString, HttpServletResponse resp) {
        try {
            return LocalDate.parse(birthDateAsString);
        } catch (Exception e) {
            //обработка ошибки чтения даты
            logger.error(DATE_PARSING_ERROR);
            resp.setStatus(400);
            return student.getBirthDate();
        }
    }

    //валидация данных объекта Student
    private boolean isValidStudentData(Student student, HttpServletResponse resp) {
        if (!Utils.isValidData(student.getName(), NAME_REGEX, resp)) return false;
        if (!Utils.isValidData(student.getPatronymic(), NAME_REGEX, resp)) return false;
        if (!Utils.isValidData(student.getLastName(), NAME_REGEX, resp)) return false;
        if (!Utils.isValidData(student.getBirthDate().toString(), DATE_REGEX, resp)) return false;
        return Utils.isValidData(student.getPhoneNumber(), PHONE_NUMBER_REGEX, resp);
    }
}
