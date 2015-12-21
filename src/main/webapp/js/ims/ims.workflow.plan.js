jQuery.ims.workflowplan = {
		workflowplanDatatable : null,
		inintWorkflowplanDatatable : function(){
			if (this.workflowplanDatatable == null) {
				this.workflowplanDatatable = $('#dt_workflowplan').dataTable(
						{
							"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
							"oLanguage" : {
								"iDisplayLength" : 10,
								"aLengthMenu" : [ 10, 25, 50, 100 ],
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
							"sAjaxSource" : $.ims.getContextPath() + '/workflow/plan/list',
							"fnServerData" : function(sSource, aoData, fnCallback) {
								var startDate = $("#start_date").val();
								var endDate = $("#end_date").val();
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
								"mDataProp" : "sn"
							}, {
								"mDataProp" : "source"
							}, {
								"mDataProp" : "trainingType"
							}, {
								"mDataProp" : "contents"
							}, {
								"mDataProp" : "planStartTime"
							},{
								"mDataProp" : "planEndTime"
							},{
								"mDataProp" : "planHours"
							},{
								"mDataProp" : "planAddr"
							},{
								"mDataProp" : "planTeacher.chinesename"
							},{
								"mDataProp" : "planStudents"
							},{
								"mDataProp" : "planCost"
							},{
								"mDataProp" : "planEquipment"
							},{
								"mDataProp" : "planCheckType"
							}, {
								"mDataProp" : "realStartTime"
							},{
								"mDataProp" : "realEndTime"
							},{
								"mDataProp" : "realHours"
							},{
								"mDataProp" : "realAddr"
							},{
								"mDataProp" : "realTeacher.chinesename"
							},{
								"mDataProp" : "realStudents"
							},{
								"mDataProp" : "realCost"
							},{
								"mDataProp" : "realEquipment"
							},{
								"mDataProp" : "realCheckType"
							},{
								"mDataProp" : "id"
							}],
							"aoColumnDefs" : [
									{
										'aTargets' : [22 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a  target="_blank" href="workflow/plan/view/'+oObj.aData.id+'" ><i class="icon-wrench"></i> 查看</a></li>';
											operate += '<li><a onclick="$.ims.workflowplan.send('+oObj.aData.id+')"><i class="icon-wrench"></i> 发起流程</a></li>';
											operate += '<li><a onclick="$.ims.workflowovertime.deleteOvertime('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									},
									{
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}
									 ]
						});
			}else{
				var oSettings = this.workflowplanDatatable.fnSettings();
				this.workflowplanDatatable.fnDraw(oSettings);
			}
		},
		send: function(id){
			bootbox.confirm( "是否发起培训流程？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/workflow/plan/send/"+id,
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
		},
}