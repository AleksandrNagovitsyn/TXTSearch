<%@ page import="java.util.List" %>
<%@ page import="ru.itpark.constants.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 14.12.2019
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<ul>

    <% for (String string:(List<String>)request.getAttribute(Constants.STRINGS)) {%>
    <li><%=string.split("\n").toString()%></li>
    <%}%>
</ul>

</body>
</html>