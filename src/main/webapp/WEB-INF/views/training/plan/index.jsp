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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.plan.js"></script>
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
										<h3>培训详细计划</h3>
									</div>
								</div>
							</div>
						</div>
							
						<div class="row-fluid">
							<div class="box well form-inline">
								<span>计划日期：</span>
								<div class="input-append date">
									<input id="start_date" type="text" value="" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<span >~</span>
								<div class="input-append date">
									<input id="end_date" type="text" value="" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<a class="btn btn-info" type="button" onclick="$.ims.trainingplan.inintTrainingplanDatatable()"><i class="icon-search"></i>查询</a>
							 </div>
						</div>
						 <div class='row-fluid'>
								<a  class="btn btn-success" style="float: right;margin:5px;" type="button" href="${pageContext.request.contextPath}/training/plan/create"><i class="icon-plus"></i>新建计划</a>
								<table id='dt_trainingplan' class=" table-hover responsive table table-striped table-bordered table-condensed table-hover ">
									<thead>
										<tr>
											<th rowspan="2">培训编号</th>
											<th  rowspan="2">来源</th>
											<th  rowspan="2">培训形式</th>
											<th  rowspan="2">培训内容</th>
											<th colspan="9" align="center" style="text-align:center;">计划</th>
											<th colspan="9"  align="center"  style="text-align: center;">实际</th>
											<th  rowspan="2">操作</th>
										</tr>
										<tr>
											<th>开始日期</th>
											<th>结束日期</th>
											<th>课时</th>
											<th>培训地点</th>
											<th>讲师</th>
											<th>参与人员</th>
											<th>预算</th>
											<th>设备器材</th>
											<th>考核方式</th>
											
											<th>开始日期</th>
											<th>结束日期</th>
											<th>课时</th>
											<th>培训地点</th>
											<th>讲师</th>
											<th>参与人员</th>
											<th>预算</th>
											<th>设备器材</th>
											<th>考核方式</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>