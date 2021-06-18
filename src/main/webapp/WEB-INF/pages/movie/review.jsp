<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Evaluation page</title>
</head>
<body>
<div>
    <h2>Movie Evaluations</h2>
    <table>
        <tr>
            <th colspan="2">${movie.title}</th>
        </tr>
        <tr>
            <td>rating</td>
            <td>${movie.rating}</td>
        </tr>
        <tr>
            <td>year</td>
            <td>${movie.releaseYear}</td>
        </tr>
        <tr>
            <td>country</td>
            <td>${movie.country}</td>
        </tr>
        <tr>
            <td>genres</td>
            <td><c:forEach items="${movie.genres}" var="genre">
                ${genre} &nbsp
            </c:forEach></td>
        </tr>
        <tr>
            <td>director</td>
            <td>${movie.director}</td>
        </tr>
        <tr>
            <td colspan="2">${movie.description}</td>
        </tr>
    </table>
    <div>
        <sec:authorize access="hasAnyRole('USER')">
            <form:form action="/user/evaluateMovie" method="POST" modelAttribute="evalRequest">
                <h2>Evaluation form</h2>
                <div>
                    <form:input type="hidden" path="movieId" value="${movie.id}"/>
                </div>
                <div>
                    <form:input type="hidden" path="userName" value="${pageContext.request.userPrincipal.name}"/>
                </div>
                <div>
                    <form:input type="number" min="1" max="5" path="satisfactionGrade" placeholder="satisfactionGrade"
                                required="true"/>
                </div>
                <div>
                    <form:input type="text" class="mytext" maxlength="250" path="review" placeholder="review" required="true"/>
                </div>
                <button type="submit">Evaluate</button>
            </form:form>
        </sec:authorize>
    </div>
    <h4>Evaluations list</h4>
    <table>
        <c:forEach items="${movie.evaluations}" var="evaluation">
            <tr>
                <th>rating</th>
                <td>${evaluation.satisfactionGrade}</td>
            </tr>
            <tr>
                <th>user</th>
                <td>${evaluation.userName}</td>
            </tr>
            <tr>
                <th>review</th>
                <td>${evaluation.review}</td>
            </tr>
            <tr>
                <th>date</th>
                <td>${evaluation.reviewDate}</td>
            </tr>
        </c:forEach>
    </table>

    <a href="${pageContext.request.contextPath}/movie/movies">Go to movies</a>
</div>
</body>
</html>