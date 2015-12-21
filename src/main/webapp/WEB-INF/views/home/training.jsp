<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.home.training.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
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
		$.ims.traininghome.initTrainingDetailDataTable();
	});
</script>
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
				<div class="row-fluid">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head bondi-blue">
								<h3>我的培训</h3>
							</div>
							<div>
								<div class="left-stripe form-horizontal" style="margin-left: 10px; margin-top:20px;">
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">培训课时：</label>
										<div class="controls">
											<input type="text" value="${totalHours }" readonly="readonly" />
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">受训次数：</label>
										<div class="controls">
											<input type="text" value="${trainedTimes }" readonly="readonly" />
										</div>
									</div>
									<div class="control-group" style="margin-left: -50px;">
										<label class="control-label">培训次数：</label>
										<div class="controls">
											<input type="text" value="${trainingTimes }" readonly="readonly" />
										</div>
									</div>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="content-widgets">
							<div>
								<div class="widget-header-block">
									<h4 class="widget-header text-warning"> 培训明细</h4>
								</div>
								<div>
									<div class="form-horizontal box well">
										<span style="margin-left: 20px;">培训日期：</span>
										<input id="from_date" type="text" value="${fromDate }" class="date span1"> ~ 
										<input id="to_date" type="text" value="${toDate }" class="span1 date">
										<a onclick="$.ims.traininghome.initTrainingDetailDataTable();" class="btn btn-info">查询</a>
									</div>
									<table id="trainingDetail_dataTable" class="responsive table table-striped table-bordered table-condensed">
										<thead>
										<tr>
											<th> # </th>
											<th> 培训课题 </th>
											<th> 培训日期 </th>
											<th> 培训时间 </th>
											<th> 培训课时 </th>
											<th> 培训讲师 </th>
											<th> 备注 </th>
										</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
<!-- 				<div class="row-fluid"> -->
<!-- 					<div class="span6"> -->
<!-- 						<div class="content-widgets"> -->
<!-- 							<div> -->
<!-- 								<div class="widget-header-block"> -->
<!-- 									<h4 class="widget-header  text-warning"> 年度培训分布</h4> -->
<!-- 								</div> -->
<!-- 								<div> -->
<!-- 									<div id="annualChart1" class="span6" style="height:500px;width:600px;border:1px solid #ccc;margin-left:0;"></div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="span6"> -->
<!-- 						<div class="content-widgets"> -->
<!-- 							<div> -->
<!-- 								<div class="widget-header-block"> -->
<!-- 									<h4 class="widget-header  text-warning"> 培训类型分布</h4> -->
<!-- 								</div> -->
<!-- 								<div> -->
<!-- 									<div id="annualChart2" class="span6" style="height:500px;width:600px;border:1px solid #ccc;"></div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/echart/echarts-all.js"></script> --%>
<!--  	<script type="text/javascript"> -->
//         var annualChart1 = echarts.init(document.getElementById('annualChart1'));
//         annualChart1.setOption({
//         	title: {
//                 text: '年度培训分布报表'
//             },
//             tooltip : {
//                 trigger: 'axis'
//             },
//             legend: {
//                 data:['培训次数','培训工时']
//             },
//             toolbox: {
//                 show : true,
//                 feature : {
//                     mark : {show: false},
//                     dataView : {show: true, readOnly: false},
//                     magicType : {show: true, type: ['line', 'bar']},
//                     restore : {show: true},
//                     saveAsImage : {show: true}
//                 }
//             },
//             calculable : true,
//             xAxis : [
//                 {
//                     type : 'category',
//                     data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
//                 }
//             ],
//             yAxis : [
//                 {
//                     type : 'value',
//                     splitArea : {show : true}
//                 }
//             ],
//             series : [
//                 {
//                     name:'培训次数',
//                     type:'bar',
//                     data:[1, 1, 0, 2, 7, 2, 3, 1, 1, 0, 0, 4]
//                 },
//                 {
//                     name:'培训工时',
//                     type:'bar',
//                     data:[3, 3, 0, 6, 20, 4, 3, 3, 2, 0, 0, 10]
//                 }
//             ]
//         });
        
//         var annualChart2 = echarts.init(document.getElementById('annualChart2'));
//         annualChart2.setOption({
//         	title : {
//                 text: '培训类型分布统计',
//                 x:'center'
//             },
//             tooltip : {
//                 trigger: 'item',
//                 formatter: "{a} <br/>{b} : {c} ({d}%)"
//             },
//             legend: {
//                 orient : 'vertical',
//                 x : 'left',
//                 data:['入职/转岗培训','企业文化制度培训','体系培训','管理者培训','任职能力/岗位技能培训','专业提升培训','战略培训','客户要求培训']
//             },
//             toolbox: {
//                 show : true,
//                 feature : {
//                     mark : {show: false},
//                     dataView : {show: true, readOnly: false},
//                     magicType : {
//                         show: true, 
//                         type: ['pie', 'funnel'],
//                         option: {
//                             funnel: {
//                                 x: '25%',
//                                 width: '50%',
//                                 funnelAlign: 'left',
//                                 max: 1548
//                             }
//                         }
//                     },
//                     restore : {show: true},
//                     saveAsImage : {show: true}
//                 }
//             },
//             calculable : true,
//             series : [
//                 {
//                     name:'访问来源',
//                     type:'pie',
//                     radius : '55%',
//                     center: ['50%', '60%'],
//                     data:[
//                         {value:335, name:'入职/转岗培训'},
//                         {value:310, name:'企业文化制度培训'},
//                         {value:234, name:'体系培训'},
//                         {value:135, name:'管理者培训'},
//                         {value:1548, name:'任职能力/岗位技能培训'},
//                         {value:234, name:'专业提升培训'},
//                         {value:135, name:'战略培训'},
//                         {value:1548, name:'客户要求培训'}
//                     ]
//                 }
//             ]
//         });
        
<!--     </script>	 -->
</body>
</html>