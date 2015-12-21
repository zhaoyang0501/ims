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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.absentee.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-switch.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-switch.css" rel="stylesheet">

<head>
<script type="text/javascript">
$(function(){
	$.ims.workflowabsentee.initworkflowabsenteeapprove("${osworkflow}");
	$(".noborder_input").each(function () { 
		$(this).val($.trim($(this).val()));
		  } );
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
});
</script>
<style type="text/css">
	
</style>
</head>
<body>

	<div class="layout">
		
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<input id="hf_requireId" type="hidden" value="${osworkflow}" />
		<input id="hf_operate" type="hidden" value="${operate}" />	<!-- 根据操作列是否可见 -->
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue">
								<h3>员工考勤补卡记录申请单</h3>
							</div>
							<div class="widget-container">
										<table  id='dt_absenteerequire_approve' class=" responsive table table-striped table_bordered_black table-condensed formtable" >
												<thead>
													<tr>
														<td colspan="8">
															<div  class="table_title">
																 <h3>员工考勤补卡记录申请单</h3> 
															</div>
															
															
															<div class="row-fluid ">
															   <div class="span4"><span class='lable'>部门：</span> <input  class='noborder_input' value="${absenteeWorkflow.user.dept.name}" type="text"> </div>
													          <div class="span4">	<span class='lable'>日期：</span> <input class='noborder_input'   value="<fmt:formatDate value="${absenteeWorkflow.createDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd"/>" type="text"> </div>
													          <div class="span4"><span class='lable'>编号：</span> <input value='${absenteeWorkflow.wfentry.wfentryExtend.sn}' class='noborder_input' type="text"> </div>
													        </div>
									        
															
														</td>
													</tr>
													
													
													
													
													<tr>
														<th class="lable">姓名 </th>
														<th class="lable">工号 </th>
														<th class="lable">漏打卡时间 </th>
														<th class="lable">考勤记录 </th>
														<th class="lable">漏打卡原因</th>
														<th class="lable">本月漏打卡次数</th>
														<th class="lable">备注</th>
														<th class="lable">是否有效</th>
													</tr>
												</thead>
												<tbody id ="dt_absenteerequire_approve_body">
												</tbody>
												 <tfoot>
												 	<tr>
													<td colspan="8">
														<span class='lable'>填表人：</span> <input value="${absenteeWorkflow.user.chinesename}" class='noborder_input' type="text"> 
														
														<span class='lable'>核准：</span> <input class='noborder_input'type="text" value="
															<c:if test="${approval2!=null}">
																	${approval2.step.owner.chinesename }  ${approval2.action }(<fmt:formatDate value="${approval2.createDate }" pattern="MM-dd HH:ss"/> )
														</c:if>										
														">  
														
														<span class='lable'>人力资源部审核：</span> <input class='noborder_input' type="text" value="
															<c:if test="${approval3!=null}">
																			${approval3.step.owner.chinesename }  ${approval3.action }(<fmt:formatDate value="${approval3.createDate }" pattern="MM-dd HH:ss"/> )
														</c:if>										
														">  
														</td>
													</tr>
												 </tfoot>
											</table>
											
											<div class="widget-header-block">
												<h4 class="widget-header" class="text-success">审批信息</h4>
											</div>
											<table id='dt_weekReport' class=" responsive table table-striped table-bordered table-condensed " >
												<thead>
													<tr>
														<th>时间</th>
														<th>流程节点</th>
														<th>动作</th>
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
												<form id="absenteeform" method="post" action='<c:url value="/workflow/absentee/approve/${absenteeWorkflow.id}" />'>
												
													<input type="hidden" name='actionId' value="">
													<input type="hidden" name='operate' value="">
													<fieldset class="default">
														<legend>${absenteeWorkflow.wfentry.currentStep.stepId==1?"备注":"审批意见"}</legend>
														
														<%-- <div class="tab-widget">
															<ul class="nav nav-tabs">
															  <li class="active">
															    <a href="#">胡景涛</a>
															  </li>
															  <li><a href="#">温家宝</a></li>
															  <li><a href="#">蒋介石</a></li>
															</ul>
															<div class="tab-content">
																<div class="tab-pane active" id="toapprove">
																	<div class="control-group">
																<label for="type" class="control-label"><span class="error">* </span>漏打卡时间</label>
																<div class="controls">
																	 <input value="2015-07-30 06:30" name="startTime" type="text">
																</div>
															</div>
								
															<div class="control-group">
																<label for="type" class="control-label"><span class="error">* </span>漏打卡类型</label>
																<div class="controls">
																	<select id="absentee_absenteeType" name="absenteeType">
																				<option value="">&nbsp;</option>
																				<option value="10">没带卡</option>
																				<option value="20">刷卡无记录</option>
																				<option value="30">忘记刷卡</option>
																				<option value="40">其他</option>
																			
																	</select>
																</div>
															</div>
															
															<div class="controls">
																	<button type="button" onclick="$.ims.workflowabsentee.approve(${item.key})" class="btn btn-primary">保存</button>
															</div>
															
																</div>
																<div class="tab-pane active" id="toapprove"></div>
															</div>	
														</div> --%>
															
														<div class="control-group">
															<label class="control-label">${absenteeWorkflow.wfentry.currentStep.stepId==1?"备注":"审批意见"}</label>
															<div class="controls">
																<textarea  name="approvals" rows="3" class="span12"></textarea>
															</div>
														</div>
														
														<div class="control-group">
															<div class="controls">
																	<c:forEach items="${acitons}" var="item">
																			<button type="button" onclick="$.ims.workflowabsentee.approve(${item.key})" class="btn btn-primary">${item.value}</button>
																		</c:forEach>
																	    <a class="btn" href='<c:url value="/workflow/absentee" />'>返 回</a>
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