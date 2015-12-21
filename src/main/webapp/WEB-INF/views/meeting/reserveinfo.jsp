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
	
	$.ims.meeting.initRoomInfoDataTable(1);
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

		<div class="main-wrapper" style = "height:100%">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets " style = "height:100%">
							<div class="widget-head  bondi-blue" >
								<h3>会议室预约</h3>
							</div>
								<div class="tab-pane active" id="bookinfo" style = "height:100%">
									<div class="box well form-inline">	
										<span>会议室：</span>
										<input type="text" maxlength="30" id="s_name" class="span2">
										<span>席位：</span>
										<input type="text" maxlength="3" id="s_seat" class="span1">	
<!-- 										<span>会议室状态：</span> -->
<!-- 										<select id="s_status" style="width: 100px;" data-placeholder="选择状态"> -->
<!-- 										</select>	 -->
										<a  onclick="$.ims.meeting.initRoomInfoDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
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
	
	
</body>
</html>