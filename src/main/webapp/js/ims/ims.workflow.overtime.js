jQuery.ims.workflowovertime = {
		user : null,
		overtimeDataTable : null,
		addNewRow : function(){
			var tr=$('<tr class="data_tr"><td><button name="removebtn"  >－</button></td>' +
				'<td><select name="user" class=" chosen required1" data-placeholder="请选择人员">' + this.user + '</select></td>' +
				'<td><input name="empnumber"  class="input-small " type="text" readonly="readonly" ></input></td>' +
				'<td><div class=" input-append date"><input    class="required"  style="width: 120px;"  name="startTime"  type="text" readonly="readonly" ><span class="add-on"><i class="icon-th"></i></span></div></td>' +
				'<td><div class=" input-append date"><input class="required"  style="width: 120px;"  name="endTime"  type="text" readonly="readonly"><span class="add-on"><i class="icon-th"></i></span></div></td>' +
				'<td><input name="hours"   class="input-small required" type="text" ></input></td>' +
				'<td><input name="remark"  class="required" type="text" ></input></td>' +
				'<td><input name="footnote"  type="text" ></input></td>' +
				'<td><span class="tip"></span><input type="hidden" name="maxHours"></input><input type="hidden" name="nowHours"></input> </tr>');
			tr.insertBefore("#bottom_tr");
			
			$(".required").change(function(){
		    	var v = $(this).val();
		    	if ($.trim(v) != "") {
		        	$(this).removeClass('valid-error');
		        }else{
		        	$(this).addClass('valid-error');
		        }
		    });
			$(".required1").change(function(){
				if ($.trim($(this).val()) != "0") {
		        	$(this).next().removeClass('valid-error');
		        	$.ims.workflowovertime.triggerMaxHour(tr);
		        }else{
		        	$(this).next().addClass('valid-error');
		        }
			});
			
		   tr.find("input[name='startTime']").change(function(){
			   $.ims.workflowovertime.triggerMaxHour(tr);
			});
			
			$("button[name=removebtn]").click(function(){
				$(this).parent().parent().remove();
			});
			
		    $(".chosen").chosen({
				allow_single_deselect : true,
			});
		  
		    $('.date').datetimepicker({
		    	language : 'zh-CN',
				format : 'yyyy-mm-dd hh:ii',
				autoclose : 1,
				todayHighlight : 1,
				minuteStep:30,
				forceParse : 0
			});
		   
		    tr.find("select[name='user']").change(function(){
		    	$.ajax({
					type:"GET",
					url:$.ims.getContextPath() + "/user/query/"+ $(this).val(),
					dataType:"json",
					async:true,
					success:function(json){
						if (json) {
							tr.find("input[name='empnumber']").val(json.empnumber);
						}
					}
				});
		    });
		},
		/**获取最大请假时间*/
		triggerMaxHour:function(tr){ 
			var user=tr.find("select[name='user']").val();
			var date=tr.find("input[name='startTime']").val();
			if(date!=''&&user!=''&&user!=0){
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/workflow/overtime/getOverTimeMaxHour",
					dataType : "json",
					data :{userid:user,curDate:date},
					success : function(json) {
						if (json.success = 1) {
							tr.find("input[name='nowHours']").val(json.datas.nowHours);
							tr.find("input[name='maxHours']").val(json.datas.maxHours);
							///**1按月，2按年*/
							if(json.datas.type=='1')
								tr.find(".tip").html("本月可申请"+json.datas.maxHours+"工时,本月已申请"+json.datas.nowHours);
							else
								tr.find(".tip").html("今年可申请"+json.datas.maxHours+"工时,今年已申请"+json.datas.nowHours);
						}else{
							noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
						}
					}
				});
			}
		},
		/**初始化加班列表*/
		initOvertimeDataTable : function(){
			if (this.overtimeDataTable == null) {
				this.overtimeDataTable = $('#overtimeDataTable')
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
									"sAjaxSource" : $.ims.getContextPath() + '/workflow/overtime/list',
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
										"mDataProp" : "applyDate"
									}, {
										"mDataProp" : "workFlowOverTimeDetail"
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
											return sVal.length;
										}
									}, {
										'aTargets' : [4 ],
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
										'aTargets' : [5 ],
										'fnRender' : function(oObj, sVal) {
											if(oObj.aData.wfentry.id==null) return "";
											return "<a target=\"_blank\" href=\"workflow/workflowgraph/"+oObj.aData.wfentry.id+"\">"+sVal+"</a>";
										}
									}, {
										'aTargets' : [6 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
											operate += ' <ul class="dropdown-menu">';
											if(oObj.aData.wfentry.id!=undefined)
											operate += '<li><a  target="_blank" href="workflow/toapprove/goApprove/'+oObj.aData.wfentry.id+'" ><i class="icon-wrench"></i> 查看加班单</a></li>';
											operate += '<li><a onclick="$.ims.workflowovertime.deleteOvertime('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				var ajaxSource =$.ims.getContextPath() + '/workflow/overtime/list';
				var oSettings = this.overtimeDataTable.fnSettings();
				oSettings.sAjaxSource = ajaxSource;
				this.overtimeDataTable.fnDraw(oSettings);
			}
		},
		/**审核提交*/
		submitForm:function(actionid){
			if($("#approvals").val()==""){
				noty({"text":"请填写审批意见！","layout":"top","type":"error","timeout":"2000"});
				return;
			}
			$("#workflowform input[name='actionId']").val(actionid);
			$("#workflowform").submit();
		},
		/**提交审核*/
		submit : function(what){
			if(!this.validation())
				return false;
			var jsonObject=new Object();
			var details = new Array();
			$.each( $("#dt_overtimerequire_body .data_tr"), function(){
				var user_= new Object();
				user_.id=$(this).find("select[name=user]").val();
				details.push({
					  startTime: $(this).find("input[name=startTime]").val(),
					  endTime: $(this).find("input[name=endTime]").val(),
					  hours: $(this).find("input[name=hours]").val(),
					  user:user_,
					  remark: $(this).find("input[name=remark]").val(),
					  footnote: $(this).find("input[name=footnote]").val()});
				});
			jsonObject.workFlowOverTimeDetails=details;
			if($("#leader").val()!="")
			jsonObject.leader=$("#leader").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/workflow/overtime/submit?token="+$("input[name=token]").val(),
				dataType : "json",
				contentType:"application/json",
				data : JSON.stringify(jsonObject),
				complete:function(XMLHttpRequest, textStatus) {
					  $(what).removeAttr("disabled");
			 	},
			 	beforeSend:function (XMLHttpRequest) {
			 		 $(what).attr("disabled","disabled");
			 	},
				success : function(json) {
					$.noty.closeAll();
					if (json.code == 1) {
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "10000"});
						$("#dt_overtimerequire_body .data_tr").remove();
						$.ims.workflowovertime.addNewRow();
						jQuery.ims.common.setchosenvalue("leader","");
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
			
		},
		/**表单验证*/
		validation : function(){
			flag=true;
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
			
			/*工时不合法*/
			var reg =  /^[0-9]+\.{0,1}[0-9]{0,2}$/;
			$("input[name=hours]").each(function(){
				var isok=reg.test($.trim($(this).val()));
				if(!isok){
					$.noty.closeAll();
					noty({"text":"工时输入不合法","layout":"top","type":"warning"});
					flag= false;
				}
			});
			if(!flag) return false;
			
			$("input[name=hours]").each(function(){
				var now=parseFloat($.trim($(this).val()));
				var maxHours=parseFloat($.trim($(this).parent().parent().find("input[name=maxHours]").val()));
				var nowHours=parseFloat($.trim($(this).parent().parent().find("input[name=nowHours]").val()));
				if(now+nowHours>=maxHours){
					$.noty.closeAll();
					noty({"text":"本月或者本年加班时数超过上限！","layout":"top","type":"warning"});
					flag= false;
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
		
		/***
		 * 删除加班单
		 */
		deleteOvertime: function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ims.getContextPath() + "/workflow/overtime/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				$.noty.closeAll();
	        				if(json.code=='1'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.workflowovertime.initOvertimeDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		}
}