<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirementgather.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<head>
<script type="text/javascript">
$(function(){
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red !important;
	}
	input[type="text"]{
		margin-bottom: 0px !important;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_requireId" value="${requireId }" />
	<div class="layout">
		<!-- top -->
		<%@ include file="../../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head bondi-blue">
								<h3>培训需求收集</h3>
							</div>
							<div class="widget" style="margin-top:10px;">
								<table class="responsive table table-striped table_bordered_black table-condensed" >
									<thead style="color: #3399ff;">
										<tr>
											<th rowspan="2" width="150px;">培训课题</th>
											<th rowspan="2" width="80px;">培训类型</th>
											<th colspan="2" width="80px;">培训场所</th>
											<th rowspan="2" width="120px;">培训对象</th>
											<th rowspan="2" width="80px;">计划月份</th>
											<th rowspan="2" width="80px;">预计费用</th>
											<th rowspan="2" width="80px;">课时(H)</th>
											<th rowspan="2" width="80px;">机构/讲师</th>
											<th rowspan="2" width="15%;">培训目标</th>
											<th rowspan="2" width="15%;">备注</th>
											<th rowspan="2"></th>
										</tr>
										<tr>
											<th width="40px;">内</th>
											<th width="40px;">外</th>
										</tr>
									</thead>
								</table>
								<div style="margin-top:20px;">
									<button class="btn" onclick="history.go(-1)" class="btn">返回</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../../foot.jsp"%>
	</div>
	
	<div class="modal hide fade" id="requireModal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="require_label"></label>
		</div>
		<div class="modal-body" style="min-height:250px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<table class="responsive table table-striped table_bordered_black table-condensed" >
							<thead>
								<tr>
									<th rowspan="2">培训课题</th>
									<th rowspan="2">培训类型</th>
									<th rowspan="2">培训对象</th>
									<th rowspan="2">计划月份</th>
									<th rowspan="2">预计费用</th>
									<th rowspan="2">课时(H)</th>
									<th colspan="2">培训场所</th>
									<th rowspan="2">机构/讲师</th>
									<th rowspan="2">培训目标</th>
									<th rowspan="2">备注</th>
									<th rowspan="2"></th>
								</tr>
								<tr>
									<th width="40px;">内</th>
									<th width="40px;">外</th>
								</tr>
							</thead>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>