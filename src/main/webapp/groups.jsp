<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Groups</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="groupsHeader" class="my-5 text-uppercase font-monospace">Учебные группы</h1>
        <form class="row justify-content-center" action='<c:url value="/groups/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по фамилии студента</button>
            </div>
            <div class="col-3">
                <label for="lastName"></label><input id="lastName" class="form-control" name="lastName" type="text" value="${lastName}">
            </div>
        </form>
        <form class="row justify-content-center" action='<c:url value="/groups/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по номеру группы</button>
            </div>
            <div class="col-3">
                <label for="groupNumber"></label><input id="groupNumber" class="form-control" name="groupNumber" type="text" value="${groupNumber}">
            </div>
        </form>
        <form action='<c:url value="/groups/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Показать все группы</button>
        </form>
        <form action='<c:url value="/groupCreator.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Создать новую группу</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th rowspan="2">Номер группы</th>
                <th colspan="5">Ученики</th>
                <th></th>
            </tr>
            <tr>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Дата рождения</th>
                <th>Номер телефона</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="group" items="${groups}">
                <c:forEach var="student" items="${group.students}">
                    <tr>
                        <c:if test = "${group.students.indexOf(student) == 0}">
                            <td rowspan="${group.students.size()}">${group.groupNumber}</td>
                        </c:if>
                        <td>${student.lastName}</td>
                        <td>${student.name}</td>
                        <td>${student.patronymic}</td>
                        <td>${student.birthDate}</td>
                        <td>${student.phoneNumber}</td>
                        <c:if test = "${group.students.indexOf(student) == 0}">
                            <td rowspan="${group.students.size()}">
                                <span style="display:none">${group.id}</span>
                                <button class="btn btn-outline-primary btn-sm" onclick="openGroupEditor(this)">Редактировать</button>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
        function openGroupEditor(button) {
            let groupId = button.parentNode.firstElementChild.textContent;
            let groupNumber = button.parentNode.parentNode.children[0].innerHTML;
            console.log(groupNumber);
            let paramString = "?id=" + groupId + "&groupNumber=" + groupNumber;

            window.location.href = '<c:url value="/groupEditor.jsp"/>' + paramString;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
