<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.exam.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript">

	$(document).ready(function(){		
				
	});
	
</script>
<style type="text/css">
/*满屏列表列表框*/
.ManageList{ float:left; width:99.6%; min-width: 824px;clear:both; height:auto; overflow:hidden;}
.ManageList .listTitle{ float:left; width:99%; clear:both; height:auto; background-color:#f1f1f1; height:32px; line-height:32px ; }
.ManageList .listTitle li span{  width: 45px; text-align: center; margin-right:10px; display:block; float:left;}
.ManageList .title li span{ width: 45px; text-align: center; margin-right:10px; display:block; float:left;}
.ManageList .title{ float:left; width:99%; clear:both; height:auto; background-color:#f1f1f1; height:32px; line-height:32px ; margin-bottom:15px;}
.ManageList input[type="checkbox"]{margin-top:0px;}
.ManageList .title input[type="checkbox"]{margin-top:5px;}
.ManageList ul{ float:left;width:99%; clear:both; padding:0px 0px; margin-bottom:5px; padding-left:10px;}
.ManageList ul li{ float:left; text-align:left; width:100px; word-break:break-all; padding-left:0px; height:30px; line-height:30px;  padding:0px 5px 0px 5px;}
.ManageList ul:link{ background:#FBF4E5}
.ManageList ul:visited{ background:#FBF4E5}
.ManageList ul:hover{ background:#eff3f4}
.ManageList ul img{ padding-top:6px;}

.QType{WORD-WRAP: break-word;float:left;width:710px!important;height:auto;margin:0px 10px; background-color:#ffffff;padding:0px 15px; text-align:left;}
.QType .TypeTitle{float:left;width:100%;height:auto;line-height:34px;color:#417ac1;border:1px #e0e0e0 solid;background-color:#f4fbf8;font-weight:bold;}
.QType .Body{float:left;width:680px!important;border:1px #c9c9c9 dashed;height:auto;line-height:24px;padding:10px 15px;margin:5px 0px;color:#000000;z-index:1000}
.QType .BodyMark{float:left;width:680px!important;border:1px #ca0100 dashed;height:auto;line-height:24px;padding:10px 15px;margin:5px 0px;color:#000000;z-index:1000;background-color:#fcebdc;}

.QType  span{color:#ff0000;font-weight:bold;cursor:pointer;}
.QType .Body .QName{float:left;width:680px!important;border-bottom:1px #c9c9c9 dashed;height:auto;line-height:30px;z-index:999;}
.QType .Body .QName span{color:#ff0000;font-weight:bold;z-index:10000;}
.QType .Answer{float:left;width:680px!important;height:auto;line-height:30px;font-weight:bold;font-family:黑体;color:#000000; font-size:12pt;border:1px #dddddd solid;background-color:#f4f4f4;padding:0px 15px;margin-bottom:5px;}

#ExamName { float:left;width:740px;margin:0px 10px;height:60px;line-height:60px;font-family:黑体;font-weight:bold;font-size:20pt;color:#ff0000;text-align:center;background-color:#ffffff;
}
.main-wrapper{
	margin-left: 0px;
	margin-bottom: 0px;
}
#ExamName {
  float: left;
  width: 940px;
  margin: 0px 10px;
  height: 60px;
  line-height: 60px;
  font-family: 黑体;
  font-weight: bold;
  font-size: 20pt;
  color: #ff0000;
  text-align: center;
  background-color: #ffffff;
}
</style>
</head>
<body>
	<div class="layout">
		
		<input type="hidden" id="hf_id" value="${answerId }"/>

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
														<span>第 ${item.qnum }题</span>：
														${item.subject }
														【题型：单选题】
													</div>
													${item.optionContents }
												</div>
												<div class="Answer inline">
													选择答案： <label class="radio inline"> ${item.ownAnswer } </label>
												</div>
												<div class="Answer" style="color: Red">
		                                       		 答案：${item.answers }
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
												</div>
												<div class="Answer inline">
													选择答案： <label class="radio inline"> ${item.ownAnswer } </label>
												</div>
												<div class="Answer" style="color: Red">
		                                       		 答案：${item.answers }
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
												</div>
												<div class="Answer inline">
													选择答案： <label class="radio inline"> ${item.ownAnswer } </label>
												</div>
												<div class="Answer" style="color: Red">
		                                       		 答案：${item.answers }
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
												</div>
												<div class="Answer inline">
		                                       		 填写答案：<label class="radio inline"> ${item.ownAnswer } </label>
			                                    </div>
			                                    <div class="Answer" style="color: Red">
		                                       		 答案：${item.answers }
			                                    </div>
											</ul>
										</div>
									</c:if>
								</c:forEach>
								<div class="QType" style="margin-left:45px; margin-bottom: 50px; border: 0px; ">
									<div class="Answer inline">
                                    	考试得分：<input id="score" style="width: 100px;" ></input>
										<a onclick="$.ims.exam.gooverexam();" class="btn btn-info" id="submitbtn">试卷批阅</a>
                                    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>