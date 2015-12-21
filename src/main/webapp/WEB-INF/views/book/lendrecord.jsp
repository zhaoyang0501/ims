<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head> 
<link href="${pageContext.request.contextPath}/css/chosen.css" rel="stylesheet"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.book.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>

<%
boolean hasDel = RightUtil.hasRight("ROLE_BOOK_MANAGER", "D");	//删除
%>

<script type="text/javascript">
$(document).ready(function(){	
 	var hasDel = <%= hasDel %>; 
 	$.ims.book.bookStatusInfo("ss_status");
 	$.ims.book.bookOverrunInfo("ss_overrun");

 	$.ims.book.initBookDataTable()
 	$.ims.book.initUserLendRecordDataTable();
});
</script>
<style type="text/css">
	.row-fluid [class*="span"]{
	min-height: 34px;
	}
</style>
</head>
<body>

   <div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="lr_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets">
							<div class="widget-head  bondi-blue" >
								<h3>图书借阅记录</h3>
							</div>
					
						<ul class="nav nav-tabs" id="myTab2">
							<li class="active"><a href="#bookinfo"><i class="icon-check"></i>图书信息</a></li>
							<li class=""><a href="#userlendrecord"><i class="icon-edit"></i>个人借阅记录</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="bookinfo">
									<div class="box well form-inline">	
										<span>编码：</span>
										<input type="text" maxlength="12" id="ss_code" class="span1">
										<span>书名：</span>
										<input type="text" maxlength="30" id="ss_bookname" class="span2">	
										<span>价格：</span>
										<input type="text" maxlength="6" id="ss_beginprice" class="span1">
										~ <input type="text" id="ss_endprice" class="span1">
										<span>作者：</span>
										<input type="text" maxlength="6" id="ss_author" class="span1">	
										<span>状态：</span>
										<select id="ss_status" style="width: 100px;" data-placeholder="选择状态">
										</select>															
										<a  onclick="$.ims.book.initBookDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<div class="widget-container"> 
										<table class="responsive table table-striped tbl-simple table-hover table-bordered table-condensed" id="book_dataTable">
											<thead>
												<tr>
													<th style="width: 5%">编码</th>
													<th style="width: 20%">书名</th>
													<th style="width: 5%">价格</th>
													<th style="width: 18%">作者</th>
													<th style="width: 10%">出版社</th>
													<th style="width: 15%">描述</th>
													<th style="width: 3%">状态</th>
													<th style="width: 5%">借阅人</th>
													<th style="width: 7%">操作</th>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
							
								<div class="tab-pane" id="userlendrecord">
									<div class="box well form-inline">	
										<span>书名：</span>
										<input type="text" maxlength="30" id="ss_book" class="span2">
										<input type="hidden" type="text" id="ss_userid" value="0" class="span2">
										<span>是否超限：</span>
										<select id="ss_overrun" style="width: 100px;" data-placeholder="选择状态">
										</select>															
										<a  onclick="$.ims.book.initUserLendRecordDataTable()" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<div class="widget-container"> 
										<table class="responsive table table-striped  table-hover tbl-simple table-bordered table-condensed" id="userlendrecord_dataTable">
											<thead>
												<tr>
													<th style="width: 20%">书名</th>
													<th style="width: 5%">借书人</th>
													<th style="width: 10%">借出时间</th>
													<th style="width: 5%">是否超限</th>
													<th style="width: 10%">归还时间</th>
													<th style="width: 7%">赔偿金额（元）</th>
													<th style="width: 5%">借阅方式</th>
													<th style="width: 10%">应还时间</th>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
</body>
</html>