jQuery.ims.userdimission = {
	// 离职
	dimissionDataTable : null,

	refreshDimissionDataTable : function() {
		if (this.dimissionDataTable != null) {
			var oSettings = this.dimissionDataTable.fnSettings();
			this.dimissionDataTable.fnDraw(oSettings);
		}
	},

	initDimissionDataTable : function() {
		if (this.dimissionDataTable == null) {
			this.dimissionDataTable = $('#dimission_datatable').dataTable(
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
						"sServerMethod" : "GET",
						"bProcessing" : true,
						"bRetrieve" : true,
						"bDestory" : true,
						"bAutoWidth" : false,
						"bSort" : false,
						"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/resume/dimission/list',
						"fnServerData" : function(sSource, aoData, fnCallback) {
							var userId = $("#s_user").val();
							var planDate_s = $("#s_planDate_s").val();
							var planDate_e = $("#s_planDate_e").val();
							var actualDate_s = $("#s_actualDate_s").val();
							var actualDate_e = $("#s_actualDate_e").val();
							var dismission_type = $("#s_dismission_type").val();
							if (!!userId) {
								aoData.push({
									"name" : "userId",
									"value" : userId
								});
							}
							if (!!planDate_s) {
								aoData.push({
									"name" : "planDate_s",
									"value" : planDate_s
								});
							}
							if (!!planDate_e) {
								aoData.push({
									"name" : "planDate_e",
									"value" : planDate_e
								});
							}
							if (!!actualDate_s) {
								aoData.push({
									"name" : "actualDate_s",
									"value" : actualDate_s
								});
							}
							if (!!actualDate_e) {
								aoData.push({
									"name" : "actualDate_e",
									"value" : actualDate_e
								});
							}
							if (!!dismission_type) {
								aoData.push({
									"name" : "dismission_type",
									"value" : dismission_type
								});
							}
							$.ajax({
								"dataType" : 'json',
								"type" : "GET",
								"url" : sSource,
								"data" : aoData,
								"success" : fnCallback
							});
						},
						"aoColumns" : [ {
							"mDataProp" : "chinesename"
						}, {
							"mDataProp" : "deptname"
						}, {
							"mDataProp" : "dimissionTypeName"
						}, {
							"mDataProp" : "applydate"
						}, {
							"mDataProp" : "plandate"
						}, {
							"mDataProp" : "actualdate"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								},
								{
									'aTargets' : [ 6 ],
									'fnRender' : function(oObj, sVal) {
										var obj = '<a onclick="$.ims.userdimission.showDimissionDetailModal(' + oObj.aData.id + ')">详细信息</a>&nbsp;&nbsp;&nbsp;'
												+ '<a onclick="$.ims.userdimission.showDimissionEidtModal(' + oObj.aData.id + ')">编辑</a>&nbsp;&nbsp;&nbsp;'
												+ '<a onclick="$.ims.userdimission.deleteDimission(' + oObj.aData.id + ')">删除</a>';
										return obj;
									}
								} ]
					});
		} else {
			this.refreshDimissionDataTable();
		}
	},

	showDimissionDetailModal : function(id) {
		var height = 500;
		var left = (window.document.body.offsetWidth - 900) / 2;
		window.open($.ims.getContextPath() + "/sysconfig/resume/dimissioninfo?id=" + id, "_blank",
				"toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=650, height=" + height + ",left=" + left + ",top=20")
	},
	
	/** 显示离职modal * */
	showDimissionModal : function() {
		$("#dimission_modal_header_label").text("员工离职");
		$("#dimission_modal").modal('show');
		$("#hf_dimission_id").val("0");
	},

	showDimissionEidtModal : function(id) {
		$("#dimission_modal_header_label").text("编辑离职信息");
		$("#div_resume").hide();
		$("#div_chinesename").show();
		$("#hf_dimission_id").val(id);
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/sysconfig/resume/dimission/query/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
					// $.ims.common.setchosenvalue("hf_resume_id", json.id);
					$("#l_chinesename").html(json.chinesename);
					$.ims.common.setchosenvalue("dismission_type", json.type);
					$("#dismission_applydate").val(json.applydate);
					$("#dismission_plandate").val(json.plandate);
					$("#dismission_actualdate").val(json.actualdate);
					json.blacklist == 1 ? $("#dismission_blacklist").prop('checked', true) : $("#dismission_blacklist").prop('checked', false);
					$("#dismission_reason").val(json.reason);
					$("#dismission_remark").val(json.remarks);

				}
			}
		});
		$("#dimission_modal").modal('show');
	},
	
	/** 保存离职信息 * */
	saveDismission : function() {
		var id = $("#hf_dimission_id").val();
		var userId = $("#employee").val();
		var type = $("#dismission_type").val();
		var applydate = $("#dismission_applydate").val();
		var plandate = $("#dismission_plandate").val();
		var actualdate = $("#dismission_actualdate").val();
		var blacklist = $('#dismission_blacklist').prop('checked') == true ? 1 : 0;
		var reason = $("#dismission_reason").val();
		var remarks = $("#dismission_remark").val();
		if (id == "0") {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/sysconfig/resume/dimission/save",
				data : {
					userId : userId,
					type : type,
					applydate : applydate,
					plandate : plandate,
					actualdate : actualdate,
					blacklist : blacklist,
					reason : reason,
					remarks : remarks
				},
				dataType : "json",
				success : function(json) {
					var success = json.code;
					if (success == "1") {
						noty({
							"text" : "操作成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					} else {
						noty({
							"text" : "操作失败，请联系管理员！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$.ims.userdimission.refreshDimissionDataTable();
				}
			});
		}else{
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/sysconfig/resume/dimission/save",
				data : {
					id : id,
					userId : userId,
					type : type,
					applydate : applydate,
					plandate : plandate,
					actualdate : actualdate,
					blacklist : blacklist,
					reason : reason,
					remarks : remarks
				},
				dataType : "json",
				success : function(json) {
					var success = json.code;
					if (success == "1") {
						noty({
							"text" : "操作成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					} else {
						noty({
							"text" : "操作失败，请联系管理员！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$.ims.userdimission.refreshDimissionDataTable();
				}
			});
		}
	},

	deleteDimission : function(id) {
		bootbox.confirm("是否确认删除", function(result) {
			if (result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/sysconfig/resume/dimission/delete",
					data : {
						id : id
					},
					dataType : "json",
					success : function(json) {
						if (json) {
							noty({
								"text" : "删除成功!",
								"layout" : "top",
								"type" : "success",
								"timeout" : "2000"
							});
						}
						$.ims.userdimission.refreshDimissionDataTable();
					}
				});
			}
		});
	},

}