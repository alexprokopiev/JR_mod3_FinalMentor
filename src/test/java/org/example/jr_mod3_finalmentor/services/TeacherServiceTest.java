package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.TeacherServlet;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.models.Subject.*;
import static org.example.jr_mod3_finalmentor.consts.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    LocalDB testDb;
    JsonMapper mapper;
    @Mock
    TeacherServlet teacherServlet;
    TeacherService testTeacherService;
    List<Teacher> testTeachersList;

    @BeforeEach
    void setup() {
        mapper = new JsonMapper();
        testTeacherService = new TeacherService(testDb, mapper);
        testTeachersList = new ArrayList<>();

        testTeachersList.add(new Teacher(1, "Леонова Виктория Артемьевна", 50L, new ArrayList<>(List.of(ALGEBRA, GEOMETRY)), 25));
        testTeachersList.add(new Teacher(2, "Дорохов Константин Денисович", 48L, new ArrayList<>(List.of(DRAWING, MUSIC)), 23));
        testTeachersList.add(new Teacher(3, "Егорова Анастасия Платоновна", 39L, new ArrayList<>(List.of(PHYSICAL_EDUCATION)), 10));
    }

    //объект Teacher парсится из верного http запроса и все поля объекта записаны корректно
    @Test
    @SneakyThrows
    void shouldAddTeacherCorrectlyTest() {
        Mockito.when(testTeacherService.getDb().getTeachers()).thenReturn(testTeachersList);

        testTeacherService.addTeacher(teacherServlet, TEST_TEACHER_JSON);

        assertEquals(4, testTeacherService.getDb().getTeachers().getLast().getId());
        assertEquals("Абрамов Виктор Степанович", testTeacherService.getDb().getTeachers().getLast().getFullName());
        assertEquals(30L, testTeacherService.getDb().getTeachers().getLast().getAge());
        assertEquals(List.of(COMPUTER_SCIENCE, PHYSICAL_EDUCATION), testTeacherService.getDb().getTeachers().getLast().getSubjects());
        assertEquals(9, testTeacherService.getDb().getTeachers().getLast().getExperience());
    }

    //при ошибке парсинга Teacher выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        assertThrows(Exception.class, () -> testTeacherService.addTeacher(teacherServlet, "{"));
    }

    //возвращается список всех преподавателей
    @Test
    @SneakyThrows
    void shouldGetAllTeachersCorrectlyTest() {
        Mockito.when(testTeacherService.getDb().getTeachers()).thenReturn(testTeachersList);

        List<Teacher> testResult = testTeacherService.getTeachers();

        assertTrue(testResult.contains(testTeachersList.get(0)));
        assertTrue(testResult.contains(testTeachersList.get(1)));
        assertTrue(testResult.contains(testTeachersList.get(2)));
    }

    //если преподаватель под заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditTeacherCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/1?subject=Physics";

        Mockito.when(testTeacherService.getDb().getTeachers()).thenReturn(testTeachersList);

        testTeacherService.editTeacher(teacherServlet, testRequestUrl, "Physics");

        assertEquals(List.of(ALGEBRA, GEOMETRY, PHYSICS), testTeacherService.getDb().getTeachers().getFirst().getSubjects());
    }

    //если преподавателя под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/5?subject=Physics";

        Mockito.when(testTeacherService.getDb().getTeachers()).thenReturn(testTeachersList);

        assertThrows(Exception.class, () ->
                testTeacherService.editTeacher(teacherServlet, testRequestUrl, "Physics")
        );
    }

    //если переданного в параметре URL Subject не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseOfSubjectErrorTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/1?subject=Rainbow";

        Mockito.when(testTeacherService.getDb().getTeachers()).thenReturn(testTeachersList);

        assertThrows(Exception.class, () ->
                testTeacherService.editTeacher(teacherServlet, testRequestUrl, "Rainbow")
        );
    }
}