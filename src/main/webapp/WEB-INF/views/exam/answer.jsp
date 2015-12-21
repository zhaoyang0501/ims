<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/exam.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.exam.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){		
		
	});
</script>
<style type="text/css">
.main-wrapper{
	margin-left: 0px;
	margin-bottom: 0px;
}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		
		<input type="hidden" id="hf_id" value="${paperId }"/>
		<input type="hidden" id="hf_qIds" value="${qIds }" />
		<input type="hidden" id="hf_planCourseId" value="${planCourseId }" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="ManageList">
								<div id="ExamName">
				                    ${subject }在线考试
							        <img src="images/help.gif" title="查看【考试帮助】" id="viewHelp" alt="">
							        <a onclick="" style="font-size: 12pt">【开始答题】</a>
							    </div>
								<c:forEach var="item" items="${list }">
									<c:if test="${item.type == 1 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>： ${item.subject } 【题型：单选题】
													</div>
													${item.optionContents }
												</div>
												<div class="Answer inline">
													选择答案：
													<c:forEach var="option" items="${item.qoptions }">
														<label class="radio inline">
														<input type="radio" value="${option }" name="${item.qId }" class="answer_radio"/>
														${option } </label>
													</c:forEach>
												</div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 2 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>： ${item.subject } 【题型：多选题】
													</div>
													${item.optionContents }
												</div>
												<div class="Answer inline">
		                                       		 选择答案：
		                                       		 <c:forEach var="option" items="${item.qoptions }">
														<label class="radio inline">
														<input type="checkBox" value="${option }" name="${item.qId }"  class="answer_check"/>
														${option } </label>
													</c:forEach>
			                                    </div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 3 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>： ${item.subject } 【题型：判断题】
													</div>
												</div>
												<div class="Answer">
		                                       		选择答案：
		                                       		<label class="radio inline">
													<input type="radio" value="A" name="${item.qId }"/>
													正确 </label>
													<label class="radio inline">
													<input type="radio" value="B" name="${item.qId }"/>
													错误 </label>
			                                    </div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 4 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>： ${item.subject } 【题型：主观题】
													</div>
												</div>
												<div class="Answer inline">
		                                       		 填写答案：<textarea rows="2" id="${item.qId }" class="span12 answer_essay"></textarea>
			                                    </div>
											</ul>
										</div>
									</c:if>
								</c:forEach>
								<div class="QType" style="margin-left:50px; margin-bottom: 50px;">
									<a onclick="$.ims.exam.checkExamAnswer();" class="btn btn-warning">检查答题</a>
									<a onclick="$.ims.exam.submitExamAnswer('bt');" class="btn btn-info" id="submitbtn">提交试卷</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 答题检查modal -->
	<div class="modal hide fade" id="answer_check_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="answer_check_modal_header_label"></label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<table id="dt_answercheck" class="responsive table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>未答题题号</th>
							</tr>
						</thead>
						<tbody>
							<tr><td id="t_answercheck">2、14</td></tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>