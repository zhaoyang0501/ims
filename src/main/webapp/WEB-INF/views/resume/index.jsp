<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.resume.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">

	$(document).ready(function () { 
		
		$.ims.common.findAllDept(function(){
			$("#s_dept").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "s_dept");
		
		$.ims.common.resumeStateChosen("s_state");

		
		$.ims.userresume.initResumeDataTable();
		
	});
	
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:150px;
	}
	.div-inline{ display:inline} 
} 
</style>
</head>
<body >
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="re_id" />
		<input type="hidden" id="us_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>人事档案</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">
									<span>员工姓名：</span>
									<input type="text" id="s_chinesename" class="span1">
									<span>工号：</span>
									<input type="text" id="s_empno" class="span1">
									<span>部门：</span>
									<select id="s_dept" data-placeholder="请选择部门" style="width: 180px;">
										<option></option>
									</select>
									<span>状态：</span>
									<select id="s_state" style="width: 120px;" data-placeholder="选择状态">
									</select>
									<a onclick="$.ims.userresume.initResumeDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
									<a class="btn btn-success" style="float: right;"
										onclick="$.ims.userresume.exportResumeReport()"><i class="icon-play"></i>导出Excel</a>
								</div>
								
								<div class="" >
									<table  class="responsive table table-striped table-bordered table-condensed table-hover" id="resume_datatable">
										<thead>
										    <tr>
										    	<th style="width: 10%">姓名</th>
										    	<th style="width: 10%">工号</th>
										    	<th style="width: 5%">性别</th>
										    	<th style="width: 10%">部门</th>
										    	<th style="width: 15%">邮箱</th>
										    	<th style="width: 10%">职称</th>
										    	<th style="width: 10%">职位</th>
										    	<th style="width: 10%">入职时间</th>
										    	<th style="width: 5%">状态</th>
										    	<th style="width: 10%">离职时间</th>
										    	<th style="width: 5%">操作</th>
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
		</div>
		<%@ include file="../foot.jsp"%>
	</div>

	
</body>
</html>
