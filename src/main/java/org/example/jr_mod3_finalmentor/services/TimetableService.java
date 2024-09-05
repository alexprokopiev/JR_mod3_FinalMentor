package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.TimetableServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.security.InvalidParameterException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
@Getter
@AllArgsConstructor
public class TimetableService {

    private final LocalDB db;
    private final JsonMapper mapper;

    public void addLesson(TimetableServlet timetableServlet, String requestBody) {
        //регистрация преобразователя json -> java-объект
        mapper.registerModule(new JavaTimeModule());
        //создание объекта LessonDTO
        LessonDTO lessonDTO;
        try {
            //чтение json в объект
            lessonDTO = mapper.readValue(requestBody, LessonDTO.class);
        }
        catch (JsonProcessingException e) {
            //обработка ошибки чтения json
            timetableServlet.setErrorMessage(JSON_PARSING_ERROR);
            throw new InvalidParameterException();
        }
        //если данные http запроса соответствуют значениям переменных, установленных в properties
        if (isOpeningDate(lessonDTO)) {
            //создание объекта Lesson
            Lesson lesson = new Lesson();
            //установка значений полей объекта Lesson
            lesson.setId(Utils.findUniqueId(db.getTimetable()));
            lesson.setStartTime(LocalDateTime.parse(lessonDTO.getStartTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            lesson.setEndTime(LocalDateTime.parse(lessonDTO.getEndTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            int groupId = Integer.parseInt(lessonDTO.getGroupId());
            Group filteredGroup = Utils.findListElementsByParameter(db.getGroups(), group -> group.getId() == groupId).getFirst();
            lesson.setGroup(filteredGroup);
            int teacherId = Integer.parseInt(lessonDTO.getTeacherId());
            Teacher filteredTeacher = Utils.findListElementsByParameter(db.getTeachers(), teacher -> teacher.getId() == teacherId).getFirst();
            lesson.setTeacher(filteredTeacher);
            //добавление объекта в общий список
            db.getTimetable().add(lesson);
            log.info(ADD_LESSON_MSG, filteredGroup.getGroupNumber(), lesson.getStartTime().toString());
        //если данные http запроса не соответствуют значениям переменных, установленных в properties
        } else {
            timetableServlet.setErrorMessage(LESSONS_NUMBER_ERROR);
            throw new InvalidParameterException();
        }
    }

    public List<Lesson> getTimetable(String[] parameters) {
        //парсинг URL (валидация не требуется)
        String groupNumber = parameters[0];
        String lastName = parameters[1];
        String fullName = parameters[2];
        String dateAsString = parameters[3];
        //получение полного расписания
        List<Lesson> timetable = db.getTimetable();
        //если в URL передан параметр с номером группы
        List<Lesson> filteredTimetable = new ArrayList<>();
        if (groupNumber != null) {
            filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getGroup().getGroupNumber().equals(groupNumber));
        //если в URL передан параметр с фамилией студента
        } else if (lastName != null) {
            for (Lesson lesson : timetable) {
                List<Student> filteredStudents = Utils.findListElementsByParameter(lesson.getGroup().getStudents(), student -> student.getLastName().equals(lastName));
                if (!filteredStudents.isEmpty()) filteredTimetable.add(lesson);
            }
        //если в URL передан параметр с ФИО преподавателя
        } else if (fullName != null) {
            filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getTeacher().getFullName().equals(fullName));
        //если в URL передан параметр с датой занятий
        } else if (dateAsString != null) {
            filteredTimetable = Utils.findListElementsByParameter(timetable, lesson -> lesson.getStartTime().toLocalDate().equals(LocalDate.parse(dateAsString)));
        //если в URL только /timetable
        } else {
            filteredTimetable = timetable;

        }
        log.info(GET_TIMETABLE_MSG);
        return filteredTimetable;
    }

    public void editLesson(TimetableServlet timetableServlet, String requestUrl, String[] parameters) {
        //валидация и парсинг URL
        int lessonId = Utils.getId(requestUrl);
        List<Lesson> timetable = db.getTimetable();
        //если занятие под заданным ID существует
        if (Utils.isExistId(timetable, lessonId)) {
            //поиск занятия по переданному ID
            Lesson filteredLesson = Utils.findListElementsByParameter(timetable, lesson -> lesson.getId() == lessonId).getFirst();
            //парсинг ID группы из параметра
            int groupId;
            try {
                groupId = Integer.parseInt(parameters[0]);
            } catch (NumberFormatException e) {
                timetableServlet.setErrorMessage(ID_EXTRACT_ERROR);
                throw new InvalidParameterException();
            }
            //если группа с данным ID существует
            if (Utils.isExistId(db.getGroups(), groupId)) {
                Group filteredGroup = Utils.findListElementsByParameter(db.getGroups(), group -> group.getId() == groupId).getFirst();
                filteredLesson.setGroup(filteredGroup);
            }
            //парсинг ID преподавателя из параметра
            int teacherId;
            try {
                teacherId = Integer.parseInt(parameters[1]);
            } catch (NumberFormatException e) {
                timetableServlet.setErrorMessage(ID_EXTRACT_ERROR);
                throw new InvalidParameterException();
            }
            //если преподаватель с данным ID существует
            if (Utils.isExistId(db.getTeachers(), teacherId)) {
                Teacher filteredTeacher = Utils.findListElementsByParameter(db.getTeachers(), teacher -> teacher.getId() == teacherId).getFirst();
                filteredLesson.setTeacher(filteredTeacher);
            }
            //парсинг времени начала и окончания занятия из параметров
            String startTimeAsString = parameters[2];
            String endTimeAsString = parameters[3];
            //запись времени начала и окончания занятия
            try {
                filteredLesson.setStartTime(LocalDateTime.parse(startTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
                filteredLesson.setEndTime(LocalDateTime.parse(endTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            } catch (Exception e) {
                timetableServlet.setErrorMessage(DATE_TIME_PARSING_ERROR);
                throw new InvalidParameterException();
            }
            log.info(EDIT_LESSON_MSG, filteredLesson.getGroup().getGroupNumber(), filteredLesson.getStartTime().toString());
        //если занятия под заданным ID не существует
        } else {
            timetableServlet.setErrorMessage(LESSON_FIND_ERROR);
            throw new InvalidParameterException();
        }
    }

    //проверка данных http запроса на соответствие значениям переменных, установленных в properties
    private boolean isOpeningDate(LessonDTO lessonDTO) {
        LocalDate extractedDate = LocalDateTime.parse(lessonDTO.getStartTime(), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)).toLocalDate();
        int extractedGroupId = Integer.parseInt(lessonDTO.getGroupId());
        List<Lesson> filteredTimetable = db.getTimetable().stream()
                .filter(lesson -> lesson.getStartTime().toLocalDate().equals(extractedDate))
                .filter(lesson -> lesson.getGroup().getId() == extractedGroupId)
                .toList();
        return filteredTimetable.size() < Integer.parseInt(db.getProps().getProperty(MAX_NUMBER_OF_LESSONS));
    }
}
