<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirementgather.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>

<head>
<script type="text/javascript">
$(function(){
	$.ims.trainingrequirementgather.trainingtype = $.ims.trainingrequirementgather.findTraingType();
	$.ims.trainingrequirementgather.monthOption = $.ims.trainingrequirementgather.monthOption();
	$.ims.trainingrequirementgather.teacher = $.ims.trainingrequirementgather.findTeacher();
	$.ims.trainingrequirementgather.trainedUser = $.ims.trainingrequirementgather.findUser();
	
	$.ims.trainingrequirementgather.addNewRow();	//初始化时新增加一行
	
	$(".chosen").chosen({
		allow_single_deselect : true,
	});
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red;
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue">
								<h3>培训需求收集</h3>
							</div>
							<div class="widget-container">
								<div class="widget-header-block">
									<h4 class="widget-header">2015年培训需求收集</h4>
								</div>
								<div style="margin-bottom: 5px;">
									<button id="btn_add" class="btn btn-success" onclick="$.ims.trainingrequirementgather.addNewRow()">＋</button>
									<button id="btn_del" class="btn btn-danger" onclick="$.ims.trainingrequirementgather.delRow()">—</button>
								</div>
								<form id="requireform" action="">
									<table id='dt_traingrequire' class="responsive table table-striped table-bordered table-condensed">
										<thead>
											<tr>
												<th rowspan="2" >#</th>
												<th rowspan="2" >岗位/类型 <span class="text-error">*</span></th>
												<th rowspan="2" >培训课题 <span class="text-error">*</span></th>
												<th rowspan="2" >培训对象 <span class="text-error">*</span></th>
												<th rowspan="2" >计划月份 <span class="text-error">*</span></th>
												<th rowspan="2" >预计费用 <span class="text-error">*</span></th>
												<th rowspan="2" >课时（H）<span class="text-error">*</span></th>
												<th colspan="2" >培训场所<span class="text-error">*</span></th>
												<th rowspan="2" >讲师/培训机构<span class="text-error">*</span></th>
												<th rowspan="2" >培训目标<span class="text-error">*</span></th>
											</tr>
											<tr>
												<th>内训</th>
												<th>外训</th>
											</tr>
										</thead>
										<tbody id ="dt_traingrequire_body">
										</tbody>
									</table>
								</form>
								<div class="form-actions">
									<button type="button" class="btn btn-success" onclick="$.ims.trainingrequirementgather.approve()">提 交</button>
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
</body>
</html>