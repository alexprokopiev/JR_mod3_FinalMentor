package org.example.jr_mod3_finalmentor.services;

import lombok.SneakyThrows;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jr_mod3_finalmentor.models.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InitServiceTest {

    @Mock
    HttpServletResponse testResponse;
    @Mock
    LocalDB testDb;

    @Test
    @SneakyThrows
    void shouldInitDataStorageCorrectlyTest() {
        InitService testInitService = new InitService();
        Properties testProps = new Properties();
        List<Student> testStudentsList = new ArrayList<>();
        List<Teacher> testTeachersList = new ArrayList<>();
        List<Group> testGroupsList = new ArrayList<>();
        List<Lesson> testTimetable = new ArrayList<>();

        Mockito.when(testDb.getProps()).thenReturn(testProps);
        Mockito.when(testDb.getStudents()).thenReturn(testStudentsList);
        Mockito.when(testDb.getTeachers()).thenReturn(testTeachersList);
        Mockito.when(testDb.getGroups()).thenReturn(testGroupsList);
        Mockito.when(testDb.getTimetable()).thenReturn(testTimetable);

        testInitService.initLocalDB(testDb, testResponse);

        assertEquals(4, testProps.size());
        assertEquals(100, testStudentsList.size());
        assertEquals(10, testTeachersList.size());
        assertEquals(4, testGroupsList.size());
        assertEquals(48, testTimetable.size());
    }
}