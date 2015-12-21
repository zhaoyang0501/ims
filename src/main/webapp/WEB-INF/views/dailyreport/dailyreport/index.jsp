<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<head>
<%@ include file="../../common/meta.jsp"%>
  <!-- 日程表控件 http://fullcalendar.io/  -->
<link href="${pageContext.request.contextPath}/css/fullcalendar.css" rel= "stylesheet">
<script src="${pageContext.request.contextPath}/js/falgun/fullcalendar.min.js"></script >
<style type="text/css">
body{line-height: 18px;}
</style>
<script type='text/javascript'>
            $(document).ready(function () {
                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                $('#calendar').fullCalendar({
                    dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
        			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
        			dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
        			buttonText : {
        				today : "今天",
        				month : "月",
        				week : "星期",
        				day : "天"
        			},
        			titleFormat : {
        				month : "yyyy MMMM",
        			},
        			header : {
        				left : 'prevYear,prev,next,nextYear today',
        				center : 'title',
        				right : ""
        			},
        			handleWindowResize : true,
        			height: 800,
        			aspectRatio : 1.8,
                    events:"${pageContext.request.contextPath}/dailyReport/dailyReport/query",
                    dayClick : function(date, allDay, jsEvent, view) {
        				var selDate = $.fullCalendar.formatDate(date, 'yyyy-MM-dd');
        				window.location.href = "${pageContext.request.contextPath}/dailyReport/dailyReport/create/" + selDate;
        			}
                });
            });
        </script>

</head>
<body>
<div class="layout">
		<!-- top -->
		<%@ include file="../../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../../menu.jsp"%>
		
		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>日报填写</h3>
							</div>
							<div class="content-widgets gray">
								<div class="widget-container" style="height: 80%">
									<div id='calendar'>
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