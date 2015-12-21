jQuery.ims.attencereport = {

		attenceAccountReportDataTable : null,	// 考勤统计报表
		/**
		 * 刷新请假数据报表。
		 * 
		 */
		dayoffreportDataTable : null,
		refreshDayoffreportDataTable : function(){
			if (this.dayoffreportDataTable != null) {
				var oSettings = this.dayoffreportDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.dayoffreportDataTable.fnDraw(oSettings);
			}
		},
		
        /**
		 * 初始化页面请假数据报表。
		 */
		initDayoffreportDataTable : function(){
			if(this.dayoffreportDataTable==null){
				this.dayoffreportDataTable = $("#dayoff_dataTable")
				.dataTable(
						{
							"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
							 "oTableTools": {
						        	 "aButtons": [
						             "copy",
						             "xls"
						             ],
						            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
						        },
							"oLanguage" : {
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
					"iDisplayLength" : 500,
					"aLengthMenu" : [ 10, 25, 50, 100,500 ],
					"bServerSide" : true,
					"sServerMethod" : "GET",
					"bProcessing" : true,
					"bRetrieve" : true,
					"bDestory" : true,
					"bAutoWidth" : false,
					"bSort" : false,
					"bFilter":false,                        
					"sAjaxSource":$.ims.getContextPath() +'/attence/report/dayoff/list',
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var sstartDate = $("#sstartDate").val();
						var sendDate = $("#sendDate").val();
						var pmId = $("#s_pm").val();
						if (!!sstartDate) {
							aoData.push({
								"name" : "sstartDate",
								"value" : sstartDate
							});
						}
						if (!!sendDate) {
							aoData.push({
								"name" : "sendDate",
								"value" : sendDate
							});
						}
						if (!!pmId) {
							aoData.push({
								"name" : "pmId",
								"value" : pmId
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
					             	{"mDataProp": "userName" },
					             	{"mDataProp": "deptName" },
					             	{"mDataProp": "startTime" },
					             	{"mDataProp": "endTime" },
					            	{"mDataProp": "spendHours" },
					             	{"mDataProp": "dayoffType" },
					             	{"mDataProp": "remark" }
					            ],
				   "aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}
			                   ]
				});
			}else{
				this.refreshDayoffreportDataTable();
			}
		},
		
		/**
		 * 请假图形统计表报。
		 */
		initDayoffReport : function(){
			var rsstartDate = $("#rsstartDate").val();
			var rsendDate = $("#rsendDate").val();
			$.ajax({
				type : "GET",
				url : $.ims.getContextPath() + "/attence/report/dayoff/chart",
				dataType : "json",
				data:{
					rsstartDate: rsstartDate,
					rsendDate: rsendDate
				},
			success:function(json){
				var deptData = json.deptData;	// 部门数据
				var dayoffData = json.dayoffData;	// 调休假
				var leaveData = json.leaveData;	// 事假
				var bornData = json.bornData;	// 产假
				var feedData = json.feedData;	// 哺乳假
				var pat_leaveData = json.pat_leaveData;	// 陪产假
				var deptDayoffChart = echarts.init(document.getElementById("deptDayoff"));
				var option = {
					    title : {
					        text: '各科室请休假工时示意图',
					        
					    },
					    tooltip : {
					        trigger: 'axis',
					      axisPointer : {type : 'shadow'
								}
					    },
					    legend: {
					        data:['调休','事假','产假','哺乳假','看护假']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            magicType : {show: true, type: []},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            axisLabel:{rotate:20, // 刻度旋转45度角
											textStyle:{
											fontSize:14,
									     	color:"red"
									       }
								          },
					            data : deptData
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'调休',
					            type:'bar',
					            data: dayoffData,
					            itemStyle: {normal: {label: { show: true }}}
					        },
					        {
					            name:'事假',
					            type:'bar',
					            data:leaveData,
					            itemStyle: {normal: {label: { show: true }}}
					        },
					        {
					            name:'产假',
					            type:'bar',
					            data:bornData,
					            itemStyle: {normal: {label: { show: true }}}
					        },
					        {
					            name:'看护假',
					            type:'bar',
					            data:pat_leaveData,
					            itemStyle: {normal: {label: { show: true }}}
					        },
					        {
					            name:'哺乳假',
					            type:'bar',
					            data:feedData,
					            itemStyle: {normal: {label: { show: true }}}
					        }
					    ]
					};
				deptDayoffChart.setOption(option);
				}
		});
	},
	
	
	
	/**
	 * 刷新漏打卡数据报表。
	 * 
	 */
	absenteereportDataTable : null,
	refreshAbsenteereportDataTable : function(){
		if (this.absenteereportDataTable != null) {
			var oSettings = this.absenteereportDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.absenteereportDataTable.fnDraw(oSettings);
		}
	},
	
    /**
	 * 初始化页面漏刷卡数据报表。
	 */
	initAbsenteereportDataTable : function(){
		if(this.absenteereportDataTable==null){
			this.absenteereportDataTable = $("#absentee_dataTable")
			.dataTable(
					{
						"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
						 "oTableTools": {
					        	 "aButtons": [
					             "copy",
					             "xls"
					             ],
					            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
					        },
						"oLanguage" : {
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
				"iDisplayLength" : 500,
				"aLengthMenu" : [ 10, 25, 50, 100,500 ],
				"bServerSide" : true,
				"sServerMethod" : "GET",
				"bProcessing" : true,
				"bRetrieve" : true,
				"bDestory" : true,
				"bAutoWidth" : false,
				"bSort" : false,
				"bFilter":false,                        
				"sAjaxSource":$.ims.getContextPath() +'/attence/report/absentee/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var sstartDate = $("#sstartDate").val();
					var sendDate = $("#sendDate").val();
					var pmId = $("#s_pm").val();
					if (!!sstartDate) {
						aoData.push({
							"name" : "sstartDate",
							"value" : sstartDate
						});
					}
					if (!!sendDate) {
						aoData.push({
							"name" : "sendDate",
							"value" : sendDate
						});
					}
					if (!!pmId) {
						aoData.push({
							"name" : "pmId",
							"value" : pmId
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
				             	{"mDataProp": "userName" },
				             	{"mDataProp": "deptName" },
				             	{"mDataProp": "absenteeDate" },
				             	{"mDataProp": "absenteeTime" },
				            	{"mDataProp": "absenteeType" },
				             	{"mDataProp": "remark" }
				            ],
			   "aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}
		                   ]
			});
		}else{
			this.refreshAbsenteereportDataTable();
		}
	},
	
	
	/**
	 * 漏打卡统计图形报表。（补签卡）
	 */
	initAbsenteeReport:function(){
		var rsstartDate = $("#rsstartDate").val();
		var rsendDate = $("#rsendDate").val();
		$.ajax({
			type : "GET",
			url : $.ims.getContextPath() + "/attence/report/Absentee/chart",
			dataType : "json",
			data:{
				rsstartDate: rsstartDate,
				rsendDate: rsendDate
			},
		success:function(json){
			var deptData = json.deptData;	// 部门数据
			var absenteeData = json.absenteeData;	// 刷卡次数
			var deptAbsenteeElement = document.getElementById("deptAbsentee");
			var deptAbsenteeChart = echarts.init(deptAbsenteeElement);
				option = {
					    title : {
					        text: '补签卡(人次)',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'left',
					        data:deptData
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: false},
					            magicType : {
					                show: true, 
					                type: ['pie'],
					                option: {
					                    funnel: {
					                        x: '35%',
					                        width: '50%',
					                        funnelAlign: 'left',
					                        max: 1024
					                    }
					                }
					            },
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
					        {
					            name:'补签卡次数',
					            type:'pie',
					            radius : '55%',
					            center: ['70%', '60%'],
					            itemStyle: {
					                normal: {
					                    label: {
					                        show: true,
					                        formatter: '{b} : {c} ({d}%)'
					                    },
					                    labelLine: {
					                        show: true
					                    }
					                }
					            },
					            data:absenteeData
					        }
					    ]
					};
				deptAbsenteeChart.setOption(option);
			}
		});
	},
	
	
	
	/**
	 * 刷新加班数据报表。
	 * 
	 */
	overtimereportDataTable : null,
	refreshOvertimereportDataTable : function(){
		if (this.overtimereportDataTable != null) {
			var oSettings = this.overtimereportDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.overtimereportDataTable.fnDraw(oSettings);
		}
	},
	
    /**
	 * 初始化页面加班数据报表。
	 */
	initOvertimereportDataTable : function(){
		if(this.overtimereportDataTable==null){
			this.overtimereportDataTable = $("#overtime_DataTable")
			.dataTable(
					{
						"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
						 "oTableTools": {
					        	 "aButtons": [
					             "copy",
					             "xls"
					             ],
					            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
					        },
						"oLanguage" : {
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
				"iDisplayLength" : 500,
				"aLengthMenu" : [ 10, 25, 50, 100,500 ],
				"bServerSide" : true,
				"sServerMethod" : "GET",
				"bProcessing" : true,
				"bRetrieve" : true,
				"bDestory" : true,
				"bAutoWidth" : false,
				"bSort" : false,
				"bFilter":false,                        
				"sAjaxSource":$.ims.getContextPath() +'/attence/report/overtime/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var sstartDate = $("#sstartDate").val();
					var sendDate = $("#sendDate").val();
					var pmId = $("#s_pm").val();
					if (!!sstartDate) {
						aoData.push({
							"name" : "sstartDate",
							"value" : sstartDate
						});
					}
					if (!!sendDate) {
						aoData.push({
							"name" : "sendDate",
							"value" : sendDate
						});
					}
					if (!!pmId) {
						aoData.push({
							"name" : "pmId",
							"value" : pmId
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
				             	{"mDataProp": "overtimeDate" },
				             	{"mDataProp": "userName" },
				             	{"mDataProp": "deptName" },
				             	{"mDataProp": "overtimeType" },
				            	{"mDataProp": "startTime" },
				             	{"mDataProp": "endTime" },
				            	{"mDataProp": "checkHours" },
				             	{"mDataProp": "projectName" },
				            	{"mDataProp": "remark" }
				            ],
			   "aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								},{
									'aTargets' : [4],
									'fnRender':function(oObj,sVal){
										var str = "";
										if(sVal == 1){
											str = "平时";
										}else if(sVal == 2){
											str = "周末";
										}else
											str = "假日";
										return str;
								    }
								}
		                   ]
			});
		}else{
			this.refreshOvertimereportDataTable();
		}
	},
	
	
	
	/**
	 * 加班图形统计表报。
	 */
	initOvertimeReport : function(){
		var rsstartDate = $("#rsstartDate").val();
		var rsendDate = $("#rsendDate").val();
		$.ajax({
			type : "GET",
			url : $.ims.getContextPath() + "/attence/report/overtime/chart",
			dataType : "json",
			data:{
				rsstartDate: rsstartDate,
				rsendDate: rsendDate
			},
		success:function(json){
			var deptData = json.deptData;	// 部门数据
			var dayOvertimeData = json.dayOvertimeData;	// 平时加班
			var weekOvertimeData = json.weekOvertimeData;	// 周末加班
			var holidayData = json.holidayData;	// 周末加班
			var deptOvertimeElement = document.getElementById("deptOvertime");
			var deptOvertimeChart = echarts.init(deptOvertimeElement);
			var option = {
				    title : {
				        text: '各科室加班工时示意图',
				        
				    },
				    tooltip : {
				        trigger: 'axis',
				      axisPointer : {type : 'shadow'
							}
				    },
				    legend: {
				        data:['平时','周末','节假日']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				        	mark: true,
				        	dataView: { readOnly: false },
				        	magicType : {show: true, type: []},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            axisLabel:{rotate:20, // 刻度旋转45度角
										textStyle:{
										fontSize:14,
								     	color:"red"
								       }
							          },
				            data : deptData
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				              {
						            name:'平时',
						            type:'bar',
						            itemStyle: {normal: {label: { show: true }}},
						            data: dayOvertimeData
						        },
						        {
						            name:'周末',
						            type:'bar',
						            itemStyle: {normal: {label: { show: true }}},
						            data:weekOvertimeData
						        },{
						            name:'节假日',
						            type:'bar',
						            itemStyle: {normal: {label: { show: true }}},
						            data:holidayData
						        }
				    ]
				};	                    
			deptOvertimeChart.setOption(option);
				}
		});
	},
	
	
	/**
	 * 刷新出差记录数据报表。
	 * 
	 */
	travelreportDataTable : null,
	refreshTravelreportDataTable : function(){
		if (this.travelreportDataTable != null) {
			var oSettings = this.travelreportDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.travelreportDataTable.fnDraw(oSettings);
		}
	},
	
    /**
	 * 初始化页面出差记录数据报表。
	 */
	initTravelreportDataTable : function(){
		if(this.travelreportDataTable==null){
			this.travelreportDataTable = $("#travel_dataTable")
			.dataTable(
					{
						"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
						 "oTableTools": {
					        	 "aButtons": [
					             "copy",
					             "xls"
					             ],
					            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
					        },
						"oLanguage" : {
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
				"iDisplayLength" : 500,
				"aLengthMenu" : [ 10, 25, 50, 100,500 ],
				"bServerSide" : true,
				"sServerMethod" : "GET",
				"bProcessing" : true,
				"bRetrieve" : true,
				"bDestory" : true,
				"bAutoWidth" : false,
				"bSort" : false,
				"bFilter":false,                        
				"sAjaxSource":$.ims.getContextPath() +'/attence/report/travel/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var sstartDate = $("#sstartDate").val();
					var sendDate = $("#sendDate").val();
					var pmId = $("#s_pm").val();
					if (!!sstartDate) {
						aoData.push({
							"name" : "sstartDate",
							"value" : sstartDate
						});
					}
					if (!!sendDate) {
						aoData.push({
							"name" : "sendDate",
							"value" : sendDate
						});
					}
					if (!!pmId) {
						aoData.push({
							"name" : "pmId",
							"value" : pmId
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
				             	{"mDataProp": "userName" },
				            	{"mDataProp": "startTime" },
				            	{"mDataProp": "startTimeType" },
				             	{"mDataProp": "endTime" },
				             	{"mDataProp": "endTimeType" },
				            	{"mDataProp": "address" },
				            	{"mDataProp": "reason" }
				            ],
			   "aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}
		                   ]
			});
		}else{
			this.refreshTravelreportDataTable();
		}
	},
	
	
	
	/**
	 * 刷新调休时数统计报表。
	 * 
	 */
	attenceStatisticsDataTable : null,
	refresAhattenceStatisticsDataTable : function(){
		if (this.attenceStatisticsDataTable != null) {
			var oSettings = this.attenceStatisticsDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.attenceStatisticsDataTable.fnDraw(oSettings);
		}
	},
	
    /**
	 * 初始化页面调休时数统计报表。
	 */
	initAttenceStatisticsDataTable : function(){
		if(this.attenceStatisticsDataTable==null){
			this.attenceStatisticsDataTable = $("#attenceStatistics_dataTable")
			.dataTable(
					{
						"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
						 "oTableTools": {
					        	 "aButtons": [
					             "copy",
					             "xls"
					             ],
					            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
					        },
						"oLanguage" : {
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
				"iDisplayLength" : 500,
				"aLengthMenu" : [ 10, 25, 50, 100,500 ],
				"bServerSide" : true,
				"sServerMethod" : "GET",
				"bProcessing" : true,
				"bRetrieve" : true,
				"bDestory" : true,
				"bAutoWidth" : false,
				"bSort" : false,
				"bFilter":false,                        
				"sAjaxSource":$.ims.getContextPath() +'/attence/report/attencestatistics/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var sendDate = $("#sendDate").val();
					var pmId = $("#s_pm").val();
					if (!!sendDate) {
						aoData.push({
							"name" : "sendDate",
							"value" : sendDate
						});
					}
					if (!!pmId) {
						aoData.push({
							"name" : "pmId",
							"value" : pmId
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
				             	{"mDataProp": "user.chinesename" },
				            	{"mDataProp": "user.dept.name" },
				            	{"mDataProp": "lastRest" },
				             	{"mDataProp": "currentIncrease" },
				             	{"mDataProp": "currentReduce" },
				            	{"mDataProp": "currentRest" },
				            	
				            	{"mDataProp": "dayoff10LastRest" },
				             	{"mDataProp": "dayoff10CurrentIncrease" },
				             	{"mDataProp": "dayoff10" },
				            	{"mDataProp": "dayoff10CurrentRest" },
				            	{"mDataProp": "dayoff40" },
				            	{"mDataProp": "dayoff50" },
				            	{"mDataProp": "dayoff60" },
				            	{"mDataProp": "dayoff70" },
				            	{"mDataProp": "dayoff80" },
				            	{"mDataProp": "dayoff100" },
				            	{"mDataProp": "dayoff110" },
				            	{"mDataProp": "dayoff120" },
				            	{"mDataProp": "overTime" }
				            ],
			   "aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}
		                   ]
			});
		}else{
			this.refresAhattenceStatisticsDataTable();
		}
	},
	
	/**
	 * 考勤统计报表
	 */
	initAttenceReport : function(){
		if (this.attenceAccountReportDataTable == null) {
			this.attenceAccountReportDataTable = $('#attencereportDataTable')
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
								"sAjaxSource" : $.ims.getContextPath() + '/attence/report/attencecount/list',
								"fnServerData" : function(sSource, aoData, fnCallback) {
									var startDate = $("#startDate").val();
									var endDate = $('#endDate').val();
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
									$.ajax({
										"dataType" : 'json',
										"type" : "GET",
										"url" : sSource,
										"data" : aoData,
										"success" : fnCallback
									});
								},
								"aoColumns" : [ {
									"mDataProp" : "deptName"
								}, {
									"mDataProp" : "dayOvertime"
								}, {
									"mDataProp" : "weekOvertime"
								}, {
									"mDataProp" : "holidayOvertime"
								}, {
									"mDataProp" : "privateLeave"
								}, {
									"mDataProp" : "maternityLeave"
								}, {
									"mDataProp" : "feedingOff"
								}, {
									"mDataProp" : "paternityLeave"
								}, {
									"mDataProp" : "dayoffFlirt"
								}, {
									"mDataProp" : "absenteeCount"
								}],
								"aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}]
							});
		} else {
			var oSettings = this.attenceAccountReportDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.attenceAccountReportDataTable.fnDraw(oSettings);
		}
	},
	
	/**
	 * 刷新个人加班请假数统计报表。
	 * 
	 */
	countStateDataTable : null,
	refreshCountStatereportDataTable : function(){
		if (this.countStateDataTable != null) {
			var oSettings = this.countStateDataTable.fnSettings();
			oSettings._iDisplayStart = 0;
			this.countStateDataTable.fnDraw(oSettings);
		}
	},
	
    /**
	 * 初始化页面个人加班请假统计报表。
	 */
	initCountStateDataTable : function(){
		if(this.countStateDataTable==null){
			this.countStateDataTable = $("#countState_DataTable")
			.dataTable(
					{
						"sDom" : "<'row-fluid'r>t<'row-fluid'<'span6'>>",
						"bServerSide" : true,
						"sServerMethod" : "GET",
						"bRetrieve" : true,
						"bDestory" : true,
						"bAutoWidth" : false,
						"bSort" : false,
						"bFilter":false,                        
						"sAjaxSource":$.ims.getContextPath() +'/attence/report/countstate/list',
						"fnServerData" : function(sSource, aoData, fnCallback) {
					var sstartDate = $("#sstartDate").val();
					var sendDate = $("#sendDate").val();
					var pmId = $("#s_pm").val();
					if (!!sstartDate) {
						aoData.push({
							"name" : "sstartDate",
							"value" : sstartDate
						});
					}
					if (!!sendDate) {
						aoData.push({
							"name" : "sendDate",
							"value" : sendDate
						});
					}
					if (!!pmId) {
						aoData.push({
							"name" : "pmId",
							"value" : pmId
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
				             	{"mDataProp": "userName" },
				            	{"mDataProp": "overtimeCount" },
				            	{"mDataProp": "dayoffCount" },
				             	{"mDataProp": "overtimeHours" },
				             	{"mDataProp": "projectName" },
				            ],
			   "aoColumnDefs" : [ {
									'aTargets' : [ '_all' ],
									'bSortable' : true,
									'sClass' : 'center'
								}
		                   ]
			});
		}else{
			this.refreshCountStatereportDataTable();
		}
	},
}