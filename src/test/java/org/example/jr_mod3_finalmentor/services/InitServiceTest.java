package org.example.jr_mod3_finalmentor.services;

import lombok.SneakyThrows;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.InitServlet;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InitServiceTest {

    @Mock
    InitServlet testInitServlet;
    @Mock
    LocalDB testDb;
    JsonMapper mapper;

    @Test
    @SneakyThrows
    void shouldInitDataStorageCorrectlyTest() {
        mapper = new JsonMapper();
        InitService testInitService = new InitService(testDb, mapper);
        Properties testProps = new Properties();
        List<Student> testStudentsList = new ArrayList<>();
        List<Teacher> testTeachersList = new ArrayList<>();
        List<Group> testGroupsList = new ArrayList<>();
        List<Lesson> testTimetable = new ArrayList<>();

        Mockito.when(testInitService.getDb().getProps()).thenReturn(testProps);
        Mockito.when(testInitService.getDb().getStudents()).thenReturn(testStudentsList);
        Mockito.when(testInitService.getDb().getTeachers()).thenReturn(testTeachersList);
        Mockito.when(testInitService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.when(testInitService.getDb().getTimetable()).thenReturn(testTimetable);

        testInitService.initLocalDB(testInitServlet);

        assertEquals(4, testProps.size());
        assertEquals(100, testStudentsList.size());
        assertEquals(10, testTeachersList.size());
        assertEquals(4, testGroupsList.size());
        assertEquals(48, testTimetable.size());
    }
}