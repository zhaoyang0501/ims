<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
 <script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.reimburse.reimburse.js"></script>
 <link href="${pageContext.request.contextPath}/resources/x-editable/css/bootstrap-editable.css" rel="stylesheet"/>
 <script src="${pageContext.request.contextPath}/resources/x-editable/js/bootstrap-editable.min.js"></script>
<head>
<script type="text/javascript">
$(document).ready(function() {
	$.ims.reimburse.reimburseform= $("#reimburseform").validate({
		errorPlacement: function(error, element) {
			$( element ).closest(".controls").append( error );
		},
		ignore:"",
		rules: {
			reimburseDate:  "required",
			number:  "digits",
			userIds:  "required",
			type:"required"
			},
		messages: {
			reimburseDate:"请填写报销日期",
			type:"请选择加班类别",
			number:"必须是个整数",
			userIds:"请选择加班人"
		}
	});
	$.validator.addMethod("checkCustomerDetails",function(value,element,params){  
	var inputArray=$("#table_onther_people input[type='hidden']")
	for(var i=0;i<inputArray.size();i++){
		if($.trim(inputArray.eq(i).val())=='') return false;
	}
	return true;
	},"外来人员信息填写不完整");
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
	 
    $.ims.common.allDinnerType("dinnerType");
    $.ims.common.findAllUser(function(){
    	  $("#userIds").chosen();
    },"userIds");
    $('form #userIds').on('change', function(evt, params) {
		  $.ims.reimburse.reimburseform.element("#userIds");
	});
    $('form #dinnerType').on('change', function(evt, params) {
		  $.ims.reimburse.reimburseform.element("#dinnerType");
	});
  
    $.fn.editable.defaults.emptytext = '空';     
    $.fn.editable.defaults.mode = 'popup';
    var userArray = new Array();
	<c:forEach items="${reimburseDetails}" var="bean" varStatus="status">
		userArray[${status.index}] =${bean.user.id };
	</c:forEach>
	$.ims.common.setchosenvalue("userIds", userArray);
    jQuery.ims.common.setchosenvalue("dinnerType","${reimburse.type}");
    if($("#number").val()!=0){
    	$("#control_onther_people").show();
    	$("#table_onther_people").show("slow");
    	jQuery.ims.reimburse.initEditTable();
    }
    	
});
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
						<div class="content-widgets light-gray">
						<div class="widget-head  bondi-blue">
							<h3>填写报销单</h3>
						</div>
						<div class="widget-container">
							<form id='reimburseform' class="form-horizontal " method="post" action="reimburse/reimburse/save">
								<input name="id" type="hidden" value="${reimburse.id}"  readonly="readonly">
										
								<input name='isSend' id='isSend' type="hidden" value="false">
								<div class="control-group">
									<label class="control-label">报销日期：</label>
									<div class="controls">
										<div class="input-append date">
											<input name="reimburseDate" type="text" value="<fmt:formatDate value="${reimburse.reimburseDate}" type="time" dateStyle="full" pattern="yyyy-MM-dd"/>" readonly="readonly">
											<span class="add-on"><i class="icon-th"></i></span>
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">餐费类别：</label>
									<div class="controls">
										<select name='type' id="dinnerType" >
											<option value="">&nbsp;</option>
										</select>
									</div>
								</div>
								<div class="control-group" >
									<label class="control-label">人员明细（本单位）：</label>
									<div class="controls">
										<select  data-placeholder="选择就餐人员" name='userIds'  id='userIds' class="chzn-select span6" multiple tabindex="4">
										</select>
									</div>
								</div>
								<div class="control-group" style="display: none;">
									<label class="control-label">其他人员人数（非本单位）：</label>
									<div class="controls">
										<input  value='${reimburseCustomerDetail.size()}' name='number' id='number' type="text" placeholder=""  class='span6' onchange="$.ims.reimburse.changePeopleNumber()" >
									</div>
								</div>
								<div class="control-group" id="control_onther_people" style="display: none;">
									<label class="control-label"></label>
									<div class="controls ">
									<div class='span12'>
										<div class='span6'>
											<table id='table_onther_people' class="responsive table table-striped table-bordered table-condensed" style="display: none;margin-bottom: 0px;">
												<thead>
													<tr>
														<th>序号</th>
														<th>姓名</th>
														<th>项目</th>
														<th>单位</th>
													</tr>
												</thead>
												<tbody>
												   <c:forEach items="${reimburseCustomerDetail}" var="bean" varStatus="status">
												   
												   <tr>
													<td>1</td> 
													<td> 
													<input  class='input_username' name='reimburseCustomerDetails[${status.index }].userName'  type='hidden' value='${bean.userName }'>
														<a href='#' class='username' id='username' data-type='text' data-pk='1' data-placement='right'  data-title='请填写用餐人员姓名'>${bean.userName }</a> 
													</td> 
													<td> 
													<input   class='input_project' name='reimburseCustomerDetails[${status.index }].project.id'  type='hidden'  value='${bean.project.id }'>
														<a href='#' class='project' id='project' data-source='reimburse/reimburse/allProject'  data-type='select' data-pk='1'  data-title='选择参与的项目' data-original-title='' >${bean.project.projectName  }</a>
													</td> 
													<td> 
													<input  class='input_company' name='reimburseCustomerDetails[${status.index }].company'  type='hidden'  value='${bean.company }'>
														<a href='#' class='company'  id='company' data-type='text' data-pk='1' data-placement='right'  data-title='请填写用餐人员单位'>${bean.company }</a>
													</td> 
													</tr>
												   </c:forEach>
												</tbody>
											</table>
											</div>
									  </div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">备注：</label>
									<div class="controls">
										<textarea name='remark'  rows="3" class="span6"></textarea>
									</div>
								</div>
								<div class="controls">
									<button type="button" class="btn btn-primary" onclick="$.ims.reimburse.saveAndSend(false)">保存</button>
									<button type="button" class="btn btn-primary" onclick="$.ims.reimburse.saveAndSend(true)">提交审批</button>
									<a class="btn " type="button" href="reimburse/reimburse/">返回</a>
								</div>
								<div class="controls" style="padding-top: 20px">
									<span  style="color: red ;">提示：报销单提交后，请及时填写加班报告。</span>
								</div>
							</form>
						</div>
					</div>
					
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../foot.jsp"%>
	</div>
</body>
</html>