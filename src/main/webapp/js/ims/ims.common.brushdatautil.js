jQuery.ims.commonbrushdata = {
		brushdataDataTable:null,
		/**初始化加班列表*/
		initbrushdataDataTable : function(){
			if (this.brushdataDataTable == null) {
				this.brushdataDataTable = $('#brushdataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span12'p>>",
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
									"sServerMethod" : "POST",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/getBrushData',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var begin = $("#begin").val();
										var user = $("#user").val();
										if (!!begin) {
											aoData.push({
												"name" : "start",
												"value" : begin
											});
										}
										if (!!user) {
											aoData.push({
												"name" : "user",
												"value" : user
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
										"mDataProp" : "personName"
									}, {
										"mDataProp" : "brushDate"
									}, {
										"mDataProp" : "brushData"
									} ,{
										"mDataProp" : "attenceAbsentee.absenteeTime"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}]
								});
			} else {
				var ajaxSource =$.ims.getContextPath() + '/getBrushData';
				var oSettings = this.brushdataDataTable.fnSettings();
				oSettings.sAjaxSource = ajaxSource;
				this.brushdataDataTable.fnDraw(oSettings);
			}
		},
}