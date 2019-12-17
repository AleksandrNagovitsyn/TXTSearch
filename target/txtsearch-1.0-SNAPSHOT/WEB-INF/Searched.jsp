<%@ page import="java.util.List" %>
<%@ page import="ru.itpark.constants.Constants" %>
<%@ page import="ru.itpark.model.Query" %><%--
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
    <%@include file="bootstrap.jsp"%>
    <style>
        body {
            background: aliceblue url("/old-book.jpg");
        }
    </style>


    <title>Document</title>
</head>
<body>
<div class="container">
    <h1 class="display-1">Результаты поиска</h1>
<form action="<%= request.getContextPath()%>/search" method="get" enctype="multipart/form-data">
    <div class="form-group">
        <input type="hidden" name="action" class="form-control" value="search">
    </div>
    <div class="form-group">
        <input type="text" placeholder="Введите искомую фразу" class="form-control" name="q">
    </div>
</form>

<form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <input type="hidden" name="action" value="save">
    </div>
    <div class="form-group">
        <input type="file" name="file" class="form-control-file" accept="text/plain">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Загрузить</button>
    </div>
</form>



    <% for (Query query:(List<Query>)request.getAttribute(Constants.ITEMS)) {%>
    <p><%=query.toString()%></p>
    <%}%>

</div>

</body>
</html>