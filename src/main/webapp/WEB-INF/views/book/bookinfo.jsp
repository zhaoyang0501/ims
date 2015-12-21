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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-popover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/chosen.jquery.js"></script> 



<script type="text/javascript">
$(document).ready(function(){	

 	$.ims.book.bookStatusInfo("s_status");
 	$.ims.book.bookOverrunInfo("s_overrun");
 	$.ims.book.lendTypeInfo("lendtype");
 	
 	$.ims.common.findAllUser(function(){
 		$("#s_pm").chosen({
 			allow_single_deselect : true,
 			no_results_text : "没有找到.",
 			disable_search_threshold : 5,
 			enable_split_word_search : true,
 			search_contains : true
 		});
 	}, "s_pm"); 
 	
 	$.ims.common.findAllUser(function(){
 		$("#s_userid").chosen({
 			allow_single_deselect : true,
 			no_results_text : "没有找到.",
 			disable_search_threshold : 5,
 			enable_split_word_search : true,
 			search_contains : true
 		});
 	}, "s_userid"); 
 	

 	$.ims.book.initBookInfoDataTable(1,1,1,1,1,1);
 	$.ims.book.initLendRecordDataTable(1)
});
</script>
<style type="text/css">
	.chzn-container .chzn-results{
		max-height:150px;
	}
	
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
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper" style = "height:100%">
			<div class="container-fluid">
				<%@include file="../breadcrumb.jsp"%>
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets " style = "height:100%">
							<div class="widget-head  bondi-blue" >
								<h3>图书信息管理</h3>
							</div>
							<ul class="nav nav-tabs" id="myTab2">
								<li class="active"><a href="#bookinfo"><i class="icon-check"></i>图书信息管理</a></li>
								<li class=""><a href="#lendrecord"><i class="icon-edit"></i>借阅记录查看</a></li>
							</ul>
							<div class="tab-content" style = "height:100%">
								<div class="tab-pane active" id="bookinfo" style = "height:100%">
									<div class="box well form-inline">	
										<span>编码：</span>
										<input type="text" maxlength="12" id="s_code" class="span1">
										<span>书名：</span>
										<input type="text" maxlength="30" id="s_bookname" class="span2">	
										<span>价格：</span>
										<input type="text" maxlength="6" id="s_beginprice" class="span1">
										~ <input type="text" maxlength="6" id="s_endprice" class="span1">
										<span>作者：</span>
										<input type="text" maxlength="10" id="s_author" class="span1">	
										<span>状态：</span>
										<select id="s_status" style="width: 100px;" data-placeholder="选择状态">
										</select>															
										<a  onclick="$.ims.book.initBookInfoDataTable( 1,1,1,1,1,1)" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<div class="widget-container" style = "height:100% ;margin-bottom:100px;"> 
										<a class="btn btn-success" style="float: right; margin: 5px;" onclick="$.ims.book.exportBookReport()"><i class="icon-play"></i> 导出Excel</a>
										<a class="btn btn-success" style="float: right; margin: 5px;" onclick="$.ims.book.createBookInfo()"><i class="icon-plus"></i> 新增图书</a>
										<table class="responsive table table-striped table-bordered table-hover table-condensed" id="bookinfo_dataTable">
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
										<div></div>
									</div>
								</div>
								<div class="tab-pane" id="lendrecord">
									<div class="box well form-inline">	
										<span>书名：</span>
										<input type="text" id="s_book" maxlength="30" class="span2">
										<span>用户：</span>
										<select id="s_userid" style="width: 200px;" data-placeholder="选择用户">
										</select>		
										<span>是否超限：</span>
										<select id="s_overrun" style="width: 100px;" data-placeholder="选择状态">
										</select>															
										<a  onclick="$.ims.book.initLendRecordDataTable(1)" class="btn btn-info"><i class="icon-search"></i>查询</a>
									</div> 
									<div class="widget-container"> 
										<table class="responsive table table-striped  table-hover table-bordered table-condensed" id="lendrecord_dataTable">
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
													<th style="width: 5%" >操作</th>
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
	
	
	<!-- 查看续约记录 -->
	<div class="modal hide fade" id="pre_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label>预约详情</label>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal">
					<table class="table table-bordered table-condensed response">
					<thead>
						<tr>
							<th rowspan="2">#</th>
							<th>书名</th>
							<th>预约时间</th>
							<th>用户</th>
						</tr>
					</thead>
					<tbody id ="pre_record"></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 出借图书弹出框 -->
	<div class="modal hide fade" id="book_lend_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="book_lend_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:300px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal"> 
	 				<form class="form-horizontal" method="post" id ="lendbook"> 
	 					<div class="control-group"> 
							<label class="control-label"> 图书编号：</label> 
							<div class="controls"> 
								<input type="text" id="lend_code"  readonly="readonly"  class="span8" > 
							</div> 
						</div>
						<div class="control-group"> 
							<label class="control-label"> 图书名称：</label> 
							<div class="controls"> 
								<input type="text" id="lend_book"  readonly="readonly"  class="span8" > 
							</div> 
						</div>	
	 					<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 借书人：</label> 
							<div class="controls"> 
								<select id="s_pm"  data-placeholder="请选择借书人"> 
								</select> 
							</div> 
						</div>
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 借阅类别：</label> 
							<div class="controls"> 
								<select id="lendtype" data-placeholder="请选择借阅方式" > 
								</select> 
							</div> 
						</div>	 
	 				</form> 
	 			</div> 
			</div>
		</div>
		<div class="modal-footer center" id="div_booklend">
			<a class="btn btn-primary" onclick="$.ims.book.lendBook()">借出</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div> 
	
	<!-- 续借图书弹出框 -->
	<div class="modal hide fade" id="book_renew_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="book_renew_header_label"></label>
		</div>
		<div class="modal-body" style="min-height:50px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal"> 
	 				<form class="form-horizontal" method="post" id ="renewbook"> 
						<div class="control-group"> 
							<div class="controls"> <span class="text-error">确定续借图书吗？只能续借 一次哦！</span>  
							</div>
						</div>		 
	 				</form> 
	 			</div> 
			</div>
		</div>
		<div class="modal-footer center" id="div_bookrenew">
			<a class="btn btn-primary" onclick="$.ims.book.renewBook()">续借</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div> 
	
	
	<!-- 归还图书弹出框 -->
	<div class="modal hide fade" id="book_return_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="book_return_header_label"></label>
		</div>
		<div class="modal-body" style="height:200px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal"> 
	 				<form class="form-horizontal" method="post" id ="returnbook"> 
						<div class="control-group"> 
							<label class="control-label"> 图书名称：</label> 
							<div class="controls"> 
								<input type="text" id="returnname" name="returnname" readonly="readonly"  class="span8" > 
							</div> 
						</div>	
						<div class="control-group"> 
							<label class="control-label"> 借书人：</label> 
							<div class="controls"> 
								<input type="text" id="lenduser" name="lenduser" readonly="readonly"  class="span8" > 
							</div> 
						</div>
						<div class="control-group"> 
							<label class="control-label"> 借书日期：</label> 
							<div class="controls"> 
								<input type="text" id="lendtime" name="lendtime" readonly="readonly"  class="span8" > 
							</div> 
						</div>
						<div class="control-group"> 
							<label class="control-label"> 赔偿金额（元）：</label> 
							<div class="controls"> 
								<input type="text" id="money" name="money" readonly="readonly"  class="span8" > 
							</div> 
						</div>
						<div class="control-group"> 
							<div class="controls"> <span class="text-error">请确保罚款已经交清！</span></div>  
						</div>
	 				</form> 
	 			</div> 
			</div>
		</div>
		<div class="modal-footer center" id="div_bookreturn">
			<a class="btn btn-primary" onclick="$.ims.book.returnBook()">归还</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div> 
	
	
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="book_edit_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="book_modal_header_label"></label>
		</div>
		<div class="modal-body" style="height:600px;">
			<div class="row-fluid">
				<div class="form-container grid-form form-background left-align form-horizontal"> 
	 				<form class="form-horizontal" method="post" id ="editbook"> 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 编码：</label> 
							<div class="controls"> 										
							<input type="text" id="code"  maxlength="12" name="code" class="span8"> 
							</div> 
						</div> 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 图书名称：</label> 
							<div class="controls"> 
								<input type="text" id="bookname" maxlength="30" name="bookname" class="span8"> 
							</div> 
						</div>			 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 价格：</label> 
							<div class="controls"> 
							<input type="text" id="price" maxlength="6" name="price" class="span8"> 
							</div> 
						</div>	 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 作者：</label> 
							<div class="controls"> 
							<input type="text" id="author" maxlength="50" name="author" class="span8"> 
							</div> 
						</div>	 
						<div class="control-group"> 
							<label class="control-label"><span class="text-error">*</span> 出版社：</label> 
							<div class="controls"> 
							<input type="text" id="publish" maxlength="20" name="publish" class="span8"> 
							</div>
						</div>									 
						<div class="control-group"> 
							<label  class="control-label">描述：</label> 
							<div class="controls"> 
								<textarea id="description" maxlength="200" name="description" style="width:210px;height:70px;"></textarea>
							</div>									 
						</div>	 
	 				</form> 
 				</div>
			</div>
		</div>
		<div class="modal-footer center" id="div_editbook">
			<a class="btn btn-primary" onclick="$.ims.book.saveOrUpdateBook()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div> 
	
</body>
</html>