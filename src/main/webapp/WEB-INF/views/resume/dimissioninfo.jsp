<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<title>合同详情</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<style type="text/css">
	th{
		background-color: #eeeeee;
	}
</style>
</head>
<body>
	<div class="layout">
		<div class="container-fluid">
			<div class="row-fluid ">
				<div class="span12">
					<div class="widget-container">
						<table class="table table-bordered table-condensed">
							<tr>
								<th>&nbsp;员工：</td>
								<td colspan="3">
									${dimission.chinesename }
								</td>
							</tr>
							<tr>
								<th width="25%">&nbsp;部门：</td>
								<td width="30%">${dimission.deptname }</td>
								<th width="20%">&nbsp;离职类型：</th>
								<td width="25%">${dimission.dimissionTypeName }</td>
							</tr>
							<tr>
								<th>&nbsp;申请日期：</th>
								<td>${dimission.applydate }</td>
								<th>&nbsp;拟离职日期：</th>
								<td>${contract.plandate }</td>
							</tr>
							<tr>
								<th>&nbsp;离职原因：</th>
								<td colspan="3">${dimission.reason }</td>
							</tr>
							<tr>
								<th>&nbsp;备注：</th>
								<td colspan="3">${dimission.remarks }</td>
							</tr>
							<tr>
								<th>&nbsp;登记时间：</th>
								<td colspan="3">${dimission.createDate }</td>
							</tr>
							<tr>
								<th>&nbsp;最后修改时间：</th>
								<td colspan="3">${dimission.lastupdateDate }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>