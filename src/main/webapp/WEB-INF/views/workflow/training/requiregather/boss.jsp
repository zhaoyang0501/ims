<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirementgather.js"></script>
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
// 	$.ims.trainingrequirementgatherprimary.inintTrainingRequirePrimary("2010");
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red !important;
	}
	input[type="text"]{
		margin-bottom: 0px !important;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_requireId" value="${requireId }" />
	<div class="layout">
		<!-- top -->
		<%@ include file="../../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="widget-head bondi-blue">
							<h3>培训需求收集</h3>
						</div>
						<div style="color: #3399ff;">
							 <h3 style="text-align: center">${year }年培训需求收集汇总</h3> 
							<span style="float:right; margin:-20px 10px 0px 0px;">
								流程编号：${sn }
							</span>
						</div>
						<div class="tab-widget">
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#deitals"><i class="icon-check"></i>培训需求明细</a></li>
							<li class=""><a href="#summary"><i class="icon-edit"></i> 培训需求汇总</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane" id="summary">
								<table id="dt_summary" class="responsive table table-striped table_bordered_black table-condensed" style="width:700px;">
									<thead style="color: #3399ff;">
										<tr>
											<th rowspan="2" width="130px;">提交部门</th>
											<th rowspan="2" width="100px;">培训需求数目</th>
											<th colspan="2" width="100px;">场所(内/外)</th>
											<th rowspan="2" width="100px;">预计总费用</th>
											<th rowspan="2" width="100px;">预计总课时</th>
											<th rowspan="2" width="100px;">提交日期</th>
										</tr>
										<tr>
											<th width="40px;">内</th>
											<th width="40px;">外</th>
										</tr>
									</thead>
									<tbody id="dt_traingrequire_body">
									</tbody>
									<tfoot style="font-weight: bolder; background-color: #CCCCCC;">
										<td>合计：</td>
										<td>0</td>
										<td>0</td>
										<td>0</td>
										<td>0</td>
										<td>0</td>
										<td></td>
									</tfoot>
								</table>
							</div>
							<div class="tab-pane active" id="deitals">
								<table id="dt_details" class="responsive table table-striped table_bordered_black table-condensed" >
									<thead style="color: #3399ff;">
										<tr>
											<th rowspan="2" width="40px;">#</th>
											<th rowspan="2" width="130px;">提交部门</th>
											<th rowspan="2" width="100px;">培训类型</th>
											<th rowspan="2" width="100px;">培训课题</th>
											<th rowspan="2" width="100px;">培训对象</th>
											<th rowspan="2" width="100px;">计划月份</th>
											<th rowspan="2" width="100px;">预计费用</th>
											<th rowspan="2" width="100px;">课时</th>
											<th colspan="2" width="100px;">场所(内/外)</th>
											<th rowspan="2" width="100px;">培训机构/讲师</th>
											<th rowspan="2" width="100px;">培训目标</th>
											<th rowspan="2">备注</th>
										</tr>
										<tr>
											<th width="40px;">内</th>
											<th width="40px;">外</th>
										</tr>
									</thead>
									<tbody id="dt_traingrequire_body">
									</tbody>
									<tfoot style="font-weight: bolder; background-color: #CCCCCC;">
										<td colspan="6" style="text-align: right;">合计：</td>
										<td>￥ 0</td>
										<td>0 Hours</td>
										<td colspan="5"></td>
									</tfoot>
								</table>
							</div>
						</div>
						<div style="margin-top:20px;">
							<c:if test="${acitons.size()!=0}">
								<div class="control-group">
									<div class="controls">
										<c:forEach items="${acitons}" var="item">
											<input name="operate" type="button" onclick="$.ims.trainingrequirementgather.approve(${item.key})" value="${item.value}" class="btn btn-default"></input>
										</c:forEach>
										<button class="btn" onclick="history.go(-1)" class="btn">返回</button>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../../foot.jsp"%>
	</div>
	
</body>
</html>