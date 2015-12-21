<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/ims/ims.home.dailyreport.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/echart/echarts-all.js"></script>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#pie_div").hide();
		$(".date").datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			format : 'yyyy-mm-dd',
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
	});
	
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>

		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue">
								<h3>我的日报</h3>
							</div>
							<div class="row-fluid">
								<div class="tab-widget">
									<ul class="nav nav-tabs" id="myTab2">
										<li class="active"><a href="#dailyreport1"><i class="icon-edit"></i> 日报</a></li>
										<li class=""><a href="#weekreport"><i class="icon-check"></i>周报</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="dailyreport1">
											<div class="box well form-inline">
												<label>日报日期：</label>
												<div class=" input-append date">
													<input id="daily_startDate" value="${fromDate }" style="width: 120px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div>
												<label class="control-lableName">~</label>
												<div class=" input-append date">
													<input id="daily_endDate" value="${toDate }" style="width: 120px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div>
												<button class="btn btn-info right" style="margin-left: 50px;" type="button" onclick="$.ims.homedailyreport.initDailyReport()">
													<i class="icon-search icon-white"></i> 查询
												</button>
											</div>
											<table id='dt_dailyreport' class="responsive table table-striped table-bordered table-condensed">
												<thead>
													<tr>
														<th width="100px;">日报日期</th>
														<th width="60px;">类型</th>
														<th width="100px;">项目</th>
														<th>日报详情</th>
														<th>异常情况/工作难点</th>
														<th width="70px;">工时(时)</th>
														<th width="100px;">创建日期</th>
													</tr>
												</thead>
											</table>
											<div id="pie_div" class="box" style="background-color: gray;">
												<hr />
												<div id="pie_dailyreport" style="width: 600px;height: 500px; border: 1px;" class="pull-left span5"></div>
												<div class="span4">
													<table id='dt_dailyreport_summary' class="responsive table table-striped table-bordered table-condensed">
														<thead>
															<tr >
																<th style="width:100px;text-align: center;">类型</th>
																<th style="width:100px;text-align: center;">工时(时)</th>
																<th style="width:100px;text-align: center;">比重</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>
										</div>
										<div class="tab-pane" id="weekreport">
											<div class="box well form-inline">
												<label>收到日期：</label>
												<div class=" input-append date">
													<input id="week_startDate" value="${fromDate }" style="width: 120px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div>
												<label class="control-lableName">~</label>
												<div class=" input-append date">
													<input id="week_endDate" value="${toDate }" style="width: 120px;" type="text" readonly="readonly">
													<span class="add-on"><i class="icon-th"></i></span>
												</div>
												<button class="btn btn-info right" style="margin-left: 50px;" type="button" onclick="$.ims.homedailyreport.initWeekReport()">
													<i class="icon-search icon-white"></i> 查询
												</button>
											</div>
											<table id='dt_weekreport' class="responsive table table-striped table-bordered table-condensed">
												<thead>
													<tr>
														<th>周次</th>
														<th>开始日期</th>
														<th>结束日期</th>
														<th>提交日期</th>
														<th>审批意见</th>
														<th>退回次数</th>
														<th>状态</th>
														<th>所在节点</th>
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
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>

	<script type="text/javascript">
// 		$.ims.homedailyreport.initDailyReportPie();
	</script>
</body>
</html>