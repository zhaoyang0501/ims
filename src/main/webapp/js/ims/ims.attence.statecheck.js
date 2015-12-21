jQuery.ims.attenceStateCheck = {
		overtimeForm:null,
		dayoffform:null,
		travelform:null,
		okform:null,
		unknownform:null,
		laterform:null,
		brushRecordId:"",
		stateCheckDataTable:null,
		dayoffs:null,
		/**打开正班模态框*/
		showOkModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			$("#attenceok_modal").modal('show');
		},
		/**打开未知模态框*/
		showUnKnownModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			$("#attenceunknown_modal").modal('show');
		},
		/**打开迟到模态框*/
		showLaterModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			$("#attencelater_modal").modal('show');
		},
		/**打开漏打卡模态框*/
		showAbsenteeModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			this.getAbsentee();
			this.getAbsenteeTip();
			$("#absentee_modal").modal('show');
		},
		/**打开请假模态框*/
		showDayOffModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			this.getDayOff();
			$("#attencedayoff_modal").modal('show');
		},
		/**打开出差态框*/
		showTravelModal:function(id){
			this.brushRecordId=id;
			this.cleanFormDate();
			this.getTravel();
			$("#attencetravel_modal").modal('show');
		},
		/**保存漏打卡记录*/
		saveAbsentee:function (){
			if(!$.ims.attenceStateCheck.overtimeForm.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveAbsentee",
					dataType: "json",
					data: {
						id:$("#absentee_id").val(),
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						absenteeTime:$("#absentee_absenteeTime").val(),
						absenteeType:$("#absentee_absenteeType").val(),
						remark:$("#absentee_remark").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#absentee_modal").modal('hide');
					}
				});	
		},
		/**保存请假*/
		saveDayOff:function (){
			if(!$.ims.attenceStateCheck.dayoffform.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveDayOff",
					dataType: "json",
					data: {
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						id:$("#dayoff_id").val(),
						startTime:$("#dayoff_startTime").val(),
						endTime:$("#dayoff_endTime").val(),
						spendHours:$("#dayoff_spendHours").val(),
						dayoffType:$("#dayoff_dayoffType").val(),
						remark:$("#dayoff_remark").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#attencedayoff_modal").modal('hide');
					}
				});	
		},
		/**保存出差*/
		saveTravel:function (){
			if(!$.ims.attenceStateCheck.travelform.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveTravel",
					dataType: "json",
					data: {
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						id:$("#travel_id").val(),
						startTime:$("#travel_startTime").val(),
						endTime:$("#travel_endTime").val(),
						address:$("#travel_address").val(),
						reason:$("#travel_reason").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#attencetravel_modal").modal('hide');
					}
				});	
		},
		/**保存正班备注*/
		saveOk:function (){
			if(!$.ims.attenceStateCheck.okform.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveOk",
					dataType: "json",
					data: {
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						remark:$("#ok_remark").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#attenceok_modal").modal('hide');
					}
				});	
		},
		/**保存正班备注*/
		saveUnknown:function (){
			if(!$.ims.attenceStateCheck.unknownform.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveUnknown",
					dataType: "json",
					data: {
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						remark:$("#unknown_remark").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#attenceunknown_modal").modal('hide');
					}
				});	
		},
		/**保存正班备注*/
		saveLater:function (){
			if(!$.ims.attenceStateCheck.unknownform.form()) return ;
			 $.ajax({
					type: "post",
					url:$.ims.getContextPath() + "/attence/statecheck/saveLater",
					dataType: "json",
					data: {
						brushRecordId:$.ims.attenceStateCheck.brushRecordId,
						remark:$("#later_remark").val()
					},
					success: function(json){
						if(json.code==1){
							noty({"text":"保存成功！","layout":"top","type":"success","timeout":"2000"});
							$.ims.attenceStateCheck.initSearchDataTable();
						}
						else{
							noty({"text":""+json.msg+"！","layout":"top","type":"error","timeout":"2000"});
						}
						$("#attencelater_modal").modal('hide');
					}
				});	
		},
		/**请假下拉框值发生改变*/
		dayOffChange:function(){
			if($("#dayoff_id").val()==''){
				$("#dayoff_endTime").val("");
				$("#dayoff_startTime").val("");
				$("#dayoff_spendHours").val("");
				$("#dayoff_dayoffType").val("");
				$("#dayoff_remark").val("");
			}else{
				for( i=0;i<dayoffs.length;i++){
					if(dayoffs[i].id==$("#dayoff_id").val()){
						$("#dayoff_endTime").val(dayoffs[i].endTime);
						$("#dayoff_startTime").val(dayoffs[i].startTime);
						$("#dayoff_spendHours").val(dayoffs[i].spendHours);
						$("#dayoff_dayoffType").val(dayoffs[i].dayoffType);
						$("#dayoff_remark").val(dayoffs[i].remark);
						jQuery.ims.common.setchosenvalue("dayoff_dayoffType",dayoffs[i].dayoffType);
					}
				}
			}
			
		},
		/**查有没有保存过的请假*/
		getDayOff:function(){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/statecheck/getDayOff/"+this.brushRecordId,
					success: function(json){
						$("#dayoff_id").empty().append("<option value=''>&nbsp;</option>");
						if(json.code==1){
							dayoffs=json.datas;
							for( i=0;i<dayoffs.length;i++){
								$("#dayoff_id").append("<option value='"+dayoffs[i].id+"'>"+dayoffs[i].startTime.substr(5)+"至"+dayoffs[i].endTime.substr(5)+"</option>");
							}
						}else{
							dayoffs=null;
							$("#dayoff_id").val("");
							$("#dayoff_endTime").val("");
							$("#dayoff_startTime").val("");
							$("#dayoff_spendHours").val("");
							$("#dayoff_dayoffType").val("");
							$("#dayoff_remark").val("");
						}
					}
				});	
		},
		/**查有没有保存过的出差*/
		getTravel:function(){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/statecheck/getTravel/"+this.brushRecordId,
					success: function(json){
						if(json.code==1){
							$("#travel_id").val(json.datas.id);
							$("#travel_endTime").val(json.datas.endTime);
							$("#travel_startTime").val(json.datas.startTime);
							$("#travel_address").val(json.datas.address);
							$("#travel_reason").val(json.datas.reason);
						}else{
							$("#travel_id").val("");
							$("#travel_endTime").val("");
							$("#travel_startTime").val("");
							$("#travel_address").val("");
							$("#travel_reason").val("");
						}
					}
				});	
		},
		/**查有没有保存过的漏打卡*/
		getAbsentee:function(){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/statecheck/getAbsentee/"+this.brushRecordId,
					success: function(json){
						if(json.code==1){
							$("#absentee_id").val(json.datas.id);
							$("#absentee_absenteeTime").val(json.datas.absenteeTime);
							$("#absentee_absenteeType").val(json.datas.absenteeType);
							$("#absentee_remark").val(json.datas.remark);
							jQuery.ims.common.setchosenvalue("absentee_absenteeType",json.datas.absenteeType);
						}else{
							$("#absentee_id").val("");
							$("#absentee_absenteeTime").val("");
							$("#absentee_absenteeType").val("");
							$("#absentee_remark").val("");
						}
					}
				});	
		},
		/**查有没有保存过的漏打卡*/
		getAbsenteeTip:function(){
			 $.ajax({
					url:$.ims.getContextPath() + "/attence/statecheck/getAbsenteeTimesThisMonth/"+this.brushRecordId,
					success: function(json){
						if(json.code==1){
							$("#absentee_tip").html("提示：本月已申请漏打卡"+json.datas+"次");
						}
					}
				});	
		},
		/**清除表单*/
		cleanFormDate:function(){
			$("#absentee_absenteeTime").val("");
			$("#absentee_remark").val("");
			$("#dayoff_startTime").val("");
			$("#dayoff_endTime").val("");
			//$("#dayoff_dayoffType").val("");
			$("#dayoff_spendHours").val("");
			$("#dayoff_remark").val("");
			$("#travel_startTime").val("");
			$("#travel_endTime").val("");
			$("#travel_address").val("");
			$("#travel_reason").val("");
			$("#ok_remark").val(""),
			$("#unknown_remark").val(""),
			jQuery.ims.common.setchosenvalue("absentee_absenteeType","");
			jQuery.ims.common.setchosenvalue("dayoff_dayoffType","");
			
		},
		initSearchDataTable : function() {
			var curState="";
			if (this.stateCheckDataTable == null) {
				this.stateCheckDataTable = $('#dt_statecheck_view').dataTable({
					"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
					"sPaginationType" : "bootstrap",
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
					"bAutoWidth" : false,
					"iDisplayLength" : 200,
					"aLengthMenu" : [  25, 50,200],
					"bServerSide" : true,
					"sServerMethod" : "POST",
					"bProcessing" : true,
					"bSort" : false,
					"bFilter":false,     
					"sAjaxSource" : $.ims.getContextPath() + "/attence/statecheck/findBrushRecordForCheck",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						var user = $("#user").val();
						var state = $("#attenceType").val();
						if (!!startDate) {
							aoData.push({
								"name" : "sstartDate",
								"value" : startDate
							});
						}
						if (!!endDate) {
							aoData.push({
								"name" : "sendDate",
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
						
					$.ajax({
						"dataType" : 'json',
						"type" : "POST",
						"url" : sSource,
						"data" : aoData,
						"success" : fnCallback
					});
					},
					"aoColumns" : [{
						"mDataProp" : "personNo"
					}, {
						"mDataProp" : "personName"
					}, {
						"mDataProp" : "cardNo"
					}, {
						"mDataProp" : "brushDate"
					}, {
						"mDataProp" : "brushData"
					}, {
						"mDataProp" : "state"
					},{
						"mDataProp" : "remark"
					},  {
						"mDataProp" : "id"
					} ],
					"aoColumnDefs" : [
					{
						'aTargets' : [5],
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
						'aTargets' : [7 ],
						'fnRender' : function(oObj, sVal) {
							var operate = '<div class="btn-group">';
							operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
							operate += ' <ul class="dropdown-menu">';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showAbsenteeModal('+oObj.aData.id+')"><i class="icon-edit"></i>漏打卡</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showDayOffModal('+oObj.aData.id+')"><i class="icon-wrench"></i>请假 </a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showTravelModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 出差</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showOkModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 正班</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showLaterModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 迟到</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showUnKnownModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 未知</a></li>';
							operate += '</ul>';
							operate += '</div>';
							return operate;
						}
					},
					{
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.stateCheckDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.stateCheckDataTable.fnDraw(oSettings);
			}

		},
};