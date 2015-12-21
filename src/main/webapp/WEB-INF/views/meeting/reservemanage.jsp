<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head> 
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.meeting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script> 
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>


<script type="text/javascript">
$(document).ready(function(){	
	
	$("#startTime").datetimepicker({
			language : 'zh-CN',
			autoclose : 1,
			startView : 0,
			minView : 0,
			maxView : 0,
			minuteStep :30,
			format : "hh:ii",
		});
	
	
	$("#endTime").datetimepicker({
			language : 'zh-CN',
			autoclose : 1,
			startView : 0,
			minView : 0,
			maxView : 0,
			minuteStep :30,
			format : "hh:ii",
		});
	
	$('#startDate').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		format : 'yyyy-mm-dd',
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
			maxView : 2
	});
	
	$('.date').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		format : 'yyyy-mm-dd',
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
			maxView : 2
	});

	$.ims.common.findAllUser(function(){
 		$("#s_compere").chosen({
 			allow_single_deselect : true,
 			no_results_text : "没有找到.",
 			disable_search_threshold : 5,
 			enable_split_word_search : true,
 			search_contains : true
 		});
 	}, "s_compere");
	
	$.ims.meeting.reserveStatus("s_status");
	
	$.ims.meeting.reserveManageDataTable();
});
</script>
<style type="text/css">
.chzn-container .chzn-results{
	max-height:150px;
}

.valid-error {
border: 1px solid red !important;
}

.row-fluid [class*="span"]{
min-height: 34px;
}
</style>
</head>
<body>

   <div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />
		<input type="hidden" id="roomId" name="roomId"/>
		<div class="main-wrapper" style = "height:100%">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets " style = "height:100%">
							<div class="widget-head  bondi-blue" >
								<h3>预约管理</h3>
							</div>
								<div class="tab-pane active" id="bookinfo" style = "height:100%">
									<div class="box well form-inline">	
										<span>会议名称：</span>
										<input type="text" maxlength="30" id="s_meetingname" class="span2">
										<span>主持人：</span>
										<select id="s_compere" style="width: 120px;" data-placeholder="选择主持人">
										</select>
										<span>开始日期：</span>
										<div class="input-append date">
										 <input id="s_startDate" style="width:80px;" type="text" value="" readonly="readonly">
										 <span class="add-on"><i class="icon-th"></i></span>
										</div>
										<span>预约状态：</span>
										<select id="s_status" style="width: 100px;" data-placeholder="选择状态">
										</select>															
										<a  onclick="$.ims.meeting.reserveManageDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<table class="responsive table table-striped table-bordered table-hover table-condensed" id="reserve_dataTable">
										<thead>
											<tr>
												<th style="width:">会议名称</th>
												<th style="width: ">申请人</th>
												<th style="width: ">主持人</th>
												<th style="width: ">纪要人</th>
												<th style="width: ">会议室</th>
												<th style="width: ">开始时间</th>
												<th style="width: ">结束时间</th>
												<th style="width:5% ">会议状态</th>
												<th style="width:5% ">会议纪要</th>
												<th style="width:10%">操作</th>
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
		<%@ include file="../foot.jsp"%>
	</div>
	
	<!-- 编辑新增弹出框 -->
<div class="modal hide fade" id="reserve_edit_modal" style="min-width: ">
		<div class="modal-header blue">
			<button type="button" class="close" onclick="$('#calendar').fullCalendar('refetchEvents');" data-dismiss="modal" id="closeViewModal">×</button>
			<label id="reserve_modal_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
				<table>
					<tr>
						<td> 
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 会议日期：</label>
							<div class="controls">
								<div class="input-append">
									 <input id="startDate" type="text" style="width: 170px;" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 会议时间：</label>
							<div class="controls">
							 	<div class="input-append">
									<input id="startTime"  type="text" style="width: 60px;" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>						 
								<div class="input-append">
									<input id="endTime"  type="text" style="width: 60px;" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						
					</td>
				</tr>
				</table>
 				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_editReserve">
			<a class="btn btn-success" onclick="$.ims.meeting.updateTime()">提交</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal" >关闭</a>
		</div>
	</div> 
	
	
</body>
</html>