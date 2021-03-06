<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.constants.*,java.util.*"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.myapprove.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<head>
<script type="text/javascript">
$(function(){
	$.ims.myApprove.initToApprove();
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

	$(".workFlowName").chosen({
		allow_single_deselect : true
	});
	$(".workState").chosen({
		allow_single_deselect : true
	});
	<c:if test="${response!=null&&response.code=='1'}">
		noty({"text":"操作成功","layout":"top","type":"success","timeout":"2000"});
	</c:if>
});
</script>

<style type="text/css"> 
     .tab-content { overflow: hidden;} 
</style> 
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
								<h3>我的待办</h3>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="tab-widget">
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#toapprove"><i class="icon-edit"></i> 待处理</a></li>
							<li class=""><a href="#approved"><i class="icon-check"></i>已处理</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="toapprove">
								<div class="box well form-inline">
									<label>收到日期：</label>
									<div class=" input-append date">
										<input id="toapprove_startDate" style="width: 120px;" type="text" value="" readonly="readonly"> 
										<span class="add-on"><i class="icon-th"></i></span>
									</div>

									<label class="control-lableName">~</label>
									<div class=" input-append date">
										<input id="toapprove_endDate" style="width: 120px;" type="text" value="" readonly="readonly">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>
									<label class="control-lableName">流程：</label>
									<select class='workFlowName' id="toapprove_workFlowName" style="width: 150px;">
										<option value="">&nbsp;</option>
										<c:forEach items="${workflowNames}" var="bean">
											<option value="${bean.key}">${bean.value}</option>
										</c:forEach>
									</select>
									<button  class="btn btn-info right" type="button" onclick="$.ims.myApprove.initToApprove()">
										<i class="icon-search icon-white"></i> 查询
									</button>
								</div>
								<table id='table_toapprove' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>事项</th>
											<th>创建人</th>
											<th>收到时间</th>
											<th>当前步骤</th>
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="tab-pane" id="approved">
							<div class="box well form-inline">
									<label>收到日期：</label>
									<div class=" input-append date">
										<input id="approved_startDate" style="width: 120px;" type="text" value="" readonly="readonly">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>

									<label class="control-lableName">~</label>
									<div class=" input-append date">
										<input id="approved_endDate" style="width: 120px;" type="text" value="" readonly="readonly">
										<span class="add-on"><i class="icon-th"></i></span>
									</div>
									<label class="control-lableName">状态</label>
									<select  class='workState' id="approved_workFlowState" style="width: 150px;">
									    <option value="">&nbsp;</option>
										<option value="4">已办结&nbsp;</option>
										<option value="1">未办结&nbsp;</option>
									</select>
									<label class="control-lableName">流程：</label>
									<select  class='workFlowName' id="approved_workFlowName" style="width: 150px;">
										<option value="">&nbsp;</option>
										<c:forEach items="${workflowNames}" var="bean">
											<option value="${bean.key}">${bean.value}</option>
										</c:forEach>
									</select>
									<button  class="btn btn-info right" type="button" onclick="$.ims.myApprove.initapproved()">
										<i class="icon-search icon-white"></i> 查询
									</button>
								</div>
								<table id='table_approved'class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>事项</th>
											<th>创建人</th>
											<th>收到时间</th>
											<th>当前步骤</th>
											<th>状态</th>
											<th>操作</th>
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