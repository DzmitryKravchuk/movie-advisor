<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
</head>

<body>
<div>
    <form:form method="POST" modelAttribute="regRequest">
        <h2>Регистрация</h2>
        <div>
            <form:input type="text" path="login" placeholder="Логин"
                        autofocus="true" required="true"/>
        </div>
        <div>
            <form:input type="password" path="password" placeholder="Пароль" required="true"/>
        </div>

        <button type="submit">Зарегистрироваться</button>
    </form:form>
    <a href="/">Главная</a>
</div>
</body>
</html>