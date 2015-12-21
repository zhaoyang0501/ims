<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.myapprove.js"></script>
<head>
<script type="text/javascript">
$(function(){
	$(".controls .btn").eq(0).addClass("btn-primary");
	$(".td_summary").each(function () { 
		  var substr=$(this).html().length>20?$(this).html().substring(0, 20) + "...":$(this).html();
		 $(this).html("<span  data-rel='popover' data-content='"+$(this).html()+"' title='概述'>"+substr+"</span>");
	 } );
	$('[rel="popover"],[data-rel="popover"]').popover();
	/**表单验证*/
	$.ims.myApprove.reimburseform= $("#reimburseform").validate({
		errorPlacement: function(error, element) {
			$( element ).closest(".controls").append( error );
		},
		rules: {
			cash:  {required:true,number:true}
			},
		messages: {
			cash:  {required:"请填写报销金额",number:"必须是数字"}
		}
	});
	<c:if test="${tip!=''}">
	noty({"text":"${tip}","layout":"top","type":"success","timeout":"2000"});
	</c:if>
});
</script>
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue">
								<h3>餐费报销审批</h3>
							</div>

							<div class="widget-container">
							<div class="widget-header-block">
									<h4 class="widget-header"> <fmt:formatDate value="${reimburse.reimburseDate}" type="date" />餐费报销单</h4>
								</div>
							<div class="row-fluid">
		                        <div class="span6 content-box" >
		                        	<table class="table table-bordered table-condensed">
		                        		<tr>
		                        			<td width=20%>报销人：</td><td width=30%>${reimburse.reimburser.chinesename}</td>
		                        			<td width=20%>报销日期：</td><td><fmt:formatDate value="${reimburse.reimburseDate}" type="date" dateStyle="full"/></td>
		                        		</tr>
		                        		<tr>
		                        			<td>报销餐别：</td><td>${reimburse.type==1?"午餐":"晚餐"}</td>
		                        			<td>报销人数：</td><td>${reimburse.number}人</td>
		                        		</tr>
		                        		<tr>
		                        			<td>报销金额：</td><td>${reimburse.reimburseMoney}</td>
		                        			<td>外部人员：</td><td>${sbCustomer }</td>
		                        		</tr>
		                        		<tr>
		                        			<td>备注：</td><td colspan="3">${reimburse.remark}</td>
		                        		</tr>
		                        	</table>
									
								</div>
									
								</div>
								<div class="widget-header-block">
									<h4 class="widget-header">加班报告</h4>
								</div>
								<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>姓名</th>
											<th>加班报告</th>
											<th>加班报告填写时间</th>
											<th>工时核对</th>
											<th>工时核对时间</th>
											<th>工时</th>
											<th>项目</th>
											<th>加班详细</th>
											<th>核对工时</th>
											<th>刷卡记录</th>
										</tr>
									</thead>
									<tbody>
									
									<c:forEach items="${dtos}" var="item">
											<tr>
											<td>${item.user.chinesename}</td>
											<td>${item.dailyReport==null?
													"<span class='label label-important'>未填写</span>":"<span class='label label-success'>已填写</span>"}</td>
											<td><fmt:formatDate value="${item.dailyReport.createDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${item.attenceOverTime==null?
											"<span class='label label-important'>未核对</span>":"<span class='label label-success'>已核对</span>"}</td>
											<td><fmt:formatDate value="${item.attenceOverTime.saveTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${item.dailyReport.spendHours}</td>
											<td>${item.dailyReport.project.projectName}</td>
											<td>${item.dailyReport.summary}</td>
											<td>${item.attenceOverTime.checkHours}</td>
											<td>${item.attenceBrushRecord.brushData}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
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
											<td> <fmt:formatDate value="${item.createDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${item.step.name}</td>
											<td>${item.step.owner.chinesename}</td>
											<td>${item.approvel}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
								
								<c:if test="${acitons.size()!=0}">
								<form  id='reimburseform' class="form-horizontal " method="post" action="${pageContext.request.contextPath}/approve/myapprove/doApproveReimburse/${reimburse.wfentry.id}">
									<input type="hidden" name='actionId' value="">
									<fieldset class="default">
										<legend>审批意见</legend>
										<c:if test="${step.stepId==4}">
											<div class="control-group" >
												<label class="control-label">实付金额</label>
												<div class="controls">
													<div class="input-prepend input-append">
													  <span class="add-on">&#65509</span>
													  <input  type="text" name="cash"  value="${reimburse.reimburseMoney}">
													  <span class="add-on">.00</span>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${step.stepId>=3}">
										<div class="control-group">
											<label class="control-label">审批意见</label>
											<div class="controls">
												<textarea  name="approvals" rows="3" class="span12"></textarea>
											</div>
										</div>
										</c:if>
										<div class="control-group">
											<div class="controls">
													<c:forEach items="${acitons}" var="item">
														<button type="button" onclick="$.ims.myApprove.submitReimburseform(${item.key})" class="btn">${item.value}</button>
													</c:forEach>
											</div>
										</div>
									</fieldset>
								</form>
								</c:if>
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