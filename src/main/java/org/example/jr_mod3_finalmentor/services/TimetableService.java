package org.example.jr_mod3_finalmentor.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.utils.Utils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;
import java.time.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

public class TimetableService {

    private static final Logger logger = LogManager.getLogger(TimetableService.class);

    public void addLesson(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException {
        //создание преобразователя json -> java-объект
        JsonMapper mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        //создание объекта LessonDTO
        LessonDTO lessonDTO = new LessonDTO();
        //получение тела запроса
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            //чтение json в объект
            lessonDTO = mapper.readValue(requestBody, LessonDTO.class);
        }
        catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            logger.error(JSON_PARSING_ERROR);
            resp.setStatus(400);
        }
        //если данные http запроса соответствуют значениям переменных, установленных в properties
        if (isOpeningDate(lessonDTO, db)) {
            //создание объекта Lesson
            Lesson lesson = new Lesson();
            //установка значений полей объекта Lesson
            lesson.setId(Utils.findUniqueId(db.getTimetable()));
            lesson.setStartTime(LocalDateTime.parse(lessonDTO.getStartTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            lesson.setEndTime(LocalDateTime.parse(lessonDTO.getEndTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            int groupId = Integer.parseInt(lessonDTO.getGroupId());
            Group filteredGroup = (Group) Utils.findListElementById(db.getGroups(), groupId);
            lesson.setGroup(filteredGroup);
            int teacherId = Integer.parseInt(lessonDTO.getTeacherId());
            Teacher filteredTeacher = (Teacher) Utils.findListElementById(db.getTeachers(), teacherId);
            lesson.setTeacher(filteredTeacher);
            //добавление объекта в общий список
            db.getTimetable().add(lesson);
            logger.info(ADD_LESSON_MSG, filteredGroup.getGroupNumber(), lesson.getStartTime().toString());
            resp.setStatus(201);
        //если данные http запроса не соответствуют значениям переменных, установленных в properties
        } else {
            logger.error(LESSONS_NUMBER_ERROR);
            resp.setStatus(400);
        }
    }

    public void getTimetable(HttpServletRequest req, HttpServletResponse resp, LocalDB db) throws IOException, ServletException {
        //парсинг URL (валидация не требуется)
        String groupNumber = req.getParameter(GROUP_NUMBER);
        String lastName = req.getParameter(LAST_NAME);
        String fullName = req.getParameter(FULL_NAME);
        String dateAsString = req.getParameter(DATE);
        //получение полного расписания
        List<Lesson> timetable = db.getTimetable();
        //если в URL передан параметр с номером группы
        if (groupNumber != null) {
            List<Lesson> filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getGroup().getGroupNumber().equals(groupNumber));
            logger.info(GET_TIMETABLE_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredTimetable.toString());
            else {
                req.setAttribute(GROUP_NUMBER, groupNumber);
                req.setAttribute(TIMETABLE, filteredTimetable);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        //если в URL передан параметр с фамилией студента
        } else if (lastName != null) {
            List<Lesson> filteredTimetable = new ArrayList<>();
            for (Lesson lesson : timetable) {
                List<Student> filteredStudents = Utils.findListElementsByParameter(lesson.getGroup().getStudents(), student -> student.getLastName().equals(lastName));
                if (!filteredStudents.isEmpty()) filteredTimetable.add(lesson);
            }
            logger.info(GET_TIMETABLE_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredTimetable.toString());
            else {
                req.setAttribute(LAST_NAME, lastName);
                req.setAttribute(TIMETABLE, filteredTimetable);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        //если в URL передан параметр с ФИО преподавателя
        } else if (fullName != null) {
            List<Lesson> filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getTeacher().getFullName().equals(fullName));
            logger.info(GET_TIMETABLE_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredTimetable.toString());
            else {
                req.setAttribute(FULL_NAME, fullName);
                req.setAttribute(TIMETABLE, filteredTimetable);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        //если в URL передан параметр с датой занятий
        } else if (dateAsString != null) {
            List<Lesson> filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getStartTime().toLocalDate().equals(LocalDate.parse(dateAsString)));
            logger.info(GET_TIMETABLE_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredTimetable.toString());
            else {
                req.setAttribute(DATE, dateAsString);
                req.setAttribute(TIMETABLE, filteredTimetable);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        //если в URL только /timetable
        } else {
            logger.info(GET_TIMETABLE_MSG);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, timetable.toString());
            else {
                req.setAttribute(TIMETABLE, timetable);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        }
    }

    public void editLesson(HttpServletRequest req, HttpServletResponse resp, LocalDB db) {
        //валидация и парсинг URL
        int lessonId = Utils.getId(req, resp);
        List<Lesson> timetable = db.getTimetable();
        //если занятие под заданным ID существует
        if (Utils.isExistId(timetable, lessonId)) {
            //поиск занятия по переданному ID
            Lesson filteredLesson = (Lesson) Utils.findListElementById(timetable, lessonId);
            //парсинг ID группы из параметра
            int groupId = -1;
            try {groupId = Integer.parseInt(req.getParameter(GROUP_ID));}
            catch (NumberFormatException e) {
                logger.error(ID_EXTRACT_ERROR);
                resp.setStatus(400);
                return;
            }
            //если группа с данным ID существует
            if (Utils.isExistId(db.getGroups(), groupId)) {
                Group filteredGroup = (Group) Utils.findListElementById(db.getGroups(), groupId);
                filteredLesson.setGroup(filteredGroup);
            }
            //парсинг ID преподавателя из параметра
            int teacherId = -1;
            try {teacherId = Integer.parseInt(req.getParameter(TEACHER_ID));}
            catch (NumberFormatException e) {
                logger.error(ID_EXTRACT_ERROR);
                resp.setStatus(400);
                return;
            }
            //если преподаватель с данным ID существует
            if (Utils.isExistId(db.getTeachers(), teacherId)) {
                Teacher filteredTeacher = (Teacher) Utils.findListElementById(db.getTeachers(), teacherId);
                filteredLesson.setTeacher(filteredTeacher);
            }
            //парсинг времени начала и окончания занятия из параметров
            String startTimeAsString = req.getParameter(START_TIME);
            String endTimeAsString = req.getParameter(END_TIME);
            //запись времени начала и окончания занятия
            filteredLesson.setStartTime(parseDateTime(startTimeAsString, resp));
            filteredLesson.setEndTime(parseDateTime(endTimeAsString, resp));
            logger.info(EDIT_LESSON_MSG, filteredLesson.getGroup().getGroupNumber(), filteredLesson.getStartTime().toString());
        //если занятия под заданным ID не существует
        } else {
            logger.error(LESSON_FIND_ERROR);
            resp.setStatus(400);
        }
    }

    //проверка данных http запроса на соответствие значениям переменных, установленных в properties
    private boolean isOpeningDate(LessonDTO lessonDTO, LocalDB db) {
        LocalDate extractedDate = LocalDateTime.parse(lessonDTO.getStartTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)).toLocalDate();
        int extractedGroupId = Integer.parseInt(lessonDTO.getGroupId());
        List<Lesson> filteredTimetable = db.getTimetable().stream()
                .filter(lesson -> lesson.getStartTime().toLocalDate().equals(extractedDate))
                .filter(lesson -> lesson.getGroup().getId() == extractedGroupId)
                .toList();
        return filteredTimetable.size() < Integer.parseInt(db.getProps().getProperty(MAX_NUMBER_OF_LESSONS));
    }

    //парсинг даты и времени из строковых параметров
    private LocalDateTime parseDateTime(String dateTimeAsString, HttpServletResponse resp) {
        try {
            return LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        } catch (Exception e) {
            logger.error(DATE_TIME_PARSING_ERROR);
            resp.setStatus(400);
            return null;
        }
    }
}
