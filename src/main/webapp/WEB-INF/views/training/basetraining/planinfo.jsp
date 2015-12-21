<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
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
								<th>&nbsp;培训计划：</td>
								<td colspan="3">
									${plan.title }
								</td>
							</tr>
							<tr>
								<th>&nbsp;培训计划描述：</td>
								<td colspan="3">${plan.description }</td>
							</tr>
							<tr>
								<th>&nbsp;培训课程：</th>
								<td colspan="3">${plan.courseName }</td>
							</tr>
							<tr>
								<th>&nbsp;参训人员：</th>
								<td colspan="3">${plan.user }</td>
							</tr>
							<tr>
								<th width="25%">&nbsp;开始日期：</th>
								<td width="30%">${plan.start }</td>
								<th width="20%">&nbsp;结束日期：</th>
								<td width="25%">${plan.end }</td>
							</tr>
							<tr>
								<th>&nbsp;培训目标：</th>
								<td colspan="3">${plan.targets }</td>
							</tr>
							<tr>
								<th>&nbsp;登记时间：</th>
								<td colspan="3">${plan.createDate }</td>
							</tr>
							<tr>
								<th>&nbsp;最后修改时间：</th>
								<td colspan="3">${plan.lastupdate }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>