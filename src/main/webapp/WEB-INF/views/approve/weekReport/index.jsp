<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.weekreport.js"></script>
<head>
<script type="text/javascript">
$(function(){
	jQuery.ims.approveWeekReport.initweekReportToApprove();
	jQuery.ims.approveWeekReport.initweekReportApproved();
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
							<div class="widget-head  bondi-blue" >
								<h3>周报审批</h3>
							</div>
						</div>
					</div>
				</div>
				
					<div class="row-fluid">
										<div class="tab-widget">
											<ul class="nav nav-tabs" id="myTab2">
												<li class="active"><a href="#toapprove"><i class="icon-edit"></i> 待处理</a></li>
												<li class=""><a href="#approved"><i class="  icon-check"></i> 已处理</a></li>
											</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="toapprove">
												<table id='table_weekreport_toapprove' class="responsive table table-striped table-bordered">
														<thead>
															<tr>
																<th>ID</th>
																<th>姓名</th>
																<th>周次</th>
																<th>开始日期</th>
																<th>结束日期</th>
																<th>提交日期</th>
																<th>审批意见</th>
																<th>退回次数</th>
																<th>状态</th>
																<th>所在节点</th>
																<th>操作</th>
															</tr>
														</thead>
												</table>
											</div>
											<div class="tab-pane" id="approved">
												<table id='table_weekreport_approved' class="responsive table table-striped table-bordered">
														<thead>
															<tr>
																<th>ID</th>
																<th>姓名</th>
																<th>周次</th>
																<th>开始日期</th>
																<th>结束日期</th>
																<th>提交日期</th>
																<th>审批意见</th>
																<th>退回次数</th>
																<th>状态</th>
																<th>所在节点</th>
															</tr>
														</thead>
												</table>
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