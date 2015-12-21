<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.weekreportstate.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<head>
<script type="text/javascript">
$(function(){
	$.ims.weekReportState.initWeekSelect();
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
					<div class="content-widgets light-gray">
						<div class="widget-head  bondi-blue" >
							<h3>周报提交统计</h3>
						</div>
						<div class="box well form-inline">
							<label class="control-lableName">周次：</label>
								<select id='week'>
									<option value=""></option>
								</select>
							<label class="control-lableName">状态：</label>
							<select id='state' class='span1'>
								<option value=""></option>
								<option value="0">未提交</option>
								<option value="1">已提交</option>
							</select>
							<a id="btnCreate" class="btn btn-info right" onclick="$.ims.weekReportState.initWeekReportstate()">
							<i class="icon-search icon-white"></i> 查询
							</a>
						</div>
						<div class="span5" style="margin-left:auto;">
							<table id='table_weekreportstate' class="table-hover responsive table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th>工号</th>
										<th>姓名</th>
										<th>提交状态</th>
									</tr>
								</thead>
								<tfoot>
										<tr class="invoice-cal">
											<td  colspan="2">
												<span  class='totalcount_span' style="text-align: right;">应提交人数：</span>
												<span  class='submitcount_span' style="text-align: right;">已提交人数：</span>
												<span  class='nosubmitcount_span' style="text-align: right;">未提交人数：</span>
											</td>
											<td>
												<span  class='totalcount_span' style="text-align: left;" id="totalCount"></span>
												<span  class='submitcount_span'  style="text-align: left;" id="submitCount"> </span>
												<span  class='nosubmitcount_span'  style="text-align: left;" id="noSubmitCount"></span>
											</td>
										</tr>
									</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>