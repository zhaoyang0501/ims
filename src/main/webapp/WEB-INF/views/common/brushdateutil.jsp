<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="ch">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<%@ include file="../common/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.brushdatautil.js"></script>
<head>
<script type="text/javascript">
$(function(){
	$.ims.common.findAllUser(function(){
		$("#user").chosen({
			no_results_text :"没有找到这个员工",
			placeholder_text:" ",
			allow_single_deselect: true
		})
	},"user");
	
	$('.date').datetimepicker({
		language : 'zh-CN',
		autoclose : 1,
		startView : 2,
		minView : 2,
		format : "yyyy-mm-dd"
	});
});
</script>
</head>
<body>

	<div class="layout">
		<!-- 导航 -->
							
							<div class="row-fluid">
								<div class="box well form-inline">
								<div class="input-append date">
									<input placeholder="起始日期" id="begin" type="text" value="" readonly="readonly" style="width:80px;" >
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								 <select id="user"  data-placeholder="选择员工"  style="width:120px;">
									  <option></option>
									</select>
								<a class="btn btn-info" type="button" onclick="$.ims.commonbrushdata.initbrushdataDataTable();"><i class="icon-search"></i>查询</a>
								</div>
							</div>
							
							<div class="row-fluid">
								<table id='brushdataTable' class=" table-hover responsive table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th>人员</th>
											<th>日期</th>
											<th>刷卡数据</th>
											<th>补卡记录</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
</body>
</html>