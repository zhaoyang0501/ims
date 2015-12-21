<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.trainingplan.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
	});
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>培训信息查询</h3>
							</div>
							<div class="box well form-inline">
								<span style="margin-left: 20px;">培训日期：</span>
								<input id="from_date" type="text" class="span1">~
								<input id="to_date" type="text" class="span1">
								<span style="margin-left: 20px;">讲师：</span>
								<input id="user" type="text" class="span1">
								<span style="margin-left: 20px;">讲师级别：</span>
								<input id="user_type" type="text" class="span1">
								
								<a style="margin-left: 20px;" onclick="$.ims.trainingReport.initTrainingplan(1,1,1)" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
							</div>
							<div class="widget-container">
								<table class="responsive table table-striped table-bordered"
									id="trainingreport_dataTable">
									<thead>
										<tr>
											<th style="width: 100px;">培训编号</th>
											<th style="width: 200px;">培训课题</th>
											<th style="width: 100px;">培训方式</th>
											<th style="width: 100px;">讲师/培训机构</th>
											<th style="width: 100px;">讲师级别</th>
											<th style="width: 100px;">培训日期</th>
											<th style="width: 100px;">培训时间</th>
											<th style="width: 100px;">培训时长</th>
											<th>备注</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>
	
</body>
</html>