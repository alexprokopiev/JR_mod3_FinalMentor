<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Timetable</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="timetableHeader" class="my-5 text-uppercase font-monospace">Расписание занятий</h1>
        <form class="row justify-content-center" action='<c:url value="/timetable/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по дате занятий</button>
            </div>
            <div class="col-3">
                <label for="date"></label><input id="date" class="form-control" name="date" type="date" value="${date}">
            </div>
        </form>
        <form class="row justify-content-center" action='<c:url value="/timetable/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по номеру группы</button>
            </div>
            <div class="col-3">
                <label for="groupNumber"></label><input id="groupNumber" class="form-control" name="groupNumber" type="text" value="${groupNumber}">
            </div>
        </form>
        <form class="row justify-content-center" action='<c:url value="/timetable/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по фамилии студента</button>
            </div>
            <div class="col-3">
                <label for="lastName"></label><input id="lastName" class="form-control" name="lastName" type="text" value="${lastName}">
            </div>
        </form>
        <form class="row justify-content-center" action='<c:url value="/timetable/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по преподавателю</button>
            </div>
            <div class="col-3">
                <label for="fullName"></label><input id="fullName" class="form-control" name="fullName" type="text" value="${fullName}">
            </div>
        </form>
        <form action='<c:url value="/timetable/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Показать полное расписание</button>
        </form>
        <form action='<c:url value="/lessonCreator.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Создать новое занятие</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Время начала занятия</th>
                <th>Время окончания занятия</th>
                <th>Номер группы</th>
                <th>Преподаватель</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="lesson" items="${timetable}">
                <tr>
                    <td>${lesson.startTime}</td>
                    <td>${lesson.endTime}</td>
                    <td>${lesson.group.groupNumber}</td>
                    <td>${lesson.teacher.fullName}</td>
                    <td>
                        <span style="display:none">${lesson.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="openLessonEditor(this)">Редактировать</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
        function openLessonEditor(button) {
            let lessonId = button.parentNode.firstElementChild.textContent;
            let startTime = button.parentNode.parentNode.children[0].innerHTML;
            let endTime = button.parentNode.parentNode.children[1].innerHTML;
            let groupNumber = button.parentNode.parentNode.children[2].innerHTML;
            let fullName = button.parentNode.parentNode.children[3].innerHTML;
            let paramString = "?id=" + lessonId + "&startTime=" + startTime + "&endTime=" + endTime + "&groupNumber=" + groupNumber + "&fullName=" + fullName;

            window.location.href = '<c:url value="/lessonEditor.jsp"/>' + paramString;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
