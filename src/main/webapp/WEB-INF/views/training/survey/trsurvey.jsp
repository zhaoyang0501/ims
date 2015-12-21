<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../../common/meta.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.training.teacher.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script>
<%
	boolean hasCreate = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "C");	//创建
	boolean hasUpdate = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "U");	//更新
	boolean hasDelete = RightUtil.hasRight("ROLE_TRAINING_TEACHER", "D");	//删除
%>
<script type="text/javascript">

	$(document).ready(function(){
	});
</script>
<style type="text/css">
	.main .main-content {
	  background-color: #fff;
	  padding: 36px 50px 0 50px;
	  border: 1px solid #e3e3e3;
	  border-top: 0px;
	  position: relative;
	}
	.center {
	  width: 950px;
	  margin: 0 auto;
	  clear: both;
	}
	.tab-content { overflow: hidden;} 
	.chzn-container .chzn-results{
		max-height:150px;
	}
	.content-head .handler {
	  padding-bottom: 24px;
	  border-bottom: 1px solid #e3e3e3;
	}
	.clearfix {
	  zoom: 1;
	}
	.content-head .handler .source, .content-head .handler .joiner, .content-head .handler .status, .content-head .handler .view-result, .content-head .handler .share {
	  float: left;
	  display: inline;
	  padding-right: 20px;
	}
	.inline{
		  display: inline;
	}
</style>
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
								<h3>培训调查问卷</h3>
							</div>
							<div class="widget">
								<div class="main left">
									<div class="main-content">
										<div class="content-head">
											<h3><img alt="" src="images/title.png">培训效果调查表</h3>
										</div>
										<div class="handler clearfix" style="font-size: 12px;">
		                   		 			<div class="source inline">课程名称：<span class="text-warning">技术管理部</span></div>
		                   		 			&nbsp;&nbsp;|&nbsp;&nbsp;
		                    				<div class="joiner inline">讲师：<span class="text-warning">管理员</span></div>
		                    				&nbsp;&nbsp;|&nbsp;&nbsp;
		                   					<div class="status inline">培训时间：<span class="text-warning">2012-09-12</span></div>
		                                </div>
		                                <p style="margin-top:20px;">
		                                	本表是想了解您在培训后对此课程的看法，作为人力资源部未来规划或安排确切针对学员需求的课程之参考。请您认真填写本表，提供您宝贵意见，以便人力资源部改进。谢谢您的支持与合作。
		                                </p>
		                                <hr />
		                                <form id="survey1">
		                                	<div>
		                                		<dt>1、	您认为此次培训中讲师的专业知识如何？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_1" >很好</label>
		                                		<label class="radio inline"><input type="radio" name="q_1" >较好</label>
		                                		<label class="radio inline"><input type="radio" name="q_1" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_1" >较差</label>
		                                		<label class="radio inline"><input type="radio" name="q_1" >极差</label>
		                                		<dt style="margin-top:12px;">2、	您认为此次培训中讲师的授课技巧及方式方法如何？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_2" >很好</label>
		                                		<label class="radio inline"><input type="radio" name="q_2" >较好</label>
		                                		<label class="radio inline"><input type="radio" name="q_2" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_2" >较差</label>
		                                		<label class="radio inline"><input type="radio" name="q_2" >极差</label>
		                                		<dt style="margin-top:12px;">3、	您认为讲师在此次培训中是否能有效地引导整体的教学进度及现场气氛？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_3" >非常有效</label>
		                                		<label class="radio inline"><input type="radio" name="q_3" >较好</label>
		                                		<label class="radio inline"><input type="radio" name="q_3" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_3" >较差</label>
		                                		<label class="radio inline"><input type="radio" name="q_3" >极差</label>
		                                		<dt style="margin-top:12px;">4、	您认为此次培训的课程内容在结构设计及详述程度方面是否合理？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_4" >很好</label>
		                                		<label class="radio inline"><input type="radio" name="q_4" >较好</label>
		                                		<label class="radio inline"><input type="radio" name="q_4" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_4" >较差</label>
		                                		<label class="radio inline"><input type="radio" name="q_4" >极差</label>
		                                		<dt style="margin-top:12px;">5、	您对讲师在此次培训的练习指引与问题解答方面是否满意？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_5" >非常满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_5" >满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_5" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_5" >不满意 </label>
		                                		<label class="radio inline"><input type="radio" name="q_5" >非常不满意</label>
		                                		<dt style="margin-top:12px;">6、	您认为此次培训的课程内容与您的需要及兴趣相符程度如何？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_6" >密切相关 </label>
		                                		<label class="radio inline"><input type="radio" name="q_6" >相关</label>
		                                		<label class="radio inline"><input type="radio" name="q_6" >有一点用</label>
		                                		<label class="radio inline"><input type="radio" name="q_6" >一般 </label>
		                                		<label class="radio inline"><input type="radio" name="q_6" >无用</label>
		                                		<dt style="margin-top:12px;">7、	您对此次培训的时间安排是否满意？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_7" >非常满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_7" >满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_7" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_7" >不满意 </label>
		                                		<label class="radio inline"><input type="radio" name="q_7" >非常不满意</label>
		                                		<dt style="margin-top:12px;">8、	您对此次培训的会务支援是否满意？<span class="text-error">（必选 ）</span></dt>
		                                		<label class="radio inline" style="margin-left:20px;"><input type="radio" name="q_8" >非常满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_8" >满意</label>
		                                		<label class="radio inline"><input type="radio" name="q_8" >一般</label>
		                                		<label class="radio inline"><input type="radio" name="q_8" >不满意 </label>
		                                		<label class="radio inline"><input type="radio" name="q_8" >非常不满意</label>
		                                	</div>
		                                </form>
		                                <a class="btn btn-warning" style="margin-bottom: 20px;">&nbsp;&nbsp;提&nbsp;交&nbsp;&nbsp;</a>
		                                <a class="btn" style="margin-bottom: 20px;">&nbsp;查看结果&nbsp;</a>
									</div>
								</div>
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