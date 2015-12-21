<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.basetraining.plan.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect-collapsible-groups.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$.ims.basetrainingplan.initBaseTraningDatatable();
		
		$.ims.basetrainingplan.findUser("employee");
		$('#employee').multiselect({
			includeSelectAllOption: true,
            enableFiltering: true,
            numberDisplayed:1,
            maxHeight: 250,
            buttonText : function(options){
				if (options.length == 0) {
				    return '请选择培训对象 ';
				}
				else if (options.length > 0) {
				    return options.length + ' 名人员被选中';
				}
            },
		});
		
		$(".chosen").chosen({
			disable_search_threshold : 5,
			allow_single_deselect : false,
			disable_search_threshold : false

		});
		$('.date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			format : 'yyyy-mm-dd',
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
		$.ims.common.findAllUser(function(){
	 		$("#s_employee").chosen({
	 			allow_single_deselect : true,
	 			no_results_text : "没有找到.",
	 			disable_search_threshold : 5,
	 			enable_split_word_search : true,
	 			search_contains : true
	 		});
	 	}, "s_employee");
		
		$(".required").change(function(){
	    	if ($.trim($(this).val()) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        }
	    });
		$(".required1").change(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		$(".required2").change(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).parent().removeClass('valid-error');
	        }else{
	        	$(this).parent().addClass('valid-error');
	        	flag = false;
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
	.multiselect-search{
		width: 200px;
	}
	.multiselect-container{
		width: 280px;
	}
	.multiselect-container {
		height: 220px;
	}
	th{
		min-width:100px;
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
						
						<div class="content-widgets">
							<div class="widget-head bondi-blue" >
								<h3>基础培训计划</h3>
							</div>
							<div class="widget" style="margin-top:5px;">
								<div class="box well form-inline">
									<span>培训计划：</span>
									<input id="s_plan" type="text"></input>
									<a onclick="$.ims.basetrainingplan.initBaseTraningDatatable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<a class="btn btn-success" style="float: right; margin-bottom:5px;"
										onclick="$.ims.basetrainingplan.showAddModel()"><i class="icon-plus"></i> 添加培训计划</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_basetraining">
									<thead>
										<tr>
											<th style="width: 10%;">培训计划</th>
											<th style="width: 12%;">培训计划描述</th>
											<th style="width: 10%;">培训课程</th>
											<th style="width: 25%;">参训人员</th>
											<th style="width: 80px;">开始日期</th>
											<th style="width: 80px;">结束日期</th>
											<th style="width: 10px;">培训目标</th>
											<th style="width: 80px;">培训状态</th>
											<th width="150px;">操作</th>
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
	<div class="modal hide fade" id="basetraining_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="basetraining_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post" action="/basetraining/save">
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;培训计划：</label>
							<div class="controls">
								<input id="title" type="text" class="required"></input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;培训计划描述：</label>
							<div class="controls">
								<textarea id="description" maxlength="300" rows="2" class="span10 required"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;参训员工：</label>
							<div class="controls">
								<select id="employee" multiple="multiple" data-placeholder="选择参训员工" class="required1">
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;基础学习课程：</label>
							<div class="controls">
								<select id="course" class="chosen multiple required1" multiple data-placeholder="选择学习课程">
									<c:forEach items="${list }" var="item">
										<option value="${item.id }">${item.courseName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;开始日期：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="start" type="text" readonly="readonly" class="required2">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;结束日期：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="end" type="text" readonly="readonly" class="required2">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 备注：</label>
							<div class="controls">
								<textarea id="remarks" maxlength="300" rows="2" class="span10"></textarea>
							</div>
						</div>	
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.basetrainingplan.save()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>