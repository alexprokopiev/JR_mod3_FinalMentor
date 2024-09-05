<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create new student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="studentCreatorHeader" class="my-5 text-uppercase font-monospace">Наши ученики</h1>
        <form onsubmit="createStudent();return false" name="createStudentForm">
            <div class="row justify-content-center my-3">
                <label for="studentNameForCreate" class="col-2 col-form-label text-start">Введите имя</label>
                <div class="col-3">
                    <input id="studentNameForCreate" class="form-control" name="name" type="text" value="${name}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentPatronymicForCreate" class="col-2 col-form-label text-start">Введите отчество</label>
                <div class="col-3">
                    <input id="studentPatronymicForCreate" class="form-control" name="patronymic" type="text" value="${patronymic}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentLastNameForCreate" class="col-2 col-form-label text-start">Введите фамилию</label>
                <div class="col-3">
                    <input id="studentLastNameForCreate" class="form-control" name="lastName" type="text" value="${lastName}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentBirthDateForCreate" class="col-2 col-form-label text-start">Введите дату рождения</label>
                <div class="col-3">
                    <input id="studentBirthDateForCreate" class="form-control" name="birthDate" type="date" value="${birthDate}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentPhoneNumberForCreate" class="col-2 col-form-label text-start">Введите номер телефона</label>
                <div class="col-3">
                    <input id="studentPhoneNumberForCreate" class="form-control" name="phoneNumber" type="text" value="${phoneNumber}">
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Создать</button>
        </form>
        <form action='<c:url value="/students/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку учеников</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function createStudent() {
            let createName = document.getElementById("studentNameForCreate").value;
            let createPatronymic = document.getElementById("studentPatronymicForCreate").value;
            let createLastName = document.getElementById("studentLastNameForCreate").value;
            let createBirthDate = document.getElementById("studentBirthDateForCreate").value;
            let createPhoneNumber = document.getElementById("studentPhoneNumberForCreate").value;

            let createData = {
                name: createName,
                patronymic: createPatronymic,
                lastName: createLastName,
                birthDate: createBirthDate,
                phoneNumber: createPhoneNumber
            }
            let body = JSON.stringify(createData);
            let createRequest = new XMLHttpRequest();
            createRequest.open("POST", '<c:url value="/students/"/>');
            createRequest.setRequestHeader("Content-type", "application/json");
            createRequest.send(body);
            createRequest.onload = function () {
                if (createRequest.status === 201) {
                    showMessage("Ученик успешно добавлен");
                    clearFields();
                } else {
                    showMessage("При создании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("studentCreatorHeader");
            let div = document.createElement("div");
            div.innerHTML = message;
            header.after(div);
        }

        function clearFields(){
            document.getElementById("studentNameForCreate").value = "";
            document.getElementById("studentPatronymicForCreate").value = "";
            document.getElementById("studentLastNameForCreate").value = "";
            document.getElementById("studentBirthDateForCreate").value = "";
            document.getElementById("studentPhoneNumberForCreate").value = "";
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
