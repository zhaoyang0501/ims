<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.viewdailyreport.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script >
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<head>
<script type="text/javascript">
$(function(){

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
	
	$.ims.common.findScopeUser(function(){
		$("#user").chosen({
			no_results_text :"没有找到",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"user");
	$("#type").chosen({
		no_results_text : " ",
		placeholder_text:" ",
		disable_search_threshold : 5
	});
	$("#project").chosen({
		no_results_text : " ",
		placeholder_text:" ",
		disable_search_threshold : 5
	});
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
									<h3>日报查询</h3>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row-fluid ">
							<div class="box well form-inline">
									<label>日报日期：</label>
									<div class=" input-append date">
										 <input id="startDate" style="width:80px;" type="text" value="" readonly="readonly" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									
									<label  class="control-lableName">~</label>
									<div class=" input-append date">
										 <input id="endDate" style="width:80px;" type="text" value="" readonly="readonly" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<label  class="control-lableName">日报类型：</label>
									 <select id='type' style="width:120px;" >
										<option value="">&nbsp;</option>  
										<c:forEach items="${dailyreportTypes}" var="bean">
											<option value="${bean.key}">${bean.value}</option>   
										</c:forEach>
									 </select>
									<label  class="control-lableName">员工：</label>
									 <select id="user" style="width:120px;">
									  	<option></option>
									 </select>
								 	<label class="control-lableName">项目：</label>
							 		<select id="project" style="width:150px;">
									 	<option value="">&nbsp;</option>  
									 	<c:forEach items="${projects}" var="bean">
												<option value="${bean.id}">${bean.projectName}</option>   
											</c:forEach>
									</select>
									
									<button id="btnCreate" class="btn btn-info right" onclick="$.ims.dailyReportView.initSearchDataTable()">
										<i class="icon-search icon-white"></i> 查询
									</button>
							</div>
							<table id='dt_dailyReport_view'
									class="table-hover responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>日报日期</th>
											<th>类型</th>
											<th>项目</th>
											<th>日报详情</th>
											<th>异常情况/工作难点</th>
											<th>工时(时)</th>
											<th>工作阶段</th>
											<th>创建日期</th>
											<th >创建者</th>
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