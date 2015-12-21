<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/exam.css" rel="stylesheet">
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
		
	<input type="hidden" id="hf_id" />

	<div class="container-fluid">
			<div class="row-fluid ">
				<div class="span12">
					<div class="content-widgets">
						<div class="widget-container">
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
														<span>第 ${item.qnum }题</span>：
														${item.subject }
														【题型：单选题】
													</div>
													${item.optionContents }
													<div class="Answer" style="color: Red">
			                                       		 答案：${item.answers }
				                                    </div>
				                                    <div class="Answer">
		                                        		  解析：${item.analysis }
				                                    </div>
												</div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 2 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>：
														${item.subject }
														【题型：多选题】
													</div>
													${item.optionContents }
													<div class="Answer" style="color: Red">
			                                       		 答案：${item.answers }
				                                    </div>
				                                    <div class="Answer">
		                                        		  解析：${item.analysis }
				                                    </div>
												</div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 3 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>：
														${item.subject }
														【题型：判断题】
													</div>
													<div class="Answer" style="color: Red">
			                                       		 答案：${item.answers == 'A' ? '正确':'错误' }
				                                    </div>
				                                    <div class="Answer">
		                                        		  解析：${item.analysis }
				                                    </div>
												</div>
											</ul>
										</div>
									</c:if>
									<c:if test="${item.type == 4 }">
										<div class="QType">
											<ul>
												<div class="Body">
													<div class="QName">
														<span>第 ${item.qnum }题</span>：
														${item.subject }
														【题型：主观题】
													</div>
													<div class="Answer" style="color: Red">
			                                       		 答案：${item.answers }
				                                    </div>
				                                    <div class="Answer">
		                                        		  解析：${item.analysis }
				                                    </div>
												</div>
											</ul>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>