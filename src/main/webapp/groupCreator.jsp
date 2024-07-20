<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create new group</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 id="groupCreatorHeader" class="my-5 text-uppercase font-monospace">Учебные группы</h1>
        <form onsubmit="createGroup();return false" name="createGroupForm">
            <div class="row justify-content-center my-3">
                <label for="groupNumberForCreate" class="col-2 col-form-label text-start">Введите номер группы</label>
                <div class="col-3">
                    <input id="groupNumberForCreate" class="form-control" name="groupNumber" type="text" value="${groupNumber}">
                </div>
            </div>
            <div class="row justify-content-center my-3">
                <label for="studentsForCreate" class="col-2 col-form-label text-start">Выберите учеников</label>
                <div class="col-3">
                    <select id="studentsForCreate" class="form-select" name="students" multiple>
                        <c:forEach var="student" items="${db.students}">
                            <option value=${student}>${student.lastName} ${student.name} ${student.patronymic} ${student.birthDate}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button class="btn btn-outline-primary w-25" type="submit">Создать</button>
        </form>
        <form action='<c:url value="/groups/"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться к списку групп</button>
        </form>
        <form action='<c:url value="/index.jsp"/>'>
            <button class="btn btn-outline-primary w-25" type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
    <script>
        function createGroup() {
            let createGroupNumber = document.getElementById("groupNumberForCreate").value;
            let checkedOptions = document.getElementById("studentsForCreate").selectedOptions;
            let createStudents = Array.from(checkedOptions).map(({value}) => value);
            let createData = {
                groupNumber: createGroupNumber,
                students: createStudents
            }
            let body = JSON.stringify(createData).replace(/"{/g,'{').replace(/}"/g,'}').replace(/\\"/g,'"')
                .replace(/\\\\u0410/g,'А').replace(/\\\\u0411/g,'Б').replace(/\\\\u0412/g,'В').replace(/\\\\u0413/g,'Г')
                .replace(/\\\\u0414/g,'Д').replace(/\\\\u0415/g,'Е').replace(/\\\\u0401/g,'Ё').replace(/\\\\u0416/g,'Ж')
                .replace(/\\\\u0417/g,'З').replace(/\\\\u0418/g,'И').replace(/\\\\u0419/g,'Й').replace(/\\\\u041A/g,'К')
                .replace(/\\\\u041B/g,'Л').replace(/\\\\u041C/g,'М').replace(/\\\\u041D/g,'Н').replace(/\\\\u041E/g,'О')
                .replace(/\\\\u041F/g,'П').replace(/\\\\u0420/g,'Р').replace(/\\\\u0421/g,'С').replace(/\\\\u0422/g,'Т')
                .replace(/\\\\u0423/g,'У').replace(/\\\\u0424/g,'Ф').replace(/\\\\u0425/g,'Х').replace(/\\\\u0426/g,'Ц')
                .replace(/\\\\u0427/g,'Ч').replace(/\\\\u0428/g,'Ш').replace(/\\\\u0429/g,'Щ').replace(/\\\\u042A/g,'Ъ')
                .replace(/\\\\u042B/g,'Ы').replace(/\\\\u042C/g,'Ь').replace(/\\\\u042D/g,'Э').replace(/\\\\u042E/g,'Ю')
                .replace(/\\\\u042F/g,'Я').replace(/\\\\u0430/g,'а').replace(/\\\\u0431/g,'б').replace(/\\\\u0432/g,'в')
                .replace(/\\\\u0433/g,'г').replace(/\\\\u0434/g,'д').replace(/\\\\u0435/g,'е').replace(/\\\\u0451/g,'ё')
                .replace(/\\\\u0436/g,'ж').replace(/\\\\u0437/g,'з').replace(/\\\\u0438/g,'и').replace(/\\\\u0439/g,'й')
                .replace(/\\\\u043A/g,'к').replace(/\\\\u043B/g,'л').replace(/\\\\u043C/g,'м').replace(/\\\\u043D/g,'н')
                .replace(/\\\\u043E/g,'о').replace(/\\\\u043F/g,'п').replace(/\\\\u0440/g,'р').replace(/\\\\u0441/g,'с')
                .replace(/\\\\u0442/g,'т').replace(/\\\\u0443/g,'у').replace(/\\\\u0444/g,'ф').replace(/\\\\u0445/g,'х')
                .replace(/\\\\u0446/g,'ц').replace(/\\\\u0447/g,'ч').replace(/\\\\u0448/g,'ш').replace(/\\\\u0449/g,'щ')
                .replace(/\\\\u044A/g,'ъ').replace(/\\\\u044B/g,'ы').replace(/\\\\u044C/g,'ь').replace(/\\\\u044D/g,'э')
                .replace(/\\\\u044E/g,'ю').replace(/\\\\u044F/g,'я');

            let createRequest = new XMLHttpRequest();
            createRequest.open("POST", '<c:url value="/groups/"/>');
            createRequest.setRequestHeader("Content-type", "application/json");
            createRequest.send(body);
            createRequest.onload = function () {
                    if (createRequest.status === 201) {
                    showMessage("Группа успешно создана");
                    clearFields();
                } else {
                    showMessage("При создании произошла ошибка");
                }
            }
        }

        function showMessage(message) {
            let header = document.getElementById("groupCreatorHeader");
            let div = document.createElement("div");
            div.className = "alert";
            div.innerHTML = message;
            header.after(div);
        }

        function clearFields(){
            document.getElementById("groupNumberForCreate").value = "";
            document.getElementById("studentsForCreate").selectedIndex = -1;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
