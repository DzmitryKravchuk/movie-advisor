<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Evaluation page</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h2>Movie Evaluations</h2>
    <table>
        <tr>
            <th colspan="2"><h4>${movie.title}</h4></th>
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
                    <p>Please select your satisfactionGrade:</p>
                    <form:radiobutton path="satisfactionGrade" value= "1" label ="1 sucks" required="true"/>
                    <form:radiobutton path="satisfactionGrade" value= "2" label ="2 poor" required="true"/>
                    <form:radiobutton path="satisfactionGrade" value= "3" label ="3 so-so" required="true"/>
                    <form:radiobutton path="satisfactionGrade" value= "4" label ="4 above avg" required="true"/>
                    <form:radiobutton path="satisfactionGrade" value= "5" label ="5 cool" required="true"/>
                </div>
                <div>
                    <form:textarea type="text" class="mytext" maxlength="250" path="review" placeholder="review"
                                   required="true"/>
                </div>
                <br>
                <div>
                    <button type="submit">Evaluate</button>
                </div>
            </form:form>
        </sec:authorize>
    </div>
    <br>
    <h4>Evaluations list</h4>
    <table>
        <c:forEach items="${movie.evaluations}" var="evaluation">
            <tr>
                <th>rating</th>
                <td>${evaluation.satisfactionGrade}</td>
            </tr>
            <tr>
                <td>user</td>
                <td>${evaluation.userName}</td>
            </tr>
            <tr>
                <td>review</td>
                <td>${evaluation.review}</td>
            </tr>
            <tr>
                <td>date</td>
                <td>${evaluation.reviewDate}</td>
            </tr>
        </c:forEach>
    </table>

    <a href="${pageContext.request.contextPath}/movie/movies">Go to movies</a>
</div>

</body>
</html>