jQuery.ims.meeting = {

	roomInfoDataTable : null,
	reserveDataTable : null,
	otype : 0, // 操作类型：0新增 1修改
	
	/**
	 * 刷新会议室信息。
	 * 
	 */
	refreshRoomInfoDataTable : function() {
		if (this.roomInfoDataTable != null) {
			var oSettings = this.roomInfoDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.roomInfoDataTable.fnDraw(oSettings);
		}
	},
	/**
	 * 刷新预约信息。
	 * 
	 */
	refreshReserveDataTable : function() {
		if (this.reserveDataTable != null) {
			var oSettings = this.reserveDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.reserveDataTable.fnDraw(oSettings);
		}
	},

	/**
	 * 会议室管理信息详情。
	 */
	initRoomInfoDataTable : function(op) {
		if (this.roomInfoDataTable == null) {
			this.roomInfoDataTable = $("#roominfo_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/roomlist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var roomname = $("#s_name").val();
								 var roomseat = $("#s_seat").val();
								 var roomstatus = $("#s_status").val();
								
								 if (!!roomname) {
								 aoData.push({
								 "name" : "roomname",
								 "value" : roomname
								 });
								 }
								 if (!!roomseat) {
								 aoData.push({
								 "name" : "roomseat",
								 "value" : roomseat
								 });
								 }
								 if (!!roomstatus) {
								 aoData.push({
								 "name" : "roomstatus",
								 "value" : roomstatus
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
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "roomSeat"
								}, {
									"mDataProp" : "materiel"
								}, {
									"mDataProp" : "roomAddress"
								}, {
									"mDataProp" : "roomDescription"
								}, 
//								{
//									"mDataProp" : "roomStatus"
//								}, {
//									"mDataProp" : "roomCreateTime"
//								}, 
								{
									"mDataProp" : "id"
								}],
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : true
								},{
									'aTargets' : [ 1 ],
									'bSortable' : false
								},{
									'aTargets' : [ 2 ],
									'bSortable' : false
								},{
									'aTargets' : [ 3 ],
									'bSortable' : false,
								},{
									'aTargets' : [ 4 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='描述信息'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
								}, 
//								{
//									'aTargets' : [ 5 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										opstatus = sVal;
//										if (sVal == "1") {
//											return "<span class='label label-success'>预约中</span>";
//										}
//										if (sVal == "2") {
//											return "<span class='label label-warning'>使用中</span>";
//										}
//										if (sVal == "3") {
//											return "<span class='label label-primary'>关闭中</span>";
//										}
//										return "<span class='label label-info'>空闲中</span>";
//									}
//								},{
//									'aTargets' : [ 6 ],
//									'bSortable' : false,
//								},
								{
									'aTargets' : [ 5 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										if (op == 0){
										operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
												operate += '<li><a onclick="$.ims.meeting.editRoom('
													+ oObj.aData.id
													+ ')"><i class="icon-wrench"></i> 修改</a></li>';
												operate += '<li><a onclick="$.ims.meeting.deleteRoom('
													+ oObj.aData.id
													+ ')"><i class="icon-trash"></i> 删除</a></li>';
										}
										if (op == 1){
											operate += '<a class="btn2 btn-info" href="'+$.ims.getContextPath()+'/meeting/reserveinfo/index?rm='+oObj.aData.id+'"><i class="icon-zoom-in"></i> 预约</a>';

										}
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshRoomInfoDataTable();
		}
	},
	
	
	/**
	 * 会议室状态。
	 */
	rommStatus : function(state){
		$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+state+"").append("<option value=\"0\">空闲中</option>");
		$("#"+state+"").append("<option value=\"1\">预约中</option>");
		$("#"+state+"").append("<option value=\"2\">使用中</option>");
		$("#"+state+"").append("<option value=\"3\">关闭中</option>");
		$("#"+state+"").chosen({
 			enable_split_word_search : false,
			allow_single_deselect : true
		});
	},
	
	/***
	 * 查找会议室信息。
	 */
	queryRoom : function(id){
		$("#roomname").val("");
		$("#seat").val("");
		$("#address").val("");
		$.ims.common.setchosenvalue("materiel","");
		$("#description").val("");
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/meeting/roomquery/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#roomname").val(json.roomName);
					$("#seat").val(json.roomSeat);
					$("#address").val(json.roomAddress);
					var ids = new Array();
					if (json.materiel != null){
						for (i= 0; i<json.materiel.length; i++){
							ids[i] = json.materiel[i].id;
						}
					}
//					alert(ids);
					$.ims.common.setchosenvalue("materiel",ids);
					$("#description").val(json.roomDescription);
					$("#status").val(json.roomStatus);
				}
			}
		});
	},

	/**
	 * 新建会议室modal弹出。
	 */
	createRoom : function(id) {
		$('#room_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#room_modal_header_label").text("新建会议室");
		this.otype = 0;
		$("#roomname").val("");
		$("#roomname").removeClass('valid-error');
		$("#seat").val("");
		$("#seat").removeClass('valid-error');
		$("#address").val("");
		$("#address").removeClass('valid-error');
		$.ims.common.setchosenvalue("materiel","");
		$("#description").val("");
		$("#room_edit_modal").modal('show');
	},
	
	
	/**
	 * 修改会议室modal弹出。
	 */
	editRoom : function(id) {
		$('#room_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#room_modal_header_label").text("编辑会议室");
		this.otype = 1;
		$("#hf_id").val(id);
		$("#room_edit_modal").modal('show');
		this.queryRoom(id);
	},
	
	saveRoom : function() {
		var flag = true;
		if(!this.validBase(flag)){
			return;		//验证失败
		}
		
		var roomname = $("#roomname").val();
		var roomseat = $("#seat").val();
		var roomaddress = $("#address").val();
//		console.info($("#materiel").val());
		var materiel = $("#materiel").val().toString();
		var description = $("#description").val();
		if (this.otype == 0) {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/meeting/roomcreate",
				dataType : "json",
				data : {
					roomName : roomname,
					roomSeat : roomseat,
					roomAddress : roomaddress,
					roommateriel : materiel,
					roomDescription : description
				},
				success : function(json) {
					if (json == 1) {
						noty({
							"text" : "新增会议室成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$("#room_edit_modal").modal('hide');
					}
					if (json == 2){
						noty({
							"text" : "会议室名称不可重复！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					if (json == 0){
						noty({
							"text" : "新建会议室失败！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$.ims.meeting.refreshRoomInfoDataTable();
				}
			});
		} else if (this.otype == 1) {
			var id = $("#hf_id").val();
			var roomStatus = $("#status").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/meeting/roomupdate",
				data : {
					id : id,
					roomName : roomname,
					roomSeat : roomseat,
					roomAddress : roomaddress,
					roommateriel : materiel,
					roomDescription : description,
					roomStatus : roomStatus
				},
				dataType : "json",
				success : function(json) {
					if (json == 1) {
						noty({
							"text" : "修改会议室信息成功!",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					}
					if (json == 2)	{
						noty({
							"text" : "会议室名称不可重复！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					if (json == 3){
						noty({
							"text" : "会议室已被预约！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					if (json == 0){
						noty({
							"text" : "更新会议室失败！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$("#room_edit_modal").modal('hide');
					$.ims.meeting.refreshRoomInfoDataTable();
				}
			});
		}
	},
	
	
	// 数据验证
	validBase : function(flag){

		// 非空验证
		$(".required").each(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        	flag = false;
	        }
		});
		
		$("#materiel").each(function(){
			var materiel = $("#materiel").val();
			if (materiel == "" || materiel == null){
	        	$("#materiel").next().addClass('valid-error');
	        	flag = false;
			}else{
	        	$("#materiel").next().removeClass('valid-error');
			}
		});
		
		//数字验证
		$(".number").each(function(){
			var r = /^\+?[1-9][0-9]*$/;
			if(r.test($(this).val())){
	    		$(this).removeClass('valid-error');
	    	}else{
	    		$(this).addClass('valid-error');
	    		flag = false;
	    	}
		});
		
		return flag;
	},
	
	
	/**
	 * 删除会议室。
	 */
	deleteRoom : function(id){
		var msg = "<font color='red'>是否确认删除？</font><font color='#0000FF'>*此操作会同时删除本会议室下的预约历史记录！</font>";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/meeting/roomdelete",
	    			data : {
	    				id : id
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json){
    						noty({
    							"text" : "删除会议室成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.meeting.refreshRoomInfoDataTable();
	    				}else{
    						noty({
    							"text" : "会议室已使用不能删除！",
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
	 * 查询指定预约。
	 */
	queryReserve : function (id){
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/meeting/reservequery/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#roomName").val(json.roomName);
					$("#meetingName").val(json.meetingName);
					$("#startDate").val(json.meetingStartDate);
					$("#startTime").val(json.meetingStartTime);
					$("#endTime").val(json.meetingEndTime);
					$.ims.common.setchosenvalue("compere",json.compere);
					$.ims.common.setchosenvalue("registrar",json.registrar);
					var ids =json.attendee.split(",");
					$('#s_attendee').val(ids);
					$('#s_attendee').multiselect("refresh");
					$("#attendeeName").val(json.attendeeName);
					$.ims.common.setchosenvalue("meetingType",json.meetingType);
					$("#meetingDescription").val(json.meetingDescription);
					$("#meetingSummary").val(json.meetingSummary);
					$("#meetingSummaryFile").val(json.meetingSummaryFile);
					var serve =json.meetingServe.split(",");
					for (var i in serve){
						$("#serve1").attr("checked",serve[0]==1?'checked':false);
						$("#serve2").attr("checked",serve[1]==1?'checked':false);
					}
					if(json.meetingFile != ""){
						var meetingFile = json.meetingFile.split(',');
						var filehtml = '';
						for(var i = 0; i < meetingFile.length; i++){
							var defaultname = meetingFile[i];
							var filename = defaultname.substring(13);
							filehtml += '<a download="'+filename+'" href="'+$.ims.getContextPath()+'/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a>'
							+'&nbsp;&nbsp;&nbsp;<a onclick="$.ims.meeting.delfile(\''+ defaultname+ '\','+id+','+0+')">删除</a><br />';
						}
						$("#meetingfile").html(filehtml);
					}
					if(json.meetingSummaryFile != ""){
						var meetingSummaryFile = json.meetingSummaryFile.split(',');
						var filehtml = '';
						for(var i = 0; i < meetingSummaryFile.length; i++){
							var defaultname = meetingSummaryFile[i];
							var filename = defaultname.substring(13);
							filehtml += '<a download="'+filename+'" href="'+$.ims.getContextPath()+'/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a>'
							+'&nbsp;&nbsp;&nbsp;<a onclick="$.ims.meeting.delfile(\''+ defaultname+ '\','+id+','+1+')">删除</a><br />';
						}
						$("#summaryfile").html(filehtml);
					}
				}
			}
		});
	},
	
	
	delfile : function (filename,id,op){
		var tag=confirm("确定删除?");
		if (tag == true){
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/meeting/delfile",
				dataType : "json",
				data : {
					id : id,
					filename :filename,
					op : op
				},
				success : function(json) {
					if (json) {
						if(json.meetingFile != "" && json.meetingFile != null){
							var meetingFile = json.meetingFile.split(',');
							var filehtml = '';
							for(var i = 0; i < meetingFile.length; i++){
								var defaultname = meetingFile[i];
								var filename = defaultname.substring(13);
								filehtml += '<a download="'+filename+'" href="'+$.ims.getContextPath()+'/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a>'
								+'&nbsp;&nbsp;&nbsp;<a onclick="$.ims.meeting.delfile(\''+ defaultname+ '\','+id+','+op+')">删除</a><br />';
							}
							$("#meetingfile").html(filehtml);
						}else{
							$("#meetingfile").html("");
						};
						if(json.meetingSummaryFile != "" && json.meetingSummaryFile != null){
							var meetingSummaryFile = json.meetingSummaryFile.split(',');
							var filehtml = '';
							for(var i = 0; i < meetingSummaryFile.length; i++){
								var defaultname = meetingSummaryFile[i];
								var filename = defaultname.substring(13);
								filehtml += '<a download="'+filename+'" href="'+$.ims.getContextPath()+'/upload/meeting/'+defaultname.substring(0,13)+defaultname.substring(defaultname.lastIndexOf('.'))+'">'+filename+'</a>'
								+'&nbsp;&nbsp;&nbsp;<a onclick="$.ims.meeting.delfile(\''+ defaultname+ '\','+id+','+op+')">删除</a><br />';
							}
							$("#summaryfile").html(filehtml);
						}else{
							$("#summaryfile").html("");
						};
					}
				}
			});
		}else{
			return false;
		}
	},
	
	/**
	 * 预约状态。 1：已提交。 2：已审核。 3：已开始。 4：已结束。
	 */
	reserveStatus : function(state){
		$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+state+"").append("<option value=\"1\">已提交</option>");
		$("#"+state+"").append("<option value=\"2\">已审核</option>");
		$("#"+state+"").append("<option value=\"3\">已开始</option>");
		$("#"+state+"").append("<option value=\"4\">已结束</option>");
		$("#"+state+"").chosen({
 			enable_split_word_search : false,
			allow_single_deselect : true
		});
	},
	
	
	/**
	 * 新建预约modal弹出。
	 */
	createReserve : function(startDate,startTime, endTime) {
		
		$("#reserve_modal_header_label").text("新建会议室预约");
		this.otype = 0;
		$("#startDate").val(startDate);
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
		
		$("#meetingName").val("");
		$("#meetingName").removeClass('valid-error');
		$.ims.common.setchosenvalue("compere","");
		$.ims.common.setchosenvalue("registrar","");
		$.ims.common.setchosenvalue("meetingType","");
		$('#s_attendee').val("");
		$('#s_attendee').multiselect("refresh");
    	$("#s_attendee").next().removeClass('valid-error');
		$("#meetingDescription").val("");
		$("#meetingfile").html("（空）");
		$("#multifile").val("");
		
		$("#reserve_edit_modal").modal('show');
	},
	
	/**
	 * 更新会议纪要modal弹出。
	 */
	updateSummary : function (id){
		$("#summary_modal_header_label").text("更新会议纪要");
		$("#hf_id").val(id);
		$("#summary_edit_modal").modal('show');
		$("#summaryfile").html("（空）");
		$("#multifile-list ul").html("");
		$("#multifile-list").hide();
		$("#meetingSummary").removeClass('valid-error');
		$("#multifile").removeClass('valid-error');
		this.queryReserve(id);
	},
	
	/**
	 * 更新预约modal弹出。
	 */
	updateReserve : function (id,roomId){
		$("#reserve_modal_header_label").text("更新会议室预约");
		this.otype = 1;
		$("#hf_id").val(id);
		$("#roomId").val(roomId);
		$("#reserve_edit_modal").modal('show');
		$("#meetingfile").html("（空）");
		$("#multifile-list ul").html("");
		$("#multifile-list").hide();
		this.queryReserve(id);

	},
	
	/**
	 * 拖拽预约modal弹出。
	 */
	dragReserve : function (id ,startDate, startTime , endTime){
		$("#reserve_modal_header_label").text("更新会议室预约");
		this.otype = 1;
		$("#hf_id").val(id);
		$("#reserve_edit_modal").modal('show');
		$("#meetingfile").html("（空）");
		this.queryReserve(id);

		$("#startDate").val(startDate);
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
		$("#multifile-list ul").html("");
		$("#multifile-list").hide();
	},
	
	updateTime : function() {
		var id = $("#hf_id").val();
		var roomId = $("#roomId").val();
		var startDate = $("#startDate").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();

			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/meeting/updatetime",
				dataType : "json",
				data : {
					id: id,
					roomId : roomId,
					startDate : startDate,
					startTime :startTime,
					endTime : endTime,
				},
				success : function(json) {
					if (json) {
						noty({
							"text" : "修改预约信息成功!",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					}else{
						noty({
							"text" : "预约时间不可选择！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$("#reserve_edit_modal").modal('hide');
				}
			});
			this.refreshReserveDataTable();
	},
	
	
	// 数据验证
	validReserve : function(flag){

		// 非空验证
		$(".required").each(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        	flag = false;
	        }
		});
		
		
		$(".required2").each(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		
		$("#compere").each(function(){
			var compere = $("#compere").val();
			if (compere == "" || compere == "0"){
	        	$("#compere").next().addClass('valid-error');
	        	flag = false;
			}else{
	        	$("#compere").next().removeClass('valid-error');
			}
		});
		
		$("#registrar").each(function(){
			var registrar = $("#registrar").val();
			if (registrar == "" || registrar == "0"){
	        	$("#registrar").next().addClass('valid-error');
	        	flag = false;
			}else{
	        	$("#registrar").next().removeClass('valid-error');
			}
		});
		
		return flag;
	},
	
	
	
	/**
	 * 我召集的会议详情。
	 */
	initReserveDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								//"bFilter" : false,
								"fnDrawCallback" : function(oSettings) {
									$('[rel="popover"],[data-rel="popover"]').popover();
								},
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/personallist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var reserveName = $("#s_meetingname").val();
								 var compere = $("#s_compere").val();
								 var startDate = $("#s_startDate").val();
								 var meetingStatus = $("#s_status").val();
								
								 if (!!reserveName) {
								 aoData.push({
								 "name" : "reserveName",
								 "value" : reserveName
								 });
								 }
								 if (!!compere) {
								 aoData.push({
								 "name" : "compere",
								 "value" : compere
								 });
								 }
								 if (!!startDate) {
								 aoData.push({
								 "name" : "startDate",
								 "value" : startDate
								 });
								 }
								 if (!!meetingStatus) {
								 aoData.push({
								 "name" : "meetingStatus",
								 "value" : meetingStatus
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "meetingStartTime"
								}, {
									"mDataProp" : "meetingEndTime"
								}, {
									"mDataProp" : "meetingStatus"
								}, 
//								{
//									"mDataProp" : "flag"
//								}, 
//								{
//									"mDataProp" : "inform"
//								}, 
								{
									"mDataProp" : "id"
								}],
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : false
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
									'bSortable' : true
								}, {
									'aTargets' : [ 5 ],
									'bSortable' : false
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-success'>已提交</span>";
										}
										if (sVal == "2") {
											return "<span class='label label-warning'>已审核</span>";
										}
										if (sVal == "3") {
											return "<span class='label label-important'>已开始</span>";
										}
										if (sVal == "4") {
											return "<span class='label'>已结束</span>";
										}
										if (sVal == "5") {
											return "<span class='label label-info'>已作废</span>";
										}
									}
								},
//								{
//									'aTargets' : [ 7 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										flag = sVal;
//										if (sVal == "0") {
//											return "<span class='label'>未生效</span>";
//										}
//										if (sVal == "1") {
//											return "<span class='label label-info'>已生效</span>";
//										}
//									}
//								},
//								{
//									'aTargets' : [ 8 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										inform = sVal;
//										if (sVal == "0") {
//											return "<span class='label'>未设通知</span>";
//										}
//										if (sVal == "1") {
//											return "<span class='label label-info'>通知开会</span>";
//										}
//										if (sVal == "2") {
//											return "<span class='label label-info'>会议取消</span>";
//										}
//									}
//								},
								{
									'aTargets' : [ 7 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';

//											if (flag == 1 && opstatus == 2) {
//												operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
//												operate += ' <ul class="dropdown-menu">';
//												operate += '<li><a onclick="$.ims.meeting.sendEmail('
//													+ oObj.aData.id
//													+ ')"><i class="icon-wrench"></i> 邮件提醒</a></li>';
//											}
											if (oObj.aData.flag == 0) {
												operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
												operate += ' <ul class="dropdown-menu">';
												operate += '<li><a onclick="$.ims.meeting.reserveDelete('
													+ oObj.aData.id
													+ ')"><i class="icon-trash"></i> 删除</a></li>';
											}
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	/**
	 * 预约提交审核。
	 */
	reserveSubmit : function (id){
		var msg = "是否确认提交预约？";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/meeting/reservesubmit",
	    			data : {
	    				id : id
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json){
    						noty({
    							"text" : "会议室预约提交成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.meeting.refreshReserveDataTable();
	    				}else{
    						noty({
    							"text" : "会议室预约提交失败！",
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
	 * 删除预约。
	 */
	reserveDelete : function (id) {
		var msg = "是否确认删除？";
		bootbox.confirm(msg, function(result){
            if(result){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/meeting/reservedelete",
	    			data : {
	    				id : id
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				if(json){
    						noty({
    							"text" : "删除会议室预约成功！",
    							"layout" : "top",
    							"type" : "success",
    							"timeout" : "2000"
    						});
	    					$.ims.meeting.refreshReserveDataTable();
	    				}else{
    						noty({
    							"text" : "预约已锁定无法删除！",
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
	 * 预约管理详情。
	 */
	reserveManageDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/reservelist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var reserveName = $("#s_meetingname").val();
								 var compere = $("#s_compere").val();
								 var startDate = $("#s_startDate").val();
								 var meetingStatus = $("#s_status").val();
								
								 if (!!reserveName) {
								 aoData.push({
								 "name" : "reserveName",
								 "value" : reserveName
								 });
								 }
								 if (!!compere) {
								 aoData.push({
								 "name" : "compere",
								 "value" : compere
								 });
								 }
								 if (!!startDate) {
								 aoData.push({
								 "name" : "startDate",
								 "value" : startDate
								 });
								 }
								 if (!!meetingStatus) {
								 aoData.push({
								 "name" : "meetingStatus",
								 "value" : meetingStatus
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "registrarName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "meetingStartTime"
								}, {
									"mDataProp" : "meetingEndTime"
								}, {
									"mDataProp" : "meetingStatus"
								}, 
//								{
//									"mDataProp" : "flag"
//								}, 
								{
									"mDataProp" : "meetingSummaryFile"
								},{
									"mDataProp" : "id"
								}],
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : false
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
								}, {
									'aTargets' : [ 5 ],
									'bSortable' : true
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false
								},{
									'aTargets' : [ 7 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										opstatus = sVal;
										if (sVal == "1") {
											return "<span class='label label-success'>已提交</span>";
										}
										if (sVal == "2") {
											return "<span class='label label-warning'>已审核</span>";
										}
										if (sVal == "3") {
											return "<span class='label label-important'>已开始</span>";
										}
										if (sVal == "4") {
											return "<span class='label'>已结束</span>";
										}
										if (sVal == "5") {
											return "<span class='label'>已作废</span>";
										}
									}
								},
//								{
//									'aTargets' : [ 8 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										flag = sVal;
//										if (sVal == "0") {
//											return "<span class='label'>未生效</span>";
//										}
//										if (sVal == "1") {
//											return "<span class='label label-info'>已生效</span>";
//										}
//									}
//								},
								
								{   
									'aTargets' : [ 8 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
									summary = sVal;	
									if (oObj.aData.meetingSummaryFile == "") {
										return "<span class='label'>未纪要</span>";
									}
									else{
										return "<span class='label label-info'>已纪要</span>";
									}
										
									}
									
								},{
									'aTargets' : [ 9 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';

										if(summary != ""){ //&& flag == 1
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
										if (opstatus != 3 ){
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
										}
										if (opstatus == 1){
											operate += '<li><a onclick="$.ims.meeting.reserveCheck('
												+ oObj.aData.id
												+ ',1)"><i class="icon-ok"></i> 通过</a></li>';
											operate += '<li><a onclick="$.ims.meeting.updateReserve('
												+ oObj.aData.id+','+oObj.aData.roomId 
												+ ')"><i class="icon-wrench"></i> 修改</a></li>';
											operate += '<li><a onclick="$.ims.meeting.reserveDelete('
												+ oObj.aData.id
												+ ')"><i class="icon-trash"></i> 删除</a></li>';
										}
										if (opstatus == 2){
											operate += '<li><a onclick="$.ims.meeting.sendEmail('
												+ oObj.aData.id
												+ ')"><i class="icon-wrench"></i> 邮件提醒</a></li>';
											operate += '<li><a onclick="$.ims.meeting.reserveCheck('
												+ oObj.aData.id
												+ ',0)"><i class="icon-trash"></i> 退回</a></li>';
										}
										if (opstatus == 4 && summary == ""){
											operate += '<li><a onclick="$.ims.meeting.remindSummary('
												+ oObj.aData.id
												+ ')"><i class="icon-trash"></i> 提醒纪要</a></li>';
										}
//												if (opstatus == 4 && flag == 0){
//													operate += '<li><a onclick="$.ims.meeting.reserveDelete('
//														+ oObj.aData.id
//														+ ')"><i class="icon-trash"></i> 删除</a></li>';
//												}
												
												
												
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	/**
	 * 提醒纪要。
	 */
	remindSummary : function (id){
		var msg = "是否确认发送撰写会议纪要邮件提醒？";
		bootbox.confirm(msg, function(result){
            if(result){
	            	$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/meeting/remindsummary",
					data : {
						id : id
					},
					dataType : "json",
					success : function(json) {
						if(json == 1){
							noty({
								"text" : "会议纪要提醒邮件发送成功！",
								"layout" : "top",
								"type" : "success",
								"timeout" : "2000"
							});
						}
						if (json == 2){
							noty({
								"text" : "此用户未设置邮箱！",
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
	 * 邮件提醒。
	 */
	sendEmail : function (id){
		var msg = "是否确认发送会议提醒邮件？";
		bootbox.confirm(msg, function(result){
            if(result){
	            	$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/meeting/sendemail",
					data : {
						id : id
					},
					dataType : "json",
					success : function(json) {
						if(json == 1){
							noty({
								"text" : "会议提醒邮件发送成功！",
								"layout" : "top",
								"type" : "success",
								"timeout" : "2000"
							});
						}
						if (json == 2){
							noty({
								"text" : "此用户未设置邮箱！",
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
	 * 管理审核预约。
	 */
	reserveCheck : function (id,op) {
		var msg = op == 1?"是否确认通过审核？":"是否退回预约？";
		bootbox.confirm(msg, function(result){
            if(result){
            	if (op == 1){
					$.ajax({
		    			type : "post",
		    			url : $.ims.getContextPath() + "/meeting/reservecheck",
		    			data : {
		    				id : id,
		    				op : op
		    			},
		    			dataType : "json",
		    			success : function(json) {
		    				if(json){
	    						noty({
	    							"text" : "预约审核通过！",
	    							"layout" : "top",
	    							"type" : "success",
	    							"timeout" : "2000"
	    						});
	    						$.ims.meeting.refreshReserveDataTable();
		    				}else{
	    						noty({
	    							"text" : "预约审核未通过！",
	    							"layout" : "top",
	    							"type" : "warning",
	    							"timeout" : "2000"
	    						});
		    				}
		    			}
		    		});
            	}
            	if (op == 0){
					$.ajax({
		    			type : "post",
		    			url : $.ims.getContextPath() + "/meeting/reservecheck",
		    			data : {
		    				id : id,
		    				op : op
		    			},
		    			dataType : "json",
		    			success : function(json) {
		    				if(json){
	    						noty({
	    							"text" : "预约退回成功！",
	    							"layout" : "top",
	    							"type" : "success",
	    							"timeout" : "2000"
	    						});
	    						$.ims.meeting.refreshReserveDataTable();
		    				}else{
	    						noty({
	    							"text" : "预约无法退回！",
	    							"layout" : "top",
	    							"type" : "warning",
	    							"timeout" : "2000"
	    						});
		    				}
		    			}
		    		});
	            }
            }
		}); 
	},
	
	/**
	 * 已参加会议。
	 */
	reserveHistoryDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/historylist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var reserveName = $("#s_meetingname").val();
								 var compere = $("#s_compere").val();
								 var startDate = $("#s_startDate").val();
								 var meetingStatus = $("#s_status").val();
								
								 if (!!reserveName) {
								 aoData.push({
								 "name" : "reserveName",
								 "value" : reserveName
								 });
								 }
								 if (!!compere) {
								 aoData.push({
								 "name" : "compere",
								 "value" : compere
								 });
								 }
								 if (!!startDate) {
								 aoData.push({
								 "name" : "startDate",
								 "value" : startDate
								 });
								 }
								 if (!!meetingStatus) {
								 aoData.push({
								 "name" : "meetingStatus",
								 "value" : meetingStatus
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "meetingStartTime"
								}, {
									"mDataProp" : "meetingEndTime"
								}, {
									"mDataProp" : "registrarName"
								},
//								{
//									"mDataProp" : "meetingStatus"
//								}, 
								{
									"mDataProp" : "id"
								}],
								
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : false
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
									'bSortable' : true
								}, {
									'aTargets' : [ 5 ],
									'bSortable' : false
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false
								},
//								{
//									'aTargets' : [ 7 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										opstatus = sVal;
//										if (sVal == "1") {
//											return "<span class='label label-success'>已提交</span>";
//										}
//										if (sVal == "2") {
//											return "<span class='label label-warning'>已审核</span>";
//										}
//										if (sVal == "3") {
//											return "<span class='label label-important'>已开始</span>";
//										}
//										if (sVal == "4") {
//											return "<span class='label'>已结束</span>";
//										}
//									}
//								},
								{
									'aTargets' : [ 7 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';

//										if (oObj.aData.identifying == 1){
//											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
//											operate += ' <ul class="dropdown-menu">';
//											operate += '<li><a onclick="$.ims.meeting.updateSummary('
//												+ oObj.aData.id
//												+ ')"><i class="icon-wrench"></i> 会议纪要</a></li>';
//											
//										}
//										if (flag == 0){
//											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
//											operate += ' <ul class="dropdown-menu">';
//												operate += '<li><a onclick="$.ims.meeting.reserveDelete('
//													+ oObj.aData.id
//													+ ')"><i class="icon-wrench"></i> 删除</a></li>';
//										}	
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	
	/**
	 * 预约状态。 1：已提交。 2：已审核。 3：已开始。 4：已结束。
	 */
	reserveStatus : function(state){
		$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
		$("#"+state+"").append("<option value=\"1\">已提交</option>");
		$("#"+state+"").append("<option value=\"2\">已审核</option>");
		$("#"+state+"").append("<option value=\"3\">已开始</option>");
		$("#"+state+"").append("<option value=\"4\">已结束</option>");
		$("#"+state+"").append("<option value=\"5\">已作废</option>");
		$("#"+state+"").chosen({
 			enable_split_word_search : false,
			allow_single_deselect : true
		});
	},
	
	/**
	 * 会议纪要。
	 */
	reserveSummaryDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/summarylist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var reserveName = $("#s_meetingname").val();
								 var compere = $("#s_compere").val();
								 var summaryDate = $("#s_summaryDate").val();
								
								 if (!!reserveName) {
								 aoData.push({
								 "name" : "reserveName",
								 "value" : reserveName
								 });
								 }
								 if (!!compere) {
								 aoData.push({
								 "name" : "compere",
								 "value" : compere
								 });
								 }
								 if (!!summaryDate) {
								 aoData.push({
								 "name" : "summaryDate",
								 "value" : summaryDate
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "summaryCreateTime"
								}, {
									"mDataProp" : "registrarName"
								}, {
									"mDataProp" : "meetingSummary"
								}, {
									"mDataProp" : "id"
								}],
								"aoColumnDefs" : [{
									'aTargets' : [ 0 ],
									'bSortable' : false
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
									'bSortable' : true
								},{
									'aTargets' : [ 5 ],
									'bSortable' : false
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='会议纪要'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
								},{
									'aTargets' : [ 7 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';
											if (oObj.aData.identifying == 1){
												operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
												operate += ' <ul class="dropdown-menu">';
												operate += '<li><a onclick="$.ims.meeting.updateSummary('
													+ oObj.aData.id
													+ ')"><i class="icon-wrench"></i> 会议纪要</a></li>';
												
											}
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	
	/**
	 * 待参加会议。
	 */
	waitingAttendDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/attendlist',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
									 var reserveName = $("#s_meetingname").val();
									 var compere = $("#s_compere").val();
									 var startDate = $("#s_startDate").val();
									 var meetingStatus = $("#s_status").val();
									
									 if (!!reserveName) {
									 aoData.push({
									 "name" : "reserveName",
									 "value" : reserveName
									 });
									 }
									 if (!!compere) {
									 aoData.push({
									 "name" : "compere",
									 "value" : compere
									 });
									 }
									 if (!!startDate) {
									 aoData.push({
									 "name" : "startDate",
									 "value" : startDate
									 });
									 }
									 if (!!meetingStatus) {
									 aoData.push({
									 "name" : "meetingStatus",
									 "value" : meetingStatus
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "meetingStartTime"
								}, {
									"mDataProp" : "meetingEndTime"
								}, 
//								{
//									"mDataProp" : "inform"
//								},
								{
									"mDataProp" : "id"
								}],
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : false
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
									'bSortable' : false
								},
//								{
//									'aTargets' : [ 6 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										inform = sVal;
//										if (sVal == "0") {
//											return "<span class='label'>未设通知</span>";
//										}
//										if (sVal == "1") {
//											return "<span class='label label-info'>通知开会</span>";
//										}
//										if (sVal == "2") {
//											return "<span class='label label-info'>会议取消</span>";
//										}
//									}
//								}
								{
									'aTargets' : [ 6 ],
									'bSortable' : false,
									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	
	/**
	 * 所有的历史会议。
	 */
	allHistoryDataTable : function() {
		if (this.reserveDataTable == null) {
			this.reserveDataTable = $("#reserve_dataTable")
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
								"sAjaxSource" : $.ims.getContextPath()+ '/meeting/allhistory',
								 "fnServerData" : function(sSource, aoData,fnCallback) {
								 var reserveName = $("#s_meetingname").val();
								 var compere = $("#s_compere").val();
								 var startDate = $("#s_startDate").val();
								 var meetingStatus = $("#s_status").val();
								
								 if (!!reserveName) {
								 aoData.push({
								 "name" : "reserveName",
								 "value" : reserveName
								 });
								 }
								 if (!!compere) {
								 aoData.push({
								 "name" : "compere",
								 "value" : compere
								 });
								 }
								 if (!!startDate) {
								 aoData.push({
								 "name" : "startDate",
								 "value" : startDate
								 });
								 }
								 if (!!meetingStatus) {
								 aoData.push({
								 "name" : "meetingStatus",
								 "value" : meetingStatus
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
									"mDataProp" : "meetingName"
								}, {
									"mDataProp" : "initiatorName"
								},{
									"mDataProp" : "compereName"
								}, {
									"mDataProp" : "roomName"
								}, {
									"mDataProp" : "meetingStartTime"
								}, {
									"mDataProp" : "meetingEndTime"
								}, {
									"mDataProp" : "registrarName"
								},
//								{
//									"mDataProp" : "meetingStatus"
//								}, 
								{
									"mDataProp" : "id"
								}],
								
								"aoColumnDefs" : [ 
								                   {
									'aTargets' : [ 0 ],
									'bSortable' : false
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
									'bSortable' : true
								}, {
									'aTargets' : [ 5 ],
									'bSortable' : false
								},{
									'aTargets' : [ 6 ],
									'bSortable' : false
								},
//								{
//									'aTargets' : [ 7 ],
//									'bSortable' : false,
//									'fnRender' : function(oObj, sVal) {
//										opstatus = sVal;
//										if (sVal == "1") {
//											return "<span class='label label-success'>已提交</span>";
//										}
//										if (sVal == "2") {
//											return "<span class='label label-warning'>已审核</span>";
//										}
//										if (sVal == "3") {
//											return "<span class='label label-important'>已开始</span>";
//										}
//										if (sVal == "4") {
//											return "<span class='label'>已结束</span>";
//										}
//									}
//								},
								{
									'aTargets' : [ 7 ],
									'bSortable' : false,

									'fnRender' : function(oObj, sVal) {
										var operate = '<div class="btn-group">';
//										operate += '<a class="btn2 btn-info"  onclick="windows.open()" href="'+$.ims.getContextPath()+'/meeting/details?rm='+oObj.aData.id+'"><i class="icon-zoom-in"></i> 查看详情</a>';
										operate += '<a class="btn2 btn-info" onclick="$.ims.meeting.openWin('+oObj.aData.id+');"><i class="icon-zoom-in"></i> 查看详情</a>';
										operate += '</ul>';
										operate += '</div>';
										return operate;
									}
								},{
								'aTargets' : [ '_all' ],
								'sClass' : 'center'
								}
								]
							});
		} else {
			this.refreshReserveDataTable();
		}
	},
	
	openWin : function (id){
		var height = 500;
		var left = (window.document.body.offsetWidth - 900) / 2;
		window.open ($.ims.getContextPath()+'/meeting/details?rm='+id+'', '_blank', 
				"toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=900, height=" + height + ",left=" + left + ",top=200")

	}
	
}
