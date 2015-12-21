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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.overtime.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<head>
<style type="text/css">
	.valid-error{
		  border: 1px solid red !important;
	}
</style>
<script type="text/javascript">
$(function(){
	$.ims.workflowovertime.user = $.ims.workflowovertime.fillDeptUser();
	$.ims.workflowovertime.addNewRow();
	$.ims.common.findAllUser(function(){
		$("#leader").chosen({
			no_results_text :"没有找到这个员工",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"leader");
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_cuid" value="${cuid }" />
	<input type="hidden" name="token" id='token' value="${token}">
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
								<h3>员工加班申请单</h3>
							</div>
							<div class="widget-container">
							<div>
								<table id='dt_overtimerequire' class=" table_bordered_black responsive table table-striped  table-condensed formtable"  style=" min-width: 1024px;">
									<thead>
										<tr>
											<td colspan="9">
												<div class="table_title">
													 <h3>员工加班申请单</h3> 
												</div>
												 <div class="row-fluid ">
									          		<div class="span4"><span class='lable'>部门：</span> <input  class='noborder_input' value="${user.dept.name}" type="text"> </div>
									        	</div>
											</td>
										</tr>
										<tr>
											<th width="30px;"><button id="addRow" onclick="$.ims.workflowovertime.addNewRow();">＋</button></th>
											<th  class='lable'>姓名 <span class="text-error">*</span></th>
											<th  class='lable'>工号 </th>
											<th  class='lable'> <span class="text-error">*</span>加班开始时间</th>
											<th  class='lable'><span class="text-error">*</span>加班结束时间 </th>
											<th  class='lable'><span class="text-error">*</span>计划工时</th>
											<th class='lable' ><span class="text-error">*</span>加班原因</th>
											<th class='lable' >备注</th>
											<th  class='lable' >提示</th>
										</tr>
									</thead>
									<tbody id ="dt_overtimerequire_body">
									<tr id='bottom_tr' >
											<td colspan="9">产品经理: <select id='leader' name="leader'  data-placeholder="选择产品经理">
										  <option></option>
										</select> 
										</td>
									</tr>
									<tr  class='remark'>
											<td colspan="9">备注:
										  	<ol>
										  	<li>项目人员请选择产品经理。</li>
											  <li>此申请表必须于每日17:00交人力资源部备案抽查。</li>
											  <li>加班完成后请及时填写加班报告。</li>
											</ol>
											</td>
									</tr>
									</tbody>
								</table>
								</div>
								<div class="content-widgets ">
									<button type="button"  class="btn btn-primary" onclick="$.ims.workflowovertime.submit(this)">提交</button>
									<a class="btn " type="button" href="${pageContext.request.contextPath}/workflow/overtime">返回</a>
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