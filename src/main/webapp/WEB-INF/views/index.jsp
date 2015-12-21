<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ch">
<head>
<%@ include file="common/meta.jsp"%>
<link href="${pageContext.request.contextPath}/css/fullcalendar.css" rel= "stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/fullcalendar.min.js"></script>

<link>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : $.ims.getContextPath() + "/getDailyReportToApproveCount",
			dataType : "json",
			success : function(json) {
				if (json.state)
					$("#count_dailyReport").html(json.count);
			}
		});
		$.ajax({
			type : "GET",
			url : $.ims.getContextPath() + "/getRemoteNews/2",
			dataType : "json",
			success : function(result) {
				$("#discuzz_ui").empty();
				$.each(result.news, function(i, n) {
					$("#discuzz_ui").append(
							"<li ><a href='"+n.url+"'><h5>"+ n.title + "</h5></a></li>");
				});
			}
		});
	});
</script>
</head>
<body>
	<div class="layout">
		<!-- Navbar
    ================================================== -->
		<!-- top -->
		<%@ include file="top.jsp"%>

		<!-- 导航 -->
		<%@ include file="menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
<!-- 						<div class="primary-head"> -->
<!-- 							<h3 class="page-header text-warning:hover">航盛电子上海技术中心</h3> -->
<!-- 							<ul class="top-right-toolbar"> -->
<!-- 								<li><a class="dropdown-toggle blue-violate" href="#" title="用户档案"><i class="icon-user"></i></a> -->
<!-- 								</li> -->
<!-- 								<li><a href="#" class="green" title="消息"><i class="icon-comment"></i></a></li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
						<ul class="breadcrumb">
							<li><a href="#" class="icon-home"></a></li>
						</ul>
					</div>
				</div>
				<iframe style="width:100%; height:1200px; border:0" src="./resources/index/index.htm"> </iframe>
				
			</div>
		</div>
		<!-- foot -->
		<%@ include file="foot.jsp"%>
	</div>
</body>
</html>