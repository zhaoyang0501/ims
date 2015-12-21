<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示流程图</title>
<%@ include file="../../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.approve.workflowgraph.js"></script>
<script type="text/javascript">
$(function() {
	    var xmlStr =$("#wfxml").html().replace(/&lt;/g, "<").replace(/&gt;/g, ">");
	    var lytStr = $("#wflyt").html().replace(/&lt;/g, "<").replace(/&gt;/g, ">");
		drawWf($("#canvas")[0], xmlStr, lytStr, [${stepid}], []);
});
</script>

</head>
<body>
	<textarea id="wfxml" style="display:none;">
		${workflow}
	</textarea>
	<textarea id="wflyt" style="display:none;">
		<layout>
		  <cell id="10" row="1" col="2" type="InitialActionCell" />
		  <cell id="1" row="2" col="2" type="StepCell" />
		  <cell id="2" row="3" col="2" type="StepCell" />
		  <cell id="3" row="4" col="2" type="StepCell" />
		  <cell id="6" row="5" col="2" type="StepCell" />
		</layout>
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
	
	
</body>
</html>
