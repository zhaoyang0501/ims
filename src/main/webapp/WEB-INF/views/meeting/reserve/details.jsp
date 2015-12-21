<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="ch">
<head>
<%@ include file="../../common/meta.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		var id = ${reserve.id};
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/meeting/reservequery/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
					if(json.meetingFile != ""){
						var meetingFile = json.meetingFile.split(',');
						var filehtml = '';
						for(var i = 0; i < meetingFile.length; i++){
							var defaultname = meetingFile[i];
							var filename = defaultname.substring(13);
							filehtml += '<li><a download="'+filename+'" href="${pageContext.request.contextPath}/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a></li>';
						}
						$("#meetingfile").html(filehtml);
					}
					if(json.meetingSummaryFile != ""){
						var summaryFile = json.meetingSummaryFile.split(',');
						var filehtml = '';
						for(var i = 0; i < summaryFile.length; i++){
							var defaultname = summaryFile[i];
							var filename = defaultname.substring(13);
							filehtml += '<li><a download="'+filename+'" href="${pageContext.request.contextPath}/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a></li>';
						}
						$("#summaryfile").html(filehtml);
					}
				}
			}
		});
		
// 		window.showModalDialog("details.jsp",obj,"dialogWidth=200px;dialogHeight=100px");

	});
</script>
<style type="text/css">
	.table td{
		vertical-align: middle;
	}
</style>
</head>
<body style="background-color:white">
	<div class="layout">
		<div class="row-fluid ">
			<div class="span12">
					<div class="widget-container">
						<!-- 编辑新增弹出框 -->
					<h4 align="center" style="color: #3399ff;">会议预约单</h4>

					<table id='dt_away' class="  table   table-condensed  table_bordered_black">
							<tr>
								<td style="background-color: #eeeeee;" width="15%">&nbsp;会议名称：</td>
								<td width="25%">
									<label>${reserve.meetingName }</label> 
								</td>
								<td style="background-color: #eeeeee;" width="15%">&nbsp;开始时间：</td>
								<td width="15%">
									<fmt:formatDate value="${reserve.startTime}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
								<td style="background-color: #eeeeee;" width="15%">&nbsp;结束时间：</td>
								<td width="15%">
									<fmt:formatDate value="${reserve.endTime}" pattern="yyyy-MM-dd HH:mm" />
								</td>
							</tr>
							<tr>
								<td style="background-color: #eeeeee;">&nbsp;会议室：</td>
								<td><label>${reserve.roomName}</label></td>
								<td style="background-color: #eeeeee;">&nbsp;会议类型：</td>
								<td><label>${reserve.typeValue}</label></td>
								<td style="background-color: #eeeeee;">&nbsp;会议人数：</td>
								<td><label>${reserve.sum}</label></td>

							</tr>
							<tr>
								<td style="background-color: #eeeeee;">&nbsp;会议主持人：</td>
								<td><label>${reserve.compereName}</label></td>
								<td style="background-color: #eeeeee;">&nbsp;会议纪要人：</d>
								<td><label>${reserve.registrarName}</label></td>
								<td style="background-color: #eeeeee;">&nbsp;会议服务：</td>
								<td><label>${reserve.meetingServeName}</label></td>
							</tr>
							<tr>
								<td style="background-color: #eeeeee;">&nbsp;参会人员：</td>
								<td colspan="5">
									<textarea readonly="readonly"  name='approve3' style="width: 95%" rows="2" cols="">${reserve.attendeeName}</textarea>
								</td>
							</tr>
							<tr>
								<td style="background-color: #eeeeee;">&nbsp;会议内容：</td>
								<td colspan="5">
									<textarea readonly="readonly"  name='approve3' style="width: 95%" rows="2" cols="">${reserve.meetingDescription}</textarea>
								</td>
							</tr>
							<tr>	
								<td style="background-color: #eeeeee;">&nbsp;会议纪要：</td>
								<td colspan="5">
									<textarea readonly="readonly"  name='approve3' style="width: 95%" rows="2" cols="">${reserve.meetingSummary}</textarea>
								</td>
							</tr>
							<tr>
								<td style="background-color: #eeeeee;">	
									&nbsp;会议附件：
								</td>
								<td colspan="2" >
									<ol id = "meetingfile" >
										
									</ol>
								</td style="background-color: #eeeeee;">
								<td>	
									&nbsp;纪要附件：
								</td>	
								<td colspan="2" >
									<ol id = "summaryfile">
									</ol>
								</td>
							</tr>
							
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>