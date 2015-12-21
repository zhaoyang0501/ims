<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.attence.overtime.js?t=715"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
  <script src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/moment.min.js"></script>
<head>
<script type="text/javascript">
/**表单验证**/
$.validator.setDefaults({
	showErrors: function(map, list) {
		$("form .formError").remove();
			$.each(list, function(index, error) {
				$("#formerror_warp .formErrorContent").html("");
				$("#formerror_warp .formErrorContent").append( error.message+"<br/>");
				$(error.element).before($("#formerror_warp  .formError").clone()).parent().css("position","relative");
				$("form .formError").css("top",-$("form .formError").height());
			});
}}); 
$(function(){
	$.ims.common.findAllUser(function(){
		$("#user").chosen({
			no_results_text :"没有找到这个员工",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"user");
	 $("#state").chosen({
			no_results_text : " ",
			placeholder_text:" ",
			disable_search_threshold : 5
		});
	 $("#oaState_").chosen({
			no_results_text : " ",
			placeholder_text:" ",
			disable_search_threshold : 5
		});
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
	$(".modal_datetime").datetimepicker({
		language:  'zh-CN',
	    weekStart: 1,
	    todayBtn:  1,
	    format:'hh:ii',
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minuteStep:30,
		minView: 0,
		forceParse: 0
    });
	$.ims.attenceOvertime.overtimeForm= $("#overtime_form").validate({
		debug:true,
		rules: {
			type:  "required",
			startTime:  "required",
			checkHours:{required:true,number:true,range:[0,24]},
			endTime:  "required",
			summary:  "required"
			},
		messages: {
			type:"请选择加班类型",
			startTime:"请选择开始结束时间",
			checkHours:{required:"请输入核对工时",number:"请输入数字",range:"工时必须介于0-24之间" },
			endTime:{required:"请选择开始结束时间"}
		}
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
											<h3>加班工时核对</h3>
											</div>
										</div>
									</div>
				</div>
							
							
				<div class="row-fluid ">
							<div class="box well form-inline">
								<label class="control-lableName">加班日期：</label>
								<div class="input-append date">
									 <input id="startDate" style="width:80px;" type="text" value="" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
								<label  class="control-lableName">~</label>
								<div class=" input-append date">
									 <input id="endDate" style="width:80px;" type="text" value="" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
								<label  class="control-lableName">状态：</label>
								 <select id='state'  style="width:120px;">
								  <option value="">&nbsp;</option>
									 <option value="1">已核对</option>
									 <option value="2">未核对</option>
								 </select>
								 <label  class="control-lableName">OA状态：</label>
								 <select id='oaState_'  style="width:120px;">
								  <option value="">&nbsp;</option>
									 <option value="1">已提交</option>
									 <option value="2">未提交</option>
								 </select>
								<label  class="control-lableName">员工：</label>
								 <select id="user" style="width:120px;">
									 <option></option>
								 </select>
								<button id="btnCreate" class="btn btn-info right" type="button" onclick="$.ims.attenceOvertime.initSearchDataTable()">
								<i class="icon-search icon-white"></i> 查询
								</button>
							</div>
						</div>
						<div class="row-fluid ">
								<table id='dt_attenceovertime_view'
									class="responsive table table-bordered table-condensed table-hover " style="background-color: white;">
										<thead>
											<tr>
												<th>加班日期</th>
												<th width="7%">部门</th>
												<th width="45px;">员工</th>
												<th width="60px;">工号</th>
												<th>加班报告日期</th>
												<th width="12%">项目</th>
												<th width="20%">加班事由</th>
												<th width="3%">加班工时</th>
												<th>当天刷卡记录</th>
												<th>核对加班时间</th>
												<th width="3%">核对工时</th>
												<th width="40px;">状态</th>
												<th width="50px;">OA状态</th>
												<th width="95px;">操作</th>
											</tr>
										</thead>
								</table>
							</div>
						</div>
					</div>
				
		<%@ include file="../../foot.jsp"%>
	</div>
	<!-- 弹出框 工时核对 -->
		<div class="modal hide fade" id="timecheckmodal">
		<div class="modal-header blue">工时核对<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height: 200px;padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id='overtime_form'>
						<input type="hidden" id="overTimeId" name='overTimeId' value="">
						<div class="control-group">
								<label for="type" class="control-label"><span>*</span>刷卡记录</label>
								<div class="controls">
									<input type="text"  id="brushData" name="brushData" class="span8" readonly="readonly" >
								</div>
							</div>
							<div class="control-group">
								<label for="type" class="control-label"><span>*</span>实际加班时间</label>
								<div class="controls">
									<input type="text" id="startTime" name="startTime" class="span4 modal_datetime" readonly="readonly">
									<input type="text" id="endTime" name="endTime"  class="span4 modal_datetime" readonly="readonly">
								</div>
							</div>
							<div class="control-group">
								<label for="type" class="control-label"><span>*</span>工时合计</label>
								<div class="controls">
									<input type="text"  id="checkHours" name="checkHours" class="span8"  >
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span>*</span>类型</label>
								<div class="controls">
									<select id='type' name="type" class='span8'>
										<option value="1">平时</option>
										<option value="2">周末</option>
										<option value="3">节假日</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label for="oaState" class="control-label"><span>*</span>OA提交状态</label>
								<div class="controls">
									<select id='oaState' name="oaState" class='span8'>
										<option value="1">已提交</option>
										<option value="2">未提交</option>
									</select>
								</div>
							</div>
							
							<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
									<label style="color: red;" id='overtime_tip'></label>
							</div>
								
							</div>
							
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary" onclick="$.ims.attenceOvertime.saveCheckTime()">保存</a>
			<a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	<!-- 弹出框结束 -->
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