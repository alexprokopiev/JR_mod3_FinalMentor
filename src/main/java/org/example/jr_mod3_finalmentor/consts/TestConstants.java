package org.example.jr_mod3_finalmentor.consts;

public class TestConstants {

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
}
