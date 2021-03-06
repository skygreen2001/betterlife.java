<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<title>美味通信薄</title>
	<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
	body {
	  padding-top: 50px;
	  padding-bottom: 20px;
	}
	</style>
</head>
<body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header" style="width:400px">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">美味通信薄</a>
           <c:if test="${!empty error}"><font color="red"><c:out value="${error}"/></font></c:if>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <form class="navbar-form navbar-right" action="<c:url value="loginCheck"/>" method="post">
            <div class="form-group">
              <input type="text" placeholder="账号" name="userName" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" placeholder="密码" name="password" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">登录</button>
            <input type="reset" class="btn btn-success" value="重置"/>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container" style="height:400px;" align="center">
      	<br/><br/><br/>
        <h1>Hello, betterlife!</h1>
        <p>简洁、直观、强悍的全端开发框架，让Web开发更迅速、简单。</p>
        <p>美味通信薄是使用Betterlife框架编写的采用Spring MVC+Postgresql+tiles+Bootstrap编写的范例</p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">更多 &raquo;</a></p>
      </div>
    </div>
	<div class="container"> 
		<footer>
        	<p>&copy; 周月璞写于上海 2015</p>
      	</footer>
	</div>
</body>
</html>