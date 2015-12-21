<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.user.resume.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/file-upload-preview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<link href="${pageContext.request.contextPath}/resources/jquery-ui/jquery-ui.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".selectmenu").selectmenu();

						$(".chosen").chosen({
							no_results_text : " ",
							placeholder_text : " ",
							disable_search_threshold : 5,
							allow_single_deselect : true,
							disable_search_threshold : true
						});

						$('.date').datetimepicker({
							language : 'zh-CN',
							weekStart : 1,
							todayBtn : 1,
							format : 'yyyy-mm-dd',
							autoclose : 1,
							todayHighlight : 1,
							startView : 2,
							minView : 2,
							forceParse : 0
						});

						$("#socialmoney").change(function() {
							var value = $("#socialmoney").val();
							$("#socialmoney_defined").val(value);
							$("#socialmoney_defined").attr("readonly", "readonly");
							if (value == "3") {
								$("#socialmoney_defined").val("");
								$("#socialmoney_defined").removeAttr("readonly");
							}
						});

						$("#publicmoney").change(function() {
							var value = $("#publicmoney").val();
							$("#publicmoney_defined").val(value);
							$("#publicmoney_defined").attr("readonly", "readonly");
							if (value == "3") {
								$("#publicmoney_defined").val("");
								$("#publicmoney_defined").removeAttr("readonly");
							}
						});

						var familyArray = new Array();
						<c:forEach items="${family}" var="bean">
						familyArray.push("${bean.id},${bean.familyName},${bean.familyAge},${bean.familyTitle},${bean.familyMobile},${bean.familyCompany}");
						</c:forEach>

						var eduArray = new Array();
						<c:forEach items="${education}" var="bean">
						eduArray.push("${bean.id},${bean.eduStartTime},${bean.eduEndTime},${bean.eduSchool},${bean.eduMajor},${bean.eduCertificate},${bean.eduWay}");
						</c:forEach>

						var outCompanyArray = new Array();
						<c:forEach items="${beforeExp}" var="bean">
						outCompanyArray
								.push("${bean.id},${bean.beforeStartTime},${bean.beforeEndTime},${bean.beforeCompany},${bean.beforeDept},${bean.beforePosition},${bean.beforePerience},${bean.beforeLeavingReasons}");
						</c:forEach>

						var inCompanyArray = new Array();
						<c:forEach items="${nowExp}" var="bean">
						inCompanyArray.push("${bean.id},${bean.nowStartTime},${bean.nowEndTime},${bean.nowDept},${bean.nowPost},${bean.nowPerience},${bean.nowAlterReasons}");
						</c:forEach>

						var rewardArray = new Array();
						<c:forEach items="${award}" var="bean">
						rewardArray.push("${bean.id},${bean.awardDate},${bean.awardType},${bean.awardReason},${bean.awardContent}");
						</c:forEach>

						$.ims.userresume.familyInfo(familyArray);
						$.ims.userresume.eduInfo(eduArray);
						$.ims.userresume.outCompanyInfo(outCompanyArray);
						$.ims.userresume.inCompanyInfo(inCompanyArray);
						$.ims.userresume.rewardInfo(rewardArray);

						$("#up").change(function() {
							var value = $("#up").val();
							if (value != "") {
								$("#sb").removeAttr("disabled");
							}
							$("#filename").val($(this).val());
						});

						$("#up").uploadPreview({
							Img : "ImgPr",
							Width : 120,
							Height : 120
						});

						if ("${tip}" != null && "${tip}" != "") {
							// 			noty({"text":"${tip}","layout":"top","type":"success","timeout":"5000"});
							alert("${tip}");
						}

					});
</script>
<style type="text/css">
input[type="text"] {
	margin-bottom: 0px;
	padding-top: 0px;
}

.valid-error {
	border: 1px solid red !important;
}
/** 文件上传 **/
.file-box {
	position: relative;
}

.txt {
	height: 22px !important;
	border: 1px solid #cdcdcd;
	width: 110px;
}

.brown1 {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
	height: 24px;
	width: 70px;
}

.file {
	position: absolute;
	top: 0;
	right: 80px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 260px
}

.subhead {
	text-align: center;
	margin-bottom: -20px;
}

.bill-top-title-green {
	background:
		url(${pageContext.request.contextPath}/images/bill-top-blue.png)
		repeat-x;
	height: 40px;
	border: none;
	line-height: 40px;
}

.table-condensed th, .table-condensed td {
	padding: 2px 2px;
}

.table th, .table td {
/* 	border: 1px solid #90c8f5 !important; */
	vertical-align: middle !important;
}

.
#baseinfo_DataTable td {
	font-size: 13px;
	line-height: 12px;
	vertical-align: middle;
	width: 15%;
}

#baseinfo_DataTable th {
	text-align: right;
	color: black;
	font-size: 14px !important;
	background-color: #f1f6ff;
	vertical-align: middle;
}

.datatable td {
	border-color: #90c8f5;
	line-height: 12px;
	vertical-align: middle;
}

.datatable th {
	color: black;
	background-color: #f1f6ff;
	font-size: 14px !important;
	vertical-align: middle;
	border-color: #90c8f5;
}

.header_img {
	width: 115px;
	height: 150px;
	border: 1px solid #CDCDCD;
}

.text {
	height: 21px !important;
	width: 115px;
	padding-top: 0px;
}

.longtext {
	height: 21px !important;
	width: 97%;
}

.datepicker {
	height: 14px !important;
	width: 100px;
}
</style>
</head>
<body style="font-size: 9px;">
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>

		<input type="hidden" id="re_id" value="${resume.id }" /> <input type="hidden" id="us_id" value="${resume.userId }" />


		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue">
								<h3>编辑档案</h3>
							</div>
							<div id="resumeedit" class="widget-container white">
								<table id="baseinfo_DataTable" class=" table table-bordered table-condensed">
									<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
										<td colspan="7"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">基本信息</span></td>
										<td style="float: right; border-left: none;"></td>
									</tr>
									<tr>
										<th width="10%">姓名：</th>
										<td width="15%"><input id="username" readonly="readonly" value="${resume.userName}" class="text" style="border: 1px solid #C0BBB4; background: #E0E0E0;"></td>
										<th width="10%">工号：</th>
										<td width="15%"><input id="empnumber" readonly="readonly" value="${resume.empnumber}" class="text" style="border: 1px solid #C0BBB4; background: #E0E0E0;"></td>
										<th width="10%">性别：</th>
										<td width="15%"><select id="sex" data-placeholder="请选择性别" class="selectmenu" style="width: 120px;">
												<option value="1" <c:if test="${resume.sexId == 1}">selected="selected"</c:if>>男</option>
												<option value="0" <c:if test="${resume.sexId == 0}">selected="selected"</c:if>>女</option>
										</select></td>
										<th width="10%"></th>
										<td rowspan="5" style="text-align: center; width: 15%;"><c:choose>
												<c:when test='${resume.picture == null || resume.picture == ""}'>
													<img id="ImgPr" class="header_img" src="${pageContext.request.contextPath}/resources/index/bootstrap-3.3.2/img/user.jpg" />
												</c:when>
												<c:otherwise>
													<img id="ImgPr" class="header_img" src="${pageContext.request.contextPath}/upload/photo/${resume.picture}" />
												</c:otherwise>
											</c:choose></td>
									</tr>
									<tr>
										<th>籍贯：</th>
										<td><input id="place" maxlength="12" value="${resume.place}" class="longtext"></td>
										<th>民族：</th>
										<td><input id="nation" maxlength="12" value="${resume.nation}" class="text"></td>
										<th>婚否：</th>
										<td><select id="marrige" data-placeholder="请选择婚否" class="selectmenu" style="width: 120px;">
												<option value="1" <c:if test="${resume.marrigeId == 1}">selected="selected"</c:if>>已婚</option>
												<option value="0" <c:if test="${resume.marrigeId == 0}">selected="selected"</c:if>>未婚</option>
										</select></td>
										<th></th>
									</tr>
									<tr>
										<th>政治面貌：</th>
										<td><select id="politicsstatus" data-placeholder="请选择政治面貌" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${politics}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.politicsStatus}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>户口所在地：</th>
										<td><input id="domicileplace" maxlength="32" value="${resume.domicilePlace}" class="longtext"></td>
										<th>身份证号码：</th>
										<td><input id="idnumber" maxlength="18" class="isIDCard2 longtext" value="${resume.idNumber}"></td>
										<th></th>
									</tr>
									<tr>
										<th>手机：</th>
										<td><input id="mobile" maxlength="11" class="nbmobile text" value="${resume.mobile}"></td>
										<th>电子邮件：</th>
										<td><input id="email" maxlength="30" class="email longtext" value="${resume.email}"></td>
										<th>身份证地址：</th>
										<td><input id="idaddress" maxlength="32" class="longtext" value="${resume.idAddress}"></td>
										<th></th>
									</tr>
									<tr>
										<th>最高学历：</th>
										<td><select id="education" data-placeholder="请选择学历" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${edu}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.education}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>最高学位：</th>
										<td><select id="degree" data-placeholder="请选择借学位" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${degree}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.degree}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>生日：</th>
										<td><input id="birthday" class="date text required" value="${resume.birthday}" readonly="readonly"></td>
										<th></th>
									</tr>
									<tr>
										<th>毕业时间：</th>
										<td><input id="graduatetime" class="date text required" value="${resume.graduateTime}" readonly="readonly"></td>
										<th>毕业学校：</th>
										<td><input id="school" maxlength="32" value="${resume.school}" class="longtext"></td>
										<th>专业：</th>
										<td><input id="major" maxlength="32" value="${resume.major}" class="longtext"></td>
										<th>选择照片：</th>
										<td style="border-top: none; text-align: center;">
											<div class="file-box">
												<form method="post" action='<c:url value="/sysconfig/resume/upload/${resume.id }"></c:url>' target="hidden_frame" class="form-inline" enctype="multipart/form-data"
													style="margin-bottom: 0px;">
													<input id='filename' class='txt' /> <input type="file" name="file" class="file" id="up" size="35" /> <input id="sb" type="submit" disabled="disabled" name="submit"
														class="brown1" value="上传" />
												</form>
												<iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe>
											</div>
										</td>
									</tr>
									<tr>
										<th>现在住址：</th>
										<td><input id="address" maxlength="32" value="${resume.address}" class="longtext"></td>
										<th>入职时间：</th>
										<td><input id="jointime" class="date text required" value="${resume.joinTime}" readonly="readonly"></td>
										<th>转正时间：</th>
										<td><input id="conversiontime" class="date text required" value="${resume.conversionTime}" readonly="readonly"></td>
										<th>职称：</th>
										<td><select id="title" data-placeholder="请选择职称" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${title}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.title}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>现任职部门：</th>
										<td><select id="dept" data-placeholder="请选择部门" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${dept}" var="bean">
													<option value="${bean.id}" <c:if test="${bean.id ==resume.deptId}">selected="selected"</c:if>>${bean.name }</option>
												</c:forEach>
										</select></td>
										<th>现岗位：</th>
										<td><select id="post" data-placeholder="请选择岗位" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${post}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.post}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>职位：</th>
										<td><select id="position" data-placeholder="请选择职位" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${position}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.position}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>职等：</th>
										<td><select id="grade" data-placeholder="请选择职等" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${grade}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.grade}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>合同开始日期：</th>
										<td><input id="contractstartdate" class="date text required" value="${resume.contractStartDate}" readonly="readonly"></td>
										<th>合同结束日期：</th>
										<td><input id="contractenddate" class="date text required" value="${resume.contractEndDate}" readonly="readonly"></td>
										<th>社保选择：</th>
										<td><select id="socialmoney" data-placeholder="请选择社保" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${moneytype}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.socialSecurityType}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>公积金选择：</th>
										<td><select id="publicmoney" data-placeholder="请选择公积金" class="selectmenu required2" style="width: 120px;">
												<option value=""></option>
												<c:forEach items="${moneytype}" var="bean">
													<option value="${bean.key}" <c:if test="${bean.value ==resume.publicMoneyType}">selected="selected"</c:if>>${bean.value }</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>外语水平：</th>
										<td colspan="3"><select id="language" data-placeholder="请选择外语" multiple tabindex="6" class="chosen multiple required2 span12">
												<option value=""></option>
												<c:forEach items="${language}" var="bean">
													<option value="${bean.key}" <c:forEach items="${lg}" var="c">
															<c:if test="${bean.value ==c}">selected="selected"</c:if>
														</c:forEach>>
														${bean.value }</option>
												</c:forEach>
										</select></td>
										<th>社保基数：</th>
										<td><input id="socialmoney_defined" maxlength="10" class="nbfloat text" value="${resume.socialMoney}"></td>
										<th>公积金基数：</th>
										<td><input id="publicmoney_defined" maxlength="10" class="nbfloat text" value="${resume.publicMoney}"></td>
									</tr>
									<tr>
										<th>兴趣爱好：</th>
										<td colspan="7"><input id="hobbies" maxlength="100" value="${resume.hobbies}" class="text span12"></td>
									</tr>
									<tr>
										<th>技能擅长：</th>
										<td colspan="7"><input id="skill" maxlength="100" value="${resume.skill}" class="text span12"></td>
									</tr>
									<tr>
										<th class="light-blue">离职时间：</th>
										<td colspan="7"><input id="departureTime" value="${resume.departureTime}" readonly="readonly" style="width: 115px;">
									</tr>
									<tr>
										<th class="light-blue">离职原因：</th>
										<td colspan="7"><textarea id="leavingReasons" readonly="readonly" maxlength="300" rows="3" class="span12">${resume.leavingReasons}</textarea></td>
									</tr>
									<tr>
										<td colspan="8" style="text-align: center;">
											<button onclick="$.ims.userresume.createBaseInfo()">
												<i class="icon-ok"></i> 保存
											</button>
											<button onclick="$.ims.userresume.showDimissionModal()">
												<i class="icon-cut"></i> 离职
											</button>
											<button onclick="javascript:history.go(-1);">
												<i class="icon-undo"></i> 返回
											</button>
										</td>
									</tr>
								</table>

								<table id="family_dataTable" class="responsive table table-bordered table-condensed datatable">
									<thead>
										<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="5"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">家庭情况（父母、配偶、子女）</span></td>
											<td style="float: right;">
												<button onclick="$.ims.userresume.createFamily()">
													<i class="icon-ok"></i>保存
												</button>
											</td>
										</tr>
										<tr>
											<th width="30px;">
												<button onclick="$.ims.userresume.familyAddBtnClick()">＋</button>
											</th>
											<th width="12%">姓名</th>
											<th width="12%">年龄</th>
											<th width="12%">称谓</th>
											<th width="12%">联系电话</th>
											<th>工作单位及职务</th>
										</tr>
									</thead>
									<tbody id="family_dataTable_body">
									</tbody>
								</table>
								<table id="edu_dataTable" class="responsive table table-bordered table-condensed datatable">
									<thead>
										<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="6"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">教育及重要培训经历</span></td>
											<td style="float: right;">
												<button onclick="$.ims.userresume.createEdu()">
													<i class="icon-ok"></i>保存
												</button>
											</td>
										</tr>
										<tr>
											<th width="30px;">
												<button onclick="$.ims.userresume.eduAddBtnClick()">＋</button>
											</th>
											<th width="12%">开始日期</th>
											<th width="12%">结束日期</th>
											<th width="12%">学校（机构）名称</th>
											<th width="12%">专业（项目名称）</th>
											<th width="24%">学历（证书）</th>
											<th>学习方式</th>
										</tr>
									</thead>
									<tbody id="edu_dataTable_body">
									</tbody>
								</table>
								<table id="outCompany_dataTable" class="responsive table table-bordered table-condensed datatable">
									<thead>
										<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="7"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">公司外工作经历</span></td>
											<td style="float: right;">
												<button onclick="$.ims.userresume.createOutCompany()">
													<i class="icon-ok"></i>保存
												</button>
											</td>
										</tr>
										<tr class="light-gray">
											<th width="30px;">
												<button onclick="$.ims.userresume.outCompanyAddBtnClick()">＋</button>
											</th>
											<th width="12%">开始日期</th>
											<th width="12%">结束日期</th>
											<th width="12%">公司名称</th>
											<th width="12%">部门</th>
											<th width="12%">职务</th>
											<th width="12%">从业经历</th>
											<th>离职原因</th>
										</tr>
									</thead>
									<tbody id="outCompany_dataTable_body">
									</tbody>
								</table>
								<table id="inCompany_dataTable" class="responsive table table-bordered table-condensed datatable">
									<thead>
										<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="6"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">公司内工作经历</span></td>
											<td style="float: right;">
												<button onclick="$.ims.userresume.createInCompany()">
													<i class="icon-ok"></i>保存
												</button>
											</td>
										</tr>
										<tr>
											<th width="30px;">
												<button onclick="$.ims.userresume.inCompanyAddBtnClick()">＋</button>
											</th>
											<th width="12%">开始日期</th>
											<th width="12%">结束日期</th>
											<th width="12%">部门</th>
											<th width="12%">岗位</th>
											<th width="24%">从业经历</th>
											<th>转岗原因</th>
										</tr>
									</thead>
									<tbody id="inCompany_dataTable_body">
									</tbody>
								</table>
								<table id="reward_dataTable" class="responsive table table-bordered table-condensed datatable">
									<thead>
										<tr style="height: 30px; color: #15428b; background: url('images/dt-head-bg.png') left top repeat-x;">
											<td colspan="4"><span style="font-size: 17px;font-weight:bold; padding: 10px 10px 10px 10px;">奖惩情况</span></td>
											<td style="float: right;">
												<button onclick="$.ims.userresume.createReward()">
													<i class="icon-ok"></i>保存
												</button>
											</td>
										</tr>
										<tr class="light-gray">
											<th width="30px;">
												<button onclick="$.ims.userresume.rewardAddBtnClick()">＋</button>
											</th>
											<th width="12%">日期</th>
											<th width="12%">奖惩类型</th>
											<th width="24%">奖惩原因</th>
											<th>奖惩内容</th>
										</tr>
									</thead>
									<tbody id="reward_dataTable_body">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
	
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="dimission_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="dimission_modal_header_label"></label>
		</div>
		<div class="modal-body" >
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<form class="form-horizontal" method="post">
						<input type="hidden" id="hf_resume_id" value="${resume.id }" />	<!-- 档案ID -->
						<table class="responsive table table-bordered table-condensed">
							<tr>
								<th>&nbsp;离职人员：</td>
								<td colspan="3"><input value="${resume.userName}" style="width:118px; border: 1px solid #C0BBB4; background: #E0E0E0;" /> &nbsp;&nbsp; 
								<a>查看考勤状态</a></td>
							</tr>
							<tr>
								<th>&nbsp;离职类型：</th>
								<td colspan="3">
									<select id="dismission_type" data-placeholder="离职类型" class="chosen" style="width: 120px;height:50px;">
										<option value=""></option>
										<c:forEach items="${dimissionType}" var="bean">
											<option value="${bean.key}" <c:if test="${bean.key ==dimission.type}">selected="selected"</c:if>>${bean.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th width="20%">&nbsp;申请日期：</td>
								<td width="30%"><input id="dismission_applydate" value="${dimission.applydate }" class="date text" readonly="readonly"></td>
								<th width="20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预计日期：</th>
								<td width="30%"><input id="dismission_plandate" value="${dimission.plandate }" class="date text" readonly="readonly"></td>
							</tr>
							<tr>
								<th>&nbsp;实际日期：</td>
								<td><input id="dismission_actualdate" value="${dimission.actualdate }" class="date text" readonly="readonly"></td>
								<th>&nbsp;加入黑名单：</th>
								<td><input id="dismission_blacklist" checked="${dimission.blacklist == 1 ? true:false }" type="checkbox"></input></td>
							</tr>
							<tr>
								<th>&nbsp;离职原因：</td>
								<td colspan="3">
									<textarea id="dismission_reason" rows="" cols="" style="width:90%">${dimission.reason }</textarea>
								</td>
							</tr>
							<tr>
								<th>&nbsp;&nbsp;&nbsp;备注：</td>
								<td colspan="3">
									<textarea id="dismission_remark" rows="" cols="" style="width:90%">${dimission.remarks }</textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.ims.userresume.saveDismission()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>
