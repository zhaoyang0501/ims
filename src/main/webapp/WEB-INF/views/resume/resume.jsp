<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" " http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>

<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/print.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.resume.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.jqprint-0.3.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

	});
	function print() {
		$("#div_print").jqprint(function() {
			importCSS: true;
			printContainer: true;
		});
	}
</script>
<style type="text/css">
#header, .top_title, #jqprint, #footer, #cssprint h3 {
	display: none !important
}
.header_img {
	width: 105px;
	height: 130px;
	display: block;
	border: 1px solid #CDCDCD;
	margin-top: 3px;
}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="content-widgets light-gray">
						<div class="widget-head  bondi-blue">
							<h3>档案详情</h3>
						</div>
						<div class="widget-container white">
							<div style="float: left;">
								<a class="btn" href="<c:url value='sysconfig/resume'></c:url>"><i class="icon-share-alt"></i>返回</a>
								<a class="btn btn-primary" onclick="print()"><i class="icon-print"></i>打印</a> 
							</div>
							<div id="div_print" class="print-div">
								<br>
								<span class="head-title">基本信息</span>
								<table style="background-color: white; table-layout: fixed; width: 100%;" class="table table-bordered table-condensed table-hover" id="b_dataTable">
									<tr>
										<td style="width:11%">姓名</td>
										<td style="">${resume.userName}</td>
										<td style="width:11%">性别</td>
										<td style="">${resume.sex}</td>
										<td style="width:11%">工号</td>
										<td colspan="2">${resume.empnumber}</td>
										<td rowspan="5" style="">
											<c:choose>
												<c:when test='${resume.picture == null || resume.picture == ""}'>
													<img class ="header_img" src="${pageContext.request.contextPath}/resources/index/bootstrap-3.3.2/img/user.jpg" />
												</c:when>
												<c:otherwise>
													<img class ="header_img" src="${pageContext.request.contextPath}/upload/photo/${resume.picture}" >
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>籍贯</td>
										<td>${resume.place}</td>
										<td>民族</td>
										<td>${resume.nation}</td>
										<td>身份证号码</td>
										<td colspan="2" style="word-wrap: break-word;">${resume.idNumber}</td>
									</tr>
									<tr>
										<td>政治面貌</td>
										<td>${resume.politicsStatus}</td>
										<td>婚否</td>
										<td>${resume.marrige}</td>
										<td>户口所在地</td>
										<td colspan="2" style="word-wrap: break-word;">${resume.domicilePlace}</td>
									</tr>
									<tr>
										<td>手机</td>
										<td style="word-wrap: break-word;">${resume.mobile}</td>
										<td>服务年限</td>
										<td>${resume.workYear}年</td>
										<td>身份证地址</td>
										<td colspan="2" style="word-wrap: break-word;">${resume.idAddress}</td>
									</tr>
									<tr>
										<td>最高学历</td>
										<td>${resume.education}</td>
										<td>最高学位</td>
										<td>${resume.degree}</td>
										<td>生日</td>
										<td colspan="2">${resume.birthday}</td>
									</tr>
									<tr>
										<td>现在住址</td>
										<td colspan="3" style="word-wrap: break-word;">${resume.address}</td>
										<td>学校</td>
										<td style="word-wrap: break-word;">${resume.school}</td>
										<td style="width:11%">专业</td>
										<td style="word-wrap: break-word;">${resume.major}</td>

									</tr>
									<tr>
										<td>毕业时间</td>
										<td>${resume.graduateTime}</td>
										<td>入职时间</td>
										<td>${resume.joinTime}</td>
										<td>转正时间</td>
										<td>${resume.conversionTime}</td>
										<td>职称</td>
										<td>${resume.title}</td>
									</tr>
									<tr>
										<td>现任职部门</td>
										<td>${resume.dept}</td>
										<td>现岗位</td>
										<td>${resume.post}</td>
										<td>职位</td>
										<td>${resume.position}</td>
										<td>职等</td>
										<td>${resume.grade}</td>
									</tr>
									<tr>
										<td>合同开始</td>
										<td>${resume.contractStartDate}</td>
										<td>合同结束</td>
										<td>${resume.contractEndDate}</td>
										<td>社保选择</td>
										<td>${resume.socialMoney}</td>
										<td>公积金选择</td>
										<td>${resume.publicMoney}</td>
									</tr>
									<tr>
										<td>外语水平</td>
										<td colspan="3">${resume.language}</td>
										<td>电子邮件</td>
										<td colspan="3" style="word-wrap: break-word;">${resume.email}</td>
									</tr>
									<tr>
										<td>兴趣爱好</td>
										<td colspan="7">${resume.hobbies}</td>
									</tr>
									<tr>
										<td>技能擅长</td>
										<td colspan="7">${resume.skill}</td>
									</tr>
								</table>
								<span class="head-title">家庭情况</span>
								<table style="background-color: white; table-layout: fixed; width: 100%" class="table table-bordered table-condensed table-hover" id="a_dataTable">
									<tr>
										<td style="width: 12%">姓名</td>
										<td style="width: 12%">年龄</td>
										<td style="width: 12%">称谓</td>
										<td style="width: 15%">工作单位职务</td>
										<td>联系电话</td>
									</tr>
									<c:forEach items="${family}" var="item">
										<tr>
											<td>
												<c:out value="${item.familyName}"></c:out>
											</td>
											<td>
												<c:out value="${item.familyAge}"></c:out>
											</td>
											<td>
												<c:out value="${item.familyTitle}"></c:out>
											</td>
											<td>
												<c:out value="${item.familyCompany}"></c:out>
											</td>
											<td>
												<c:out value="${item.familyMobile}"></c:out>
											</td>
										</tr>
									</c:forEach>
								</table>
								<span class="head-title">教育及重要培训经历</span>
								<table style="background-color: white; table-layout: fixed; width: 100%" class="table table-bordered table-condensed table-hover" id="c_dataTable">
									<tr>
										<td style="width: 12%">开始日期</td>
										<td style="width: 12%">结束日期</td>
										<td style="width: 12%">学校（机构）</td>
										<td style="width: 15%">专业（项目名称）</td>
										<td style="width: 15%">学历（证书）</td>
										<td style="">学习方式</td>
									</tr>
									<c:forEach items="${education}" var="item">
										<tr>
											<td style="">
												<c:out value="${item.eduStartTime}"></c:out>
											</td>
											<td style="">
												<c:out value="${item.eduEndTime}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.eduSchool}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.eduMajor}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.eduCertificate}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.eduWay}"></c:out>
											</td>
										</tr>
									</c:forEach>
								</table>
								<span class="head-title">公司外工作经历</span>
								<table style="background-color: white; table-layout: fixed; width: 100%" class="table  table-bordered table-condensed table-hover" id="d_dataTable">
									<tr>
										<td style="width: 12%">开始日期</td>
										<td style="width: 12%">结束日期</td>
										<td style="width: 12%">公司名称</td>
										<td style="width: 15%">部门</td>
										<td style="width: 15%">职务</td>
										<td style="width: 15%">从业经历</td>
										<td style="">离职原因</td>
									</tr>
									<c:forEach items="${beforeExp}" var="item">
										<tr>
											<td style="">
												<c:out value="${item.beforeStartTime}"></c:out>
											</td>
											<td style="">
												<c:out value="${item.beforeEndTime}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.beforeEndTime}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.beforeDept}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.beforePosition}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.beforePosition}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.beforeLeavingReasons}"></c:out>
											</td>
										</tr>
									</c:forEach>
								</table>
								<span class="head-title">公司内工作经历</span>
								<table style="background-color: white; table-layout: fixed; width: 100%" class="table  table-bordered table-condensed table-hover" id="f_dataTable">
									<tr>
										<td style="width: 12%">开始日期</td>
										<td style="width: 12%">结束日期</td>
										<td style="width: 12%">部门</td>
										<td style="width: 15%">岗位</td>
										<td style="width: 15%">从业经历</td>
										<td>转岗原因</td>
									</tr>
									<c:forEach items="${nowExp}" var="item">
										<tr>
											<td style="">
												<c:out value="${item.nowStartTime}"></c:out>
											</td>
											<td style="">
												<c:out value="${item.nowEndTime}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.nowDept}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.nowPost}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.nowPerience}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.nowAlterReasons}"></c:out>
											</td>
										</tr>
									</c:forEach>
								</table>
								<span class="head-title">公司内奖惩情况</span>
								<table style="background-color: white; table-layout: fixed; width: 100%" class="table  table-bordered table-condensed table-hover" id="s_dataTable">
									<tr>
										<td style="width: 12%">日期</td>
										<td style="width: 12%">奖惩类型</td>
										<td style="width: 12%">奖惩原因</td>
										<td>奖惩内容</td>
									</tr>
									<c:forEach items="${award}" var="item">
										<tr>
											<td style="word-wrap: break-word;">
												<c:out value="${item.awardDate}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.awardType}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.awardReason}"></c:out>
											</td>
											<td style="word-wrap: break-word;">
												<c:out value="${item.awardContent}"></c:out>
											</td>
										</tr>
									</c:forEach>
								</table>
								<table class="table table-bordered table-condensed">
									<tr>
										<td style="width: 12%;">离职时间</td>
										<td style="width: 12%;">${resume.departureTime}</td>
										<td style="width: 12%;">离职原因</td>
										<td style="word-wrap: break-word;">${resume.leavingReasons}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>

</body>
</html>
