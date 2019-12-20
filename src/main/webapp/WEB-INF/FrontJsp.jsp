<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
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
    <title>RFCSearcher</title>
</head>
<body bgcolor="#c0c0c0" background="old-book.jpg">

<div class="container">


    <h1 class="page-header">RFC Searcher</h1>

    <form action="<%= request.getContextPath()%>/search" method="get" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" class="form-control" value="search">
        </div>
        <div class="form-group">
            <input type="text" class="form-control form-control-lg" placeholder="Введите искомую фразу"
                   class="form-control" name="q"
                   value="<%= request.getAttribute("Strings") == null?"": request.getAttribute("Strings")%>">
        </div>
    </form>


    <form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" value="save">
        </div>
        <div class="custom-file">
            <input type="file" name="file" id="File" class="form-file-input" accept="text/plain" multiple>

            <label class="custom-file-label" for="File">Выбирет файл для загрузки на сервер</label>

        </div>


        <div class="form-group mt-3">
            <button type="submit" class="btn btn-primary">Загрузить</button>
        </div>
    </form>

    <h3>В наличии следующие файлы:</h3>
    <%for (Path path : (List<Path>) request.getAttribute("Up")) {%>
    <p><%=path%>
    </p>
    <%}%>

</div>
</body>
</html>


