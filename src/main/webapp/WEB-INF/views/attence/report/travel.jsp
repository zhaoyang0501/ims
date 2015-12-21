<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head> 
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.attence.report.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	//
	$.ims.common.findAllUser(function(){
		$("#s_pm").chosen({
			allow_single_deselect : true,
			no_results_text : "没有找到.",
			disable_search_threshold : 5,
			enable_split_word_search : true,
			search_contains : true
		});
	}, "s_pm");
	
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
	$.ims.attencereport.initTravelreportDataTable()
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
								<h3>出差统计报表</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">	
									<!-- 查询条件 -->
									<span>出差日期：</span>
								    <div class=" input-append date">
									<input id="sstartDate" value="${startDate }" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span></div> ~ 
									<div class=" input-append date">
									<input id="sendDate" value="${endDate }" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
									 <span>出差人员：</span>
									 <select id="s_pm"  data-placeholder="选择出差人员" class="span2">
									 </select>
									<a  onclick="$.ims.attencereport.initTravelreportDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>							
								</div>
								<table class="responsive table table-striped tbl-simple table-bordered table-condensed table-hover" id="travel_dataTable">
									<thead>
										<tr>
										    <th rowspan="2">#</th>
											<th rowspan="2">姓名</th>
											<th colspan="2" style="text-align: center">出差时间</th>
											<th colspan="2" style="text-align: center">返回时间</th>
											<th rowspan="2">出差地点</th>
											<th rowspan="2">出差原因</th>
										</tr>
										<tr>
											<th>出差日期</th>
											<th>时间状态</th>
											<th>返回日期</th>
											<th>时间状态</th>
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