<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirement.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$(".chzn-select").chosen({
			allow_single_deselect : false,
			search_contains : false
		});

	});
</script>
<style type="text/css">
.chzn-container .chzn-results {
	max-height: 150px;
}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>

		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue">
								<h3>培训需求收集</h3>
							</div>
							<div class="widget-container">
								<a class="btn btn-success" style="float: right; margin-bottom: 5px;" onclick="$.ims.trainingrequirement.showRequireAddModal()"><i class="icon-plus"></i> 培训需求收集</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="requiregather_dataTable">
									<thead>
										<tr>
											<th rowspan="2" style="text-align: center; width: 150px;">岗位/类型</th>
											<th rowspan="2" style="text-align: center; width: 150px;">培训课题</th>
											<th rowspan="2" style="text-align: center; width: 150px;">培训对象</th>
											<th rowspan="2" style="text-align: center; width: 80px;">计划月份</th>
											<th rowspan="2" style="text-align: center; width: 80px;">课时（H）</th>
											<th colspan="2" style="text-align: center; width: 100px;">培训场所</th>
											<th rowspan="2" style="text-align: center; width: 120px;">培训机构/讲师</th>
											<th rowspan="2" style="text-align: center; width: 20%">培训目标</th>
											<th rowspan="2" style="text-align: center; width: 10%""></th>
										</tr>
										<tr>
											<th style="text-align: center; width: 40px;">内训</th>
											<th style="text-align: center; width: 40px;">外训</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">提 交</button>
							<button type="button" class="btn">取 消</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>

</body>
</html>