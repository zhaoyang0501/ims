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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript">

	$(document).ready(function(){
		$(".date").datetimepicker({
			language:  'zh-CN',
	        format:'yyyy',
			autoclose: 1,
			startView: 4,
			minView:4
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
								<h3>各科室培训统计</h3>
							</div>
							<div class="box well form-inline">
								<span style="margin-left: 20px;">年份：</span>
								<div class=" input-append date">
									<input id="year" value="${year }" style="width: 120px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<a style="margin-left: 20px;" onclick="$.ims.trainingreport.initDeptSummaryDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
							</div>
							<div class="widget-container">
								<table class="responsive table table-striped table-bordered table-condensed"
									id="deptsummary_dataTable">
									<thead>
										<tr>
											<th style="width: 200px;">部门</th>
											<th style="width: 100px;">部门人数</th>
											<th style="width: 100px;">人均课时(h)</th>
											<th style="width: 100px;">全年总课时(h)</th>
											<th style="width: 100px;">1-12月总课时(h)</th>
											<th style="width: 100px;">剩余课时(h)</th>
											<th style="width: 100px;">完成率</th>
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