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
	<style type="text/css">
	#planUserIds,#realUserIds{
		width: 80%;
	}
	#dayoffform input{
		width: 118px;
	}
	</style>
<head>
	
	<script type="text/javascript">
		$(function() {
			$.ims.common.findAllUser(function(){
				$("#planTeacher").chosen({
					no_results_text :"没有找到这个讲师",
					placeholder_text:" ",
					allow_single_deselect: true
				})
			},"planTeacher");
			
			$.ims.common.findAllUser(function(){
				$("#realTeacher").chosen({
					no_results_text :"没有找到这个讲师",
					placeholder_text:" ",
					allow_single_deselect: true
				})
			},"realTeacher");
			
			 $.ims.common.findAllUser(function(){
		    	  $("#realUserIds").chosen();
		    },"realUserIds");
			 $.ims.common.findAllUser(function(){
		    	  $("#planUserIds").chosen();
		    },"planUserIds");
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
			
			jQuery.ims.workflowdayoff.dayoffform= $("#dayoffform").validate({
				errorPlacement: function(error, element) {
					if($("input[name=dayoffType]").hasClass("error"))
						$("input[name=dayoffType]").parent().parent().addClass("error");
					else 
						$("input[name=dayoffType]").parent().parent().removeClass("error");
				},
				ignore:"",
				rules: {
					startTime:"required",
					endTime:"required",
					remark:"required"
					}
				
			});
			$(".date").datetimepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd ',
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
							<h3>培训计划</h3>
						</div>
						<div class="widget-container">
							<form id='dayoffform' class="form-horizontal " method="post" action="training/plan/save">
								<table id='' class=" responsive table table-striped table_bordered_black table-condensed formtable" >
									<tr>
										<td colspan="9">
											<div class="table_title">
												 <h3>培训记录表</h3> 
											</div>
										</td>
									</tr>
									
									<tr>
										<td class='lable'>来源：</td>
										<td  colspan="2"> 
											 <input name='source' type="text"  style='width: 80%' />
										</td>
										<td class='lable'>培训类型：<span class="text-error">*</span></td>
										<td  colspan="2"><input name='trainingType' type="text" value=""></input> </td>
										<td class='lable'>编号：<span class="text-error">*</span></td>
										<td colspan="2"> 
											<input type="text"   name='sn' />
										</td>
									</tr>
									<tr>
										<td class='lable'>培训主题：</td>
										<td colspan="8" >
											<input type="text"   name='name' style="width: 90%" />
										 </td>
									</tr>
									<tr>
										<td class='lable'>培训内容：</td>
										<td colspan="8" >
											<textarea  name='contents' style="width: 90%" rows="2" cols=""></textarea> 
										 </td>
									</tr>
									<tr>
										<td class='lable'>备注：</td>
										<td colspan="8" >
											<textarea   name='remark' style="width: 90%" rows="2" cols=""></textarea> 
										 </td>
									</tr>
									
									<tr>
										<td class='lable' rowspan="3">计划：</td>
										<td class='lable' >培训日期起：</td>
										<td >
											<div class="input-append date">
												 <input style="width: 85px"  name="planStartTime" type="text" readonly="readonly" class="form_datetime">
												 <span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										<td class='lable' >培训日期止：</td>
										<td >
											<div class="input-append date">
											 	<input style="width: 85px"  name="planEndTime" type="text" readonly="readonly" class="form_datetime">
											 	<span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										
										<td class='lable'>培训时间：</td>
										<td>
											<div class="input-append date">
												 <input style="width: 85px" id="planTime" name="startTime" type="text" readonly="readonly" class="form_datetime">
											 	<span class="add-on"><i class="icon-th"></i></span>
										   	</div>
										</td>
										<td class='lable'>课时：</td>
										<td> 
										 	<input type="text" name='planHours'/>
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>预算：</td>
										<td> 
										 	<input type="text" name='planCost'/>
										</td> 
										
										<td class='lable'>讲师：</td>
										<td> 
										 	<select id="planTeacher" name="planTeacher.id"  data-placeholder='选择讲师'>
										 	 <option></option>
										 	</select>
										</td> 
										
										<td class='lable'>考核方式：</td>
										<td> 
											 <input type="text" name='planCheckType'/>
										</td> 
										
										<td class='lable'>设备器材：</td>
										<td> 
										 	<input type="text" name='planEquipment'/>
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>参与人员：</td>
										<td colspan="3"> 
										 	<select  onchange="users_change()" data-placeholder="选择就餐人员" name='planUserIds'  id='planUserIds'   class="chzn-select " multiple >
											</select>
										</td> 
										<td class='lable'>培训地点：</td>
										<td colspan="3"> 
										 		<textarea  name='planAddr' style="width: 90%" rows="2" cols=""></textarea> 
										</td> 
									</tr>
									<!-- 实际情况 -->
									<tr>
										<td class='lable' rowspan="3">实际：</td>
										<td class='lable' >培训日期起：</td>
										<td >
											<div class="input-append date">
												 <input style="width: 85px"  name="realStartTime" type="text" readonly="readonly" class="form_datetime">
												 <span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										<td class='lable' >培训日期止：</td>
										<td >
											<div class="input-append date">
											 	<input style="width: 85px"  name="realEndTime" type="text" readonly="readonly" class="form_datetime">
											 	<span class="add-on"><i class="icon-th"></i></span>
										    </div>
										</td>
										
										<td class='lable'>培训时间：</td>
										<td>
											<div class="input-append date">
												 <input style="width: 85px" id="realTime" name="realTime" type="text" readonly="readonly" class="form_datetime">
											 	<span class="add-on"><i class="icon-th"></i></span>
										   	</div>
										</td>
										<td class='lable'>课时：</td>
										<td> 
										 	<input type="text" name='realHours'/>
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>预算：</td>
										<td> 
										 	<input type="text" name='realCost'/>
										</td> 
										
										<td class='lable'>讲师：</td>
										<td> 
										 	<select id="realTeacher" name="realTeacher.id"  data-placeholder="选择讲师">
										 	 <option></option>
										 	</select>
										</td> 
										
										<td class='lable'>考核方式：</td>
										<td> 
											 <input type="text" name='realCheckType'/>
										</td> 
										
										<td class='lable'>设备器材：</td>
										<td> 
										 	<input type="text" name='realEquipment'/>
										</td> 
									</tr>
									
									<tr>
										<td class='lable'>参与人员：</td>
										<td colspan="3"> 
										 	<select  onchange="users_change()" data-placeholder="选择就餐人员" name='realUserIds'  id='realUserIds'   class="chzn-select " multiple >
											</select>
										</td> 
										<td class='lable'>培训地点：</td>
										<td colspan="3"> 
										 		<textarea  name='realAddr' style="width: 90%" rows="2" cols=""></textarea> 
										</td> 
									</tr>
									
									<tr  class='remark'>
										<td colspan="9">
											<ol>
											  <li>XXXx</li>
											  <li>XXXx</li>
											  <li>XXXx</li>
											  <li>XXXx</li>
											 
											</ol>
										</td>
									</tr>
								</table>
								<div >
									<button type="submit" class="btn btn-primary" onclick="">提交</button>
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