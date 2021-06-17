<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies</title>
</head>
<body>
<div>
    <h2>Movies list</h2>
    <table>
        <thead>
        <th>Title</th>
        <th>Year</th>
        <th>Director</th>
        <th>Country</th>
        <th>Genres</th>
        <th>Rating</th>
        </thead>
        <c:forEach items="${allMovies}" var="movie">
            <tr>
                <td>${movie.title}</td>
                <td>${movie.releaseYear}</td>
                <td>${movie.director} </td>
                <td>${movie.country} </td>
                <td><c:forEach items="${movie.genres}" var="genre">
                    ${genre} &nbsp
                </c:forEach> </td>
                <td>${movie.rating} </td>
                <sec:authorize access="hasAnyRole('USER')">
                <td>
                    <a href="/user/movie/${movie.id}">Evaluate</a>
                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>



    <a href="/">Go home</a>
</div>
</body>
</html>