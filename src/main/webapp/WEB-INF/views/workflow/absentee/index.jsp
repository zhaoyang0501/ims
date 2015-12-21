<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.absentee.js"></script>
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
	$.ims.workflowabsentee.initAbsenteeDataTable();
	
	$('.date').datetimepicker({
		language : 'zh-CN',
		autoclose : 1,
		startView : 2,
		minView : 2,
		format : "yyyy-mm-dd"
	});
	
	$(".chosen").chosen({
		allow_single_deselect : true,
	});
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_cuid" value="${cuid }" />
	<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
						  <div class="row-fluid ">
									<div class="span12">
										<div class="content-widgets">
											<div class="widget-head  bondi-blue">
												<h3>补打卡申请</h3>
											</div>
										</div>
									</div>
							</div>
							
							<div class="row-fluid">
								<div class="box well form-inline">
									<span>查询日期：</span>
									<div class="input-append date">
										<input id="begin" type="text" value="" readonly="readonly" style="width:80px;">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>
									<span >~</span>
									<div class="input-append date">
										<input id="end" type="text" value="" readonly="readonly" style="width:80px;">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>
									
									<label  class="control-lableName">流程状态：</label>
									 <select id='state'  style="width:120px;" class="chosen">
									  	<option value="">&nbsp;</option>
										 <option value="1">审批中</option>
										 <option value="4">已结束</option>
									 </select>
									
									<a class="btn btn-info" type="button" onclick="$.ims.workflowabsentee.initAbsenteeDataTable()"><i class="icon-search"></i>查询</a>
								</div>
							</div>
							
							<div class="row-fluid">
								<a class="btn btn-success" style="float: right;margin:5px;" type="button" href="workflow/absentee/create"><i class="icon-plus"></i>填写漏打卡单据</a>
								<table id='absenteeDataTable' class=" table-hover responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>提交人</th>
											<th>发送日期</th>
											<th>状态</th>
											<th>当前步骤</th>
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>