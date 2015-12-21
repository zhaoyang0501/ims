jQuery.ims.approve= {
	toApprove:null,
	approved:null,
	reimburseform:null,	
	initToApprove : function() {
		if (this.toApprove == null) {
			this.toApprove = $('#table_toapprove').dataTable(
					{
						"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
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
						"bAutoWidth" : false,
						"iDisplayLength" : 10,
						"aLengthMenu" : [ 5, 10, 25, 50],
						"bServerSide" : true,
						"sServerMethod" : "POST",
						"bProcessing" : true,
						"bSort" : false,
						"sAjaxSource" : $.ims.getContextPath() + "/workflow/toApproveList",
						"fnServerData" : function(sSource, aoData, fnCallback) {
							var workFlowName = $("#toapprove_workFlowName").val();
							var startDate = $("#toapprove_startDate").val();
							var endDate = $("#toapprove_endDate").val();
							var creater = $("#creater").val();
							var sn = $("#sn").val();
							if (!!workFlowName) {
								aoData.push({
									"name" : "workFlowName",
									"value" : workFlowName
								});
							}
							if (!!startDate) {
								aoData.push({
									"name" : "startDate",
									"value" : startDate
								});
							}
							if (!!endDate) {
								aoData.push({
									"name" : "endDate",
									"value" : endDate
								});
							}
							if (!!creater) {
								aoData.push({
									"name" : "craterId",
									"value" : creater
								});
							}
							if (!!sn) {
								aoData.push({
									"name" : "sn",
									"value" : sn
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
							"mDataProp" : "wfentry.name"
						},{
							"mDataProp" : "wfentry.wfentryExtend.sn"
						},{
							
							"mDataProp" : "wfentry.wfentryExtend.creater.chinesename"
						},{
							"mDataProp" : "startDate"
						}, {
							"mDataProp" : "name"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [ 0],
									'fnRender' : function(oObj, sVal) {
										return  "<a style='font-weight: bold;'  href=\""+$.ims.getContextPath() +
										"/workflow/toapprove/goApprove/"+ oObj.aData.wfentry.id+"\" >"+sVal+"</a>&nbsp;";
									}
								},
								{
									'aTargets' : [ 4],
									'fnRender' : function(oObj, sVal) {
										return "<a target='_blank' href='workflow/workflowgraph/"+oObj.aData.wfentry.id+"'>"+sVal+"</a>";
									}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var ajaxSource = $.ims.getContextPath() + "/workflow/toApproveList";
			var oSettings = this.toApprove.fnSettings();
			oSettings.sAjaxSource = ajaxSource;
			this.toApprove.fnDraw(oSettings);
		}
	},
	initapproved : function() {
		if (this.approved == null) {
			this.approved = $('#table_approved').dataTable(
					{
						"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
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
						"bAutoWidth" : false,
						"iDisplayLength" : 10,
						"aLengthMenu" : [ 5, 10, 25, 50],
						"bServerSide" : true,
						"sServerMethod" : "POST",
						"bProcessing" : true,
						"bSort" : false,
						"sAjaxSource" : $.ims.getContextPath() + "/workflow/approvedList",
						"fnServerData" : function(sSource, aoData, fnCallback) {
							var workFlowName = $("#approved_workFlowName").val();
							var startDate = $("#approved_startDate").val();
							var endDate = $("#approved_endDate").val();
							var workflowState = $("#approved_workFlowState").val();
							var creater = $("#creater").val();
							var sn = $("#sn").val();
							if (!!workFlowName) {
								aoData.push({
									"name" : "workFlowName",
									"value" : workFlowName
								});
							}
							if (!!startDate) {
								aoData.push({
									"name" : "startDate",
									"value" : startDate
								});
							}
							if (!!endDate) {
								aoData.push({
									"name" : "endDate",
									"value" : endDate
								});
							}
							if (!!workflowState) {
								aoData.push({
									"name" : "workflowState",
									"value" : workflowState
								});
							}
							if (!!creater) {
								aoData.push({
									"name" : "craterId",
									"value" : creater
								});
							}
							if (!!sn) {
								aoData.push({
									"name" : "sn",
									"value" : sn
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
							"mDataProp" : "wfentry.name"
						},{
							"mDataProp" : "wfentry.wfentryExtend.sn"
						},{
							
							"mDataProp" : "wfentry.wfentryExtend.creater.chinesename"
						},{
							"mDataProp" : "startDate"
						}, {
							"mDataProp" : "name"
						}, {
							"mDataProp" : "wfentry.state"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [0],
									'fnRender' : function(oObj, sVal) {
										return  "<a style='font-weight: bold;'  href=\""+$.ims.getContextPath() +
										"/workflow/approved/goApprove/"+ oObj.aData.wfentry.id+"\" >"+sVal+"</a>&nbsp;";
									}
								},
								{
									'aTargets' : [ 4],
									'fnRender' : function(oObj, sVal) {
										return "<a target='_blank' href='workflow/workflowgraph/"+oObj.aData.wfentry.id+"'>"+sVal+"</a>";
									}
								},
								{
									'aTargets' : [ 5 ],
									'fnRender' : function(oObj, sVal) {
										if (sVal == "4") 
											return '<span class="label label-success">已办结</span>';
										else if (sVal == "3") 
											return '<span class="label badge-warning">作废</span>';
										else
											return '<span class="label label-Info">进行中</span>';
									}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var ajaxSource = $.ims.getContextPath() + "/workflow/approvedList";
			var oSettings = this.approved.fnSettings();
			oSettings.sAjaxSource = ajaxSource;
			this.approved.fnDraw(oSettings);
		}
	},
	submitReimburseform:function(actionid){
		$("#reimburseform input[name='actionId']").val(actionid);
		$("#reimburseform").submit();
	},
	submitWeekReportform:function(actionid){
		if(actionid=='13'&&$("#approvals").val()==""){
			noty({"text":"请填写审批意见！","layout":"top","type":"error","timeout":"2000"});
			return;
		}
		$("#weekreportform input[name='actionId']").val(actionid);
		$("#weekreportform").submit();
	}
};