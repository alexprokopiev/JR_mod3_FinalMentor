package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.example.jr_mod3_finalmentor.models.*;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest testRequest;
    @Mock
    HttpServletResponse testResponse;
    @Mock
    LocalDB testDb;

    StudentService testStudentService;
    List<Student> testStudentsList;

    @BeforeEach
    void setup() {
        testStudentService = new StudentService();
        testStudentsList = new ArrayList<>();
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
        Mockito.when(testRequest.getReader()).thenReturn(new BufferedReader(new StringReader(TEST_STUDENT_JSON)));
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.addStudent(testRequest, testResponse, testDb);

        assertEquals(6, testDb.getStudents().getLast().getId());
        assertEquals("Александр", testDb.getStudents().getLast().getName());
        assertEquals("Николаевич", testDb.getStudents().getLast().getPatronymic());
        assertEquals("Прокопьев", testDb.getStudents().getLast().getLastName());
        assertEquals("1986-08-09", testDb.getStudents().getLast().getBirthDate().toString());
        assertEquals("+79091409294", testDb.getStudents().getLast().getPhoneNumber());
        verify(testResponse).setStatus(201);
    }

    //в json переданы некорректные данные
    @Test
    @SneakyThrows
    void shouldInformAboutAddErrorInCaseOfIncorrectJsonDataTest() {
        Mockito.when(testRequest.getReader()).thenReturn(new BufferedReader(new StringReader(TEST_INCORRECT_STUDENT_JSON)));
        Mockito.lenient().when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.addStudent(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
    }

    //при ошибке парсинга Student выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        Mockito.when(testRequest.getReader()).thenReturn(new BufferedReader(new StringReader("{")));

        assertThrows(Exception.class, () -> testStudentService.addStudent(testRequest, testResponse, testDb));
        verify(testResponse).setStatus(400);
    }

    //в URL передан ID студента -> возвращается студент с данным ID
    @Test
    @SneakyThrows
    void shouldGetStudentByIdCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/2";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getHeader("User-Agent")).thenReturn("Postman");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);
        Mockito.when(testResponse.getWriter()).thenReturn(printWriter);

        testStudentService.getStudents(testRequest, testResponse, testDb);

        assertTrue(stringWriter.toString().contains(testStudentsList.get(1).toString()));
    }

    //в URL передан параметр с фамилией студента -> возвращается список студентов с данной фамилией
    @Test
    @SneakyThrows
    void shouldGetStudentsByLastNameCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students?lastName=Ситникова";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(LAST_NAME)).thenReturn("Ситникова");
        Mockito.when(testRequest.getHeader("User-Agent")).thenReturn("Postman");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);
        Mockito.when(testResponse.getWriter()).thenReturn(printWriter);

        testStudentService.getStudents(testRequest, testResponse, testDb);

        assertFalse(stringWriter.toString().contains(testStudentsList.get(0).toString()));
        assertFalse(stringWriter.toString().contains(testStudentsList.get(1).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(2).toString()));
        assertFalse(stringWriter.toString().contains(testStudentsList.get(3).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(4).toString()));
    }

    //в URL передан только /students -> возвращается список всех студентов
    @Test
    @SneakyThrows
    void shouldGetAllStudentsCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getHeader("User-Agent")).thenReturn("Postman");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);
        Mockito.when(testResponse.getWriter()).thenReturn(printWriter);

        testStudentService.getStudents(testRequest, testResponse, testDb);

        assertTrue(stringWriter.toString().contains(testStudentsList.get(0).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(1).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(2).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(3).toString()));
        assertTrue(stringWriter.toString().contains(testStudentsList.get(4).toString()));
    }

    //в URL передан несуществующий ID студента -> информирование об ошибке
    @Test
    @SneakyThrows
    void shouldInformAboutGetErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.getStudents(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
    }

    //если студент под заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditStudentCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?name=Александр&patronymic=Сергеевич&lastName=Пушкин&birthDate=1799-06-06&phoneNumber=%2b79994224242";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(NAME)).thenReturn("Александр");
        Mockito.when(testRequest.getParameter(PATRONYMIC)).thenReturn("Сергеевич");
        Mockito.when(testRequest.getParameter(LAST_NAME)).thenReturn("Пушкин");
        Mockito.when(testRequest.getParameter(BIRTH_DATE)).thenReturn("1799-06-06");
        Mockito.when(testRequest.getParameter(PHONE_NUMBER)).thenReturn("+79994224242");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("Александр", testDb.getStudents().get(3).getName());
        assertEquals("Сергеевич", testDb.getStudents().get(3).getPatronymic());
        assertEquals("Пушкин", testDb.getStudents().get(3).getLastName());
        assertEquals("1799-06-06", testDb.getStudents().get(3).getBirthDate().toString());
        assertEquals("+79994224242", testDb.getStudents().get(3).getPhoneNumber());
    }

    //если данные для полей Student не переданы
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentDataIsNullTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(NAME)).thenReturn(null);
        Mockito.when(testRequest.getParameter(PATRONYMIC)).thenReturn(null);
        Mockito.when(testRequest.getParameter(LAST_NAME)).thenReturn(null);
        Mockito.when(testRequest.getParameter(BIRTH_DATE)).thenReturn(null);
        Mockito.when(testRequest.getParameter(PHONE_NUMBER)).thenReturn(null);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("Полина", testDb.getStudents().get(3).getName());
        assertEquals("Михайловна", testDb.getStudents().get(3).getPatronymic());
        assertEquals("Боброва", testDb.getStudents().get(3).getLastName());
        assertEquals("2008-11-21", testDb.getStudents().get(3).getBirthDate().toString());
        assertEquals("+79627318718", testDb.getStudents().get(3).getPhoneNumber());
    }

    //если передано некорректное имя
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentNameIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?name=123";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(NAME)).thenReturn("123");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("Полина", testDb.getStudents().get(3).getName());
    }

    //если передано некорректное отчество
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentPatronymicIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?patronymic=123";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.lenient().when(testRequest.getParameter(PATRONYMIC)).thenReturn("123");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("Михайловна", testDb.getStudents().get(3).getPatronymic());
    }

    //если передана некорректная фамилия
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentLastNameIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?lastName=123";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.lenient().when(testRequest.getParameter(LAST_NAME)).thenReturn("123");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("Боброва", testDb.getStudents().get(3).getLastName());
    }

    //если передан некорректный номер телефона
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentPhoneNumberIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?phoneNumber=123";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.lenient().when(testRequest.getParameter(PHONE_NUMBER)).thenReturn("123");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("+79627318718", testDb.getStudents().get(3).getPhoneNumber());
    }

    //если передана некорректная дата рождения
    @Test
    @SneakyThrows
    void shouldDoNothingCaseStudentBirthDateIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/4?birthDate=123";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.lenient().when(testRequest.getParameter(BIRTH_DATE)).thenReturn("123");
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        assertEquals("2008-11-21", testDb.getStudents().get(3).getBirthDate().toString());
    }

    //если студента под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.editStudent(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
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

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.deleteStudent(testRequest, testResponse, testDb);

        assertTrue(testStudentsList.contains(student1));
        assertFalse(testStudentsList.contains(student2));
        assertTrue(testStudentsList.contains(student3));
        assertTrue(testStudentsList.contains(student4));
        assertTrue(testStudentsList.contains(student5));
        verify(testResponse).setStatus(204);
    }

    //если студента под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutDeleteErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/10";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);

        testStudentService.deleteStudent(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
    }
}