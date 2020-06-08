<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User form</title>
</head>
<body>
<span style="color: red; ">${errorMessage}</span>
<form method="post">
    User Name : <input type="text" name="username"/>
    First Name : <input type="text" name="firstname"/>
    Last Name : <input type="text" name="lastname"/>
    Birth Date : <input type="date" name="birth"/>

    <input type="submit" value="Add"/>
    <br><br>
    All Users
    <br>
    <table border="1">
        <tr>
            <td>User Name:</td>
            <td>First Name:</td>
            <td>Last Name:</td>
            <td>Birth Date:</td>
        </tr>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td>${user.userName}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.birth}</td>
            </tr>
        </c:forEach>
    </table>

</form>
</body>
</html>
