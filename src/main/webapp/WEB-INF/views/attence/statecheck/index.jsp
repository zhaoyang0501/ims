<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head> 
<script src="${pageContext.request.contextPath}/js/falgun/date.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.attence.statecheck.js?t=23045"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript">

	/**扩展date**/
	Date.prototype.format = function(fmt) { //author: meizz   
		var o = {
			"M+" : this.getMonth() + 1, //月份   
			"d+" : this.getDate(), //日   
			"h+" : this.getHours(), //小时   
			"m+" : this.getMinutes(), //分   
			"s+" : this.getSeconds(), //秒   
			"q+" : Math.floor((this.getMonth() + 3) / 3), //季度   
			"S" : this.getMilliseconds()
		//毫秒   
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
	
	$(function() {
		$.ims.common.findAllUser(function(){
			$("#user").chosen({
				no_results_text :"没有找到这个员工",
				placeholder_text:" ",
				allow_single_deselect: true
			})
		},"user");
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
		$(".form_datetime").datetimepicker({
			language : 'zh-CN',
			format : 'yyyy-mm-dd hh:ii',
			autoclose : 1,
			todayHighlight : 1,
			minuteStep:30,
			forceParse : 0
		});
		
		$(".chosen").chosen({
			no_results_text : " ",
			placeholder_text : " ",
			disable_search_threshold : 5
		});
		/***漏打卡表单验证*/
		$.ims.attenceStateCheck.overtimeForm = $("#overtime_form").validate({
			errorPlacement: function(error, element) {
				$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				absentee_absenteeTime : "required",
				absenteeType : "required",
			
			},
			messages : {
				absentee_absenteeTime : "请填写漏打卡时间",
				absenteeType : "请选择漏打卡类型",
				
			}
		});
		/***请假表单验证*/
		$.ims.attenceStateCheck.dayoffform = $("#dayoff_form").validate({
			errorPlacement: function(error, element) {
				$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				dayoff_startTime : "required",
				dayoff_endTime : "required",
				dayoff_dayoffType: "required",
				dayoff_spendHours:{required:true,number:true},
				dayoff_remark: "required",
			},
			messages : {
				dayoff_startTime : "请填写开始时间",
				dayoff_endTime : "请填写结束时间",
				dayoff_dayoffType: "请选择假别",
				dayoff_spendHours: {required:"请填写加班工时",number:"工时必须是个数字"},
				dayoff_remark: "请填写请假事由",
				
			}
		});
		/***出差表单验证*/
		$.ims.attenceStateCheck.travelform = $("#travel_form").validate({
			errorPlacement: function(error, element) {
				$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				travel_startTime : "required",
				travel_endTime : "required",
				travel_address: "required",
				travel_reason:"required"
			},
			messages : {
				travel_startTime : "请选择开始时间",
				travel_endTime : "请选择结束时间",
				travel_address: "请填写出差地址",
				travel_reason: "请填写出差原因"
				
			}
		});
		/***正班*/
		$.ims.attenceStateCheck.okform = $("#ok_form").validate({
			errorPlacement: function(error, element) {
				$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				ok_remark : "required"
			},
			messages : {
				ok_remark : "请填写正班原因"
			}
		});
		/***正班*/
		$.ims.attenceStateCheck.unknownform = $("#unknown_form").validate({
			errorPlacement: function(error, element) {
				$( element ).closest(".controls").append( error );
			},
			ignore:"",
			rules : {
				ok_remark : "required"
			},
			messages : {
				ok_remark : "请填写异常原因"
			}
		});
	});
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:120px;
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
											<div class="widget-head  bondi-blue">
												<h3>考勤状态核对</h3>
											</div>
										</div>
									</div>
				</div>
				
				<div class="row-fluid ">
							<div class="box well form-inline">
								<label class="control-lableName">考勤日期：</label>
								
								<div class=" input-append date">
											<input id="startDate" value="" style="width: 80px;" type="text" readonly="readonly">
											<span class="add-on"><i class="icon-th"></i></span></div> ~ 
											<div class=" input-append date">
											<input id="endDate" value="" style="width: 80px;" type="text" readonly="readonly">
											<span class="add-on"><i class="icon-th"></i></span>
								</div>
								
								 <span>员工：</span>
								  <select id="user"  data-placeholder="选择员工"  style="width:120px;">
									  <option></option>
									</select>
								  <span>考勤状态：</span>
									 <select id="attenceType"  data-placeholder="考勤状态"  style="width:120px;" class="chosen">
									 <option value="">&nbsp;</option>
											<c:forEach items="${attenceType}" var="bean">
												<c:if test="${bean.name!='加班'}">
													<option value="${bean.code}">${bean.name}</option>
												</c:if>
											</c:forEach>
									 </select>
								 <button id="btnCreate" class="btn btn-info right" type="button" onclick="$.ims.attenceStateCheck.initSearchDataTable()">
								<i class="icon-search icon-white"></i> 查询
								</button>
							</div>
					</div>
					<div class="row-fluid">
								<table class="responsive table table-striped tbl-simple table-bordered table-hover" id="dt_statecheck_view">
									<thead>
										<tr>
											<th>员工工号</th>
											<th>员工姓名</th>
											<th>卡号</th>
											<th>打卡日期</th>
											<th>刷卡记录</th>
											<th>状态</th>
											<th>备注</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
						</div>
					</div>
					</div>
		<%@ include file="../../foot.jsp"%>
	</div>
	<!-- 漏打卡 -->
	<div class="modal hide fade" id="absentee_modal">
		<div class="modal-header blue">漏打卡<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height: 200px;padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id='overtime_form'>
						<input type="hidden" id='absentee_id' value="">
							<div class="control-group">
								<label class="control-label"><span class="error">* </span>漏打卡时间：</label>
								<div class="controls">
									<div class="input-append">
										 <input id="absentee_absenteeTime" name="absentee_absenteeTime" type="text"  >
									</div>
								</div>
							</div>
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>漏打卡类型：</label>
								<div class="controls">
									<select id='absentee_absenteeType' name="absenteeType" class='chosen '>
										<option value="">&nbsp;</option>
											<c:forEach items="${absenteeType}" var="bean">
												<option value="${bean.code}">${bean.name}</option>
											</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label">备注：</label>
								<div class="controls">
									<textarea rows="3" class='span8' id='absentee_remark' name='absentee_remark'></textarea>
								</div>
							</div>
							<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
									<label style="color: red;" id='absentee_tip'></label>
							</div>
								
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveAbsentee()">保存</a> 
			<a class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
	<!--请假 -->
	<div class="modal hide fade" id="attencedayoff_modal">
		<div class="modal-header blue">请假<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height: 200px;padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id='dayoff_form'>
						
						<div class="control-group">
								<label for="type" class="control-label">请假</label>
								<div class="controls">
									<select style="width: 244px" id='dayoff_id' onchange="$.ims.attenceStateCheck.dayOffChange()"  >
										<option value="">&nbsp;</option>
									</select>
								</div>
							</div>
						
						<!-- <input type="hidden" id='dayoff_id' value=""> -->
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>开始时间</label>
								<div class="controls">
									<div class="input-append">
										 <input id="dayoff_startTime" name="dayoff_startTime" type="text" readonly="readonly" class="form_datetime" style="width: 197px" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>结束时间</label>
								<div class="controls">
									<div class="input-append">
										 <input id="dayoff_endTime" name="dayoff_endTime" type="text" readonly="readonly" class="form_datetime" style="width: 197px" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>假别</label>
								<div class="controls">
									<select id='dayoff_dayoffType' name="dayoffType" style="width: 244px"  class='chosen ' >
										<option value="">&nbsp;</option>
									<c:forEach items="${dayyOffType}" var="bean">
										<option value="${bean.code }">${bean.name }</option>
									</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>工时共计</label>
								<div class="controls">
									<input type="text" style="width: 230px" id="dayoff_spendHours" name="dayoff_spendHours" class=" modal_datetime"  >
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>事由</label>
								<div class="controls">
									<textarea rows="3" style="width: 230px"  id='dayoff_remark' name='dayoff_remark'></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveDayOff()">保存</a> 
			<a class="btn" data-dismiss="modal" >关闭</a>
		</div>
	</div>
	<!--出差-->
	<div class="modal hide fade" id="attencetravel_modal">
		<div class="modal-header blue">出差<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="min-height: 200px;padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form id='travel_form' action="" method="get">
						<input type="hidden" id='travel_id' value="">
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>开始时间</label>
								<div class="controls">
									<div class="input-append">
										 <input id="travel_startTime" name="travel_startTime" type="text" readonly="readonly" class="form_datetime" style="width: 197px" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>结束时间</label>
								<div class="controls">
									<div class="input-append">
										 <input id="travel_endTime" name="travel_endTime" type="text" readonly="readonly" class="form_datetime" style="width: 197px" >
										 <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>出差地点</label>
								<div class="controls">
									<input type="text" style="width: 230px"  id="travel_address" name="travel_address" class=" modal_datetime"  >
								</div>
							</div>
							
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>出差原因</label>
								<div class="controls">
									<textarea rows="3"   style="width: 230px"  id='travel_reason' name='travel_reason'></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveTravel()">保存</a> 
			<a class="btn" data-dismiss="modal" >关闭</a>
		</div>
	</div>
		<!--正班-->
	<div class="modal hide fade" id="attenceok_modal">
		<div class="modal-header blue">正班<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form id='ok_form' action="" method="get">
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>备注</label>
								<div class="controls">
									<textarea rows="3" class='span8' id='ok_remark' name='ok_remark'></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveOk()">保存</a> 
			<a class="btn" data-dismiss="modal" >关闭</a>
		</div>
	</div>
	
		<!--未知-->
	<div class="modal hide fade" id="attenceunknown_modal">
		<div class="modal-header blue">未知<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form id='unknown_form' action="" method="get">
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>备注</label>
								<div class="controls">
									<textarea rows="3" class='span8' id='unknown_remark' name='unknown_remark'></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveUnknown()">保存</a> 
			<a class="btn" data-dismiss="modal" >关闭</a>
		</div>
	</div>
	
	<!-- 迟到 -->
		<div class="modal hide fade" id="attencelater_modal">
		<div class="modal-header blue">迟到<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		<div class="modal-body" style="padding-top: 50px;">
			<div class="row-fluid">
				<div class="span12">
					<div
						class="form-container grid-form form-background left-align form-horizontal">
						<form id='later_form' action="" method="get">
							<div class="control-group">
								<label for="type" class="control-label"><span class="error">* </span>备注</label>
								<div class="controls">
									<textarea rows="3" class='span8' id='later_remark' name='later_remark'></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer center">
			<a class="btn btn-primary"onclick="$.ims.attenceStateCheck.saveLater()">保存</a> 
			<a class="btn" data-dismiss="modal" >关闭</a>
		</div>
	</div>
</body>
</html>