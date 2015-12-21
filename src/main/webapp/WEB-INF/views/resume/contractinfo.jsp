<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
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
									${contract.chinesename }
								</td>
							</tr>
							<tr>
								<th width="25%">&nbsp;合同编号：</td>
								<td width="30%">${contract.contractNo }</td>
								<th width="20%">&nbsp;合同类型：</th>
								<td width="25%">${contract.contractTypeName }</td>
							</tr>
							<tr>
								<th>&nbsp;合同签订日期：</th>
								<td>${contract.signDate }</td>
								<th>&nbsp;合同生效日期：</th>
								<td>${contract.fromDate }</td>
							</tr>
							<tr>
								<th>&nbsp;合同终止日期：</th>
								<td colspan="3">${contract.endDate }</td>
							</tr>
							<tr>
								<th>&nbsp;是否含试用期：</th>
								<td colspan="3">${contract.isProbation == 1? '是' : '否' }</td>
							</tr>
							<tr>
								<th>&nbsp;合同是否已解除：</th>
								<td colspan="3">${contract.isRelieve == 1? '是' : '否' }</td>
							</tr>
							<tr>
								<th>&nbsp;合同是否续签：</th>
								<td colspan="3">${contract.isRenewal == 1? '是' : '否' }</td>
							</tr>
							<tr>
								<th>&nbsp;合同创建日期：</th>
								<td>${contract.createDate }</td>
								<th>&nbsp;最近更新日期：</th>
								<td>${contract.lastUpdateDate }</td>
							</tr>
							<tr>
								<th>&nbsp;备注：</th>
								<td colspan="3">${contract.remarks }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>