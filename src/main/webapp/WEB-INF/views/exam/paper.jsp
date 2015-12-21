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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.exam.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.cleditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){	
		$.ims.exam.initExamPaperDataTable();
		
		$('.contents').cleditor();
		
		$(".chosen").chosen({
			disable_search_threshold : 5,
			allow_single_deselect : false,
			disable_search_threshold : false
		});
		
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
								<h3>在线考试管理</h3>
							</div>
							<div class="widget">
								<div class="box well form-inline" style="margin-top: 10px;">
									<span>试题名称：</span>
									<input id="s_subject" type="text"></input>
									<a onclick="$.ims.exam.initExamPaperDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<button class="btn btn-success" style="float: right; margin-bottom:5px;"
										onclick="$.ims.exam.showExamPaperAddModel()"><i class="icon-plus"></i> 添加考试试卷</button>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_exampaper">
									<thead>
										<tr>
											<th width="20%;">试卷题目</th>
											<th width="10%;">试卷领域</th>
											<th width="20%;">试卷说明</th>
											<th width="100px;">创建时间</th>
											<th width="100px;">创建人</th>
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