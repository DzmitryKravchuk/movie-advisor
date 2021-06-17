<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Registration</title>
</head>

<body>
<div>
    <form:form method="POST" modelAttribute="regRequest">
        <h2>Registration form</h2>
        <div>
            <form:input type="text" path="login" placeholder="login"
                        autofocus="true" required="true"/>
        </div>
        <div>
            <form:input type="password" path="password" placeholder="password" required="true"/>
        </div>

        <button type="submit">Submit</button>
    </form:form>
    <a href="/">Go home</a>
</div>
</body>
</html>