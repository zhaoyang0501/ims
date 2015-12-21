jQuery.ims.user = {
	UserDataTable : null,
	otype : 0, // 操作类型：0新增 1修改
	/**
	 * 初始化用户管理列表  D: 删除 U: 更新 S:重置
	 */
	initUserDataTable : function(U, D, S) {
		var freeze = 0;
		if (this.UserDataTable == null) {
			this.UserDataTable = $('#user_dataTable')
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
								"sAjaxSource" : $.ims.getContextPath() + '/user/list',
								"fnServerData" : function(sSource, aoData, fnCallback) {
									var chinesename = $("#s_chinesename").val();
									var empnumber = $("#s_empno").val();
									var deptId = $("#s_dept").val();
									var freeze = $("#s_freeze").val();
									if (!!chinesename) {
										aoData.push({
											"name" : "chinesename",
											"value" : chinesename
										});
									}
									if (!!empnumber) {
										aoData.push({
											"name" : "empnumber",
											"value" : empnumber
										});
									}
									if (!!deptId) {
										aoData.push({
											"name" : "deptId",
											"value" : deptId
										});
									}
									if (!!freeze) {
										aoData.push({
											"name" : "freeze",
											"value" : freeze
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
									"mDataProp" : "username"
								}, {
									"mDataProp" : "chinesename"
								}, {
									"mDataProp" : "empnumber"
								}, {
									"mDataProp" : "dept"
								}, {
									"mDataProp" : "position"
								}, {
									"mDataProp" : "sex"
								}, {
									"mDataProp" : "email"
								}, {
									"mDataProp" : "freeze"
								}, {
									"mDataProp" : "id"
								} ],
								"aoColumnDefs" : [
										{
											'aTargets' : [ '_all' ],
											'sClass' : 'center'
										},{
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
											'bSortable' : false
										},{
											'aTargets' : [ 4 ],
											'bSortable' : false
										},
//										{
//											'aTargets' : [ 3 ],
//											'fnRender' : function(oObj, sVal) {
//												if (sVal != null && sVal != "") {
//													return sVal.name;
//												}
//												return "";
//											}
//										},
										{
											'aTargets' : [ 5 ],
											'bSortable' : false,
											'fnRender' : function(oObj, sVal) {
												if (sVal == "1") {
													return "男";
												}
												return "女";
											}
										},{
											'aTargets' : [ 6 ],
											'bSortable' : false
										},
										{
											'aTargets' : [ 7 ],
											'bSortable' : false,
											'fnRender' : function(oObj, sVal) {
												freeze = sVal;
												if (sVal == 0) {
													return '<span class="label label-success">正常</span>';
												} else if (sVal == 1) {
													return '<span class="label label-important">冻结</span>';
												}
											}
										},
										{
											'aTargets' : [ 8 ],
											'bSortable' : false,
											'fnRender' : function(oObj, sVal) {
												var username = oObj.aData.username;
												if (username == "admin") {
													return "";
												}
												var operate = '<div class="btn-group">';
												operate += '<a class="btn2 btn-info" onclick="$.ims.user.showUserDetail('
														+ oObj.aData.id
														+ ')"><i class="icon-zoom-in"></i> 查看详情</a>';
												if (U || D || S) {
													operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
													operate += ' <ul class="dropdown-menu">';
													if (U) {
														operate += '<li><a onclick="$.ims.user.showUserEditModal('
																+ oObj.aData.id
																+ ')"><i class="icon-edit"></i> 编辑</a></li>';
													}
													if (D) {
														if (freeze == "1") {
															operate += '<li><a onclick="$.ims.user.showUserFreezeModal('
																	+ oObj.aData.id
																	+ ',0)"><i class="icon-unlock"></i> 解冻</a></li>';
														} else {
															operate += '<li><a onclick="$.ims.user.showUserFreezeModal('
																	+ oObj.aData.id
																	+ ',1)"><i class="icon-lock"></i> 冻结</a></li>';
														}
													}
													if(S){
														operate += '<li><a onclick="$.ims.user.resetpwd('
															+ oObj.aData.id
															+ ')"><i class="icon-wrench"></i> 密码重置</a></li>';
													}
													operate += '</ul>';
													operate += '</div>';
												}
												return operate;
											}
										} ]
							});
		} else {
			this.refreshUserDataTable();
		}
	},

	/**
	 * 刷新用户table
	 */
	refreshUserDataTable : function() {
		if (this.UserDataTable != null) {
			var oSettings = this.UserDataTable.fnSettings();
			this.UserDataTable.fnDraw(oSettings);
		}
	},

	/**
	 * 新建用户信息modal弹出
	 */
	showUserAddModal : function(id) {
		this.disableAllInput(0);
		$('#user_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#user_modal_header_label").text("新增用户信息");
		this.otype = 0;
		$("#username").val("");
		$("#chinesename").val("");
		$("#empnumber").val("");
		$("#email").val("");
		$("#weekreport").is(":checked") == true ? 1 : 0;
		$("#train").is(":checked") == true ? 1 : 0;
		$("#attendance").is(":checked") == true ? 1 : 0;
		$.ims.common.setchosenvalue("dept", 0);
		$.ims.common.setchosenvalue("authorityScope", 0);
		$.ims.common.setchosenvalue("sex", "");
		$.ims.common.setchosenvalue("position", 0);
		$("#user_edit_modal").modal('show');
	},

	/**
	 * 修改用户信息modal弹出
	 */
	showUserEditModal : function(id) {
		this.disableAllInput(0);
		$('#user_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#user_modal_header_label").text("编辑用户信息");
		this.otype = 1;
		$("#hf_id").val(id);
		$("#user_edit_modal").modal('show');
		this.queryUser(id);
	},

	/**
	 * 新建和修改用户信息
	 */
	saveOrUpdateUser : function() {
		var username = $("#username").val();
		if (username == "") {
			noty({"text" : "用户名不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var chinesename = $("#chinesename").val();
		if (chinesename == "") {
			noty({"text" : "中文姓名不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var empnumber = $("#empnumber").val();
		if (empnumber == "") {
			noty({"text" : "工号不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var dept = $("#dept").val();
		if (dept == "") {
			noty({"text" : "所属部门不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var position = $("#position").val();
		if (position == "") {
//			noty({"text" : "职位不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
//			return;
			position = 0;
		}
		
		var email = $("#email").val();
		if (email == "") {
			noty({"text" : "邮箱不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		if(!this.emailCheck("email"))
			return;

		
		var sex = $("#sex").val();
		var weekreport = $("#weekreport").is(":checked") == true ? 1 : 0;
		var train = $("#train").is(":checked") == true ? 1 : 0;
		var attendance = $("#attendance").is(":checked") == true ? 1 : 0;
		var authorityScope = $("#authorityScope").val();
		if (this.otype == 0) {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/user/create",
				dataType : "json",
				data : {
					username : username,
					chinesename : chinesename,
					empnumber : empnumber,
					deptId : dept,
					authorityScope:authorityScope,
					sex : sex,
					email : email,
					weekreport : weekreport,
					train : train,
					attendance : attendance,
					position : position
				},
				complete:function(XMLHttpRequest, textStatus) {
					  $("#submit_buttion").removeAttr("disabled");
			 	},
			 	beforeSend:function (XMLHttpRequest) {
			 		 $("#submit_buttion").attr("disabled","disabled");
			 	},
				success : function(json) {
					if (json) {
						noty({
							"text" : "新增用户成功！初始密码：123456",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					}else{
						noty({
							"text" : "用户名不能重复！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
					$("#user_edit_modal").modal('hide');
					$.ims.user.refreshUserDataTable();
				}
			});
		} else if (this.otype == 1) {
			var id = $("#hf_id").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/user/update",
				data : {
					id : id,
					username : username,
					chinesename : chinesename,
					empnumber : empnumber,
					deptId : dept,
					authorityScope:authorityScope,
					sex : sex,
					email : email,
					weekreport : weekreport,
					train : train,
					attendance : attendance,
					position : position
					
				},
				dataType : "json",
				success : function(json) {
					if (json) {
						noty({
							"text" : "修改用户信息成功!",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
					}
					$("#user_edit_modal").modal('hide');
					$.ims.user.refreshUserDataTable();
				}
			});
		}
	},

	/**
	 * 查找用户信息
	 */
	queryUser : function(id) {
		$("#username").val("");
		$("#chinesename").val("");
		$("#empnumber").val("");
		$("#email").val("");
		$.ims.common.setchosenvalue("dept", 0);
		$.ims.common.setchosenvalue("authorityScope", 0);
		$.ims.common.setchosenvalue("sex", 0);
		$.ims.common.setchosenvalue("position", 0);
		$("#weekreport").prop("checked", true);
		$("#train").prop("checked", true);
		$("#attendance").prop("checked", true);
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/user/queryuser/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#username").val(json.username);
					$("#chinesename").val(json.chinesename);
					$("#empnumber").val(json.empnumber);
					$("#email").val(json.email);

					if(json.deptid != null){
						$.ims.common.setchosenvalue("dept", json.deptid);
					}
					if(json.positionid != null){
						$.ims.common.setchosenvalue("position",json.positionid);
					}
					if(json.authorityScope != null){
						$.ims.common.setchosenvalue("authorityScope", json.authorityScope);
					}
					$.ims.common.setchosenvalue("sex", json.sex);
					if (json.weekreport == 1) {
						$("#weekreport").prop("checked", true);
					} else if (json.weekreport == 0) {
						$("#weekreport").prop("checked", false);
					}
					if (json.train == 1) {
						$("#train").prop("checked", true);
					} else if (json.train == 0) {
						$("#train").prop("checked", false);
					}
					if (json.attendance == 1) {
						$("#attendance").prop("checked", true);
					} else if (json.attendance == 0) {
						$("#attendance").prop("checked", false);
					}
				}
			}
		});
	},


	/**
	 * 冻结用户
	 */
	freezeUser : function(id) {
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/user/freeze/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "冻结用户成功!",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				}
				$.ims.user.refreshUserDataTable();
			}
		});
	},

	/**
	 * 解冻用户
	 */
	unFreezeUser : function(id) {
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/user/unfreeze/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "解冻用户成功!",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				}
				$.ims.user.refreshUserDataTable();
			}
		});
	},

	/**
	 * 冻结、解冻确认 1: 冻结 0：解冻
	 */
	showUserFreezeModal : function(id, type) {
		var msg = "";
		if (type == 1) {
			msg = "确定冻结该用户？";
		} else if (type == 0) {
			msg = "确定解冻该用户？";
		}
		bootbox.confirm(msg, function(result) {
			if (result && type == 1) {
				$.ims.user.freezeUser(id);
			} else if (result && type == 0) {
				$.ims.user.unFreezeUser(id);
			}
		});
	},

	/**
	 * 密码重置
	 */
	resetpwd : function(id) {
		var msg = "是否确认重置密码？";
		bootbox.confirm(msg, function(result) {
			if (result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/user/" + id
							+ "/pwd/reset",
					dataType : "json",
					success : function(json) {
						if (json) {
							noty({
								"text" : "密码重置成功! 新密码：123456",
								"layout" : "top",
								"type" : "success",
								"timeout" : "2000"
							});
						} else {
							noty({
								"text" : "密码重置失败! 请稍后再试或联系管理员！",
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
	 * 查看用户详细信息
	 */
	showUserDetail : function(id) {
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/user/queryuser/" + id,
			dataType : "json",
			async : false,
			success : function(json) {
				if (json) {
					$("#display_username").text(json.username == null ? "" : json.username);
					$("#display_no").text(json.empnumber == null ? "" : json.empnumber);
					$("#display_chinesename").text(json.chinesename == null ? "" : json.chinesename);
					$("#display_dept").text(json.dept);
					$("#display_position").text(json.position);
					$("#display_authorityCode").text(json.authorityCode);
					$("#display_authorityScope").text(json.authorityScope);
					$("#display_sex").text(json.sex == "1"? "男" : "女");
					$("#display_email").text(json.email == null? "" : json.email);
				}
			}
		});
		$("#user_details_modal").modal('show');
	},

	/**
	 * disable 所有的输入框，1：只读不能编辑 0： 可以编辑
	 */
	disableAllInput : function(b) {
		if (b == 1) {
			$("#div_footer").hide();
		} else {
			$("#div_footer").show();
		}
	},

	/**
	 * 显示用户密码修改modal
	 */
	showpwdUpdateModal : function() {
		$('#password_edit_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#password_modal_header_label").text("密码修改");
		$("#password_edit_modal").modal('show');
		$("#pwd_old").val("");
		$("#pwd").val("");
		$("#pwd_comfirm").val("");
	},
	
	
	/**
	 * 密码修改
	 */
	updatepwd : function() {
		var pwd_old = $("#pwd_old").val();
		var pwd = $("#pwd").val();
		var pwd_comfirm = $("#pwd_comfirm").val();
		
		if (pwd_old == "" ){
			noty({"text" : "请输入旧密码！","layout" : "top","type" : "warning","timeout" : "2000"});
			return;
		}
		if (pwd == "" ){
			noty({"text" : "请输入新密码！","layout" : "top","type" : "warning","timeout" : "2000"});
			return;
		}
		if (pwd.length >8 ){
			noty({"text" : "密码长度最多8位！","layout" : "top","type" : "warning","timeout" : "2000"});
			return;
		}
		if (pwd_comfirm == "" ){
			noty({"text" : "请确认新密码！","layout" : "top","type" : "warning","timeout" : "2000"});
			return;
		}
		if (pwd_comfirm != pwd ){
			noty({"text" : "两次输入的密码不一致！","layout" : "top","type" : "warning","timeout" : "2000"});
			return;
		}
		
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/user/pwd/update",
			dataType : "json",
			data : {
				pwd_old : pwd_old,
				pwd : pwd
			},
			success : function(json) {
				var success = json.success;
				var msg = json.msg;
				if (success == 1) {
					$("#password_edit_modal").modal('hide');
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	emailCheck : function(obj) {  
	    var objValue = $("#"+obj+"").val();
	    var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
	    if (!pattern.test(objValue)) {
	    	noty({
				"text" : "请输入正确的邮箱地址",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
	        return false;  
	    }  
	    return true;  
	},
};