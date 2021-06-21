<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Complete users list</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
    <meta charset="utf-8">
</head>

<body>
<div>
    <table>
        <th>ID</th>
        <th>UserName</th>
        <th>Role</th>
        <c:forEach items="${allUsers}" var="movie">
            <tr>
                <td>${movie.id}</td>
                <td>${movie.userName}</td>
                <td>${movie.role.roleName} </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/users" method="post">
                        <input type="hidden" name="userId" value="${movie.id}"/>
                        <button type="submit">Delete</button>
                    </form>

                </td>

            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/">Go home</a>
</div>
</body>
</html>