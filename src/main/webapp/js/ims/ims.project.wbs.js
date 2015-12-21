jQuery.ims.projectwbs = {

	projectWbsDataTable : null,
	/**
	 * 刷新wbs管理table
	 */
	refreshProjectWbsDataTable : function() {
		if (this.projectWbsDataTable != null) {
			var oSettings = this.projectWbsDataTable.fnSettings();
			this.projectWbsDataTable.fnDraw(oSettings);
		}
	},
	

	/**
	 * 创建wbs管理table
	 */
	initProjectWbsDataTable : function() {
		if (this.projectWbsDataTable == null) {
			this.projectWbsDataTable = $('#projectWps_dataTable')
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
								"sAjaxSource" : $.ims.getContextPath()
										+ '/project/wbs/list',
								"fnServerData" : function(sSource, aoData,
										fnCallback) {
									var pmId = $("#s_pm").val();
									var wbsName = $("#s_wbsName").val();
									if (!!pmId) {
										aoData.push({
											"name" : "pmId",
											"value" : pmId
										});
									}
									if (!!wbsName) {
										aoData.push({
											"name" : "wbsName",
											"value" : wbsName
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
									"mDataProp" : "wbsName"
								}, {
									"mDataProp" : "pm"
								}, {
									"mDataProp" : "phaseNode"
								}, {
									"mDataProp" : "phaseDR"
								}, {
									"mDataProp" : "planStart"
								}, {
									"mDataProp" : "planEnd"
								}, {
									"mDataProp" : "planHours"
								}, {
									"mDataProp" : "actualStart"
								}, {
									"mDataProp" : "actualEnd"
								}, {
									"mDataProp" : "actualHours"
								}, {
									"mDataProp" : "delayReason"
								}, {
									"mDataProp" : "complex"
								}, {
									"mDataProp" : "state"
								}, {
									"mDataProp" : "description"
								} ],
								"aoColumnDefs" : [
										{
											'aTargets' : [ '_all' ],
											'bSortable' : true,
											'sClass' : 'center'
										},
										{
											'aTargets' : [ 12 ],
											'fnRender' : function(oObj, sVal) {
												return '<span class="badge badge-warning">'+sVal+'</span>';
											}
										},{
											'aTargets' : [ 13 ],
											'fnRender' : function(oObj, sVal) {
												if (sVal == 0) {
													return "<span class='label'>新建</span>";
												} else if (sVal == 1) {
													return "<span class='label label-info'>进行中</span>";
												} else if (sVal == 2) {
													return "<span class='label label-success'>结束</span>";
												} else if (sVal == 3) {
													return "<span class='label label-inverse'>关闭</span>";
												}
											}
										} ]
							});
		} else {
			this.refreshProjectWbsDataTable();
		}
	},

}