<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.weekreportexport.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<head>
<script type="text/javascript">
$(function(){
	$.ims.weekReportExport.initWeekSelect();
	 $("#state").chosen({
			no_results_text : " ",
			placeholder_text:" ",
			disable_search_threshold : 5,
			allow_single_deselect: true
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
									<h3>周报导出</h3>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row-fluid ">
							<div class="box well form-inline">
								<label class="control-lableName">周次：</label>
								<select id='week'>
									<option value=""></option>
								</select>
								<button id="btnCreate" class="btn btn-info right" onclick="$.ims.weekReportExport.initSearchDataTable()">
									<i class="icon-search icon-white"></i> 查询
								</button>
								<button id="btnCreate" class="btn btn-info right" onclick="$.ims.weekReportExport.exportWeekReport()">
									<i class=" icon-download-alt icon-white"></i> 导出已提交周报
								</button>
							</div>
							<table id='dt_dailyReport'
									class="table-hover responsive table table-striped table-bordered table-condensed">
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
													<th >创建者</th>
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