jQuery.ims.approveWeekReport= {
	weekReportToApprove:null,
	weekReportApproved:null,
	doApprove:function(weekid,actionid,approvals){
		 $.ajax({
				url: $.ims.getContextPath()+"/approve/weekReport/doApprove/"+weekid+"/"+actionid,
				dataType: "json",
				type: "POST",
				data:{"approvals":$("#approvals").val()},
				success: function(json){
					if (json.status == "success"){
						  window.location.href=$.ims.getContextPath()+"/approve/weekReport";
					}
					else{
						noty({"text":"错误："+json.msg,"layout":"top","type":"error"});
					}
				}
			});	
	},
	initweekReportToApprove : function() {
		if (this.weekReportToApprove == null) {
			this.weekReportToApprove = $('#table_weekreport_toapprove').dataTable(
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
						"sAjaxSource" : $.ims.getContextPath() + "/approve/weekReport/weekReportToApproveList",
						"aoColumns" : [  {
							"mDataProp" : "id"
						},{
							"mDataProp" : "creater.chinesename"
						}, {
							"mDataProp" : "week.weekNum"
						}, {
							"mDataProp" : "week.startDate"
						}, {
							"mDataProp" : "week.endDate"
						}, {
							"mDataProp" : "createDate"
						}, {
							"mDataProp" : "approvals"
						}, {
							"mDataProp" : "rejects"
						},{
							"mDataProp" : "state"
						} ,{
							"mDataProp" : "step"
						} ,{
							"mDataProp" : "id"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [ 0 ],
									'bVisible' : false
								},{
									'aTargets' : [ 7 ],
									'fnRender' : function(oObj, sVal) {
										return "<span class='badge badge-info'>"+sVal+"</span>";
									}
								},{
									'aTargets' : [ 8 ],
									'fnRender' : function(oObj, sVal) {
										if (sVal == 1) {
											return "<span class='label label-warning'>正在审批</span>";
										} else if (sVal == 4) {
											return "<span class='label label-success'>已结束</span>";
										} else{
											return "<span class='label'>未知状态"+sVal+"</span>";
										}
									}
								},{
									'aTargets' : [ 9 ],
									'fnRender' : function(oObj, sVal) {
										return "<a target='_blank' href='approve/weekReport/workflowgraph/"+oObj.aData.osworkflow+"'>"+sVal+"</a>";
									}
								},
								{
									'aTargets' : [ 10 ],
									'fnRender' : function(oObj, sVal) {
										return  "<a class=\"btn btn-info\" href=\""+$.ims.getContextPath() +
										"/approve/weekReport/weekReportToApprove/"+ sVal+"\" ><i class=\"icon-edit icon-white\"></i>详情</a>&nbsp;";
									}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var ajaxSource = $.ims.getContextPath() + "/approve/weekReport/weekReportToApproveList";
			var oSettings = this.weekReportToApprove.fnSettings();
			oSettings.sAjaxSource = ajaxSource;
			this.weekReportToApprove.fnDraw(oSettings);
		}
	},
	initweekReportApproved : function() {
		if (this.weekReportApproved == null) {
			this.weekReportApproved = $('#table_weekreport_approved').dataTable(
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
						"sAjaxSource" : $.ims.getContextPath() + "/approve/weekReport/weekReportApprovedList",
						"aoColumns" : [  {
							"mDataProp" : "id"
						},{
							"mDataProp" : "creater.chinesename"
						}, {
							"mDataProp" : "week.weekNum"
						}, {
							"mDataProp" : "week.startDate"
						}, {
							"mDataProp" : "week.endDate"
						}, {
							"mDataProp" : "createDate"
						}, {
							"mDataProp" : "approvals"
						}, {
							"mDataProp" : "rejects"
						},{
							"mDataProp" : "state"
						},{
							"mDataProp" : "step"
						} ],
						"aoColumnDefs" : [
								{
									'aTargets' : [ 0 ],
									'bVisible' : false
								},{
									'aTargets' : [ 7 ],
									'fnRender' : function(oObj, sVal) {
										return "<span class='badge badge-info'>"+sVal+"</span>";
									}
								},{
									'aTargets' : [ 8 ],
									'fnRender' : function(oObj, sVal) {
										if (sVal == 1) {
											return "<span class='label label-warning'>正在审批</span>";
										} else if (sVal == 4) {
											return "<span class='label label-success'>已结束</span>";
										} else{
											return "<span class='label'>未知状态"+sVal+"</span>";
										}
									}
								},{
									'aTargets' : [ 9 ],
									'fnRender' : function(oObj, sVal) {
										return "<a target='_blank' href='approve/weekReport/workflowgraph/"+oObj.aData.osworkflow+"'>"+sVal+"</a>";
									}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var ajaxSource = $.ims.getContextPath() + "/approve/weekReport/weekReportApprovedList";
			var oSettings = this.weekReportApproved.fnSettings();
			oSettings.sAjaxSource = ajaxSource;
			this.weekReportApproved.fnDraw(oSettings);
		}
	}
	
};