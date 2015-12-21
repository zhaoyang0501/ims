jQuery.ims.trainingteacher = {
		trainingteacherDatatable : null,
		otype : null,
		
		refreshTrainingTeacherDatatable : function(){
			var oSettings = this.trainingteacherDatatable.fnSettings();
			this.trainingteacherDatatable.fnDraw(oSettings);
		},
		
		inintTrainingTeacherDatatable : function(U, D){
			if (this.trainingteacherDatatable == null) {
				this.trainingteacherDatatable = $('#dt_trainingteacher')
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
							"sServerMethod" : "GET",
							"bProcessing" : true,
							"bRetrieve" : true,
							"bDestory" : true,
							"bAutoWidth" : false,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + '/training/teacher/list',
							"fnServerData" : function(sSource, aoData, fnCallback) {
								var userId = $("#training_teacher").val();
								if (!!userId) {
									aoData.push({
										"name" : "userId",
										"value" : userId
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
								"mDataProp" : "user"
							}, {
								"mDataProp" : "level"
							}, {
								"mDataProp" : "domain"
							}, {
								"mDataProp" : "introduction"
							}, {
								"mDataProp" : "id"
							}],
							"aoColumnDefs" : [
									{
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									},
									{
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal == null || sVal == "") {
												return '';
											} else{
												return sVal.chinesename;
											}
										}
									},
									{
										'aTargets' : [ 4 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 查看详情</a>';
											if (U || D) {
												operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
												operate += ' <ul class="dropdown-menu">';
												if (U) {
													operate += '<li><a onclick="$.ims.trainingteacher.showEditModal('
															+ oObj.aData.id
															+ ')"><i class="icon-edit"></i> 编辑</a></li>';
												}
												if (D) {
													operate += '<li><a onclick="$.ims.trainingteacher.showDeleteModal('
															+ oObj.aData.id
															+ ',1)"><i class="icon-trash"></i> 删除</a></li>';
												}
												operate += '</ul>';
												operate += '</div>';
											}
											return operate;
										}
									} ]
						});
			}else{
				this.refreshTrainingTeacherDatatable();
			}
		},
		
		showAddModal : function(){
			$("#traingteacher_modal_header_label").text("新增讲师信息");
			this.otype = 0;
			$("#hf_id").val(0);
			$("#traingteacher_modal").modal('show');
		},
		
		showEditModal : function(id){
			$("#traingteacher_modal_header_label").text("编辑讲师信息");
			this.otype = 1;
			this.queryTrainingTeacherInfo(id);
			$("#traingteacher_modal").modal('show');
		},
		
		queryTrainingTeacherInfo : function(id){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/training/teacher/id/" + id,
				dataType : "json",
				success : function(json) {
					if (json) {
						$.ims.common.setchosenvalue("level",json.level);
						$("#domain").val(json.domain);
						$("#introduction").val(json.introduction);
						$("#hf_id").val(json.id);
						$.ims.common.setchosenvalue("teacher",json.user.id);
						
					}
				}
			});
		},
		
		showDeleteModal : function(id){
			bootbox.confirm("是否确认删除", function(result) {
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/training/teacher/delete",
					data:{
						id : id
					},
					dataType : "json",
					success : function(json) {
						if (json) {
							noty({ "text" : "删除讲师成功!", "layout" : "top", "type" : "success", "timeout" : "2000" });
						}
						$.ims.trainingteacher.refreshTrainingTeacherDatatable();
					}
				});
			});
		},
		
		saveOrupdate : function(){
			var userId = $("#teacher").val();
			var level = $("#level").val();
			var domain = $("#domain").val();
			var introduction = $("#introduction").val();
			var id = $("#hf_id").val();
			if(this.otype == 0){
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/training/teacher/create",
					dataType : "json",
					data : {
						userId : userId,
						level : level,
						domain : domain,
						introduction : introduction
					},
					success : function(json) {
						if (json) {
							noty({"text" : "新增讲师成功！","layout" : "top","type" : "success","timeout" : "2000"});
							$.ims.trainingteacher.refreshTrainingTeacherDatatable();
						}
					}
				});
			}else if(this.otype == 1){
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/training/teacher/create",
					dataType : "json",
					data : {
						id : id,
						userId : userId,
						level : level,
						domain : domain,
						introduction : introduction
					},
					success : function(json) {
						if (json) {
							noty({"text" : "编辑讲师成功！","layout" : "top","type" : "success","timeout" : "2000"});
							$.ims.trainingteacher.refreshTrainingTeacherDatatable();
						}
					}
				});
			}
			$("#traingteacher_modal").modal('hide');
		},
}