<%@ page import="java.util.List" %>
<%@ page import="ru.itpark.constants.Constants" %>
<%@ page import="ru.itpark.model.Query" %>
<%@ page import="ru.itpark.enumeration.QueryStatus" %>
<%@ page import="java.util.Queue" %>
<%@ page import="java.nio.file.Path" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru">
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

        .mt-0 {
            margin-top: 100px !important;
        }

        .mt-10{
            margin-top: 200px;
        }
        .mt-50 {
            margin-top: 50px;
        }

    </style>


    <title>Загруженные файлы</title>
</head>
<body>
<div class="container">

    <div class="mx-auto" style="width: 200px;">

        <a href="<%= request.getContextPath()%>/" class="btn btn-primary btn-lg active mt-3" role="button"
           aria-pressed="true">TXT Searcher</a>
    </div>



    <h3>В наличии следующие файлы:</h3>
    <%for (Path path : (List<Path>) request.getAttribute("Up")) {%>
    <p><%=path%>
    </p>
    <%}%>

</div>

</body>
</html>