<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.contract.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">

	$(document).ready(function () { 
		$.ims.usercontract.initContractDataTable();
		$.ims.common.findAllUser(function(){
			$("#s_user").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "s_user");
		
		$.ims.common.findAllUser(function(){
			$("#employee").chosen({
				allow_single_deselect : true,
				no_results_text : "没有找到",
				disable_search_threshold : 5,
				enable_split_word_search : true,
				search_contains : true
			});
		}, "employee");
		
		$('.date').datetimepicker({
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
		$(".chosen").chosen({
			allow_single_deselect : false,
			search_contains : false
		});
	});
	
</script>
<style type="text/css">
	.modal{
		width:650px;
	}
	a{
		cursor:pointer;
	}
</style>
</head>
<body >
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
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>合同管理</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">
									<span>员工：</span>
									<select id="s_user" style="width:120px;" data-placeholder="选择员工"></select>
									<span>合同生效日期：</span>
									<input type="text" id="s_fromDate_s" class="date" style="width:80px;" readonly="readonly">~
									<input type="text" id="s_fromDate_e" class="date" style="width:80px;" readonly="readonly">
									<span>合同到期日期：</span>
									<input type="text" id="s_endDate_s" class="date" style="width:80px;" readonly="readonly">~
									<input type="text" id="s_endDate_e" class="date" style="width:80px;" readonly="readonly">
									<a onclick="$.ims.usercontract.initContractDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<a class="btn btn-success" style="float: right; margin: 5px;" onclick="$.ims.usercontract.showContractAddModal()"><i class="icon-plus"></i> 新增合同</a>
								<table  class="table table-striped table-bordered table-condensed table-hover" id="dt_contract">
									<thead>
									    <tr>
									    	<th style="width:100px;">姓名</th>
									    	<th style="width:100px;">部门</th>
									    	<th style="width:200px;">合同编号</th>
									    	<th style="width:100px;">合同类型</th>
									    	<th style="width:100px;">合同签订日期</th>
									    	<th style="width:100px;">合同生效日期</th>
									    	<th style="width:100px;">合同到期日期</th>
									    	<th>操作</th>
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
		<%@ include file="../foot.jsp"%>
	</div>

	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="contract_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="contract_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<input type="hidden" id="hf_resume_id" value="${resume.id }" />	<!-- 档案ID -->
						<table class="responsive table table-bordered table-condensed">
							<tr>
								<th>&nbsp;员工：</td>
								<td colspan="3">
									<select id="employee" data-placeholder="选择员工" style="width:164px;" ></select>
								</td>
							</tr>
							<tr>
								<th width="25%">&nbsp;合同编号：</td>
								<td width="30%"><input id="contractNo" type="text" style="width:150px;" />
								<th width="20%">&nbsp;合同类型：</th>
								<td width="25%">
									<select id="contractType" data-placeholder="合同类型" class="chosen" style="width:160px;">
										<option value=""></option>
										<c:forEach items="${contractType}" var="bean">
											<option value="${bean.key}">${bean.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>&nbsp;合同签订日期：</th>
								<td>
									<div class=" input-append date">
									<input id="signDate" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<th>&nbsp;合同生效日期：</th>
								<td>
									<div class=" input-append date">
									<input id="fromDate" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<th>&nbsp;合同终止日期：</th>
								<td colspan="3">
									<div class=" input-append date">
									<input id="endDate" style="width: 80px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<th>&nbsp;是否含试用期：</th>
								<td colspan="3">
									<label class="radio inline">
									<input id="isProbation_1" name="isProbation" type="radio" value="1">
									是 </label>
									<label class="radio inline">
									<input id="isProbation_0" name="isProbation" type="radio" value="0" checked="checked">
									否 </label>
								</td>
							</tr>
							<tr>
								<th>&nbsp;合同是否已解除：</th>
								<td colspan="3">
									<label class="radio inline">
									<input id="isRelieve_1" name="isRelieve" type="radio" value="1">
									是 </label>
									<label class="radio inline">
									<input id="isRelieve_0" name="isRelieve" type="radio" value="0" checked="checked">
									否 </label>
								</td>
							</tr>
							<tr>
								<th>&nbsp;合同是否续签：</th>
								<td colspan="3">
									<label class="radio inline">
									<input id="isRenewal_1" name="isRenewal" type="radio" value="1">
									是 </label>
									<label class="radio inline">
									<input id="isRenewal_0" name="isRenewal" type="radio" value="0" checked="checked">
									否 </label>
								</td>
							</tr>
							<tr>
								<th>&nbsp;备注：</th>
								<td colspan="3"><textarea id="remarks" rows="" cols="" style="width:90%;"></textarea></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.usercontract.saveContract()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>	
</body>
</html>
