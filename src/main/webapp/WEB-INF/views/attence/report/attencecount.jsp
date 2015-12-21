<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head> 

<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.attence.report.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>

<script type="text/javascript">
$(function () {
	
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
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>部门每月考勤报表</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">	
									<!-- 查询条件 -->
									<span>查询日期：</span>
									<div class=" input-append date">
									<input id="startDate"  style="width: 80px;" value="${firstDayOfMonth}" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span></div> ~ 
									<div class=" input-append date">
									<input id="endDate"  style="width: 80px;" value="${lastDayOfMonth}" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
									<a onclick="$.ims.attencereport.initAttenceReport();" class="btn btn-info"><i class="icon-search"></i>查询</a>							
								</div>
								<table class="responsive table table-striped tbl-simple table-bordered table-condensed table-hover" id="attencereportDataTable">
									<thead>
										<tr>
											<th rowspan="2">部门</th>
											<th colspan="3" style="text-align: center;">加班(H)</th>
											<th colspan="4" style="text-align: center;">请假(H)</th>
											<th rowspan="2">调休(H)</th>
											<th rowspan="2">补签卡(人次)</th>
										</tr>
										<tr>
											<th>平时</th>
											<th>周末</th>
											<th>假日</th>
											<th>事假(H)</th>
											<th>产假(H)</th>
											<th>哺乳假(H)</th>
											<th>看护假(H)</th>
										</tr>
									</thead>
									<tbody></tbody>
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