Коллекция http запросов:

{
"info": {
"_postman_id": "d8a975fb-e022-469d-8166-2764803dc33f",
"name": "EducationSystem",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
"_exporter_id": "35217458",
"_collection_link": "https://www.postman.com/alexprokopiev/workspace/my-workspace/collection/35217458-d8a975fb-e022-469d-8166-2764803dc33f?action=share&source=collection_link&creator=35217458"
},
"item": [
{
"name": "Students",
"item": [
{
"name": "Get students list",
"protocolProfileBehavior": {
"disabledSystemHeaders": {}
},
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students"
]
}
},
"response": []
},
{
"name": "Get student by lastName",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students?lastName=Иванов",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students"
],
"query": [
{
"key": "lastName",
"value": "Иванов"
}
]
}
},
"response": []
},
{
"name": "Get student by ID",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/42",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students",
"42"
]
}
},
"response": []
},
{
"name": "Create new student",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"name\": \"Александр\",\r\n    \"patronymic\": \"Николаевич\",\r\n    \"lastName\": \"Прокопьев\",\r\n    \"birthDate\": \"1986-08-09\",\r\n    \"phoneNumber\": \"+79091409294\"\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students"
]
}
},
"response": []
},
{
"name": "Edit student",
"request": {
"method": "PUT",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/42?name=Александр&patronymic=Сергеевич&lastName=Пушкин&birthDate=1799-06-06&phoneNumber=%2b79994224242",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students",
"42"
],
"query": [
{
"key": "name",
"value": "Александр"
},
{
"key": "patronymic",
"value": "Сергеевич"
},
{
"key": "lastName",
"value": "Пушкин"
},
{
"key": "birthDate",
"value": "1799-06-06"
},
{
"key": "phoneNumber",
"value": "%2b79994224242"
}
]
}
},
"response": []
},
{
"name": "Delete student",
"request": {
"method": "DELETE",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/students/42",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"students",
"42"
]
}
},
"response": []
}
]
},
{
"name": "Teachers",
"item": [
{
"name": "Get teachers list",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"teachers"
]
}
},
"response": []
},
{
"name": "Create new teacher",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"fullName\": \"Абрамов Виктор Степанович\",\r\n    \"age\": 30,\r\n    \"subjects\": [\r\n        \"COMPUTER_SCIENCE\",\r\n        \"PHYSICAL_EDUCATION\"\r\n    ],\r\n    \"experience\": 9\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"teachers"
]
}
},
"response": []
},
{
"name": "Add subject to teacher",
"request": {
"method": "PUT",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/teachers/1?subject=Physics",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"teachers",
"1"
],
"query": [
{
"key": "subject",
"value": "Physics"
}
]
}
},
"response": []
}
]
},
{
"name": "Groups",
"item": [
{
"name": "Get groups list",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"groups"
]
}
},
"response": []
},
{
"name": "Get group by number",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups?groupNumber=992",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"groups"
],
"query": [
{
"key": "groupNumber",
"value": "992"
}
]
}
},
"response": []
},
{
"name": "Get groups by student's last name",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups?lastName=Никитин",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"groups"
],
"query": [
{
"key": "lastName",
"value": "Никитин"
}
]
}
},
"response": []
},
{
"name": "Create new group",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"groupNumber\": \"4242\",\r\n    \"students\": [\r\n        {\r\n            \"id\": 1,\r\n            \"name\": \"Захар\",\r\n            \"patronymic\": \"Степанович\",\r\n            \"lastName\": \"Шаповалов\",\r\n            \"birthDate\": \"2006-04-04\",\r\n            \"phoneNumber\": \"+79911556928\"\r\n        },\r\n        {\r\n            \"id\": 2,\r\n            \"name\": \"Платон\",\r\n            \"patronymic\": \"Михайлович\",\r\n            \"lastName\": \"Попов\",\r\n            \"birthDate\": \"2010-06-01\",\r\n            \"phoneNumber\": \"+79884197690\"\r\n        },\r\n        {\r\n            \"id\": 1,\r\n            \"name\": \"Захар\",\r\n            \"patronymic\": \"Степанович\",\r\n            \"lastName\": \"Шаповалов\",\r\n            \"birthDate\": \"2006-04-04\",\r\n            \"phoneNumber\": \"+79911556928\"\r\n        },\r\n        {\r\n            \"id\": 2,\r\n            \"name\": \"Платон\",\r\n            \"patronymic\": \"Михайлович\",\r\n            \"lastName\": \"Попов\",\r\n            \"birthDate\": \"2010-06-01\",\r\n            \"phoneNumber\": \"+79884197690\"\r\n        },\r\n        {\r\n            \"id\": 1,\r\n            \"name\": \"Захар\",\r\n            \"patronymic\": \"Степанович\",\r\n            \"lastName\": \"Шаповалов\",\r\n            \"birthDate\": \"2006-04-04\",\r\n            \"phoneNumber\": \"+79911556928\"\r\n        },\r\n        {\r\n            \"id\": 2,\r\n            \"name\": \"Платон\",\r\n            \"patronymic\": \"Михайлович\",\r\n            \"lastName\": \"Попов\",\r\n            \"birthDate\": \"2010-06-01\",\r\n            \"phoneNumber\": \"+79884197690\"\r\n        }\r\n    ]\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"groups"
]
}
},
"response": []
},
{
"name": "Add student to group",
"request": {
"method": "PUT",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/groups/3?studentId=21&studentId=44",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"groups",
"3"
],
"query": [
{
"key": "studentId",
"value": "21"
},
{
"key": "studentId",
"value": "44"
}
]
}
},
"response": []
}
]
},
{
"name": "Timetable",
"item": [
{
"name": "Get timetable",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
]
}
},
"response": []
},
{
"name": "Get timetable by group number",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable?groupNumber=992",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
],
"query": [
{
"key": "groupNumber",
"value": "992"
}
]
}
},
"response": []
},
{
"name": "Get timetable by student last name",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable?lastName=Никитин",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
],
"query": [
{
"key": "lastName",
"value": "Никитин"
}
]
}
},
"response": []
},
{
"name": "Get timetable by teacher name",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable?fullName=Романова Валерия Всеволодовна",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
],
"query": [
{
"key": "fullName",
"value": "Романова Валерия Всеволодовна"
}
]
}
},
"response": []
},
{
"name": "Get timetable by date",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable?date=2024-07-02",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
],
"query": [
{
"key": "date",
"value": "2024-07-02"
}
]
}
},
"response": []
},
{
"name": "Add lesson to timetable",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"groupId\": 3,\r\n    \"teacherId\": 7,\r\n    \"startTime\": \"2024-07-03 13:00\",\r\n    \"endTime\": \"2024-07-03 14:30\"\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable"
]
}
},
"response": []
},
{
"name": "Edit timetable",
"request": {
"method": "PUT",
"header": [],
"url": {
"raw": "http://localhost:8080/Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war/timetable/10?groupId=1&teacherId=4&startTime=2024-07-02 14:45&endTime=2024-07-02 16:15",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"Gradle___org_example___JR_mod3_FinalMentor_1_0_SNAPSHOT_war",
"timetable",
"10"
],
"query": [
{
"key": "groupId",
"value": "1"
},
{
"key": "teacherId",
"value": "4"
},
{
"key": "startTime",
"value": "2024-07-02 14:45"
},
{
"key": "endTime",
"value": "2024-07-02 16:15"
}
]
}
},
"response": []
}
]
}
]
}