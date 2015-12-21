<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@page import="com.hsae.ims.utils.RightUtil"%>
	<%@ include file="../../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.reimburse.js"></script>
	<style type="text/css">
	input[type="radio"]{
	  vertical-align: top;
	}
	#userIds{
		width: 280px;
	}
	</style>
<head>
	
	<script type="text/javascript">
		function users_change(){
			 if($("#userIds").val()!=null) 
				$("#total_monney").val($("#userIds").val().toString().split(",").length*12);
			else 
				$("#total_monney").val(0); 
		};
	
		$(function() {
			<c:if test="${response!=null&&response.code=='1'}">
			<c:if test="${response.datas!=null}">
				noty({"text":"操作成功,流程已提交给${response.datas.chinesename}","layout":"top","type":"success","timeout":"10000"});
			</c:if>
			<c:if test="${response.datas==null}">
				noty({"text":"操作成功,流程已结束${response.datas.chinesename}","layout":"top","type":"success","timeout":"2000"});
			</c:if>
		</c:if>
		<c:if test="${response!=null&&response.code!='1'}">
			noty({"text":"${response.msg}","layout":"top","type":"error","timeout":"2000"});
		</c:if>
			 $.ims.common.findAllUser(function(){
		    	  $("#userIds").chosen({width:"200px"});
		    },"userIds");
			
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
			

			jQuery.ims.workflowreimburse.reimburseform= $("#reimburseform").validate({
				errorPlacement: function(error, element) {
					if($("input[name=type]").hasClass("error"))
						$("input[name=type]").parent().parent().addClass("error");
					else 
						$("input[name=type]").parent().parent().removeClass("error");
					if($("select[name=userIds]").hasClass("error"))
						$("select[name=userIds]").next().addClass("error");
					else 
						$("select[name=userIds]").next().removeClass("error");
				},
				ignore:"",
				rules: {
					reimburseDate:"required",
					userIds:"required",
					type:  "required"
					}
				
			});
			
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
						<div class="content-widgets ">
						<div class="widget-head  bondi-blue">
							<h3>填写报销单</h3>
						</div>
						<div class="widget-container">
							<form id='reimburseform' class="form-horizontal " method="post" action="workflow/reimburse/save">
							 <input type="hidden" name="token" value="${token}">
								<table class=" table_bordered_black responsive table table-striped  table-condensed formtable" >
									<tr>
											<td colspan="6">
												<div class="table_title">
													 <h3>员工餐费报销单</h3> 
												</div>
											</td>
									</tr>
										
									<tr>
										<td class='lable'>部门</td>
										<td>${user.dept.name }</td>
										<td class='lable'>姓名：</td>
										<td>${user.chinesename }</td>
										<td class='lable'>工号：</td>
										<td><input   type="text" value="${user.empnumber }" readonly="readonly"></input> </td>
									</tr>
									<tr>
									<td class='lable'>就餐日期：</td>
										<td > 
										<div class=" input-append date"><input class="required"  style="width: 120px;"  name="reimburseDate"  type="text" readonly="readonly"><span class="add-on"><i class="icon-th"></i></span></div>
										</td>
										<td class='lable'>餐别：</td>
										<td > 
										<div>
												<label class="radio inline">
													  <input  type="radio" name='type' value="1">午餐
												</label>
												<label class="radio inline">
													  <input  type="radio" name='type' value="2">晚餐
												</label>
												
											</div>
										</td>
										
										<td class='lable'>就餐人员明细：</td>
										<td > 
										 <select  onchange="users_change()" data-placeholder="选择就餐人员" name='userIds'  id='userIds'   class="chzn-select " multiple >
										</select>
										</td>
									</tr>
									<tr>
										<td  class='lable'>总报销金额：</td>
										<td colspan="5" ><input id='total_monney' style="width: 154px;"  type="text" value="0" readonly="readonly"></input> </td>
									</tr>
									<tr >
										<td class='lable'>备注：</td>
										<td colspan="5" ><textarea  name='remark' style="width: 90%" rows="3" cols=""></textarea> </td>
									</tr>
									<tr  class='remark'>
										<td colspan="6">
											<ol>
											  <li>加班餐费报销范围：上海技术中心扬州办公人员。</li>
											  <li>加班餐费报销标准：12元/人餐。</li>
											   <li>因出差人员有餐补，出差加班不可报销加班餐费。</li>
											  <li>加班餐费报销条件（需同时满足以下几点）：
											  <ol type="a">
												 <li>工作原因加班；</li>
												 <li>
												 	满足一定加班时长且跨过用餐时间，具体见下表：
												 	<table class='table_bordered_black responsive table table-striped  table-condensed formtable'>
												 		<tr>
												 		<td></td>
												 		<td>午餐</td>
												 		<td>晚餐</td>
												 		</tr>
												 		<tr>
												 		<td>工作日</td>
												 		<td></td>
												 		<td>加班2h（含）以上，且跨过晚餐时间</td>
												 		</tr>
												 		<tr>
												 		<td>休息日</td>
												 		<td>11:30之前开始加班，加班时间3h（含）以上，且跨过午餐时间（12:30）</td>
												 		<td>17:00之前开始加班，加班时间3h（含）以上，且跨过晚餐时间（18:00）
												 		</td>
												 		</tr>
												 	</table>
												 </li>
												 <li>IMS系统日报中填写加班内容；</li>
												 <li>订餐人在IMS系统填写加班餐费报销单。</li>
												</ol> 
											 </li>
											</ol>
										</td>
									</tr>
								</table>
								<div>
									<button type="button" class="btn btn-primary" onclick="$.ims.workflowreimburse.saveAndSend(this)">提交</button>
									<a class="btn " type="button" href="workflow/reimburse">返回</a>
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