jQuery.ims.weekReport = {
		curWeekId:null,
		/**当前weekid*/
		weekPoint:null,
		weekReportDataTable:null,
		projectStepMap:null,
		weekreportForm:null,
		initProjectStepMap:function(){
			 $.ajax({
					url: $.ims.getContextPath()+"/dailyReport/dailyReport/getProjectSteps",
					dataType: "json",
					async:false,
					success: function(json){
						jQuery.ims.weekReport.projectStepMap=json.datas;
					}
				});
		},
		showSubmitWeekReportModal:function(){
			if($.ims.weekReport.weekPoint==null)
				noty({"text":"：请选择要提交的周次","layout":"top","type":"info","timeout":"2000"});
			else
				$("#myWeekReport").modal('show');
		},
		submitWeekReport:function(){
			if(!$.ims.weekReport.weekreportForm.form()) return ;
			 $.ajax({
				 	type: "post",
					url: $.ims.getContextPath()+"/dailyReport/weekReport/submitWeekReport/"+$.ims.weekReport.weekPoint,
					dataType: "json",
					data: {
						remark:$("#remark").val()
					},
					async:false,
					success: function(json){
						if (json.status == "success"){
							jQuery.ims.weekReport.initTableCaption(json.weekConfig,json.weekReport);
							noty({"text":"提交成功！","layout":"top","type":"success","timeout":"2000"});
						}
						else{
							noty({"text":"错误："+json.msg,"layout":"top","type":"error","timeout":"2000"});
						}
						$("#myWeekReport").modal('hide');
					}
				});	
		},
		/***
		 * 显示表头信息
		 */
		initTableCaption:function(weekConfig,weekReport){
			$("#dateArea").html(weekConfig.startDate +" ~ "+weekConfig.endDate);
			$("#weekNum").html(weekConfig.year+" 年，第"+weekConfig.weekNum+" 周");
			if(weekReport!=null&&weekReport.state!=null&&weekReport.state==1&&weekReport.step!='提交周报'){
				$("#state").html('<span class="badge badge-important" style="width:35px;">审批中</span>');
				$("#btnCreate").attr('disabled', true);
			}else if(weekReport!=null&&weekReport.state!=null&&weekReport.state==4){
				$("#state").html('<span class="badge badge-success" style="width:35px;">已审批</span>');
				$("#btnCreate").attr("disabled", true);
			}else{
				$("#state").html("<span class='badge' style='width:35px;'>未提交</span>");
				$("#btnCreate").removeAttr("disabled");
			}
		},
		initcurWeekId:function(){
			 $.ajax({
					url: $.ims.getContextPath()+"/dailyReport/weekReport/getWeekId",
					dataType: "json",
					async:false,
					success: function(json){
						if (json.status == "success"){
							$.ims.weekReport.curWeekId=json.weekid;
							$.ims.weekReport.weekPoint=json.weekid;
						}
						else{
							noty({"text":"错误："+json.msg,"layout":"top","type":"error","timeout":"2000"});
						}
					}
				});	
		},
		/***
		 * 当前周
		 */
		queryCurrentWeek:function(){
			if(this.curWeekId==null){
				this.initcurWeekId();
			}
			this.weekPoint=this.curWeekId;
			this.queryWeekReport();
		},
		/***
		 * 上一周
		 */
		queryLastWeek:function(){
			if(this.curWeekId==null){
				this.initcurWeekId();
			}
			this.weekPoint--;
			this.queryWeekReport();
		},
		/****
		 * 下一周
		 */
		queryNextWeek:function(){
			if(this.curWeekId==null){
				this.initcurWeekId();
			}
			this.weekPoint++;
			this.queryWeekReport();
		},
		/***
		 * 查询指定日期周报
		 */
		queryWeekReport : function() {
			if (this.weekReportDataTable == null) {
				this.weekReportDataTable = $('#dt_weekReport').dataTable({
					"sDom" : "t<'row-fluid'<'span12 center'p>>",
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
					"bProcessing" : false,
					"bRetrieve" : true,
					"bDestory" : true,
					"bAutoWidth" : false,
					"bSort" : false,
					"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
						var iTotalMarket = 0;         
						 for ( var i=0 ; i<aaData.length ; i++ ) {
							 if(aaData[i].type!='请假')
								 iTotalMarket += aaData[i].spendHours*1;           
						 }
						$("#foot_total").html(iTotalMarket + " Hours");
					},

					"sAjaxSource" : $.ims.getContextPath() + "/dailyReport/weekReport/getweekReport?weekId="+this.weekPoint,
					"fnServerData" : function(sSource, aoData, fnCallback) {
						$.ajax({
							"dataType" : 'json',
							"url" : sSource,
							"data" : aoData,
							"async":false,
							"success" :function(data){
								if(data.weekConfig==null){
									noty({"text":"错误：没有该周次的配置信息","layout":"top","type":"error"});
									return;
								}
								jQuery.ims.weekReport.initProjectStepMap();
								jQuery.ims.weekReport.initTableCaption(data.weekConfig,data.weekReport);
								fnCallback(data);
							}
						});
					},
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
						jQuery.ims.weekReport.table_rowspan("#dt_weekReport",1);
					},
					"aoColumns" : [{
						"mDataProp" : "reportDate"
					}, {
						"mDataProp" : "week"
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
					"aoColumnDefs" : [
							{
								'aTargets' : [ 3 ],
								'fnRender' : function(oObj, sVal) {
									return sVal == "" ? "": sVal.projectName;
								}
							},
							{
								'aTargets' : [ 4 ],
								'fnRender' : function(oObj, sVal) {
									return "<span  data-rel='popover' data-content='"+ sVal+ "' title='日报详情'>"
											+ (sVal.length > 10 ? sVal.substring(0,10)+ "...": sVal)+ "</span>";
								}
							},
							{
								'aTargets' : [ 5 ],
								'fnRender' : function(oObj, sVal) {
									return "<span  data-rel='popover' data-content='"+ sVal+ "' title='异常/难点'>"
											+ (sVal.length > 10 ? sVal.substring(0,10)+ "...": sVal)+ "</span>";
								}
							},
							{
								'aTargets' : [7 ],
								'fnRender' : function(oObj, sVal) {
									return sVal==''?"": jQuery.ims.weekReport.projectStepMap[sVal];
								 }
						   },
							{
								'aTargets' : [9 ],
								'fnRender' : function(oObj, sVal) {
									return sVal == "" ? "": sVal.chinesename;
								}
							}, {
								'aTargets' : [ '_all' ],
								'bSortable' : false,
								'sClass' : 'center'
							} ]

				});
			} else {
				var oSettings = this.weekReportDataTable.fnSettings();
				oSettings.sAjaxSource=$.ims.getContextPath() + "/dailyReport/weekReport/getweekReport?weekId="+this.weekPoint;
				this.weekReportDataTable.fnDraw(oSettings);
				this.table_rowspan("#dt_weekReport",1);
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
	        }
		
};