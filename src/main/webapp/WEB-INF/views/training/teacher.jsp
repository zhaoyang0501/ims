<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.teacher.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<%
	boolean hasCreate = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "C");	//创建
	boolean hasUpdate = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "U");	//更新
	boolean hasDelete = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "D");	//删除
%>
<script type="text/javascript">

	$(document).ready(function(){
		$.ims.common.findAllUser(function(){
			$("#training_teacher").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "training_teacher");
		$.ims.common.findAllUser(function(){
			$("#teacher").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "teacher");
		$("#level").chosen({
			allow_single_deselect : true,
		});
		
		$.ims.trainingteacher.inintTrainingTeacherDatatable(1, 1);
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
							<div class="widget-head  bondi-blue" >
								<h3>讲师库管理</h3>
							</div>
							<div class="box well form-inline">
								<span>培训讲师：</span>
								<select id="training_teacher" data-placeholder="选择培训讲师" class="span2">
								</select>
								<a onclick="$.ims.trainingteacher.inintTrainingTeacherDatatable(1, 1)" class="btn btn-info"><i class="icon-search"></i>查询</a>
							</div>
							<div class="widget-container">
								<a class="btn btn-success" style="float: right; margin: 5px;"
										onclick="$.ims.trainingteacher.showAddModal()"><i class="icon-plus"></i> 新增</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_trainingteacher">
									<thead>
										<tr>
											<th style="width: 120px;">讲师姓名</th>
											<th style="width: 150px;">讲师级别</th>
											<th>专业领域</th>
											<th>讲师简介</th>
											<th>操作</th>
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
		<%@ include file="../foot.jsp"%>
	</div>
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="traingteacher_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="traingteacher_modal_header_label"></label>
		</div>
		<div class="modal-body" >
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						
						<div class="control-group">
							<label class="control-label">讲师：</label>
							<div class="controls">
								<select id="teacher"  data-placeholder="选择人员"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">讲师级别：</label>
							<div class="controls">
								<select id="level">
									<c:forEach var="level" items="${levels }">
										<option value="${level.key } ">${level.value }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">专业领域：</label>
							<div class="controls">
								<input type="text" id="domain">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">讲师简介：</label>
							<div class="controls">
								<textarea type="text" id="introduction" style="width:300px;height:50px;"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.trainingteacher.saveOrupdate()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>