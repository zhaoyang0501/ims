jQuery.ims.workflowdayoff = {
		dayoffform:null,
		dayoffDataTable : null,
		saveAndSend:function(what){
			if(jQuery.ims.workflowdayoff.dayoffform.form())
			$(what).attr("disabled","disabled");
			$("form:first").submit();
		},
		submitForm:function(actionid){
			if($("#approvals").val()==""){
				noty({"text":"请填写审批意见！","layout":"top","type":"error","timeout":"2000"});
				return;
			}
			$("#workflowform input[name='actionId']").val(actionid);
			$("#workflowform").submit();
		},
		initDayoffDataTable : function(){
			var workflowId="";
			var step="";
			if (this.dayoffDataTable == null) {
				this.dayoffDataTable = $('#dayoffDataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
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
									"sAjaxSource" : $.ims.getContextPath() + '/workflow/dayoff/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var begin = $("#begin").val();
										var end = $("#end").val();
										var state = $("#state").val();
										if (!!begin) {
											aoData.push({
												"name" : "begin",
												"value" : begin
											});
										}
										if (!!end) {
											aoData.push({
												"name" : "end",
												"value" : end
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
											"type" : "GET",
											"url" : sSource,
											"data" : aoData,
											"success" : fnCallback
										});
									},
									"aoColumns" : [ {
										"mDataProp" : "user.empnumber"
									}, {
										"mDataProp" : "user.chinesename"
									}, {
										"mDataProp" : "dayoffDate"
									}, {
										"mDataProp" : "startTime"
									}, {
										"mDataProp" : "hours"
									},{
										"mDataProp" : "wfentry.state"
									},{
										"mDataProp" : "step"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [3],
										'fnRender' : function(oObj, sVal) {
											return oObj.aData.startTime+" 到  "+oObj.aData.endTime;
										}
									}, {
										'aTargets' : [4],
										'fnRender' : function(oObj, sVal) {
											return oObj.aData.days+"天  "+oObj.aData.hours+"小时";
										}
									}, {
										'aTargets' : [5 ],
										'fnRender' : function(oObj, sVal) {
											
											if (sVal == null||sVal=="" ) {
												return '<span class="label label-primary">未提交</span>';
											}else if(sVal==4){
												return '<span class="label label-success">已结束</span>';
											}else if(sVal==1){
												return '<span class="label label-info">审批中</span>';
											}else{
												return '<span class="label label-info">未知状态</span>';
											}
											
										}
									}, {
										'aTargets' : [6 ],
										'fnRender' : function(oObj, sVal) {
											if(oObj.aData.wfentry.id==null) return "";
											return "<a target=\"_blank\" href=\"workflow/workflowgraph/"+oObj.aData.wfentry.id+"\">"+sVal+"</a>";
										}
									}, {
										'aTargets' : [7 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
											operate += ' <ul class="dropdown-menu">';
											if(oObj.aData.wfentry.id!=undefined)
											operate += '<li><a  target="_blank" href="workflow/toapprove/goApprove/'+oObj.aData.wfentry.id+'" ><i class="icon-wrench"></i> 查看请假单</a></li>';
											if(oObj.aData.wfentry.id==undefined||step=='提交请假单')
											operate += '<li><a  href="dayoff/dayoff/update/'+oObj.aData.id+'" ><i class="icon-wrench"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.workflowdayoff.deleteDayOff('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				var ajaxSource =$.ims.getContextPath() + '/workflow/dayoff/list';
				var oSettings = this.dayoffDataTable.fnSettings();
				oSettings.sAjaxSource = ajaxSource;
				this.dayoffDataTable.fnDraw(oSettings);
			}
		},
		deleteDayOff: function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/workflow/dayoff/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.code=='1'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.workflowdayoff.initDayoffDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		}
}