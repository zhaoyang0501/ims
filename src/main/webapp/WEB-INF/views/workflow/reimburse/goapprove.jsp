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
								<h3>餐费报销审批</h3>
							</div>

							<div class="widget-container">
		                        	<table class="responsive table table-striped table_bordered_black table-condensed formtable">
		                        		<tr>
											<td colspan="6">
												<div  class="table_title">
													 <h3>餐费报销审批</h3> 
												</div>
												 <div class="row-fluid ">
									         	 <div class="span4">	<span class='lable'>日期：</span> <input class='noborder_input'   value="<fmt:formatDate value="${reimburse.createTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd"/>" type="text"> </div>
									          	<div class="span4"><span class='lable'>编号：</span> <input value='${reimburse.wfentry.wfentryExtend.sn}' class='noborder_input' type="text"> </div>
									       	 </div>
											</td>
										</tr>
										
										<tr>
											<td class='lable'>部门</td>
											<td>${reimburse.reimburser.dept.name }</td>
											<td class='lable'>姓名：</td>
											<td>${reimburse.reimburser.chinesename}</td>
											<td class='lable'>工号：</td>
											<td>${reimburse.reimburser.empnumber } </td>
										</tr>
										
										<tr>
											<td class='lable'>就餐日期：</td>
												<td > 
												<fmt:formatDate value="${reimburse.reimburseDate}" type="date" dateStyle="full"/>
												</td>
												<td class='lable'>餐别：</td>
												<td > 
												${reimburse.type==1?"午餐":"晚餐"}
												</td>
												
												<td class='lable'>报销金额：</td>
												<td > 
												${reimburse.reimburseMoney}
												</td>
										</tr>
										
										<tr>
											<td class='lable'>就餐人数：</td>
												<td > 
												${reimburse.number }人
												</td>
												<td class='lable'>就餐人员明细:</td>
												<td colspan="3"> 
													<c:forEach items="${dtos }" var="bean">
														${bean.user.chinesename},
													</c:forEach>
												</td>
										</tr>
										
										<tr>
		                        			<td class='lable'>备注：</td><td colspan="5">${reimburse.remark}</td>
		                        		</tr>
										<tr  class='remark'>
										<td colspan="6">
											<ol>
											  <li>加班餐费报销范围：上海技术中心扬州办公人员。</li>
											  <li>加班餐费报销标准：12元/人餐。</li>
											   <li>因出差人员有餐补，出差加班不可报销加班餐费。</li>
											  <li>加班餐费报销条件（需同时满足以下几点）：
											  <ol type="a">
												 <li>工作原因加班；</li>
												 <li>
												 	满足一定加班时长且跨过用餐时间，具体见下表：
												 	<table class='table_bordered_black responsive table table-striped  table-condensed formtable'>
												 		<tr>
												 		<td></td>
												 		<td>午餐</td>
												 		<td>晚餐</td>
												 		</tr>
												 		<tr>
												 		<td>工作日</td>
												 		<td></td>
												 		<td>加班2h（含）以上，且跨过晚餐时间</td>
												 		</tr>
												 		<tr>
												 		<td>休息日</td>
												 		<td>11:30之前开始加班，加班时间3h（含）以上，且跨过午餐时间（12:30）</td>
												 		<td>17:00之前开始加班，加班时间3h（含）以上，且跨过晚餐时间（18:00）
												 		</td>
												 		</tr>
												 	</table>
												 </li>
												 <li>IMS系统日报中填写加班内容；</li>
												 <li>订餐人在IMS系统填写加班餐费报销单。</li>
												</ol> 
											 </li>
											</ol>
										</td>
									</tr>
										
		                        	</table>
								
								<table id='dt_weekReport'  class="responsive table table-striped table-bordered table-condensed ">
										<caption> <h3>报销人员加班报告（附）</h3> </caption>
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
								<form  id='reimburseform' class="form-horizontal " method="post" action="${pageContext.request.contextPath}/workflow/reimburse/doApprove/${reimburse.wfentry.id}">
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
														<button type="button" onclick="$.ims.myApprove.submitReimburseform(${item.key})" class="btn btn-primary">${item.value}</button>
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