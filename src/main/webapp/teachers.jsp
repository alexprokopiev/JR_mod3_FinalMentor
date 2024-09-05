<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Our teachers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="teachersHeader" class="my-5 text-uppercase font-monospace">Наши преподаватели</h1>
        <form action='<c:url value="/teacherCreator.jsp?subject=${subject}"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Создать нового преподавателя</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Имя преподавателя</th>
                <th>Возраст</th>
                <th>Преподаваемые дисциплины</th>
                <th>Опыт работы</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="teacher" items="${teachers}">
                <tr>
                    <td>${teacher.fullName}</td>
                    <td>${teacher.age}</td>
                    <td>${teacher.subjects.toString().substring(1, teacher.subjects.toString().length()-1)}</td>
                    <td>${teacher.experience}</td>
                    <td>
                        <span style="display:none">${teacher.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="openTeacherEditor(this)">Редактировать</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
        function openTeacherEditor(button) {
            let teacherId = button.parentNode.firstElementChild.textContent;
            let fullName = button.parentNode.parentNode.children[0].innerHTML;
            let subjectsString = button.parentNode.parentNode.children[2].innerHTML;
            let subjects = subjectsString.split(", ");
            let paramString = "?id=" + teacherId + "&fullName=" + fullName;
            for(let subject of subjects) {
                paramString = paramString + "&subjects=" + subject;
            }
            window.location.href = '<c:url value="/teacherEditor.jsp"/>' + paramString;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
