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
<style type="text/css">
	.table > thead > tr > th {
		text-align:center;
		background-color: #CCCCCC;
	}
</style>
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
								<h3>部门培训按期完成率</h3>
							</div>
							<div class="box well form-inline">
								<span style="margin-left: 20px;">培训日期：</span>
								<div class=" input-append date">
									<input id="from_date" value="${from_date }" style="width: 120px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								~
								<div class=" input-append date">
									<input id="to_date" value="${to_date }" style="width: 120px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<a style="margin-left: 20px;" onclick="$.ims.trainingreport.initDeptCompleteOntimeDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
							</div>
							<div class="widget-container">
								<table class="responsive table table-striped table-bordered table-condensed"
									id="deptcompleteontime_dataTable">
									<thead>
										<tr>
											<th rowspan="2" style="width:150px;">部门</th>
											<th colspan="2">计划内数量</th>
											<th colspan="2">按期完成数量</th>
											<th rowspan="2">完成率</th>
											<th rowspan="2">延期次数</th>
											<th rowspan="2" style="width:400px;">延期的培训</th>
										</tr>
										<tr>
											<th>内训</th>
											<th>外训</th>
											<th>内训</th>
											<th>外训</th>
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