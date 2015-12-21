<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.reimburse.reimburse.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<head>
<script type="text/javascript">
$.validator.setDefaults({
	showErrors: function(map, list) {
		$("form .formError").remove();
			$.each(list, function(index, error) {
				$("#formerror_warp .formErrorContent").html("");
				$("#formerror_warp .formErrorContent").append( error.message+"<br/>");
				$(error.element).before($("#formerror_warp  .formError").clone()).parent().css("position","relative");
				$("form .formError").css("top",-$("form .formError").height());
			});
}}); 
$(document).ready(function() {
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
	$("#state").chosen({
		allow_single_deselect : true
	});
	<c:if test="${response!=null&&response.code=='1'}">
	noty({"text":"操作成功","layout":"top","type":"success","timeout":"2000"});
	</c:if>
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>餐费报销</h3>
							</div>
							<div class="box well form-inline">
								<span>报销日期：</span>
								<div class="input-append date">
									<input id="begin" type="text" value="" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<span >~</span>
								<div class="input-append date">
									<input id="end" type="text" value="" readonly="readonly" style="width:80px;">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								
								<label  class="control-lableName">流程状态：</label>
								 <select id='state'  style="width:120px;">
								  	<option value="">&nbsp;</option>
									 <option value="1">审批中</option>
									 <option value="4">已结束</option>
								 </select>
								
								<a class="btn btn-info" type="button" onclick="$.ims.reimburse.initReimburseDataTable()"><i class="icon-search"></i>查询</a>
							</div>
							<div class="widget-container">
								<a class="btn btn-success" style="float: right;margin:5px;" type="button" href="reimburse/reimburse/create"><i class="icon-plus"></i>填写报销单</a>
								<table id='reimburseDataTable' class="responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>报销日期</th>
											<th>报销人员</th>
											<th>报销金额</th>
											<th>报销人数</th>
											<th>就餐人员明细</th>
											<th>备注</th>
											<th>实报金额</th>
											<th>付款日期</th>
											<th>创建日期</th>
											<th>状态</th>
											<th>当前步骤</th>
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