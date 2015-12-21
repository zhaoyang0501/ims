<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.report.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
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
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>员工培训信息统计</h3>
							</div>
							<div class="box well form-inline">
								<span style="margin-left: 20px;">培训日期：</span>
								<div class=" input-append date">
									<input id="from_date" value="${fromDate }" style="width: 120px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								~ 
								<div class=" input-append date">
									<input id="to_date" value="${toDate }" style="width: 120px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<a style="margin-left: 20px;" onclick="$.ims.trainingreport.initStaffSummaryDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
							</div>
							<div class="widget-container">
								<table class="responsive table table-striped table-bordered"
									id="staffsummary_dataTable">
									<thead>
										<tr>
											<th>培训课题</th>
											<th>讲师</th>
											<th>讲师级别</th>
											<th>培训日期</th>
											<th>培训时间</th>
											<th>培训时长</th>
											<th>培训备注</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
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