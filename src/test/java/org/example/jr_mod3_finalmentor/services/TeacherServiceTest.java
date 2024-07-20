package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import jakarta.servlet.http.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.example.jr_mod3_finalmentor.models.*;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.models.Subject.*;
import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest testRequest;
    @Mock
    HttpServletResponse testResponse;
    @Mock
    LocalDB testDb;

    TeacherService testTeacherService;
    List<Teacher> testTeachersList;

    @BeforeEach
    void setup() {
        testTeacherService = new TeacherService();
        testTeachersList = new ArrayList<>();
        testTeachersList.add(new Teacher(1, "Леонова Виктория Артемьевна", 50L, new ArrayList<>(List.of(ALGEBRA, GEOMETRY)), 25));
        testTeachersList.add(new Teacher(2, "Дорохов Константин Денисович", 48L, new ArrayList<>(List.of(DRAWING, MUSIC)), 23));
        testTeachersList.add(new Teacher(3, "Егорова Анастасия Платоновна", 39L, new ArrayList<>(List.of(PHYSICAL_EDUCATION)), 10));
    }

    //объект Teacher парсится из верного http запроса и все поля объекта записаны корректно
    @Test
    @SneakyThrows
    void shouldAddTeacherCorrectlyTest() {
        Mockito.when(testRequest.getReader()).thenReturn(new BufferedReader(new StringReader(TEST_TEACHER_JSON)));
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);

        testTeacherService.addTeacher(testRequest, testResponse, testDb);

        assertEquals(4, testDb.getTeachers().getLast().getId());
        assertEquals("Абрамов Виктор Степанович", testDb.getTeachers().getLast().getFullName());
        assertEquals(30L, testDb.getTeachers().getLast().getAge());
        assertEquals(List.of(COMPUTER_SCIENCE, PHYSICAL_EDUCATION), testDb.getTeachers().getLast().getSubjects());
        assertEquals(9, testDb.getTeachers().getLast().getExperience());
        verify(testResponse).setStatus(201);
    }

    //при ошибке парсинга Teacher выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        Mockito.when(testRequest.getReader()).thenReturn(new BufferedReader(new StringReader("{")));

        assertThrows(Exception.class, () -> testTeacherService.addTeacher(testRequest, testResponse, testDb));
        verify(testResponse).setStatus(400);
    }

    //возвращается список всех преподавателей
    @Test
    @SneakyThrows
    void shouldGetAllTeachersCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Mockito.lenient().when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getHeader("User-Agent")).thenReturn("Postman");
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);
        Mockito.when(testResponse.getWriter()).thenReturn(printWriter);

        testTeacherService.getTeachers(testRequest, testResponse, testDb);

        assertTrue(stringWriter.toString().contains(testTeachersList.get(0).toString()));
        assertTrue(stringWriter.toString().contains(testTeachersList.get(1).toString()));
        assertTrue(stringWriter.toString().contains(testTeachersList.get(2).toString()));
    }

    //если преподаватель под заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditTeacherCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/1?subject=Physics";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(SUBJECT)).thenReturn("Physics");
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);

        testTeacherService.editTeacher(testRequest, testResponse, testDb);

        assertEquals(List.of(ALGEBRA, GEOMETRY, PHYSICS), testDb.getTeachers().getFirst().getSubjects());
    }

    //если преподавателя под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/5?subject=Physics";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);

        testTeacherService.editTeacher(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
    }

    //если переданного в параметре URL Subject не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseOfSubjectErrorTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/1?subject=Rainbow";

        Mockito.when(testRequest.getRequestURL().toString()).thenReturn(testRequestUrl);
        Mockito.when(testRequest.getParameter(SUBJECT)).thenReturn("Rainbow");
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);

        testTeacherService.editTeacher(testRequest, testResponse, testDb);

        verify(testResponse).setStatus(400);
    }
}