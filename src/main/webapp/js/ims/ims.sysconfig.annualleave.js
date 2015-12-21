jQuery.ims.annualleave= {
		annualleaveDataTable:null,
		initDatetable : function(){
			if (this.annualleaveDataTable == null) {
				this.annualleaveDataTable = $('#annualleaveDataTable')
						.dataTable(
								{
									"sDom" : "<'span6'r><'span6'T><'row-fluid't<'row-fluid'<'span6'>>",
									 "oTableTools": {
								        	 "aButtons": [
								             "copy",
								             "xls"
								             ],
								            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
								        },
									"bServerSide" : true,
									"sServerMethod" : "GET",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/annualleave/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var year = $("#year").val();
										var endDate = $('#endDate').val();
										if (!!year) {
											aoData.push({
												"name" : "year",
												"value" : year
											});
										}
										if (!!endDate) {
											aoData.push({
												"name" : "endDate",
												"value" : endDate
											});
										}
										$.ajax({
											"dataType" : 'json',
											"type" : "GET",
											"url" : sSource,
											"data" : aoData,
											"success" : function(data){
												fnCallback(data);
												$('.username').editable({
														validate: function(value) {
															var re =/^[1-9]+[0-9]*]*$/; 
															if (!re.test(value)) {
																return "请输入正整数";
															}
														}
												});
												
											}
										});
									},
									"aoColumns" : [ {
										"mDataProp" : "user.chinesename"
									}, {
										"mDataProp" : "year"
									}, {
										"mDataProp" : "lastRest"
									}, {
										"mDataProp" : "currentIncrease"
									}],
									"aoColumnDefs" : [ 
										{
											'aTargets' : [3 ],
											'fnRender' : function(oObj, sVal) {
												return "<a href='#' class='username' data-type='text' data-pk='"+oObj.aData.id+"' data-url='sysconfig/annualleave/update' data-title='输入年假时数'>"+sVal+"</a>";
											}
										},
									 {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}]
								});
			} else {
				var oSettings = this.annualleaveDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.annualleaveDataTable.fnDraw(oSettings);
			}
		},
}