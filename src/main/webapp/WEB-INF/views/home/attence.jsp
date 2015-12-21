<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.home.attence.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('.date').datetimepicker({
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
	$(".chosen").chosen();
});
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<!-- 我的考勤 所属年月 加班、请假合计 调休时间 -->
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<!--  -->
							<div class="widget-head  bondi-blue">
								<h3>我的考勤</h3>
							</div>
							<div class="widget-container">
								<div class="left-stripe form-horizontal" style="margin-left: 10px; margin-top:20px;">
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">上月结余：</label>
										<div class="controls">
											<input type="text" value="${attence.lastmonthRemain }" readonly="readonly" />
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">本月新增：</label>
										<div class="controls">
											<input type="text" value="${attence.currentmonthIncrease }" readonly="readonly" />
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">本月减少：</label>
										<div class="controls">
											<input type="text" value="${attence.currentmonthMiuns }" readonly="readonly" />
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">剩余调休：</label>
										<div class="controls">
											<span class="badge badge-warning">${attence.hours }</span>
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">绩效加班：</label>
										<div class="controls">
											<span class="badge badge-warning">${attence.hours }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="widget-container">
								<div class="widget-header-block">
									<h4 class="widget-header text-warning"> 考勤明细</h4>
								</div>
								<div class="box well form-inline">
									<span>选择类型：</span>
									<select id="type" class="span2 chosen" data-placeholder="考勤类型">
										<option value=""></option>
										<option value="1">请假</option>
										<option value="2">加班</option>
										<option value="3">漏打卡</option>
										<option value="4">出差</option>
									</select>
									<span>考勤日期：</span>
									<div class=" input-append date">
									 <input id="fromDate" style="width:120px;" type="text" value="" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<label  class="control-lableName">~</label>
									<div class=" input-append date">
										 <input  id="toDate" style="width:120px;" type="text" value="" >
										 <span   class="add-on"><i class="icon-th"></i></span>
									</div>
									<a onclick="$.ims.homeattence.initAttenceDataTable()" class="btn btn-info">查询</a>													
								</div>
								<table class="responsive table table-striped tbl-simple table-bordered table-condensed" id="attenceDataTable">
									<thead>
										<tr>
											<th>#</th>
											<th>类型</th>
											<th>日期时间</th>
											<th>明细</th>
											<th>概述</th>
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
		<%@ include file="../foot.jsp"%>
	</div>
</body>
</html>