jQuery.ims.dailyReportCreate= {
	/**保存或者修改标志*/
	isToSave:true,
	/**被操作的日志ID*/
	dailyReportId:"",
	
	dailyReportDataTable : null, 
	/**表单验证*/
	dailyreportForm:null,
	isCanEdit:true,
	/**项目阶段map*/
	projectStepMap:null,
	
	initProjectStepMap:function(){
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/getProjectSteps",
				dataType: "json",
				async:false,
				success: function(json){
					jQuery.ims.dailyReportCreate.projectStepMap=json.datas;
				}
			});
	},
	
	initIsCanEdit:function(date){
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/canEdit/"+date,
				dataType: "json",
				async:false,
				success: function(json){
					$.ims.dailyReportCreate.isCanEdit=json;
					if(!$.ims.dailyReportCreate.isCanEdit)
						$("#btnCreate").attr("disabled", true);
				}
			});	
	},
	initProjectSelect:function(){
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/getProjectsUserJoined",
				dataType: "json",
				success: function(json){
					for (var i = 0; i < json.length; i++) {
						$("#project").append("<option value='" + json[i].id + "'>" + json[i].projectName + "</option>");
					}
					$("#project").chosen({
						no_results_text : " ",
						placeholder_text:" ",
						disable_search_threshold : 5
					});
				}
			});	
	},
	initDailyReport : function(date) {
		if (this.dailyReportDataTable == null) {
			this.dailyReportDataTable = $('#table_dailyReport_create').dataTable(
					{
						"sDom" : "t<'row-fluid'<'span12 center'p>>",
						"sPaginationType" : "bootstrap",
						"oLanguage" : {
							"sLengthMenu" : "每页显示 _MENU_ 条记录",
							"sZeroRecords" : "抱歉， 暂时没有记录",
							"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
							"sSearch" : "",
							"sInfoEmpty" : "没有数据",
							"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "前一页",
								"sNext" : "后一页",
								"sLast" : "尾页"
							}
						},
						"bPaginate" : false,
						"bServerSide" : true,
						"sServerMethod" : "GET",
						"bRetrieve" : true,
						"bDestory" : true,
						"bAutoWidth" : false,
						"bSort" : false,
						"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/dailyReport/queryByCreateDate/" + date + "/",
						"fnServerData" : function(sSource, aoData, fnCallback) {
							$.ajax({
								"dataType" : 'json',
								"url" : sSource,
								"data" : aoData,
								"async":false,
								"success" :function(data){
									jQuery.ims.dailyReportCreate.initProjectStepMap();
									fnCallback(data);
								}
							});
						},
						"fnDrawCallback" : function(oSettings) {
							$('[rel="popover"],[data-rel="popover"]').popover();
						},
						"aoColumns" : [ {
							"mDataProp" : "id"
						}, {
							"mDataProp" : "reportDate"
						}, {
							"mDataProp" : "type"
						}, {
							"mDataProp" : "project"
						}, {
							"mDataProp" : "summary"
						}, {
							"mDataProp" : "difficulty"
						}, {
							"mDataProp" : "spendHours"
						}, {
							"mDataProp" : "projectStep"
						}, {
							"mDataProp" : "createDate"
						}, {
							"mDataProp" : "id"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [ 0 ],
									'bVisible' : false
								},{
									'aTargets' : [ 3 ],
									'fnRender' : function(oObj, sVal) {
										return sVal==""?"":sVal.projectName;
									}
								},{
									'aTargets' : [ 4 ],
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='日报详情'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
							   },{
									'aTargets' : [ 5 ],
									'fnRender' : function(oObj, sVal) {
										return  "<span  data-rel='popover' data-content='" + sVal + "' title='异常/难点'>" +
										"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
										}
							   },{
									'aTargets' : [7 ],
									'fnRender' : function(oObj, sVal) {
										return sVal==''?"": jQuery.ims.dailyReportCreate.projectStepMap[sVal];
									 }
							   },{
									'aTargets' : [ 9 ],
									'fnRender' : function(oObj, sVal) {
										var operate = "";
										var operate = '<div class="btn-group">';
										if ($.ims.dailyReportCreate.isCanEdit) {
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a onclick="$.ims.dailyReportCreate.showUpdateDailyReportModal('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.dailyReportCreate.deleteDailyReport('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
										} else {
											operate += '<span class="label label-success" icon="icon-align-justify">不可编辑</span>';
										}
										return operate;
									}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var ajaxSource = $.ims.getContextPath() + "/dailyReport/dailyReport/queryByCreateDate/" + date + "/";
			var oSettings = this.dailyReportDataTable.fnSettings();
			oSettings.sAjaxSource = ajaxSource;
			this.dailyReportDataTable.fnDraw(oSettings);
		}
	},
	/**
	 * 显示新增日志弹出框
	 */
	showNewDailyReportModal : function(id) {
		this.isToSave=true;
		this.cleanFormDate();
		$("#myDailyReport").modal('show');
	},
	/***
	 * 显示修改弹出框
	 */
	showUpdateDailyReportModal : function(id) {
		this.isToSave=false;
		this.dailyReportId=id;
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/getDailyReport/"+id,
				dataType: "json",
				success: function(json){
					if(json!=null){
						$("#type").val(json.type);
						$("#project").val(json.project==null?"":json.project.id);
						$("#spendHours").val(json.spendHours);
						$("#projectStep").val(json.projectStep);
						$("#summary").val(json.summary);
						$("#difficulty").val(json.difficulty);
						jQuery.ims.common.setchosenvalue("type",json.type);
						jQuery.ims.common.setchosenvalue("project",json.project==null?"":json.project.id);
						jQuery.ims.common.setchosenvalue("spendHours",json.spendHours);
						jQuery.ims.common.setchosenvalue("projectStep",json.projectStep);
						$.ims.dailyReportCreate.dailyReportTypeChange();
						$("#myDailyReport").modal('show');
					}
					else
						noty({"text":"查找不到该日志!","layout":"top","type":"error","timeout":"2000"});
				}
			});	
	},
	/**
	 * 保存或者修改一条日报
	 * 
	 */
	saveOrUpdateDailyReport: function(){
		if(!$.ims.dailyReportCreate.dailyreportForm.form()) return ;
		if(this.isToSave){
			 $.ajax({
					type: "post",
					url: $.ims.getContextPath()+"/dailyReport/dailyReport/saveDailyReport",
					dataType: "json",
					data: {
						reportDate:$("#reportDate").val(),
						type:$("#type").val(),
						projectId:$("#project").val(),
						spendHours:$("#spendHours").val(),
						projectStep:$("#projectStep").val(),
						summary:$("#summary").val(),
						difficulty:$("#difficulty").val()
					},
					complete:function(XMLHttpRequest, textStatus) {
						  $("#submit_buttion").removeAttr("disabled");
				 	},
				 	beforeSend:function (XMLHttpRequest) {
				 		 $("#submit_buttion").attr("disabled","disabled");
				 	},
					success: function(json){
						if(json.id!=null){
							noty({"text":"保存成功!","layout":"top","type":"success","timeout":"2000"});
//							$("#myDailyReport").modal('hide');
							$.ims.dailyReportCreate.cleanFormDate();
							$.ims.dailyReportCreate.initDailyReport($("#reportDate").val());
						}
						
						else
							noty({"text":"内部错误!","layout":"top","type":"error","timeout":"2000"});
					}
				});	
		}else{
			$.ajax({
				type: "post",
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/updateDailyReport",
				dataType: "json",
				data: {
					id:$.ims.dailyReportCreate.dailyReportId,
					type:$("#type").val(),
					projectId:$("#project").val(),
					spendHours:$("#spendHours").val(),
					projectStep:$("#projectStep").val(),
					summary:$("#summary").val(),
					difficulty:$("#difficulty").val()
				},
				success: function(json){
					if(json.id!=null){
						noty({"text":"修改成功!","layout":"top","type":"success","timeout":"2000"});
						$("#myDailyReport").modal('hide');
						$.ims.dailyReportCreate.initDailyReport($("#reportDate").val());
					}
					
					else
						noty({"text":"内部错误!","layout":"top","type":"error","timeout":"2000"});
				}
			});	
		}
		
	},
	/***
	 * 删除日志
	 */
	deleteDailyReport : function(id) {
		 bootbox.confirm("确认删除吗?", function (result) {
            if(result){
            	$.ajax({
					type : "get",
					url: $.ims.getContextPath()+"/dailyReport/dailyReport/deleteDailyReport/"+id,
					dataType : "json",
					success : function(json) {
						if(json.state){
							noty({"text":"删除成功!","layout":"top","type":"success","timeout":"2000"});
							$.ims.dailyReportCreate.initDailyReport($("#reportDate").val());
						}else{
							noty({"text":json.msg,"layout":"top","type":"error","timeout":"2000"});
						}
						
					}
				});
            }
         });
	},
	/***
	 * 清除表单数据
	 */
	cleanFormDate:function(){
		$("#type").val("1");
		$("#project").val("");
		$("#projectStep").val("");
		$("#spendHours").val("0.5");
		jQuery.ims.common.setchosenvalue("type","1");
		jQuery.ims.common.setchosenvalue("project","");
		jQuery.ims.common.setchosenvalue("projectStep","");
		jQuery.ims.common.setchosenvalue("spendHours","0.5");
		$("#control_project").show();
		$("#control_projectStep").show();
		$("#control_spendhours").show();
		$("#control_difficulty").show();
		$("#summary").val("");
		$("#difficulty").val("");
	},
	/**日志类型选择框改变*/
	dailyReportTypeChange:function(){
		/**加班 项目*/
		if($("#type").val()=='1'||$("#type").val()=='7'){
			$("#control_project").show();
			$("#control_projectStep").show();
			$("#control_spendhours").show();
			$("#minhour").hide();
			$("#control_difficulty").show();
		}
		/**请假*/
		else if($("#type").val()=='6'){
			$("#control_project").hide();
			$("#control_spendhours").show();
			$("#control_difficulty").hide();
			$("#control_projectStep").hide();
		}else{
			$("#control_project").hide();
			$("#control_projectStep").hide();
			$("#control_spendhours").show();
		}
	}
};