<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Our students</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="studentsHeader" class="my-5 text-uppercase font-monospace">Наши ученики</h1>
        <form class="row justify-content-center" action='<c:url value="/students/"/>'>
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit">Фильтровать по фамилии</button>
            </div>
            <div class="col-3">
                <label for="lastName"></label><input class="form-control" id="lastName" name="lastName" type="text" value="${lastName}">
            </div>
        </form>
        <div class="row justify-content-center my-3">
            <div class="col-3">
                <button class="btn btn-outline-primary w-100" type="submit" onclick="getStudentById()">Искать по ID</button>
            </div>
            <div class="col-3">
                <label for="studentId"></label><input class="form-control" id="studentId" name="studentId" type="number" value="${id}">
            </div>
        </div>
        <form action='<c:url value="/students/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Показать всех учеников</button>
        </form>
        <form action='<c:url value="/studentCreator.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Создать нового ученика</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Дата рождения</th>
                <th>Номер телефона</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="singleStudent" value="${student}"/>
            <c:if test = "${singleStudent != null}">
                <tr>
                    <td>${singleStudent.lastName}</td>
                    <td>${singleStudent.name}</td>
                    <td>${singleStudent.patronymic}</td>
                    <td>${singleStudent.birthDate}</td>
                    <td>${singleStudent.phoneNumber}</td>
                    <td>
                        <span style="display:none">${singleStudent.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="openStudentEditor(this)">Редактировать</button>
                    </td>
                    <td>
                        <span style="display:none">${singleStudent.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="deleteStudent(this)">Удалить</button>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.lastName}</td>
                    <td>${student.name}</td>
                    <td>${student.patronymic}</td>
                    <td>${student.birthDate}</td>
                    <td>${student.phoneNumber}</td>
                    <td>
                        <span style="display:none">${student.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="openStudentEditor(this)">Редактировать</button>
                    </td>
                    <td>
                        <span style="display:none">${student.id}</span>
                        <button class="btn btn-outline-primary btn-sm" onclick="deleteStudent(this)">Удалить</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
        function getStudentById() {
            let studentId = document.getElementById("studentId");
            let playersCountRequest = new XMLHttpRequest();
            playersCountRequest.open("GET", '<c:url value="/students/"/>'+studentId.value);
            playersCountRequest.send();
            window.location.href = '<c:url value="/students/"/>'+studentId.value;
        }

        function openStudentEditor(button) {
            let studentId = button.parentNode.firstElementChild.textContent;
            let studentName = button.parentNode.parentNode.children[1].innerHTML;
            let studentPatronymic = button.parentNode.parentNode.children[2].innerHTML;
            let studentLastName = button.parentNode.parentNode.children[0].innerHTML;
            let studentBirthDate = button.parentNode.parentNode.children[3].innerHTML;
            let studentPhoneNumber = button.parentNode.parentNode.children[4].innerHTML.replace(/\+/g, "%2b");
            let paramString = "?id=" + studentId + "&name=" + studentName + "&patronymic=" + studentPatronymic + "&lastName=" + studentLastName + "&birthDate=" + studentBirthDate + "&phoneNumber=" + studentPhoneNumber;

            window.location.href = '<c:url value="/studentEditor.jsp"/>' + paramString;
        }

        function deleteStudent(button) {
            let studentId = button.parentNode.firstElementChild.textContent;

            let deleteRequest = new XMLHttpRequest();
            deleteRequest.open("DELETE", '<c:url value="/students/"/>' + studentId);
            deleteRequest.setRequestHeader("Content-type", "text/html;charset=UTF-8");
            deleteRequest.send();
            deleteRequest.onload = function () {
                if (deleteRequest.status === 204) {
                    showMessage("Ученик успешно удален");
                    setTimeout(() => {window.location.href = '<c:url value="/students/"/>';}, 2000);
                } else {
                    showMessage("При удалении произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("studentsHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
