jQuery.ims.weekReportExport = {
	dailyReportSearchDataTable:null,
	dailyReportType:null,
	projectStepMap:null,
	exportWeekReport:function(){
		if($("#week").val()=='')
			noty({"text":"请选择周次","layout":"top","type":"success","timeout":"2000"});
		else
			window.open ($.ims.getContextPath() + "/dailyReport/weekReportExport/export/"+$("#week").val());
	},
	/***
	 * 初始化代码对照表
	 */
	initCodeMap:function(){
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/getProjectSteps",
				dataType: "json",
				async:false,
				success: function(json){
					jQuery.ims.weekReportExport.projectStepMap=json.datas;
				}
			});
		 $.ajax({
				url: $.ims.getContextPath()+"/dailyReport/dailyReport/getDailyTypeCode",
				dataType: "json",
				async:false,
				success: function(json){
					jQuery.ims.weekReportExport.dailyReportType=json.datas;
				}
			});
	},
	/****
	 * 初始化周次下拉框
	 */
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
	initSearchDataTable : function() {
		if (this.dailyReportSearchDataTable == null) {
			this.dailyReportSearchDataTable = $('#dt_dailyReport').dataTable({
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
				"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/weekReportExport/queryAll",
				"fnDrawCallback" : function(oSettings) {
					$('[rel="popover"],[data-rel="popover"]').popover();
				},
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var week = $("#week").val();
					if (!!week) {
						aoData.push({
							"name" : "weekId",
							"value" : week
						});
					}
					$.ajax({
						"dataType" : 'json',
						"type" : "POST",
						"url" : sSource,
						"data" : aoData,
						"success" : function(data){
							jQuery.ims.weekReportExport.initCodeMap();
							fnCallback(data);
						}
					});
				},
				"aoColumns" : [{
					"mDataProp" : "reportDate"
				},{
					"mDataProp" : "week"
				},{
					"mDataProp" : "type"
				}, {
					"mDataProp" : "project"
				}, {
					"mDataProp" : "summary"
				}, {
					"mDataProp" : "difficulty"
				}, {
					"mDataProp" : "spendHours"
				}, {
					"mDataProp" : "projectStep"
				},{
					"mDataProp" : "createDate"
				}, {
					"mDataProp" : "user"
				} ],
				"aoColumnDefs" : [ 
				{
					'aTargets' : [2 ],
					'fnRender' : function(oObj, sVal) {
						return jQuery.ims.weekReportExport.dailyReportType[sVal];
					}
			   },{
					'aTargets' : [ 3 ],
					'fnRender' : function(oObj, sVal) {
						return sVal==""?"":sVal.projectName;
					}
				},{
					'aTargets' : [ 4 ],
					'fnRender' : function(oObj, sVal) {
						return  "<span  data-rel='popover' data-content='" + sVal + "' title='日报详情'>" +
						"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
						}
			   },{
					'aTargets' : [ 5 ],
					'fnRender' : function(oObj, sVal) {
						return  "<span  data-rel='popover' data-content='" + sVal + "' title='异常/难点'>" +
						"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
						}
			   },{
					'aTargets' : [7 ],
					'fnRender' : function(oObj, sVal) {
						return sVal==''?"": jQuery.ims.weekReportExport.projectStepMap[sVal];
					}
			   },
			   {
					'aTargets' : [9 ],
					'fnRender' : function(oObj, sVal) {
						return sVal==""?"":sVal.chinesename;
					}
				},
				 {
					'aTargets' : [ '_all' ],
					'bSortable' : false,
					'sClass' : 'center'
				}]

			});
		} else {
			var oSettings = this.dailyReportSearchDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.dailyReportSearchDataTable.fnDraw(oSettings);
		}

	}
};