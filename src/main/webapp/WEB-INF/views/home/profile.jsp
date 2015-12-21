<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ch">
<head>
<%@ include file="../common/meta.jsp"%>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script src="${pageContext.request.contextPath}/js/ims/ims.user.js"></script>
<script src="${pageContext.request.contextPath}/js/ims/ims.user.resume.js"></script>

<script type="text/javascript">
$(document).ready(function () { 
		
});
</script>
<style type="text/css">
#header, .top_title, #jqprint, #footer, #cssprint h3 {
	display: none !important
}
.head-title {
	font-size: 17.5px;
	font-weight: normal;
	line-height: normal;
	margin-top: 0px;
	text-align: center;
	margin: 10px 0;
	display: block;
	color: inherit;
	margin: 10px 0;
	font-family: inherit;
}
.header_img {
	width: 120px;
	height: 150px;
	border: 1px solid #CDCDCD;
}
.table th, .table td{
  	border-top: 1px solid #90c8f5 !important;
  	vertical-align: middle !important;
}
#b_dataTable td{
	font-size: 13px;
	border-color:#90c8f5;
	line-height:12px;
	vertical-align: middle;
	width:15%;
}
#b_dataTable th {
	hight: 20px;
	text-align: right;
	color: black;
	background-color: #f1f6ff;
	vertical-align: middle;
	width:120px;
	border-color:#90c8f5;
}
.datatable td{
	font-size: 13px;
	border-color:#90c8f5;
	line-height:12px;
	vertical-align: middle;
}
.datatable th{
	color: black;
	background-color: #f1f6ff;
	vertical-align: middle;
	border-color:#90c8f5;
}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		<input type="hidden" id="hf_id" value="${user.id }" />
		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
						<div class="primary-head">
						<%@include file="../breadcrumb.jsp"%>
					</div>
				</div>
				<div class="row-fluid">
			        <div class="span10">
			        	<div class="profile-info">
							<div class="tab-widget">
								<div id="div_print" class="print-div">
									<table class="table table-bordered table-condensed table-hover" id="b_dataTable">
										<tr style="background:#6BC2F8;color:#15428b;background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="7"><span style="font-size:16px;padding:10px 10px 10px 10px;">基础资料</span></td>
											<td style="float:right; width: 100px; border-left: none;">
												<button onclick="$.ims.user.showpwdUpdateModal()"> <i class="icon-ok"></i>密码修改</button> 
											</td>
										</tr>
										<tr>
											<th>姓名：</th>
											<td style="">${resume.userName}</td>
											<th>性别：</th>
											<td style="">${resume.sex}</td>
											<th>工号：</th>
											<td colspan="2">${resume.empnumber}</td>
											<th rowspan="6" style="vertical-align:middle; text-align:center;">
												<c:choose>
													<c:when test='${resume.picture == null || resume.picture == ""}'>
														<img class="header_img"  src="${pageContext.request.contextPath}/resources/index/bootstrap-3.3.2/img/user.jpg" />
													</c:when>
													<c:otherwise>
														<img class="header_img"  src="${pageContext.request.contextPath}/upload/photo/${resume.picture}" >
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<th>籍贯：</th>
											<td>${resume.place}</td>
											<th>民族：</th>
											<td>${resume.nation}</td>
											<th>身份证号码：</th>
											<td colspan="2" style="word-wrap: break-word;">${resume.idNumber}</td>
										</tr>
										<tr>
											<th>政治面貌：</th>
											<td>${resume.politicsStatus}</td>
											<th>婚否：</th>
											<td>${resume.marrige}</td>
											<th>户口所在地：</th>
											<td colspan="2" style="word-wrap: break-word;">${resume.domicilePlace}</td>
										</tr>
										<tr>
											<th>手机：</th>
											<td style="word-wrap: break-word;">${resume.mobile}</td>
											<th>服务年限：</th>
											<td>${resume.workYear}年</td>
											<th>身份证地址：</th>
											<td colspan="2" style="word-wrap: break-word;">${resume.idAddress}</td>
										</tr>
										<tr>
											<th>最高学历：</th>
											<td>${resume.education}</td>
											<th>最高学位：</th>
											<td>${resume.degree}</td>
											<th>生日：</th>
											<td colspan="2">${resume.birthday}</td>
										</tr>
										<tr>
											<th>现在住址：</th>
											<td style="word-wrap: break-word;">${resume.address}</td>
											<th>学校：</th>
											<td style="word-wrap: break-word;">${resume.school}</td>
											<th>专业：</th>
											<td style="word-wrap: break-word;">${resume.major}</td>
											<th></th>
										</tr>
										<tr>
											<th>毕业时间：</th>
											<td>${resume.graduateTime}</td>
											<th>入职时间：</th>
											<td>${resume.joinTime}</td>
											<th>转正时间：</th>
											<td>${resume.conversionTime}</td>
											<th>职称：</th>
											<td>${resume.title}</td>
										</tr>
										<tr>
											<th>现任职部门：</th>
											<td>${resume.dept}</td>
											<th>现岗位：</th>
											<td>${resume.post}</td>
											<th>职位：</th>
											<td>${resume.position}</td>
											<th>职等：</th>
											<td>${resume.grade}</td>
										</tr>
										<tr>
											<th>合同开始：</th>
											<td>${resume.contractStartDate}</td>
											<th>合同结束：</th>
											<td>${resume.contractEndDate}</td>
											<th>社保选择：</th>
											<td>${resume.socialMoney}</td>
											<th>公积金选择：</th>
											<td>${resume.publicMoney}</td>
										</tr>
										<tr>
											<th>外语水平：</th>
											<td colspan="3">${resume.language}</td>
											<th>电子邮件：</th>
											<td colspan="3" style="word-wrap: break-word;">${resume.email}</td>
										</tr>
										<tr>
											<th>兴趣爱好：</th>
											<td colspan="7">${resume.hobbies}</td>
										</tr>
										<tr>
											<th>技能擅长：</th>
											<td colspan="7">${resume.skill}</td>
										</tr>
									</table>
									<table class="table table-bordered table-condensed table-hover" id="a_dataTable">
										<tr style="height: 35px;background:#f1f6ff;">
											<th colspan="6"><span style="font-size:15px;padding:10px 10px 10px 10px;">家庭情况</span></th>
										</tr>
										<tr>
											<th style="width: 12%">姓名</th>
											<th style="width: 12%">年龄</th>
											<th style="width: 12%">称谓</th>
											<th style="width: 15%">工作单位职务</th>
											<th>联系电话</td>
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
									<table class="table table-bordered table-condensed table-hover" id="c_dataTable">
										<tr style="height: 35px;background:#f1f6ff;">
											<th colspan="6"><span style="font-size:15px;padding:10px 10px 10px 10px;">教育及重要培训经历</span></th>
										</tr>
										<tr>
											<th style="width: 12%">开始日期</th>
											<th style="width: 12%">结束日期</th>
											<th style="width: 12%">学校（机构）</th>
											<th style="width: 15%">专业（项目名称）</th>
											<th style="width: 15%">学历（证书）</th>
											<th style="">学习方式</th>
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
									<table class="table  table-bordered table-condensed table-hover" id="d_dataTable">
										<tr style="height: 35px;background:#f1f6ff;">
											<th colspan="7"><span style="font-size:15px;padding:10px 10px 10px 10px;">公司外工作经历</span></th>
										</tr>
										<tr>
											<th style="width: 12%">开始日期</th>
											<th style="width: 12%">结束日期</th>
											<th style="width: 12%">公司名称</th>
											<th style="width: 15%">部门</th>
											<th style="width: 15%">职务</th>
											<th style="width: 15%">从业经历</th>
											<th style="">离职原因</th>
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
									<table class="table  table-bordered table-condensed table-hover" id="f_dataTable">
										<tr style="height: 35px;background:#f1f6ff;">
											<th colspan="6"><span style="font-size:15px;padding:10px 10px 10px 10px;">公司内工作经历</span></th>
										</tr>
										<tr>
											<th style="width: 12%">开始日期</th>
											<th style="width: 12%">结束日期</th>
											<th style="width: 12%">部门</th>
											<th style="width: 15%">岗位</th>
											<th style="width: 15%">从业经历</th>
											<th>转岗原因</th>
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
									<table class="table  table-bordered table-condensed table-hover" id="s_dataTable">
										<tr style="height: 35px;background:#f1f6ff;">
											<th colspan="4"><span style="font-size:15px;padding:10px 10px 10px 10px;">公司内奖惩情况</span></th>
										</tr>
										<tr>
											<th style="width: 12%">日期</th>
											<th style="width: 12%">奖惩类型</th>
											<th style="width: 12%">奖惩原因</th>
											<th>奖惩内容</th>
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
									
								</div>
							</div>
						</div>
			        </div>

				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
	
	<!-- 修改密码弹出框 -->
	<div class="modal hide fade" id="password_edit_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="password_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form action="" id = "pwd_form">
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span>旧密码</label>
							<div class="controls">
								<input type="password" id="pwd_old" name="pwd_old" class="span7" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span>新密码</label>
							<div class="controls">
								<input id='pwd' name='pwd' type="password" class="span7" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="text-error">*</span>新密码确认</label>
							<div class="controls">
								<input id='pwd_comfirm' name='pwd_comfirm' type="password" class="span7" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" id="btnSave" onclick="$.ims.user.updatepwd()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
		</div>
	</div>
</body>
</html>