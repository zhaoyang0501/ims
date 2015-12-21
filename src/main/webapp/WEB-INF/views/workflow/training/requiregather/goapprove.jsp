<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.requirementgather.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<head>
<script type="text/javascript">
$(function(){
	$.ims.trainingrequirementgather.findTraingType();
	$.ims.trainingrequirementgather.findTeacher();
	$.ims.trainingrequirementgather.findUser();
	
	$.ims.trainingrequirementgather.addNewRow();
	
	
	$(".controls .btn").eq(0).addClass("btn-primary");
	$("#btn-submit").click(function(){
        $(this).button('提交中...').delay(1000).queue(function() {
          	$(this).button('reset');
        });        
    });
	
	if("${tip}" != null && "${tip}" != ""){
		noty({"text":"${tip}","layout":"top","type":"success","timeout":"2000"});
	}
});
</script>
<style type="text/css">
	.valid-error{
		border: 1px solid red !important;
	}
	input[type="text"]{
		margin-bottom: 0px !important;
	}
</style>
</head>
<body>

	<input type="hidden" id="hf_requireId" value="${requireId }" />
	<div class="layout">
		<!-- top -->
		<%@ include file="../../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head bondi-blue">
								<h3>培训需求收集</h3>
							</div>
							<div class="widget" style="margin-top:20px;">
								<table id="dt_traingrequire" class="responsive table table-striped table_bordered_black table-condensed formtable" >
									<tr>
										<td colspan="11">
											<div class="table_title">
												 <h3 style="color:#3399ff;">${year }年培训需求收集</h3> 
											</div>
											<span style="float:right; margin:-20px 10px 0px 0px; color: #3399ff;">
												编号：${sn }
											</span>
										</td>
									</tr>
									<tr style="color: #3399ff;">
										<th width="30px;"><button id="addRow" onclick="$.ims.trainingrequirementgather.addNewRow();">＋</button></th>
										<th width="130px;">培训类型</th>
										<th width="130px;">培训课题</th>
										<th width="130px;">培训对象</th>
										<th width="60px;">计划月份</th>
										<th width="60px;">预计费用</th>
										<th width="40px;">课时</th>
										<th width="80px;">场所(内/外)</th>
										<th width="100px;">机构/讲师</th>
										<th width="130px;">培训目标</th>
										<th>备注</th>
									</tr>
									<tbody id="dt_traingrequire_body">
									</tbody>
								</table>
								<c:if test="${acitons.size()!=0}">
									<div class="control-group">
										<div class="controls">
											<c:forEach items="${acitons}" var="item">
												<input name="operate" type="button" onclick="$.ims.trainingrequirementgather.approve(${item.key})" value="${item.value}" class="btn"></input>
											</c:forEach>
											<button class="btn" onclick="history.go(-1)" class="btn">返回</button>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../../foot.jsp"%>
	</div>
</body>
</html>