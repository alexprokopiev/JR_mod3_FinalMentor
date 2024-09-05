<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit teacher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100" onload="setTeacherData()">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="teacherEditorHeader" class="my-5 text-uppercase font-monospace">Наши преподаватели</h1>
        <div>Преподаватель <span id="teacherFullName"></span></div>
        <form onsubmit="editTeacher();return false" name="editTeacherForm">
            <div class="row justify-content-center my-3">
                <label for="teacherSubjectsForEdit" class="col-2 col-form-label text-start">Выберите дисциплины</label>
                <div class="col-3">
                    <select id="teacherSubjectsForEdit" class="form-select" name="subjects" multiple>
                        <c:forEach var="subjectItem" items="${subject}">
                            <option value="${subjectItem}">${subjectItem.toString()}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Редактировать</button>
        </form>
        <form action='<c:url value="/teachers/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку преподавателей</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function setTeacherData() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let teacherFullName = document.getElementById("teacherFullName");
            let teacherSubjects = document.getElementById("teacherSubjectsForEdit");
            let subjects = params.getAll("subjects");

            teacherFullName.innerText = params.get("fullName");
            let option;
            let subject;
            for (option of teacherSubjects.options) {
                for (subject of subjects) {
                    if (option.text === subject) option.selected = "true";
                }
            }
        }

        function editTeacher() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editId = params.get("id");
            let editSubjects = document.getElementById("teacherSubjectsForEdit").selectedOptions;
            let paramString = "";
            for (let i = 0; i < editSubjects.length; i++) {
                if (i === 0) paramString = paramString + "?subject=" + editSubjects[i].value;
                else paramString = paramString + "&subject=" + editSubjects[i].value;
            }

            let editRequest = new XMLHttpRequest();
            editRequest.open("PUT", '<c:url value="/teachers/"/>' + editId + paramString);
            editRequest.setRequestHeader("Content-type", "text/html;charset=UTF-8");
            editRequest.send();
            editRequest.onload = function () {
                if (editRequest.status === 200) {
                    showMessage("Преподаватель успешно отредактирован");
                } else {
                    showMessage("При редактировании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("teacherEditorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
