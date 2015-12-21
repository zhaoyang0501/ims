<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.myapprove.js"></script>
<head>
<script type="text/javascript">
$(function(){
	$(".controls .btn").eq(0).addClass("btn-primary");
	$(".td_summary").each(function () { 
		  var substr=$(this).html().length>20?$(this).html().substring(0, 20) + "...":$(this).html();
		 $(this).html("<span  data-rel='popover' data-content='"+$(this).html()+"' title='日报详情'>"+substr+"</span>");
	 } );
	$('[rel="popover"],[data-rel="popover"]').popover();
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
								<h3>周报审批</h3>
							</div>

							<div class="widget-container">
								<div class="widget-header-block">
									<h4 class="widget-header">${weekReport.creater.chinesename}${weekReport.week.year }年第${weekReport.week.weekNum }周周报</h4>
								</div>
								<div class="row-fluid">
		                        <div class="span6 content-box" >
		                        	<table class="table table-bordered table-condensed">
		                        		<tr>
		                        			<td>周次：</td><td>${weekReport.week.year } 年，第${weekReport.week.weekNum } 周</td>
		                        			<td>日期：</td>
		                        			<td>
			                        			<fmt:formatDate pattern="yyyy-MM-dd" value="${weekReport.week.startDate}" /> ~
												<fmt:formatDate pattern="yyyy-MM-dd" value="${weekReport.week.endDate}" />
											</td>
		                        		</tr>
		                        		<tr>
		                        			<td>提交日期：</td>
		                        			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${weekReport.createDate}" /></td>
		                        			<td>总工时：</td>
		                        			<td>${totalHours}小时</td>
		                        		</tr>
		                        		<tr>
		                        			<td>周总结：</td><td colspan="3">${weekReport.remark}</td>
		                        		</tr>
		                        	</table>
									
								</div>
								</div>
								<div class="widget-header-block">
									<h4 class="widget-header">周报详情</h4>
								</div>
								
								<table id='dt_weekReport' class="responsive table table-striped table-bordered">
									<thead>
										<tr>
											<th>日报日期</th>
											<th>星期</th>
											<th>类型</th>
											<th>项目</th>
											<th>日报详情</th>
											<th>异常情况/工作难点</th>
											<th>工时(时)</th>
											<th>工作阶段</th>
											<th>创建日期</th>
											<th>创建者</th>
										</tr>
									</thead>
									
									<tbody>
										<c:forEach items="${dailyReports}" var="bean">
											<tr>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${bean.reportDate}" /></td>
												<td>${bean.week}</td>
												<td> ${bean.type}</td>
												<td>${bean.project.projectName}</td>
												<td class="td_summary">${bean.summary}</td>
												<td>${bean.difficulty}</td>
												<td>${bean.spendHours}</td>
												<td>${workSteps.get(bean.projectStep)}</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd" value="${bean.createDate}" /></td>
												<td>${bean.user.chinesename}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								
								<c:if test="${acitons.size()!=0}">
									<form  id='weekreportform' class="form-horizontal " method="post" action="${pageContext.request.contextPath}/approve/myapprove/doApproveWeekReport/${weekReport.id}">
									<input type="hidden" name='actionId' value="">
									<input type="hidden" name='weekReportId' value="${weekReport.id}">
										<fieldset class="default">
											<legend>审批意见</legend>
											<div class="control-group">
												<label class="control-label">审批意见</label>
												<div class="controls">
													<textarea  name='approvals' id="approvals" rows="3" class="span12"></textarea>
												</div>
											</div>
											<div class="control-group">
												<div class="controls">
														<c:forEach items="${acitons}" var="item">
																<button type="button" onclick="$.ims.myApprove.submitWeekReportform(${item.key})" class="btn">${item.value}</button>
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