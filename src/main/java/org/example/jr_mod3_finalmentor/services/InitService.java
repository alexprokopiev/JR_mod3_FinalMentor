package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.InitServlet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
@Getter
@AllArgsConstructor
public class InitService {

    private final LocalDB db;
    private final JsonMapper mapper;

    public void initLocalDB(InitServlet initServlet) throws IOException {
        readProperties(initServlet);
        initStudentsList(initServlet);
        initTeachersList(initServlet);
        initGroupslist();
        initTimetable(initServlet);
        log.info(INIT_MSG);
    }

    private void readProperties(InitServlet initServlet) throws IOException {
        Properties props = db.getProps();
        try (InputStream inputStream = InitService.class.getResourceAsStream(PROPS_PATH)) {
            props.load(inputStream);
            initServlet.setErrorMessage(PROPERTIES_ERROR);
        }
    }

    private <T> void readFromFile(InitServlet initServlet, List<T> dataList, TypeReference<List<T>> typeRef, String dataFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(InitService.class.getResourceAsStream(dataFile))))) {
            String studentsData = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            mapper.registerModule(new JavaTimeModule());
            dataList.addAll(mapper.readValue(studentsData, typeRef));
            initServlet.setErrorMessage(FILE_READ_ERROR);
        }
    }

    private void initStudentsList(InitServlet initServlet) throws IOException {
        List<Student> students = db.getStudents();
        TypeReference<List<Student>> typeRef = new TypeReference<>() {};
        if (students.isEmpty()) readFromFile(initServlet, students, typeRef, STUDENTS_DATA_PATH);
    }

    private void initTeachersList(InitServlet initServlet) throws IOException {
        List<Teacher> teachers = db.getTeachers();
        TypeReference<List<Teacher>> typeRef = new TypeReference<>() {};
        if (teachers.isEmpty()) readFromFile(initServlet, teachers, typeRef, TEACHERS_DATA_PATH);
    }

    private void initGroupslist() {
        List<Group> groups = db.getGroups();
        if (groups.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                List<Student> students = new ArrayList<>();
                for (int j = 0; j < 25; j++) {
                    students.add(db.getStudents().get(i * 25 + j));
                }
                groups.add(new Group(i + 1, "" + (31 + (int) Math.pow(31,i)), students));
            }
        }
    }

    private void initTimetable(InitServlet initServlet) throws IOException {
        List<LessonDTO> timetableDTO = new ArrayList<>();
        TypeReference<List<LessonDTO>> typeRef = new TypeReference<>() {};
        readFromFile(initServlet, timetableDTO, typeRef, TIMETABLE_DATA_PATH);
        List<Lesson> timetable = db.getTimetable();
        if (timetable.isEmpty()) {
            for (int i = 0; i < timetableDTO.size(); i++) {
                timetable.add(new Lesson(i + 1, LocalDateTime.parse(timetableDTO.get(i).getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        LocalDateTime.parse(timetableDTO.get(i).getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        db.getGroups().get(Integer.parseInt(timetableDTO.get(i).getGroupId()) - 1),
                        db.getTeachers().get(Integer.parseInt(timetableDTO.get(i).getTeacherId()) - 1)));
            }
        }
    }
}