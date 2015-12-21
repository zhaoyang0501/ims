<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.sysconfig.attencedatareflesh.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script >
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
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
			no_results_text :"没有找到这个员工",
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>考勤数据迁移</h3>
							</div>
							<div class="box well form-inline">
									<label>迁移日期：</label>
									<div class=" input-append date">
										 <input id="startDate" style="width:80px;" type="text" value="" readonly="readonly" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									
									<label  class="control-lableName">~</label>
									<div class=" input-append date">
										 <input id="endDate" style="width:80px;" type="text" value="" readonly="readonly" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<button id="btnCreate" class="btn btn-info right" onclick="$.ims.attencedatareflesh.initSearchDataTable()">
										<i class="icon-search icon-white"></i> 查询
									</button>
							</div>
							<div class="widget-container">
								<table id='dt_attencelog_view'
									class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>迁移日期</th>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>用时(秒)</th>
											<th>迁移条数</th>
											<th>日志</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
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