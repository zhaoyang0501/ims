<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head> 
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.meeting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script> 



<script type="text/javascript">
$(document).ready(function(){	
	
	$(".required").change(function(){
		if ($.trim($(this).val()) != "") {
        	$(this).removeClass('valid-error');
        }else{
        	$(this).addClass('valid-error');
        	flag = false;
        }
	});
	
	$(".number").change(function(){
		var r = /^\+?[1-9][0-9]*$/;
		if(r.test($(this).val())){
    		$(this).removeClass('valid-error');
    	}else{
    		$(this).addClass('valid-error');
    		flag = false;
    	}
	});
	
	$("#materiel").change(function(){
		var materiel = $("#materiel").val();
		if (materiel == "" || materiel == null){
        	$("#materiel").next().addClass('valid-error');
        	flag = false;
		}else{
        	$("#materiel").next().removeClass('valid-error');
		}
	});
	
 	$.ims.common.findAllMateriel(function(){
 		$("#materiel").chosen({
 			allow_single_deselect : true,
 			no_results_text : "没有找到.",
 			disable_search_threshold : 5,
 			enable_split_word_search : true,
 			search_contains : true
 		});
 	}, "materiel"); 
 	
	$(".chosen").chosen({
		no_results_text : " ",
		placeholder_text : " ",
		disable_search_threshold : 5,
		allow_single_deselect : true,
		disable_search_threshold : true

	});
	
	$.ims.meeting.initRoomInfoDataTable(0);
	$.ims.meeting.rommStatus("s_status");

	
});
</script>
<style type="text/css">
.chzn-container .chzn-results{
	max-height:150px;
}

.valid-error {
border: 1px solid red !important;
}

.row-fluid [class*="span"]{
min-height: 34px;
}
</style>
</head>
<body>

   <div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />
		<input type="hidden" id="status" />

		<div class="main-wrapper" style = "height:100%">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets " style = "height:100%">
							<div class="widget-head  bondi-blue" >
								<h3>会议室管理</h3>
							</div>
								<div class="tab-pane active" id="bookinfo" style = "height:100%">
									<div class="box well form-inline">	
										<span>会议室：</span>
										<input type="text" maxlength="30" id="s_name" class="span2">
										<span>席位：</span>
										<input type="text" maxlength="3" id="s_seat" class="span1">	
<!-- 										<span>状态：</span> -->
<!-- 										<select id="s_status" style="width: 100px;" data-placeholder="选择状态"> -->
<!-- 										</select>															 -->
										<a  onclick="$.ims.meeting.initRoomInfoDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<a class="btn btn-success" style="float: right;margin:5px;" type="button" onclick="$.ims.meeting.createRoom()"><i class="icon-plus"></i>新增会议室</a>
									<table class="responsive table table-striped table-bordered table-hover table-condensed" id="roominfo_dataTable">
										<thead>
											<tr>
												<th style="width:">名称</th>
												<th style="width: ">席位</th>
												<th style="width: ">设备状况</th>
												<th style="width: ">地点</th>
												<th style="width: ">描述</th>
<!-- 												<th style="width: ">状态</th> -->
<!-- 												<th style="width: ">创建时间</th> -->
												<th style="width:10%">操作</th>
											</tr>
										</thead>
										<tbody></tbody>
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
	<div class="modal hide fade" id="room_edit_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="room_modal_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:300px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal"> 
	 				<form class="form-horizontal" method="post" id ="editroom"> 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 名称：</label> 
							<div class="controls"> 										
							<input type="text" id="roomname"  style="width:220px;"  maxlength="12" name="code" class="span8 required"> 
							</div> 
						</div> 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 席位：</label> 
							<div class="controls"> 
								<input type="text" style="width:220px;"  id="seat" maxlength="30" name="bookname" class="span8 number"> 
							</div> 
						</div>			 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 地点：</label> 
							<div class="controls"> 
							<input type="text" id="address" style="width:220px;" maxlength="50" name="author" class="span8 required"> 
							</div> 
						</div>	 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 设备：</label> 
							<div class="controls"> 
								<select id="materiel" data-placeholder="请选择设备"  multiple tabindex="6" class="chosen multiple">
								</select> 
							</div> 
						</div>
						<div class="control-group"> 
							<label  class="control-label">描述：</label> 
							<div class="controls"> 
								<textarea id="description" maxlength="200" name="description" style="width:210px;height:100px;"></textarea>
							</div>									 
						</div>	 
	 				</form> 
 				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_editroom">
			<a class="btn btn-primary" onclick="$.ims.meeting.saveRoom()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div> 
	
	
</body>
</html>