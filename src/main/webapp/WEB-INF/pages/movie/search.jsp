<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Movies</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h3>Search movie by genre</h3>
    <select id="genres" name="genres">
        <option value="">Choose options</option>
        <c:forEach items="${allGenres}" var="genre">
            <option>${genre.genreName}</option>
        </c:forEach>
    </select>
    <br><br>
    <table id="demo"></table>

    <h3>Search movie by country</h3>
    <select id="countries" name="countries">
        <option value="">Choose options</option>
        <c:forEach items="${allCountries}" var="country">
            <option>${country.countryName}</option>
        </c:forEach>
    </select>
    <br><br>
    <table id="demo1"></table>


    <br><br>
    <a href="${pageContext.request.contextPath}/">Go home</a>
    <script>
        $(function () {
            $("#genres").change(function (event) {
                $.ajax({
                    url: "/movie/genreChoose",
                    type: "POST",
                    dataType: "json",
                    data: {genre: $(event.target).val()},
                })
                    .done(function (data) {
                        setMovies(data, "demo")
                    })
                    .fail(function (xhr, status, error) {
                        alert(xhr.responseText + '|\n' + status + '|\n' + error);
                    });
            });
        });

        $(function () {
            $("#countries").change(function (event) {
                $.ajax({
                    url: "/movie/countryChoose",
                    type: "POST",
                    dataType: "json",
                    data: {country: $(event.target).val()},
                })
                    .done(function (data) {
                        setMovies(data, "demo1")
                    })
                    .fail(function (xhr, status, error) {
                        alert(xhr.responseText + '|\n' + status + '|\n' + error);
                    });
            });
        });


        var setMovies = function (data,elementName) {
            let table = "<tr><th>Title</th><th>Year</th><th>Director</th><th>Country</th><th>Genres</th><th>Rating</th></tr>";
            var len = data.length;
            for (var i = 0; i < len; i++) {
                table += "<tr><td>" + data[i].title + "</td><td>"
                    + data[i].releaseYear + "</td><td>"
                    + data[i].director + "</td><td>"
                    + data[i].country + "</td><td>"
                    + data[i].genres + "</td><td>"
                    + data[i].rating + '</td><td><a target="_blank" href="/movie/review'
                    + data[i].id
                    + '">Review</a></td></tr>';
            }
            document.getElementById(elementName).innerHTML = table;
        };
    </script>
</div>
</body>
</html>