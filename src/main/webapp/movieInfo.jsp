<%@page import="com.bean.Movie" %>
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
<div class="container">
    <div class="col-sm-3 col-md-7">
        <div class="row">
            <div class="col-sm-3 col-md-3">
                <div class="thumbnail">
                    <img src="${movie.getPictures()}" alt="...">
                    <div class="caption">
                        <h3>${movie.getName()}</h3>
                        <p>
                            forEach var="tag" items="${fn:split(movie.genere,'|')}">
                                <span class="label label-default">${tag}</span>
                            </c:forEach>
                        </p>
                        <a href="##" class="btn btn-warning btn-xs" role="button" id="like">点赞</a>
                    </div>
                    <div>
                        <span>评分</span>
                        <select id="select_score">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                        <button id="submit">确定</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 打分
    $("#submit").on("click", function () {
        var options=$("#select_score option:selected")
        var score = options.val()

        $.post('/recommend3_war_exploded/score', {
            userID: ${sessionScope.user.userid},
            movieID: ${movie.getMovieid()},
            score: score
        }).done(function (data) {
            if (data > 0) {
                alert('打分成功！')
            } else {
                alert('服务器开小差了，一会儿再试试吧！')
            }
        })
    })

    // 点赞
    $("#like").on("click", function () {
        $.post('/recommend3_war_exploded/calRate', {
            userID: ${sessionScope.user.userid},
            movieID: ${movie.getMovieid()},
        }).done(function (data) {
            if (data == 1) {
                $("#like").attr("class", "btn btn-success btn-xs")
                $("#like").text("取消")
                alert('点赞成功！')
            } else if (data == 0) {
                $("#like").attr("class", "btn btn-warning btn-xs")
                $("#like").text("点赞")
                alert('取消成功！')
            } else {
                alert('服务器开小差了，一会儿再试试吧！')
            }
        })
    })
</script>
</body>
</html>