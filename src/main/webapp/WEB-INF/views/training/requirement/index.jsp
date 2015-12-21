<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirement.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript">

	$(document).ready(function(){		
		$.ims.trainingrequirement.initRequirementDataTable();
		$(".date").datetimepicker({
	        format:'yyyy',
			autoclose: 1,
			startView: 4,
			minView: 4
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
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>培训需求收集</h3>
							</div>
							<div class="widget">
								<a class="btn btn-success" style="float: right; margin-bottom:5px;margin-top:20px;"
										onclick="$.ims.trainingrequirement.showRequireAddModal()"><i class="icon-plus"></i> 培训需求收集</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="trainingrequire_dataTable">
									<thead>
										<tr>
											<th width="100px;">收集人</th>
											<th width="100px;">收集日期</th>
											<th width="100px;">年份</th>
											<th width="100px;">流程状态</th>
											<th>备注</th>
											<th width="150px;">查看详情</th>
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
		<%@ include file="../../foot.jsp"%>
	</div>

	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="requirement_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="requirement_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 培训需求年份：</label>
							<div class="controls">
								<div class="input-append date">
									 <input id="year" style="width:80px;" type="text" value="" readonly="readonly">
									 <span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 备注：</label>
							<div class="controls">
								<textarea rows="4" cols="8" id="description"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.trainingrequirement.submit()">发起流程</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
</body>
</html>