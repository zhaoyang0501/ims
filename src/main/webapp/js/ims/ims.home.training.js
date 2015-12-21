jQuery.ims.traininghome = {
		trainingDetailDataTable : null,
		
		/**
		 * 刷新培训详细信息table
		 */
		refreshTrainingDetailDataTable : function(){
			if(this.trainingDetailDataTable != null){
				var oSettings = this.trainingDetailDataTable.fnSettings();
				this.trainingDetailDataTable.fnDraw(oSettings);
			}
		},
		
		/**
		 * 初始化培训详细信息table
		 */
		initTrainingDetailDataTable : function(){
			if (this.trainingDetailDataTable == null) {
				this.trainingDetailDataTable = $('#trainingDetail_dataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/home/training/detail/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var fromDate = $("#from_date").val();
										var toDate = $("#to_date").val();
										if (!!fromDate) {
											aoData.push({
												"name" : "fromDate",
												"value" : fromDate
											});
										}
										if (!!toDate) {
											aoData.push({
												"name" : "toDate",
												"value" : toDate
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
										"mDataProp" : "trainingName"
									}, {
										"mDataProp" : "trainingDate"
									}, {
										"mDataProp" : "trainingTime"
									}, {
										"mDataProp" : "hours"
									}, {
										"mDataProp" : "user"
									}, {
										"mDataProp" : "remarks"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}]
								});
			} else {
				this.refreshTrainingDetailDataTable();
			}
		},
}