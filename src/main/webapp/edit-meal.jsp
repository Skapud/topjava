<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit/Create</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Edit-Create meal</h3>

<form method="post" action="meals">
    <table>
        <input type="hidden" name="id" value="${meal.id}">
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="text" name="calories" value="${meal.calories}"></td>
        </tr>
    </table>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>

</body>
</html>
