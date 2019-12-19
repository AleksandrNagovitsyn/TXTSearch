<%@ page import="java.util.UUID" %>
<%@ page import="ru.itpark.constants.Constants" %>
<%@ page import="java.io.Writer" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 02.12.2019
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            background: aliceblue url("/old-book.jpg");
        }
    </style>
    <title>TXTSearcher</title>
</head>
<body bgcolor="#c0c0c0" background="old-book.jpg">

<div class="container">



    <h1 class="page-header">Добро пожаловать На TXTSearchService!</h1>

    <form action="<%= request.getContextPath()%>/search" method="get" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" class="form-control" value="search">
        </div>
        <div class="form-group">
            <input type="text"  class ="form-control form-control-lg" placeholder="Введите искомую фразу" class="form-control" name="q"
                      value="<%= request.getAttribute("Strings") == null?"": request.getAttribute("Strings")%>">
        </div>
    </form>


    <form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" value="save">
        </div>
        <div class="custom-file">
            <input type="file" name="file" id = "File" class="form-file-input" accept="text/plain">

            <label class="custom-file-label" for="File">Выбирет файл для загрузки на сервер</label>
        </div>

        <div class="form-group mt-3">
            <button type="submit" class="btn btn-primary">Загрузить</button>
        </div>
    </form>
</div>
</body>
</html>


<%--<%@ page import="ru.itpark.model.House" %>--%>
<%--<%@ page import="java.util.Collection" %>--%>
<%--<%@ page import="ru.itpark.constant.Constants" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="java.util.UUID" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<!doctype html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport"--%>
<%--          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="ie=edge">--%>
<%--    <title>Document</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form action="<%= request.getContextPath() %>/houses/search">--%>
<%--    <input name="q" placeholder="Поиск" value="<%= request.getAttribute(Constants.SEARCH_QUERY) == null ? "" : request.getAttribute(Constants.SEARCH_QUERY) %>">--%>
<%--    &lt;%&ndash; если поле всего одно, то Enter приводит к отправке формы &ndash;%&gt;--%>
<%--</form>--%>

<%--<ul>--%>
<%--    <% for (House item : (Collection<House>)request.getAttribute(Constants.ITEMS)) { %>--%>
<%--    <li>--%>
<%--        <img src="<%= request.getContextPath() %>/images/<%= item.getId() %>">--%>
<%--        <%= item.getDistrict() %> : <%= item.getPrice() %>--%>
<%--        <form  action="<%= request.getContextPath() %>/houses" method="post">--%>
<%--            <input type="hidden" name="id" value="<%= item.getId() %>">--%>
<%--            <input type="hidden" name="action" value="edit">--%>
<%--            <button>Редактировать</button>--%>
<%--        </form>--%>
<%--        <form  action="<%= request.getContextPath() %>/houses" method="post">--%>
<%--            <input type="hidden" name="id" value="<%= item.getId() %>">--%>
<%--            <input type="hidden" name="action" value="remove">--%>
<%--            <button>Удалить</button>--%>
<%--        </form>--%>
<%--        <ul>--%>
<%--            <% for (String underground : item.getUndergrounds()) { %>--%>
<%--            <li><%= underground %></li>--%>
<%--            <% } %>--%>
<%--        </ul>--%>
<%--    </li>--%>
<%--    <% } %>--%>
<%--</ul>--%>

<%--<% House item = new Hose(); %>--%>
<%--<form action="<%= request.getContextPath() %>/houses" method="post" enctype="multipart/form-data">--%>
<%--    <input type="hidden" name="id" value="<%= item == null ? "" : item.getId() %>">--%>
<%--    <input type="hidden" name="action" value="save">--%>
<%--    <input name="district" placeholder="Район" value="<%= item == null ? "" : item.getDistrict() %>">--%>
<%--    <input name="price" type="number" placeholder="Цена" value="<%= item == null ? "" : item.getPrice() %>">--%>
<%--    <input name="undergrounds" placeholder="Метро через пробел" value="<%= item == null ? "" : item.getUndergrounds() %>">--%>
<%--    <input type="file" name="file" accept="image/*">--%>
<%--    <button>Сохранить</button>--%>
<%--</form>--%>

<%--</body>--%>
<%--</html>--%>
