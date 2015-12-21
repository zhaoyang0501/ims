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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/echart/echarts-all.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	
	$.ims.common.findAllUser(function(){
		$("#s_pm").chosen({
			allow_single_deselect : true,
			no_results_text : "没有找到这个请假人.",
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
	
});

</script>
<style type="text/css"> 
	.tab-content { overflow: hidden;} 
	.chzn-container .chzn-results{
		max-height:150px;
	}
</style> 

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
					<div class="widget-head  bondi-blue" >
						<h3>请假统计报表</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="tab-widget">
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#dayoff"><i class="icon-edit"></i> 请假统计报表</a></li>
							<li class=""><a href="#pie"><i class="icon-check"></i>各科室请假工时示意图</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="dayoff">
								<div class="box well form-inline">	
									<!-- 查询条件 -->
									<span>请假日期：</span>
									<div class=" input-append date">
									<input id="sstartDate" value="${startDate }" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span></div> ~ 
									<div class=" input-append date">
									<input id="sendDate" value="${endDate }" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
									 <span>请假人员：</span>
									 <select id="s_pm"  data-placeholder="选择请假人员" class="span2">
									 </select>
									 <a  onclick="$.ims.attencereport.initDayoffreportDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
								</div>
								 <table class="responsive table table-striped table-simple table-bordered table-condensed table-hover" id="dayoff_dataTable">
									<thead>
										<tr>
											<th rowspan="2">#</th>
											<th rowspan="2">姓名</th>
											<th rowspan="2">组别</th>
											<th colspan="3" style="text-align: center">请假时间</th>
											<th rowspan="2">假别</th>
											<th rowspan="2">请假事由</th>
										</tr>
										<tr>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>共计(h)</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>	
							</div>
							<div class="tab-pane" id="pie">
								<div class="box well form-inline">	
									<span>请假日期：</span>
								    <div class=" input-append date">
										<input id="rsstartDate" value="${startDate }" style="width: 80px;" type="text" readonly="readonly">
										<span class="add-on"><i class="icon-th"></i></span>
									</div> ~ 
									<div class=" input-append date">
										<input id="rsendDate" value="${endDate }" style="width: 80px;" type="text" readonly="readonly">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>
									<a onclick="$.ims.attencereport.initDayoffReport()" class="btn btn-info"><i class="icon-search"></i>查询</a>							
								</div>
								<div id="deptDayoff" style="width: 1200px;height: 400px;"></div>
							</div>
							<br /><br /><br /><br />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../foot.jsp"%>
</body>
</html>