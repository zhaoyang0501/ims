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
	
	
	$.ims.meeting.allHistoryDataTable();
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
								<h3>历史会议</h3>
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
	
</body>
</html>