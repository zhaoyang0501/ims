<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>
<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head> 
<script src="${pageContext.request.contextPath}/js/falgun/date.js"></script>
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.home.myattence.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script >
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript">
	$(function() {
		$(".date").datetimepicker({
			language:  'zh-CN',
		    weekStart: 1,
		    todayBtn:  1,
		    format:'yyyy-mm-dd',
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
		});
		$.ims.myattence.initCodeMap();
		$(".bill_choose_l li").eq(5).trigger("click");
		
	});
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:120px;
	}
	/*--月份选择样式--*/	
	.bill_choose {
	  overflow: hidden;
	  height: 50px;
	  border-bottom: 4px solid #ededed;
	  line-height: 45px;
	  margin-top: 10px;
	  margin-bottom: 15px;
	}
	.bill_choose_l {
	 float: left;
	  margin-left: 180px;
	}
	.bill_choose li{
	  float: left;
	  display: block;
	  text-align: center;
	  width: 40px;
	  margin: 0 10px;
	  cursor: pointer;
	  line-height: 45px;
	}
	.now_m {
	  background: url(${pageContext.request.contextPath}/images/bill_icon.png) no-repeat 0 -64px;
	  height: 50px;
	  font-size: 16px;
	  color: #fff;
	  width: 40px;
	  font-weight: 700;
	}
	.choose-month {
	  position: absolute;
	  color: #c1c1c1;
	  right: 0;
	  top: 50px;
	  font-family: simhei;
	  background: url(${pageContext.request.contextPath}/images/month-bg.png);
	  width: 141px;
	  height: 126px;
	  display: inline-block;
	  text-align: center;
	}
	.choose-month em {
	  line-height: 120px;
	  font-style: normal;
	  font-weight: 400;
	  font-size: 60px;
	  transform: rotate(-25deg);
	  -ms-transform: rotate(-25deg);
	  -moz-transform: rotate(-25deg);
	  -webkit-transform: rotate(-25deg);
	  -o-transform: rotate(-25deg);
	  display: inline-block;
	}
	.bill_list1 li{
	    list-style: none;
	 	padding: 5px 0;
	 	line-height: 25px;
	}
	
	.font-green {
	  color: #8ec31f;
	}
	.bill-top-title-green {
	  background: url(${pageContext.request.contextPath}/images/bill-top-blue.png) repeat-x;
	  height: 50px;
	  border: none;
	  line-height: 50px;
	}
	.bill_list1 .business-icon2 {
	  background: url(${pageContext.request.contextPath}/images/business-sprite.png) no-repeat 0 -325px;
	  width: 190px;
	  height: 25px;
	  color: #fff;
	  line-height: 25px;
	  padding-left: 17px;
	  margin: 0 30px;
	  font-weight: 300;
	  color: #fff;
	  position: absolute;
	  z-index: 99;
	}
</style>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue" >
								<h3>我的考勤</h3>
							</div>
							<div class="widget-container">
								<div class="bill_choose">
									<ul class="bill_choose_l">
									<c:forEach items="${monthMap}" var="bean">  
									       <li  class="" onclick="$.ims.myattence.initMyattenceDataTable('${bean.key}',this)">${bean.value}</li>
									</c:forEach>  
									</ul>
								</div>
							</div>
							
							<div class="row-fluid">
							<div class="span6">
								<div class="content-widgets ">
									<div class=" widget-head bill-top-title-green">
										<h3>调休统计</h3>
									</div>
									<div class="widget-container">
												<ul class="bill_list1">
													<li>上期结余：<b id='last_rest' class="font-green"></b></li>
													<li>本期新增：<b id='current_increase' class="font-green"></b><span style="display: none;" class="business-icon2" >已经击败了公司<span id='rank'></span>%的人！</span></li>
													<li>本期减少：<b id='current_reduce'  class="font-green"></b></li>
													<li>本期剩余：<b id='current_rest'  class="font-green"></b>
													<li>绩效加班：<b id='current_overtime'  class="font-green"></b>
												</ul>
												<p class="choose-month">
													<em class='cur_month'></em>
												</p>
									</div>
								</div>
							</div>
							
							<div class="span6">
								<div class="content-widgets ">
									<div class=" widget-head bill-top-title-green">
										<h3>考勤统计</h3>
									</div>
									<div class="widget-container">
										<div id='dayoff_chart' style="height: 200px"></div>
												<p class="choose-month">
													<em class='cur_month'></em>
												</p>
										</div>
								</div>
							</div>
					 </div>
							
							<div class="widget-container">
								<div class="widget-header-block">
									<h4 class="widget-header">打卡明细</h4>
								</div>
								<table id='myattenceDataTable' class="responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>员工工号</th>
											<th>员工姓名</th>
											<th>打卡日期</th>
											<th>星期</th>
											<th>刷卡记录</th>
											<th>备注</th>
											<th>状态</th>
											<th>详细</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
	
	
	<div class="modal hide fade" id="myAttence">
		<div class="modal-header blue">
			考勤详情<button type="button" class="close" data-dismiss="modal">×</button>
		</div>
		
		<div class="modal-body "  style="min-height: 400px;background: #fff;">
			<div class="row-fluid">
				<div class="tab-widget">
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#absentee"><i class="icon-edit"></i> 漏打卡</a></li>
							<li class=""><a href="#overtime"><i class="icon-check"></i>加班</a></li>
							<li class=""><a href="#dayoff"><i class="icon-check"></i>请假</a></li>
							<li class=""><a href="#travel"><i class="icon-check"></i>出差</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="absentee">
							<table  class="responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>漏打卡时间</th>
											<th>漏打卡类型</th>
											<th>漏打卡原因</th>
											<th>核对时间</th>
										</tr>
									</thead>
									<tbody  id='absentee_table'>
									</tbody>
								</table>
							</div>
							
							<div class="tab-pane" id="overtime">
							 <table  class="responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>核对加班时间</th>
											<th>核对工时</th>
											<th>OA状态</th>
											<th>核对时间</th>
										</tr>
									</thead>
									<tbody  id='overtime_table'>
									</tbody>
								</table>
							</div>
							
							<div class="tab-pane" id="dayoff">
							 <table  class="responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>请假时间</th>
											<th>假别</th>
											<th>核对工时</th>
											<th>核对时间</th>
										</tr>
									</thead>
									<tbody  id='dayoff_table'>
									</tbody>
								</table>
							</div>
							
							<div class="tab-pane" id="travel">
							 <table  class="responsive table table-striped table-bordered table-condensed table-hover">
									<thead>
										<tr>
											<th>出差时间</th>
											<th>出差地址</th>
											<th>出差原因</th>
											<th>核对时间</th>
										</tr>
									</thead>
									<tbody  id='travel_table'>
									</tbody>
								</table>
							</div>
							
						</div>
					</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/echart/echarts-all.js"></script>
</body>
</html>