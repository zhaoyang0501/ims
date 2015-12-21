<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.project.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
		
		$(".chzn-select").chosen({
			allow_single_deselect : false,
			search_contains : false
		});
		$.ims.common.findAllUser(function(){
			$("#pm").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到这个用户.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "pm");
		
		$.ims.common.findAllUser(function(){
			$("#users").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到这个用户.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "users");
		
		$.ims.project.initProjectEidtForm();
		$.ims.project.initProjectMemberDataTable();
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
		
		<input type="hidden" id="hf_id" value="${id }" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="content-widgets">
						<div class="widget-head  bondi-blue" >
							<h3>项目编辑</h3>
						</div>
					<div class="tab-widget">
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#projectEdit"><i class="icon-edit"></i>项目编辑</a></li>
							<li class=""><a href="#membersetting"><i class="icon-check"></i>项目成员配置</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="projectEdit">
								<div class="widget-container">
									<form class="form-horizontal">
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 项目名称：</label>
											<div class="controls">
												<input type="text" id="projectName">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 项目编号：</label>
											<div class="controls">
												<input type="text" id="projectCode">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 项目复杂度：</label>
											<div class="controls">
												<select id="complex" class="chzn-select">
													<option value="A1">A1</option>
													<option value="A2">A2</option>
													<option value="B1">B1</option>
													<option value="B2">B2</option>
													<option value="C">C</option>
													<option value="D">D</option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 产品经理：</label>
											<div class="controls">
												<select id="pm" data-placeholder="选择产品经理"></select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 客户名称：</label>
											<div class="controls">
												<input type="text" id="customer">
											</div>
										</div>
										<div id="div_state" class="control-group">
											<label class="control-label"><span class="text-error">*</span> 项目状态：</label>
											<div class="controls">
												<select id="state" class="chzn-select">
													<option value="1">进行中</option>
													<option value="2">完成</option>
													<option value="3">关闭</option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><span class="text-error">*</span> 项目描述：</label>
											<div class="controls">
												<textarea rows="3" cols="4" id="description"></textarea>
											</div>
										</div>
										<div class="form-actions">
											<a type="button" onclick="$.ims.project.save()" class="btn btn-success">保存修改</a>
											<a type="button" href="<c:url value='project/index'></c:url>" class="btn">返回</a>
										</div>
									</form>
								</div>
							</div>
							<div class="tab-pane" id="membersetting">
								<div class="row-fluid span6">
									<a class="btn btn-info" style="float: right;"
										onclick="$.ims.project.showMemberAddModal()"><i class="icon-plus"></i> 配置项目成员</a>
									<table class="responsive table table-striped table-bordered" id="member_dataTable">
										<thead>
											<tr>
											    <th>#</th>
												<th width="20%">成员名称</th>
												<th width="20%">角色</th>
												<th></th>
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
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="project_Member_edit">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="project_Member_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:250px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 角色：</label>
							<div class="controls">
								<select id="role" class="chzn-select">
									<c:forEach items="${roles }" var="role">
										<option value="${role.key }">${role.value }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 成员名称：</label>
							<div class="controls">
								<select id="users" data-placeholder="成员名称" multiple></select>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary" onclick="$.ims.project.saveMember()">保存</a>
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>
	</div>
	</div>
</body>
</html>