<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.news.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.cleditor.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/multifile/css/jquery.multifile.min.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/multifile/jquery.multifile.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		var id = $("#hf_id").val();
		$.ajax({
    		type : "get",
    		url : $.ims.getContextPath() + "/sysconfig/news/query/"+id,
    		dataType : "json",
    		success : function(json) {
    			$("#title").val(json.title);
    			$("#date").val(json.date);
    			var o = $("#contents").cleditor()[0];
    			$("#contents").val(json.contents);
    			o.updateFrame();
    			$.ims.common.setchosenvalue("type",json.type);
    			$("#top").prop('checked', json.top == 1 ? true : false);
    		}
    	});
		
		//富文本编辑
        $('#contents').cleditor();
        //多文件上传
        $("#multifile").multifile();
		
		$(".chzn-select").chosen({
			allow_single_deselect : false,
			search_contains : false
		});
		
		$(".date").datetimepicker({
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
		
		if("${msg}" != ""){
			noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
		}
	});
	
	function submit_sure(){
		var tag=confirm("确定保存?");
		if (tag == true){
			var title = $("#title").val();
			var contents = $("#contents").val();
			if ($.trim(title) == "" || $.trim(contents) == "") {
				noty({"text":"请填写完整公告信息！","layout":"top","type":"error","timeout":"2000"});
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:150px;
	}
	.cleditorMain{
		width:800px !important;
	}
	input[type="file"]{
		line-height: 21px !important;
		float: left;
	}
	
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" value="${id }" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="content-widgets">
						<div class="widget-head  bondi-blue" >
							<h3>通知公告</h3>
						</div>
						<div class="tab-widget">
							<div class="widget-container">
								<form class="form-horizontal" method="post" action="<c:url value='/sysconfig/news/update'></c:url>" onsubmit="return submit_sure();" enctype="multipart/form-data">
									<input id="id" name="id" value="${id }" type="hidden"></input>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 新闻标题：</label>
										<div class="controls">
											<input type="text" id="title" name="title" class="span4" maxlength="30">(最多输入30个字)
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 发布时间：</label>
										<div class="controls">
											<div class="input-append date">
												 <input id="date" name="date" type="text" readonly="readonly">
												 <span class="add-on"><i class="icon-th"></i></span>
											</div>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 新闻类型：</label>
										<div class="controls">
											<select id="type" name="typeCode" class="chzn-select">
												<c:forEach items="${types }" var="type">
													<option value="${type.key }">${type.value }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="control-group form-inline">
										<label class="control-label"> 置顶：</label>
										<div class="controls">
											<label class="checkbox">
									    	<input type="checkbox" id="top" name="topValue">
									   		 <span style="color: blue;">使新闻置顶，显示为重要 </span></label>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 附件上传：</label>
										<div class="controls">
											<input type="file" id="multifile" name="multifile[]" method="" />
											<font style="color: red;">(支持多文件上传)</font>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 公告详情：</label>
										<div class="controls">
											<textarea id="contents" name="contents" cols="10" rows="5"></textarea>
										</div>
									</div>
									<div class="form-actions">
								    	<button type="submit" class="btn btn-success">保 存</button>
								    	<a class="btn" href="<c:url value='/sysconfig/news'></c:url>">取 消</a>
								    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="project_Member_edit">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="project_Member_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:250px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 角色：</label>
							<div class="controls">
								<select id="role" class="chzn-select">
									<c:forEach items="${roles }" var="role">
										<option value="${role.key }">${role.value }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 成员名称：</label>
							<div class="controls">
								<select id="users" data-placeholder="成员名称" multiple></select>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary" onclick="$.ims.project.saveMember()">保存</a>
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>
	</div>
	</div>
</body>
</html>