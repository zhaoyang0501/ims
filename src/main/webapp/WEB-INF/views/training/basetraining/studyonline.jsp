<!DOCTYPE html>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pdfobject.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
		var success = new PDFObject({ url: "<c:url value='/upload/basetraining/${pdfFile}'/>", pdfOpenParams: { scrollbars: '0', toolbar: '-1', statusbar: '0'}}).embed("pdfDiv");
	});
	
	function studyover(){
		bootbox.confirm("亲 ，你真的看完了吗？", function(result){
            if(result){
            	var planCourseId = $("#hf_id").val();
            	$.ajax({
        			type : "post",
        			url : $.ims.getContextPath() + "/basetraining/studyonline/state",
        			data : { 
        				state : 1,
        				planCourseId : planCourseId
       				},
        			dataType : "json",
        			success : function(json) {
        				var code = json.code;
        				var msg = json.msg;
        				if(code == 1){
        					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
        				}else{
        					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
        				}
        			}
        		});
            }
		});
	}

</script>
<style type="text/css">
	#pdfDiv {
		width: 900px;
 		height: 600px;
		margin: 2em auto;
		border: 1px solid #65452;
	}
 
	#pdfDiv p {
	   padding: 1em;
	}
	 
	#pdfDiv object {
	   display: block;
	   border: solid 1px #123;
	}
	@media print{ 
	   	body{display:none} 
	} 
</style>
<title>在线学习</title>
</head>
<body>
	<!-- top -->
	<input type="hidden" id="hf_id" value="${id }" />
	<div id="pdfDiv" style="height: 90%;position: fixed;">
	</div>
	<div style="position:fixed; bottom:15px; right:15px;">
		<c:if test="${state != 1 }">
			<button id="btn_over" onclick="studyover()" class="btn btn-info">学习完毕</button>
		</c:if>
	</div>
</body>
</html>