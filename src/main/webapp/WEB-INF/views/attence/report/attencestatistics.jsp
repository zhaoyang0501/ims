<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head> 
	<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.attence.report.js?1=1"></script>
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
	    format:'yyyy-mm',
		autoclose: 1,
		todayHighlight: 1,
		startView: 3,
		minView: 3,
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
								<h3>请假时数统计表</h3>
							</div>
							<div class="widget-container">
							<div class="alert alert-success">
								<button type="button" class="close" data-dismiss="alert">×</button>
								<i class="icon-ok-sign"></i><strong>提示!</strong> 报表最后更新时间：  ${lastupdatedate }
							</div>
								<div class="box well form-inline">	
									<!-- 查询条件 -->
									<span>截止日期：</span>
									<div class=" input-append date">
									<input id="sendDate" value='${queryDate}' style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
									 <span>调休人员：</span>
									 <select id="s_pm"  data-placeholder="选择调休人员" class="span2">
									 </select>
									<a  onclick="$.ims.attencereport.initAttenceStatisticsDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>							
								</div>
								<table class="responsive table table-striped tbl-simple table-bordered table-condensed table-hover" id="attenceStatistics_dataTable">
									<thead>
										<tr>
											<th rowspan="2">姓名</th>
										    <th rowspan="2">部门</th>
											<th colspan="4" style="text-align: center">调休时数(H)</th>
											<th  colspan="4" style="text-align: center">年休假</th>
											<th rowspan="2">事假</th>
											<th rowspan="2">病假</th>
											<th rowspan="2">婚假</th>
											<th rowspan="2">产检假</th>
											<th rowspan="2">产假</th>
											<th rowspan="2">哺乳假</th>
											<th rowspan="2">陪产假</th>
											<th rowspan="2">丧假</th>
											<th rowspan="2">绩效加班</th>
										</tr>
										<tr>
											<th>上月结余</th>
											<th>本月新增</th>
											<th>本月减少</th>
											<th>本月剩余</th>
											<th>去年结余</th>
											<th>本年新增</th>
											<th>本月减少</th>
											<th>本年剩余</th>
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
		<%@ include file="../../foot.jsp"%>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/echart/echarts-all.js"></script>
	<script type="text/javascript">
	
	</script>
</body>
</html>