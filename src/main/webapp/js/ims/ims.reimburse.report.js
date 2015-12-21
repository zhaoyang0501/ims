jQuery.ims.reimburseReport = {
		reimburseReportByUserTable:null,
		reimburseReportByProjectTable:null,
		initReimburseReportByUserTable:function() {
			if($("#date_byuser").val()==""){
				noty({"text":"请选择统计月份","layout":"top","type":"warning","timeout":"4000"});
				return false;
			}
			if (this.reimburseReportByUserTable == null) {
				this.reimburseReportByUserTable = $('#table_byUser').dataTable(
						{
							"sDom" : "<'span12'T><'row-fluid'<'span5'l>r>t<'row-fluid'<'span12 center'p>>",
							 "oTableTools": {
						        	 "aButtons": [
						             "copy",
						             "xls"
						             ],
						            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
						        },
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
							"sServerMethod" : "POST",
							"bProcessing" : true,
							"bRetrieve" : true,
							"bDestory" : true,
							"bAutoWidth" : false,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + "/reimburse/report/reportByUser",
							"fnServerData" : function(sSource, aoData, fnCallback) {
								var date = $("#date_byuser").val();
								if (!!date) {
									aoData.push({
										"name" : "month",
										"value" : date
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
							"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
								var reimburseMoney = 0;         
								 for ( var i=0 ; i<aaData.length ; i++ ) {
									 reimburseMoney += aaData[i].reimburseMoney*1.0;           
								 }
								$("#foot_total_user").html(reimburseMoney);
							},
							"aoColumns" : [ {
							   "mDataProp" : "user.dept.name"
							},
							{
								"mDataProp" : "user.chinesename"
							}, {
								"mDataProp" : "reimburseMoney"
							}, {
								"mDataProp" : ""
							} ],
							"aoColumnDefs" : [
									{
										'aTargets' : [ '_all' ],
										'bSortable' : false,
										'sClass' : 'center'
									} ]

						});
			} else {
				var oSettings = this.reimburseReportByUserTable.fnSettings();
				this.reimburseReportByUserTable.fnDraw(oSettings);
			}
		},
		initReimburseReportByProjectTable:function() {
			if($("#date_byproject").val()==""){
				noty({"text":"请选择统计月份","layout":"top","type":"warning","timeout":"4000"});
				return false;
			}
			if (this.reimburseReportByProjectTable == null) {
				this.reimburseReportByProjectTable = $('#table_byProject').dataTable(
						{
							"sDom" : "<'span12'T><'row-fluid'<'span5'l>r>t<'row-fluid'<'span12 center'p>>",
							 "oTableTools": {
						        	 "aButtons": [
						             "copy",
						             "xls"
						             ],
						            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
						        },
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
							"sServerMethod" : "POST",
							"bProcessing" : true,
							"bRetrieve" : true,
							"bDestory" : true,
							"bAutoWidth" : false,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + "/reimburse/report/reportByProject",
							"fnServerData" : function(sSource, aoData, fnCallback) {
								var date = $("#date_byproject").val();
								var project = $("#project").val();
								if (!!date) {
									aoData.push({
										"name" : "month",
										"value" : date
									});
								}
								if (!!project) {
									aoData.push({
										"name" : "projectId",
										"value" : project
									});
								}
								$.ajax({
									"dataType" : 'json',
									"type" : "POST",
									"url" : sSource,
									"data" : aoData,
									"success" : function(data){
										fnCallback(data);
										jQuery.ims.common.tableRowspan("#table_byProject",1);
									}
								});
							},
							"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
								var reimburseMoney = 0;         
								 for ( var i=0 ; i<aaData.length ; i++ ) {
									 reimburseMoney += aaData[i].reimburseMoney*1.0;           
								 }
								$("#foot_total_project").html(reimburseMoney);
								
							},
							"aoColumns" : [ {
							   "mDataProp" : "projectName"
							},
							{
								"mDataProp" : "reimburseDate"
							}, {
								"mDataProp" : "reimburseType",
							}, {
								"mDataProp" : "users"
							}, {
								"mDataProp" : "userNumber"
							}, {
								"mDataProp" : "standard"
							},
							{
								"mDataProp" : "reimburseMoney"
							} ],
							"aoColumnDefs" : [
				                  {
							            'aTargets' : [ 2 ],
										'fnRender' : function(oObj, sVal) {
											return sVal==2?"晚餐":"午餐";
										}
							        },
									{
										'aTargets' : [ '_all' ],
										'bSortable' : false,
										'sClass' : 'center'
									} ]

						});
			} else {
				var oSettings = this.reimburseReportByProjectTable.fnSettings();
				this.reimburseReportByProjectTable.fnDraw(oSettings);
			}
		}
}