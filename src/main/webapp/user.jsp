<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<div class="">
			我的赞
			<c:forEach  var = "rates" items = "${rateslist}">
				<li>${rates.getName()}</li>
			</c:forEach>
		</div>
	</div>
</body>
</html>