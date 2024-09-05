package org.example.jr_mod3_finalmentor.services;

import lombok.SneakyThrows;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.TimetableServlet;

import org.example.jr_mod3_finalmentor.models.LocalDB;


import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.models.Subject.*;
import static org.example.jr_mod3_finalmentor.consts.Constants.*;
import static org.example.jr_mod3_finalmentor.consts.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class TimetableServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    LocalDB testDb;
    JsonMapper mapper;
    @Mock
    TimetableServlet timetableServlet;

    TimetableService testTimetableService;
    List<Lesson> testTimetable;
    List<Group> testGroupsList;
    List<Teacher> testTeachersList;
    List<Student> testStudentsList;

    @BeforeEach
    void setup() {
        mapper = new JsonMapper();
        testTimetableService = new TimetableService(testDb, mapper);
        testTimetable = new ArrayList<>();
        testGroupsList = new ArrayList<>();
        testTeachersList = new ArrayList<>();
        testStudentsList = new ArrayList<>();

        testStudentsList.add(new Student(1, "Захар", "Степанович", "Шаповалов", LocalDate.parse("04.04.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79911556928"));
        testStudentsList.add(new Student(2, "Платон", "Михайлович", "Попов", LocalDate.parse("01.06.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79884197690"));
        testStudentsList.add(new Student(3, "Варвара", "Романовна", "Ситникова", LocalDate.parse("19.09.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79554967582"));
        testStudentsList.add(new Student(4, "Полина", "Михайловна", "Боброва", LocalDate.parse("21.11.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79627318718"));
        testStudentsList.add(new Student(5, "Александра", "Матвеевна", "Ситникова", LocalDate.parse("29.09.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79889609879"));
        testStudentsList.add(new Student(6, "Вероника", "Леонидовна", "Мельникова", LocalDate.parse("24.10.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79057538939"));
        testStudentsList.add(new Student(7, "Елизавета", "Владимировна", "Федорова", LocalDate.parse("19.04.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79393475908"));
        testStudentsList.add(new Student(8, "Полина", "Константиновна", "Филиппова", LocalDate.parse("18.03.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79991999385"));
        testStudentsList.add(new Student(9, "Ева", "Викторовна", "Ковалева", LocalDate.parse("26.02.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79992125259"));
        testStudentsList.add(new Student(10, "Никита", "Иванович", "Никитин", LocalDate.parse("12.04.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79319428981"));
        testStudentsList.add(new Student(11, "Лука", "Лукич", "Сазонов", LocalDate.parse("01.11.2002", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79388645195"));
        testStudentsList.add(new Student(12, "Семён", "Викторович", "Высоцкий", LocalDate.parse("09.03.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79635925880"));
        testStudentsList.add(new Student(13, "Леонид", "Никитич", "Ткачев", LocalDate.parse("27.10.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79325360790"));
        testStudentsList.add(new Student(14, "Денис", "Андреевич", "Сидоров", LocalDate.parse("31.08.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79629519295"));
        testStudentsList.add(new Student(15, "Аделина", "Кирилловна", "Федорова", LocalDate.parse("01.08.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79190853477"));
        testStudentsList.add(new Student(16, "Софья", "Владимировна", "Попова", LocalDate.parse("06.11.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79920527792"));
        testStudentsList.add(new Student(46, "Виктория", "Константиновна", "Костина", LocalDate.parse("16.10.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79883755463"));
        testStudentsList.add(new Student(63, "Варвара", "Романовна", "Ситникова", LocalDate.parse("19.09.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79554967582"));

        testTeachersList.add(new Teacher(1, "Леонова Виктория Артемьевна", 50L, new ArrayList<>(List.of(ALGEBRA, GEOMETRY)), 25));
        testTeachersList.add(new Teacher(2, "Дорохов Константин Денисович", 48L, new ArrayList<>(List.of(DRAWING, MUSIC)), 23));
        testTeachersList.add(new Teacher(3, "Егорова Анастасия Платоновна", 39L, new ArrayList<>(List.of(PHYSICAL_EDUCATION)), 10));

        testGroupsList.add(new Group(1, "111", new ArrayList<>()));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(17));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(0));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(1));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(2));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(3));
        testGroupsList.add(new Group(2, "2222", new ArrayList<>()));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(16));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(5));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(6));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(7));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(8));
        testGroupsList.get(1).getStudents().add(testStudentsList.get(9));
        testGroupsList.add(new Group(3, "33333", new ArrayList<>()));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(4));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(10));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(11));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(12));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(13));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(14));
        testGroupsList.get(2).getStudents().add(testStudentsList.get(15));

        testTimetable.add(new Lesson(1, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(0), testTeachersList.get(0)));
        testTimetable.add(new Lesson(2, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(1), testTeachersList.get(1)));
        testTimetable.add(new Lesson(3, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(1), testTeachersList.get(2)));
        testTimetable.add(new Lesson(4, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(2), testTeachersList.get(0)));
        testTimetable.add(new Lesson(5, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(0), testTeachersList.get(1)));
        testTimetable.add(new Lesson(6, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(1), testTeachersList.get(2)));
        testTimetable.add(new Lesson(7, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(1), testTeachersList.get(0)));
        testTimetable.add(new Lesson(8, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), testGroupsList.get(2), testTeachersList.get(1)));
    }

    //объект Lesson парсится из верного http запроса и все поля объекта записаны корректно
    @Test
    @SneakyThrows
    void shouldAddLessonCorrectlyTest() {
        Mockito.when(testTimetableService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.when(testTimetableService.getDb().getTeachers()).thenReturn(testTeachersList);
        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);
        Mockito.when(testTimetableService.getDb().getProps().getProperty(MAX_NUMBER_OF_LESSONS)).thenReturn("5");

        testTimetableService.addLesson(timetableServlet, TEST_LESSON_JSON);

        assertEquals(9, testTimetable.getLast().getId());
        assertEquals("2024-07-02T13:00", testTimetable.getLast().getStartTime().toString());
        assertEquals("2024-07-02T14:30", testTimetable.getLast().getEndTime().toString());
        assertEquals(3, testTimetable.getLast().getGroup().getId());
        assertEquals(1, testTimetable.getLast().getTeacher().getId());
    }

    //при ошибке парсинга Lesson выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        assertThrows(Exception.class, () ->
                testTimetableService.addLesson(timetableServlet, "{")
        );
    }

    //если данные объекта Lesson не соответствуют значениям переменных, установленных в properties -> возвращается resp с кодом статуса 400
    @Test
    @SneakyThrows
    void shouldInformAboutAddErrorInCaseOfMismatchPropertyTest() {
        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);
        Mockito.when(testTimetableService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.when(testTimetableService.getDb().getProps().getProperty(MAX_NUMBER_OF_LESSONS)).thenReturn("1");

        assertThrows(Exception.class, () ->
                testTimetableService.addLesson(timetableServlet, TEST_LESSON_JSON)
        );
    }

    //если в URL передан параметр с номером группы
    @Test
    @SneakyThrows
    void shouldGetTimetableByGroupNumberCorrectlyTest() {
        String[] parameters = {"2222", null, null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        List<Lesson> testResult = testTimetableService.getTimetable(parameters);

        assertFalse(testResult.contains(testTimetable.get(0)));
        assertTrue(testResult.contains(testTimetable.get(1)));
        assertTrue(testResult.contains(testTimetable.get(2)));
        assertFalse(testResult.contains(testTimetable.get(3)));
        assertFalse(testResult.contains(testTimetable.get(4)));
        assertTrue(testResult.contains(testTimetable.get(5)));
        assertTrue(testResult.contains(testTimetable.get(6)));
        assertFalse(testResult.contains(testTimetable.get(7)));
    }

    //если в URL передан параметр с фамилией студента
    @Test
    @SneakyThrows
    void shouldGetTimetableByStudentLastNameCorrectlyTest() {
        String[] parameters = {null, "Ситникова", null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        List<Lesson> testResult = testTimetableService.getTimetable(parameters);

        assertTrue(testResult.contains(testTimetable.get(0)));
        assertFalse(testResult.contains(testTimetable.get(1)));
        assertFalse(testResult.contains(testTimetable.get(2)));
        assertTrue(testResult.contains(testTimetable.get(3)));
        assertTrue(testResult.contains(testTimetable.get(4)));
        assertFalse(testResult.contains(testTimetable.get(5)));
        assertFalse(testResult.contains(testTimetable.get(6)));
        assertTrue(testResult.contains(testTimetable.get(7)));
    }

    //если в URL передан параметр с ФИО преподавателя
    @Test
    @SneakyThrows
    void shouldGetTimetableByTeacherNameCorrectlyTest() {
        String[] parameters = {null, null, "Егорова Анастасия Платоновна", null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        List<Lesson> testResult = testTimetableService.getTimetable(parameters);

        assertFalse(testResult.contains(testTimetable.get(0)));
        assertFalse(testResult.contains(testTimetable.get(1)));
        assertTrue(testResult.contains(testTimetable.get(2)));
        assertFalse(testResult.contains(testTimetable.get(3)));
        assertFalse(testResult.contains(testTimetable.get(4)));
        assertTrue(testResult.contains(testTimetable.get(5)));
        assertFalse(testResult.contains(testTimetable.get(6)));
        assertFalse(testResult.contains(testTimetable.get(7)));
    }

    //если в URL передан параметр с датой занятий
    @Test
    @SneakyThrows
    void shouldGetTimetableByDateCorrectlyTest() {
        String[] parameters = {null, null, null, "2024-07-02"};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        List<Lesson> testResult = testTimetableService.getTimetable(parameters);

        assertFalse(testResult.contains(testTimetable.get(0)));
        assertFalse(testResult.contains(testTimetable.get(1)));
        assertFalse(testResult.contains(testTimetable.get(2)));
        assertFalse(testResult.contains(testTimetable.get(3)));
        assertTrue(testResult.contains(testTimetable.get(4)));
        assertTrue(testResult.contains(testTimetable.get(5)));
        assertTrue(testResult.contains(testTimetable.get(6)));
        assertTrue(testResult.contains(testTimetable.get(7)));
    }

    //если в URL только /timetable
    @Test
    @SneakyThrows
    void shouldGetFullTimetableCorrectlyTest() {
        String[] parameters = {null, null, null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        List<Lesson> testResult = testTimetableService.getTimetable(parameters);

        assertTrue(testResult.contains(testTimetable.get(0)));
        assertTrue(testResult.contains(testTimetable.get(1)));
        assertTrue(testResult.contains(testTimetable.get(2)));
        assertTrue(testResult.contains(testTimetable.get(3)));
        assertTrue(testResult.contains(testTimetable.get(4)));
        assertTrue(testResult.contains(testTimetable.get(5)));
        assertTrue(testResult.contains(testTimetable.get(6)));
        assertTrue(testResult.contains(testTimetable.get(7)));
    }

    //если занятие под заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditLessonCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable/5?groupId=1&teacherId=2&startTime=2024-07-02 14:45&endTime=2024-07-02 16:15";
        String[] parameters = {"1", "2", "2024-07-02 14:45", "2024-07-02 16:15"};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);
        Mockito.when(testTimetableService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.when(testTimetableService.getDb().getTeachers()).thenReturn(testTeachersList);

        testTimetableService.editLesson(timetableServlet, testRequestUrl, parameters);

        assertEquals(5, testTimetableService.getDb().getTimetable().get(4).getId());
        assertEquals(1, testTimetableService.getDb().getTimetable().get(4).getGroup().getId());
        assertEquals(2, testTimetableService.getDb().getTimetable().get(4).getTeacher().getId());
        assertEquals("2024-07-02T14:45", testTimetableService.getDb().getTimetable().get(4).getStartTime().toString());
        assertEquals("2024-07-02T16:15", testTimetableService.getDb().getTimetable().get(4).getEndTime().toString());
    }

    //при ошибке парсинга ID занятия код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorInCaseOfParsingIdErrorTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable/id?groupId=1&teacherId=2&startTime=2024-07-02 14:45&endTime=2024-07-02 16:15";
        String[] parameters = {null, null, null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        assertThrows(Exception.class, () ->
                testTimetableService.editLesson(timetableServlet, testRequestUrl, parameters)
        );
    }

    //при ошибке парсинга GroupID занятия код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorInCaseOfParsingGroupIdErrorTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable/5?groupId=id&teacherId=2&startTime=2024-07-02 14:45&endTime=2024-07-02 16:15";
        String[] parameters = {"id", null, null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        assertThrows(Exception.class, () ->
                testTimetableService.editLesson(timetableServlet, testRequestUrl, parameters)
        );
    }

    //при ошибке парсинга TeacherID занятия код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorInCaseOfParsingTeacherIdErrorTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable/5?groupId=1&teacherId=id&startTime=2024-07-02 14:45&endTime=2024-07-02 16:15";
        String[] parameters = {null, "id", null, null};

        Mockito.when(testTimetableService.getDb().getTimetable()).thenReturn(testTimetable);

        assertThrows(Exception.class, () ->
                testTimetableService.editLesson(timetableServlet, testRequestUrl, parameters)
        );
    }
}