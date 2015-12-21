jQuery.ims.project = {
		
		projectDataTable : null,
		projectMemberTable : null,
		
		/**
		 * 刷新项目管理table
		 */
		refreshProjectDataTable : function(){
			if(this.projectDataTable != null){
				var oSettings = this.projectDataTable.fnSettings();
				this.projectDataTable.fnDraw(oSettings);
			}
		},
		
		/**
		 * 创建项目管理table
		 */
		initProjectDataTable : function(){
			if (this.projectDataTable == null) {
				this.projectDataTable = $('#project_dataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
									"oLanguage" : {
										"iDisplayLength": 10, 
										"aLengthMenu": [10,25,50,100],
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
									"sAjaxSource" : $.ims.getContextPath() + '/project/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var pmId = $("#s_pm").val();
										var projectName = $("#s_projectName").val();
										if (!!pmId) {
											aoData.push({
												"name" : "pmId",
												"value" : pmId
											});
										}
										if (!!projectName) {
											aoData.push({
												"name" : "projectName",
												"value" : projectName
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
										"mDataProp" : "index"
									}, {
										"mDataProp" : "projectName"
									}, {
										"mDataProp" : "projectCode"
									}, {
										"mDataProp" : "complex"
									}, {
										"mDataProp" : "customer"
									}, {
										"mDataProp" : "pm"
									}, {
										"mDataProp" : "state"
									}, {
										"mDataProp" : "description"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											return '<span class="badge badge-warning">'+sVal+'</span>';
										}
									}, {
										'aTargets' : [ 6 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal == 0) {
												return "<span class='label'>新建</span>";
											}else if(sVal == 1){
												return "<span class='label label-info'>进行中</span>";
											}else if(sVal == 2){
												return "<span class='label label-success'>结束</span>";
											}else if(sVal == 3){
												return "<span class='label label-inverse'>关闭</span>";
											}
										}
									}, {
										'aTargets' : [ 8 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';

//											operate += '<li><a onclick="$.ims.project.showProjectEditModal('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a href="'+$.ims.getContextPath()+'/project/edit?id='+oObj.aData.id+'"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.project.deleteProject('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				this.refreshProjectDataTable();
			}
		},

		/** 清空text **/
		clearProjectModal : function(){
			$("#projectName").val("");
			$("#projectCode").val("");
			$.ims.common.setchosenvalue("complex","");
			$("#customer").val("");
			$.ims.common.setchosenvalue("pm","");
			$.ims.common.setchosenvalue("state","");
			$("#description").val("");
		},
		
		showProjectAddModal : function(){
			$("#hf_id").val(0);
			this.clearProjectModal();
			$("#div_state").hide();
			$('#project_edit_modal').modal({  keyboard: false, backdrop : false});
			$("#project_modal_header_label").text("新增项目");
			$("#project_edit_modal").modal('show');
		},
		
		showProjectEditModal : function(id){
			$("#hf_id").val(id);
			$.ajax({
    			type : "get",
    			url : $.ims.getContextPath() + "/project/" + id,
    			dataType : "json",
    			success : function(json) {
    				if (json != null) {
						$("#projectName").val(json.projectName);
						$("#projectCode").val(json.projectCode);
						$.ims.common.setchosenvalue("complex",json.complex);
						$("#customer").val(json.customer);
						$.ims.common.setchosenvalue("pm",json.pm == null? "":json.pm.id);
						$.ims.common.setchosenvalue("state",json.state);
						$("#description").val(json.description);
					}
    			}
    		});
			$("#div_state").show();
			$('#project_edit_modal').modal({  keyboard: false, backdrop : false});
			$("#project_modal_header_label").text("编辑项目");
			$("#project_edit_modal").modal('show');
		},
		
		initProjectEidtForm : function(){
			var id = $("#hf_id").val();
			$.ajax({
    			type : "get",
    			url : $.ims.getContextPath() + "/project/" + id,
    			dataType : "json",
    			success : function(json) {
    				if (json != null) {
						$("#projectName").val(json.projectName);
						$("#projectCode").val(json.projectCode);
						$.ims.common.setchosenvalue("complex",json.complex);
						$("#customer").val(json.customer);
						$.ims.common.setchosenvalue("pm",json.pm == null? "":json.pm.id);
						$.ims.common.setchosenvalue("state",json.state);
						$("#description").val(json.description);
					}
    			}
    		});
		},
		
		/**
		 * 项目删除
		 */
		deleteProject : function(id){
			var msg = "是否确认删除？";
			bootbox.confirm( msg, function (result) {
	            if(result){
					$.ajax({
		    			type : "post",
		    			url : $.ims.getContextPath() + "/project/delete",
		    			data : {
		    				id : id
		    			},
		    			dataType : "json",
		    			success : function(json) {
		    				var success = json.success;
		    				var msg = json.msg;
		    				if(success == 1){
		    					$("#project_edit_modal").modal('hide');
		    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
		    					$.ims.project.refreshProjectDataTable();
		    				}else{
		    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
		    				}
		    			}
		    		});
	            }
			}); 
		},
		
		
		
		/**
		 * 新增/编辑
		 */
		save : function(){
			var id = $("#hf_id").val();
			var projectName = $("#projectName").val();
			if (projectName == "") {
				noty({"text" : "项目名称不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var projectCode = $("#projectCode").val();
			if (projectCode == "") {
				noty({"text" : "项目编号不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var complex = $("#complex").val();
			if (complex == "") {
				noty({"text" : "项目复杂度不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var pm = $("#pm").val();
			if (pm == "") {
				noty({"text" : "产品经理不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var customer = $("#customer").val();
			if (customer == "") {
				noty({"text" : "客户名称不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var state = $("#state").val();
			if (state == "") {
				noty({"text" : "项目状态不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			var description = $("#description").val();
			if (description == "") {
				noty({"text" : "项目描述不能为空！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			if(id == 0){
				
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/project/create",
	    			data : {
	    				projectName : projectName,
	    				projectCode : projectCode,
	    				complex : complex,
	    				pmId : pm,
	    				customer : customer,
	    				state : 0,
	    				description : description
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var success = json.success;
	    				var msg = json.msg;
	    				if(success == 1){
	    					$("#project_edit_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.project.refreshProjectDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}else{
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/project/update",
	    			data : {
	    				id : id,
	    				projectName : projectName,
	    				projectCode : projectCode,
	    				complex : complex,
	    				pmId : pm,
	    				customer : customer,
	    				state : state,
	    				description : description
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var success = json.success;
	    				var msg = json.msg;
	    				if(success == 1){
	    					$("#project_edit_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.project.refreshProjectDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
		},
		
		
		/**
		 * 配置项目成员弹出页面。
		 */
		showMemberAddModal :function(){
			$('#project_Member_edit').modal({  keyboard: false, backdrop : false});
			$("#project_Member_header_label").text("配置项目成员");
			$.ims.common.setchosenvalue("role","");
			$.ims.common.setchosenvalue("users","");
			$("#project_Member_edit").modal('show');
		},
		
		/**
		 * 新增配置项目成员。
		 */
		saveMember :function(){
			var id = $("#hf_id").val();
			var users = $("#users").val().toString();
			var role = $("#role").val();
			$.ajax({
    			type : "post",
    			url : $.ims.getContextPath() + "/project/member/create",
    			data : {
    				id : id,
    				users : users,
    				role : role
    			},
    			dataType : "json",
    			success : function(json) {
    				var success = json.success;
    				var msg = json.msg;
    				if(success == 1){
    					$("#project_Member_edit").modal('hide');
    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
    					$.ims.project.refreshProjectMemberDataTable();
    				}else{
    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
    				}
    			}
    		});
		},
			

			
			/**
			 * 项目成员删除
			 */
			deleteProjectMember : function(id){
				var msg = "是否确认删除？";
				bootbox.confirm( msg, function (result) {
		            if(result){
						$.ajax({
			    			type : "post",
			    			url : $.ims.getContextPath() + "/project/member/delete",
			    			data : {
			    				id : id
			    			},
			    			dataType : "json",
			    			success : function(json) {
			    				var success = json.success;
			    				var msg = json.msg;
			    				if(success == 1){
			    					$("#project_edit_modal").modal('hide');
			    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
			    					$.ims.project.refreshProjectMemberDataTable();
			    				}else{
			    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
			    				}
			    			}
			    		});
		            }
				}); 
			},
		
		
		/**
		 * 刷新项目管理table
		 */
		refreshProjectMemberDataTable : function(){
			if(this.projectMemberDataTable != null){
				var oSettings = this.projectMemberDataTable.fnSettings();
				this.projectMemberDataTable.fnDraw(oSettings);
			}
		},
		
		/**
		 * 创建项目管理table
		 */
		initProjectMemberDataTable : function(){
			var pId = $("#hf_id").val();
			if (this.projectMemberDataTable == null) {
				this.projectMemberDataTable = $('#member_dataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'r>t<'row-fluid'>",
									"oLanguage" : {
										"sZeroRecords" : "抱歉， 暂时没有记录",
										"sInfoEmpty" : "没有数据"
									},
									"bServerSide" : true,
									"sServerMethod" : "GET",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/project/member/list?pId='+ pId,
									"aoColumns" : [ {
										"mDataProp" : "index"
									}, {
										"mDataProp" : "chinesename"
									}, {
										"mDataProp" : "role"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									},{
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-danger" onclick="$.ims.project.deleteProjectMember('+oObj.aData.id+')"><i class="icon-trash"></i> 删除</a>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				this.refreshProjectMemberDataTable();
			}
		},
}