<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chosen.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.home.reportot.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#rbDate").datetimepicker({
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
		
		$("#reimburse_Date").datetimepicker({
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
		
		$.ims.common.allDinnerType("dinnerType");
		var date = $("#rbDate").val();
		$.ims.common.findAllOvertime(function(){
			$("#dinnerUsers").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到.",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "dinnerUsers", date)
	});
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>

		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="accordion" id="accordion2">
							<div class="accordion-group">
								<div class="accordion-heading bondi-blue">
									<a href="#collapseOne" data-parent="#accordion2" data-toggle="collapse" class="accordion-toggle">餐费报销</a>
								</div>
								<div class="accordion-body collapse in" id="collapseOne">
									<div class="accordion-inner">
									 	<div>
											<div class="widget-header-block">
												<h4 class="widget-header text-error">报销注意事项(当前餐费报销标准12元/餐)</h4>
											</div>
											<div class="content-box">
												<ul>
													<li>餐费报销人员必须首先填写加班报告，否则无法进行餐费报销。</li>
													<li>如果有外来出差或无需填写加班报告人员，可让报销人员备注描述。</li>
												</ul>
											</div>
										</div>
										<div class="control-group">
											报销日期：<input type="text" id="rbDate" style="width:100px;" value="${date }" />&nbsp;
											餐费类别：
											<select id="dinnerType" data-placeholder=" " style="width:100px;">
												<option></option>
											</select>&nbsp;
										</div>
										<div class="control-group">
											用餐人数：<input type="text" id="dinnerNo"  style="width:100px;" onchange="$.ims.reportot.ondinnerNochange()" />&nbsp;
											报销金额：&nbsp;<input type="text" id="dinnerMoney" readonly="readonly" type="text" class="span1" />&nbsp;
										</div>
										<div class="control-group form-inline ">
											人员明细：
											<select id="dinnerUsers" data-placeholder="选择就餐人员 (可多选)" class="chzn-select span3" multiple onchange="$.ims.reportot.ondinnerUsersChange()" >
												<option></option>
											</select>
										</div>
										<div class="control-group" style="margin-top: 20px;">
											备注 ：
											<textarea id="remark" rows="3" cols="3" class="span5"></textarea>
										</div>
										<div class="form-actions">
											<a class="btn btn-success" onclick="$.ims.reportot.save()">提 交</a>
										</div>
									</div>
								</div>
							</div>
							<div class="accordion-group">
								<div class="accordion-heading">
									<a href="#collapseTwo" data-parent="#accordion2" data-toggle="collapse" class="accordion-toggle">餐费报销记录</a>
								</div>
								
								<div class="accordion-body collapse" id="collapseTwo">
									<div class="box well form-inline">
										<span style="margin-left: 20px;">报销日期：</span>
										<input id="reimburse_Date" type="text" class="span1" style="margin-right:50px;">
										<a onclick="$.ims.reportot.initotDataTable(1,1,1)" class="btn btn-info">查询</a>
									</div>
									<div class="accordion-inner">
										 <table id="otDataTable" class="responsive table table-striped table-bordered" >
											<thead>
												<tr>
													<th style="width: 100px;">报销日期</th>
													<th style="width: 80px;">报销人员</th>
													<th style="width: 80px;">报销金额</th>
													<th style="width: 80px;">报销人数</th>
													<th style="width: 300px;">就餐人员明细</th>
													<th style="width: 300px;">备注</th>
													<th style="width: 50px;">状态</th>
													<th style="width: 80px;">实报金额</th>
													<th style="width: 80px;">付款日期</th>
													<th style="width: 150px;">操作</th>
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
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
</body>
</html>