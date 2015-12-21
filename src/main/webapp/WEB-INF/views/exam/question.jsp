<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.exam.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.cleditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){	
		$.ims.exam.initExamQuestionDataTable("${id}");//id为paperid
		
		$('.contents').cleditor();
		
		$(".chosen").chosen({
			disable_search_threshold : 5,
			allow_single_deselect : false,
			disable_search_threshold : false
		});
		
		$("#type").change(function(){
			var type = $("#type").val();
			if(type == "1"){
				$("#div_radio").show();
				$("#div_check").hide();
				$("#div_tfng").hide();
				$("#div_essay").hide();
			}
			if(type == "2"){
				$("#div_radio").hide();
				$("#div_check").show();
				$("#div_tfng").hide();
				$("#div_essay").hide();
			}
			if(type == "3"){
				$("#div_radio").hide();
				$("#div_check").hide();
				$("#div_tfng").show();
				$("#div_essay").hide();
			}
			if(type == "4"){
				$("#div_radio").hide();
				$("#div_check").hide();
				$("#div_tfng").hide();
				$("#div_essay").show();
			}
		});
	});
</script>
<style type="text/css">
	.modal{
		width:800px;
	}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		<!-- 试卷ID -->
		<input type="hidden" id="hf_paperid" value="${id }" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						
						<div class="content-widgets">
							<div class="widget-head bondi-blue" >
								<h3>试题管理</h3>
							</div>
							<div class="widget" style="margin-top:10px;">
								<a type="button" class="btn btn-info" onclick="$.ims.exam.viewexam()"><i class="icon-search"></i> 预览</a>
								<a class="btn btn-success" onclick="$.ims.exam.showExamQuestionAddModal()"><i class="icon-plus"></i> 添加试题</a>
								<a class="btn" href="<c:url value='/exam'></c:url>"><i class="icon-reply"></i> 返回</a>
								<table class="responsive table table-striped table-bordered table-condensed" id="dt_examQuestion">
									<thead>
										<tr>
											<th width="80px;">试题类型</th>
											<th width="80px;">试题难度</th>
											<th width="20%">试题题目</th>
											<th width="60px;">选项个数</th>
											<th width="20%">选项内容</th>
											<th width="">试题答案</th>
											<th width="">试题解析</th>
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
		<%@ include file="../foot.jsp"%>
	</div>
	
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="exam_paper_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="exam_paper_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post" action="/training/exam/question/save">
						<div class="control-group">
							<label class="control-label"> 试题类型：</label>
							<div class="controls">
								<select id="type" class="chosen">
									<option value="1" selected="selected">单选题</option>
									<option value="2">多选题</option>
									<option value="3">判断题</option>
									<option value="4">主观题</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 试题难度：</label>
							<div class="controls">
								<select id="difficulty" class="chosen">
									<option value="1" selected="selected">低难度</option>
									<option value="2">中难度</option>
									<option value="3">高难度</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 试题题目：</label>
							<div class="controls">
								<textarea rows="2" id="topic" class="span10"></textarea>
							</div>
						</div>
						<div id="div_radio">
							<div class="control-group">
								<label class="control-label"> 选项个数：</label>
								<div class="controls">
									<select id="radio_number" class="chosen">
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4" selected="selected">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"> 试题答案：</label>
								<div class="controls">
									<label class="radio inline">
									<input type="radio" value="A" name="radio_answeroption"/>
									A </label>
									<label class="radio inline">
									<input type="radio" value="B" name="radio_answeroption"/>
									B </label>
									<label class="radio inline">
									<input type="radio" value="C" name="radio_answeroption"/>
									C </label>
									<label class="radio inline">
									<input type="radio" value="D" name="radio_answeroption"/>
									D </label>
									<label class="radio inline">
									<input type="radio" value="E" name="radio_answeroption"/>
									E </label>
									<label class="radio inline">
									<input type="radio" value="F" name="radio_answeroption"/>
									F </label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"> 选项内容：</label>
								<div class="controls">
									<textarea rows="1" id="radio_options" class="span12 contents"></textarea>
								</div>
							</div>
						</div>
						<div id="div_check" style="display: none;">
							<div class="control-group">
								<label class="control-label"> 选项个数：</label>
								<div class="controls">
									<select id="check_number" class="chosen">
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4" selected="selected">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"> 试题答案：</label>
								<div class="controls" id="div_check">
									<label class="checkbox inline">
									<input type="checkBox" value="A" name="check_answeroption"/>
									A </label>
									<label class="checkbox inline">
									<input type="checkBox" value="B" name="check_answeroption"/>
									B </label>
									<label class="checkbox inline">
									<input type="checkBox" value="C" name="check_answeroption"/>
									C </label>
									<label class="checkbox inline">
									<input type="checkBox" value="D" name="check_answeroption"/>
									D </label>
									<label class="checkbox inline">
									<input type="checkBox" value="E" name="check_answeroption"/>
									E </label>
									<label class="checkbox inline">
									<input type="checkBox" value="F" name="check_answeroption"/>
									F </label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"> 选项内容：</label>
								<div class="controls">
									<textarea rows="2" id="check_options" class="span10 contents"></textarea>
								</div>
							</div>
						</div>
						<div id="div_tfng" style="display: none;">
							<div class="control-group">
								<label class="control-label"> 试题答案：</label>
								<div class="controls">
									<label class="radio inline">
									<input type="radio" value="A" name="tfngoption"/>
									正确 </label>
									<label class="radio inline">
									<input type="radio" value="B" name="tfngoption"/>
									错误 </label>
								</div>
							</div>
						</div>
						<div id="div_essay" style="display: none;">
							<div class="control-group">
								<label class="control-label"> 试题答案：</label>
								<div class="controls">
									<textarea rows="2" id="essay_answers" class="span12"></textarea>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 试题解析：</label>
							<div class="controls">
								<textarea rows="2" id="answers_analyze" class="span10"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.exam.saveExamQuestion()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>