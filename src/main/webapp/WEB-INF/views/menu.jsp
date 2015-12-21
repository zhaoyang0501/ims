<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.hsae.ims.utils.RightUtil" %>
<%@page import="com.hsae.ims.entity.AuthenticatedUser"%>
<%@page import="com.hsae.ims.entity.Rights"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority" %>
<%@page import="org.springframework.security.core.context.SecurityContextHolder" %>
<% String userName = RightUtil.getCurrentChinesename(); %>
<%
	AuthenticatedUser c = (AuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	List<Rights> rights = c.getAllrights();
	
	/** 个人主页   **/
// 	boolean hasHome = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOME"));	//主页
// 	boolean hasHomeProfile = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOME_PROFILE"));	//个人资料.
// 	boolean hasHomeDailyreport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOME_DAILYREPORT"));	//我的日报
// 	boolean hasHomeAttence = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOME_ATTENCE"));	//我的考勤
// 	boolean hasHomeTraining = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOME_TRAINING"));	//我的培训
	
	/** 日报 周报   **/
	boolean hasDailyReport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DAILYREPORT"));	//日报
// 	boolean hasDailyReport_D_Write = RightUtil.hasRight("ROLE_DAILYREPORT", "P");	//日报填写
// 	boolean hasDailyReport_W_Submit = RightUtil.hasRight("ROLE_DAILYREPORT", "O");	//周报提交
// 	boolean hasDailyReport_D_Search = RightUtil.hasRight("ROLE_DAILYREPORT", "M");	//日报查询
// 	boolean hasDailyReport_W_Search = RightUtil.hasRight("ROLE_DAILYREPORT", "N");	//周报查询
	boolean hasDailyReport_W_SubmitState = RightUtil.hasRight("ROLE_DAILYREPORT", "S");	//周报提交统计
	boolean hasDailyReport_W_Export = RightUtil.hasRight("ROLE_DAILYREPORT", "E");	//周报导出
	
	/** 餐费报销   **/
	boolean hasReimburse = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REIMBURSE"));	//餐费报销
// 	boolean hasReimburseData = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REIMBURSE_DATA"));	//餐费报销管理
	boolean hasReimburseSummary = RightUtil.hasRight("ROLE_REIMBURSE", "S");	//餐费报销汇总
	boolean hasReimburseReport = RightUtil.hasRight("ROLE_REIMBURSE", "R");	//餐费统计报表
	
	/** 培训   **/
	boolean hasTraining = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING"));	//培训
	boolean hasTrainingBase = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_BASE"));	//培训库
	boolean hasTrainingPlan = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_PLAN"));	//培训计划
	boolean hasTrainingReport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_REPORT"));	//培训信息统计
	boolean hasTrainingDeptReport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_DEPT_REPORT"));	//各科室培训统计
	boolean hasTrainingCompleteReport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_COMPLETE_REPORT"));	//部门培训数量完成率
	boolean hasTrainingCompleteOntimeReport = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINING_COMPLETE_ONTIME_REPORT"));	//部门培训按期完成率
	
	/** 考勤   **/
	boolean hasAttence = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ATTENCE"));	//考勤
	
	boolean hasAttence_R = RightUtil.hasRight("ROLE_ATTENCE", "R");	//考勤信息管理 
	boolean hasAttence_E = RightUtil.hasRight("ROLE_ATTENCE", "E");	//加班工时核对
	boolean hasAttence_F = RightUtil.hasRight("ROLE_ATTENCE", "F");	//加班统计报表
	boolean hasAttence_G = RightUtil.hasRight("ROLE_ATTENCE", "G");	//请假统计报表
	boolean hasAttence_H = RightUtil.hasRight("ROLE_ATTENCE", "H");	//漏打卡统计报表
	boolean hasAttence_I = RightUtil.hasRight("ROLE_ATTENCE", "I");	//出差记录报表
	boolean hasAttence_J = RightUtil.hasRight("ROLE_ATTENCE", "J");	//调休时数统计报表
	boolean hasAttence_K = RightUtil.hasRight("ROLE_ATTENCE", "K");	//考勤统计报表
	boolean hasAttence_L = RightUtil.hasRight("ROLE_ATTENCE", "L");	//个人加班信息统计

	/** 项目   **/
	boolean hasProject = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROJECT"));	//项目
	boolean hasProject_C = RightUtil.hasRight("ROLE_PROJECT", "C");	//项目管理
	boolean hasProject_U = RightUtil.hasRight("ROLE_PROJECT", "U");	//项目WBS
	
	/** 会议管理 **/
	boolean hasMeetingManager = RightUtil.hasRight("ROLE_SYSCONFIG_MEETING", "M");	//会议室管理
	boolean hasReserveManager = RightUtil.hasRight("ROLE_SYSCONFIG_RESERVE", "Y");	//会议预约管理
	boolean hasHistoryManager = RightUtil.hasRight("ROLE_SYSCONFIG_HISTORY", "H");	//历史会议管理
	
	
	/** 图书管理 **/
	boolean hasBookManager = RightUtil.hasRight("ROLE_BOOK", "M");
	
	/** 缺陷   **/
	boolean hasBuglist = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BUGLIST"));	//缺陷
	
	/** 系统设置   **/
	boolean hasSysConfig = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG"));	//系统设置
	boolean hasSysConfigUser = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_USER"));	//用户管理
	boolean hasSysConfigDept = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_DEPT"));	//部门管理
	boolean hasSysConfigFunction = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_FUNCTION"));	//功能权限设置
	boolean hasSysConfigAnnualIndex = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_ANNUAL_INDEX"));	//年度指标设置
	boolean hasSysConfigAttenceDataSync = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_ATTENCE_DATASYNC"));	//考勤数据同步
	boolean hasSysConfigAttencedatareflesh = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_ATTENCEDATAREFLESH"));	//考勤数据刷新

	boolean hasSysConfigResume = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_RESUME"));	//个人档案
	boolean hasSysConfigHoliday = c.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SYSCONFIG_HOLIDAY"));	//节假日设置
%>

<div class="leftbar leftbar-close clearfix">
	<div class="admin-info clearfix">
		<div class="admin-thumb">
			<img alt="" class="img-thumbnail" src="${pageContext.request.contextPath}/resources/index/bootstrap-3.3.2/img/3.jpg">
		</div>
		<div class="admin-meta">
			<ul>
				<li class="admin-username" style="margin-top: 10px;">欢迎你, <%= userName %></li>
				<li><a href="${pageContext.request.contextPath}/logoutsuccess">
					<i class="icon-lock"></i> 退出</a>
<!-- 					<i class="icon-comments "></i> <a href="#"><span class="text-info">消息</span></a> -->
				</li>
			</ul>
		</div>
	</div>

	<div class="left-nav clearfix">
		<div class="left-primary-nav">
			<ul id="myTab">
				<li class="active"><a href="#main" class="icon-desktop" data-original-title="首页"></a></li>
				
				<li><a href="#workflow" class=" icon-envelope" data-original-title="协同审批"></a></li>
				
				<li><a href="#dailyreport" class="icon-calendar" data-original-title="日报"></a></li>
				<li><a href="#reimburse" class="icon-coffee" data-original-title="餐费报销"></a></li>
				<%if(hasAttence){ %>
				<li><a href="#attence" class="icon-time" data-original-title="考勤"></a></li>
				<%} %>
<%-- 				<%if(hasTraining){ %> --%>
				<li><a href="#training" class="icon-pinterest-sign" data-original-title="培训"></a></li>
<%-- 				<%} %> --%>
				<li><a href="#basetraining" class="icon-bold" data-original-title="基础培训"></a></li>
				<li><a href="#exam"  class="icon-paste" data-original-title="在线考试"></a></li>
				<%if(hasProject){ %>
				<li><a href="#ui-elements" class="icon-building" data-original-title="项目"></a></li>
				<%} %>
				<li><a href="#meeting"  class="icon-briefcase" data-original-title="会议管理"></a></li>
				
				<li><a href="#book"  class="icon-book" data-original-title="图书借阅"></a></li>
				<li><a href="#hr"  class=" icon-user" data-original-title="人力资源"></a></li>
				<%if(hasSysConfig){ %>
				<li class='sysconfig'><a href="#sysconfig" class="icon-cog" data-original-title="系统管理"></a></li>
				<%} %>
			</ul>
		</div>
		<div class="responsive-leftbar">
			<i class="icon-list"></i>
		</div>
		<div class="left-secondary-nav tab-content" >
			<div class="tab-pane active" id="main">
				<ul class="metro-sidenav clearfix">
					<li><a href="<c:url value='/home/dailyreport' />" class=" green"><i class="icon-shopping-cart"></i><span>我的日报</span></a></li>
					<li><a href="<c:url value='/home/myattence' />" class="orange"><i class="icon-cogs"></i><span>我的考勤</span></a></li>
<%-- 					<li><a href="<c:url value='/home/training' />" class=" dark-yellow"><i class="icon-file-alt"></i><span>我的培训</span></a></li> --%>
					<%-- 暂时不做 --%>
<%-- 					<li><a href="<c:url value='/home/project' />" class=" blue"><i class="icon-copy"></i><span>我的项目</span></a></li> --%>
<%-- 					<li><a href="<c:url value='/home/buglist' />" class=" bondi-blue"><i class="icon-lightbulb"></i><span>我的缺陷</span></a></li> --%>
					<li><a href="<c:url value='/home/toapprove' />" class="brown"><i class="icon-bar-chart"></i><span>我的待办</span></a></li>
<%-- 					<li><a href="<c:url value='/home/profile' />" class="blue-violate"><i class="icon-user"></i><span>个人资料</span></a></li>		 --%>
				</ul>
			</div>
			<div class="tab-pane dailyreport" id="dailyreport">
				<ul id="nav" class="accordion-nav" >
<%-- 					<%if(hasDailyReport_D_Write){ %> --%>
					<li><a href="${pageContext.request.contextPath}/dailyReport/dailyReport"><i class="icon-pencil"></i>日报填写</a></li>
<%-- 					<%} %> --%>
<%-- 					<%if(hasDailyReport_W_Submit){ %> --%>
					<li><a href="${pageContext.request.contextPath}/dailyReport/weekReport"><i class="icon-upload"></i>周报提交 </a></li>
<%-- 					<%} %> --%>
<%-- 					<%if(hasDailyReport_D_Search){ %> --%>
					<li><a href="${pageContext.request.contextPath}/dailyReport/viewDailyReport"><i class="icon-zoom-in"></i>日报查询 </a></li>
<%-- 					<%} %> --%>
<%-- 					<%if(hasDailyReport_W_Search){ %> --%>
					<li><a href="${pageContext.request.contextPath}/dailyReport/viewWeekReport"><i class="icon-zoom-out"></i>周报查询</a></li>
<%-- 					<%} %> --%>
					<%if(hasDailyReport_W_Export){ %>
					<li><a href="${pageContext.request.contextPath}/dailyReport/weekReportExport"><i class="icon-download-alt"></i>周报导出</a></li>
					<%} %>
					<%if(hasDailyReport_W_SubmitState){ %>
					<li><a href="${pageContext.request.contextPath}/dailyReport/weekReportState"><i class="icon-th"></i>周报提交统计</a></li>
					<%} %>
				</ul>
			</div>
			
			<div class="tab-pane reimburse" id="reimburse">
				<ul id="nav" class="accordion-nav" >
					<li><a href="${pageContext.request.contextPath}/reimburse/reimburse/"><i class="icon-user"></i>餐费报销</a></li>
					<%if(hasReimburseSummary){ %>
					<li><a href="${pageContext.request.contextPath}/reimburse/reimburse/summary"><i class="icon-user"></i>餐费汇总查询</a></li>
					<%} %>
					<%if(hasReimburseReport){ %>
					<li><a href="${pageContext.request.contextPath}/reimburse/report/"><i class="icon-table"></i> 餐费报销统计</a></li>
					<%} %>
				</ul>
			</div>
			
			<div class="tab-pane workflow" id="workflow">
				<ul id="nav" class="accordion-nav" >
					<li><a href="${pageContext.request.contextPath}/workflow/toapprove"><i class="icon-user"></i>待办事项</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/approved"><i class="icon-user"></i>已办事项</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/reimburse"><i class="icon-table"></i>餐费报销</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/dayoff"><i class="icon-table"></i>请假流程</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/overtime"><i class="icon-table"></i>加班流程 </a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/away"><i class="icon-table"></i>外出流程</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/absentee"><i class="icon-table"></i>补打卡流程</a></li>
					<li><a href="${pageContext.request.contextPath}/workflow/plan"><i class="icon-table"></i>培训流程</a></li>
				</ul>
			</div>
			<div class="tab-pane attence" id="attence">
				<ul id="nav" class="accordion-nav ">
					<%if(hasAttence_R){ %>
					<li><a href="${pageContext.request.contextPath}/attence/statecheck/index"><i class="icon-list-alt"></i> 考勤信息管理 <span class="notify-tip">考勤状态核对</span></a></li>
					<%} %>
					<%if(hasAttence_E){ %>
					<li><a href="${pageContext.request.contextPath}/attence/overtime/index"><i class="icon-th"></i> 加班工时核对 </a></li>
					<%} %>
					<%if(hasAttence_F){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/overtime/index"><i class="icon-bar-chart"></i> 加班统计报表 </a></li>
					<%} %>
					<%if(hasAttence_G){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/dayoff/index"><i class="icon-bar-chart"></i> 请假统计报表 </a></li>
					<%} %>
					<%if(hasAttence_H){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/absentee/index"><i class="icon-bar-chart"></i> 漏打卡统计报表 </a></li>
					<%} %>
					<%if(hasAttence_I){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/travel/index"><i class="icon-bar-chart"></i> 出差记录报表 </a></li>
					<%} %>
					<%if(hasAttence_J){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/attencestatistics/index"><i class="icon-bar-chart"></i> 请假时数统计表 </a></li>
					<%} %>
					<%if(hasAttence_K){ %>
					<li><a href="${pageContext.request.contextPath}/attence/report/attencecount/index"><i class="icon-bar-chart"></i> 考勤统计报表 </a></li>
					<%} %>
				</ul>
			</div>
			<div class="tab-pane training" id="training">
				<ul class="accordion-nav ">
					<li><a href="${pageContext.request.contextPath}/training/teacher/index"><i class="icon-user"></i> 讲师库管理</a></li>
					<li><a href="${pageContext.request.contextPath}/training/workflow/require/gather/index"><i class="icon-th"></i> 培训需求收集</a></li>
					<li><a href="${pageContext.request.contextPath}/survey/training"><i class="icon-th"></i> 培训问卷调查</a></li>
				</ul>
			</div>
			<div class="tab-pane training" id="basetraining">
				<ul class="accordion-nav ">
					<li><a href="${pageContext.request.contextPath}/basetraining/course"><i class=" icon-file-alt"></i> 在线学习课程管理</a></li>
					<li><a href="${pageContext.request.contextPath}/basetraining/plan"><i class=" icon-file-alt"></i> 基础培训计划</a></li>
					<li><a href="${pageContext.request.contextPath}/basetraining/my"><i class=" icon-file-alt"></i> 我的基础培训</a></li>
					<li><a href="page-503.html"><i class=" icon-file-alt"></i> 基础培训汇总</a></li>
				</ul>
			</div>
			<div class="tab-pane training" id="exam">
				<ul class="accordion-nav ">
					<li><a href="${pageContext.request.contextPath}/exam"><i class=" icon-file-alt"></i> 在线考试管理</a></li>
					<li><a href="${pageContext.request.contextPath}/exam/goover"><i class=" icon-file-alt"></i> 阅卷</a></li>
				</ul>
			</div>			
			<div class="tab-pane" id="ui-elements">
				<%if(hasProject){ %>
				<ul class="accordion-nav">
					<%if(hasProject_C){ %>
					<li><a href="${pageContext.request.contextPath}/project/index"><i class="icon-th-large"></i> 项目管理</a></li>
					<%} %>
					<%if(hasProject_U){ %>
					<li><a href="${pageContext.request.contextPath}/project/wbs/index"><i class="icon-th-large"></i>WBS查询</a></li>
					<%} %>
					
				</ul>
				<%} %>
			</div>
			<div class="tab-pane meeting" id="meeting">
				<ul class="accordion-nav">
					<% if (hasMeetingManager){%>
  				    <li><a href="${pageContext.request.contextPath}/meeting/roominfo"><i class=" icon-leaf"></i> 会议室管理</a></li>
  				    <%} %>
  				    <% if (hasReserveManager){%>
   				    <li><a href="${pageContext.request.contextPath}/meeting/reservemanage"><i class="icon-certificate"></i> 预约管理</a></li>
   				    <%} %>
  				    <% if (hasHistoryManager){%>
   				    <li><a href="${pageContext.request.contextPath}/meeting/historymanage"><i class="icon-list"></i> 历史会议</a></li>
   				    <%} %>
   				    <%%>
					<li><a href="${pageContext.request.contextPath}/meeting/waitingattend"><i class="icon-flag"></i> 待参加会议</a></li>
					<%%>
					<%%>
					<li><a href="${pageContext.request.contextPath}/meeting/reserveinfo"><i class="icon-tasks"></i> 会议室预约</a></li>
					<%%>
					<li><a href="${pageContext.request.contextPath}/meeting/personalcall"><i class="icon-signal"></i> 我召集的会议</a></li>
					<%%>
					<li><a href="${pageContext.request.contextPath}/meeting/reservehistory"><i class="icon-barcode"></i> 已参加会议</a></li>
					<%%>
					<li><a href="${pageContext.request.contextPath}/meeting/meetingsummary"><i class="icon-film"></i> 会议纪要</a></li>
					<%%>
				</ul>
			</div>
			<div class="tab-pane book" id="book">
				<ul class="accordion-nav">
					<% if(hasBookManager) {%>
  				    <li><a href="${pageContext.request.contextPath}/book/bookinfo"><i class=" icon-bold"></i> 图书管理</a></li>
  				    <%} %>
   				    <li><a href="${pageContext.request.contextPath}/book/lendrecord"><i class="icon-bookmark"></i> 图书借阅</a></li>
				</ul>
			</div>
			<div class="tab-pane" id="hr">
				<ul class="accordion-nav">
					<li><a href="${pageContext.request.contextPath}/sysconfig/resume"><i class=" icon-file-alt"></i> 人事档案</a></li>
					<li><a href="${pageContext.request.contextPath}/sysconfig/resume/contract"><i class=" icon-file-alt"></i> 合同管理</a></li>
					<li><a href="${pageContext.request.contextPath}/sysconfig/resume/dimission"><i class=" icon-file-alt"></i> 离职管理</a></li>
				</ul>
			</div>
			<div class="tab-pane sysconfig" id="sysconfig">
				<%if(hasSysConfig){ %>
				<ul class="accordion-nav " >
					<li><a href="${pageContext.request.contextPath}/sysconfig/news"><i class="icon-user"></i> 新闻公告</a></li>
					
					<%if(hasSysConfigHoliday){ %>
					<li><a href="${pageContext.request.contextPath}/sysconfig/workandholiday"><i class="icon-rss"></i> 节假日设置</a></li>
					<%} %>
					<%if(hasSysConfigAttencedatareflesh){ %>
					<li><a href="${pageContext.request.contextPath}/sysconfig/attencedatareflesh/index"><i class="icon-download-alt"></i>考勤数据迁移</a></li>
					<%} %>
					<%if(hasSysConfigFunction){ %>
					<li><a href="${pageContext.request.contextPath}/functionrights/index"><i class="icon-group"></i>功能权限设置</a></li>
					<%} %>
					<%if(hasSysConfigDept){ %>
						<li><a href="${pageContext.request.contextPath}/sysconfig/dept/index"><i class=" icon-file-alt"></i>部门管理</a></li>
					<%} %>
					<%if(hasSysConfigUser){ %>
						<li><a href="${pageContext.request.contextPath}/user/index"><i class=" icon-file-alt"></i>用户管理</a></li>
					<%} %>
<%-- 					<%if(hasSysConfigResume){ %> --%>
<%--   				    	<li><a href="${pageContext.request.contextPath}/sysconfig/resume"><i class=" icon-file-alt"></i>人事档案</a></li> --%>
<%-- 					<%} %> --%>
				</ul>
				<%} %>
			</div>
		</div>
	</div>
</div>