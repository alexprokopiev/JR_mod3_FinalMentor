package org.example.jr_mod3_finalmentor.consts;

public class Constants {
    //путь к файлу properties
    public static final String PROPS_PATH = "/config.properties";

    //"база данных"
    public static final String LOCAL_DB = "db";

    //регулярные выражения и паттерны
    public static final String NAME_REGEX = "^[А-ЯЁа-яё\\ \\`\\-]+$";
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String PHONE_NUMBER_REGEX = "^(8|\\+7|\\+375|80)[ \\(]?\\d{2,3}[ \\)]?[\\d\\- ]{7,9}$";

    //пути к ресурсам
    public static final String SLASH = "/";
    public static final String ASTERISK = "*";
    public static final String INIT = "init";
    public static final String STUDENTS = "students";
    public static final String TEACHERS = "teachers";
    public static final String GROUPS = "groups";
    public static final String TIMETABLE = "timetable";

    //параметры URL
    public static final String NAME = "name";
    public static final String PATRONYMIC = "patronymic";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTH_DATE = "birthDate";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SUBJECT = "subject";
    public static final String GROUP_NUMBER = "groupNumber";
    public static final String STUDENT = "student";
    public static final String STUDENT_ID = "studentId";
    public static final String FULL_NAME = "fullName";
    public static final String DATE = "date";
    public static final String GROUP_ID = "groupId";
    public static final String TEACHER_ID = "teacherId";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    //свойства в файле properties
    public static final String MIN_GROUP_SIZE = "minGroupSize";
    public static final String MAX_GROUP_SIZE = "maxGroupSize";
    public static final String MAX_NUMBER_OF_LESSONS = "maxNumberOfLessons";
    //при существующем функционале нет необходимости и смысла в сравнении с данной переменной. Можно использовать при удалении уроков из расписания
    //public static final String MIN_NUMBER_OF_LESSONS = "minNumberOfLessons";

    //сообщения об ошибках
    public static final String STUDENTS_NUMBER_ERROR = "The number of students in the group does not meet the specified conditions";
    public static final String LESSONS_NUMBER_ERROR = "The number of classes per day does not meet the specified conditions";
    public static final String ID_EXTRACT_ERROR = "There was an error receiving the ID";
    public static final String GROUP_FIND_ERROR = "Group with the specified ID was not found";
    public static final String STUDENT_FIND_ERROR = "Student with the specified ID was not found";
    public static final String TEACHER_FIND_ERROR = "The teacher with the specified ID was not found";
    public static final String LESSON_FIND_ERROR = "Class with the specified ID was not found";
    public static final String SUBJECT_ERROR = "The subject is not on the list of subjects";
    public static final String PROPERTIES_ERROR = "Configuration file could not be found";
    public static final String JSON_PARSING_ERROR = "The data sent to the server contains an error";
    public static final String DATE_PARSING_ERROR = "Date of birth information contains an error";
    public static final String DATE_TIME_PARSING_ERROR = "The class date information contains an error";
    public static final String DATA_FORMAT_ERROR = "The format of the entered data does not comply with the system requirements";

    //сообщения для логов
    public static final String INIT_MSG = "Start of the program. Application data has been successfully loaded";
    public static final String ADD_GROUP_MSG = "A new group has been added [{}]";
    public static final String GET_GROUP_MSG = "A request to retrieve a group/group list has been executed";
    public static final String EDIT_GROUP_MSG = "Into the group [{}] added a student [{} {} {}]";
    public static final String ADD_STUDENT_MSG = "Added a new student [{} {} {}]";
    public static final String GET_STUDENT_MSG = "A request for a student/student list has been executed";
    public static final String EDIT_STUDENT_MSG = "Student [{} {} {}] data updated";
    public static final String DELETE_STUDENT_MSG = "Deleted student [{} {} {}]";
    public static final String ADD_TEACHER_MSG = "Added a new teacher [{}]";
    public static final String GET_TEACHER_MSG = "A request for a list of teachers has been executed";
    public static final String EDIT_TEACHER_MSG = "The teacher's [{}] list of subjects taught has been updated";
    public static final String ADD_LESSON_MSG = "A class for the group [{}] has been added to the schedule, which will be held [{}]";
    public static final String GET_TIMETABLE_MSG = "A request to retrieve the class schedule has been fulfilled";
    public static final String EDIT_LESSON_MSG = "Updated the class for the group [{}], which will be held [{}]";

    //тестовые данные
    public static final String TEST_STUDENT_JSON = "{\n" +
            "    \"name\": \"Александр\",\n" +
            "    \"patronymic\": \"Николаевич\",\n" +
            "    \"lastName\": \"Прокопьев\",\n" +
            "    \"birthDate\": \"1986-08-09\",\n" +
            "    \"phoneNumber\": \"+79091409294\"\n" +
            "}";
    public static final String TEST_INCORRECT_STUDENT_JSON = "{\n" +
            "    \"name\": \"123\",\n" +
            "    \"patronymic\": \"123\",\n" +
            "    \"lastName\": \"123\",\n" +
            "    \"birthDate\": \"1986-08-09\",\n" +
            "    \"phoneNumber\": \"123\"\n" +
            "}";
    public static final String TEST_TEACHER_JSON = "{\n" +
            "    \"fullName\": \"Абрамов Виктор Степанович\",\n" +
            "    \"age\": 30,\n" +
            "    \"subjects\": [\n" +
            "        \"COMPUTER_SCIENCE\",\n" +
            "        \"PHYSICAL_EDUCATION\"\n" +
            "    ],\n" +
            "    \"experience\": 9\n" +
            "}";
    public static final String TEST_GROUP_JSON = "{\n" +
            "    \"groupNumber\": \"4242\",\n" +
            "    \"students\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Захар\",\n" +
            "            \"patronymic\": \"Степанович\",\n" +
            "            \"lastName\": \"Шаповалов\",\n" +
            "            \"birthDate\": \"2006-04-04\",\n" +
            "            \"phoneNumber\": \"+79911556928\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Платон\",\n" +
            "            \"patronymic\": \"Михайлович\",\n" +
            "            \"lastName\": \"Попов\",\n" +
            "            \"birthDate\": \"2010-06-01\",\n" +
            "            \"phoneNumber\": \"+79884197690\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Захар\",\n" +
            "            \"patronymic\": \"Степанович\",\n" +
            "            \"lastName\": \"Шаповалов\",\n" +
            "            \"birthDate\": \"2006-04-04\",\n" +
            "            \"phoneNumber\": \"+79911556928\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Платон\",\n" +
            "            \"patronymic\": \"Михайлович\",\n" +
            "            \"lastName\": \"Попов\",\n" +
            "            \"birthDate\": \"2010-06-01\",\n" +
            "            \"phoneNumber\": \"+79884197690\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Захар\",\n" +
            "            \"patronymic\": \"Степанович\",\n" +
            "            \"lastName\": \"Шаповалов\",\n" +
            "            \"birthDate\": \"2006-04-04\",\n" +
            "            \"phoneNumber\": \"+79911556928\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Платон\",\n" +
            "            \"patronymic\": \"Михайлович\",\n" +
            "            \"lastName\": \"Попов\",\n" +
            "            \"birthDate\": \"2010-06-01\",\n" +
            "            \"phoneNumber\": \"+79884197690\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    public static final String TEST_LESSON_JSON = "{\n" +
            "    \"groupId\": 3,\n" +
            "    \"teacherId\": 1,\n" +
            "    \"startTime\": \"2024-07-02 13:00\",\n" +
            "    \"endTime\": \"2024-07-02 14:30\"\n" +
            "}";

    //jsp страницы
    public static final String INDEX_JSP = "/index.jsp";
    public static final String STUDENTS_JSP = "/students.jsp";
    public static final String TEACHERS_JSP = "/teachers.jsp";
    public static final String GROUPS_JSP = "/groups.jsp";
    public static final String TIMETABLE_JSP = "/timetable.jsp";
}
