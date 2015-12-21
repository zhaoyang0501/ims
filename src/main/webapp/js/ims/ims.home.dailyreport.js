jQuery.ims.homedailyreport = {
		dailyReportDataTable : null,
		weekReportDataTable : null,
		
		/**
		 * 刷新table
		 */
		refreshDataTable : function(type){
			if(type == 1){
				if(this.dailyReportDataTable != null){
					var oSettings = this.dailyReportDataTable.fnSettings();
					this.dailyReportDataTable.fnDraw(oSettings);
					this.table_rowspan("dailyReportDataTable", 1);
				}
			}else if(type == 2){
				if(this.weekReportDataTable != null){
					var oSettings = this.weekReportDataTable.fnSettings();
					this.weekReportDataTable.fnDraw(oSettings);
					this.table_rowspan("weekReportDataTable", 1);
				}
			}
		},
		
		/**
		 * 初始化日报datatable
		 */
		initDailyReport : function(){
			$("#pie_div").show();
			if (this.dailyReportDataTable == null) {
				this.dailyReportDataTable = $('#dt_dailyreport').dataTable(
						{
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
							"sServerMethod" : "GET",
							"bProcessing" : true,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/viewDailyReport/query/owner",
							"fnDrawCallback" : function(oSettings) {
								$('[rel="popover"],[data-rel="popover"]').popover();
							},
							"fnServerData" : function(sSource, aoData, fnCallback) {
								var startDate = $("#daily_startDate").val();
								var endDate = $("#daily_endDate").val();
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
								"mDataProp" : "createDate"
							}],
							"aoColumnDefs" : [{
								'aTargets' : [ 2 ],
								'fnRender' : function(oObj, sVal) {
									return sVal==""?"":sVal.projectName;
								}
							}, {
								'aTargets' : [ '_all' ],
								'bSortable' : false,
								'sClass' : 'center'
							} ]
						});
			} else {
				this.refreshDataTable(1);
			}
			this.initDailyReportPie();
		},
		
		/**
		 * 初始化周报datatable
		 */
		initWeekReport : function(){
			if (this.weekReportDataTable == null) {
				this.weekReportDataTable = $('#dt_weekreport').dataTable({
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
					"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/viewWeekReport/queryAll",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#week_startDate").val();
						var endDate = $("#week_endDate").val();
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
							"type" : "POST",
							"url" : sSource,
							"data" : aoData,
							"success" : fnCallback
						});
					},
					"aoColumns" : [{
						"mDataProp" : "week.weekNum"
					}, {
						"mDataProp" : "week.startDate"
					}, {
						"mDataProp" : "week.endDate"
					}, {
						"mDataProp" : "createDate"
					}, {
						"mDataProp" : "approvals"
					}, {
						"mDataProp" : "rejects"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : "step"
					} ],
					"aoColumnDefs" : [{
						'aTargets' : [ 5 ],
						'fnRender' : function(oObj, sVal) {
							return "<span class='badge badge-info'>"+sVal+"</span>";
						}
					},{
						'aTargets' : [ 6 ],
						'fnRender' : function(oObj, sVal) {
							if (sVal == 1) {
								return "<span class='label label-info'>正在审批</span>";
							} else if (sVal == 4) {
								return "<span class='label label-success'>已结束</span>";
							} else{
								return "<span class='label'>未知状态"+sVal+"</span>";
							}
						}
					},{
						'aTargets' : [ 7 ],
						'fnRender' : function(oObj, sVal) {
							return "<a target='_blank' href='workflow/workflowgraph/"+oObj.aData.osworkflow+"'>"+sVal+"</a>";
						}
					},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			}else{
				this.refreshDataTable(2);
			}
		},
		
		table_rowspan :function(table_id, table_colnum) {
            table_firsttd = "";
            table_currenttd = "";
            table_SpanNum = 0;
            colnum_Obj = $(table_id + " tr td:nth-child(" + table_colnum + ")");
            colnum_Obj.each(function (i) {
                if (i == 0) {
                    table_firsttd = $(this);
                    table_SpanNum = 1;
 
                } else {
                    table_currenttd = $(this);
                    if (table_firsttd.text() == table_currenttd.text()) {
                        table_SpanNum++;
                        table_currenttd.hide(); 
                        table_firsttd.attr("rowSpan", table_SpanNum);
                    } else {
                        table_firsttd = $(this);
                        table_SpanNum = 1;
                    }
                }
            });
        },
        
        /**
         * 初始化饼图
         */
        initDailyReportPie : function(){
    		var pie = echarts.init(document.getElementById('pie_dailyreport'));
    		var startDate = $("#daily_startDate").val();
			var endDate = $("#daily_endDate").val();
			$.ajax({
    			type : "get",
    			url : $.ims.getContextPath() + "/home/dailyreport/pie",
    			data : {
    				startDate : startDate,
    				endDate : endDate
    			},
    			dataType : "json",
    			success : function(json) {
    				var adata = json.data;
    				pie.setOption({
    	    			title : {
    	    		        text: '日报类型分布图',
    	    		        x:'center'
    	    		    },
    	    		    tooltip : {
    	    		        trigger: 'item',
    	    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    		    },
    	    		    legend: {
    	    		        orient : 'vertical',
    	    		        x : 'left',
    	    		        data:['项目','培训','会议','部门管理','请假','加班','其他']
    	    		    },
    	    		    toolbox: {
    	    		        show : true,
    	    		        feature : {
    	    		            mark : {show: false},
    	    		            dataView : {show: false, readOnly: false},
    	    		            magicType : {
    	    		                show: true, 
    	    		                type: ['pie', 'funnel'],
    	    		                option: {
    	    		                    funnel: {
    	    		                        x: '25%',
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
    	    		            name:'访问来源',
    	    		            type:'pie',
    	    		            radius : '55%',
    	    		            center: ['50%', '60%'],
    	    		            data:adata
    	    		        }
    	    		    ]
    	    		});
    				
    				$.ims.homedailyreport.initStaticsData(json.staticsData);
    			}
    		});
        },
		
        /**
         * 初始化汇总数据
         */
        initStaticsData : function(data){
        	$("#dt_dailyreport_summary tbody").empty();
        	var staticsTable = $("#dt_dailyreport_summary");
        	var htmlData = "";
        	for (var i=0;i<data.length;i++)
        	{
        		var d = data[i];
        		htmlData +="<tr>"+
        		"<td>"+ d.name +"</td>"+
        		"<td>"+ d.value +"</td>"+
        		"<td>"+ d.percent +"</td>"+
        		"</tr>";
        	}
        	staticsTable.append(htmlData);
        },
}