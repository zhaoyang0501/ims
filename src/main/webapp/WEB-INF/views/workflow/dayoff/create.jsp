<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@page import="com.hsae.ims.utils.RightUtil"%>
	<%@ include file="../../common/meta.jsp"%>
	<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
	<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.workflow.dayoff.js"></script>
	
<head>
	
	<script type="text/javascript">
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
			$("input[name=dayoffType]").change(function(){
				if($(this).hasClass("error"))
					$(this).parent().parent().addClass("error");
				else 
					$(this).parent().parent().removeClass("error");
			});
			
			$("select[name='proxy.id']").change(function(){
				if($("select[name='proxy.id']").hasClass("error"))
					$("select[name='proxy.id']").next().addClass("error");
				else 
					$("select[name='proxy.id']").next().removeClass("error");
			});
			
			
			jQuery.ims.workflowdayoff.dayoffform= $("#dayoffform").validate({
				errorPlacement: function(error, element) {
					if($("input[name=dayoffType]").hasClass("error"))
						$("input[name=dayoffType]").parent().parent().addClass("error");
					else 
						$("input[name=dayoffType]").parent().parent().removeClass("error");
					
					if($("select[name='proxy.id']").hasClass("error"))
						$("select[name='proxy.id']").next().addClass("error");
					else 
						$("select[name='proxy.id']").next().removeClass("error");
					
				},
				ignore:"",
				rules: {
					"proxy.id":  "required",
					startTime:"required",
					endTime:"required",
					tel:  "required",
					days:{digits:true,required:true},
					hours:{number:true,required:true},
					dayoffType:"required",
					remark:"required"
					}
				
			});
			
			$.ims.common.findAllUser(function(){
				$("#proxy").chosen({
					no_results_text :"没有找到这个员工",
					placeholder_text:" ",
					allow_single_deselect: true
				})
			},"proxy");
			
			$.ims.common.findAllUser(function(){
				$("#leader").chosen({
					no_results_text :"没有找到这个员工",
					placeholder_text:" ",
					allow_single_deselect: true
				})
			},"leader");
			
			
			$(".date").datetimepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd hh:ii',
				autoclose : 1,
				todayHighlight : 1,
				minuteStep:30,
				forceParse : 0
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
							<h3>填写请假单</h3>
						</div>
						<div class="widget-container">
							<form id='dayoffform' class="form-horizontal " method="post" action="workflow/dayoff/save">
							 <input type="hidden" name="token" id='token' value="${token}">
								<table id='' class=" responsive table table-striped table_bordered_black table-condensed formtable" >
									<tr>
										<td colspan="6">
											<div class="table_title">
												 <h3>员 工 请 假 单</h3> 
											</div>
										</td>
									</tr>
									<tr>
										<td class='lable'>部门</td>
										<td>${user.dept.name }</td>
										<td class='lable'>姓名：</td>
										<td>${user.chinesename }</td>
										<td class='lable'>工号：</td>
										<td><input type="text" value="${user.empnumber }" readonly="readonly"></input> </td>
									</tr>
									<tr>
										<td class='lable'>工作接替人：<span class="text-error">*</span></td>
										<td> 
										 <select id='proxy' name="proxy.id"  data-placeholder=""  >
										  <option></option>
										</select> 
										</td>
										<td class='lable'>直属产品经理：</td>
										<td> 
										 <select id='leader' name="leader"  data-placeholder=""  >
										  <option></option>
										</select> 
										</td>
										<td class='lable'>联系电话：<span class="text-error">*</span></td>
										<td><input name='tel' type="text" value=""></input> </td>
									</tr>
									<tr>
										<td class='lable'>请假类别：</td>
										<td colspan="5" >
											<div>
												<c:forEach items="${dayyOffTypes }" var="bean">
												<label class="radio inline">
													  <input  type="radio" name='dayoffType' value="${bean.code}">${bean.name}
												</label>
												</c:forEach>
											</div>
										 </td>
									</tr>
									<tr>
										<td class='lable'>请假时间：</td>
										<td colspan="2" >
										<span class='lable'>自<span class="text-error">*</span></span>
										<div class="input-append date">
										 <input style="width: 115px" id="dayoff_startTime" name="startTime" type="text" readonly="readonly" class="form_datetime">
										 <span class="add-on"><i class="icon-th"></i></span>
									   </div>
									<span class='lable'>至<span class="text-error">*</span></span>
											<div class="input-append date">
											 <input style="width: 115px" id="dayoff_endTime" name="endTime" type="text" readonly="readonly" class="form_datetime">
											 <span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										<td colspan="3"><span class='lable'> 总共请假</span><input  name='days' style="width: 100px" type="text" value=""  ></input>	<span class="text-error">*</span><span class='lable'> 天</span>  <input   name='hours'  style="width: 100px"  type="text" value=""  ></input>   <span class='lable'> 小时</span></td>
									</tr>
									<tr>
									</tr>
									<tr>
										<td class='lable'>请假事由：<span class="text-error">*</span></td>
										<td colspan="5" ><textarea  name='remark' style="width: 90%" rows="2" cols=""></textarea> </td>
									</tr>
									<tr >
										<td class='lable'>产品经理：</td>
										<td colspan="5" ><textarea readonly="readonly"  name='approve1' style="width: 90%" rows="2" cols=""></textarea> </td>
									</tr>
									
									<tr >
										<td class='lable'>申请部门意见：</td>
										<td colspan="5" ><textarea readonly="readonly"  name='approve2' style="width: 90%" rows="2" cols=""></textarea> </td>
									</tr>
									<tr >
										<td class='lable'>领导意见：</td>
										<td colspan="5" ><textarea readonly="readonly"  name='approve3' style="width: 90%" rows="2" cols=""></textarea> </td>
									</tr>
									<tr >
										<td class='lable'>人事意见：</td>
										<td colspan="5" ><textarea readonly="readonly"  name='approve4' style="width: 90%" rows="2" cols=""></textarea> </td>
									</tr>
									<tr  class='remark'>
										<td colspan="6">
											<ol>
											  <li>凡请病假者，需提供镇级以上医院证明。</li>
											  <li>凡请婚假者，需提供结婚证原件及复印件，由HR验原件留复印件。</li>
											  <li>凡请调休假者，加班必须事先已经完成，而且在HR已经有各级领导审批通过的加班申请单相核对，方可按当日实际出勤打卡时间对调调休，不足1小时不计加班也免予调休。</li>
											  <li>凡请产假者，休产假前需提供有效的准生证明，方可按相关政策按有薪计算，否则按无薪事假处理。</li>
											  <li>工伤假凭工伤申报单享受工伤待遇。</li>
											  <li>凡请假满三天以上者需公司领导加签，HR审核后方可生效。</li>
											  <li>未提供以上相关证明者按无薪事假计算。</li>
											</ol>
										</td>
									</tr>
								</table>
								<div >
									<button type="button" class="btn btn-primary" onclick="$.ims.workflowdayoff.saveAndSend(this)">提交</button>
									<a class="btn " type="button" href="workflow/dayoff">返回</a>
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