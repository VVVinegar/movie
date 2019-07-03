<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>recommend system</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <style>
        body {
            padding-top: 70px;
        }

        * {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        h3 {
            font-size: 18px;
            margin-top: 10px;
        }

        .margin-bottom {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
            </button>
            <a class="navbar-brand"
               href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Action&page=1">电影推荐</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/recommend?userID=${sessionScope.user.userid}">个人推荐</a></li>
                <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/userInfo">个人中心</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/login.jsp">登陆</a></li>
                        <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/login.jsp">注册</a></li>
                    </c:when>
                    <c:when test="${!empty sessionScope.user}">
                        <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/login.jsp">注销</a></li>
                    </c:when>
                </c:choose>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<%--数据计算推荐--%>
<div class="container">
    <div class="col-sm-3 col-md-7">
        <div class="row">
            <c:forEach var="userScore" items="${userScores}">
                <div class="col-sm-3 col-md-3">
                    <div class="thumbnail">
                        <div class="caption movie" hidden-movie-id="${userScore.getMovieid()}">
                            <h3></h3>
                            <span>推荐值：${userScore.getScore()}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<%--点赞推荐--%>
<div class="container">
    <div class="col-sm-3 col-md-7">
        <h3>这些是你偏爱的电影：</h3>
        <div class="row" id="like-recommend">
            <%-- js deal --%>
        </div>
    </div>
</div>

<script>
    // 渲染结束后，初始化数据计算得出的推荐
    window.onload = function () {
        $(".movie").each(function () {
            self = $(this)
            $.ajax({
                url: "/recommend3_war_exploded/movieInfoApi",
                dataType: "json",
                async: false,	// 同步
                data: {"movieID": self.attr("hidden-movie-id")},
                type: "GET",   //请求方式
                success: function (data) {
                    self.children("h3").text(data.name);
                },
                error: function(){
                    alert("wrong")
                }
            });
        });
    }

    // 初始化点赞得出的推荐
    $(function () {
        var father = $("#like-recommend");
        $.ajax({
            url: "/recommend3_war_exploded/recommendByLike",
            dataType: "json",
            async: true,
            data: {userID: ${sessionScope.user.userid}},
            type: "GET",   //请求方式
            success: function (data) {
                var html = ""
                for (var index in data) {
                    html += "<h4>"+data[index]['name']+"</h4>"
                }
                father.html(html)
            },
            error: function(){
                alert("wrong")
            }
        });

    })
</script>
</body>
</html>