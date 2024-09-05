<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Education Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-black bg-gradient min-vh-100">
    <div class="container text-center text-primary min-vh-100">
        <h1 class="my-5 text-uppercase font-monospace">Образовательный портал</h1>
        <div class="w-auto h-50">
            <form action='<c:url value="/students/"/>'>
                <button type="submit" class="btn btn-outline-primary btn-lg fs-4 w-25 my-1">Список учащихся</button>
            </form>
            <form action='<c:url value="/teachers/"/>'>
                <button type="submit" class="btn btn-outline-primary btn-lg fs-4 w-25 my-1">Список преподавателей</button>
            </form>
            <form action='<c:url value="/groups/"/>'>
                <button type="submit" class="btn btn-outline-primary btn-lg fs-4 w-25 my-1">Список групп</button>
            </form>
            <form action='<c:url value="/timetable/"/>'>
                <button type="submit" class="btn btn-outline-primary btn-lg fs-4 w-25 my-1">Расписание занятий</button>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>