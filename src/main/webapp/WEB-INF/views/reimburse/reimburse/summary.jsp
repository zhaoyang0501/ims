<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.reimburse.summary.js?1=1"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<head>
<script type="text/javascript">
$(document).ready(function() {
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
	$("#type").chosen({
		allow_single_deselect : true
	});
	$("#step").chosen({
		allow_single_deselect : true
	});
	$.ims.common.findAllUser(function(){
		$("#user").chosen({
			no_results_text :"没有找到",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"user");
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
						<div class="content-widgets">
							<div class="widget-head  bondi-blue">
									<h3>餐费汇总查询</h3>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row-fluid ">
							<div class="box well form-inline">
								<span>报销日期：</span>
								<div class="input-append date">
									<input id="begin" type="text" value="${begin }" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<span >~</span>
								<div class="input-append date">
									<input id="end" type="text" value="${end }" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<label  class="control-lableName">报销人员：</label>
								<select id="user"></select>
								<label  class="control-lableName">餐费类别：</label>
								<select id="type">
									 <option value="">&nbsp;</option>
									 <option value="1">午餐</option>
									 <option value="2">晚餐</option>
								</select>
								<label  class="control-lableName">流程状态：</label>
								<select id="step">
									 <option value="">&nbsp;</option>
									 <c:forEach items="${steps }" var="bean">
									 	 <option value="${bean.id }">${bean.name }</option>
									 </c:forEach>
								</select>
								<a class="btn btn-info" type="button" onclick="$.ims.reimbursesummary.initReimburseDataTable()"><i class="icon-search"></i>查询</a>
							</div>
							<table id='reimburseDataTable' class="table-hover responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>报销日期</th>
											<th>报销人员</th>
											<th>餐费类别</th>
											<th>报销金额</th>
											<th>报销人数</th>
											<th>就餐人员明细</th>
											<th>备注</th>
											<th>实报金额</th>
											<th>付款日期</th>
											<th>创建日期</th>
											<th>状态</th>
											<th>当前步骤</th>
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>