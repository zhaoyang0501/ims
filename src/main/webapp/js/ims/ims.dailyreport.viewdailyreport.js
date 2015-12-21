jQuery.ims.dailyReportView = {
		dailyReportSearchDataTable:null,
		/**项目阶段map*/
		projectStepMap:null,
		initProjectStepMap:function(){
			 $.ajax({
					url: $.ims.getContextPath()+"/dailyReport/dailyReport/getProjectSteps",
					dataType: "json",
					async:false,
					success: function(json){
						jQuery.ims.dailyReportView.projectStepMap=json.datas;
					}
				});
		},
		
		initSearchDataTable : function() {
			if (this.dailyReportSearchDataTable == null) {
				this.dailyReportSearchDataTable = $('#dt_dailyReport_view').dataTable({
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
					"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/viewDailyReport/queryAll",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						var project = $("#project").val();
						var user = $("#user").val();
						var type = $("#type").val();
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
						if (!!project) {
							aoData.push({
								"name" : "project",
								"value" : project
							});
						}
						if (!!user) {
							aoData.push({
								"name" : "user",
								"value" : user
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
							"type" : "POST",
							"url" : sSource,
							"data" : aoData,
							"success" : function(data){
								jQuery.ims.dailyReportView.initProjectStepMap();
								fnCallback(data);
							}
						});
					},
					"aoColumns" : [ {
						"mDataProp" : "reportDate"
					}, {
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
					}, {
						"mDataProp" : "createDate"
					}, {
						"mDataProp" : "user"
					} ],
					"aoColumnDefs" : [{
						'aTargets' : [ 2 ],
						'fnRender' : function(oObj, sVal) {
							return sVal==""?"":sVal.projectName;
						}
					},{
						'aTargets' : [ 3 ],
						'fnRender' : function(oObj, sVal) {
							return  "<span  data-rel='popover' data-content='" + sVal + "' title='日报详情'>" +
							"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
							}
				   },{
						'aTargets' : [ 4 ],
						'fnRender' : function(oObj, sVal) {
							return  "<span  data-rel='popover' data-content='" + sVal + "' title='异常/难点'>" +
							"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
							}
				   }, {
						'aTargets' : [6 ],
						'fnRender' : function(oObj, sVal) {
							return sVal==''?"": jQuery.ims.dailyReportView.projectStepMap[sVal];
						}
				   },
				   {
						'aTargets' : [ 8 ],
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

		},
};