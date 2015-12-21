jQuery.ims.reportot = {
		
		otDataTable : null,
		
		refreshotDataTable : function(){
			if(this.otDataTable != null){
				var oSettings = this.otDataTable.fnSettings();
				this.otDataTable.fnDraw(oSettings);
			}
		},
		
		initotDataTable : function(){
			if (this.otDataTable == null) {
				this.otDataTable = $('#otDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/otreimburse/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var reimburseDate = $("#reimburse_Date").val();
										if (!!reimburseDate) {
											aoData.push({
												"name" : "reimburseDate",
												"value" : reimburseDate
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
										"mDataProp" : "reimburseMoney"
									}, {
										"mDataProp" : "number"
									}, {
										"mDataProp" : "otrDetails"
									}, {
										"mDataProp" : "remark"
									}, {
										"mDataProp" : "state"
									}, {
										"mDataProp" : "actualMoney"
									}, {
										"mDataProp" : "payMoneyDate"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal != null && sVal != "") {
												return sVal.chinesename;
											}else{
												return sVal;
											}
										}
									}, {
										'aTargets' : [ 4 ],
										'fnRender' : function(oObj, sVal) {
											var details = "";
											if (sVal != null && sVal != "") {
												for(var i=0; i<sVal.length; i++){
													details += sVal[i].user.chinesename;
													details += ",";
												}
												details = details.substring(0, details.length-1);
											}
											return details;
										}
									}, {
										'aTargets' : [ 6 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal == "0") {
												return '<span class="label label-important">未付款</span>';
											}
											else if(sVal == "1"){
												return '<span class="label label-success">已付款</span>';
											}
											return "";
										}
									}, {
										'aTargets' : [ 9 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操  作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a onclick="$.ims.reportot.edit('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.reportot.deleteOT('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				this.refreshotDataTable();
			}
		},
		
		save : function(){
			var date = $("#rbDate").val();
			var type = $("#dinnerType").val();
			var number = $("#dinnerNo").val();
			var userIds = $("#dinnerUsers").val().toString();
			var remark = $("#remark").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/otreimburse/save",
				dataType : "json",
				data : {
					reimburseDate : date,
					type : type,
					number : number,
					userIds : userIds,
					remark : remark
				},
				async : false,
				success : function(json) {
					var success = json.success;
    				var msg = json.msg;
    				if(success == 1){
    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
    				}else{
    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
    				}
				}
			});
		},
		
		edit : function(id){
			
		},
		
		deleteOT : function(id){
			var msg = "是否确认删除？";
			bootbox.confirm( msg, function (result) {
	            if(result){
	            	$.ajax({
	        			type : "post",
	        			url : $.ims.getContextPath() + "/otreimburse/delete",
	        			data : {
	        				id : id
	        			},
	        			dataType : "json",
	        			success : function(json) {
	        				var success = json.success;
	        				var msg = json.msg;
	        				if(success == 1){
	        					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.reportot.refreshotDataTable();
	        				}else{
	        					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		
		ondinnerNochange : function(){
			var dinnerNo = $("#dinnerNo").val();
			var money = dinnerNo * 12;
			$("#dinnerMoney").val(money);
		},
		
}