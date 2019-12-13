<%@ page import="java.util.UUID" %>
<%@ page import="ru.itpark.constants.Constants" %><%--
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
    <title>TXTSearcher</title>
</head>
<body>

<form action="<%= request.getContextPath()%>/search" method="get" enctype="multipart/form-data">
    <input type="hidden" name = "action" value="search">
    <input type="text" placeholder="Введите искомую фразу" name="q" value = "<%= request.getAttribute("Strings") == null?"": request.getAttribute("String")%>">
</form>

<form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="save">
    <input type="file" name = "file" accept="text/plain">
    <button>Загрузить</button>
</form>
<p>${Strings}</p>
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
