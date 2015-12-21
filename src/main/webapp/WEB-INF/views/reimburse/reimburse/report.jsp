<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
	<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.reimburse.report.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
 <link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<head>
<script type="text/javascript">
$(document).ready(function() {
	$(".date").datetimepicker({
		language:  'zh-CN',
	    weekStart: 1,
	    todayBtn:  1,
	    format:'yyyy-mm',
		autoclose: 1,
		todayHighlight: 1,
		startView: 3,
		minView: 3,
		forceParse: 0
		
	});
	$("#project").chosen({
		no_results_text : " ",
		placeholder_text:" ",
		disable_search_threshold : 5
	});
	$.ims.reimburseReport.initReimburseReportByProjectTable();
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
							<div class="widget-head  bondi-blue" >
								<h3>餐费报销统计</h3>
							</div>
							<div class="tab-widget">
								<ul class="nav nav-tabs" id="myTab2">
									<li class="active"><a href="#toapprove"><i class="icon-edit"></i>按项目汇总</a></li>
									<li class=""><a href="#approved"><i class="icon-check"></i>按人汇总</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="toapprove">
										<div class="box well form-inline">
											<label>统计月份：</label>
											<div class=" input-append date">
												<input id="date_byproject" style="width: 120px;" type="text" value="${yearMonth }" readonly="readonly">
												<span class="add-on"><i class="icon-th"></i></span>
											</div>
											<label>项目：</label>
											<select id="project" style="width:150px;">
											 	<option value="">&nbsp;</option>  
											 	<c:forEach items="${projects}" var="bean">
														<option value="${bean.id}">${bean.projectName}</option>   
													</c:forEach>
											</select>
											<button class="btn btn-info right" type="button" onclick="$.ims.reimburseReport.initReimburseReportByProjectTable()">
												<i class="icon-search icon-white"></i> 查询
											</button>
										</div>
										<table id='table_byProject' class="responsive table table-striped table-bordered table-condensed">
											<thead>
												<tr>
													<th>所属项目</th>
													<th>加班日期</th>
													<th>餐别</th>
													<th>人员姓名</th>
													<th>人数</th>
													<th>订餐标准</th>
													<th>合计</th>
												</tr>
											</thead>
											<tfoot>
												<tr><th style=" text-align:right;padding-right: 60px" colspan="10" rowspan="1">总金额:<span id="foot_total_project"></span></th></tr>
											</tfoot>
										</table>
									</div>
									<div class="tab-pane" id="approved">
									<div class="box well form-inline">
											<label>统计月份：</label>
											<div class=" input-append date">
												<input id="date_byuser" style="width: 120px;" type="text" value="${yearMonth }" readonly="readonly"> 
												<span class="add-on"><i class="icon-th"></i></span>
											</div>
		
											
											<button  class="btn btn-info right" type="button" onclick="$.ims.reimburseReport.initReimburseReportByUserTable()">
												<i class="icon-search icon-white"></i> 查询
											</button>
										</div>
										<table id='table_byUser'class="responsive table table-striped table-bordered table-condensed">
											<thead>
												<tr>
													<th>所属部门</th>
													<th>姓名</th>
													<th>总金额</th>
													<th>备注</th>
												</tr>
											</thead>
											<tfoot>
												<tr><th style=" text-align:right;padding-right: 60px" colspan="10" rowspan="1">总金额:<span id="foot_total_user"></span></th></tr>
											</tfoot>
										</table>
									</div>
								</div><!-- end tab-content -->
							</div><!-- end tab-widget -->
						</div><!--end  content-widgets -->
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>