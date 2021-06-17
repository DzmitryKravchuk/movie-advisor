<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
    <form method="POST" action="/login">
        <h2>Entering the system</h2>
        <div>
            <input name="username" type="text" placeholder="login"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="password"/>
            <button type="submit">Log In</button>
            <h4><a href="/registration">Register</a></h4>
        </div>
    </form>
</div>
</body>
</html>