<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirementgather.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>

<head>
<script type="text/javascript">
$(function(){
	
	$.ims.trainingrequirementgatherleader.inintTrainingRequireLeader("${year}");
	
	$(".chosen").chosen();
	
	$("input[name='trainingmethod']").change(function(){
		var trainingmethod = $("input[name='trainingmethod']:checked").val();
		if(trainingmethod == "1"){
			$("#div_teacher").show();
			$("#div_org").hide();
		}else{
			$("#div_teacher").hide();
			$("#div_org").show();
		}
	});
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red;
	}
	.chzn-container .chzn-results{
		max-height:150px;
	}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../../../top.jsp"%>
		<input id="hf_requireId" type="hidden" value="${requireId}" />
		<input id="hf_actionId" type="hidden" value="${actionId}" />
		<!-- 导航 -->
		<%@ include file="../../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head  bondi-blue">
								<h3>培训需求收集</h3>
							</div>
							<div class="row-fluid">
								<div class="widget-header-block" style="margin-top:20px;">
									<h4 class="widget-header">${year }年培训需求收集</h4>
								</div>
								<button id="btn_add" class="btn btn-success" style="float: right;" onclick="$.ims.trainingrequirementgatherleader.showAddModal()"><i class="icon-plus"></i>新增额外培训需求</button>
								<table id='dt_traingrequireleader' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th style="width:7%;">所属部门 </th>
											<th style="width:8%;">岗位/类型 </th>
											<th style="width:15%;">培训课题 </th>
											<th style="width:5%;">培训对象 </th>
											<th style="width:5%;">计划月份 </th>
											<th style="width:5%;">预计费用 </th>
											<th style="width:5%;">课时（H）</th>
											<th style="width:5%;">培训场所 </th>
											<th style="width:10%;">讲师/培训机构 </th>
											<th style="width:15%;">培训目标 </th>
											<th style="width:5%;">状态 </th>
											<th style="width:10%;">备注 </th>
											<th style="width:5%;"></th>
										</tr>
									</thead>
								</table>
								<div class="form-actions">
									<button type="button" class="btn btn-success" onclick="$.ims.trainingrequirementgatherleader.approve()">审批通过</button>
								    <button type="button" class="btn" onclick="history.go(-1)">返 回</button>
								</div>
								<div class="widget-header-block">
									<h4 class="widget-header">流程信息</h4>
								</div>
								<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>时间</th>
											<th>流程节点</th>
											<th>处理人</th>
											<th>处理意见</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${approvals}" var="item">
											<tr>
												<td>
													<fmt:formatDate value="${item.createDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm" />
												</td>
												<td>${item.step.name}</td>
												<td>${item.step.owner.chinesename}</td>
												<td>${item.approvel}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../../foot.jsp"%>
	</div>
	<!-- 详细信息 -->
	<div class="modal hide fade" id="trainingrequiremodal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			新增额外培训需求
		</div>
		<div class="modal-body" style="max-height:800px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<div class="control-group">
						<label for="summary" class="control-label">培训课题：
						</label>
						<div class="controls">
							<input id="subject" type="text" />
						</div>
					</div>
					<div class="control-group" id='control_projectStep'>
						<label for="projectStep" class="control-label">培训类型：</label>
						<div class="controls">
							<select id="trainingType" name="trainingType" class="chosen" data-placeholder="请选择培训类型">
								<c:forEach items="${trainingType}" var="bean">
									<option value="${bean.key}">${bean.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label for="summary" class="control-label">培训对象：
						</label>
						<div class="controls">
							<input id="subject" type="text" />
						</div>
					</div>
					<div class="control-group">
						<label for="month" class="control-label">计划月份：
						</label>
						<div class="controls">
							<select id="month" name="month" class="chosen">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label for="summary" class="control-label">预计费用：
						</label>
						<div class="controls">
							<input id="subject" type="text" />
						</div>
					</div>
					<div class="control-group">
						<label for="summary" class="control-label">培训课时：
						</label>
						<div class="controls">
							<input id="subject" type="text" /><span class="text-success">（H）</span>
						</div>
					</div>
					<div class="control-group">
						<label for="summary" class="control-label">培训方式：
						</label>
						<div class="controls">
							<input type="radio" value="1" name="trainingmethod" checked="checked">&nbsp;内训</input>&nbsp;&nbsp;
							<input type="radio" value="2" name="trainingmethod">内训</input>
						</div>
					</div>
					<div class="control-group" id="div_teacher">
						<label for="summary" class="control-label">培训讲师：
						</label>
						<div class="controls">
							<select id="teacher" name="teacher" class="chosen" data-placeholder="请选择培训讲师">
							</select>
						</div>
					</div>
					<div class="control-group" id="div_org" style="display: none;">
						<label for="summary" class="control-label">培训机构：
						</label>
						<div class="controls">
							<input id="subject" type="text" />
						</div>
					</div>
					<div class="control-group">
						<label for="summary" class="control-label">培训目标：
						</label>
						<div class="controls">
							<textarea id="goals" name="goals" style="width:300px;height:50px;"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary" onclick="">保存</a> 
			<a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>