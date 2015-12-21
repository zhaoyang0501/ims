jQuery.ims.reimbursesummary = {
		
		reimburseSummaryDataTable : null,
		
		initReimburseDataTable : function(){
			var workflowId="";
			var step="";
			if (this.reimburseSummaryDataTable == null) {
				this.reimburseSummaryDataTable = $('#reimburseDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/reimburse/reimburse/summary/list',
									"fnDrawCallback" : function(oSettings) {
										$('[rel="popover"],[data-rel="popover"]').popover();
									},
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var begin = $("#begin").val();
										var end = $("#end").val();
										var user = $("#user").val();
										var type = $("#type").val();
										var step = $("#step").val();
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
										if (!!step) {
											aoData.push({
												"name" : "step",
												"value" : step
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
										"mDataProp" : "reimburseDate"
									}, {
										"mDataProp" : "reimburser"
									}, {
										"mDataProp" : "type"
									}, {
										"mDataProp" : "reimburseMoney"
									}, {
										"mDataProp" : "number"
									}, {
										"mDataProp" : "otrDetails"
									}, {
										"mDataProp" : "remark"
									}, {
										"mDataProp" : "actualMoney"
									}, {
										"mDataProp" : "payMoneyDate"
									}, {
										"mDataProp" : "createTime"
									},{
										"mDataProp" : "wfentry"
									},{
										"mDataProp" : "step"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									},
									
									{
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal != null && sVal != "") {
												return sVal.chinesename;
											}else{
												return sVal;
											}
										}
									},
									
									{
										'aTargets' : [2 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal==1)
												return '<span class="label label-warning">午餐</span>';
											else if(sVal==2)
												return '<span class="label label-important">晚餐</span>';
											else
												return '<span class="label label-info">未知状态</span>';
										}
									},
									
									{
										'aTargets' : [ 5 ],
										'fnRender' : function(oObj, sVal) {
											var details = "";
											if (sVal != null && sVal != "") {
												for(var i=0; i<sVal.length; i++){
													details += sVal[i].user.chinesename;
													details += ",";
												}
												for(var i=0; i<oObj.aData.reimburseCustomerDetails.length; i++){
													details += oObj.aData.reimburseCustomerDetails[i].userName+"("+oObj.aData.reimburseCustomerDetails[i].company+")";
													details += ",";
												}
												
												details = details.substring(0, details.length-1);
											}
											return details;
										}
									},{
										'aTargets' : [ 6 ],
										'fnRender' : function(oObj, sVal) {
											return  "<span  data-rel='popover' data-content='" + sVal + "' title='备注'>" +
											"" + ( sVal.length>10?sVal.substring(0, 10) + "...":sVal )+ "</span>";
											}
								   }, {
										'aTargets' : [10],
										'fnRender' : function(oObj, sVal) {
											workflowId=sVal.id;
											if (sVal == null||sVal=="" ) {
												return '<span class="label label-primary">未提交</span>';
											}else if(sVal.state==4){
												return '<span class="label label-success">已结束</span>';
											}else if(sVal.state==1){
												return '<span class="label label-info">审批中</span>';
											}else{
												return '<span class="label label-info">未知状态</span>';
											}
											
										}
									}, {
										'aTargets' : [ 11 ],
										'fnRender' : function(oObj, sVal) {
											step=sVal;
											if(workflowId==null) return "";
											return "<a target=\"_blank\" href=\"workflow/workflowgraph/"+workflowId+"\">"+sVal+"</a>";
										}
									}, {
										'aTargets' : [12 ],
										'fnRender' : function(oObj, sVal) {
											if(step=='财务审核')
											return '<a target="_blank" href="workflow/toapprove/goApprove/'+workflowId+'"><i class="icon-share-alt"></i> 审核</a>';
										}
									}]
								});
			} else {
				var oSettings = this.reimburseSummaryDataTable.fnSettings();
				this.reimburseSummaryDataTable.fnDraw(oSettings);
			}
		},
}