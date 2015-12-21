jQuery.ims.weekReportView = {
		weekReportSearchDataTable:null,
		initSearchDataTable : function() {
			if (this.weekReportSearchDataTable == null) {
				this.weekReportSearchDataTable = $('#dt_weekReport_view').dataTable({
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
					"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/viewWeekReport/queryAll",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						var user = $("#user").val();
						var state = $("#state").val();
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
						if (!!user) {
							aoData.push({
								"name" : "user",
								"value" : user
							});
						}
						if (!!state) {
							aoData.push({
								"name" : "state",
								"value" : state
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
						"mDataProp" : "remark"
					}, {
						"mDataProp" : "approvals"
					}, {
						"mDataProp" : "rejects"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : "step"
					} ],
					"aoColumnDefs" : [
					{
						'aTargets' : [ 5],
						'fnRender' : function(oObj, sVal) {
							return  "<span  data-rel='popover' data-content='" + sVal + "' title='周总结'>" +
							"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
							}
					},
					{
						'aTargets' : [ 7 ],
						'fnRender' : function(oObj, sVal) {
							return "<span class='badge badge-important'>"+sVal+"</span>";
						}
					},{
						'aTargets' : [8],
						'fnRender' : function(oObj, sVal) {
							if (sVal == 1) {
								return "<span class='label label-info'>正在审批</span>";
							} else if (sVal == 4) {
								return "<span class='label label-success'>已结束</span>";
							} else{
								return "<span class='label'>未知状态"+sVal+"</span>";
							}
						}
					},{
						'aTargets' : [9 ],
						'fnRender' : function(oObj, sVal) {
							return "<a target='_blank' href='workflow/workflowgraph/"+oObj.aData.osworkflow+"'>"+sVal+"</a>";
						}
					},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.weekReportSearchDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.weekReportSearchDataTable.fnDraw(oSettings);
			}

		},
};