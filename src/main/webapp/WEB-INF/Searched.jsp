<%@ page import="ru.itpark.constants.Constants" %>
<%@ page import="ru.itpark.model.Query" %>
<%@ page import="ru.itpark.enumeration.QueryStatus" %>
<%@ page import="java.util.Queue" %>
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


    <title>Результаты поиска</title>
</head>
<body>
<div class="container">


    <div class="mt-0">
        <div class="mx-auto" style="width: 200px;">

            <a href="<%= request.getContextPath()%>/" class="btn btn-primary btn-lg active" role="button"
               aria-pressed="true">TXT Searcher</a>
        </div>
    </div>

    <div class="mt-10">
        <h3 class="display-5">Введите искомую фразу</h3>

        <form action="<%= request.getContextPath()%>/search" method="get" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" class="form-control" value="search">
        </div>
        <div class="form-group">
            <input type="text" placeholder="Введите искомую фразу" class="form-control" name="q">
        </div>
    </form>
    </div>

    <div class="mt-50">
    <form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="hidden" name="action" value="save">
        </div>
    </form>
    </div>

<h3>Результаты</h3>
    <% for (Query query : (Queue<Query>) request.getAttribute(Constants.ITEMS)) {%>
    <div class="card">
        <div class="card-body">

            Поисковый запрос: <%=query.getQuery()%>

            <% if (query.getStatus() == QueryStatus.ENQUEUED) {%>
            <p>Статус: в очереди</p>
            <% }%>
            <% if (query.getStatus() == QueryStatus.INPROGRESS) {%>
            <p>Статус : идет поиск</p>
            <%}%>
            <% if (query.getStatus() == QueryStatus.DONE) {%>
            <div class="row">
                <div class="col-10">
                    Статус: готово
                </div>
                <div class="col">

                    <a href="<%= request.getContextPath()%>/download/<%= query.getId()%>.txt" download="">Скачать
                        результат</a>
                </div>

            </div>
        </div>
    </div>
    <%}%>
    <%}%>


</div>

</body>
</html>