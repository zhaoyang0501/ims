<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="common/meta.jsp"%>
<head>
</head>
<body>
	<div class="layout">
		<div class="container error-wrapper">
			<div class="row">
				<div class="span4 offset2">
					<div class="error-code">
						error
						<div>
						</div>
					</div>
				</div>
				<div class="span5">
					<div class="error-message">
						<h4>${tip} </h4>
						<p style="color: red" >
							${exception } :(
						</p>
						<ul class="error-suggestion">
							<li>Check to be sure you have a correct username and password. If it's a link, it could be out of date and no longer available on the website. </li>
							<li>Visiting our full website <a href="${pageContext.request.contextPath}/">sitemap here.</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>