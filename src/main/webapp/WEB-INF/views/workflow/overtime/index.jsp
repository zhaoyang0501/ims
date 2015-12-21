<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.overtime.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<head>
<script type="text/javascript">
$(document).ready(function() {
	$(".date").datetimepicker({
		language:  'zh-CN',
	    weekStart: 1,
	    todayBtn:  1,
	    format:'yyyy-mm-dd',
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$("#state").chosen({
		allow_single_deselect : true
	});
	<c:if test="${response!=null&&response.code=='1'}">
	noty({"text":"操作成功","layout":"top","type":"success","timeout":"2000"});
	</c:if>
});
</script>
</head>
<body>
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
											<h3>加班流程</h3>
										</div>
									</div>
								</div>
						</div>
							
						<div class="row-fluid">
							<div class="box well form-inline">
								<span>加班申请日期：</span>
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
								 <select id='state'  style="width:120px;">
								  	<option value="">&nbsp;</option>
									 <option value="1">审批中</option>
									 <option value="4">已结束</option>
								 </select>
								
								<a class="btn btn-info" type="button" onclick="$.ims.workflowovertime.initOvertimeDataTable()"><i class="icon-search"></i>查询</a>
							</div>
							</div>
							
							<div class="row-fluid">
								<a class="btn btn-success" style="float: right;margin:5px;" type="button" href="workflow/overtime/create"><i class="icon-plus"></i>填写加班单</a>
								<table id='overtimeDataTable' class="table-hover responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>工号</th>
											<th>姓名</th>
											<th>加班申请日期</th>
											<th>人数</th>
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