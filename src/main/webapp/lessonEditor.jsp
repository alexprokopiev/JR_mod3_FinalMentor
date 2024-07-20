<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit lesson</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100" onload="setLessonData()">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="lessonEditorHeader" class="my-5 text-uppercase font-monospace">Расписание занятий</h1>
        <form onsubmit="editLesson();return false" name="editStudentForm">
            <div class="row justify-content-center my-3">
                <label for="lessonStartTimeForEdit" class="col-2 col-form-label text-start">Введите время начала занятия</label>
                <div class="col-3">
                    <input id="lessonStartTimeForEdit" class="form-control" name="startTime" type="datetime-local" value="${startTime}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="lessonEndTimeForEdit" class="col-2 col-form-label text-start">Введите время окончания занятия</label>
                <div class="col-3">
                    <input id="lessonEndTimeForEdit" class="form-control" name="endTime" type="datetime-local" value="${endTime}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="groupsForEdit" class="col-2 col-form-label text-start">Выберите номер группы</label>
                <div class="col-3">
                    <select id="groupsForEdit" class="form-select" name="group" size="1">
                        <c:forEach var="group" items="${db.groups}">
                            <option value=${group.id}>${group.groupNumber}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="teachersForEdit" class="col-2 col-form-label text-start">Выберите преподавателя</label>
                <div class="col-3">
                    <select id="teachersForEdit" class="form-select" name="teacher" size="1">
                        <c:forEach var="teacher" items="${db.teachers}">
                            <option value=${teacher.id}>${teacher.fullName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Редактировать</button>
        </form>
        <form action='<c:url value="/timetable/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к расписанию</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function setLessonData() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editStartTime = document.getElementById("lessonStartTimeForEdit");
            let editEndTime = document.getElementById("lessonEndTimeForEdit");
            let editGroups = document.getElementById("groupsForEdit");
            let editTeachers = document.getElementById("teachersForEdit");

            editStartTime.value = params.get("startTime");
            editEndTime.value = params.get("endTime");
            editGroups.options.filter((text) => text === params.get("groupNumber")).selected = "true";
            editTeachers.options.filter((text) => text === params.get("fullName")).selected = "true";
        }

        function editLesson() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editId = params.get("id");
            let groupSelector = document.getElementById("groupsForEdit");
            let editGroup = parseInt(groupSelector.options[groupSelector.selectedIndex].value);
            let teacherSelector = document.getElementById("teachersForEdit");
            let editTeacher = parseInt(teacherSelector.options[teacherSelector.selectedIndex].value);
            let editStartTime = document.getElementById("lessonStartTimeForEdit").value.replace(/T/g, ' ');
            let editEndTime = document.getElementById("lessonEndTimeForEdit").value.replace(/T/g, ' ');
            let paramString = "?groupId=" + editGroup + "&teacherId=" + editTeacher + "&startTime=" + editStartTime + "&endTime=" + editEndTime;

            let editRequest = new XMLHttpRequest();
            editRequest.open("PUT", '<c:url value="/timetable/"/>' + editId + paramString);
            editRequest.setRequestHeader("Content-type", "text/html;charset=UTF-8");
            editRequest.send();
            editRequest.onload = function () {
                if (editRequest.status === 200) {
                    showMessage("Расписание успешно отредактировано");
                } else {
                    showMessage("При редактировании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("lessonEditorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
