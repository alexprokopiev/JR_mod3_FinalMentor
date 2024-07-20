<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create new lesson</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="lessonCreatorHeader" class="my-5 text-uppercase font-monospace">Расписание занятий</h1>
        <form onsubmit="createLesson();return false" name="createLessonForm">
            <div class="row justify-content-center my-3">
                <label for="lessonStartTimeForCreate" class="col-2 col-form-label text-start">Введите время начала</label>
                <div class="col-3">
                    <input id="lessonStartTimeForCreate" class="form-control" name="startTime" type="datetime-local" value="${startTime}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="lessonEndTimeForCreate" class="col-2 col-form-label text-start">Введите время окончания</label>
                <div class="col-3">
                    <input id="lessonEndTimeForCreate" class="form-control" name="endTime" type="datetime-local" value="${endTime}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="groupForCreate" class="col-2 col-form-label text-start">Выберите номер группы</label>
                <div class="col-3">
                    <select id="groupForCreate" class="form-select" name="group" size="1">
                        <c:forEach var="group" items="${db.groups}">
                            <option value=${group.id}>${group.groupNumber}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="teacherForCreate" class="col-2 col-form-label text-start">Выберите преподавателя</label>
                <div class="col-3">
                    <select id="teacherForCreate" class="form-select" name="teacher" size="1">
                        <c:forEach var="teacher" items="${db.teachers}">
                            <option value=${teacher.id}>${teacher.fullName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Создать</button>
        </form>
        <form action='<c:url value="/timetable/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к расписанию</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function createLesson() {
            let createStartTime = document.getElementById("lessonStartTimeForCreate").value.replace(/T/g, ' ');
            let createEndTime = document.getElementById("lessonEndTimeForCreate").value.replace(/T/g, ' ');
            let groupSelector = document.getElementById("groupForCreate");
            let createGroup = parseInt(groupSelector.options[groupSelector.selectedIndex].value);
            let teacherSelector = document.getElementById("teacherForCreate");
            let createTeacher = parseInt(teacherSelector.options[teacherSelector.selectedIndex].value);
            let createData = {
                startTime: createStartTime,
                endTime: createEndTime,
                groupId: createGroup,
                teacherId: createTeacher
            }
            let body = JSON.stringify(createData);
            console.log(body);

            let createRequest = new XMLHttpRequest();
            createRequest.open("POST", '<c:url value="/timetable/"/>');
            createRequest.setRequestHeader("Content-type", "application/json");
            createRequest.send(body);
            createRequest.onload = function () {
                if (createRequest.status === 201) {
                    showMessage("Группа успешно создана");
                    clearFields();
                } else {
                    showMessage("При создании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("lessonCreatorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }

        function clearFields(){
            document.getElementById("lessonStartTimeForCreate").value = "";
            document.getElementById("lessonEndTimeForCreate").value = "";
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
