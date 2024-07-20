<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit group</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100" onload="setGroupData()">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="groupEditorHeader" class="my-5 text-uppercase font-monospace">Учебные группы</h1>
        <div>Группа <span id="groupNumber"></span></div>
        <form onsubmit="editGroup();return false" name="editGroupForm">
            <div class="row justify-content-center my-3">
                <label for="studentsForEdit" class="col-2 col-form-label text-start">Выберите учеников</label>
                <div class="col-3">
                    <select id="studentsForEdit" class="form-select" name="students" multiple>
                        <c:forEach var="student" items="${db.students}">
                            <option value=${student.id}>${student.lastName} ${student.name} ${student.patronymic} ${student.birthDate}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Добавить</button>
        </form>
        <form action='<c:url value="/groups/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку групп</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function setGroupData() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let groupNumber = document.getElementById("groupNumber");

            groupNumber.innerText = params.get("groupNumber");
        }

        function editGroup() {
            const currentUrl = new URL(window.location.href);
            const params = new URLSearchParams(currentUrl.search);
            let editId = params.get("id");
            let editStudentsId = document.getElementById("studentsForEdit").selectedOptions;
            let paramString = "";
            for (let i = 0; i < editStudentsId.length; i++) {
                if (i === 0) paramString = paramString + "?studentId=" + editStudentsId[i].value;
                else paramString = paramString + "&studentId=" + editStudentsId[i].value;
            }

            let editRequest = new XMLHttpRequest();
            editRequest.open("PUT", '<c:url value="/groups/"/>' + editId + paramString);
            editRequest.setRequestHeader("Content-type", "text/html;charset=UTF-8");
            editRequest.send();
            editRequest.onload = function () {
                if (editRequest.status === 200) {
                    showMessage("Группа успешно отредактирована");
                } else {
                    showMessage("При редактировании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("groupEditorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
