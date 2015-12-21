<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chosen.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.functionrights.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<%
	boolean hasCreate = RightUtil.hasRight("ROLE_SYSCONFIG_FUNCTION", "C");	//角色创建
	boolean hasUpdate = RightUtil.hasRight("ROLE_SYSCONFIG_FUNCTION", "U");	//角色更新
	boolean hasDelete = RightUtil.hasRight("ROLE_SYSCONFIG_FUNCTION", "D");	//角色删除
%>
<script type="text/javascript">

	$(document).ready(function(){
		$.ims.common.findAllUser(function(){
     		$("#user").chosen({
     			allow_single_deselect : true,
     			no_results_text : "没有找到.",
     			disable_search_threshold : 5,
     			enable_split_word_search : true,
     			search_contains : true
     		});
     	}, "user"); 
		
		
		$.ims.functionrights.queryrole();
		var hasUpdate = <%=hasUpdate %>;
		var hasDelete = <%=hasDelete %>;
		$.ims.functionrights.initRoleDataTable(hasUpdate,hasDelete);
		$.ims.functionrights.initUserRightsDataTable(hasUpdate,hasDelete);
		$.ims.functionrights.initRoleUserDataTable(hasUpdate,hasDelete);
		
		
		$(".required2").change(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		
		$("#roleuser_role").change(function(){
			var roleuser_role = $("#roleuser_role").val();
			if (roleuser_role == "" || roleuser_role == null){
	        	$("#roleuser_role").next().addClass('valid-error');
	        	flag = false;
			}else{
	        	$("#roleuser_role").next().removeClass('valid-error');
			}
		});
        
	});
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:150px;
	}
	.tab-content{
	  overflow-y: auto;
	}
	.valid-error {
	border: 1px solid red !important;
	}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<h3 class="page-header">功能权限设置</h3>
						<div class="accordion" id="accordion2">
							<div class="accordion-group">
								<div class="accordion-heading">
									<a href="#collapseOne" data-parent="#accordion2" data-toggle="collapse" class="accordion-toggle">角色权限管理</a>
								</div>
								<div class="accordion-body collapse in" id="collapseOne">
									<div class="accordion-inner" style="margin-bottom: 30px;">
										<c:if test="<%=hasCreate %>">
											<a class="btn btn-success" style="float: right; margin: 5px;"
												onclick="$.ims.functionrights.showRoleAddModal()"><i class="icon-plus"></i> 新建角色</a>
										</c:if>
										 <table class="responsive table table-striped table-bordered table-condensed" id="role_dataTable">
											<thead>
												<tr>
													<th style="width: 200px;">角色名称</th>
													<th style="width: 200px;">角色描述</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="accordion-group">
								<div class="accordion-heading">
									<a href="#collapseTwo" data-parent="#accordion2" data-toggle="collapse" class="accordion-toggle">人员权限管理</a>
								</div>
								<div class="accordion-body collapse" id="collapseTwo">
									<div class="accordion-inner" style="margin-bottom: 30px;">
										<c:if test="<%=hasCreate %>">
											<a class="btn btn-success" style="float: right; margin: 5px;"
												onclick="$.ims.functionrights.showUserRightsAddModal()"><i class="icon-plus"></i> 新建人员权限</a>
										 </c:if>	
										 <table class="responsive table table-striped table-bordered table-condensed" id="userRights_dataTable">
											<thead>
												<tr>
													<th style="width: 200px;">员工姓名</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="accordion-group">
								<div class="accordion-heading">
									<a href="#collapseThree" data-parent="#accordion2" data-toggle="collapse" class="accordion-toggle">角色人员管理</a>
								</div>
								<div class="accordion-body collapse" id="collapseThree">
									<div class="accordion-inner" style="margin-bottom: 30px;">
										<c:if test="<%=hasCreate %>">
											<a class="btn btn-success" style="float: right; margin: 5px;"
													onclick="$.ims.functionrights.showRoleUserAddModal()"><i class="icon-plus"></i> 新建角色人员</a>
										</c:if>
										<table class="responsive table table-striped table-bordered table-condensed" id="roleUser_dataTable">
											<thead>
												<tr>
													<th style="width: 200px;">角色名称</th>
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
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>
	<!-- ----------------------------------------------角色列表 查看-------------------------------------------------- -->
	<!-- 查看 -->
	<div class="modal hide fade" id="roleDetailModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>详细信息</h3>
		</div>
		<div class="modal-body">
			<ul class="nav nav-tabs">
				<li class="active"><a>角色信息</a></li>
			</ul>
			<div class="tab-content" >
				<div class="tab-pane active">
					<table class="table">
						<tr>
							<td style="width: 150px; height: 20px;">角色名称：</td>
							<td><span id="detail_rolename" ></span></td>
						</tr>
						<tr>
							<td style="width: 150px; height: 20px;">角色描述：</td>
							<td><span id="detail_roledesc" ></span></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-body" style="margin-top: -40px;"> 
			  <ul class="nav nav-tabs">
			    <li class="active"><a data-toggle="tab">权限列表</a></li>
			  </ul>
			  <div class="tab-content">
				<div class="tab-pane active"  style="height: 200px">
				   	<div class="zTreeDemoBackground left">
						<ul id="treeRightView" class="ztree"></ul>
					</div>
				</div>
			  </div>
		</div>
		<div class="modal-footer center">
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<!-- 新增、编辑 -->
	<input type="hidden" id="hf_roleId" />
	<div class="modal hide fade" id="role_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="role_modal_header_label"></label>
		</div>
		<div class="modal-body" style="min-height: 550px;">
			<ul class="nav nav-tabs">
				<li class="active"><a>角色信息</a></li>
			</ul>
			<div class="row-fluid tab-content">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<div class="control-group">
						<div style="margin-top:5px; margin-left:20px;" class="form-inline">
							<label><font style="color:red;">*</font> 角色名称：</label>
							<input type="text" id="rolename" name="rolename">
						</div>
						<div style="margin-top:5px; margin-left:20px;" class="form-inline">
							<label><font style="color:red;">*</font> 角色描述：</label>
							<textarea id="roledesc" name="roledesc" style="width:350px;height:40px;"></textarea>
						</div>
					</div>
					<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab">权限列表</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active"  style="height: 200px">
							<div class="zTreeDemoBackground left" style="margin-left:80px;">
								<ul id="rightsTree1" class="ztree"></ul>
							</div>
						</div>
				 	</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.functionrights.saveOrUpdateRole()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<!-- ----------------------------------------------用户权限 查看-------------------------------------------------- -->
	<!-- 用户权限查看 -->
	<div class="modal hide fade" id="user_right_detail_Modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h4>详细信息</h4>
		</div>
		<div class="modal-body">
			<ul class="nav nav-tabs" id="myTabDetail">
				<li class="active"><a href="#dept">人员权限信息</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="edituserrightinfo">
					 <table class="table">
						<tr>
							<td style="width: 150px; height: 20px;">人员：</td>
							<td>
								<span  id='detail_userright_user'></span>
							</td>
						</tr>
						<tr>
							<td style="width: 150px; height: 20px;" colspan="2">权限列表：</td>
						</tr>
					</table>
					<div class="zTreeDemoBackground left">
						<ul id="userrightTreeView" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
		 <a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	<!-- 用户权限新增、编辑 -->
	<div class="modal hide fade" id="userRights_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="userRights_modal_header_label">编辑人员权限</label>
		</div>
		<div class="modal-body" style="min-height:300px;">
			<div class="row-fluid left-align form-horizontal">
				<div class="control-group">
					<div style="margin-top:5px; margin-left:20px;" class="form-inline">
						<label><font style="color:red;">*</font> 用户选择：</label>
						<select id="user" class="chosen required2" data-placeholder="请选择人员..."  onchange="">
							<option ></option>
					    </select>
					</div>
					<div style="margin-top:5px; margin-left:20px;">
						<label><font style="color:red;">*</font> 功能权限列表：</label>
						<div class="zTreeDemoBackground left" style="margin-left:80px;">
							<ul id="rightsTree2" class="ztree"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.functionrights.saveOrUpdateUserRights()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<!-- ----------------------------------------------角色对应人员 查看-------------------------------------------------- -->
	<!-- 角色对应人员查看 -->
	<div class="modal hide fade" id="role_user_detail_modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>详细信息</h3>
		</div>
		<div class="modal-body">
			<ul class="nav nav-tabs" id="myTabdetail">
				<li class="active"><a href="#dept">角色人员信息</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active">
					<table class="table">
						<tr>
							<td style="width: 150px; height: 20px;">角色名称：</td>
							<td>
								<span id="span_rolename"></span>
							</td>
						</tr>
						<tr>
							<td style="width: 150px; height: 20px;" colspan="2">人员列表：</td>
						</tr>
					</table>
					 <table class="table" id="usertabledetail">
					</table>			
				</div>
			</div>
		</div>
		<div class="modal-footer center">
		 <a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	<!-- 角色人员新增、编辑 -->
	<div class="modal hide fade" id="roleUser_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="roleUser_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<table>
						<tr>
							<td>
								<div class="control-group">
									<div style="margin-top:5px; margin-left:20px;" class="form-inline">
										<label><font style="color:red;">*</font> 角色选择：</label>
										<select id="roleuser_role" class="chosen" data-placeholder="请选择角色..."  onchange="$.ims.functionrights.queryUser2UserRole(this.value);">
											<option ></option>
									    </select>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-inline">
									<div style="margin-top:5px; margin-left:20px;">
										<label><font style="color:red;">*</font> 人员列表：</label>
										<table class="table" id="usertable">
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.functionrights.saveOrUpdateRoleUser()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>