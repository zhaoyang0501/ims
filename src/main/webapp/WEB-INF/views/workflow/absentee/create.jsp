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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.absentee.js"></script>
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
	$.ims.workflowabsentee.user = $.ims.workflowabsentee.fillUser();
	$.ims.workflowabsentee.addNewRow();
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red !important;
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
								<h3>员工考勤补卡记录申请单</h3>
							</div>
							<div class="widget-container">
								<table id='dt_absenteerequire' class=" table_bordered_black responsive table table-striped  table-condensed formtable .form-horizontal"  style=" min-width: 1024px;">
									<thead>
										<tr>
											<td colspan="8">
												<div class="table_title">
													 <h3>员工考勤补卡记录申请单</h3> 
												</div>
												<div class="row-fluid ">
									          		<div class="span4"><span class='lable'>部门：</span> <input  class='noborder_input' value="${user.dept.name}" type="text"> </div>
									        	</div>
											</td>
										</tr>
										<tr>
											<th  class='lable'><button id="addRow" onclick="$.ims.workflowabsentee.addNewRow();">＋</button></th>
											<th  class='lable'>姓名 <span class="text-error">*</span></th>
											<th  class='lable'>工号 </th>
											<th  class='lable'>漏打卡时间 <span class="text-error">*</span></th>
											<th  class='lable'>考勤记录 </th>
											<th  class='lable'>漏打卡原因<span class="text-error">*</span></th>
											<th  class='lable'>本月漏打卡次数</th>
											<th  class='lable'>备注</th>
										</tr>
									</thead>
									<tbody id ="dt_absenteerequire_body">
									</tbody>
								</table>
								<div class="content-widgets ">
									<button type="button" class="btn btn-primary" id="btn-submit" onclick="$.ims.workflowabsentee.submit(this)">提 交</button>
								    <button type="button" class="btn" onclick="history.go(-1)">返 回</button>
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