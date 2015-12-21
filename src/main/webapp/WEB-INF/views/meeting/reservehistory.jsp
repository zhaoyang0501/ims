<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/multifile/css/jquery.multifile.min.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/multifile/jquery.multifile.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect-collapsible-groups.js"></script>



<script type="text/javascript">
$(document).ready(function(){	
	
	$('.date').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		format : 'yyyy-mm-dd',
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
			maxView : 2
	});
	
	
 	$.ims.common.findAllUser(function(){
 		$("#s_compere").chosen({
 			allow_single_deselect : true,
 			no_results_text : "没有找到.",
 			disable_search_threshold : 5,
 			enable_split_word_search : true,
 			search_contains : true
 		});
 	}, "s_compere"); 
 	
	$("#multifile").multifile();
 	
 	if("${msg}"=="成功" && "${msg}" != ""){
		noty({"text":"会议纪要保存成功！","layout":"top","type":"success","timeout":"2000"});
	}
	if("${msg}"=="失败" && "${msg}" !=""){
		noty({"text":"会议纪要保存失败！","layout":"top","type":"warning","timeout":"2000"});
	}
	
	$.ims.meeting.reserveHistoryDataTable();
});

function submit_sure(){
	var flag = true;
	if(!$.ims.meeting.validReserve(flag)){
		return false;		//验证失败
	}
}
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

input[type="file"]{ 
		line-height: 21px !important; 
 		float: left; 
 	} 
#multifile-list span{
	width: 500px;
}
#multifile-list{
	width: 610px;
	padding: 5px; 
}
	
</style>
</head>
<body>

   <div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		

		<div class="main-wrapper" style = "height:100%">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets " style = "height:100%">
							<div class="widget-head  bondi-blue" >
								<h3>已参加会议</h3>
							</div>
								<div class="tab-pane active" id="bookinfo" style = "height:100%">
									<div class="box well form-inline">	
										<span>会议名称：</span>
										<input type="text" maxlength="30" id="s_meetingname" class="span2">
										<span>主持人：</span>
										<select id="s_compere" style="width: 120px;" data-placeholder="选择主持人">
										</select>
										<span>开始日期：</span>
										<div class="input-append date">
											 <input id="s_startDate" style="width:80px;" type="text" value="" readonly="readonly">
											 <span class="add-on"><i class="icon-th"></i></span>
										</div>			
										<a  onclick="$.ims.meeting.reserveHistoryDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<table class="responsive table table-striped table-bordered table-hover table-condensed" id="reserve_dataTable">
										<thead>
											<tr>
												<th style="width: ">会议名称</th>
											    <th style="width: ">申请人</th>
												<th style="width: ">主持人</th>
												<th style="width: ">会议室</th>
												<th style="width: ">开始时间</th>
												<th style="width: ">结束时间</th>
												<th style="width: ">纪要人</th>
<!-- 												<th style="width: 5%">会议状态</th> -->
												<th style="width: 10%">操作</th>
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
<div class="modal hide fade" id="summary_edit_modal" style="min-width:900px; ">
		<div class="modal-header blue">
			<button type="button" class="close"  data-dismiss="modal" id="closeViewModal">×</button>
			<label id="summary_modal_header_label"></label>
		</div>
		<div class="modal-body"  style="min-height:200px;">
			<div class="row-fluid">
	 			<form class="form-horizontal" method="post" action="<c:url value='/meeting/summarysave'></c:url>" onsubmit="return submit_sure();" enctype="multipart/form-data">
					<div class="form-container grid-form form-background left-align form-horizontal">
					<input type="hidden" id="hf_id" name = "id" />
					<table>
						<tr>
							<td> 
								<div class="control-group"> 
									<label class="control-label"> 会议室：</label> 
									<div class="controls"> 										
										<input type="text" id="roomName" name="roomName"  readonly="readonly"> 
									</div> 
								</div> 
							</td>
							<td>	
								<div class="control-group"> 
									<label class="control-label"> 会议名称：</label> 
									<div class="controls"> 										
									<input type="text" id="meetingName" name="meetingName" maxlength="30"   readonly="readonly" class="required"> 
									</div> 
								</div> 
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="control-group">
									<label class="control-label"><span class="text-error">*</span> 会议纪要：</label>
									<div class="controls">
										<textarea id="meetingSummary" style="width: 610px;" name ="meetingSummary"  class="required"  rows="3"></textarea>
									</div>
								</div>
<!-- 								<div class="control-group"> -->
<!-- 									<label class="control-label"><span class="text-error">*</span> 参会人员：</label> -->
<!-- 									<div class="controls"> -->
<!-- 										<select id="attendee"  name="attendee"  data-placeholder="选择参会人" multiple tabindex="10" class="chosen multiple required2"> -->
<!-- 										</select>							 -->
<!-- 									</div> -->
<!-- 								</div> -->

							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="control-group"> 
									<label class="control-label"> 参会人员：</label> 
									<div class="controls"> 										
<!-- 										<input type="text" id="attendeeName" style="width: 610px;height: 100%" name="attendeeName"  readonly="readonly"> -->
										<textarea id="attendeeName" style="width: 610px;" name ="attendeeName"  disabled="disabled"  class="required" rows="3"></textarea>
									</div> 
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">	
								<div class="control-group">
									<label class="control-label"> 纪要附件：</label>
									<div class="controls" id ="summaryfile">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="control-group">
									<label class="control-label"> 附件上传：</label>
									<div class="controls">
										<input type="file" id="multifile" name="multifile[]" method=""  class="multi"/>
									</div>
								</div>
							</td>
						</tr>
					</table>
	 				</div>
	 				<div class="modal-footer center" id="div_editSummar">
			   			<button type="submit" class="btn btn-success">保存</button>
						<a href="#" class="btn" data-dismiss="modal" id="closeViewModal" >关闭</a>
					</div>
 				</form>
			</div>
		</div>
	</div> 
	
</body>
</html>