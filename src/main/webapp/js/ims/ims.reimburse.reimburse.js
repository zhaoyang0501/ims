jQuery.ims.reimburse = {
		reimburseDataTable : null,
		changePayNumber:function(){
			$("#reimburseMoney").val($("#number").val()*12);
		},
		initEditTable:function(){
			  $('.username').editable({
			        type: 'text',
			        pk: 1,
			        name: 'username',
			        title: '请填写用餐人员姓名',
			        validate: function(value) {
			            if($.trim(value) == '') return '请填写用餐人员姓名';
			         }
			 	});
			    $('.company').editable({
			        type: 'text',
			        pk: 1,
			        name: 'company',
			        title: '请填写用餐人员单位',
			        validate: function(value) {
			            if($.trim(value) == '') return '请填写用餐人员单位';
			         }
			 	});
			    $('.project').editable({
			         select2: {
			             width: 200,
			             allowClear: true
			         } 
			  }); 
			    
			    $(".input_username").rules("add",  {checkCustomerDetails:[]});
			    $('.project').on('save', function(e, params) {
			    	$(this).prev("input").val(params.newValue);
			    });
			    $('.company').on('save', function(e, params) {
			    	$(this).prev("input").val(params.newValue);
			    });
			    $('.username').on('save', function(e, params) {
			    	$(this).prev("input").val(params.newValue);
			    	 $.ims.reimburse.reimburseform.element(".input_username");
			    });
			   
		},
		changePeopleNumber:function(){
			var length=$("#number").val();
			$("#table_onther_people tbody").empty();
			$("#control_onther_people").show();
			for(i=0;i<length;i++){
				var str="<tr>"+
						"<td>"+(i+1)+"</td> "+
						"<td> "+
						"<input  class='input_username' name='reimburseCustomerDetails["+i+"].userName'  type='hidden' value=''>"+
						"	<a href='#' class='username' id='username' data-type='text' data-pk='1' data-placement='right'  data-title='请填写用餐人员姓名'></a> "+
						"</td> "+
						"<td> "+
						"<input   class='input_project' name='reimburseCustomerDetails["+i+"].project.id'  type='hidden' value=''>"+
						"	<a href='#' class='project' id='project' data-source='reimburse/reimburse/allProject'  data-type='select' data-pk='1'  data-title='选择参与的项目' data-original-title='' ></a>"+
						"</td> "+
						"<td> "+
						"<input  class='input_company' name='reimburseCustomerDetails["+i+"].company'  type='hidden' value=''>"+
						"	<a href='#' class='company'  id='company' data-type='text' data-pk='1' data-placement='right'  data-title='请填写用餐人员单位'></a>"+
						"</td> "+
						"</tr> ";
				$("#table_onther_people tbody").append(str);
				$("#table_onther_people").show("slow");
			
				
			}
			jQuery.ims.reimburse.initEditTable();
		},
		initReimburseDataTable : function(){
			var workflowId="";
			var step="";
			if (this.reimburseDataTable == null) {
				this.reimburseDataTable = $('#reimburseDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/reimburse/reimburse/list',
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
												for(var i=0; i<oObj.aData.reimburseCustomerDetails.length; i++){
													details += oObj.aData.reimburseCustomerDetails[i].userName+"("+oObj.aData.reimburseCustomerDetails[i].company+")";
													details += ",";
												}
												
												details = details.substring(0, details.length-1);
											}
											return details;
										}
									}, {
										'aTargets' : [9 ],
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
										'aTargets' : [ 10 ],
										'fnRender' : function(oObj, sVal) {
											step=sVal;
											if(workflowId==null) return "";
											return "<a target=\"_blank\" href=\"approve/myapprove/workflowgraph/"+workflowId+"\">"+sVal+"</a>";
										}
									}, {
										'aTargets' : [11 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操  作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											if(workflowId!=undefined)
											operate += '<li><a  target="_blank" href="approve/myapprove/goApprove/'+workflowId+'" ><i class="icon-wrench"></i> 查看报销单</a></li>';
											if(workflowId==undefined||step=='加班报告填写'||step=='提交报销单')
											operate += '<li><a  href="reimburse/reimburse/update/'+oObj.aData.id+'" ><i class="icon-wrench"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.reimburse.deleteReimburse('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '<li><a onclick="$.ims.reimburse.sendReimburse('+oObj.aData.id+')"><i class="icon-wrench"></i> 提交审批</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				var ajaxSource =$.ims.getContextPath() + '/reimburse/reimburse/list';
				var oSettings = this.reimburseDataTable.fnSettings();
				oSettings.sAjaxSource = ajaxSource;
				this.reimburseDataTable.fnDraw(oSettings);
			}
		},
		saveAndSend:function(isSend){
			if(isSend)
				$("#isSend").val("true");
			$("form:first").submit();
		},
		sendReimburse: function(id){
			bootbox.confirm( "是否确认提交？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/reimburse/reimburse/sendReimburse/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.reimburse.initReimburseDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning","timeout":"2000"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		deleteReimburse: function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/reimburse/reimburse/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.reimburse.initReimburseDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		}
}