package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.StudentServlet;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.consts.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    JsonMapper mapper;
    List<Student> testStudentsList;
    StudentService testStudentService;
    @Mock
    LocalDB testDb;
    @Mock
    StudentServlet studentServlet;

    @BeforeEach
    void setup() {
        mapper = new JsonMapper();
        testStudentsList = new ArrayList<>();
        testStudentService = new StudentService(testDb, mapper);

        testStudentsList.add(new Student(1, "Захар", "Степанович", "Шаповалов", LocalDate.parse("04.04.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79911556928"));
        testStudentsList.add(new Student(2, "Платон", "Михайлович", "Попов", LocalDate.parse("01.06.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79884197690"));
        testStudentsList.add(new Student(3, "Варвара", "Романовна", "Ситникова", LocalDate.parse("19.09.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79554967582"));
        testStudentsList.add(new Student(4, "Полина", "Михайловна", "Боброва", LocalDate.parse("21.11.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79627318718"));
        testStudentsList.add(new Student(5, "Александра", "Матвеевна", "Ситникова", LocalDate.parse("29.09.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79889609879"));
    }

    //объект Student парсится из верного http запроса и все поля объекта записаны корректно
    @Test
    @SneakyThrows
    void shouldAddStudentCorrectlyTest() {
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.addStudent(studentServlet, TEST_STUDENT_JSON);

        assertEquals(6, testStudentService.getDb().getStudents().getLast().getId());
        assertEquals("Александр", testStudentService.getDb().getStudents().getLast().getName());
        assertEquals("Николаевич", testStudentService.getDb().getStudents().getLast().getPatronymic());
        assertEquals("Прокопьев", testStudentService.getDb().getStudents().getLast().getLastName());
        assertEquals("1986-08-09", testStudentService.getDb().getStudents().getLast().getBirthDate().toString());
        assertEquals("+79091409294", testStudentService.getDb().getStudents().getLast().getPhoneNumber());
    }

    //в json переданы некорректные данные
    @Test
    @SneakyThrows
    void shouldInformAboutAddErrorInCaseOfIncorrectJsonDataTest() {
        Mockito.lenient().when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        assertThrows(InvalidParameterException.class, () -> testStudentService.addStudent(studentServlet, TEST_INCORRECT_STUDENT_JSON));
    }

    //при ошибке парсинга Student выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        assertThrows(InvalidParameterException.class, () -> testStudentService.addStudent(studentServlet, "{"));
    }

    //в URL передан ID студента -> возвращается студент с данным ID
    @Test
    @SneakyThrows
    void shouldGetStudentByIdCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/2";

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        List<Student> testResult = testStudentService.getStudents(studentServlet, testRequestUrl, null);

        assertTrue(testResult.contains(testStudentsList.get(1)));
    }

    //в URL передан параметр с фамилией студента -> возвращается список студентов с данной фамилией
    @Test
    @SneakyThrows
    void shouldGetStudentsByLastNameCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students?lastName=Ситникова";
        String lastName = "Ситникова";

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        List<Student> testResult = testStudentService.getStudents(studentServlet, testRequestUrl, lastName);

        assertFalse(testResult.contains(testStudentsList.get(0)));
        assertFalse(testResult.contains(testStudentsList.get(1)));
        assertTrue(testResult.contains(testStudentsList.get(2)));
        assertFalse(testResult.contains(testStudentsList.get(3)));
        assertTrue(testResult.contains(testStudentsList.get(4)));
    }

    //в URL передан только /students -> возвращается список всех студентов
    @Test
    @SneakyThrows
    void shouldGetAllStudentsCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students";

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        List<Student> testResult = testStudentService.getStudents(studentServlet, testRequestUrl, null);

        assertTrue(testResult.contains(testStudentsList.get(0)));
        assertTrue(testResult.contains(testStudentsList.get(1)));
        assertTrue(testResult.contains(testStudentsList.get(2)));
        assertTrue(testResult.contains(testStudentsList.get(3)));
        assertTrue(testResult.contains(testStudentsList.get(4)));
    }

    //в URL передан несуществующий ID студента -> информирование об ошибке
    @Test
    @SneakyThrows
    void shouldInformAboutGetErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        assertThrows(InvalidParameterException.class, () -> testStudentService.getStudents(studentServlet, testRequestUrl, null));
    }

    //если студент под заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditStudentCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?name=Александр&patronymic=Сергеевич&lastName=Пушкин&birthDate=1799-06-06&phoneNumber=%2b79994224242";
        String[] parameters = {"Александр", "Сергеевич", "Пушкин", "1799-06-06", "+79994224242"};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("Александр", testStudentService.getDb().getStudents().get(3).getName());
        assertEquals("Сергеевич", testStudentService.getDb().getStudents().get(3).getPatronymic());
        assertEquals("Пушкин", testStudentService.getDb().getStudents().get(3).getLastName());
        assertEquals("1799-06-06", testStudentService.getDb().getStudents().get(3).getBirthDate().toString());
        assertEquals("+79994224242", testStudentService.getDb().getStudents().get(3).getPhoneNumber());
    }

    //если данные для полей Student не переданы
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentDataIsNullTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4";
        String[] parameters = {null, null, null, null, null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("Полина", testStudentService.getDb().getStudents().get(3).getName());
        assertEquals("Михайловна", testStudentService.getDb().getStudents().get(3).getPatronymic());
        assertEquals("Боброва", testStudentService.getDb().getStudents().get(3).getLastName());
        assertEquals("2008-11-21", testStudentService.getDb().getStudents().get(3).getBirthDate().toString());
        assertEquals("+79627318718", testStudentService.getDb().getStudents().get(3).getPhoneNumber());
    }

    //если передано некорректное имя
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentNameIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?name=123";
        String[] parameters = {"123", null, null, null, null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("Полина", testStudentService.getDb().getStudents().get(3).getName());
    }

    //если передано некорректное отчество
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentPatronymicIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?patronymic=123";
        String[] parameters = {null, "123", null, null, null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("Михайловна", testStudentService.getDb().getStudents().get(3).getPatronymic());
    }

    //если передана некорректная фамилия
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentLastNameIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?lastName=123";
        String[] parameters = {null, null, "123", null, null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("Боброва", testStudentService.getDb().getStudents().get(3).getLastName());
    }

    //если передан некорректный номер телефона
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentPhoneNumberIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?phoneNumber=123";
        String[] parameters = {null, null, null, null, "123"};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("+79627318718", testStudentService.getDb().getStudents().get(3).getPhoneNumber());
    }

    //если передана некорректная дата рождения
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentBirthDateIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?birthDate=123";
        String[] parameters = {null, null, null, "123", null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(studentServlet, testRequestUrl, parameters);

        assertEquals("2008-11-21", testStudentService.getDb().getStudents().get(3).getBirthDate().toString());
    }

    //если студента под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";
        String[] parameters = {null, null, null, null, null};
        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        assertThrows(InvalidParameterException.class, () -> testStudentService.editStudent(studentServlet, testRequestUrl, parameters));
    }

    //если студент под заданным ID существует
    @Test
    @SneakyThrows
    void shouldDeleteStudentCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/2";
        Student student1 = testStudentsList.get(0);
        Student student2 = testStudentsList.get(1);
        Student student3 = testStudentsList.get(2);
        Student student4 = testStudentsList.get(3);
        Student student5 = testStudentsList.get(4);

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        testStudentService.deleteStudent(studentServlet, testRequestUrl);

        assertTrue(testStudentsList.contains(student1));
        assertFalse(testStudentsList.contains(student2));
        assertTrue(testStudentsList.contains(student3));
        assertTrue(testStudentsList.contains(student4));
        assertTrue(testStudentsList.contains(student5));
    }

    //если студента под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutDeleteErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";

        Mockito.when(testStudentService.getDb().getStudents()).thenReturn(testStudentsList);

        assertThrows(InvalidParameterException.class, () -> testStudentService.deleteStudent(studentServlet, testRequestUrl));
    }
}