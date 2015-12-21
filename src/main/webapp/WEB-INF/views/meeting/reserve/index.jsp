<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<head>
<%@ include file="../../common/meta.jsp"%>
  <!-- 日程表控件 http://fullcalendar.io/  -->
  <link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect-collapsible-groups.js"></script>
<link href="${pageContext.request.contextPath}/css/fullcalendar.css" rel= "stylesheet">
<link href="${pageContext.request.contextPath}/css/themes/south-street/jquery.ui.all.css" rel= "stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/fullcalendar.min.js"></script >
<script src="${pageContext.request.contextPath}/js/falgun/jquery-ui-1.10.1.custom.min.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.meeting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/multifile/css/jquery.multifile.min.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/multifile/jquery.multifile.min.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>






<style type="text/css">
 	body{line-height: 18px;} 
 	.chzn-container .chzn-results{ 
 		max-height:120px; 
 	} 
	.valid-error {
	border: 1px solid red !important;
	}
 	.chzn-container-multi .chzn-choices .search-field input{ 
 		height:20px; 
	} 
 	.chosen.multiple{ 
 		min-height: 130px; 
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
  		padding: 1px;  
 	} 
 	.multiselect-container{  
  		width: 220px; 
  	}  
  	.multiselect-search{  
  		width: 180px;  
  	} 
</style>
<script type='text/javascript'>
            $(document).ready(function () {
            	
            	$(".time").datetimepicker({
         			language : 'zh-CN',
         			autoclose : 1,
         			startView : 0,
         			minView : 0,
         			maxView : 0,
         			minuteStep :30,
         			format : "hh:ii",
         		});
            	
        	   

            	
            	$('#startDate').datetimepicker({
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
             		$("#compere").chosen({
             			allow_single_deselect : true,
             			no_results_text : "没有找到.",
             			disable_search_threshold : 5,
             			enable_split_word_search : true,
             			search_contains : true
             		});
             	}, "compere"); 
             	
             	$.ims.common.findAllUser(function(){
             		$("#registrar").chosen({
             			allow_single_deselect : true,
             			no_results_text : "没有找到.",
             			disable_search_threshold : 5,
             			enable_split_word_search : true,
             			search_contains : true
             		});
             	}, "registrar"); 
             	
             	
                $("#multifile").multifile();
				
        		$(".chzn-select").chosen({
        			allow_single_deselect : false,
        			search_contains : false
        		});
        		
        		if("${msg}"=="成功" && "${msg}" != ""){
        			noty({"text":"预约保存成功！","layout":"top","type":"success","timeout":"2000"});
        			$('#calendar').fullCalendar('refetchEvents');
        		}
        		if("${msg}"=="错误" && "${msg}" != ""){
        			noty({"text":"预约开始时间大于结束时间！","layout":"top","type":"error","timeout":"2000"});
        			$('#calendar').fullCalendar('refetchEvents');
        		}
        		if("${msg}"=="失败" && "${msg}" !=""){
        			noty({"text":"预约时间不可选择！","layout":"top","type":"warning","timeout":"2000"});
        			$('#calendar').fullCalendar('refetchEvents');
        		}
        		
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
        				week : "周",
        				day : "日",
        			},
        			titleFormat : {
        				month : "yyyy年 MM月",
        				week: "yyyy年 MM月 d[yyyy]{—[MMMM]d日}", // Sep 7 - 13 2013
        				day: "yyyy年 MM月d日" // Tuesday, Sep 8, 2013
        			},
       			    header:{
       			    	left: 'prev,next today',
       		            center: 'title',
       		            right: 'month,agendaWeek,agendaDay'
       		        },
//        		     columnFormat: {month:'ddd M/d'},
			selectHelper:true,
       		     	defaultView : "agendaWeek",
        			allDaySlot :false,//显示全天。
    				allDayDefault :false, //是否为全天事件。周日中。
        			theme: true,//使用jquery ui。
//         			buttonIcons :true,//不适用jquery ui ico。
        			weekMode : 'fixed',//月周数和高度保持一致。
        			firstHour : 8, //设置默认显示时间8点。
        			minTime : 8, //设置时间轴从8点开始。
        			slotEventOverlap :true, //显示事件不可重叠。
        			editable : {month :false, agenda :true}, //事件是否可编辑，可编辑是指可以移动, 改变大小等。
        			dragOpacity: {  //拖动时候的不透明度。
        				agenda: .5,
        				'':.6
        			},
        			timeFormat :{agenda: 'H:mm{ - H:mm}'},
        			axisFormat :'H:mm', //左侧时间为24H。
        			selectable: {month :false, agenda :true},
    				handleWindowResize : true, //根据浏览器改变大小。
        			height: 800,
        			aspectRatio : 1.8,
        			
       			    events:{
                    	url : "${pageContext.request.contextPath}/meeting/query?rm=${room.id}",
                    },

        			eventDrop: function(event,dayDelta,minuteDelta,allDay,revertDrop) {
        				if (event.color !="#009600"){
        					revertDrop();
        					return false;
        	    		}
        				var d =$.fullCalendar.formatDate(event.start,'yyyy-MM-dd HH:mm');
        			    var now = new Date().getTime();  
       				  	var diffValue = now - Date.parse(d.replace(/-/g,'/').replace(/：/g,":"));  
                   	    if(diffValue > 0){  
                   	    	revertDrop();
                   	    	alert("预约时间不可选择!");
                   	    	return false;
                   	    } 
            	    	var startTime = $.fullCalendar.formatDate(event.start, 'H:mm');
        				var endTime = $.fullCalendar.formatDate(event.end, 'H:mm');
        				var startDate =$.fullCalendar.formatDate(event.start,'yyyy-MM-dd');
        				
        				$.ims.meeting.dragReserve(event.id, startDate, startTime , endTime);
        	    	},
        	    	
        	    	eventResize :function(event,dayDelta,minuteDelta,allDay,revertResize) {
        				if (event.color !="#009600"){
        					$('#calendar').fullCalendar('refetchEvents');
        					return false;
        	    		}
        				var d =$.fullCalendar.formatDate(event.start,'yyyy-MM-dd HH:mm');
        			    var now = new Date().getTime();  
       				  	var diffValue = now - Date.parse(d.replace(/-/g,'/').replace(/：/g,":"));  
                   	    if(diffValue > 0){       
                   	    	alert("预约时间不可选择!");
                   	    	return false;
                   	    } 
	        	    	var startTime = $.fullCalendar.formatDate(event.start, 'H:mm');
	    				var endTime = $.fullCalendar.formatDate(event.end, 'H:mm');
	    				var startDate =$.fullCalendar.formatDate(event.start,'yyyy-MM-dd');
    					$.ims.meeting.dragReserve(event.id, startDate, startTime , endTime);
    	    		},
    	    		
        			select: function( startDate, endDate, allDay, jsEvent, view ){
        				var startTime =$.fullCalendar.formatDate(startDate,'H:mm');
        				var endTime =$.fullCalendar.formatDate(endDate,'H:mm');
        				var startDates =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd');
        				var d =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd HH:mm');
        			    var now = new Date().getTime();  
       				  	var diffValue = now - Date.parse(d.replace(/-/g,'/').replace(/：/g,":"));  
                    	    if(diffValue > 0){       
                    	    	alert("预约时间不可选择!");
                    	    	return false;
                    	    }   
        				$.ims.meeting.createReserve(startDates,startTime, endTime);
					},
        			
                   	eventClick :function(calEvent, jsEvent, view){
                   		if (calEvent.color != "#009600"){
                   			return false;
        	    		}
                   		var now = new Date().getTime();  
        				$.ims.meeting.updateReserve(calEvent.id,calEvent.reserve.room.id);
                   	},
                });
                
                $('#calendar').fullCalendar('refetchEvents');
                
                $("#s_attendee").multiselect({
                    enableFiltering: true,
             		includeSelectAllOption: true,
             		enableClickableOptGroups: true,
//              		enableCollapsibleOptGroups: true,
                    numberDisplayed:1,
                    maxHeight: 200,
                    buttonWidth: '220px'
                });
                
                $(".required").change(function(){
        			if ($.trim($(this).val()) != "") {
        	        	$(this).removeClass('valid-error');
        	        }else{
        	        	$(this).addClass('valid-error');
        	        	flag = false;
        	        }
        		});
                
                
                $(".required2").change(function(){
        			if ($.trim($(this).val()) != "") {
        	        	$(this).next().removeClass('valid-error');
        	        }else{
        	        	$(this).next().addClass('valid-error');
        	        	flag = false;
        	        }
        		});
                
            });
           
            function submit_sure(){
            	var flag = true;
        		if(!$.ims.meeting.validReserve(flag)){
        			return false;		//验证失败
        		}
        	}
            
        </script>

</head>
<body>
<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue" >
								<h3>会议预约</h3>
							</div>
							<div align="center">
								<span class='btn btn-info'  ></span> 非本人
								<span class='btn btn-success ' ></span> 已提交
								<span class='btn  btn-warning ' ></span> 已审核
								<span class='btn btn-danger ' ></span> 已开始
								<span class='btn '  ></span> 已结束
							</div>
							<div class="content-widgets ">
								<div class="widget-container " style="height: 80%">
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
	<div class="modal hide fade" id="reserve_edit_modal" style="min-width:950px; ">
		<div class="modal-header blue">
			<button type="button" class="close" onclick="$('#calendar').fullCalendar('refetchEvents');" data-dismiss="modal" id="closeViewModal">×</button>
			<label id="reserve_modal_header_label"></label>
		</div>
	<form class="form-horizontal" method="post" action="<c:url value='/meeting/reservesave'></c:url>" onsubmit="return submit_sure();" enctype="multipart/form-data">
		<div class="modal-body" style="min-height: 400px;">
			<div class="row-fluid">
					<div class="form-container grid-form form-background left-align form-horizontal">	
						<input type="hidden" id="roomId" name="roomId" value="${rm }"/>
						<input type="hidden" id="hf_id" name = "id" />
						<table>
							<tr>
								<td> 
									<div class="control-group"> 
										<label class="control-label"><span class="text-error">*</span> 会议室：</label> 
										<div class="controls"> 										
											<input type="text" id="roomName" name ="roomName"  value="${room.roomName}" readonly="readonly" style="border: 1px solid #C0BBB4; background: #E0E0E0;"> 
										</div> 
									</div> 
								</td>
								<td>	
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 申请人：</label>
										<div class="controls">
											<input type="text" id="convener" name ="convener"  value="${initiator}" readonly="readonly" style="border: 1px solid #C0BBB4; background: #E0E0E0;"> 
										</div>							
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="control-group"> 
										<label class="control-label"><span class="text-error">*</span> 会议名称：</label> 
										<div class="controls"> 										
										<input type="text" id="meetingName"  name ="meetingName" maxlength="30"  class="required"> 
										</div> 
									</div> 
								</td>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 主持人：</label>
										<div class="controls">
											<select id="compere" name="compere" class="chosen required2" data-placeholder="选择主持人">
											</select>
										</div>
									</div>	
								</td>
							</tr>
							<tr>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 会议日期：</label>
										<div class="controls">
											<div class="input-append date">
												 <input id="startDate" name="startDate" type="text" style="width: 170px;" readonly="readonly">
												 <span class="add-on"><i class="icon-th"></i></span>
											</div>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 纪要人：</label>
										<div class="controls">
											<select id="registrar" name="registrar" class="chosen required2" data-placeholder="选择纪要人">
											</select>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 会议时间：</label>
										<div class="controls">
										 	<div class="input-append ">
												<input id="startTime" name="startTime" class="time" type="text" style="width: 60px;" readonly="readonly">
												 <span class="add-on"><i class="icon-th"></i></span>
											</div>						 
											<div class="input-append">
												<input id="endTime" name="endTime"  class="time" type="text" style="width: 60px;" readonly="readonly">
												 <span class="add-on"><i class="icon-th"></i></span>
											</div>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 参会人员：</label>
										<div class="controls">
											<select id="s_attendee"  name="attendee" class="multiselect required2"  multiple="multiple" style="width: 220px;">
											<c:forEach items="${alldept}" var="dept">
										        <optgroup label="${dept.name}">
	        										<c:forEach items="${alluser}" var="user">
											        	<c:if test="${dept ==user.dept}">
											        		<option value="${user.id}">${user.chinesename}</option>
											        	</c:if>
											        </c:forEach>	
											    </optgroup>
											</c:forEach>    
											</select>	
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="control-group">
										<label class="control-label"><span class="text-error">*</span> 会议类型：</label>
										<div class="controls">
											<select id="meetingType" name="meetingType" class="chzn-select required2" data-placeholder="选择会议类型">
												<c:forEach items="${meeting }" var="type">
													<option value="${type.key }">${type.value }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</td>
								<td rowspan="">
									<div class="control-group ">
										<label class="control-label"> 会议服务：</label>
										<div class="controls">
											<label class="checkbox inline">
									    	<input type="checkbox" id="serve1" value="1" name="serve1" >矿泉水</label> 
											<label class="checkbox inline">
									    	<input type="checkbox" id="serve2" value="1" name="serve2" >水果 </label> 
										</div>
									</div>
								</td>
							</tr>
							<tr>
								
							</tr>
							<tr >	
								<td colspan="2">
									<div class="control-group">
										<label class="control-label"> 会议内容：</label>
										<div class="controls">
											<textarea id="meetingDescription" name="meetingDescription" cols="10" rows="3" style="width: 605px;"></textarea>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="control-group">
										<label class="control-label"> 会议附件：</label>
										<div class="controls" id ="meetingfile">
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
											<font style="color: red;" >(支持多文件上传)</font>
										</div>
									</div>
								</td>
							</tr>
						</table>
 				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_editReserve">
  				<button type="submit" class="btn btn-success">提交</button>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal" onclick="$('#calendar').fullCalendar('refetchEvents');">关闭</a>
		</div>
		</form>
	</div>
</body>
</html>