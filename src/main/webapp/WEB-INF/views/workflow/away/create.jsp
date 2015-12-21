<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.away.js?1=1"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<head>
<script type="text/javascript">
$(function(){
	$.ims.workflowaway.user = $.ims.workflowaway.fillDeptUser();
	$.ims.common.findAllUser(function(){
		$("#manager").chosen({
			no_results_text :"没有找到",
			placeholder_text:" ",
			allow_single_deselect: false
		})
	},"manager");
	$.ims.common.setchosenvalue2("manager","${manager}");
	$.ims.workflowaway.addNewRow();
	$('.date').datetimepicker({
		language : 'zh-CN',
		autoclose : 1,
		startView : 2,
		minView : 0,
		format : "yyyy-mm-dd hh:ii"
	});
	$(".required").change(function(){
    	var v = $(this).val();
    	if ($.trim(v) != "") {
        	$(this).removeClass('valid-error');
        }else{
        	$(this).addClass('valid-error');
        }
    });
	$(".required1").change(function(){
		alert($(this).val());
		if ($.trim($(this).val()) != "0") {
        	$(this).next().removeClass('valid-error');
        }else{
        	$(this).next().addClass('valid-error');
        }
	});
	$(".required2").change(function(){
		if ($.trim($(this).val()) != "") {
        	$(this).parent().removeClass('valid-error');
        }else{
        	$(this).parent().addClass('valid-error');
        }
	});
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red !important;
	}
	input[type="text"]{
		margin-bottom: 0px !important;
	}
	.error_larger{
		font-size: 20px !important;
	}
	.table td{
		vertical-align : middle;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_cuid" value="${cuid }" />
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
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue">
								<h3>员工外出申请单</h3>
							</div>
							<div class="widget-container">
								<table  class=" responsive table table-striped table_bordered_black table-condensed formtable" >
									<tr>
										<td colspan="4">
											<div class="table_title">
												 <h3>员工外出申请单</h3> 
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="4" style="padding: 0;">
											<table id='dt_away' class="responsive table  table-condensed" style="margin-bottom: 0;border-collapse: collapse;" >
												<thead>
													<tr>
														<th  width="30px;"><button id="addRow" onclick="$.ims.workflowaway.addNewRow();">＋</button></th>
														<th class='lable' width="100px;">姓名 <span class="text-error">*</span></th>
														<th class='lable' >工号 </th>
														<th class='lable'>部门 </th>
														<th class='lable'  style="width:60%;">职位 </th>
													</tr>
												</thead>
												<tbody id ="dt_away_body">
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td class='lable'><span class="error_larger text-error">*</span>&nbsp;外出时间：</td>
										<td colspan="3" class="form-inline">
											<div class=" input-append date">
												<input id="awayFrom" style="width: 120px;" type="text" readonly="readonly" class="required2">
												<span class="add-on"><i class="icon-th"></i></span>
											</div>
											至
											<div class=" input-append date">
												<input id="awayTo" style="width: 120px;" type="text" readonly="readonly" class="required2">
												<span class="add-on"><i class="icon-th"></i></span>
											</div>
										</td>
									</tr>
									<tr><td class='lable'><span class="error_larger text-error">*</span>&nbsp;外出地点：</td><td colspan="3"><input style="width: 95%;" type="text" id="awayAddress" type="text" class="required "/></td></tr>
									<tr><td class='lable'><span class="error_larger text-error">*</span>&nbsp;外出原因：</td><td colspan="3"><input style="width: 95%;"  type="text" id="awayReason" type="text" class="required "/></td></tr>
									<tr>
										<td class='lable'>外(派)出车辆：</td><td><input style="width: 138px;" id="awayCar" type="text" /></td>
										<td class='lable'>司  机：</td><td>     <input style="width: 138px;" id="awayDriver" type="text"/></td>
									</tr>
									<tr>
										<td  class='lable'><span class="error_larger text-error">*</span>&nbsp;外出审核：</td>
										<td colspan="3">
											<select id="manager" style="width:150px;" class="chosen" data-placeholder="部门主管"></select>
											<span class="h5 text-error">(注：默认提交部门主管审核，如主管不在请选择管晓琴审批。)</span>
										</td>
									</tr>
								</table>
								<div class="content-widgets ">
											<button type="button" class="btn btn-primary" id="btn-submit" onclick="$.ims.workflowaway.submit(this)" data-loading-text="Loading...">提 交</button>
										    <a class="btn" href='<c:url value="/workflow/away" />'>返 回</a>
								</div>
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