jQuery.ims.trainingrequirementgather = {
	
	index : 1,	//需求收集行标
	trainingtype : null,	//培训类型
	monthOption : null,		//月份
	trainedUser : null,		//培训对象
	teacher : null,			//培训讲师
	
	// 数据验证
	valid : function(flag){

		//培训方式改变
		for(var n=1; n< this.index; n++){
			
			var method = $("input[name='t_method_"+ n +"']:checked").val();
			if(method == "2"){
				$("#orgnization_" + n).addClass('required');
				$("#teacher_" + n).removeClass('required2');
			}else{
				$("#orgnization_" + n).removeClass('required');
				$("#teacher_" + n).addClass('required2');
			}
		}
		//为上面新增的required重新绑定change事件
		$(".required").each(function(){
	    	var v = $(this).val();
	    	if ($.trim(v) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        	flag = false;
	        }
	    });
		$(".required2").each(function(){
			if ($.trim($(this).val()) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        	flag = false;
	        }
		});
		//数字验证
		$(".number").each(function(){
			if(!isNaN($(this).val())){
	    		$(this).removeClass('valid-error');
	    	}else{
	    		$(this).addClass('valid-error');
	    		flag = false;
	    	}
		});
		
		// 参训人员验证
		$("select[name='trainees']").each(function(){
			var trainees = $(this).val();
			if (trainees == undefined) {
				$(this).next().addClass('valid-error');
				flag = false;
			}else{
				$(this).next().removeClass('valid-error');
			}
		});
	
		return flag;
	},
	
	/**
	 * 培训需求收集 提交
	 */
	approve : function(actionId){
		var flag = true;
		if(!this.valid(flag)){
			return;		//验证失败
		}
		
		var requireId = $("#hf_requireId").val();
		
		var rows = $("#dt_traingrequire tr").length;	//前两行为header
		var array = new Array();
		for(var i=1; i< this.index; i++){
			var element;
			var type = $("#type_"+ i).val();
			var subject = $("#subject_" + i).val();
			var trainees = $("#trainees_" + i).val();
			var month = $("#month_" + i).val();
			var cost = $("#money_" + i).val();
			var hours = $("#hours_" + i).val();
			var method = $('input:radio[name="t_method_'+ i +'"]:checked').val();
			var teacher = $("#teacher_" + i).val();
			var orgnization = $("#orgnization_" + i).val();
			var goals = $("#goals_" + i).val();
			var remark = $("#remark_" + i).val();
			
			array.push({trainingType: type,subject: subject,subject: subject,month: month,trainees: trainees.toString(),
				cost: cost,hours: hours,method: method,teacher:teacher,orgnization: orgnization,goals: goals,remark: remark});
		}
		var data = JSON.stringify(array);
		$.ajax({
			type:"POST",
			url:$.ims.getContextPath() + "/training/workflow/require/gather/approve/" + requireId + "/" + actionId,
			dataType:"json",
			contentType:"application/json",
			data : data,
			success:function(json){
				var code = json.code;
				var msg = json.msg;
				if(code == 1){
					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
					$("input[name=operate]").attr("disabled","disabled");
				}else{
					noty({"text":""+ msg +"","layout":"top","type":"error","timeout":"2000"});
				}
			}
		});
	},
		
	/** 添加新的一行 **/
	addNewRow : function(){
		var tr= '<tr><td><button name="removebtn" id="require_' + this.index + '">－</button></td>' +
				'<td><select name="trainingtype" id="type_' + this.index + '" style="width:130px;" class="chosen required2" data-placeholder="请选择培训类型">' + this.trainingtype + '</select></td>' +
				'<td><input id="subject_' + this.index + '" type="text" style="width:130px;" class="required"></input></td>' +
				'<td><select name="trainees" id="trainees_' + this.index + '" multiple="multiple" class="required2">'+ this.trainedUser +'</select></td>' +
				'<td><select name="month" id="month_' + this.index + '" style="width:60px;" class="chosen">' + this.monthOption() + '</select></td>' +
				'<td><input id="money_' + this.index + '" style="line-height:28px; width:80px;" value="0" class="required number"></input></td>' +
				'<td><input id="hours_' + this.index + '" style="line-height:28px; width:50px;" value="0" class="required number"></input></td>' +
				'<td>' +
				'<label class="radio inline"><input name="t_method_' + this.index + '" value="1" onclick="$.ims.trainingrequirementgather.methodChange('+this.index+')" type="radio" checked="checked" />内</label>' +
				'<label class="radio inline"><input name="t_method_' + this.index + '" value="2" onclick="$.ims.trainingrequirementgather.methodChange('+this.index+')" type="radio">外</label>' +
				'</td>' +
				'<td>' +
				'<select id="teacher_' + this.index + '" style="width:130px;" class="chosen" data-placeholder="请选择讲师">' + this.teacher + '</select>' +
				'<input id="orgnization_' + this.index + '" type="text" style="display:none;width:115px;"></input>' +
				'</td>' +
				'<td><input id="goals_' + this.index + '" type="text" style="width:130px;"  class="required"></input></td>' +
				'<td><input id="remark_' + this.index + '" type="text" style="width:90%;"></input></td>';
		tr +="</tr>";
		$("#dt_traingrequire_body").append(tr);
		this.index ++;
		
		$("button[name=removebtn]").click(function(){
			$(this).parent().parent().remove();
		});
		
		$(".chosen").chosen({
			allow_single_deselect : true,
		});

		$('select[name=trainees]').multiselect({
			includeSelectAllOption: true,
            enableFiltering: true,
            numberDisplayed:1,
            maxHeight: 300,
            buttonText : function(options){
				if (options.length == 0) {
				    return '请选择培训对象 ';
				}
				else if (options.length > 0) {
				    return options.length + ' 名人员被选中';
				}
            },
		});
		
		//change事件绑定
	    $(".required").change(function(){
	    	var v = $(this).val();
	    	if ($.trim(v) != "") {
	        	$(this).removeClass('valid-error');
	        }else{
	        	$(this).addClass('valid-error');
	        }
	    });
	    //multiple-select专用
	    $(".required2").change(function(){
	    	var v = $(this).val();
	    	if ($.trim(v) != "") {
	        	$(this).next().removeClass('valid-error');
	        }else{
	        	$(this).next().addClass('valid-error');
	        }
	    });
		
	    $(".number").change(function () {
	    	if(!isNaN($(this).val())){
	    		$(this).removeClass('valid-error');
	    	}else{
	    		$(this).addClass('valid-error');
	    	}
	    });
	},
	
	delRow : function(){
		var row = $("input[name='requireCheckbox']:checked");
		if(row.length != 1){
			alert("请选择一条数据进行删除操作。");
			return false;
		}else{
			if(window.confirm("你确定要删除这条吗？")){
				row.parent().parent().remove();
			}
		}
	},
	
	monthOption : function(){
		var monthoption;
		for(var i=1;i<=12;i++){
			monthoption += "<option value='"+i+"'>"+i+"</option>";
		}
		return monthoption;
	},

	findTraingType : function(){
		var tr;
		$.ajax({
			type:"GET",
			url:$.ims.getContextPath() + "/sysconfig/code/traingtype",
			dataType:"json",
			async:false,
			success:function(json){
				if (json) {
					tr +='<option value=""></option>';
					for(var key in json){
						tr +='<option value="'+key+'">'+json[key]+'</option>';
					}
				}
			}
		});
		this.trainingtype = tr;
	},
	
	findTeacher : function(){
		var tr;
		$.ajax({
			type:"GET",
			url:$.ims.getContextPath() + "/training/teacher/all",
			dataType:"json",
			async:false,
			success:function(json){
				if (json) {
					tr +='<option value=""></option>';
					for(var key in json){
						tr +='<option value="'+key+'">'+json[key]+'</option>';
					}
				}
			}
		});
		this.teacher = tr;
	},
	
	/**
	 * 获取培训对象
	 */
	findUser : function(){
		var tr;
		$.ajax({
			type:"GET",
			url:$.ims.getContextPath() + "/common/user/train/list",
			dataType:"json",
			async:false,
			success:function(json){
				if (json) {
					for(var i=0;i<json.length;i++){
						var deptname = json[i].dept == null ? "" : json[i].dept.name
						tr +='<option value="'+json[i].id+'">'+deptname+'-'+json[i].chinesename+'</option>';
					}
				}
			}
		});
		this.trainedUser = tr;
	},
	
	//培训方式更变时间
	methodChange : function(index){
		var method = $("input[name='t_method_"+ index +"']:checked").val();
		if (method == "1") {
			$("#teacher_" + index).hide();
			$("#teacher_" + index + "_chzn").show();
			$("#orgnization_" + index).hide();
		}else if(method == "2"){
			$("#teacher_" + index).hide();
			$("#teacher_" + index + "_chzn").hide();
			$("#orgnization_" + index).show();
		}
	},
}

/** 初步审核 **/
jQuery.ims.trainingrequirementgatherprimary = {
		
		trainingrequireprimary : null,
		
		refreshTrainingRequirePrimary : function(){
			var oSettings = this.trainingrequireprimary.fnSettings();
			this.trainingrequireprimary.fnDraw(oSettings);
			jQuery.ims.common.tableRowspan("#dt_traingrequireprimary",1);
		},
		
		inintTrainingRequirePrimary : function(year){
			if (this.trainingrequireprimary == null) {
				this.trainingrequireprimary = $('#dt_traingrequireprimary')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span5'l>r>t<'row-fluid'>",
									"oLanguage" : {
										"sZeroRecords" : "抱歉， 暂时没有记录",
									},
									"bPaginate" : false,
									"bServerSide" : true,
									"sServerMethod" : "GET",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/training/workflow/require/gather/'+ year +'/dept/submit/state',
									"fnDrawCallback" : function(oSettings) {
										jQuery.ims.common.tableRowspan("#dt_traingrequireprimary",1);
									},
									"aoColumns" : [ {
										"mDataProp" : "dept"
									}, {
										"mDataProp" : "totalno"
									}, {
										"mDataProp" : "totalneino"
									}, {
										"mDataProp" : "totalwaino"
									}, {
										"mDataProp" : "totalmoney"
									}, {
										"mDataProp" : "totalhours"
									}, {
										"mDataProp" : "sbdate"
									}, {
										"mDataProp" : "deitals"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 4 ],
										'fnRender' : function(oObj, sVal) {
											return '￥ '+sVal;
										}
									}, {
										'aTargets' : [ 7 ],
										'fnRender' : function(oObj, sVal) {
											return '<a href="'+$.ims.getContextPath()+'/training/workflow/require/gather/hr/details/'+oObj.aData.deptid+'"> 详细信息</a>';
										}
									}]
								});
			} else {
				this.refreshTrainingRequirePrimary();
			}
		},
		
		showDetails : function(deptid){
			$("#require_label").text("详细信息");
			$("#requireModal").modal('show');
		},
}


/** 中心领导审核 **/
jQuery.ims.trainingrequirementgatherleader = {
		
		trainingrequireleader : null,
		
		refreshTrainingRequireLeader : function(){
			var oSettings = this.trainingrequireleader.fnSettings();
			this.trainingrequireleader.fnDraw(oSettings);
			jQuery.ims.common.tableRowspan("#dt_traingrequireleader",1);
		},
		
		inintTrainingRequireLeader : function(year){
			if (this.trainingrequireleader == null) {
				this.trainingrequireleader = $('#dt_traingrequireleader')
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
									"sAjaxSource" : $.ims.getContextPath() + '/training/requirement/'+ year +'/list',
									"fnDrawCallback" : function(oSettings) {
//										$("input[name='leaderCheckbox']").uniform();
										jQuery.ims.common.tableRowspan("#dt_traingrequireleader",1);
									},
									"aoColumns" : [ {
										"mDataProp" : "dept"
									}, {
										"mDataProp" : "trainingType"
									}, {
										"mDataProp" : "subject"
									}, {
										"mDataProp" : "trainees"
									}, {
										"mDataProp" : "month"
									}, {
										"mDataProp" : "cost"
									}, {
										"mDataProp" : "hours"
									}, {
										"mDataProp" : "method"
									}, {
										"mDataProp" : "teacher_orgnization"
									}, {
										"mDataProp" : "goals"
									}, {
										"mDataProp" : "state"
									}, {
										"mDataProp" : "remark"
									}, {
										"mDataProp" : "id"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [10],
										'fnRender':function(oObj,sVal){
											if (sVal == "3") {
												return '<span class="label label-important">已去除</span>';
											}else{
												return '<span class="label label-success">正常</span>';
											}
										}
									}, {
										'aTargets' : [12],
										'fnRender':function(oObj,sVal){
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a href="'+$.ims.getContextPath()+'/project/edit?id='+oObj.aData.id+'"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.project.deleteProject('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;	
										}
									}]
								});
				
			} else {
				this.refreshTrainingRequireLeader();
			}
		},
		
		showAddModal : function(){
			$("#trainingrequiremodal").modal('show');
		},
		
		approve : function(){
			
		},
}