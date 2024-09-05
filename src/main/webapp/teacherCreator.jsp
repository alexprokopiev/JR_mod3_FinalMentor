<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create new teacher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="teacherCreatorHeader" class="my-5 text-uppercase font-monospace">Наши преподаватели</h1>
        <form onsubmit="createTeacher();return false" name="createTeacherForm">
            <div class="row justify-content-center my-3">
                <label for="teacherFullNameForCreate" class="col-2 col-form-label text-start">Введите полное имя</label>
                <div class="col-3">
                    <input id="teacherFullNameForCreate" class="form-control" name="fullName" type="text" value="${fullName}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="teacherAgeForCreate" class="col-2 col-form-label text-start">Введите возраст</label>
                <div class="col-3">
                    <input id="teacherAgeForCreate" class="form-control" name="age" type="number" value="${age}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="teacherSubjectsForCreate" class="col-2 col-form-label text-start">Выберите дисциплины</label>
                <div class="col-3">
                    <select id="teacherSubjectsForCreate" class="form-select" name="subjects" multiple>
                        <c:forEach var="subjectItem" items="${subject}">
                            <option value="${subjectItem}">${subjectItem.toString()}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="teacherExperienceForCreate" class="col-2 col-form-label text-start">Введите опыт работы</label>
                <div class="col-3">
                    <input id="teacherExperienceForCreate" class="form-control" name="experience" type="number" value="${experience}">
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Создать</button>
        </form>
        <form action='<c:url value="/teachers/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку преподавателей</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function createTeacher() {
            let createFullName = document.getElementById("teacherFullNameForCreate").value;
            let createAge = document.getElementById("teacherAgeForCreate").value;
            let checkedOptions = document.getElementById("teacherSubjectsForCreate").selectedOptions;
            let createSubjects = Array.from(checkedOptions).map(({ value }) => value);
            let createExperience = document.getElementById("teacherExperienceForCreate").value;

            let createData = {
                fullName: createFullName,
                age: createAge,
                subjects: createSubjects,
                experience: createExperience
            }
            let body = JSON.stringify(createData);
            let createRequest = new XMLHttpRequest();
            createRequest.open("POST", '<c:url value="/teachers/"/>');
            createRequest.setRequestHeader("Content-type", "application/json");
            createRequest.send(body);
            createRequest.onload = function () {
                if (createRequest.status === 201) {
                    showMessage("Преподаватель успешно добавлен");
                    clearFields();
                } else {
                    showMessage("При создании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("teacherCreatorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }

        function clearFields(){
            document.getElementById("teacherFullNameForCreate").value = "";
            document.getElementById("teacherAgeForCreate").value = "";
            document.getElementById("teacherSubjectsForCreate").selectedIndex = -1;
            document.getElementById("teacherExperienceForCreate").value = "";
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
