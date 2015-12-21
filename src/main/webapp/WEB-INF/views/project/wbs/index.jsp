<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.project.wbs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">

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

		$.ims.projectwbs.initProjectWbsDataTable();
	});
</script>
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>WBS查询</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">
									 <span>项目名称：</span>
									<input id="s_wbsName" type="text" class="span2">
									<span>责任人：</span>
									<select id="s_pm" data-placeholder="选择责任人">
									</select>
									<a onclick="$.ims.projectwbs.initProjectWbsDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								</div>
								<table class="responsive table table-striped table-bordered" id="projectWps_dataTable">
									<thead>
										<tr>
											<th>#</th>
											<th>项目名称</th>
											<th>责任人</th>
											<th>阶段节点</th>
											<th>DR阶段</th>
											<th>计划开始时间</th>
											<th>计划结束时间</th>
											<th>计划工时</th>
											<th>实际开始时间</th>
											<th>实际结束时间</th>
											<th>实际工时</th>
											<th>延期原因</th>
											<th>项目复杂度</th>
											<th>项目状态</th>
											<th>项目描述</th>
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
</body>
</html>