package org.example.jr_mod3_finalmentor.services;

import lombok.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.servlets.GroupServlet;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.jr_mod3_finalmentor.consts.Constants.*;
import static org.example.jr_mod3_finalmentor.consts.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    LocalDB testDb;
    JsonMapper mapper;
    @Mock
    GroupServlet groupServlet;
    @Mock
    GroupService testGroupService;
    List <Student> testStudentsList;
    List<Group> testGroupsList;

    @BeforeEach
    void setup() {
        mapper = new JsonMapper();
        testGroupService = new GroupService(testDb, mapper);
        testStudentsList = new ArrayList<>();
        testGroupsList = new ArrayList<>();

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

        testGroupsList.add(new Group(1, "111", new ArrayList<>()));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(17));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(0));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(1));
        testGroupsList.getFirst().getStudents().add(testStudentsList.get(2));
        testGroupsList.get(0).getStudents().add(testStudentsList.get(3));
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
    }

    //объект Group парсится из верного http запроса, выполняются условия из properties и все поля объекта записаны корректно
    @Test
    @SneakyThrows
    void shouldAddGroupCorrectlyTest() {
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.when(testGroupService.getDb().getProps().getProperty(MIN_GROUP_SIZE)).thenReturn("5");
        Mockito.when(testGroupService.getDb().getProps().getProperty(MAX_GROUP_SIZE)).thenReturn("30");

        testGroupService.addGroup(groupServlet, TEST_GROUP_JSON);

        assertEquals(4, testGroupService.getDb().getGroups().getLast().getId());
        assertEquals("4242", testGroupService.getDb().getGroups().getLast().getGroupNumber());
        assertEquals(6, testGroupService.getDb().getGroups().getLast().getStudents().size());
    }

    //при ошибке парсинга Group выкидывается ошибка и код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldThrowExceptionInCaseOfParsingErrorTest() {
        assertThrows(Exception.class, () ->
                testGroupService.addGroup(groupServlet, "{")
        );
    }

    //если данные объекта Group не соответствуют значениям переменных, установленных в properties -> возвращается resp с кодом статуса 400
    @Test
    @SneakyThrows
    void shouldInformAboutAddErrorInCaseOfMismatchPropertyTest() {
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);
        Mockito.lenient().when(testGroupService.getDb().getProps().getProperty(MIN_GROUP_SIZE)).thenReturn("7");
        Mockito.lenient().when(testGroupService.getDb().getProps().getProperty(MAX_GROUP_SIZE)).thenReturn("30");

        assertThrows(Exception.class, () ->
                testGroupService.addGroup(groupServlet, TEST_GROUP_JSON)
        );
    }

    //в URL передан параметр с фамилией студента -> возвращается список групп с данной фамилией
    @Test
    @SneakyThrows
    void shouldGetGroupsByStudentLastNameCorrectlyTest() {
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        List<Group> testResult = testGroupService.getGroups("Ситникова", null);

        assertTrue(testResult.contains(testGroupsList.get(0)));
        assertFalse(testResult.contains(testGroupsList.get(1)));
        assertTrue(testResult.contains(testGroupsList.get(2)));
    }

    //в URL передан параметр с номером группы -> возвращается группа с данным номером
    @Test
    @SneakyThrows
    void shouldGetGroupsByGroupNumberCorrectlyTest() {
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        List<Group> testResult = testGroupService.getGroups(null, "33333");

        assertFalse(testResult.contains(testGroupsList.get(0)));
        assertFalse(testResult.contains(testGroupsList.get(1)));
        assertTrue(testResult.contains(testGroupsList.get(2)));
    }

    //в URL передан только /groups -> возвращается список всех групп
    @Test
    @SneakyThrows
    void shouldGetAllGroupsCorrectlyTest() {
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        List<Group> testResult = testGroupService.getGroups(null, null);

        assertTrue(testResult.contains(testGroupsList.get(0)));
        assertTrue(testResult.contains(testGroupsList.get(1)));
        assertTrue(testResult.contains(testGroupsList.get(2)));
    }

    //группа под заданным ID существует и студент с заданным ID существует
    @Test
    @SneakyThrows
    void shouldEditGroupCorrectlyTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups/3?studentId=21&studentId=42";
        String[] testStudentsId = {"21", "42"};
        Student testStudent21 = new Student(21, "Александр", "Артёмович", "Ефимов", LocalDate.parse("29.11.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79399329849");
        Student testStudent42 = new Student(42, "Мария", "Матвеевна", "Самойлова", LocalDate.parse("10.11.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79626504600");
        testStudentsList.add(testStudent21);
        testStudentsList.add(testStudent42);

        Mockito.when(testGroupService.getDb().getStudents()).thenReturn(testStudentsList);
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        testGroupService.editGroup(groupServlet, testRequestUrl, testStudentsId);

        assertTrue(testGroupsList.get(2).getStudents().contains(testStudent21));
        assertTrue(testGroupsList.get(2).getStudents().contains(testStudent42));
        assertEquals(9, testGroupsList.get(2).getStudents().size());
    }

    //при ошибке парсинга StudentsId код статуса resp равен 400
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseStudentIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups/3?studentId=21&studentId=id";
        String[] testStudentsId = {"21", "id"};
        Student testStudent21 = new Student(21, "Александр", "Артёмович", "Ефимов", LocalDate.parse("29.11.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79399329849");
        testStudentsList.add(testStudent21);

        Mockito.when(testGroupService.getDb().getStudents()).thenReturn(testStudentsList);
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        assertThrows(Exception.class, () ->
                testGroupService.editGroup(groupServlet, testRequestUrl, testStudentsId)
        );
    }

    //группа под заданным ID существует, но студента с заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseStudentNotFoundTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups/3?studentId=21&studentId=42";
        String[] testStudentsId = {"21", "42"};
        Student testStudent21 = new Student(21, "Александр", "Артёмович", "Ефимов", LocalDate.parse("29.11.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79399329849");
        testStudentsList.add(testStudent21);

        Mockito.when(testGroupService.getDb().getStudents()).thenReturn(testStudentsList);
        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        assertThrows(Exception.class, () ->
                testGroupService.editGroup(groupServlet, testRequestUrl, testStudentsId)
        );
    }

    //если группа под заданным ID не существует
    @Test
    @SneakyThrows
    void shouldInformAboutEditErrorCaseGroupIdIncorrectTest() {
        String testRequestUrl = "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups/5?studentId=21&studentId=42";
        String[] testStudentsId = {"21", "42"};

        Mockito.when(testGroupService.getDb().getGroups()).thenReturn(testGroupsList);

        assertThrows(Exception.class, () ->
                testGroupService.editGroup(groupServlet, testRequestUrl, testStudentsId)
        );
    }
}