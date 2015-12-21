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
	//
	$.ims.common.findAllUser(function(){
		$("#s_pm").chosen({
			allow_single_deselect : true,
			no_results_text : "没有找到这个漏刷卡人.",
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
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>漏刷卡统计报表</h3>
							</div>
							<div class="tab-widget">
								<ul class="nav nav-tabs" id="myTab2">
									<li class="active"><a href="#absentee"><i class="icon-edit"></i>员工漏刷卡统计报表</a></li>
									<li class=""><a href="#pie"><i class="icon-check"></i>各科室漏打卡次数示意图</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="absentee">
										<div class="box well form-inline">
											<span>漏刷卡日期：</span>
											<div class=" input-append date">
											<input id="sstartDate" value="${startDate }" style="width: 80px;" type="text" readonly="readonly">
											<span class="add-on"><i class="icon-th"></i></span></div> ~ 
											<div class=" input-append date">
											<input id="sendDate" value="${endDate }" style="width: 80px;" type="text" readonly="readonly">
											<span class="add-on"><i class="icon-th"></i></span>
											</div>
											 <span>漏刷卡人员：</span>
											 <select id="s_pm"  data-placeholder="选择漏刷卡人员" class="span2">
											 </select>
											<a  onclick="$.ims.attencereport.initAbsenteereportDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
										</div>
										<table class="responsive table table-striped tbl-simple table-bordered  table-hover" id="absentee_dataTable">
											<thead>
												<tr>
												    <th>#</th>
													<th>姓名</th>
													<th>组别</th>
													<th>漏刷卡日期</th>
													<th>漏刷卡时间</th>
													<th>漏刷卡类别</th>
													<th>漏刷卡原因</th>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
									<div class="tab-pane" id="pie">
										<div class="box well form-inline">
											<span>漏打卡日期：</span>
											   	<div class=" input-append date">
													<input id="rsstartDate" value="${startDate }" style="width: 80px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div> ~ 
												<div class=" input-append date">
													<input id="rsendDate" value="${endDate }" style="width: 80px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div>
												<a onclick="$.ims.attencereport.initAbsenteeReport()" class="btn btn-info"><i class="icon-search"></i>查询</a>							
										</div>
										<div id="deptAbsentee" style="width: 1200px;height: 500px;"></div>
									</div>
									<br /><br /><br /><br />
								</div>
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