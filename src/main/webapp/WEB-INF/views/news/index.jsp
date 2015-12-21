<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.news.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">
	function openwin(id){
		var high = 500;
		var width = 700;
		var top = 200;
		var left = 500;
		var obj = window;
		$("#hf_id").val(id);
		window.open("news/newsinfo.htm","newwindo","modal=yes,height="+high+",width="+width+",top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
	}
	$(document).ready(function(){

		$(".chzn-select").chosen({
			allow_single_deselect : false,
			search_contains : false
		});

		$.ims.common.findAllUser(function(){
			$("#s_pm").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到这个用户.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "s_pm");
		
		$.ims.common.findAllUser(function(){
			$("#pm").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到这个用户.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "pm");
		$.ims.news.initNewsDataTable();
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
								<h3>通知公告</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">
									<span>标题：</span>
									<input id="s_title" type="text" class="span2">
									<span>类型：</span>
									<select id="s_type" data-placeholder="选择类型" class="chzn-select">
										<option value="0"></option>
										<c:forEach var="item" items="${news }">
											<option value="${item.key }">${item.value }</option>
										</c:forEach>
									</select>
									<a onclick="$.ims.news.initNewsDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<a class="btn btn-info" style="float: right;"
									href="<c:url value='/sysconfig/news/create'></c:url>"><i class="icon-plus"></i> 新建</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="news_dataTable" style="background-color: white;">
									<thead>
										<tr>
											<th width="100px;">发布人</th>
											<th width="100px;">类型</th>
											<th width="30%;">标题</th>
											<th width="100px;">创建时间</th>
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
	<div class="modal hide fade" id="news_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="news_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:350px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 新闻标题：</label>
							<div class="controls">
								<input type="text" id="projectName">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 发布时间：</label>
							<div class="controls">
								<input type="text" id="projectCode">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 新闻类型：</label>
							<div class="controls">
								<select id="type" class="chzn-select">
									
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span>置顶：</label>
							<div class="controls">
								<input type="checkbox" />使新闻置顶，显示为重要
							</div>
						</div>
						
						<textarea name="1" cols="5" rows="5" class="textarea wysihtml"></textarea>
						
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.news.save()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
</body>
</html>