<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime.format(DateUtil.DATE_TIME_FORMATTER)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
</table>
<hr>

</body>
</html>