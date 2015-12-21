		
jQuery.ims.workflowaway = {
		user : null,
		index : 1,
		awayDataTable : null,
		
		addNewRow : function(){
			var tr= '<tr><td><button name="removebtn" id="absentee_' + this.index + '" style="margin-top:5px;">－</button></td>' +
				'<td><select name="user" id="user_' + this.index + '" style="width:150px;" class="required1 chosen" data-placeholder="请选择人员">' + this.user + '</select></td>' +
				'<td><input id="empnumber_' + this.index + '" type="text" style="line-height:28px;" readonly="readonly"></input></td>' +
				'<td><input id="dept_' + this.index + '" type="text" style="line-height:28px;" readonly="readonly"></input></td>' +
				'<td><input id="position_' + this.index + '" type="text" style="line-height:28px;" readonly="readonly"></input></td>';
				tr +="</tr>";
			
			$("#dt_away_body").append(tr);
			
			$("button[name=removebtn]").click(function(){
				$(this).parent().parent().remove();
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
		        	$("#empnumber_"+ index).val("");
					$("#dept_"+ index).val("");
		        	flag = false;
		        }
		    	var i = this.id.split('_')[1];
		    	$.ims.workflowaway.setNumberandDept($(this).val(), i);
		    });
		    
		    $.ims.common.setchosenvalue2("user_"+ this.index, cuid);
	        this.setNumberandDept(cuid, this.index);
			this.index ++;
		},
		
		fillDeptUser : function(){
			var tr;
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/workflow/away/deptofuser",
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
		
		/** 设置工号、部门和职称 **/
		setNumberandDept : function(cuid, index){
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/user/query/"+ cuid,
				dataType:"json",
				async:false,
				success:function(json){
					if (json) {
						$("#empnumber_"+ index).val(json.empnumber);
						$("#dept_"+ index).val(json.dept.name);
					}
				}
			});
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/sysconfig/resume/getposition/"+ cuid,
				dataType:"json",
				async:false,
				success:function(json){
					if (json) {
						$("#position_"+ index).val(json.position);
					}
				}
			});
		},

		/** 验证 **/
		validation : function(flag){
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
		
		/** 提交 **/
		submit : function(what){
			var n = this.index;
			var flag = true;
			if(!$.ims.workflowaway.validation(flag)){
				return;		//验证失败
			}
			var awayer = "";
			for(var i=1;i<n;i++){
				var userId = $("#user_"+i).val();
				if($.trim(userId) == ""){
					continue;
				}
				awayer += userId + ",";
			}
			awayer = awayer.substring(0, awayer.length - 1);
			var awayFrom = $("#awayFrom").val();
			var awayTo = $("#awayTo").val();
			var awayAddress = $("#awayAddress").val();
			var awayReason = $("#awayReason").val();
			var awayCar = $("#awayCar").val();
			var awayDriver = $("#awayDriver").val();
			var manager = $("#manager").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/workflow/away/submit",
				dataType : "json",
				data : {
					awayFrom : awayFrom,
					awayTo : awayTo,
					awayAddress : awayAddress,
					awayReason : awayReason,
					awayCar : awayCar,
					awayDriver : awayDriver,
					manager : manager,
					awayer : awayer
				},
				complete:function() {
					  $(what).removeAttr("disabled");
			 	},
			 	beforeSend:function () {
			 		 $(what).attr("disabled","disabled");
			 	},
				success : function(json) {
					if (json.code == 1) {
						noty({"text" : "操作成功，流程已提交给"+json.datas.chinesename+"","layout" : "top","type" : "success","timeout" : "2000"});
						$("#awayFrom").val("");
						$("#awayTo").val("");
						$("#awayAddress").val("");
						$("#awayReason").val("");
						$("#awayDriver").val("");
						$("#awayCar").val("");
						$.ims.workflowaway.index = 1;
					}else{
						noty({"text" : "提交失败！","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
		
		},
		
		initAwayDataTable : function(){
			if (this.awayDataTable == null) {
				this.awayDataTable = $('#awayDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/workflow/away/list',
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
										"mDataProp" : "fillDate"
									}, {
										"mDataProp" : "awayUser"
									}, {
										"mDataProp" : "awayFrom"
									}, {
										"mDataProp" : "awayAddress"
									}, {
										"mDataProp" : "awayReason"
									}, {
										"mDataProp" : "awayCar"
									}, {
										"mDataProp" : "awayDriver"
									}, {
										"mDataProp" : "awayTime"
									}, {
										"mDataProp" : "guider"
									}, {
										"mDataProp" : "remarks"
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
											var users = "";
											if (sVal != null && sVal != "") {
												for(var i=0;i<sVal.length;i++){
													users += '<span class="label label-info">'+sVal[i].chinesename+'</span><br />';
												}
											}
											return users;
										}
									}, {
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											return sVal + "<br />~<br />" + oObj.aData.awayTo;
										}
									}, {
										'aTargets' : [ 9 ],
										'fnRender' : function(oObj, sVal) {
											if (sVal != "") {
												return sVal.chinesename;
											}
											return "";
										}
									}, {
										'aTargets' : [ 11 ],
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
										'aTargets' : [ 12 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != "")
												return '<a target=\"_blank\" href=\"workflow/workflowgraph/'+oObj.aData.wfentry.id+'\">'+sVal+'</a>';
											return "";
										}
									}, {
										'aTargets' : [ 13 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a  target="_blank" href="workflow/toapprove/goApprove/'+oObj.aData.wfentry.id+'" ><i class="icon-wrench"></i> 查看详情</a></li>';
											operate += '<li><a onclick="$.ims.workflowaway.deleteaway('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				var oSettings = this.awayDataTable.fnSettings();
				this.awayDataTable.fnDraw(oSettings);
			}
		},
		
		approve : function(actionId){
			$("#awayform input[name='actionId']").val(actionId);
			$("#awayform input[name='remarks']").val($("#remarks").val());
			$("#awayform").submit();
		},

		/***
		 * 删除加班单
		 */
		deleteaway: function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/workflow/away/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.code=='1'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.workflowaway.initAwayDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		}
}