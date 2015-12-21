<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.dailyreport.weekreport.js?t=13"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<head>
<script type="text/javascript">
	$(function() {
		$.ims.weekReport.queryCurrentWeek();
		
		$.ims.weekReport.weekreportForm = $("#weekreport_form").validate({
			errorPlacement: function(error, element) {
						$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				remark : "required",
			},
			messages : {
				remark : "请填写周总结"
			}
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
								<h3>周报提交</h3>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row-fluid ">
							<div class="box well form-inline">
								<table style="width: 100%" class=' form-inline'>
									<tr>
										<td style="width: 100%" align="left">
											<div class="btn-group">
												<button type="button" class="btn btn-default" onclick="$.ims.weekReport.queryLastWeek();">
													<i class="icon-backward"></i>上一周
												</button>
												<button type="button" class="btn btn-default" onclick="$.ims.weekReport.queryCurrentWeek();">本周</button>
												<button type="button" class="btn btn-default" onclick="$.ims.weekReport.queryNextWeek();">
													下一周&nbsp;<i class="icon-forward"></i>
												</button>
											</div>
											<button id="btnCreate" class="btn btn-info right" type="button" onclick="$.ims.weekReport.showSubmitWeekReportModal()">
												<i class="icon-upload icon-white"></i> 提交
											</button>

										</td>
									</tr>
								</table>
							</div>
							<table id='dt_weekReport' class="table-hover responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th width="80px;">日报日期</th>
											<th width="200px;">星期</th>
											<th>类型</th>
											<th>项目</th>
											<th>日报详情</th>
											<th>异常情况/工作难点</th>
											<th>工时(时)</th>
											<th>工作阶段</th>
											<th>创建日期</th>
											<th>创建者</th>
										</tr>
									</thead>
									<tbody></tbody>
									<tfoot>
										<tr class="invoice-cal">
											<td>
												<span>总工时：</span>
												<span>周次：</span>
												<span>日期：</span>
												<span>状态：</span>
											</td>
											<td colspan="8" class="pull-left">
												<span style="text-align: left;" id="foot_total">0</span>
												<span style="text-align: left;" id="weekNum"> 0</span>
												<span style="text-align: left;" id="dateArea"></span>
												<span style="text-align: left;" id="state">0</span>
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
							
						</div>
					</div>
					
					<div class="modal hide fade" id="myWeekReport">
					<div class="modal-header blue">
						周报提交
						<button type="button" class="close" data-dismiss="modal">×</button>
					</div>
					<div class="modal-body" >
						<div class="row-fluid">
							<div class="span12">
								<div class="form-container grid-form form-background left-align form-horizontal">
									<form action="" method="get" id='weekreport_form'>
										<div class="control-group" id='control_difficulty'>
											<label for="difficulty" class="control-label">周总结：</label>
											<div class="controls">
												<textarea class="span10" id="remark" rows="6" name="remark"></textarea>
											</div>
										</div>
										
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer center">
						<a class="btn btn-primary" onclick="$.ims.weekReport.submitWeekReport()">提交</a> <a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
					</div>
				</div>
		
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>