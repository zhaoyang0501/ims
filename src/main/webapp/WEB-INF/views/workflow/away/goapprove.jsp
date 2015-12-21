<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.away.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<head>
<script type="text/javascript">
$(function(){
	$(".noborder_input").each(function () { 
		$(this).val($.trim($(this).val()));
		  } );
	
	$("#btn-submit").click(function(){
        $(this).button('提交中...').delay(1000).queue(function() {
          	$(this).button('reset');
        });        
    });
	
	<c:if test="${response!=null&&response.code=='1'}">
	<c:if test="${response.datas!=null}">
		noty({"text":"操作成功,流程已提交给${response.datas.chinesename}","layout":"top","type":"success","timeout":"10000"});
	</c:if>
	<c:if test="${response.datas==null}">
		noty({"text":"操作成功,流程已结束${response.datas.chinesename}","layout":"top","type":"success","timeout":"2000"});
	</c:if>
	</c:if>
	<c:if test="${response!=null&&response.code!='1'}">
		noty({"text":"${response.msg}","layout":"top","type":"error","timeout":"2000"});
	</c:if>
	if(${over == 1}){
		$("#remarks").attr("readonly","readonly");
	}else{
		$("#remarks").removeAttr("readonly");
	}
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red;
	}
	input[type="text"]{
		margin-bottom: 0px !important;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_cuid" value="${cuid }" />
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
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue">
								<h3>员工外出申请单</h3>
							</div>
							<div class="widget-container" >
								<table  id='dt_away' class=" responsive table table-striped table_bordered_black table-condensed formtable" >
												<tbody id ="dt_away_body">
													<tr >
														<td colspan="4">
															<div  class="table_title">
																 <h3>员 工 外出申请单</h3> 
															</div>
															 <div class="row-fluid ">
													          <div class="span4">	<span class='lable'>日期：</span> <input class='noborder_input'   value="<fmt:formatDate value="${workflowAway.fillDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd"/>" type="text"> </div>
													          <div class="span4"><span class='lable'>编号：</span> <input value='${workflowAway.wfentry.wfentryExtend.sn}' class='noborder_input' type="text"> </div>
													         </div>
														</td>
													</tr>
													
													<tr>
														<td colspan="4" style="padding: 0;">
															<table id='dt_away' class="responsive table  table-condensed" style="margin-bottom: 0;border-collapse: collapse;" >
																<thead>
																	<tr>
																		<td class='lable' width="150px;">姓名</td>
																		<td class='lable' width="150px;">工号 </td>
																		<td  class='lable' width="150px;">部门 </td>
																		<td class='lable' >职位 </td>
																	</tr>
																	<c:forEach items="${workflowAway.awayUser}" var="item">
																		<tr>
																			<td>${item.chinesename}</td>
																			<td>${item.empnumber}</td>
																			<td>${item.dept.name}</td>
																			<td>${item.position}</td>
																		</tr>
																	</c:forEach>
															</table>
														</td>
													</tr>
													
													
													<tr>
														<td class='lable'>外出时间：</td>
														<td colspan="3" >
															<input id="awayFrom" style="width: 120px;" type="text" value='<fmt:formatDate value="${workflowAway.awayFrom}" pattern="yyyy-MM-dd HH:mm"/>' readonly="readonly">
															至
															<input id="awayTo" style="width: 120px;" type="text" value='<fmt:formatDate value="${workflowAway.awayTo}" pattern="yyyy-MM-dd HH:mm"/>' readonly="readonly">
														</td>
													</tr>
													<tr><td class='lable'>外出地点：</td><td colspan="3"><input  style="width: 95%;" id="awayAddress" type="text" class="span12" value="${workflowAway.awayAddress }" readonly="readonly"/></td></tr>
													<tr><td class='lable'>外出原因：</td><td colspan="3"><input  style="width: 95%;" id="awayReason" type="text" class="span12" value="${workflowAway.awayReason }" readonly="readonly"/></td></tr>
													<tr>
														<td class='lable'>外(派)出车辆：</td><td><input style="width: 138px;" id="awayCar" type="text" value="${workflowAway.awayCar }" readonly="readonly" /></td>
														<td class='lable'>司  机：</td><td><input  style="width: 138px;" id="awayDriver" type="text" value="${workflowAway.awayDriver }" readonly="readonly" /></td>
													</tr>
												</tbody>
												<tfoot >
													<tr>
														<td colspan="4" >
															<span class='lable'>填表人：</span> <input value="${workflowAway.user.chinesename}" class='noborder_input' type="text"> 
														
														
														<span class='lable'>确认：</span> <input class='noborder_input'type="text" value="
															<c:if test="${approval2!=null}">
																			${approval2.step.owner.chinesename }  ${approval2.action }(<fmt:formatDate value="${approval2.createDate }" pattern="MM-dd HH:ss"/> )
														</c:if>										
														">  
														<span class='lable'>门卫：</span> <input class='noborder_input' type="text" value="
															<c:if test="${approval3!=null}">
																	${approval3.step.owner.chinesename }  ${approval3.action }(<fmt:formatDate value="${approval3.createDate }" pattern="MM-dd HH:ss"/> )
														</c:if>										
														">  
														
														<span class='lable'>人事：</span> <input class='noborder_input' type="text" value="
															<c:if test="${approval4!=null}">
															${approval4.step.owner.chinesename }  ${approval4.action }(<fmt:formatDate value="${approval4.createDate }" pattern="MM-dd HH:ss"/> )
														</c:if>										
														">  
														</td>
													</tr>
												</tfoot>
											</table>
											<div class="widget-header-block">
												<h4 class="widget-header" class="text-success">审批信息</h4>
											</div>
											<table id='dt_approvals' class=" responsive table table-striped table-bordered table-condensed " >
												<thead>
													<tr>
														<th>时间</th>
														<th>流程节点</th>
														<th >动作</th>
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
															<td>${item.action}</td>
															<td>${item.step.owner.chinesename}</td>
															<td>${item.approvel}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<c:if test="${acitons.size()!=0}">
											<form id="awayform"  class="form-horizontal " method="post" action='<c:url value="/workflow/away/approve/${workflowAway.id}" />'>
													<input type="hidden" name='actionId' value="">
													<input type="hidden" name='remarks' value="">
													<fieldset class="default">
													<legend>${workflowAway.wfentry.currentStep.stepId==1?"备注":"审批意见"}</legend>
										
														<div class="control-group">
															<label class="control-label">${workflowAway.wfentry.currentStep.stepId==1?"备注":"审批意见"}</label>
															<div class="controls">
																<textarea  id="approvals"   name="approvals" rows="3" class="span12"></textarea>
															</div>
														</div>
														
														<div class="control-group">
															<div class="controls">
																	<c:forEach items="${acitons}" var="item">
																			<button type="button" onclick="$.ims.workflowaway.approve(${item.key})" class="btn btn-primary">${item.value}</button>
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