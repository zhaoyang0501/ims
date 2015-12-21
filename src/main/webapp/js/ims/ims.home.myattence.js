jQuery.ims.myattence = {
		myattenceDataTable : null,
		absenteeMap:null,
		dayoffMap:null,
		/**事假统计报表*/
		dayOffChart : null,
		/**报表参数*/
		dayOffChartOption : {
			series : [
			          {
			              name:'请假统计报表',
			              type:'pie',
			              radius : ['50%', '70%'],
			              itemStyle : {
			                  normal : {
			                      label : {
			                          show : true
			                      },
			                      labelLine : {
			                          show : true
			                      }
			                  },
			                  emphasis : {
			                      label : {
			                          show : false,
			                          position : 'center',
			                          textStyle : {
			                              fontSize : '30',
			                              fontWeight : 'bold'
			                          }
			                      }
			                  }
			              },
			              data:[  ]
			          }
			      ],
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	itemWidth:10,
		        orient : 'vertical',
		        x : 'left',
		        formatter: function (name){ 
		        	var lable="";
		        	$.each(jQuery.ims.myattence.dayOffChartOption.series[0].data, function(i, n){
		        		 if(n.name==name)
		        			 lable= name+":"+n.value;
		        		});
		        	return lable;
		        },
		        data:[]
		    },
		    toolbox: {
		        show : false
		    }
		},
		/***
		 * 打卡明细表格
		 */
		initMyattenceDataTable : function(date_query,obj){
			if(date_query.charAt(5)==0){
				$(".cur_month").html(date_query.substr(-1)+"月");
			}else{
				$(".cur_month").html(date_query.substr(-2)+"月");
			}
			var that=this;
			$(".bill_choose_l li").removeClass("now_m");
			$(obj).addClass("now_m");
			if (this.myattenceDataTable == null) {
				this.myattenceDataTable = $('#myattenceDataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'r>t<'row-fluid'>",
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
									"sServerMethod" : "GET",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/home/myattence/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										aoData.push({
											"name" : "date",
											"value" : jQuery.ims.myattence.initMyattenceDataTable.arguments[0]
										});
										$.ajax({
											"dataType" : 'json',
											"type" : "GET",
											"url" : sSource,
											"data" : aoData,
											"success" : function(data){
												fnCallback(data);
												/**渲染echart，调休统计*/
												if(data.attence){
													$(".bill_list1").show();
													$("#current_increase").html(data.attence.currentIncrease+" 工时");
													$("#current_reduce").html(data.attence.currentReduce+" 工时");
													$("#current_rest").html(data.attence.currentRest+" 工时");
													$("#last_rest").html(data.attence.lastRest+" 工时");
													$("#current_overtime").html(data.attence.overTime+" 工时");
												}else{
													$(".bill_list1").hide();
												}
												
												if(data.attenceCount){
													$.ims.myattence.dayOffChart = echarts.init(document.getElementById("dayoff_chart"));
													var legend_data=[]; 
													for(var i=0; i<data.attenceCount.length; i++){
														legend_data.push(data.attenceCount[i].name);
									                }
													$.ims.myattence.dayOffChartOption.legend.data=legend_data;
													$.ims.myattence.dayOffChartOption.series[0].data=data.attenceCount;
													$.ims.myattence.dayOffChart.setOption($.ims.myattence.dayOffChartOption);
												}else{
													$.ims.myattence.dayOffChartOption.legend.show=false;
													$.ims.myattence.dayOffChart.setOption($.ims.myattence.dayOffChartOption);
												}
												
												if(data.rank==0){
													$(".business-icon2").css("display","none");
													}
												else{
													$(".business-icon2").css("display","inline-block");
													$("#rank").html(data.rank);
												}
												
											}
										});
									},
									"aoColumns" : [{
										"mDataProp" : "personNo"
									}, {
										"mDataProp" : "personName"
									}, {
										"mDataProp" : "brushDate"
									}, {
										"mDataProp" : "week"
									}, {
										"mDataProp" : "brushData"
									},{
										"mDataProp" : "remark"
									}, {
										"mDataProp" : "state"
									}, {
										"mDataProp" : "id"
									} ],
									"aoColumnDefs" : [
										{
											'aTargets' : [6],
											'fnRender' : function(oObj, sVal) {
												curState=oObj.aData.state;
												
												if(oObj.aData.state==null) return ;
												var labelstates="";
												var states=oObj.aData.state.split(",");
												for(j=0;j<states.length;j++){
													if(states[j]!=''){
														 if(states[j]=="80")
															 labelstates=labelstates+"   <span class='label label-info'>节假日</span>";
														 if(states[j]=="70")
															 labelstates=labelstates+ "<span class='label label-info'>迟到</span>";
														 if(states[j]=="60")
															 labelstates=labelstates+"   <span class='label label-important'>未知</span>";
														 else if(states[j]=="30")
															 labelstates=labelstates+"   <span class='label label-info'>漏打卡</span>";
														 else if(states[j]=="50")
															 labelstates=labelstates+"   <span class='label label-info'>加班</span>";
														 else if(states[j]=="40")
															 labelstates=labelstates+"   <span class='label label-info'>出差</span>";
														 else if(states[j]=="20")
															 labelstates=labelstates+"   <span class='label label-info'>请假</span>";
														 else if(states[j]=="10")
															 labelstates=labelstates+"   <span class='label label-info'>正班</span>";
													}
												}
												return labelstates;
												/* if(oObj.aData.state=="80")
														return "<span class='label label-info'>节假日</span>";
												 if(oObj.aData.state=="70")
														return "<span class='label label-info'>迟到</span>";
												 if(oObj.aData.state=="60")
													return "<span class='label label-important'>未知</span>";
												 else if(oObj.aData.state=="30")
													return "<span class='label label-info'>漏打卡</span>";
												 else if(oObj.aData.state=="50")
														return "<span class='label label-info'>加班</span>";
												 else if(oObj.aData.state=="40")
														return "<span class='label label-info'>出差</span>";
												 else if(oObj.aData.state=="20")
														return "<span class='label label-info'>请假</span>";
												 else if(oObj.aData.state=="10")
														return "<span class='label label-info'>正班</span>";
												 else return oObj.aData.state;*/
											}
										},
										{
											'aTargets' : [7],
											'fnRender' : function(oObj, sVal) {
												return "<a href='javascript:void(0)' onclick='$.ims.myattence.showModal("+oObj.aData.id+")'><i class='icon-zoom-in'></i></a>";
											}
										},
										{
												'aTargets' : [ '_all' ],
												'bSortable' : false,
												'sClass' : 'center'
									}]
								});
			} else {
				var ajaxSource =$.ims.getContextPath() + '/home/myattence/list';
				var oSettings = this.myattenceDataTable.fnSettings();
				oSettings.sAjaxSource = ajaxSource;
				this.myattenceDataTable.fnDraw(oSettings);
			}
		},
		/**初始化代码表*/
		initCodeMap:function(){
			 $.ajax({
					url: $.ims.getContextPath()+"/home/myattence/getAttenceCode",
					dataType: "json",
					async:false,
					success: function(json){
						jQuery.ims.myattence.absenteeMap=json.absentee;
						jQuery.ims.myattence.dayoffMap=json.dayoff;
					}
				});
		},
		
		/**查看考勤明细*/
		showModal:function (id){
			 $.ajax({
					url: $.ims.getContextPath()+"/home/myattence/queryAttenceDetail/"+id,
					dataType: "json",
					async:false,
					success: function(json){
						$("#absentee_table").empty();
						$("#overtime_table").empty();
						$("#travel_table").empty();
						$("#dayoff_table").empty();
						/**漏打卡*/
						if(json.attenceAbsentee){
							$("#absentee_table").append("<tr>"+
									"<td>"+json.attenceAbsentee.absenteeTime+"</td>"+
									"<td>"+$.ims.myattence.absenteeMap[json.attenceAbsentee.absenteeType]+"</td>"+
									"<td>"+json.attenceAbsentee.reason+"</td>"+
									"<td>"+json.attenceAbsentee.saveTime+"</td>"+
									"</tr>");
						}
						/**加班*/
						for(var i=0; i<json.attenceOverTimes.length; i++){
							$("#overtime_table").append("<tr>"+
									"<td>"+json.attenceOverTimes[i].startTime+"到"+json.attenceOverTimes[i].endTime+"</td>"+
									"<td>"+json.attenceOverTimes[i].checkHours+"小时</td>"+
									"<td>"+(json.attenceOverTimes[i].oaState==1?"<span class='label label-info'>已提交</span>":"<span class='label  label-important'>未提交</span>")+"</td>"+
									"<td>"+json.attenceOverTimes[i].saveTime+"</td>"+
									"</tr>");
		                }
						/**出差*/
						if(json.attenceTravel){
							$("#travel_table").append("<tr>"+
									"<td>"+json.attenceTravel.startTime+"到"+json.attenceTravel.endTime+"</td>"+
									"<td>"+json.attenceTravel.address+"</td>"+
									"<td>"+json.attenceTravel.reason+"</td>"+
									"<td>"+json.attenceTravel.saveTime+"</td>"+
									"</tr>");
						}
						/**请假*/
						for(var i=0; i<json.attenceDayoffs.length; i++){
							$("#dayoff_table").append("<tr>"+
									"<td>"+json.attenceDayoffs[i].startTime+"到"+json.attenceDayoffs[i].endTime+"</td>"+
									"<td>"+$.ims.myattence.dayoffMap[json.attenceDayoffs[i].dayoffType]+"</td>"+
									"<td>"+json.attenceDayoffs[i].spendHours+"</td>"+
									"<td>"+json.attenceDayoffs[i].saveTime+"</td>"+
									"</tr>");
		                }
					}
				});	
			$("#myAttence").modal('show');
		}
}