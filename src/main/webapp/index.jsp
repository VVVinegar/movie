<%@page import="com.bean.Movie"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta  http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>recommend system</title>
<link rel="stylesheet"type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"type="text/css" href="css/bootstrap.min.css">
<style>
body { padding-top: 70px; }
* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
h3{
	font-size:18px;
	margin-top:10px;
}
.thumbnail{
	height:340px;
}
.margin-bottom{
	margin-bottom:10px;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	      </button>
	      <a class="navbar-brand" href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Action">电影推荐</a>
	    </div>
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav navbar-left">
	        <li><a href="${PageContext.request.contextPath}/recommend3_war_exploded/tongji.jsp">个人推荐</a></li>
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
			<div class ="margin-bottom" >			 
			 	  <h3>电影分类 <span class="label label-warning"></span></h3>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Action" type="button" class="btn btn-primary">Action</a>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Fantasy" type="button" class="btn btn-primary">Fantasy</a>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Comedy"  type="button" class="btn btn-primary">Comedy</a>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Romance"  type="button" class="btn btn-primary">Romance</a>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Drama"  type="button" class="btn btn-primary">Drama</a>
				  <a href="${PageContext.request.contextPath}/recommend3_war_exploded/showFilms?genere=Drama"  type="button" class="btn btn-primary">Others</a>
			</div>
			<div class="row">
				<c:forEach  var = "movie" items = "${movieslist}">
					 <div class="col-sm-3 col-md-3">
						    <div class="thumbnail">
						      <img src="${movie.getPictures()}" alt="..." >
						      <div class="caption">
						        <h3>${movie.getName()}</h3>
						        <p>
							        <c:forEach var="tag" items="${fn:split(movie.genere,'|')}">
							        	<span class="label label-default">${tag}</span>
							        </c:forEach>
						        </p>
						       
						        <a href="${PageContext.request.contextPath}/recommend3_war_exploded/movieInfo.jsp?movieid=${movie.getMovieid()}" class="btn btn-primary  btn-xs" role="button">查看更多</a>
						       <%--  <c:if test="${empty request.fancy||request.fancy=='0'}">
						        	 <a href="${PageContext.request.contextPath}/recommend3_war_exploded/calRate?userid=${sessionScope.user.userid}&movieid=${movie.getMovieid()}&genere=${param.genere}" class="btn btn-default btn-xs" role="button" >点赞</a>
						        </c:if> --%>  
						        	<a href="${PageContext.request.contextPath}/recommend3_war_exploded/calRate?userid=${sessionScope.user.userid}&movieid=${movie.getMovieid()}&genere=${param.genere}" class="btn btn-warning btn-xs" role="button" >点赞</a>
						       
						      </div>
						    </div>
						  </div>
				</c:forEach>
			</div>	 
		</div>
		<div class="col-sm-7 col-md-5">
			  <h3>电影TOP10<span class="label label-warning"></span></h3>
		</div>
	</div>
</body>
</html>