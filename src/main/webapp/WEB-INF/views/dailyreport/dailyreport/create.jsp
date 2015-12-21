<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<html lang="ch">
<head>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.dailyreport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript">
	$.validator.addMethod("checkproject",function(value,element,params){  
		if(($("#type").val()=='1'||$("#type").val()=='7')&&$("#project").val()=='')
			return false;
		else return true;
	},"请选择项目");
	
	$.validator.addMethod("checkprojectStep",function(value,element,params){  
		 if(($("#type").val()=='1'||$("#type").val()=='7')&&$("#projectStep").val()=='')
			return false;
		else return true;
	},"请选择工作阶段");
	
	$(document).ready(function() {
		$.ims.dailyReportCreate.initIsCanEdit("${reportDate}");
		$.ims.dailyReportCreate.initDailyReport("${reportDate}");
		$.ims.dailyReportCreate.initProjectSelect();
		$.ims.dailyReportCreate.dailyreportForm = $("#dailyreport_form").validate({
			errorPlacement: function(error, element) {
						$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				type : "required",
				spendHours : "required",
				projectStep : "checkprojectStep",
				summary : "required",
				project:"checkproject"
			},
			messages : {
				type : "请选择日报类型",
				summary : "请输入日报详情"
			}
		});

		$(".chosen").chosen({
			no_results_text : " ",
			placeholder_text : " ",
			disable_search_threshold : 5
		});
		
	});
</script>
<style type="text/css">
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
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue">
								<h3>日报填写</h3>
							</div>
							<div class="container-fluid">
								<h3 class="badge badge-info" style="margin-top:10px;">${reportDate}日报</h3>
								<button id="btnCreate" class="btn btn-success pull-right" onclick="$.ims.dailyReportCreate.showNewDailyReportModal()" style="margin:5px;">
									<i class='icon-plus'></i> 新增日报
								</button>
								<table id='table_dailyReport_create' class="table-hover  responsive table table-striped table-bordered">
									<thead>
										<tr>
											<th>ID</th>
											<th>日报日期</th>
											<th>类型</th>
											<th>项目</th>
											<th>日报详情</th>
											<th>异常/难点</th>
											<th>工时(时)</th>
											<th>工作阶段</th>
											<th>创建日期</th>
											<th class="center">操作</th>
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
	<!-- 新增日志 -->
	<input type="hidden" id="selDate" name="selDate" value="${selDate}"></input>
	<div class="modal hide fade" id="myDailyReport">
		<div class="modal-header blue">
			日报填写
			<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height: 300px;">
			<div class="row-fluid">
				<div class="span12">
					<div class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id='dailyreport_form'>

							<div class="control-group">
								<label for="type" class="control-label">日报类型：</label>
								<div class="controls">
									<select id="type" name="type" onchange="$.ims.dailyReportCreate.dailyReportTypeChange();" class="chosen">
										<option value=""></option>
										<c:forEach items="${dailyreportTypes}" var="bean">
											<option value="${bean.key}">${bean.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group" id='control_project'>
								<label for="project" class="control-label">项目名称：</label>
								<div class="controls">
									<select id="project" name="project">
										<option value=""></option>
									</select>
								</div>
							</div>
							<div class="control-group" id='control_projectStep'>
								<label for="projectStep" class="control-label">工作阶段：</label>
								<div class="controls">
									<select id="projectStep" name="projectStep" class="chosen">
										<option value=""></option>
										<c:forEach items="${workSteps}" var="bean">
											<option value="${bean.key}">${bean.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group" id='control_spendhours'>
								<label for="spendHours" class="control-label">工时：
								</label>
								<div class="controls">
									<select id="spendHours" name="spendHours" class="chosen">
										<option id='minhour' value="0.5" selected="selected">0.5</option>
										<option value="1">1</option>
										<option value="1.5">1.5</option>
										<option value="2">2</option>
										<option value="2.5">2.5</option>
										<option value="3">3</option>
										<option value="3.5">3.5</option>
										<option value="4">4</option>
										<option value="4.5">4.5</option>
										<option value="5">5</option>
										<option value="5.5">5.5</option>
										<option value="6">6</option>
										<option value="6.5">6.5</option>
										<option value="7">7</option>
										<option value="7.5">7.5</option>
										<option value="8">8</option>
										<option value="8.5">8.5</option>
										<option value="9">9</option>
										<option value="9.5">9.5</option>
										<option value="10">10</option>
										<option value="10.5">10.5</option>
										<option value="11">11</option>
										<option value="11.5">11.5</option>
										<option value="12">12</option>
										<option value="12.5">12.5</option>
										<option value="13">13</option>
										<option value="13.5">13.5</option>
										<option value="14">14</option>
										<option value="14.5">14.5</option>
										<option value="15">15</option>
										<option value="15.5">15.5</option>
										<option value="16">16</option>
										<option value="16.5">16.5</option>
										<option value="17">17</option>
										<option value="17.5">17.5</option>
										<option value="18">18</option>
										<option value="18.5">18.5</option>
										<option value="19">19</option>
										<option value="19.5">19.5</option>
										<option value="20">20</option>
										<option value="20.5">20.5</option>
										<option value="21">21</option>
										<option value="21.5">21.5</option>
										<option value="22">22</option>
										<option value="22.5">22.5</option>
										<option value="23">23</option>
										<option value="23.5">23.5</option>
										<option value="24">24</option>
									</select>
								</div>
							</div>
							<div class="control-group" id='control_summary'>
								<label for="summary" class="control-label">日报详情：
								</label>
								<div class="controls">
									<textarea class="span10" rows="3" name="summary" id="summary"></textarea>
								</div>
							</div>
							<div class="control-group" id='control_difficulty'>
								<label for="difficulty" class="control-label">异常情况/工作难点：</label>
								<div class="controls">
									<textarea class="span10" id="difficulty" rows="3" name="difficulty"></textarea>
								</div>
							</div>
							<div class="control-group">
								<span  style="color: red;">提示：如有加班，日报类型请务必填写“加班”否则影响餐费报销！</span>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<button class="btn btn-primary" id="submit_buttion" onclick="$.ims.dailyReportCreate.saveOrUpdateDailyReport()">保存</button> <a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	<input type="hidden" id="reportDate" value="${reportDate}"></input>
	<!-- 表单验证错误提示 -->
	<div id='formerror_warp'>
		<div class='formError'>
			<div class='formErrorContent'></div>
			<div class='formErrorArrow'>
				<div class='line10'></div>
				<div class='line9'></div>
				<div class='line8'></div>
				<div class='line7'></div>
				<div class='line6'></div>
				<div class='line5'></div>
				<div class='line4'></div>
				<div class='line3'></div>
				<div class='line2'></div>
				<div class='line1'></div>
			</div>
		</div>
	</div>
</body>
</html>