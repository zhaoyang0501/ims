jQuery.ims.homeattence = {
		
		attenceDataTable : null,
		
		refreshAttenceDataTable : function(){
			var oSettings = this.attenceDataTable.fnSettings();
			this.attenceDataTable.fnDraw(oSettings);
		},
		
		initAttenceDataTable : function(){
			if(this.attenceDataTable==null){
				this.attenceDataTable = $("#attenceDataTable").dataTable({
					"oLanguage" : {
						"iDisplayLength": 50, 
						"aLengthMenu": [10,25,50,100],
						"sLengthMenu" : "每页显示 _MENU_ 条记录",
						"sZeroRecords" : "抱歉， 暂时没有记录",
						"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
						"sInfoEmpty" : "没有数据",
						"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
						"oPaginate" : {
							"sFirst" : " 首页 ",
							"sPrevious" : " 前一页 ",
							"sNext" : " 后一页 ",
							"sLast" : " 尾页 "
						}
					},
					"bServerSide" : true,
					"sServerMethod" : "GET",
					"bProcessing" : true,
					"bRetrieve" : true,
					"bDestory" : true,
					"bAutoWidth" : false,
					"bSort" : false,
					"bFilter":false,
					"sAjaxSource":$.ims.getContextPath() +'/home/attence/list',
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var fromDate = $("#fromDate").val();
						var toDate = $("#toDate").val();
						var type = $("#type").val();
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
						if (!!type) {
							aoData.push({
								"name" : "type",
								"value" : type
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
					"aoColumns":[
					             	{"mDataProp": "index" },
					             	{"mDataProp": "type" },
					             	{"mDataProp": "date" },
					             	{"mDataProp": "details" },
					             	{"mDataProp": "description" }
					            ]
				});
			}else{
				this.refreshAttenceDataTable();
			}
		},
}