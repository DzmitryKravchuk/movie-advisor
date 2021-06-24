<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Home page</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div>
    <h3>Hello ${pageContext.request.userPrincipal.name}</h3>
    <sec:authorize access="!isAuthenticated()">
        <h4><a href="/login">Log in</a></h4>
        <h4><a href="/registration">Register</a></h4>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <h4><a href="/logout">Log out</a></h4>
    </sec:authorize>
    <h4><a href="/movie/movies/page/1">Movies (available for all users)</a></h4>
    <h4><a href="/admin/users">Users (available for admin only)</a></h4>
</div>
</body>
</html>