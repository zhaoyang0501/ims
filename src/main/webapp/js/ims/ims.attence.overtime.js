jQuery.ims.attenceOvertime = {
		overtimeForm:null,
		saveCheckTimeReportId:"",
		attenceOvertimeDataTable:null,
		reportDate:null,
		showCheckTimeModal:function(id,reportDate,user){
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/workflow/overtime/getOverTimeMaxHour",
				dataType : "json",
				data :{userid:user,curDate:reportDate},
				success : function(json) {
					if (json.success = 1) {
						/**1按月，2按年*/
						if(json.datas.type=='1')
							$("#overtime_tip").html("本月可申请"+json.datas.maxHours+"工时,本月已申请"+json.datas.nowHours);
						else
							$("#overtime_tip").html("今年可申请"+json.datas.maxHours+"工时,今年已申请"+json.datas.nowHours);
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
			this.cleanFormDate();
			this.reportDate=reportDate;
			this.saveCheckTimeReportId=id;
			if(moment(reportDate,"YYYY-MM-DD").weekday()>=1&&moment(reportDate,"YYYY-MM-DD").weekday()<=5)
				$("#type").val(1);
			else 
				$("#type").val(2);
			this.getOverTime();
			$("#timecheckmodal").modal('show');
		},
		
		/**查有没有保存过的工时核对*/
		getOverTime:function(){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/overtime/getOverTime/"+$.ims.attenceOvertime.saveCheckTimeReportId,
					success: function(json){
						if(json.code==1){
							$("#overTimeId").val(json.datas.id);
							$("#brushData").val(json.datas.brushRecord);
							$("#startTime").val(json.datas.startTime);
							$("#endTime").val(json.datas.endTime);
							$("#checkHours").val(json.datas.checkHours);
							//$("#type").val(json.datas.overtimeType);
							$("#oaState").val(json.datas.oaState==null?"2":json.datas.oaState);
						}else{
							$("#overTimeId").val("");
							$("#brushData").val("");
							$("#startTime").val("");
							$("#endTime").val("");
							$("#checkHours").val("");
							$("#oaState").val(2);
							jQuery.ims.attenceOvertime.getBrushCardData($.ims.attenceOvertime.saveCheckTimeReportId);
						}
					}
				});	
		},
		
		cleanFormDate:function(){
			$("#brushData").val("");
			$("#type").val("");
			$("#startTime").val("");
			$("#endTime").val("");
			$("#checkHours").val("");
		},
		/**获取刷卡数据*/
		getBrushCardData: function(id){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/overtime/getBrushCardData/"+id,
					success: function(json){
						if(json.code==1)
							$("#brushData").val(json.datas==null?"":json.datas.brushData);
						    $.ims.attenceOvertime.setCheckHoursAndTimes();
					}
				});	
		},
		 /*自动计算工时自动计算*/
		setCheckHoursAndTimes:function(){
		    var times=$.trim($("#brushData").val()).split(" ");
		    if(times.length>=2){
		    	var startTime=times[0];
		    	var endTime=times[times.length-1];
		    	$("#startTime").val("18:00");
		    	if($("#type").val()=='2'){
		    		if(startTime.length==5&&startTime.substr(-2)>30)
			    		$("#startTime").val(parseInt(startTime.substr(0,2))+1+":00");
			    	else if(startTime.length==5&&startTime.substr(-2)<=30)
			    		$("#startTime").val(startTime.substr(0,2)+":30");
			    	else if(startTime.length==5&&startTime.substr(-2)==0)
			    		$("#startTime").val(startTime.substr(0,2)+":00");
		    	}
		    		
		    	if(endTime.length==5&&endTime.substr(-2)>=30)
		    		$("#endTime").val(endTime.substr(0,2)+":30");
		    	else if(endTime.length==5&&endTime.substr(-2)<30)
		    		$("#endTime").val(endTime.substr(0,2)+":00");
		    	/**计算工时合计，如果是平时直接减*/
		    	
		    	if(moment($("#endTime").val(),"HH:mm").isBefore(moment($("#startTime").val(),"HH:mm"))){
		    		$("#checkHours").val("");
	    			return ;
	    		}
		    	if($("#type").val()=='1'){
		    		$("#checkHours").val(moment($("#endTime").val(),"HH:mm").diff(moment($("#startTime").val(),"HH:mm"), 'hours',true));
		    	}else{
		    		p1=moment("12:30","HH:mm");
		    		p2=moment("13:30","HH:mm");
		    		p3=moment("17:30","HH:mm");
		    		p4=moment("18:00","HH:mm");
		    		b=moment($("#endTime").val(),"HH:mm");
		    		a=moment($("#startTime").val(),"HH:mm");
		    		
		    		if(a.isBefore(p1)||a.isSame(p1)){
		    			
		    			if(b.isBefore(p1)||b.isSame(p1))
		    				$("#checkHours").val(b.diff(a, 'hours',true));
		    			else if(b.isBetween(p1,p2)||b.isSame(p2))
		    				$("#checkHours").val(p1.diff(a, 'hours',true));
		    			else if(b.isBetween(p2,p3)||b.isSame(p3))
		    				$("#checkHours").val(b.diff(a, 'hours',true)-1);
		    			else if(b.isBetween(p3,p4)||b.isSame(p4))
		    				$("#checkHours").val(p1.diff(a, 'hours',true)+4);
		    			else if(b.isAfter(p4))
		    				$("#checkHours").val(b.diff(a, 'hours',true)-1.5);
		    		}else if(a.isBetween(p1,p2)||a.isSame(p2)){
		    			 if(b.isBetween(p2,p3)||b.isSame(p3))
		    				$("#checkHours").val(b.diff(p2, 'hours',true));
		    			 if(b.isBetween(p3,p4)||b.isSame(p4))
		    				 $("#checkHours").val(4);
		    			else if(b.isAfter(p4))
		    				$("#checkHours").val(b.diff(p2, 'hours',true)-0.5);
		    		}
		    		else if(a.isBetween(p2,p3)||a.isSame(p3)){
		    			 if(b.isBefore(p3)||b.isSame(p3))
		    				$("#checkHours").val(b.diff(a, 'hours',true));
		    			 if(b.isBetween(p3,p4)||b.isSame(p4))
		    				$("#checkHours").val(p3.diff(a, 'hours',true));
		    			else if(b.isAfter(p4))
		    				$("#checkHours").val(b.diff(a, 'hours',true)-0.5);
		    		}
		    		else if(c.isBetween(p3,p4)||c.isSame(p4)){
		    			 if(b.isAfter(p4)||b.isSame(p4))
		    				$("#checkHours").val(b.diff(p4, 'hours',true));
		    		}
		    		else if(a.isAfter(p4)){
		    				$("#checkHours").val(b.diff(a, 'hours',true));
		    		}
		    	}
		    }
		},
		saveCheckTime: function(){
			if(!$.ims.attenceOvertime.overtimeForm.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/overtime/save",
					dataType: "json",
					data: {
						reportId:$.ims.attenceOvertime.saveCheckTimeReportId,
						overTimeId:$("#overTimeId").val(),
						overtimeType:$("#type").val(),
						startTime:$("#startTime").val(),
						endTime:$("#endTime").val(),
						checkHours:$("#checkHours").val(),
						brushRecord:$("#brushData").val(),
						oaState:$("#oaState").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$("#timecheckmodal").modal('hide');
							$.ims.attenceOvertime.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
					}
				});	
		},
		initSearchDataTable : function() {
			if (this.attenceOvertimeDataTable == null) {
				this.attenceOvertimeDataTable = $('#dt_attenceovertime_view').dataTable({
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
					"bFilter":false,     
					"sAjaxSource" : $.ims.getContextPath() + "/attence/overtime/findDailyReportForCheck",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						var user = $("#user").val();
						var state = $("#state").val();
						var oaState = $("#oaState_").val();
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
						if (!!user) {
							aoData.push({
								"name" : "user",
								"value" : user
							});
						}
						if (!!state) {
							aoData.push({
								"name" : "state",
								"value" : state
							});
						}
						if (!!oaState) {
							aoData.push({
								"name" : "oaState",
								"value" : oaState
							});
						}
						$.ajax({
							"dataType" : 'json',
							"type" : "POST",
							"url" : sSource,
							"data" : aoData,
							"success" : function(data){
								fnCallback(data);
								jQuery.ims.attenceOvertime.table_rowspan("#dt_attenceovertime_view",1);
								jQuery.ims.attenceOvertime.table_rowspan("#dt_attenceovertime_view",2);
								jQuery.ims.attenceOvertime.table_rowspan("#dt_attenceovertime_view",3);
								
							}
						});
					},
					"aoColumns" : [{
						"mDataProp" : "id"
					}, {
						"mDataProp" : "user.dept.name"
					}, {
						"mDataProp" : "user.chinesename"
					}, {
						"mDataProp" : "user.empnumber"
					}, {
						"mDataProp" : "reportDate"
					}, {
						"mDataProp" : "project.projectName"
					}, {
						"mDataProp" : "summary"
					}, {
						"mDataProp" : "spendHours"
					}, {
						"mDataProp" : "attenceOverTime.brushRecord"
					}, {
						"mDataProp" : ""
					}, {
						"mDataProp" : "attenceOverTime.checkHours"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : "attenceOverTime.oaState"
					}, {
						"mDataProp" : "id"
					} ],
					"aoColumnDefs" : [{
						'aTargets' : [ 0 ],
						'bVisible' : false
					},
					{
						'aTargets' : [ 9 ],
						'fnRender' : function(oObj, sVal) {
							if(oObj.aData.attenceOverTime.startTime!=null)
							return oObj.aData.attenceOverTime.startTime+"~"+
									oObj.aData.attenceOverTime.endTime;
							else return  "";
						}
					},
					{
						'aTargets' : [11],
						'fnRender' : function(oObj, sVal) {
							 return oObj.aData.attenceOverTime.startTime==null?"<span class='label label-important'>未核对</span>":"<span class='label label-info'>已核对</span>";
						}
					},
					{
						'aTargets' : [12],
						'fnRender' : function(oObj, sVal) {
							if(oObj.aData.attenceOverTime.oaState=='1')
							 return"<span class='label label-info'>已提交</span>";
							else return "<span class='label  label-important'>未提交</span>";
						}
					},
					{
						'aTargets' : [ 13],
						'fnRender' : function(oObj, sVal) {
								return  '<a class="btn btn-info" onclick="$.ims.attenceOvertime.showCheckTimeModal(\'' + oObj.aData.id
							+ '\',\''+oObj.aData.reportDate+'\',\''+oObj.aData.user.id+'\')"><i class="icon-edit icon-white"></i>记为加班</a>&nbsp;';
							
						}
					},{
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.attenceOvertimeDataTable.fnSettings();
				this.attenceOvertimeDataTable.fnDraw(oSettings);
			}

		},
		table_rowspan :function(table_id, table_colnum) {
            table_firsttd = "";
            table_currenttd = "";
            table_SpanNum = 0;
            colnum_Obj = $(table_id + " tr td:nth-child(" + table_colnum + ")");
            colnum_Obj1 = $(table_id + " tr td:nth-child(2)");
            colnum_Obj3 = $(table_id + " tr td:nth-child(3)");
            colnum_Obj.each(function (i) {
                if (i == 0) {
                    table_firsttd = $(this);
                    table_SpanNum = 1;
 
                } else {
                    table_currenttd = $(this);
                    if (table_firsttd.text() == table_currenttd.text()&&colnum_Obj1.eq(i-1).text()==colnum_Obj1.eq(i).text()&&colnum_Obj3.eq(i-1).text()==colnum_Obj3.eq(i).text()) {
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