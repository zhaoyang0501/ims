<%@ page session="true"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ch">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>综合管理系统</title>

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="./js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="./js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="./html5shiv.min.js"></script>
      <script src="./1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
body {
	background: url(${pageContext.request.contextPath}/images/login-bg.jpg) center 0 no-repeat;
	background-color: #eeedeb;
	background-attachment: fixed;
	padding-top: 10px;
}

.form-signin {
	max-width: 390px;
	padding: 15px;
	padding-top:0px;
	margin: 0 auto;
}

.form-signin-heading{
	text-align: center;
	padding-top: 70px;
	margin-bottom: 20px;
	color:#FFF
}

.form-signin , .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="email"] {
	margin-bottom: -1px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}

#msg.errors {
	border: 1px dotted #BB0000;
	color: #BB0000;
	padding-left: 100px;
	background: url(${pageContext.request.contextPath}/images/error.gif) no-repeat 20px center;
	background-color: rgb(255, 238, 221);
}

#msg {
	padding: 20px;
	margin-bottom: 10px;
}

.warn{
	font-size:13px;
	margin-bottom: 10px;
}

[class^="icon-"],
[class*=" icon-"] {
  display: inline-block;
  width: 14px;
  height: 14px;
  *margin-right: .3em;
  line-height: 14px;
  vertical-align: text-top;
  background-image: url("${pageContext.request.contextPath}/images/glyphicons-halflings.png");
  background-position: 14px 14px;
  background-repeat: no-repeat;
}

.icon-user {
  background-position: -168px 0;
}

.icon-lock {
  background-position: -287px -24px;
}


</style>
</head>

<body>

	<div class="container">
		<div>
			<h1 class="form-signin-heading">
				航盛电子上海技术中心综合管理系统
			</h1>
		</div>

		<form class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
			<c:if test="${tip!=null}">
				<div id="msg" class="errors">${tip }</div>
			</c:if>
				<div class="input-group">
					<span class="input-group-addon"><i class="icon-user"></i></span>
					<input id="username" name="username" class="required form-control" tabindex="1" placeholder="用户名" accesskey="n" type="text" value="" autocomplete="false"/>
				</div>
			
			<div class="input-group" style="margin-bottom: 10px;margin-top: 10px;">
				<span class="input-group-addon"><i class="icon-lock"></i></span>
				<input id="password" name="password" class="required form-control" tabindex="2" placeholder="密码" accesskey="p" type="password" value="" autocomplete="off"/>
			</div>
			<input class="btn btn-lg btn-primary btn-block" name="submit" accesskey="l" value="登录" tabindex="4" type="submit" />
		</form>
	</div>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="./js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
