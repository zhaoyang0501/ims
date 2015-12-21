jQuery.ims.workflowabsentee = {
		
		index : 1,
		user : null,
		absenteeDataTable : null,
		
		addNewRow : function(){
			var tr= '<tr><td><button name="removebtn" id="absentee_' + this.index + '" style="margin-top:5px;">－</button></td>' +
				'<td><select name="user" id="user_' + this.index + '" style="width:100px;" class="required1 chosen" data-placeholder="请选择人员">' + this.user + '</select></td>' +
				'<td><input id="empnumber_' + this.index + '" type="text" style="width: 70px;" readonly="readonly"></input></td>' +
				'<td><div class=" input-append date"><input name="time" id="time_'+this.index+'" style="width: 120px;" type="text" readonly="readonly" class="required2"><span class="add-on"><i class="icon-th"></i></span></div></td>' +
				'<td><input id="record_' + this.index + '" type="text" style="width: 100px;" readonly="readonly"></input></td>' +
				'<td><input name="reason" id="reason_' + this.index + '"   type="text" style="width:90%"  class="required "></input></td>' +
				'<td><input id="frequency_' + this.index + '" type="text" style="width:85px;" readonly="readonly"></input></td>' +
				'<td><input name="remark" id="remark_' + this.index + '"   type="text" style="width:90%" ></input></td>';
				tr +="</tr>";
			
			$("#dt_absenteerequire_body").append(tr);
			
			$("button[name=removebtn]").click(function(){
				$(this).parent().parent().remove();
			});
			
		    $(".required").change(function(){
		    	var v = $(this).val();
		    	if ($.trim(v) != "") {
		        	$(this).removeClass('valid-error');
		        }else{
		        	$(this).addClass('valid-error');
		        }
		    });
		    
		    $(".chosen").chosen({
				allow_single_deselect : true,
			});
		    
		    var cuid = $("#hf_cuid").val();
		    $("#user_"+ this.index).change(function(){
		    	if ($.trim($(this).val()) != "0") {
		        	$(this).next().removeClass('valid-error');
		        }else{
		        	$(this).next().addClass('valid-error');
		        	flag = false;
		        }
		    	var i = this.id.split('_')[1];
		    	$.ims.workflowabsentee.setNumberandDept($(this).val(), i);
		    });
		    
		    $("#time_"+ this.index).change(function(){
		    	if ($.trim($(this).val()) != "") {
		        	$(this).parent().removeClass('valid-error');
		        }else{
		        	$(this).parent().addClass('valid-error');
		        	flag = false;
		        }
		    });
		    
	        $('.date').datetimepicker({
				language : 'zh-CN',
				autoclose : 1,
				startView : 2,
				minView : 0,
				format : "yyyy-mm-dd hh:ii"
			});
	        
	        $.ims.common.setchosenvalue2("user_"+ this.index, cuid);
	        this.setNumberandDept(cuid, this.index);
			this.index ++;
		},
		
		validation : function(flag){
			// 非空验证
			$(".required").each(function(){
				if ($.trim($(this).val()) != "") {
		        	$(this).removeClass('valid-error');
		        }else{
		        	$(this).addClass('valid-error');
		        	flag = false;
		        }
			});
			$(".required1").each(function(){
				if ($.trim($(this).val()) != "0") {
		        	$(this).next().removeClass('valid-error');
		        }else{
		        	$(this).next().addClass('valid-error');
		        	flag = false;
		        }
			});
			$(".required2").each(function(){
				if ($.trim($(this).val()) != "") {
		        	$(this).parent().removeClass('valid-error');
		        }else{
		        	$(this).parent().addClass('valid-error');
		        	flag = false;
		        }
			});
			return flag;
		},
		
		fillUser : function(){
			var tr;
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/common/user/list",
				dataType:"json",
				async:false,
				success:function(json){
					if (json) {
						tr += "<option value=\"" + "0" + "\">" + "</option>";
						for(var i = 0; i < json.length; i++){
							tr +='<option value="'+json[i].id+'">'+json[i].chinesename+'</option>';
						}
					}
				}
			});
			return tr;
		},
		
		/** 设置工号和部门 **/
		setNumberandDept : function(cuid, index){
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/user/query/"+ cuid,
				dataType:"json",
				async:false,
				success:function(json){
					if (json) {
						$("#empnumber_"+ index).val(json.empnumber);
					}
				}
			});
		},
		
		//提交
		submit : function(what){
			var flag = true;
			if(!$.ims.workflowabsentee.validation(flag)){
				return;		//验证失败
			}
			var array = new Array();
			$("#dt_absenteerequire_body tr").each(function(){
				 array.push({user: $(this).find("select[name='user']").val(),
					 		absenteeDate: $(this).find("input[name='time']").val().split(' ')[0], 
					 		absenteeTime: $(this).find("input[name='time']").val().split(' ')[1], 
					 		reason: $(this).find("input[name='reason']").val(), 
					 		remark: $(this).find("input[name='remark']").val()});
			});
			
			var data = JSON.stringify(array);
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/workflow/absentee/submit",
				dataType : "json",
				contentType:"application/json",
				data : data,
				complete:function() {
					  $(what).removeAttr("disabled");
			 	},
			 	beforeSend:function () {
			 		 $(what).attr("disabled","disabled");
			 	},
				success : function(json) {
					if (json.success = 1) {
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "2000"});
						$("#dt_absenteerequire_body").html("");
						$.ims.workflowabsentee.index = 1;
						$.ims.workflowabsentee.addNewRow();
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
		},
		
		//审批
		approve : function(actionId){
			$("#absenteeform input[name='actionId']").val(actionId);
			var operate = "";
			$('input[name="create-switch"]').each(function(){
				var checked = $(this).prop('checked') == true?"1":"0";
				var id = $(this).attr("id").split("_")[1];
				operate += id + "-" + checked + ",";
			});
			$("#absenteeform input[name='operate']").val(operate);
			$("#absenteeform").submit();
		},
		
		/**
		 * 补打卡流程index页面datatable初始化
		 */
		initAbsenteeDataTable : function(){
			if (this.absenteeDataTable == null) {
				var step = "";
				this.absenteeDataTable = $('#absenteeDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/workflow/absentee/list',
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
										"mDataProp" : "user"
									}, {
										"mDataProp" : "createDate"
									}, {
										"mDataProp" : "wfentry.state"
									}, {
										"mDataProp" : "step"
									}, {
										"mDataProp" : "id"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											return sVal == null? "" : sVal.chinesename;
										}
									}, {
										'aTargets' : [ 2 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal == "1") {
												return '<span class="label label-info">审批中</span>';
											}else if (sVal == "4") {
												return '<span class="label label-success">已结束</span>';
											}else {
												return '<span class="label label-important">未知</span>';
											}
										}
									}, {
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											if(oObj.aData.wfentry.id==null) return "";
											return "<a target=\"_blank\" href=\"workflow/workflowgraph/"+oObj.aData.wfentry.id+"\">"+sVal+"</a>";
										}
									}, {
										'aTargets' : [ 4 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
											operate += ' <ul class="dropdown-menu">';
											if(oObj.aData.wfentry.id != undefined)
											operate += '<li><a  target="_blank" href="workflow/toapprove/goApprove/'+oObj.aData.wfentry.id+'" ><i class="icon-wrench"></i> 查看详情</a></li>';
											operate += '<li><a onclick="$.ims.workflowabsentee.deleteAbsentee('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				var oSettings = this.absenteeDataTable.fnSettings();
				this.absenteeDataTable.fnDraw(oSettings);
			}
		},
		
		deleteAbsentee: function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "post",
	        			url : $.ims.getContextPath() + "/workflow/absentee/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				$.noty.closeAll();
	        				if(json.code=='1'){
	        					noty({"text":"删除成功","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.workflowabsentee.initAbsenteeDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		
		absenteeApproveDataTable : null,
		
		//审批中的table
		initworkflowabsenteeapprove : function(osworkflow){
			//控制操作列是否可见
			var operate = $("#hf_operate").val() == "1" ? true : false;
			
			if (this.absenteeApproveDataTable == null) {
				this.absenteeApproveDataTable = $('#dt_absenteerequire_approve')
						.dataTable(
								{
									"sDom" : "<'row-fluid'r>t<'row-fluid'>",
									"oLanguage" : {
										"sZeroRecords" : "抱歉， 暂时没有记录",
										"sInfoEmpty" : "没有数据"
									},
									"bServerSide" : true,
									"sServerMethod" : "GET",
									"bProcessing" : false,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/workflow/absentee/goapprove/details/list/'+osworkflow,
									"fnDrawCallback" : function(oSettings) {
										$("#dt_absenteerequire_approve").removeClass("dataTable");
										$('input[name="create-switch"]').bootstrapSwitch();
									},
									"aoColumns" : [ {
										"mDataProp" : "username"
									}, {
										"mDataProp" : "empnumber"
									}, {
										"mDataProp" : "absenteetime"
									}, {
										"mDataProp" : "record"
									}, {
										"mDataProp" : "reason"
									}, {
										"mDataProp" : "frequency"
									}, {
										"mDataProp" : "remark"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 7 ],
										'bVisible' : operate,	//是否可见
										'fnRender' : function(oObj, sVal) {
											var switchDiv = '<div class="switch" ><input name="create-switch" id="create-switch_'+oObj.aData.id+'" type="checkbox" checked /></div>'; 
											if(oObj.aData.state == 0){
												switchDiv = '<div class="switch" ><input name="create-switch" id="create-switch_'+oObj.aData.id+'" type="checkbox" /></div>';
											}
											return switchDiv;
										}
									}]
								});
			} else {
				var oSettings = this.absenteeApproveDataTable.fnSettings();
				this.absenteeApproveDataTable.fnDraw(oSettings);
			}
		},
		
		
}