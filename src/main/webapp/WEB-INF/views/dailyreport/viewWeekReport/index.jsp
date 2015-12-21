<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.viewweekreport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
 <script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
  <script src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script >
  
<head>
<script type="text/javascript">
$(function(){
	$.ims.common.findScopeUser(function(){
		$("#user").chosen({
			no_results_text :"没有找到这个员工",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"user");
	 $("#state").chosen({
			no_results_text : " ",
			placeholder_text:" ",
			disable_search_threshold : 5
		});
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
									<h3>周报查询</h3>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row-fluid ">
							<div class="box well form-inline">
								<label  class="control-lableName">提交日期：</label>
								<div class=" input-append date">
									 <input id="startDate" style="width:80px;" type="text" value="" readonly="readonly" >
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
								<label  class="control-lableName">~</label>
								<div class=" input-append date">
									 <input id="endDate"  style="width:80px;" type="text" value="" readonly="readonly" >
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
								<label  class="control-lableName">流程状态：</label>
								 <select id='state'  style="width:120px;">
								  <option value="">&nbsp;</option>
									 <option value="1">审批中</option>
									 <option value="4">已结束</option>
								 </select>
								<label  class="control-lableName">员工：</label>
								 <select id="user" style="width:120px;">
									 <option></option>
								 </select>
								<button id="btnCreate" class="btn btn-info right" type="button" onclick="$.ims.weekReportView.initSearchDataTable()">
								<i class="icon-search icon-white"></i> 查询
								</button>
							</div>
							<table id='dt_weekReport_view'
									class="table-hover responsive table table-striped table-bordered table-condensed">
										<thead>
												<tr>
													<th>发起人</th>
													<th>周次</th>
													<th>开始日期</th>
													<th>结束日期</th>
													<th>提交日期</th>
													<th>周总结</th>
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
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>