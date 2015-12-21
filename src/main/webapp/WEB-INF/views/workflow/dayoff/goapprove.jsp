<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@page import="com.hsae.ims.utils.RightUtil"%>
	<%@ include file="../../common/meta.jsp"%>
	<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
	<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.dayoff.js"></script>
	
	<style type="text/css">
	.table td{
	vertical-align: middle;
	}
	input[type="radio"]{
	  vertical-align: top;
	}
	</style>
<head>
	
	<script type="text/javascript">
		$(function() {
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
			$("#dayoffform input").attr("readonly","readonly");
			$("#dayoffform textarea").attr("readonly","readonly");
			
			$("#dayoffform textarea").each(function () { 
				$(this).html($.trim($(this).html()));
				  } );
				  
			
			$(".date").datetimepicker({
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
			$(".form_datetime").datetimepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd hh:ii',
				autoclose : 1,
				todayHighlight : 1,
				minuteStep:30,
				forceParse : 0
			});
			
			$("#workflowform").validate({
				errorPlacement: function(error, element) {
					$( element ).closest(".controls").append( error );
				},
				ignore:"",
				rules : {
					spendHours:{required:true,number:true},
					endTime : "required",
					startTime : "required",
					dayoffType: "required",
					approvals: "required"
				},
				messages : {
					spendHours: {required:"请填写工时",number:"工时必须是个数字"},
					endTime : "请填写结束时间",
					startTime:"请填写开始时间",
					dayoffType: "请选择假别",
					approvals: "请填写${workFlowDayoff.wfentry.currentStep.stepId==1?"备注":"审批意见"}"
				}
			});
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
						<div class="content-widgets ">
						<div class="widget-head  bondi-blue">
							<h3>请假单审批</h3>
						</div>
						<div class="widget-container">
							<form id='dayoffform' class="form-horizontal " method="post" action="workflow/dayoff/save">
								<table id='' class=" responsive table table-striped  table-condensed formtable table_bordered_black" >
									<tr >
										<td colspan="6">
											<div  class="table_title">
												 <h3>员 工 请 假 单</h3> 
											</div>
										</td>
									</tr>
									<tr>
										<td class='lable'>部门</td>
										<td>${workFlowDayoff.user.dept.name }</td>
										<td class='lable'>姓名：</td>
										<td>${workFlowDayoff.user.chinesename }</td>
										<td class='lable'>工号：</td>
										<td><input type="text" value="${workFlowDayoff.user.empnumber }" readonly="readonly"></input> </td>
									</tr>
									<tr>
										<td class='lable'>工作接替人：</td>
										<td>${workFlowDayoff.proxy.chinesename}</td>
										<td class='lable'>直属产品经理：</td>
										<td>${leader.chinesename}</td>
										<td class='lable'>联系电话：</td>
										<td><input name='tel' readonly="readonly" type="text" value="${workFlowDayoff.tel }"></input> </td>
									</tr>
									<tr>
										<td class='lable'>请假类别：</td>
										<td colspan="5" >
											<div>
												<c:forEach items="${dayyOffTypes }" var="bean">
												<label class="radio inline">
												<c:if test="${bean.code== workFlowDayoff.dayoffType}">
												 	<input readonly="readonly"  checked="checked" type="radio" name='dayoffType' value="${bean.code}">${bean.name}
												</c:if>
												
												<c:if test="${bean.code!= workFlowDayoff.dayoffType}">
												 	<input readonly="readonly" type="radio" name='dayoffType' value="${bean.code}">${bean.name}
												</c:if>
													 
												</label>
												</c:forEach>
											</div>
										 </td>
									</tr>
									<tr>
										<td class='lable'>请假时间：</td>
										<td colspan="2" >
										<span class='lable'>自</span>
										<div class="input-append">
										 <input style="width: 115px" disabled="disabled" value="<fmt:formatDate value="${workFlowDayoff.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>" id="dayoff_startTime"  name="startTime" type="text" readonly="readonly" class="form_datetime">
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<span class='lable'>	至</span>
											<div class="input-append">
											 <input style="width: 115px" disabled="disabled" value="<fmt:formatDate value="${workFlowDayoff.endTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>" id="dayoff_endTime" name="endTime" type="text" readonly="readonly" class="form_datetime">
											 <span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										<td colspan="3"><span class='lable'>总共请假</span><input readonly="readonly" name='days' style="width: 100px" type="text" value="${workFlowDayoff.days}"  ></input> <span class='lable'> 天</span> <input readonly="readonly"  name='hours'  style="width: 100px"  type="text" value="${workFlowDayoff.hours}"  ></input> <span class='lable'> 小时</span></td>
									</tr>
									<tr>
									</tr>
									<tr >
										<td class='lable'>请假事由：</td>
										<td colspan="5" ><textarea  readonly="readonly" name='remark' style="width: 90%" rows="2" cols="">${workFlowDayoff.remark}</textarea> </td>
									</tr>
									<tr >
										<td class='lable'>产品经理：</td>
										<td colspan="5" >
										<textarea  name='approve1' style="width: 90%" rows="2" cols="">
										<c:if test="${approval1!=null}">
										 ${approval1.approvel }-- ${approval1.step.owner.chinesename }  ${approval1.action }(<fmt:formatDate value="${approval1.createDate }" pattern="yyyy-MM-dd HH:mm"/> )
										</c:if>
										</textarea>
										 </td>
									</tr>
									
									<tr >
										<td class='lable'>申请部门意见：</td>
										<td colspan="5" >
										<textarea  name='approve2' style="width: 90%" rows="2" cols=""> 
											<c:if test="${approval2!=null}">
													 ${approval2.approvel }-- ${approval2.step.owner.chinesename }  ${approval2.action }(<fmt:formatDate value="${approval2.createDate }" pattern="yyyy-MM-dd HH:mm"/> )
											</c:if>
										</textarea>
										 </td>
									</tr>
									<tr >
										<td class='lable'>领导意见：</td>
										<td colspan="5" >
										<textarea readonly="readonly"  name='approve3' style="width: 90%" rows="2" cols="">
										  <c:if test="${approval3!=null}">
												${approval3.approvel }-- ${approval3.step.owner.chinesename }  ${approval3.action }(<fmt:formatDate value="${approval3.createDate }" pattern="yyyy-MM-dd HH:mm"/> )
											</c:if>
										</textarea>
										 </td>
									</tr>
									<tr >
										<td class='lable'>人事意见：</td>
										<td colspan="5" ><textarea readonly="readonly"  name='approve4' style="width: 90%" rows="2" cols="">
										
										<c:if test="${approval4!=null}">
															${approval4.approvel }-- ${approval4.step.owner.chinesename }  ${approval4.action }(<fmt:formatDate value="${approval4.createDate }" pattern="yyyy-MM-dd HH:mm"/> )
										
											</c:if>
										</textarea> </td>
									</tr>
									<tr class='remark'>
										<td colspan="6">
											<ol>
											  <li>凡请病假者，需提供镇级以上医院证明。</li>
											  <li>凡请婚假者，需提供结婚证原件及复印件，由HR验原件留复印件。</li>
											  <li>凡请调休假者，加班必须事先已经完成，而且在HR已经有各级领导审批通过的加班申请单相核对，方可按当日实际出勤打卡时间对调调休，不足1小时不计加班也免予调休。</li>
											  <li>凡请产假者，休产假前需提供有效的准生证明，方可按相关政策按有薪计算，否则按无薪事假处理。</li>
											  <li>工伤假凭工伤申报单享受工伤待遇。</li>
											  <li>凡请假满三天以上者需公司领导加签，HR审核后方可生效。</li>
											  <li>未提供以上相关证明者按无薪事假计算。</li>
											</ol>
										</td>
									</tr>
								</table>
							</form>
							
							<div class="widget-header-block">
									<h4 class="widget-header">审批信息</h4>
								</div>
								<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed ">
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
								<form  id='workflowform' class="form-horizontal " method="post" action="${pageContext.request.contextPath}/workflow/dayoff/doApprove/${workFlowDayoff.wfentry.id}">
									<input type="hidden" name='actionId' value="">
									<fieldset class="default">
										<legend>${workFlowDayoff.wfentry.currentStep.stepId==1?"备注":"审批意见"}</legend>
										<c:if test="${workFlowDayoff.wfentry.currentStep.stepId==6}">
										<div class="control-group">
											<label for="type" class="control-label"><span class="error">* </span>开始时间</label>
											<div class="controls">
												<div class="input-append">
													 <input value="<fmt:formatDate value="${workFlowDayoff.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>"  name="startTime" type="text" readonly="readonly" class="form_datetime">
													 <span class="add-on"><i class="icon-th"></i></span>
												</div>
											</div>
										</div>
			
										<div class="control-group">
											<label for="type" class="control-label"><span class="error">* </span>结束时间</label>
											<div class="controls">
												<div class="input-append">
													 <input value="<fmt:formatDate value="${workFlowDayoff.endTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>" name="endTime" type="text" readonly="readonly" class="form_datetime">
													 <span class="add-on"><i class="icon-th"></i></span>
												</div>
											</div>
										</div>
			                           
			                            <div class="control-group">
											<label for="type" class="control-label"><span class="error">* </span>工时共计</label>
											<div class="controls">
												<input value="${workFlowDayoff.days*8+workFlowDayoff.hours }" type="text"  name="spendHours" class=" span8 modal_datetime" style="width: 253px;">
											</div>
										</div>
										
										<div class="control-group">
										<label for="type" class="control-label"><span class="error">* </span>假别</label>
										<div class="controls">
											<select  name="dayoffType" style="width: 253px;">
												<option value=""></option>
											<c:forEach items="${dayyOffTypes}" var="bean">
												<c:if test="${bean.code== workFlowDayoff.dayoffType}">
												 	<option selected="selected" value="${bean.code}">${bean.name }</option>
												</c:if>
												<c:if test="${bean.code!= workFlowDayoff.dayoffType}">
												 	<option  value="${bean.code}">${bean.name }</option>
												</c:if>
											</c:forEach>
											</select>
										</div>
									</div>
										</c:if>
										<div class="control-group">
											<label class="control-label"><span class="error">* </span>${workFlowDayoff.wfentry.currentStep.stepId==1?"备注":"审批意见"}</label>
											<div class="controls">
												<textarea  name="approvals" rows="3" class="span12"></textarea>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
													<c:forEach items="${acitons}" var="item">
														<button type="button" onclick="$.ims.workflowdayoff.submitForm(${item.key})" class="btn btn-primary">${item.value}</button>
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