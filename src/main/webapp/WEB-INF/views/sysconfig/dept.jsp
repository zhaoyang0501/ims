<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hsae.ims.utils.RightUtil" %>
<html lang="ch">
<head>
<%@ include file="../common/meta.jsp"%>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dept.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<%
	boolean hasCreate = RightUtil.hasRight("ROLE_SYSCONFIG_DEPT", "C");	//创建
	boolean hasUpdate = RightUtil.hasRight("ROLE_SYSCONFIG_DEPT", "U");	//更新
	boolean hasDelete = RightUtil.hasRight("ROLE_SYSCONFIG_DEPT", "D");	//删除
%>
<SCRIPT type="text/javascript">
$(document).on("click", ".formError", function() {
	$(this).fadeOut(150, function() {
		$(this).closest('.formError').remove();
	});
});
 $.validator.setDefaults({
	showErrors: function(map, list) {
		$("form .formError").remove();
			$.each(list, function(index, error) {
				$("#formerror_warp .formErrorContent").html("");
				$("#formerror_warp .formErrorContent").append( error.message+"<br/>");
				$(error.element).before($("#formerror_warp  .formError").clone()).parent().css("position","relative");
				$("form .formError").css("top",-$("form .formError").height());
			});
		}}); 
	$(function() {
		$.ims.dept.hasUpdate = <%=hasUpdate %>;
		$.ims.dept.hasDelete = <%=hasDelete %>;
		
		$.ims.dept.dept_form= $("#dept_form").validate({
			rules: {
				dept_name:  "required",
				dept_permission: "required"
				},
			messages: {
				dept_name: "请输入部门名称",
				dept_permission: "请输入部门权限代码"
			}
		});
		$.ims.dept.loadTree();
		
		$.ims.common.findAllUser(function(){
			$("#manager").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "manager");
	});
	</SCRIPT>
	<style type="text/css">
	.chzn-container .chzn-results{
		max-height:100px;
		
	}
</style>
</head>
<body>
	<div class="layout">
	<%@ include file="../top.jsp"%>
	<%@ include file="../menu.jsp"%>
	</div>
	<div class="main-wrapper">
		<div class="container-fluid">
			<%@ include file="../breadcrumb.jsp"%>
			<div class="widget-head bondi-blue">
				<h3>部门管理</h3>
			</div>
			<div class="row-fluid content-widgets light-gray" style="padding-top:5px;">
				<div class="span4">
					<div class="zTreeDemoBackground">
						<ul id="treeDept" class="ztree"></ul>
					</div>
				</div>
				<div class="span8">
					<div align="left">
						<c:if test="<%=hasCreate %>">
							<a class="btn btn-success" onclick="$.ims.dept.showCreateModal()" style="margin:5px;"><i class="icon-plus"></i> 创建子部门</a>
						</c:if>
					</div>							
					<table class="responsive table table-striped table-bordered table-condensed">
						  <thead>
							  <tr role="row">
<!-- 							      <th role="columnheader" class="center" style="width:80px;">部门代码</th> -->
								  <th role="columnheader" class="center" style="width:100px;">部门名称</th>
								  <th role="columnheader" class="center" style="width:100px;">部门主管</th>
								  <th role="columnheader" class="center" style="width:100px;">上级部门</th>
								  <th role="columnheader" class="center" style="width:80px;">权限代码</th>
								  <th role="columnheader" class="center" style="width:100px;">操作</th>
							  </tr>
						  </thead>   
						 <tbody id='dept_table' role="alert" aria-live="polite" aria-relevant="all">
						 </tbody>
					</table> 
				</div>
			</div>
		</div>
	</div>

	<!-- foot -->
	<%@ include file="../foot.jsp"%>
		<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="createModal" >
		<div class="modal-header blue">部门信息
			<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height:210px;">
			<div class="row-fluid">
				<div class="span12">
					<div class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id='dept_form'>
							<input type="hidden" id='dept_pid'/>
			 		 		<input type="hidden" id='dept_operation' value="save"/>
							<div class="control-group hide">
								<label class="control-label">部门代码</label>
								<div class="controls">
									<input type="text" id="dept_id"  class="span10" value=""   readonly="readonly">
								</div>
							</div>
							<div class="control-group">
								<label  for="dept_name" class="control-label">部门名称</label>
								<div class="controls">
									<input id='dept_name' name='dept_name' type="text" class="span8">
								</div>
							</div>
							<div class="control-group">
								<label for="manager" class="control-label">部门主管</label>
								<div class="controls">
									<select id='manager' style="width: 230px" name='manager' data-placeholder="选择部门主管" class="chzn-select">
										<option value=""></option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label  for="dept_permission" class="control-label">权限代码</label>
								<div class="controls">
									<input id='dept_permission'  name='dept_permission' type="text"  class="span8">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>	
		<div class="modal-footer center">
		    <a  class="btn btn-primary" onclick="$.ims.dept.saveOrUpdateDept()">保存</a> 		
			<a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<!-- 删除确认模态窗口 -->
	<div class="modal hide fade" id="deleteModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Warning</h3>
		</div>
		<div class="modal-body">
			<p><strong>您确定要删除这个部门吗？</strong></p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
			<a onclick="$.ims.dept.deleteDept()" class="btn btn-danger">确定删除</a>
		</div>
	</div>
	<!-- 表单验证错误提示 -->
	<div id='formerror_warp'>
	<div   class='formError'>
		<div class='formErrorContent'></div>
			<div class='formErrorArrow'>
				<div class='line10'></div>
				<div class='line9'></div>
				<div class='line8'></div>
				<div class='line7'></div>
				<div class='line6'></div>
				<div class='line5'></div>
				<div class='line4'></div>
				<div class='line3'></div>
				<div class='line2'></div>
				<div class='line1'></div>
			</div>
		</div>
	</div>
</body>
</html>