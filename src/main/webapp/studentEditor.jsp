<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100" onload="setFormData()">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="studentEditorHeader" class="my-5 text-uppercase font-monospace">Наши ученики</h1>
        <form onsubmit="editStudent();return false" name="editStudentForm">
            <div class="row justify-content-center my-3">
                <label for="studentNameForEdit" class="col-2 col-form-label text-start">Введите имя</label>
                <div class="col-3">
                    <input id="studentNameForEdit" class="form-control" name="name" type="text" value="${name}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentPatronymicForEdit" class="col-2 col-form-label text-start">Введите отчество</label>
                <div class="col-3">
                    <input id="studentPatronymicForEdit" class="form-control" name="patronymic" type="text" value="${patronymic}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentLastNameForEdit" class="col-2 col-form-label text-start">Введите фамилию</label>
                <div class="col-3">
                    <input id="studentLastNameForEdit" class="form-control" name="lastName" type="text" value="${lastName}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentBirthDateForEdit" class="col-2 col-form-label text-start">Введите дату рождения</label>
                <div class="col-3">
                    <input id="studentBirthDateForEdit" class="form-control" name="birthDate" type="date" value="${birthDate}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentPhoneNumberForEdit" class="col-2 col-form-label text-start">Введите номер телефона</label>
                <div class="col-3">
                    <input id="studentPhoneNumberForEdit" class="form-control" name="phoneNumber" type="text" value="${phoneNumber}">
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Редактировать</button>
        </form>
        <form action='<c:url value="/students/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку учеников</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function setFormData() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editName = document.getElementById("studentNameForEdit");
            let editPatronymic = document.getElementById("studentPatronymicForEdit");
            let editLastName = document.getElementById("studentLastNameForEdit");
            let editBirthDate = document.getElementById("studentBirthDateForEdit");
            let editPhoneNumber = document.getElementById("studentPhoneNumberForEdit");

            editName.value = params.get("name");
            editPatronymic.value = params.get("patronymic");
            editLastName.value = params.get("lastName");
            editBirthDate.value = params.get("birthDate");
            editPhoneNumber.value = params.get("phoneNumber");
        }

        function editStudent() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editId = params.get("id");
            let editName = document.getElementById("studentNameForEdit").value;
            let editPatronymic = document.getElementById("studentPatronymicForEdit").value;
            let editLastName = document.getElementById("studentLastNameForEdit").value;
            let editBirthDate = document.getElementById("studentBirthDateForEdit").value;
            let editPhoneNumber = document.getElementById("studentPhoneNumberForEdit").value.replace(/\+/g, "%2b");
            let paramString = "?name=" + editName + "&patronymic=" + editPatronymic + "&lastName=" + editLastName + "&birthDate=" + editBirthDate + "&phoneNumber=" + editPhoneNumber;

            let editRequest = new XMLHttpRequest();
            editRequest.open("PUT", '<c:url value="/students/"/>' + editId + paramString);
            editRequest.setRequestHeader("Content-type", "text/html;charset=UTF-8");
            editRequest.send();
            editRequest.onload = function () {
                if (editRequest.status === 200) {
                    showMessage("Ученик успешно отредактирован");
                } else {
                    showMessage("При редактировании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("studentEditorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
