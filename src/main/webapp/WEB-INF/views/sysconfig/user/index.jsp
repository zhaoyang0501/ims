<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<%
	boolean hasCreate = RightUtil.hasRight("ROLE_SYSCONFIG_USER", "C");	//创建
	boolean hasUpdate = RightUtil.hasRight("ROLE_SYSCONFIG_USER", "U");	//更新
	boolean hasFreeze = RightUtil.hasRight("ROLE_SYSCONFIG_USER", "D");	//冻结、解冻
	boolean hasReset = RightUtil.hasRight("ROLE_SYSCONFIG_USER", "S");	//密码重置
%>
<script type="text/javascript">

	$(document).ready(function(){
		$.ims.common.findAllDept(function(){
			$("#s_dept").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "s_dept");
		
		$.ims.common.findAllDept(function(){
			$("#dept").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "dept");
		
		$.ims.common.findAllPosition(function(){
			$("#position").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "position");
		
		$.ims.common.findPermissionDept(function(){
			$("#authorityScope").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "authorityScope");
		$.ims.common.sexChosen("sex");
		$.ims.common.userStateChosen("s_freeze");
		
		var hasUpdate = <%= hasUpdate %>;
		var hasFreeze = <%= hasFreeze %>;
		var hasReset = <%= hasReset %>;
		
		$.ims.user.initUserDataTable(hasUpdate,hasFreeze,hasReset);
	});
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:120px;
	}
	
	.chzn-single{
	 	height: 28px !important;
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>用户信息列表</h3>
							</div>
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
								<select id="s_freeze" style="width: 120px;" data-placeholder="选择状态">
								</select>
								<a onclick="$.ims.user.initUserDataTable(<%=hasUpdate %>,<%=hasFreeze %>,<%=hasReset %>)"
									class="btn btn-info" data-loading-text="正在加载..."><i class="icon-search"></i>查询</a>
							</div>
							<div class="widget-container">
								<c:if test="<%=hasCreate %>">
									<a class="btn btn-success" style="float: right; margin: 5px;" onclick="$.ims.user.showUserAddModal()"><i class="icon-plus"></i> 新增用户</a>
								</c:if>
								<table class="responsive table table-striped table-bordered" id="user_dataTable">
									<thead>
										<tr>
											<th style="">用户名</th>
											<th style="">中文名</th>
											<th style="">工号</th>
											<th style="">部门</th>
											<th style="">职位</th>
											<th style="">性别</th>
											<th style="">邮箱</th>
											<th style="">状态</th>
											<th style="width:5%">操作</th>
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
		<%@ include file="../../foot.jsp"%>
	</div>

	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="user_edit_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="user_modal_header_label"></label>
		</div>
		<div class="modal-body" style="height:320px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<table>
						<tr>
							<td>
								<div class="control-group">
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 用户账号：</label>
										<input type="text" id="username" name="username" class="span8">
									</div>
									<div style="margin-top:8px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 中文姓名：</label>
										<input type="text" id="chinesename" name="chinesename" class="span8">
									</div>
									<div style="margin-top:8px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 员工工号：</label>
										<input type="text" id="empnumber" name="empnumber" class="span8">
									</div>
									<div style="margin-top:8px;margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 员工邮箱：</label>
										<input type="text" id="email" name="email" class="span8">
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
									<label><font style="color: gray;">*</font> 权限：</label>
									<select id="authorityScope" name="authorityScope" data-placeholder="请选择权限范围" style="width: 150px;">
										<option></option>
									</select>
									</div>
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 性别：</label>
										<select id="sex" name="sex" data-placeholder="请选择性别" style="width: 150px;">
										</select>
									</div>
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 部门：</label>
										<select id="dept" name="dept" data-placeholder="请选择部门" style="width: 150px;">
											<option></option>
										</select>
									</div>
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label><font style="color:gray;">*</font> 职位：</label>
										<select id="position" name="position" data-placeholder="请选择职位" style="width: 150px;height: 30px;">
											<option></option>
										</select>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-inline">
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label>是否需要周报：</label>
										<input type="checkbox" id="weekreport" name="weekreport" checked="checked">
									</div>
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label>是否参加培训：</label>
										<input type="checkbox" id="train" name="train" checked="checked">
									</div>
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label>是否需要考勤：</label>
										<input type="checkbox" id="attendance" name="attendance" checked="checked">
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<button class="btn btn-primary" id='submit_buttion' onclick="$.ims.user.saveOrUpdateUser()">保存</button>
			<button class="btn" data-dismiss="modal" id="closeViewModal">关闭</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div class="modal hide fade" id="user_details_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label>用户信息详情</label>
		</div>
		<div class="modal-body" style="max-height:800px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<table class="table table-striped table-bordered table-condensed">
						<tr><td style="width:30%; text-align: right;">用户名：</td><td><label id="display_username"></label></td></tr>
						<tr><td style="text-align: right;">工号：</td><td><label id="display_no"></label></td></tr>
						<tr><td style="text-align: right;">姓名：</td><td><label id="display_chinesename"></label></td></tr>
						<tr><td style="text-align: right;">部门：</td><td><label id="display_dept"></label></td></tr>
						<tr><td style="text-align: right;">职位：</td><td><label id="display_position"></label></td></tr>
						<tr><td style="text-align: right;">权限代码：</td><td><label id="display_authorityCode"></label></td></tr>
						<tr><td style="text-align: right;">权限范围：</td><td><label id="display_authorityScope"></label></td></tr>
						<tr><td style="text-align: right;">性别：</td><td><label id="display_sex"></label></td></tr>
						<tr><td style="text-align: right;">邮箱：</td><td><label id="display_email"></label></td></tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>