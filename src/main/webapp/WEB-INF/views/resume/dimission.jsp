<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.dimission.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.ims.userdimission.initDimissionDataTable();

		$.ims.common.findAllUser(function(){
			$("#s_user").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "s_user");
		
		$('.date').datetimepicker({
			language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
	        format:'yyyy-mm-dd',
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
		
		$(".chosen").chosen({
			allow_single_deselect : false,
			search_contains : false
		});
	});
</script>
<style type="text/css">
	.modal{
		width:650px;
	}
	a{
		cursor:pointer;
	}
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
						<div class="content-widgets">
							<div class="widget-head  bondi-blue">
								<h3>离职管理</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">
									<span>员工：</span>
									<select id="s_user" style="width:100px;" data-placeholder="选择员工"></select>
									<span>拟离职日期：</span>
									<input type="text" id="s_planDate_s" class="date" style="width:70px;" readonly="readonly">~
									<input type="text" id="s_planDate_e" class="date" style="width:70px;" readonly="readonly">
									<span>实际离职日期：</span>
									<input type="text" id="s_actualDate_s" class="date" style="width:70px;" readonly="readonly">~
									<input type="text" id="s_actualDate_e" class="date" style="width:70px;" readonly="readonly">
									<span>离职类型：</span>
									<select id="s_dismission_type" data-placeholder="离职类型" class="chosen" style="width: 100px;">
										<option value=""></option>
										<c:forEach items="${dimissionType}" var="bean">
											<option value="${bean.key}" <c:if test="${bean.key ==dimission.type}">selected="selected"</c:if>>${bean.value }</option>
										</c:forEach>
									</select>
									<a onclick="$.ims.userdimission.initDimissionDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>

								<a class="btn btn-success" style="float: right;" onclick="$.ims.userdimission.showDimissionModal()"><i class="icon-play"></i>员工离职</a>
								<table class="responsive table table-striped table-bordered table-condensed table-hover" id="dimission_datatable">
									<thead>
										<tr>
											<th style="width: 10%">离职人员</th>
											<th style="width: 10%">离职部门</th>
											<th style="width: 10%">离职类型</th>
											<th style="width: 10%">申请日期</th>
											<th style="width: 10%">拟离职日期</th>
											<th style="width: 10%">实际离职日期</th>
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
	<div class="modal hide fade" id="dimission_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="dimission_modal_header_label"></label>
		</div>
		<div class="modal-body" >
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<input type="hidden" id="hf_dimission_id"></input>
						<table class="responsive table table-bordered table-condensed">
							<tr>
								<th><span style="padding-left: 10px;">离职人员：</span></td>
								<td colspan="3">
									<div id="div_resume">
										<select id="employee" style="width:120px;" data-placeholder="选择员工" class="chosen">
											<option value="0"></option>
											<c:forEach items="${resume}" var="bean">
													<option value="${bean.key}">${bean.value }</option>
												</c:forEach>
										</select>
										<a style="padding:20px;">查看考勤状态</a>
									</div>
									<div id="div_chinesename" style="display: none;">
										<span id="l_chinesename"></span>
										<a style="padding:20px;">查看考勤状态</a>
									</div>
								</td>
							</tr>
							<tr>
								<th><span style="padding-left: 10px;">离职类型：</span></th>
								<td colspan="3">
									<select id="dismission_type" data-placeholder="离职类型" class="chosen" style="width: 120px;height:50px;">
										<option value=""></option>
										<c:forEach items="${dimissionType}" var="bean">
											<option value="${bean.key}" <c:if test="${bean.key ==dimission.type}">selected="selected"</c:if>>${bean.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th width="20%"><span style="padding-left: 10px;">申请日期：</span></td>
								<td width="30%"><input id="dismission_applydate" value="${dimission.applydate }" class="date text" style="width:100px;" readonly="readonly"></td>
								<th width="20%"><span style="padding-left: 10px;">预计日期：</span></th>
								<td width="30%"><input id="dismission_plandate" value="${dimission.plandate }" class="date text" style="width:100px;" readonly="readonly"></td>
							</tr>
							<tr>
								<th><span style="padding-left: 10px;">实际日期：</span></td>
								<td><input id="dismission_actualdate" value="${dimission.actualdate }" class="date text" style="width:100px;" readonly="readonly"></td>
								<th><span style="padding-left: 10px;">加入黑名单：</span></th>
								<td><input id="dismission_blacklist" checked="${dimission.blacklist == 1 ? true:false }" type="checkbox"></input></td>
							</tr>
							<tr>
								<th><span style="padding-left: 10px;">离职原因：</span></td>
								<td colspan="3">
									<textarea id="dismission_reason" rows="" cols="" style="width:90%">${dimission.reason }</textarea>
								</td>
							</tr>
							<tr>
								<th><span style="padding-left: 38px;">备注：</span></td>
								<td colspan="3">
									<textarea id="dismission_remark" rows="" cols="" style="width:90%">${dimission.remarks }</textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.userdimission.saveDismission()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>
