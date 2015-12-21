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
	#planUserIds,#realUserIds{
		width: 80%;
	}
	#dayoffform input{
		width: 118px;
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
			
			jQuery.ims.workflowdayoff.dayoffform= $("#dayoffform").validate({
				errorPlacement: function(error, element) {
					if($("input[name=dayoffType]").hasClass("error"))
						$("input[name=dayoffType]").parent().parent().addClass("error");
					else 
						$("input[name=dayoffType]").parent().parent().removeClass("error");
				},
				ignore:"",
				rules: {
					startTime:"required",
					endTime:"required",
					remark:"required"
					}
				
			});
			$(".date").datetimepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd ',
				autoclose : 1,
				todayHighlight : 1,
				minuteStep:30,
				forceParse : 0
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
							<h3>培训计划</h3>
						</div>
						<div class="widget-container">
							<form  class="form-horizontal " method="post" 
							action="${pageContext.request.contextPath}/workflow/plan/doapprove/${task.id}/${prcessInstanceid}">
							<input type="hidden" name="pass" id='pass' value="true"/>
								<table id="" class=" responsive table table-striped table_bordered_black table-condensed formtable" >
									<tr>
										<td colspan="9">
											<div class="table_title">
												 <h3>培训记录表</h3> 
											</div>
										</td>
									</tr>
									
									<tr>
										<td class='lable'>来源：</td>
										<td  colspan="2"> 
											${plan.source }
										</td>
										<td class='lable'>培训类型：<span class="text-error">*</span></td>
										<td  colspan="2">${plan.trainingType } </td>
										<td class='lable'>编号：<span class="text-error">*</span></td>
										<td colspan="2"> 
											${plan.sn }
										</td>
									</tr>
									<tr>
										<td class='lable'>培训主题：</td>
										<td colspan="8" >
											${plan.name }
										 </td>
									</tr>
									<tr>
										<td class='lable'>培训内容：</td>
										<td colspan="8" >
											${plan.contents } 
										 </td>
									</tr>
									<tr>
										<td class='lable'>备注：</td>
										<td colspan="8" >
											${plan.remark }
										 </td>
									</tr>
									
									<tr>
										<td class='lable' rowspan="3">计划：</td>
										<td class='lable' >培训日期起：</td>
										<td >
											${plan.planStartTime}
										</td>
										<td class='lable' >培训日期止：</td>
										<td >
											${plan.planEndTime}
										</td>
										
										<td class='lable'>培训时间：</td>
										<td>
											${plan.planTime}
										</td>
										<td class='lable'>课时：</td>
										<td> 
										 	${plan.planHours}
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>预算：</td>
										<td> 
										 	${plan.planCost}
										</td> 
										
										<td class='lable'>讲师：</td>
										<td> 
										 	${plan.planTeacher.chinesename}
										</td> 
										
										<td class='lable'>考核方式：</td>
										<td> 
											${plan.planCheckType}
										</td> 
										
										<td class='lable'>设备器材：</td>
										<td> 
										 	${plan.planEquipment}
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>参与人员：</td>
										<td colspan="3"> 
										</td> 
										<td class='lable'>培训地点：</td>
										<td colspan="3"> 
										 		${plan.planAddr}
										</td> 
									</tr>
									<!-- 实际情况 -->
									<tr>
										<td class='lable' rowspan="3">实际：</td>
										<td class='lable' >培训日期起：</td>
										<td >
											${plan.realStartTime}
										</td>
										<td class='lable' >培训日期止：</td>
										<td >
											${plan.realEndTime}
										</td>
										
										<td class='lable'>培训时间：</td>
										<td>
											${plan.realTime}
										</td>
										<td class='lable'>课时：</td>
										<td> 
										 	${plan.realHours}
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>预算：</td>
										<td> 
										${plan.realCost}
										</td> 
										
										<td class='lable'>讲师：</td>
										<td> 
										 	${plan.realTeacher.chinesename }
										</td> 
										
										<td class='lable'>考核方式：</td>
										<td> 
											${plan.realCheckType}
										</td> 
										
										<td class='lable'>设备器材：</td>
										<td> 
										 	${plan.realEquipment}
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>参与人员：</td>
										<td colspan="3"> 
										</td> 
										<td class='lable'>培训地点：</td>
										<td colspan="3"> 
										 		${plan.realAddr}
										</td> 
									</tr>
									
									
									<!-- 培训材料 -->
									<tr>
										<td class='lable' rowspan="3">培训材料：</td>
										<td class='lable' >资料一：</td>
										<td colspan="7">
											<div class="input-append" >
												<input name='docurl1' type="text" >
												<button class="btn" type="button"><span class="icon-copy "></span></button>
											</div>
										</td>
									</tr>
									<tr>
										<td class='lable' >资料二：</td>
										<td colspan="7">
											<div class="input-append" >
												<input name='docurl2' type="text" >
												<button class="btn" type="button"><span class="icon-copy "></span></button>
											</div>
										</td>
									</tr>
									<tr>
										<td class='lable' >资料三：</td>
										<td colspan="7">
											<div class="input-append" >
												<input  name='docurl3'  type="text" >
												<button class="btn" type="button"><span class="icon-copy "></span></button>
											</div>
										</td>
									</tr>
									
									<tr  class='remark'>
										<td colspan="9">
											<ol>
											  <li>XX1Xx</li>
											  <li>XX2Xx</li>
											  <li>XX4Xx</li>
											  <li>XX3Xx</li>
											 
											</ol>
										</td>
									</tr>
								</table>
									<fieldset class="default">
										<legend>您的意见</legend>
										<div class="control-group">
											<label class="control-label">您的意见</label>
											<div class="controls">
												<textarea  name="approvals" rows="3" class="span12"></textarea>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
													<button type="submit" class="btn btn-primary" onclick="$('#pass').val('true')">发送</button>
													<button type="submit" class="btn btn-primary" onclick="$('#pass').val('false')">驳回</button>
													<a class="btn " type="button" href="workflow/dayoff">返回</a>
											</div>
										</div>
									</fieldset>
								
								<div class="widget-header-block">
									<h4 class="widget-header">流程信息</h4>
								</div>
								<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>流程节点</th>
											<th>处理人</th>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>处理意见</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${taskhistory}" var="item">
											<tr>
											<td>${item.name}</td>
											<td>${item.user==null?"":item.user.chinesename}</td>
											<td> <fmt:formatDate value="${item.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td> <fmt:formatDate value="${item.endTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${item.approves}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
							</form>
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