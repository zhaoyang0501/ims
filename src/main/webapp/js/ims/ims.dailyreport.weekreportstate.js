jQuery.ims.weekReportState = {
	weekReportStateDataTable:null,
	initWeekSelect:function(){
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/dailyReport/weekReportState/allWeekConfig",
			dataType : "json",
			success : function(json) {
				$("#week").append("<option value=\"" + "0" + "\">" + "</option>");
				for (var i = 0; i < json.map.length; i++) {
					$("#week").append("<option value=\"" + json.map[i].id+ "\">" + json.map[i].startDate+" ~ " +json.map[i].endDate +"</option>");
				}
				/**查询当前周ID*/
				 $.ajax({
						url: $.ims.getContextPath()+"/dailyReport/weekReport/getWeekId",
						dataType: "json",
						async:false,
						success: function(json){
							if (json.status == "success"){
								$("#week").val(json.weekid);
							}
							else{
								noty({"text":"错误："+json.msg,"layout":"top","type":"error","timeout":"2000"});
							}
						}
					});	
				 $("#week").chosen({
						no_results_text : " ",
						placeholder_text:" ",
						disable_search_threshold : 5
					});
			}
		});
	},
	initWeekReportstate : function() {
		if (this.weekReportStateDataTable == null) {
			this.weekReportStateDataTable = $('#table_weekreportstate').dataTable(
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
						"bPaginate" : false,
						"bServerSide" : true,
						"sServerMethod" : "POST",
						"bProcessing" : true,
						"bRetrieve" : true,
						"bDestory" : true,
						"bAutoWidth" : false,
						"bSort" : false,
						"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/weekReportState/queryAll",
						"fnServerData" : function(sSource, aoData, fnCallback) {
							var week = $("#week").val();
							var state = $("#state").val();
							if (!!week) {
								aoData.push({
									"name" : "weekId",
									"value" : week
								});
							};
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
								"success" : function(data){
									$("#totalCount").html(data.totalCount);
									$("#submitCount").html(data.submitCount);
									$("#noSubmitCount").html(data.noSubmitCount);
									if($("#state").val()==''){
										$(".totalcount_span").css("display","block");
										$(".submitcount_span").css("display","block");
										$(".nosubmitcount_span").css("display","block");
									}else if($("#state").val()==0){
										$(".totalcount_span").css("display","none");
										$(".submitcount_span").css("display","none");
										$(".nosubmitcount_span").css("display","block");
									}
									else if($("#state").val()==1){
										$(".totalcount_span").css("display","none");
										$(".submitcount_span").css("display","block");
										$(".nosubmitcount_span").css("display","none");
									}
									fnCallback(data);
								}
							});
						},
						"aoColumns" : [ {
							"mDataProp" : "empnumber"
						}, {
							"mDataProp" : "name"
						}, {
							"mDataProp" : "state"
						} ],
						"aoColumnDefs" : [
									{
										'aTargets' : [ 2 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal == 0) {
												return "<span class='label label-default'>未提交</span>";
											}else {
												return "<span class='label label-success'>已提交</span>";
											}
										}
								},
								 {
									'aTargets' : [ '_all' ],
									'bSortable' : false,
									'sClass' : 'center'
								} ]

					});
		} else {
			var oSettings = this.weekReportStateDataTable.fnSettings();
			this.weekReportStateDataTable.fnDraw(oSettings);
		}
	}
};