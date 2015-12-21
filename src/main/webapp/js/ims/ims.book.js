jQuery.ims.book = {

    opstatus : null,
	bookInfoDataTable : null,
	bookDataTable : null,
	lendRecordDataTable: null,
	userLendRecordDataTable: null,
	otype : 0, // 操作类型：0新增 1修改
	
	/**
	 * 刷新图书管理信息。
	 * 
	 */
	refreshBookInfoDataTable : function() {
		if (this.bookInfoDataTable != null) {
			var oSettings = this.bookInfoDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.bookInfoDataTable.fnDraw(oSettings);
		}
	},
	
	/**
	 * 刷新图书信息。
	 * 
	 */
	refreshBookDataTable : function() {
		if (this.bookDataTable != null) {
			var oSettings = this.bookDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.bookDataTable.fnDraw(oSettings);
		}
	},
	
	/**
	 * 刷新借阅记录。
	 * 
	 */
	refreshLendRecordDataTable : function() {
		if (this.lendRecordDataTable != null) {
			var oSettings = this.lendRecordDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.lendRecordDataTable.fnDraw(oSettings);
		}
	},
	
	/**
	 * 刷新个人借阅记录。
	 * 
	 */
	refreshUserLendRecordDataTable : function() {
		if (this.userLendRecordDataTable != null) {
			var oSettings = this.userLendRecordDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.userLendRecordDataTable.fnDraw(oSettings);
		}
	},

	/**
	 * 图书管理信息详情。
	 * P：预约记录 L：借出 R：归还 U：更新 D：删除 X:续借图书
	 */
	initBookInfoDataTable : function(P, L, R, U, D, X) {
		if (this.bookInfoDataTable == null) {
			this.bookInfoDataTable = $("#bookinfo_dataTable")
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
								"oLanguage" : {
									"iDisplayLength" : 10,
									"aLengthMenu" : [ 10, 25, 50, 100 ],
									"sLengthMenu" : "每页显示 _MENU_ 条记录",
									"sZeroRecords" : "抱歉， 暂时没有记录",
									"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
									"sInfoEmpty" : "没有数据",
									"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "尾页"
									}
								},
								"bServerSide" : true,
								"sServerMethod" : "POST",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : true,
								"bFilter" : false,
								"fnDrawCallback" : function(oSettings) {
									$('[rel="popover"],[data-rel="popover"]').popover();
								},
								"sAjaxSource" : $.ims.getContextPath()+ '/book/list',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var code = $("#s_code").val();
								 var bookName = $("#s_bookname").val();
								 var beginPrice = $("#s_beginprice").val();
								 var endPrice = $("#s_endprice").val();
								 var author = $("#s_author").val();
								 var status = $("#s_status").val();
								
								 if (!!code) {
								 aoData.push({
								 "name" : "code",
								 "value" : code
								 });
								 }
								 if (!!bookName) {
								 aoData.push({
								 "name" : "bookName",
								 "value" : bookName
								 });
								 }
								 if (!!beginPrice) {
								 aoData.push({
								 "name" : "beginPrice",
								 "value" : beginPrice
								 });
								 }
								 if (!!endPrice) {
								 aoData.push({
								 "name" : "endPrice",
								 "value" : endPrice
								 });
								 }
								 if (!!author) {
								 aoData.push({
								 "name" : "author",
								 "value" : author
								 });
								 }
								 if (!!status) {
								 aoData.push({
								 "name" : "status",
								 "value" : status
								 });
								 }
								 $.ajax({
									 "dataType" : 'json',
									 "type" : "POST",
									 "url" : sSource,
									 "data" : aoData,
									 "success" : fnCallback
									 });
								 },
								"aoColumns" : [ {
									"mDataProp" : "code"
								}, {
									"mDataProp" : "bookName"
								}, {
									"mDataProp" : "price"
								}, {
									"mDataProp" : "author"
								}, {
									"mDataProp" : "publish"
								}, {
									"mDataProp" : "description"
								}, {
									"mDataProp" : "status"
								}, {
									"mDataProp" : "lendUser"
								}, {
									"mDataProp" : "id"
								}

								],
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 1 ],
									'bSortable' : false
								},{
									'aTargets' : [ 2 ],
									'bSortable' : false
								},{
									'aTargets' : [ 3 ],
									'bSortable' : false
								},{
									'aTargets' : [ 4 ],
									'bSortable' : false
								}, {
									'aTargets' : [ 5 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='描述信息'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-success'>借出</span>";
										}
										if (sVal == "2") {
											return "<span class='label label-warning'>长期</span>";
										}
										return "<span class='label label-info'>正常</span>";
									}
								},
								{
									'aTargets' : [ 7 ],
									'bSortable' : false
								},
								{
									'aTargets' : [ 8 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';

										if (P) {
										operate += '<a class="btn2 btn-info" onclick="$.ims.book.showPreRecord('
												+ oObj.aData.id
												+ ')"><i class="icon-zoom-in"></i> 查看预约</a>';
										}
										if (L || R || U || D || X) {
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											if (L || X){
												if (opstatus == 0){
														operate += '<li><a onclick="$.ims.book.lendBookInfo('
															+ oObj.aData.id
															+ ')"><i class="icon-edit"></i> 借出</a></li>';
												}
												if(opstatus == 1){
														operate += '<li><a onclick="$.ims.book.renewBookInfo('
															+ oObj.aData.id
															+ ')"><i class="icon-share"></i> 续借图书</a></li>';
														operate += '<li><a onclick="$.ims.book.returnBookInfo('
															+ oObj.aData.id
															+ ')"><i class="icon-edit"></i> 归还</a></li>';
												}
												if(opstatus == 2){
														operate += '<li><a onclick="$.ims.book.returnBookInfo('
															+ oObj.aData.id
															+ ')"><i class="icon-check"></i> 归还</a></li>';
												}
											}
											if(D){
												operate += '<li><a onclick="$.ims.book.editBookInfo('
													+ oObj.aData.id
													+ ')"><i class="icon-wrench"></i> 更新</a></li>';
											}
											if(R){
												operate += '<li><a onclick="$.ims.book.deleteBookInfo('
													+ oObj.aData.id
													+ ')"><i class="icon-trash"></i> 删除</a></li>';
											}
											operate += '</ul>';
											operate += '</div>';
										}
										
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshBookInfoDataTable();
		}
	},

	
	/**
	 * 图书信息详情。
	 */
	initBookDataTable : function() {
		if (this.bookDataTable == null) {
			this.bookDataTable = $("#book_dataTable")
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
								"oLanguage" : {
									"iDisplayLength" : 10,
									"aLengthMenu" : [ 10, 25, 50, 100 ],
									"sLengthMenu" : "每页显示 _MENU_ 条记录",
									"sZeroRecords" : "抱歉， 暂时没有记录",
									"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
									"sInfoEmpty" : "没有数据",
									"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "尾页"
									}
								},
								"bServerSide" : true,
								"sServerMethod" : "POST",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : true,
								"bFilter" : false,
								"fnDrawCallback" : function(oSettings) {
									$('[rel="popover"],[data-rel="popover"]').popover();
									},
								"sAjaxSource" : $.ims.getContextPath()+ '/book/list',
								 "fnServerData" : function(sSource, aoData,
								 fnCallback) {
								 var code = $("#ss_code").val();
								 var bookName = $("#ss_bookname").val();
								 var beginPrice = $("#ss_beginprice").val();
								 var endPrice = $("#ss_endprice").val();
								 var author = $("#ss_author").val();
								 var status = $("#ss_status").val();
								
								 if (!!code) {
								 aoData.push({
								 "name" : "code",
								 "value" : code
								 });
								 }
								 if (!!bookName) {
								 aoData.push({
								 "name" : "bookName",
								 "value" : bookName
								 });
								 }
								 if (!!beginPrice) {
								 aoData.push({
								 "name" : "beginPrice",
								 "value" : beginPrice
								 });
								 }
								 if (!!endPrice) {
								 aoData.push({
								 "name" : "endPrice",
								 "value" : endPrice
								 });
								 }
								 if (!!author) {
								 aoData.push({
								 "name" : "author",
								 "value" : author
								 });
								 }
								 if (!!status) {
								 aoData.push({
								 "name" : "status",
								 "value" : status
								 });
								 }
								 $.ajax({
									 "dataType" : 'json',
									 "type" : "POST",
									 "url" : sSource,
									 "data" : aoData,
									 "success" : fnCallback
								 	});
								 },
								"aoColumns" : [ {
									"mDataProp" : "code"
								}, {
									"mDataProp" : "bookName"
								}, {
									"mDataProp" : "price"
								}, {
									"mDataProp" : "author"
								}, {
									"mDataProp" : "publish"
								}, {
									"mDataProp" : "description"
								}, {
									"mDataProp" : "status"
								}, {
									"mDataProp" : "lendUser"
								}, {
									"mDataProp" : "id"
								}

								],
								"aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'sClass' : 'center'
								},{
									'aTargets' : [ 1 ],
									'bSortable' : false
								},{
									'aTargets' : [ 2 ],
									'bSortable' : false
								},{
									'aTargets' : [ 3 ],
									'bSortable' : false
								},{
									'aTargets' : [ 4 ],
									'bSortable' : false
								},{
									'aTargets' : [ 5 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='描述信息'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
								},
								{
									'aTargets' : [ 6 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-success'>借出</span>";
										}
										if (sVal == "2") {
											return "<span class='label label-warning'>长期</span>";
										}
										return "<span class='label label-info'>正常</span>";
									}
								},{
									'aTargets' : [ 7 ],
									'bSortable' : false
								},{
									'aTargets' : [ 8 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';

										operate += '<a class="btn2 btn-info" onclick="$.ims.book.prebook('
												+ oObj.aData.id
												+ ')"><i class="icon-book"></i>  预约图书</a>';
										return operate;
									}
								}
								]
							});
		} else {
			this.refreshBookDataTable();
		}
	},
	
	
	/**
	 * 借阅记录详情。
	 * D：删除 
	 */
	initLendRecordDataTable : function(D) {
		if (this.lendRecordDataTable == null) {
			this.lendRecordDataTable = $("#lendrecord_dataTable")
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
								"oLanguage" : {
									"iDisplayLength" : 10,
									"aLengthMenu" : [ 10, 25, 50, 100 ],
									"sLengthMenu" : "每页显示 _MENU_ 条记录",
									"sZeroRecords" : "抱歉， 暂时没有记录",
									"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
									"sInfoEmpty" : "没有数据",
									"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "尾页"
									}
								},
								"bServerSide" : true,
								"sServerMethod" : "POST",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : false,
								"bFilter" : false,
								"sAjaxSource" : $.ims.getContextPath()
										+ '/book/lendrecordlist',
								 "fnServerData" : function(sSource, aoData,
								 fnCallback) {
								 var bookName = $("#s_book").val();
								 var userId = $("#s_userid").val();
								 var overrun = $("#s_overrun").val();
								 if (!!bookName) {
									 aoData.push({
									 "name" : "bookName",
									 "value" : bookName
									 });
									 }
								 if (!!userId) {
								 aoData.push({
								 "name" : "userId",
								 "value" : userId
								 });
								 }
								 if (!!overrun) {
								 aoData.push({
								 "name" : "overrun",
								 "value" : overrun
								 });
								 }
								 $.ajax({
								 "dataType" : 'json',
								 "type" : "POST",
								 "url" : sSource,
								 "data" : aoData,
								 "success" : fnCallback
								 });
								 },
								"aoColumns" : [{
									"mDataProp" : "lendBookName"
								}, {
									"mDataProp" : "lendUserName"
								}, {
									"mDataProp" : "lendTime"
								}, {
									"mDataProp" : "isOverLimit"
								}, {
									"mDataProp" : "returnTime"
								}, {
									"mDataProp" : "money"
								},{
									"mDataProp" : "lendType"
								}, {
									"mDataProp" : "expectTime"
								}, {
									"mDataProp" : "lendId"
								}

								],
								"aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}, 
								{
									'aTargets' : [ 3 ],
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-danger'>已超限</span>";
										}
										return "<span class='label label-success'>未超限</span>";
									}
								},
								{
									'aTargets' : [ 6 ],
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "0") {
											return "<span class='label label-info'>正常</span>";
										}
										return "<span class='label label-warning'>长期</span>";
									}
								},
								{
									'aTargets' : [ 8 ],
									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.book.sendEmail('
												+ oObj.aData.lendId
												+ ')"><i class="icon-envelope"></i> 邮件提醒</a>';
										
										operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
										operate += ' <ul class="dropdown-menu">';
										if (D) {
											operate += '<li><a onclick="$.ims.book.delLendRecord('
												+ oObj.aData.lendId
												+ ')"><i class="icon-trash"></i> 删除</a></li>';
										}
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								}
								]
							});
		} else {
			this.refreshLendRecordDataTable();
		}
	},
	
	
	
	/**
	 * 个人借阅记录详情。
	 */
	initUserLendRecordDataTable : function() {
		if (this.userLendRecordDataTable == null) {
			this.userLendRecordDataTable = $("#userlendrecord_dataTable")
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
								"oLanguage" : {
									"iDisplayLength" : 10,
									"aLengthMenu" : [ 10, 25, 50, 100 ],
									"sLengthMenu" : "每页显示 _MENU_ 条记录",
									"sZeroRecords" : "抱歉， 暂时没有记录",
									"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
									"sInfoEmpty" : "没有数据",
									"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "尾页"
									}
								},
								"bServerSide" : true,
								"sServerMethod" : "POST",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : false,
								"bFilter" : false,
								"sAjaxSource" : $.ims.getContextPath()
										+ '/book/userlendrecordlist',
								 "fnServerData" : function(sSource, aoData,
								 fnCallback) {
								 var bookName = $("#ss_book").val();
								 var userId = $("#ss_userid").val();
								 var overrun = $("#ss_overrun").val();
								 if (!!bookName) {
									 aoData.push({
									 "name" : "bookName",
									 "value" : bookName
									 });
									 }
								 if (!!userId) {
								 aoData.push({
								 "name" : "userId",
								 "value" : userId
								 });
								 }
								 if (!!overrun) {
								 aoData.push({
								 "name" : "overrun",
								 "value" : overrun
								 });
								 }
								 $.ajax({
								 "dataType" : 'json',
								 "type" : "POST",
								 "url" : sSource,
								 "data" : aoData,
								 "success" : fnCallback
								 });
								 },
								"aoColumns" : [ {
									"mDataProp" : "lendBookName"
								}, {
									"mDataProp" : "lendUserName"
								}, {
									"mDataProp" : "lendTime"
								}, {
									"mDataProp" : "isOverLimit"
								}, {
									"mDataProp" : "returnTime"
								}, {
									"mDataProp" : "money"
								}, {
									"mDataProp" : "lendType"
								},{
									"mDataProp" : "expectTime"
								}
								],
								"aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}, 
								{
									'aTargets' : [ 3 ],
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-danger'>已超限</span>";
										}
										return "<span class='label label-success'>未超限</span>";
									}
								},
								{
									'aTargets' : [ 6 ],
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "0") {
											return "<span class='label label-info'>正常</span>";
										}
										return "<span class='label label-warning'>长期</span>";
									}
								}
								]
							});
		} else {
			this.refreshUserLendRecordDataTable();
		}
	},
	
	
	/**
	 * 图书状态。
	 */
	bookStatusInfo : function(state){
		$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+state+"").append("<option value=\"0\">正常</option>");
		$("#"+state+"").append("<option value=\"1\">借出</option>");
		$("#"+state+"").append("<option value=\"2\">长期</option>");
		$("#"+state+"").chosen({
			allow_single_deselect : true
		});
	},
	
	/**
	 * 图书借阅类型。
	 */
	lendTypeInfo : function(lendtype){
		$("#"+lendtype+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+lendtype+"").append("<option value=\"0\">正常借阅</option>");
		$("#"+lendtype+"").append("<option value=\"1\">长期借阅</option>");
		$("#"+lendtype+"").chosen({
			allow_single_deselect : true
		});
	},
	
	/**
	 * 图书是否超限。
	 */
	bookOverrunInfo : function(overrun){
		$("#"+overrun+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+overrun+"").append("<option value=\"0\">未超限</option>");
		$("#"+overrun+"").append("<option value=\"1\">已超限</option>");
		$("#"+overrun+"").chosen({
			allow_single_deselect : true
		});
	},
	
	
	/**
	 * 预约记录。
	 */
	showPreRecord : function(id) {
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/book/prerecord/" + id,
			dataType : "json",
			data : {},
			success : function(json) {
				$("#pre_record").html("");
				var column = '';
				var data = json.aaData;
				for (var i = 0; i < data.length; i++) {
					column = '<tr><td>'
						+ data[i].index
						+ '</td>'
						+ '<td>'
						+ data[i].preBookName
						+ '</td>'
						+ '<td >'
						+ data[i].preTime
						+ '</td>'
						+ '<td >'
						+ data[i].preUserName
						+ '</td>'
						+ '</tr>';
				$("#pre_record").append(column);
				console.info($("#pre_record").html());
				}
			}
		});
		$("#pre_modal").modal('show');
	},
	
	
	/**
	 * 续借图书。
	 */
	renewBook  : function(id){
		var id = $("#hf_id").val();
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/book/renew",
			data : {
				id : id,
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "图书续借成功!",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					$("#book_renew_modal").modal('hide');
					$.ims.book.refreshBookInfoDataTable();
					$.ims.book.refreshLendRecordDataTable();
				}else{
					noty({
						"text" : "续借次数限制!",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
					
				}
			}
		});
	},
	
	/**
	 * 续借图书信息modal弹出。
	 */
	renewBookInfo : function(id){
		$('#book_renew_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#book_renew_header_label").text("续借图书");
		$("#hf_id").val(id);
		$("#book_renew_modal").modal('show');
	},
	
	/**
	 * 归还图书。
	 */
	returnBook : function(id){
		var id = $("#hf_id").val();
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/book/return",
			data : {
				id : id,
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "图书归还成功!",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					$("#book_return_modal").modal('hide');
					$.ims.book.refreshBookInfoDataTable();
					$.ims.book.refreshLendRecordDataTable();
				}
			}
		});
	},
	
	
	/**
	 * 归还图书信息modal弹出。
	 */
	returnBookInfo : function(id) {
		$('#book_return_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#book_return_header_label").text("归还图书");
		$("#hf_id").val(id);
		$("#book_return_modal").modal('show');
		this.queryLendRecord(id);
	},
	
	/**
	 * 借出图书信息modal弹出。
	 */
	lendBookInfo : function(id) {
		$('#book_lend_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#book_lend_header_label").text("借出图书");
		$("#hf_id").val(id);
		this.clearModal();
		$("#book_lend_modal").modal('show');
		this.queryBookInfo(id);
	},
	
	clearModal : function(){
		$.ims.common.setchosenvalue("s_pm","");
		$.ims.common.setchosenvalue("lendtype","");
	},
	
	/**
	 * 借出图书。
	 */
	lendBook : function(){
		var id = $("#hf_id").val();
		var lendtype = $("#lendtype").val();
		var userid = $("#s_pm").val();
		if (lendtype == "") {
			noty({"text" : "借阅方式不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		if (userid == "0") {
			noty({"text" : "借书人不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/book/lend",
			data : {
				id : id,
				userid : userid,
				lendtype :lendtype,
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "图书出借成功!",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					$("#book_lend_modal").modal('hide');
					$.ims.book.refreshBookInfoDataTable();
					$.ims.book.refreshLendRecordDataTable();
				}else{
					noty({
						"text" : "此借书人已超越借阅限制!",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
					
				}
			}
		});
	},
	
	/**
	 * 删除图书记录。
	 */
	deleteBookInfo : function(id){
		var msg = "是否确认删除？";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/book/delete",
	    			data : {
	    				id : id
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json){
    						noty({
    							"text" : "删除记录成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.book.refreshBookInfoDataTable();
	    					$.ims.book.refreshLendRecordDataTable();
	    				}else{
    						noty({
    							"text" : "图书未归还不能删除！",
    							"layout" : "top",
    							"type" : "warning",
    							"timeout" : "2000"
    						});
	    				}
	    			}
	    		});
            }
		}); 
	},
	
	
	/**
	 * 删除借阅记录。
	 */
	delLendRecord : function(lendId){
		var msg = "是否确认删除？";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/book/dellendrecord",
	    			data : {
	    				lendId : lendId
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json){
    						noty({
    							"text" : "删除借阅记录成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.book.refreshLendRecordDataTable();
	    				}else{
    						noty({
    							"text" : "图书未归还不能删除借阅记录！",
    							"layout" : "top",
    							"type" : "warning",
    							"timeout" : "2000"
    						});
	    				}
	    			}
	    		});
            }
		}); 
	},
	
	
	
	/**
	 * 归还日邮件提醒。
	 */
	sendEmail : function(lendId){
		var msg = "是否确认发送提醒邮件？";
		bootbox.confirm(msg, function(result){
            if(result){
	            	$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/book/sendemail",
					data : {
						lendId : lendId
					},
					dataType : "json",
					success : function(json) {
						if(json == 1){
							noty({
								"text" : "邮件提醒成功！",
								"layout" : "top",
								"type" : "success",
								"timeout" : "2000"
							});
							$.ims.book.refreshLendRecordDataTable();
						}
						if (json == 2){
							noty({
								"text" : "此用户未设置邮箱！",
								"layout" : "top",
								"type" : "error",
								"timeout" : "2000"
							});
						}
						if (json == 3){
							noty({
								"text" : "此用户已归还图书！",
								"layout" : "top",
								"type" : "warning",
								"timeout" : "2000"
							});
						}
					}
				});
            }
		});
	},
	
	
	/**
	 * 预约图书。
	 */
	prebook : function(id){
		var msg = "是否确认预约本书？";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/book/prebook",
	    			data : {
	    				id : id
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json == 1){
    						noty({
    							"text" : "预约图书成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.book.initBookDataTable();
	    				}
	    				if(json == 2){
    						noty({
    							"text" : "系统未设置图书管理员！",
    							"layout" : "top",
    							"type" : "error",
    							"timeout" : "2000"
    						});
	    				}
	    				if(json == 3){
    						noty({
    							"text" : "未分配图书管理员或其未设置邮箱！",
    							"layout" : "top",
    							"type" : "warning",
    							"timeout" : "2000"
    						});
	    				}
	    				if(json == 0){
    						noty({
    							"text" : "预约图书失败！",
    							"layout" : "top",
    							"type" : "error",
    							"timeout" : "2000"
    						});
	    				}
	    			}
	    		});
            }
		}); 
	},
	
	
	/**
	 * 导出图书信息Excel。
	 */
	exportBookReport:function(){
//		if($("#bookinfo_dataTable").val()=='')
//			noty({"text":"没有图书信息","layout":"top","type":"success","timeout":"2000"});
//		else
			window.open ($.ims.getContextPath() + "/book/export");
	},
	
	/**
	 * 新建图书信息modal弹出。
	 */
	createBookInfo : function(id) {
		$('#book_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#book_modal_header_label").text("新增图书");
		this.otype = 0;
		$("#code").val("");
		$("#bookname").val("");
		$("#price").val("");
		$("#author").val("");
		$("#publish").val("");
		$("#description").val("");
		$("#book_edit_modal").modal('show');
	},

	/**
	 * 修改图书信息modal弹出。
	 */
	editBookInfo : function(id) {
		$('#book_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#book_modal_header_label").text("编辑图书");
		this.otype = 1;
		$("#hf_id").val(id);
		$("#book_edit_modal").modal('show');
		this.queryBookInfo(id);
	},
	
	

	/**
	 * 新建和修改图书信息。
	 */
	saveOrUpdateBook : function() {
		var code = $("#code").val();
		if (code == "") {
			noty({"text" : "图书编码不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var bookname = $("#bookname").val();
		if (bookname == "") {
			noty({"text" : "图书名称不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var price = $("#price").val();
//		var re=/^([1-9]\d*|0|)\.\d{2}$/;

		if (price == "") {
			noty({"text" : "图书价格不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		if(isNaN(price)){
			noty({"text" : "价格格式不正确！必须为数字。 ","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		if(!parseFloat(price)){
			noty({"text" : "价格格式不正确！不可以是负数。 ","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}

		var author = $("#author").val();
		if (author == "") {
			noty({"text" : "作者不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var publish = $("#publish").val();
		if (publish == "") {
			noty({"text" : "出版社不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}		
		var description = $("#description").val();
		if (this.otype == 0) {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/book/create",
				dataType : "json",
				data : {
					code : code,
					bookName : bookname,
					price : price,
					author : author,
					publish:publish,
					description : description
				},
				success : function(json) {
					if (json) {
						noty({
							"text" : "新增图书成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$("#book_edit_modal").modal('hide');
					}
					else{
						noty({
							"text" : "新增图书编码不可重复！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$.ims.book.refreshBookInfoDataTable();
					$.ims.book.refreshLendRecordDataTable();
				}
			});
		} else if (this.otype == 1) {
			var id = $("#hf_id").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/book/update",
				data : {
					id : id,
					code : code,
					bookName : bookname,
					price : price,
					author : author,
					publish:publish,
					description : description
				},
				dataType : "json",
				success : function(json) {
					if (json) {
						noty({
							"text" : "修改图书信息成功!",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					}
					$("#book_edit_modal").modal('hide');
					$.ims.book.refreshBookInfoDataTable();
					$.ims.book.refreshLendRecordDataTable();
				}
			});
		}
	},

	
	
	/***
	 * 查找图书信息。
	 */
	queryBookInfo : function(id){
		$("#code").val("");
		$("#bookname").val("");
		$("#price").val("");
		$("#author").val("");
		$("#publish").val("");
		$("#description").val("");
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/book/query/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#code").val(json.code);
					$("#lend_code").val(json.code);
					$("#bookname").val(json.bookName);
					$("#lend_book").val(json.bookName);
					$("#price").val(json.price);
					$("#author").val(json.author);
					$("#publish").val(json.publish);
					$("#description").val(json.description);
				}
			}
		});
	},

	
	
	/***
	 * 查找借阅记录。
	 */
	queryLendRecord : function(id){
		$("#returnname").val("");
		$("#lenduser").val("");
		$("#lendtime").val("");
		$("#money").val("");
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/book/querylendrecord/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#returnname").val(json.lendBookName);
					$("#lenduser").val(json.lendUserName);
					$("#lendtime").val(json.lendTime);
					$("#money").val(json.money);
				}
			}
		});
	},
	
	
	
	/**
	 * 查找所有用户
	 */
	findAllBook : function(callback, book){
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/book/booklist",
			dataType : "json",
			async : false,
			success : function(json) {
				$("#"+book+"").append("<option value=\"" + "0" + "\">" + "</option>");
				for (var i = 0; i < json.length; i++) {
					$("#"+book+"").append("<option value=\"" + json[i].id+ "\">" + json[i].code+" "+ json[i].bookName + "</option>");
				}
				if($.isFunction(callback)) callback();
			}
		});
	},
	
	
}
