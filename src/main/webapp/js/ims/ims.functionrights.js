jQuery.ims.functionrights = {
	roleDataTable : null, // 角色列表
	userRightsDataTable : null, // 用户权限列表
	roleUserDataTable : null, // 角色用户列表
	rights_setting : {
		data : {
			key : {
				name : "rightsdesc",
			},
			simpleData : {
				enable : true
			}
		},
		check : {
			enable : true,
			chkStyle : "checkbox"
		}
	},

	/**
	 * 加载rights树
	 */
	loadRightsTree : function() {
		$.ajax({
			async : false,
			url : $.ims.getContextPath() + "/rights/list",
			dataType : "json",
			success : function(data) {
				$.fn.zTree.init($("#rightsTree1"),
						$.ims.functionrights.rights_setting, data);
				$.fn.zTree.init($("#rightsTree2"),
						$.ims.functionrights.rights_setting, data);
				$.fn.zTree.init($("#treeRightView"),
						$.ims.functionrights.rights_setting, data);
				$.fn.zTree.init($("#userrightTreeView"),
						$.ims.functionrights.rights_setting, data);
			}
		});
	},

	/**
	 * 刷新datatable
	 */
	refreshDataTable : function(type) {
		if (type == '1' && this.roleDataTable != null) {
			var oSettings = this.roleDataTable.fnSettings();
			this.roleDataTable.fnDraw(oSettings);
			this.refreshDataTable(3);
		} else if (type == '2' && this.userRightsDataTable != null) {
			var oSettings = this.userRightsDataTable.fnSettings();
			this.userRightsDataTable.fnDraw(oSettings);
		} else if (type == '3' && this.roleUserDataTable != null) {
			var oSettings = this.roleUserDataTable.fnSettings();
			this.roleUserDataTable.fnDraw(oSettings);
		}
	},

	/**
	 * 初始化角色列表
	 */
	initRoleDataTable : function(U, D) {
		if (this.roleDataTable == null) {
			this.roleDataTable = $('#role_dataTable')
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span5'l>r>t<'row-fluid'<'span12 center'p>>",
								"bPaginate" : false,
								"bServerSide" : true,
								"sServerMethod" : "GET",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : false,
								"sAjaxSource" : $.ims.getContextPath() + '/role/list',
								"aoColumns" : [ {
									"mDataProp" : "name"
								}, {
									"mDataProp" : "roledesc"
								}, {
									"mDataProp" : "operate"
								} ],
								"aoColumnDefs" : [
										{
											'aTargets' : [ '_all' ],
											'bSortable' : true,
											'sClass' : 'center'
										},
										{
											'aTargets' : [ 2 ],
											'fnRender' : function(oObj, sVal) {
												var name = oObj.aData.name;
												if (name == "admin") {
													return "";
												}
												var operate = '<div class="btn-group">';
												operate += '<a class="btn2 btn-info" onclick="$.ims.functionrights.showRoleDetail('
														+ oObj.aData.id
														+ ',\''
														+ oObj.aData.name
														+ '\',\''
														+ oObj.aData.roledesc
														+ '\')"><i class="icon-zoom-in"></i> 查看详情</a>';
												if (U || D) {
													operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
													operate += ' <ul class="dropdown-menu">';
													if (U) {
														operate += '<li><a onclick="$.ims.functionrights.showRoleEditModal('
																+ oObj.aData.id
																+ ',\''
																+ oObj.aData.name
																+ '\',\''
																+ oObj.aData.roledesc
																+ '\')"><i class="icon-edit"></i> 编辑</a></li>';
													}
													if (D) {
														operate += '<li><a onclick="$.ims.functionrights.roleDelete('
																+ oObj.aData.id
																+ ')"><i class="icon-wrench"></i> 删除</a></li>';
													}
													operate += '</ul>';
													operate += '</div>';
												}
												return operate;
											}
										} ]
							});
		} else {
			this.refreshDataTable(1);
		}
	},

	/**
	 * 显示角色详情
	 */
	showRoleDetail : function(id, name, desc) {
		$.ajax({
			async : false,
			url : $.ims.getContextPath() + "/rights/list",
			dataType : "json",
			success : function(data) {
				$.fn.zTree.init($("#treeRightView"),
						$.ims.functionrights.rights_setting, data);
			}
		});
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/rolerights/list/role/" + id,
			dataType : "json",
			data : {},
			success : function(json) {

				var treeObj = $.fn.zTree.getZTreeObj("treeRightView");
				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						treeObj.setChkDisabled(this, false);
					});
					treeObj.setChkDisabled(this, false);
				});

				treeObj.checkAllNodes(false);
				treeObj.expandAll(false);

				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						for (var i = 0; i < json.length; i++) {
							if (this.id == json[i].rightsId) {
								treeObj.checkNode(this, true, true);
							}
						}
					});
				});

				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						treeObj.setChkDisabled(this, true);
					});
					treeObj.setChkDisabled(this, true);
				});
			}
		});
		$('#detail_rolename').html(name);
		$('#detail_roledesc').html(desc);
		$('#roleDetailModal').modal('show');
	},

	/**
	 * 显示角色新增对话框
	 */
	showRoleAddModal : function(id) {
		this.loadRightsTree();

		var treeObj = $.fn.zTree.getZTreeObj("rightsTree1");
		treeObj.checkAllNodes(false);
		treeObj.expandAll(false);

		$("#hf_roleId").val(0);

		$('#role_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#role_modal_header_label").text("新建角色");
		$("#hf_roleId").val("0");
		$("#rolename").val("");
		$("#roledesc").val("");
		$("#role_modal").modal('show');
	},

	/**
	 * 显示角色编辑对话框
	 */
	showRoleEditModal : function(id, name, desc) {
		this.loadRightsTree();
		this.changeZtreeRightsNodes("rightsTree1", id);
		var treeObj = $.fn.zTree.getZTreeObj("rightsTree1");
		treeObj.checkAllNodes(false);
		treeObj.expandAll(false);

		$('#role_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#role_modal_header_label").text("编辑角色");
		$("#hf_roleId").val(id);
		$("#rolename").val(name);
		$("#roledesc").val(desc);
		$("#role_modal").modal('show');
	},

	/**
	 * 根据用户id 初始化权限
	 */
	changeZtreeRightsNodes : function(tree, id) {
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/rolerights/list/role/" + id,
			dataType : "json",
			success : function(json) {
				var treeObj = $.fn.zTree.getZTreeObj(tree);
				treeObj.checkAllNodes(false);
				treeObj.expandAll(false);
				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						for (var i = 0; i < json.length; i++) {
							if (this.id == json[i].rightsId)
								treeObj.checkNode(this, true, true);
						}
					});
				});
			}
		});
	},

	/**
	 * 保存或更新Role
	 */
	saveOrUpdateRole : function() {
		var roleId = $("#hf_roleId").val();
		var name = $('#rolename').val();
		if (name == "") {
			noty({"text" : "权限名称不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var desc = $('#roledesc').val();
		if (desc == "") {
			noty({"text" : "权限描述不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		var rightsIds = "";

		var treeObj = $.fn.zTree.getZTreeObj("rightsTree1");
		$(treeObj.getCheckedNodes(true)).each(function() {
			$(this.children).each(function() {
				if (this.checked) {
					if (rightsIds == '') {
						rightsIds = this.id;
					} else {
						rightsIds = rightsIds + "," + this.id;
					}
				}
			});
		});
		if (rightsIds == "") {
			noty({"text" : "权限列表不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
			return;
		}
		if (roleId == "0" || roleId == "") {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/role/create",
				dataType : "json",
				data : {
					name : name,
					roledesc : desc,
					rightsIds : rightsIds
				},
				success : function(json) {
					var success = json.success;
					var msg = json.msg;
					if (success == 1) {
						$("#role_modal").modal('hide');
						noty({
							"text" : "" + msg + "",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$.ims.functionrights.refreshDataTable(1);
					} else {
						noty({
							"text" : "" + msg + "",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
				}
			});
		} else {
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/role/update",
				dataType : "json",
				data : {
					id : roleId,
					name : name,
					roledesc : desc,
					rightsIds : rightsIds
				},
				success : function(json) {
					var success = json.success;
					var msg = json.msg;
					if (success == 1) {
						$("#role_modal").modal('hide');
						noty({
							"text" : "" + msg + "",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$.ims.functionrights.refreshDataTable(1);
					} else {
						noty({
							"text" : "" + msg + "",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
				}
			});
		}
	},

	/**
	 * 角色删除
	 */
	roleDelete : function(id) {
		var msg = "是否确认删除该角色？如果该角色被删除，该角色下属所有用户权限将失效！";
		bootbox.confirm(msg, function(result) {
			if (result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/role/delete",
					dataType : "json",
					data : {
						id : id
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
							$.ims.functionrights.refreshDataTable(1);
						} else {
							noty({
								"text" : "" + msg + "",
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
	 * 初始化用户权限列表
	 */
	initUserRightsDataTable : function(U, D) {
		if (this.userRightsDataTable == null) {
			this.userRightsDataTable = $('#userRights_dataTable')
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span5'l>r>t<'row-fluid'<'span12 center'p>>",
								"bPaginate" : false,
								"bServerSide" : true,
								"sServerMethod" : "GET",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : false,
								"sAjaxSource" : $.ims.getContextPath()
										+ '/userrights/list',
								"aoColumns" : [ {
									"mDataProp" : "chinesename"
								}, {
									"mDataProp" : "operate"
								} ],
								"aoColumnDefs" : [
										{
											'aTargets' : [ '_all' ],
											'bSortable' : true,
											'sClass' : 'center'
										},
										{
											'aTargets' : [ 1 ],
											'fnRender' : function(oObj, sVal) {
												var operate = '<div class="btn-group">';
												operate += '<a class="btn2 btn-info" onclick="$.ims.functionrights.showUserRightsDetail('
														+ oObj.aData.id
														+ ',\''+ oObj.aData.chinesename +'\')"><i class="icon-zoom-in"></i> 查看详情</a>';
												if (U || D) {
													operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
													operate += ' <ul class="dropdown-menu">';
													if (U) {
														operate += '<li><a onclick="$.ims.functionrights.showUserRightsEditModal('
																+ oObj.aData.id
																+ ')"><i class="icon-edit"></i> 编辑</a></li>';
													}
													if (D) {
														operate += '<li><a onclick="$.ims.functionrights.deleteUserRights('
																+ oObj.aData.id
																+ ')"><i class="icon-lock"></i> 删除</a></li>';
													}
													operate += '</ul>';
													operate += '</div>';
												}
												return operate;
											}
										} ]
							});
		} else {
			this.refreshDataTable(2);
		}
	},

	/**
	 * 显示用户权限详情
	 */
	showUserRightsDetail : function(userId, username) {
		$("#detail_userright_user").html(username);
		$.ajax({
			async : false,
			url : $.ims.getContextPath() + "/rights/list",
			dataType : "json",
			success : function(data) {
				$.fn.zTree.init($("#userrightTreeView"),
						$.ims.functionrights.rights_setting, data);
			}
		});
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/userrights/user/"+ userId +"/query",
			dataType : "json",
			success : function(json) {
				var treeObj = $.fn.zTree.getZTreeObj("userrightTreeView");
				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						treeObj.setChkDisabled(this, false);
					});
					treeObj.setChkDisabled(this, false);
				});

				treeObj.checkAllNodes(false);
				treeObj.expandAll(false);

				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						for (var i = 0; i < json.length; i++) {
							if (this.id == json[i].rightsId) {
								treeObj.checkNode(this, true, true);
							}
						}
					});
				});

				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						treeObj.setChkDisabled(this, true);
					});
					treeObj.setChkDisabled(this, true);
				});
			}
		});

		$('#user_right_detail_Modal').modal('show');
	},

	/**
	 * 显示用户权限新增对话框
	 */
	showUserRightsAddModal : function(id) {
		this.loadRightsTree();
		$.ims.common.setchosenvalue("user","");
		var treeObj = $.fn.zTree.getZTreeObj("rightsTree2");
		treeObj.checkAllNodes(false);
		treeObj.expandAll(false);

		$('#userRights_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#userRights_modal_header_label").text("新建角色");
		$("#userRights_modal").modal('show');
	},

	/**
	 * 显示角色编辑对话框
	 */
	showUserRightsEditModal : function(userId) {
		$.ims.common.setchosenvalue("user", userId);
		this.loadRightsTree();
		this.changeZtreeRightsNodesByUserId("rightsTree2", userId);
		$("#userRights_modal_header_label").text("编辑角色");
		$('#userRights_modal').modal('show');
	},
	
	// 根据用户初始化权限列表
	changeZtreeRightsNodesByUserId : function(tree, userId){
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/userrights/user/" + userId +"/query",
			dataType : "json",
			success : function(json) {
				var treeObj = $.fn.zTree.getZTreeObj(tree);
				treeObj.checkAllNodes(false);
				treeObj.expandAll(false);
				$(treeObj.getNodes()).each(function() {
					$(this.children).each(function() {
						for (var i = 0; i < json.length; i++) {
							if (this.id == json[i].rightsId)
								treeObj.checkNode(this, true, true);
						}
					});
				});
			}
		});
	},
	
	
	// 数据验证
	validRights : function(flag){
		// 非空验证
		$(".required2").each(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		
		//数字验证
		return flag;
	},
	

	/**
	 * 保存UserRights
	 */
	saveOrUpdateUserRights : function() {
		var flag = true;
		if(!this.validRights(flag)){
			return;		//验证失败
		}
		var userId = $('#user').val();
		var rightsIds = "";

		var treeObj = $.fn.zTree.getZTreeObj("rightsTree2");
		$(treeObj.getCheckedNodes(true)).each(function() {
			$(this.children).each(function() {
				if (this.checked) {
					if (rightsIds == '') {
						rightsIds = this.id;
					} else {
						rightsIds = rightsIds + "," + this.id;
					}
				}
			});
		});

		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/userrights/save",
			dataType : "json",
			data : {
				userId : userId,
				rightsIds : rightsIds
			},
			success : function(json) {
				var success = json.success;
				var msg = json.msg;
				if (success == 1) {
					$("#userRights_modal").modal('hide');
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					$.ims.functionrights.refreshDataTable(2);
				} else {
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 用户权限删除
	 */
	deleteUserRights : function(id) {
		var msg = "是否确认删除该用户权限？";
		bootbox.confirm(msg, function(result) {
			if (result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/userrights/user/" + id
							+ "/delete",
					dataType : "json",
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
							$.ims.functionrights.refreshDataTable(2);
						} else {
							noty({
								"text" : "" + msg + "",
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
	 * 用户权限中选择用户改变时
	 */
//	changeUser : function(id) {
//		$.ajax({
//			type : "get",
//			url : $.ims.getContextPath() + "/userrights/user/" + id + "/query",
//			dataType : "json",
//			success : function(json) {
//				var treeObj = $.fn.zTree.getZTreeObj("rightsTree2");
//				treeObj.checkAllNodes(false);
//				treeObj.expandAll(false);
//				$(treeObj.getNodes()).each(function() {
//					$(this.children).each(function() {
//						for (var i = 0; i < json.length; i++) {
//							if (this.id == json[i].rightsId)
//								treeObj.checkNode(this, true, true);
//						}
//					});
//				});
//			}
//		});
//	},

	/**
	 * 初始化角色用户列表
	 */
	initRoleUserDataTable : function(U, D) {
		if (this.roleUserDataTable == null) {
			this.roleUserDataTable = $('#roleUser_dataTable')
					.dataTable(
							{
								"sDom" : "<'row-fluid'<'span5'l>r>t<'row-fluid'<'span12 center'p>>",
								"bPaginate" : false,
								"bServerSide" : true,
								"sServerMethod" : "GET",
								"bProcessing" : true,
								"bRetrieve" : true,
								"bDestory" : true,
								"bAutoWidth" : false,
								"bSort" : false,
								"sAjaxSource" : $.ims.getContextPath()
										+ '/roleuser/list',
								"aoColumns" : [ {
									"mDataProp" : "name"
								}, {
									"mDataProp" : "operate"
								} ],
								"aoColumnDefs" : [
										{
											'aTargets' : [ '_all' ],
											'bSortable' : true,
											'sClass' : 'center'
										},
										{
											'aTargets' : [ 1 ],
											'fnRender' : function(oObj, sVal) {
												var operate = '<div class="btn-group">';
												operate += '<a class="btn2 btn-info" onclick="$.ims.functionrights.showRoleUserDetail('
														+ oObj.aData.id
														+ ',\''+ oObj.aData.name +'\')"><i class="icon-zoom-in"></i> 查看详情</a>';
												if (U || D) {
													operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
													operate += ' <ul class="dropdown-menu">';
													if (U) {
														operate += '<li><a onclick="$.ims.functionrights.showRoleUserEditModal('
																+ oObj.aData.id
																+ ')"><i class="icon-edit"></i> 编辑</a></li>';
													}
													if (D) {
														operate += '<li><a onclick="$.ims.functionrights.deleteRoleUser('
																+ oObj.aData.id
																+ ',1)"><i class="icon-lock"></i> 删除</a></li>';
													}
													operate += '</ul>';
													operate += '</div>';
												}
												return operate;
											}
										} ]
							});
		} else {
			this.refreshDataTable(3);
		}
	},

	/**
	 * 显示角色用户详情
	 */
	showRoleUserDetail : function(id, rolename) {
		$("#usertabledetail").html("");
		$("#span_rolename").text(rolename);
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/roleuser/user4role/query?roleId=" + id,
			dataType : "json",
			success : function(json) {
				column = '<tr><td style="width: 150px; height: 20px;"  class="center">员工工号</td><td style="width: 150px; height: 20px;"  class="center">员工中文名</td></tr>';
				$("#usertabledetail").append(column);
				for (var i = 0; i < json.length; i++) {
					if (json[i].checked) {
						column = '<tr><td style="width: 150px; height: 20px;"  class="center">'
								+ json[i].userempnumber
								+ '</td>'
								+ '<td style="width: 150px; height: 20px;"  class="center">' + json[i].userchinesename + '</td>' + '</tr>';
						$("#usertabledetail").append(column);
					}
				}
			}
		});
		$('#role_user_detail_modal').modal('show');
	},

	/**
	 * 显示角色用户新增对话框
	 */
	showRoleUserAddModal : function(id) {
		this.queryUser2UserRole("0");
		$.ims.common.setchosenvalue("roleuser_role","");
		$('#roleUser_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#roleUser_modal_header_label").text("新建角色人员");
		$("#roleUser_modal").modal('show');
	},

	/**
	 * 显示角色用户编辑对话框
	 */
	showRoleUserEditModal : function(id) {
		$.ims.common.setchosenvalue("roleuser_role",id);
//		$("#roleuser_role").val(id);
		this.queryUser2UserRole(id);
		$('#roleUser_modal').modal({
			keyboard : false,
			backdrop : false
		});
		$("#roleUser_modal_header_label").text("编辑角色人员");
		$("#roleUser_modal").modal('show');
	},

	
	
	// 数据验证
	validRoleUser : function(flag){
		// 非空验证
		$("#roleuser_role").each(function(){
			var roleuser_role = $("#roleuser_role").val();
			if (roleuser_role == 0 || roleuser_role == null){
	        	$("#roleuser_role").next().addClass('valid-error');
	        	flag = false;
			}else{
	        	$("#roleuser_role").next().removeClass('valid-error');
			}
		});
		
		//数字验证
		return flag;
	},
	
	
	/**
	 * 保存角色用户
	 */
	saveOrUpdateRoleUser : function() {
		var flag = true;
		if(!this.validRoleUser(flag)){
			return;		//验证失败
		}
		var roleId = $('#roleuser_role').val();
		var userIds = "";
		$('[id=roleuser_userid]').each(function() {
			if ($(this).prop('checked')) {
				if (userIds == '') {
					userIds = $(this).val();
				} else {
					userIds += "," + $(this).val();
				}
			}
		});

		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/roleuser/save",
			dataType : "json",
			data : {
				roleId : roleId,
				userIds : userIds
			},
			success : function(json) {
				var success = json.success;
				var msg = json.msg;
				if (success == 1) {
					$("#roleUser_modal").modal('hide');
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					$.ims.functionrights.refreshDataTable(3);
				} else {
					noty({
						"text" : "" + msg + "",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 角色用户删除
	 */
	deleteRoleUser : function(id) {
		var msg = "是否确认删除该用户权限？";
		bootbox.confirm(msg, function(result) {
			if (result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/roleuser/role/" + id
							+ "/delete",
					dataType : "json",
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
							$.ims.functionrights.refreshDataTable(3);
						} else {
							noty({
								"text" : "" + msg + "",
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
	 * 当Role改变事件
	 */
	queryUser2UserRole : function(roleId) {
		$
				.ajax({
					type : "get",
					url : $.ims.getContextPath()
							+ "/roleuser/user4role/query?roleId=" + roleId,
					dataType : "json",
					data : {},
					success : function(json) {
						$("#usertable").html("");
						column = '<tr><td style="width: 150px; height: 20px;" class="center"><input  type="checkbox"  id="select_all_roleuser_userid" onclick="$.ims.functionrights.selectAll(\'select_all_roleuser_userid\',\'roleuser_userid\')" value=\'1\'></td><td style="width: 150px; height: 20px;"  class="center">员工工号</td><td style="width: 150px; height: 20px;"  class="center">员工中文名</td></tr>';
						$("#usertable").append(column);
						for (var i = 0; i < json.length; i++) {
							if (!json[i].checked) {
								column = '<tr><td style="width: 50px; height: 20px;" class="center"><input type="checkbox" name=\'roleuser_userid\' id="roleuser_userid" value='
										+ json[i].userId
										+ '></input></td>'
										+ '<td style="width: 150px; height: 20px;"  class="center">'
										+ json[i].userempnumber
										+ '</td>'
										+ '<td style="width: 150px; height: 20px;"  class="center">'
										+ json[i].userchinesename
										+ '</td>'
										+ '</tr>';
							} else {
								column = '<tr><td style="width: 50px; height: 20px;" class="center"><input type="checkbox"   name=\'roleuser_userid\'  id="roleuser_userid" checked=true value='
										+ json[i].userId
										+ '></input></td>'
										+ '<td style="width: 150px; height: 20px;"  class="center">'
										+ json[i].userempnumber
										+ '</td>'
										+ '<td style="width: 150px; height: 20px;"  class="center">'
										+ json[i].userchinesename
										+ '</td>'
										+ '</tr>';
							}
							$("#usertable").append(column);
						}
					}
				});
	},

	queryrole : function() {
		$("#roleuser_role").html("");
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/common/role/list",
			dataType : "json",
			data : {},
			success : function(json) {
				$("#roleuser_role").append("<option value='0'></option>");
				for (var i = 0; i < json.length; i++) {
					$("#roleuser_role").append(
							"<option value=\"" + json[i].id + "\">"
									+ json[i].name + "</option>");
				}
				$("#roleuser_role").chosen({
					allow_single_deselect : true
				});
			}
		});
	},

	/**
	 * checkbox 全选
	 */
	selectAll : function(selectallid, checkboxName) {
		if ($('#' + selectallid).is(":checked"))
			$('input[name=' + checkboxName + ']').each(function() {
				$(this).prop("checked", true);
			});
		else
			$('input[name=' + checkboxName + ']').each(function() {
				$(this).prop("checked", false);
			});
	},

}