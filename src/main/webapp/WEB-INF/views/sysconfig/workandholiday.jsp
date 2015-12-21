<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<head>
<%@ include file="../common/meta.jsp"%>
  <!-- 日程表控件 http://fullcalendar.io/  -->
<link href="${pageContext.request.contextPath}/css/fullcalendar.css" rel= "stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/fullcalendar.min.js"></script >
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.sysconfig.workandholiday.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type='text/javascript'>
    $(document).ready(function () {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        $('#calendar').fullCalendar({
            dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
			dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
			buttonText : {
				today : "今天",
				month : "月",
				week : "星期",
				day : "天"
			},
			titleFormat : {
				month : "yyyy MMMM",
			},
			header : {
				left : 'prevYear,prev,next,nextYear today',
				center : 'title',
				right : ""
			},
			handleWindowResize : true,
			height: 300,
			aspectRatio : 1.8,
            events:"${pageContext.request.contextPath}/sysconfig/workandholiday/calendar",
            dayClick : function(date, allDay, jsEvent, view) {
				var selDate = $.fullCalendar.formatDate(date, 'yyyy-MM-dd');
				$("#setting_date").val(selDate);
				// 设置时间
				$.ims.workandholiday.queryTimetable(selDate);
			},
			eventClick : function(event, jsEvent, view) {
				var selDate = $.fullCalendar.formatDate(event.start, 'yyyy-MM-dd');
				$("#setting_date").val(selDate);
				// 设置时间
				$.ims.workandholiday.queryTimetable(selDate);
			}
        });
        
        $('.date').datetimepicker({
        	language:  'zh-CN',
    	    weekStart: 1,
    	    todayBtn:  1,
    	    format:'hh:ii',
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 1,
    		minuteStep:30,
    		minView: 0,
    		forceParse: 0
		});
        
        $(".chosen").chosen({
			no_results_text : " ",
			placeholder_text : " ",
			disable_search_threshold : 5,
			allow_single_deselect : true
		});
        
        $("#type").change(function(){
        	var type = $(this).val();
        	if(type == 0){
        		$("#foreworktime").val("");
        		$("#foreresttime").val("");
        		$("#afterworktime").val("");
        		$("#afterresttime").val("");
        		$("#div_forework").hide();
        		$("#div_forerest").hide();
        		$("#div_afterwork").hide();
        		$("#div_afterrest").hide();
        	}else{
        		$("#div_forework").show();
        		$("#div_forerest").show();
        		$("#div_afterwork").show();
        		$("#div_afterrest").show();
        	}
        });
    });
</script>

</head>
<body>
<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>节假日设置</h3>
							</div>
							<div class="content-widgets gray">
								<div class="widget-container" style="width:50%">
									<div id='calendar'>
									</div> 
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="holiday_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label>节假日设置</label>
		</div>
		<div class="modal-body" style="min-height:150px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<div class="control-group">
							<label class="control-label"> 日期：</label>
							<div class="controls">
								<input id="setting_date" style="width: 205px;" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span> 类型：</label>
							<div class="controls">
								<select id="type" class="chosen">
									<option value="0">节假日</option>
									<option value="1" selected="selected">上班</option>
								</select>
							</div>
						</div>
						<div id="div_forework" class="control-group">
							<label class="control-label"> 上午上班时间：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="foreworktime" style="width: 170px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div id="div_forerest" class="control-group">
							<label class="control-label"> 上午下班时间：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="foreresttime" style="width: 170px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div id="div_afterwork" class="control-group">
							<label class="control-label"> 下午上班时间：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="afterworktime" style="width: 170px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div id="div_afterrest" class="control-group">
							<label class="control-label"> 下午下班时间：</label>
							<div class="controls">
								<div class=" input-append date">
									<input id="afterresttime" style="width: 170px;" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"> 备注：</label>
							<div class="controls">
								<input id="remark" type="text">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.workandholiday.save()">保存</a>
<!-- 			<a class="btn btn-danger" onclick="$.ims.workandholiday.cancle()">取消</a> -->
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	
	<%@ include file="../foot.jsp"%>
</body>
</html>