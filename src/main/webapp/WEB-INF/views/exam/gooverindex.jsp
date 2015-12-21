<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.exam.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.cleditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){	
		$.ims.exam.initExamGooverDataTable();
		$('.contents').cleditor();
		
		$(".chosen").chosen({
			disable_search_threshold : 5,
			allow_single_deselect : true,
			disable_search_threshold : false
		});
		
		$.ims.common.findAllUser(function(){
			$("#s_user").chosen({
	 			allow_single_deselect : true,
	 			no_results_text : "没有找到.",
	 			disable_search_threshold : 5,
	 			enable_split_word_search : true,
	 			search_contains : true
	 		});
		}, "s_user");
		
		$.ims.common.findAllExam(function(){
			$("#s_subject").chosen({
	 			allow_single_deselect : true,
	 			no_results_text : "没有找到.",
	 			disable_search_threshold : 5,
	 			enable_split_word_search : true,
	 			search_contains : true
	 		});
		}, "s_subject");
		
	});
</script>
<style type="text/css">
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
						
						<div class="content-widgets">
							<div class="widget-head bondi-blue" >
								<h3>在线阅卷</h3>
							</div>
							<div class="widget" style="margin-top:5px;">
								<div class="box well form-inline">
									<span>员工：</span>
									<select id="s_user" data-placeholder="请选择员工" style="width:150px;"></select>
									<span>试题：</span>
									<select id="s_subject" data-placeholder="请选择试题" style="width:150px;"></select>
									<span>阅卷状态：</span>
									<select id="s_state" class="chosen" data-placeholder="请选择阅卷状态" style="width:150px;">
										<option value="0"></option>
										<option value="1">未阅卷</option>
										<option value="2">已阅卷</option>
									</select>
									<a onclick="$.ims.exam.initExamGooverDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_examgoover">
									<thead>
										<tr>
											<th width="10%;">员工</th>
											<th width="10%;">试题名称</th>
											<th width="20%;">考试时间</th>
											<th width="10%;">是否已阅卷</th>
											<th width="100px;">得分</th>
											<th>阅卷</th>
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
	<div class="modal hide fade" id="exam_paper_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="exam_paper_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post" action="/training/exam/question/save">
						<div class="control-group">
							<label class="control-label"> 试卷题目：</label>
							<div class="controls">
								<input id="subject" name="subject" type="text"></input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 试卷领域：</label>
							<div class="controls">
								<input id="domain" name="domain" type="text"></input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 试卷说明：</label>
							<div class="controls">
								<textarea rows="2" id="remarks" name="remarks"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.exam.savePaper()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>