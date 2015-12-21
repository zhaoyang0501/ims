<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="ch">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示流程图</title>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.workflowgraph.js"></script>
<style type="text/css"> 
#detail{border:1px solid #cccccc; background:#0088cc;color:#fff; padding:5px; display:none; position:absolute;} 
</style>
<script type="text/javascript">
$(function() {
		var xmlStr = $("#wfxmlText").val();
		var lytStr = $("#wflytText").val();
		var canvas = $("#canvas");
		drawWf(canvas[0], xmlStr, lytStr, [${currentStepStr}], [${historyStepStr}]);
});

function doReset(){
	var xmlStr = $("#wfxmlText").val();
	var lytStr = $("#wflytText").val();
	var canvas = $("#canvas");
	drawWf(canvas[0], xmlStr, lytStr, [${currentStepStr}], [${historyStepStr}]);
}

function mouseOverCallBack(e,cell){
	if($("#step"+cell.id).size()!==0) 
		$("#detail").css("top",(e.pageY - 30) + "px").css("left",(e.pageX + 30) + "px").fadeIn("slow").html($("#step"+cell.id).html());
	else{
		$("#detail").fadeOut("fast").html("");
	}
}
</script>

</head>
<body>
<textarea id="wfxmlText" cols="60" rows="10" style="display: none;">
${workflowXml}
</textarea>
<br/>
<textarea id="wflytText" cols="60" rows="10" style="display: none;">
${workflowLayOutXml}
</textarea>
<div class="row-fluid">
		<div class="span12">
			<div class="content-widgets gray">
				<div class=" widget-head  bondi-blue">
					<h3>提示：绿色代表已完成，红色代表进行中，黄色代表待完成</h3>
				</div>
				<div class="widget-container">
					<canvas id="canvas" width="500" height="500">
					</canvas>
				</div>
			</div>
		</div>
	</div>
	<div  id='detail'>
	</div>
	<div  style="display: none;">
	<c:forEach items="${historyStep }" var="step">
		<table id='step${step.stepid}'>
			<tr> <td>处理人：</td><td>${step.userName}</td></tr>
			<tr> <td>动作：</td><td>${step.action }</td></tr>
			<tr> <td>收到时间：</td><td> <fmt:formatDate value="${step.startDate }" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>  </td></tr>
			<tr> <td>处理时间：</td><td> <fmt:formatDate value="${step.finishDate }" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>  </td></tr>
			<tr> <td>处理意见：</td><td>${step.approve }</td></tr>
		</table>
	</c:forEach>
	<c:forEach items="${currentStep }" var="step">
		<table id='step${step.stepid}'>
			<tr> <td>处理人：</td><td>${step.userName}</td></tr>
			<tr> <td>收到时间：</td><td> <fmt:formatDate value="${step.startDate }" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/>  </td></tr>
		</table>
	</c:forEach>
	</div>
</body>
</html>
