jQuery.ims.attencedatareflesh = {
		attencedatarefleshDataTable:null,
		initSearchDataTable : function() {
			if (this.attencedatarefleshDataTable == null) {
				this.attencedatarefleshDataTable = $('#dt_attencelog_view').dataTable({
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
					"sAjaxSource" : $.ims.getContextPath() + "/sysconfig/attencedatareflesh/findLogs",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
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
							"success" : function(data){
								fnCallback(data);
							}
						});
					},
					"aoColumns" : [ {
						"mDataProp" : "attenceDate"
					}, {
						"mDataProp" : "beginTime"
					}, {
						"mDataProp" : "endTime"
					}, {
						"mDataProp" : "totalTime"
					}, {
						"mDataProp" : "recordNum"
					}, {
						"mDataProp" : "log"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : ""
					} ],
					"aoColumnDefs" : [
					{
						'aTargets' : [6 ],
						'fnRender' : function(oObj, sVal) {
							if (sVal =='10') 
								return "<span class=\"label label-success\">成功</span>";
							else if(sVal=='40')
								return  "<span class=\"label  label-important\">失败</span>";
						}
					},
					{
						'aTargets' : [7 ],
						'fnRender' : function(oObj, sVal) {
							if (sVal !='10') {
								return "<button class=\"btn2 btn-info\" onclick=\"jQuery.ims.attencedatareflesh.doreflesh('"+oObj.aData.attenceDate+"')\">执行迁移</button>";
							}
						}
					},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.attencedatarefleshDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.attencedatarefleshDataTable.fnDraw(oSettings);
			}

		},
		/***
		 * 执行刷新数据
		 */
		doreflesh: function(date){
			bootbox.confirm( "是否重新执行？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/sysconfig/attencedatareflesh/doReflesh/"+date,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.code=='1'){
	        					noty({"text":"操作成功","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.attencedatareflesh.initSearchDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning","timeout":"2000"});
	        				}
	        			}
	        		});
	            }
	        });
		}
};