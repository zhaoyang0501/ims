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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.overtime.js"></script>
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
	$(".noborder_input").each(function () { 
		$(this).val($.trim($(this).val()));
		  } );
});
</script>
</head>
<body>

	<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<input id="hf_requireId" type="hidden" value="${osworkflow}" />
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue">
								<h3>员工加班申请单</h3>
							</div>
							<div class="widget-container">
								<table id='dt_overtimerequire' class=" responsive table table-striped table_bordered_black table-condensed formtable">
									<tbody >
									<tr >
										<td colspan="8">
											<div  class="table_title">
												 <h3>员工加班申请单</h3> 
											</div>
											
											 <div class="row-fluid ">
									          <div class="span4"><span class='lable'>部门：</span> <input  class='noborder_input' value="${workFlowOverTime.user.dept.name}" type="text"> </div>
									          <div class="span4">	<span class='lable'>申请日期：</span> <input class='noborder_input'   value="<fmt:formatDate value="${workFlowOverTime.applyDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd"/>" type="text"> </div>
									          <div class="span4"><span class='lable'>编号：</span> <input value='${workFlowOverTime.wfentry.wfentryExtend.sn}' class='noborder_input' type="text"> </div>
									        </div>
										</td>
									</tr>
									<tr>
											<td class='lable'>工号 </td>
											<td class='lable'>姓名 </td>
											<td class='lable'>职位 </td>
											<td class='lable'>加班开始时间 </td>
											<td class='lable'>加班结束时间</td>
											<td class='lable'>计划工时</td>
											<td class='lable'>加班原因</td>
											<td class='lable'>备注</td>
										</tr>
									<c:forEach var="bean" items="${workFlowOverTime.workFlowOverTimeDetail }">
									<tr>
									    <td>${bean.user.empnumber}</td>
										<td>${bean.user.chinesename }</td>
										<td>${bean.position }</td>
										<td><fmt:formatDate value="${bean.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
										<td><fmt:formatDate value="${bean.endTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
										<td>${bean.hours}</td>
										<td>${bean.remark}</td>
										<td>${bean.footnote}</td>
									</tr>
									</c:forEach>
									<tr  class='remark'>
											<td colspan="8">备注:
										  	<ol>
										  	<li>项目人员请选择产品经理。</li>
											  <li>此申请表必须于每日17:00交人力资源部备案抽查。</li>
											  <li>加班完成后请及时填写加班报告。</li>
											</ol>
											</td>
									</tr>
									
									<tr>
									<td colspan="8">
										<span class='lable'>申请人：</span> <input value="${workFlowOverTime.user.chinesename}" class='noborder_input' type="text"> 
										<span class='lable'>产品经理：</span> <input class='noborder_input' type="text" value="
											<c:if test="${approval2!=null}">
												${approval2.step.owner.chinesename }  ${approval2.action }(<fmt:formatDate value="${approval2.createDate }" pattern="MM-dd HH:ss"/> )
										</c:if>										
										">  
										<span class='lable'>主管：</span> <input class='noborder_input'type="text" value="
											<c:if test="${approval3!=null}">
													${approval3.step.owner.chinesename }  ${approval3.action }(<fmt:formatDate value="${approval3.createDate }" pattern="MM-dd HH:ss"/> )
										</c:if>										
										">  
										
										<span class='lable'>人事：</span> <input class='noborder_input' type="text" value="
											<c:if test="${approval6!=null}">
															${approval6.step.owner.chinesename }  ${approval6.action }(<fmt:formatDate value="${approval6.createDate }" pattern="MM-dd HH:ss"/> )
										</c:if>										
										">  
										</td>
									</tr>
									</tbody>
								</table>
									<div class="widget-header-block">
									<h4 class="widget-header">审批信息</h4>
								</div>
								<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed">
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
											<td> <fmt:formatDate value="${item.createDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${item.step.name}</td>
											<td>${item.action}</td>
											<td>${item.step.owner.chinesename}</td>
											<td>${item.approvel}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
							<c:if test="${acitons.size()!=0}">
								<form  id='workflowform' class="form-horizontal " method="post" action="${pageContext.request.contextPath}/workflow/overtime/doApprove/${workFlowOverTime.wfentry.id}">
									<input type="hidden" name='actionId' value="">
									<fieldset class="default">
										<legend>${workFlowOverTime.wfentry.currentStep.stepId==1?"备注":"审批意见"}</legend>
										
										<%-- <table class='responsive table table-striped table_bordered_black table-condensed formtable'>
											<thead>
												<tr>
													<th>姓名</th>
													<th>工号</th>
													<th>加班日期</th>
													<th>刷卡记录</th>
													<th>实际加班时间</th>
													<th>实际工时</th>
													<th>类型</th>
													<th>OA状态</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach var="bean" items="${workFlowOverTime.workFlowOverTimeDetail }" varStatus="status">  
												<tr>
													<td>${bean.user.chinesename }</td>
													<td>${bean.user.empnumber}</td>
													<td><fmt:formatDate value="${bean.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd "/></td>
													<td><input name='overtimes[${status.index}].brushData' type="text" style="width: 80px"></td>
													<td><input  name='overtimes[${status.index}].startTime' type="text" style="width: 80px">~<input name='overtimes[${status.index}].endTime' type="text" style="width: 80px"> </td>
													<td><input name="overtimes[${status.index}].checkHours"  type="text" style="width: 80px"></td>
													<td>
														<select  name="overtimes[${status.index}].overtimeType"   id="type" class="span8">
															<option value="1">平时</option>
															<option value="2">周末</option>
															<option value="3">节假日</option>
														</select>
													</td>
													<td>
													<select id="oaState" name="overtimes[${status.index}].oaState"  class="span8">
														<option value="1">已提交</option>
														<option value="2">未提交</option>
													</select>
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
										 --%>
										
										<div class="control-group">
											<label class="control-label">${workFlowOverTime.wfentry.currentStep.stepId==1?"备注":"审批意见"}</label>
											<div class="controls">
												<textarea  name="approvals" rows="3" class="span12"></textarea>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
													<c:forEach items="${acitons}" var="item">
														<button type="button" onclick="$.ims.workflowovertime.submitForm(${item.key})" class="btn btn-primary">${item.value}</button>
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