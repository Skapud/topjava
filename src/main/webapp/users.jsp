<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>

<form action="users" method="get">
    <input type="hidden" name="action" value="select">
    <label for="userId">Выберите пользователя:</label>
    <select name="userId" id="userId">
        <option value="1">Admin</option>
        <option value="2">User</option>
    </select>
    <input type="submit" value="Enter">
</form>
</body>
</html>