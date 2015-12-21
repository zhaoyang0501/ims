<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.basetraining.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$.ims.basetraining.initBaseTraningDatatable();
		
		$(".chosen").chosen({
			disable_search_threshold : 5,
			allow_single_deselect : false,
			disable_search_threshold : false

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
			forceParse : 0
		});
		
		$.ims.common.findAllUser(function(){
	 		$("#employee").chosen({
	 			allow_single_deselect : true,
	 			no_results_text : "没有找到.",
	 			disable_search_threshold : 5,
	 			enable_split_word_search : true,
	 			search_contains : true
	 		});
	 	}, "employee"); 
		$.ims.common.findAllUser(function(){
	 		$("#s_employee").chosen({
	 			allow_single_deselect : true,
	 			no_results_text : "没有找到.",
	 			disable_search_threshold : 5,
	 			enable_split_word_search : true,
	 			search_contains : true
	 		});
	 	}, "s_employee");
		
	});
	
</script>
<style type="text/css">
	.modal{
/* 		width:800px; */
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
							<div class="widget-head bondi-blue" >
								<h3>基础培训计划</h3>
							</div>
							<div class="widget" style="margin-top:5px;">
								<div class="box well form-inline">
									<span>培训计划：</span>
									<input id="s_plan" type="text"></input>
<!-- 									<span>状态：</span> -->
<!-- 									<select id="s_state" style="width: 120px;" data-placeholder="选择培训状态" class="chosen"> -->
<!-- 										<option value="-1"></option> -->
<!-- 										<option value="0">未开始学习</option> -->
<!-- 										<option value="1">进行中</option> -->
<!-- 										<option value="2">已结束</option> -->
<!-- 									</select> -->
									<a onclick="$.ims.basetraining.initBaseTraningDatatable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<a class="btn btn-success" style="float: right; margin-bottom:5px;"
										onclick="$.ims.basetraining.showAddModel()"><i class="icon-plus"></i> 添加培训计划</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_basetraining">
									<thead>
										<tr>
											<th width="10%;">培训计划</th>
											<th width="12%;">培训计划描述</th>
											<th width="10%;">培训课程</th>
											<th width="10%;">参训人员</th>
											<th width="100px;">开始日期</th>
											<th width="100px;">结束日期</th>
											<th>培训目标</th>
											<th>备注</th>
											<th width="100px;">培训状态</th>
											<th width="100px;">操作</th>
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
	
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="basetraining_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="basetraining_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post" action="/basetraining/save">
						<div class="control-group">
							<label class="control-label"> 培训计划：</label>
							<div class="controls">
								<input id="title" type="text" ></input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 培训计划描述：</label>
							<div class="controls">
								<textarea id="description" maxlength="300" rows="2" class="span10"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 参训员工：</label>
							<div class="controls">
								<select id="employee" multiple data-placeholder="选择参训员工" class="multiple">
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 基础学习课程：</label>
							<div class="controls">
								<select id="course" class="chosen multiple" multiple data-placeholder="选择学习课程">
									<optgroup label="入职培训">
										<c:forEach items="${list }" var="item">
											<option value="${item.id }">${item.courseName }</option>
										</c:forEach>
									</optgroup>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 开始日期：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="start" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 结束日期：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="end" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 备注：</label>
							<div class="controls">
								<textarea id="remarks" maxlength="300" rows="2" class="span10"></textarea>
							</div>
						</div>	
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.basetraining.save()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>