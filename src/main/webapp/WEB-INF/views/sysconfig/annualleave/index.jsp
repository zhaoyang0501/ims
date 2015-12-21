<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head> 

<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.sysconfig.annualleave.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
 <link href="${pageContext.request.contextPath}/resources/x-editable/css/bootstrap-editable.css" rel="stylesheet"/>
 <script src="${pageContext.request.contextPath}/resources/x-editable/js/bootstrap-editable.min.js"></script>
<script type="text/javascript">
$(function () {
	$.ims.annualleave.initDatetable();
	$(".date").datetimepicker({
        format:'yyyy',
		autoclose: 1,
		startView: 4,
		minView: 4
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
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>年休假设置</h3>
							</div>
							<div class="widget-container">
								<div class="box well form-inline">	
									<!-- 查询条件 -->
									<span>年份：</span>
									<div class=" input-append date">
									<input id="year"  style="width: 80px;" value="${nowYear}" type="text" readonly="readonly">
									<span class="add-on"><i class="icon-th"></i></span></div> 
									<a onclick="$.ims.annualleave.initDatetable();" class="btn btn-info"><i class="icon-search"></i>查询</a>							
								</div>
								<table class="responsive table table-striped tbl-simple table-bordered table-condensed table-hover" id="annualleaveDataTable">
									<thead>
										<tr>
											<th >员工</th>
											<th >年份</th>
											<th >去年结余</th>
											<th >今年新增</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
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