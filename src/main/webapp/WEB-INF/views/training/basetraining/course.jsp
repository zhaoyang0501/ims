<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.basetraining.course.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/multifile/css/jquery.multifile.min.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/multifile/jquery.multifile.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$("#multifile").multifile({
			list: '#file-Log'
		});
		
		$.ims.basetrainingcourse.inintCourseDataTable();
		$.ims.common.trainingTypeChosen("courseType");
		$.ims.common.trainingTypeChosen("s_coursetype");
		
		$(".chosen").chosen({
			placeholder_text:" ",
	        allow_single_deselect : true,
	        no_results_text : "没有找到.",
	        disable_search_threshold : 5,
	        enable_split_word_search : true,
	        search_contains : true

		});
		$(".required").change(function(){
	    	if ($.trim($(this).val()) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        }
	    });
		$("#courseType").change(function(){
			if ($.trim($(this).val()) != "0") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		if("${msg}" != ""){
			noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
		}
	});
	
	//上传验证1：上传文件不能为空 2：当前只支持pdf文件
	function submit_sure(){
		var flag = false;
		var suffixflag = true;
		$("#multifile-list li").each(function(i){
			var v = $(this).text();
			//验证是否选择了文件,当前只支持上传pdf文件
			if($.trim(v) != ""){
				var suffix = v.substring(v.lastIndexOf(".")+1);
				if(suffix != "pdf"){
					suffixflag = false;
				}
				flag = true;
			}
		});
		if(!suffixflag){
			noty({"text":"请上传PDF文件！","layout":"top","type":"error","timeout":"2000"});
			return false;
		}
		if (!flag) {
			noty({"text":"请选择要上传的文件！","layout":"top","type":"error","timeout":"2000"});
			return false;
		}
		var tag=confirm("确定上传?");
		if (tag == true){
			return true;
		}else{
			return false;
		}
	}
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
								<h3>在线学习课程管理</h3>
							</div>
							<div class="widget" style="margin-top:5px;">
								<div class="box well form-inline">
									<span>课程名称：</span>
									<input id="s_coursename" type="text"></input>
									<span>课程类型：</span>
									<select id="s_coursetype" data-placeholder="选择课程类型"></select>
									<a onclick="$.ims.basetrainingcourse.inintCourseDataTable()" class="btn btn-info"><i class="icon-zoom-in"></i>查询</a>
								</div>
								<a class="btn btn-success" style="float: right; margin-bottom:5px;"
										onclick="$.ims.basetrainingcourse.showAddModal()"><i class="icon-plus"></i> 添加学习课程</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_course">
									<thead>
										<tr>
											<th width="150px;">课程名称</th>
											<th width="150px;">课程类型</th>
											<th width="80px;">培训课时</th>
											<th>培训教程</th>
											<th>培训目标</th>
											<th width="60px;">是否考试</th>
											<th>备注</th>
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
	<div class="modal hide fade" id="course_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="course_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form id="courseform" class="form-horizontal" method="post" action="">
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;课程名称：</label>
							<div class="controls">
								<input id="courseName" name="courseName" type="text" class="required"></input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;课程类型：</label>
							<div class="controls">
								<select id="courseType" name="courseType" data-placeholder="选择课程类型"  class="required2">
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;培训课时：</label>
							<div class="controls">
								<input id="hours" name="hours" type="text" style="width:100px;" class="required"></input>
								<span>Hours</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font>*</font>&nbsp;考试安排：</label>
							<div class="controls">
								<select id="examination" name="examination" class="chosen" data-placeholder="选择考试安排">
									<option value="0"></option>
									<c:forEach items="${examList }" var="item">
										<option value="${item.id }">${item.subject }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font class="text-error">*</font>&nbsp;培训目标：</label>
							<div class="controls">
								<textarea id="targets" name="tragets" maxlength="300" rows="3" class="required"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 备注：</label>
							<div class="controls">
								<textarea id="remarks" name="remarks" maxlength="300" rows="3"></textarea>
							</div>
						</div>	
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.basetrainingcourse.save()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<!-- 上传培训教程弹出框 -->
	<div class="modal hide fade" id="course_material_modal" style="min-width: 760px;">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="course_material_modal_header_label"></label>
		</div>
		<form id="coursematerialform" class="form-horizontal" method="post" action="<c:url value='/basetraining/course/material/upload'></c:url>" onsubmit="return submit_sure();" enctype="multipart/form-data">
			<div class="modal-body">
				<div class="row-fluid">
					<div class="form-container grid-form form-background left-align form-horizontal">
						<input type="file" id="multifile" name="multifile[]" method="" style="height:25px;line-height: 23px !important;"/>
						<font style="color: red;">(支持多文件上传)</font>
						<input type="hidden" id="hf_courseId" name="courseId"></input>
					</div>
				</div>
			</div>
			<div class="modal-footer center" id="div_footer">
				<button type="submit" class="btn btn-success">保 存</button>
				<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
			</div>
		</form>
	</div>
</body>
</html>