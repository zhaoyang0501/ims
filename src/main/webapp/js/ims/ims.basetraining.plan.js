jQuery.ims.basetrainingplan = {
		baseTrainingDatatable : null,
		
		refreshBaseTrainingDatatable : function(){
			var oSettings = this.baseTrainingDatatable.fnSettings();
			this.baseTrainingDatatable.fnDraw(oSettings);
		},
		
		
		showAddModel : function(){
			$("#basetraining_modal_header_label").text("添加培训计划");
			$("#hf_id").val(0);
			$("#basetraining_modal").modal('show');
		},
		
		/**
		 * 获取培训对象
		 */
		findUser : function(object){
			var options;
			$.ajax({
				type:"GET",
				url:$.ims.getContextPath() + "/common/user/train/list",
				dataType:"json",
				async:false,
				success:function(json){
					if (json) {
						for(var i=0;i<json.length;i++){
							var deptname = json[i].dept == null ? "" : json[i].dept.name
							options +='<option value="'+json[i].id+'">'+deptname+'-'+json[i].chinesename+'</option>';
						}
					}
				}
			});
			$("#"+object+"").html(options);
		},

		showEidtModel : function(id){
			$("#basetraining_modal_header_label").text("编辑培训计划");
			$("#hf_id").val(id);
			$("#basetraining_modal").modal('show');
			
			$.ajax({
    			type : "GET",
    			url : $.ims.getContextPath() + "/basetraining/plan/query/"+id,
    			dataType : "json",
    			success : function(json) {
    				
    				$("#title").val(json.plan.title);
    				$("#description").val(json.plan.description);
    				var userIds = new Array();
    				var users = json.plan.users;
    				if(users != null){
    					for(var v=0;v<users.length;v++){
    						users[v] = users[v].id;
    					}
    				}
    				$('#employee').val(users);
					$('#employee').multiselect("refresh");
					var courseIds = new Array();
					var courses = json.plan.courses;
					if(courses != null){
    					for(var v=0;v<courses.length;v++){
    						courseIds[v] = courses[v].id;
    					}
    				}
    				$.ims.common.setchosenvalue("course", courseIds);
    				$("#start").val(json.plan.start);
    				$("#end").val(json.plan.end);
    				$("#remarks").val(json.plan.remarks);
    			}
    		});
		},
		
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
				if ($.trim($(this).val()) != "") {
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
		
		save : function(){
			var flag = true;
			if(!$.ims.basetrainingplan.validation(flag)){
				return;		//验证失败
			}
			var id = $("#hf_id").val();
			var title = $("#title").val();
			var description = $("#description").val();
			var employeeId = $("#employee").val().toString();
			var courses = $("#course").val().toString();
			var start = $("#start").val();
			var end = $("#end").val();
			var remarks = $("#remarks").val();
			if(id == 0){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/basetraining/plan/save",
	    			data : {
	    				title : title,
	    				description : description,
	    				employeeIds : employeeId,
	    				courseIds : courses,
	    				start : start,
	    				end : end,
	    				remarks : remarks
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#basetraining_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.basetrainingplan.initBaseTraningDatatable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}else{
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/basetraining/plan/edit",
	    			data : {
	    				id : id,
	    				title : title,
	    				description : description,
	    				employeeIds : employeeId,
	    				courseIds : courses,
	    				start : start,
	    				end : end,
	    				remarks : remarks
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#basetraining_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.basetrainingplan.initBaseTraningDatatable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
			
		},
		
		deleteBaseTraningPlan : function(id){
			bootbox.confirm("是否确定删除？", function(result){
	            if(result){
	            	$.ajax({
	        			type : "post",
	        			url : $.ims.getContextPath() + "/basetraining/plan/delete",
	        			data : {
	        				id : id
	        			},
	        			dataType : "json",
	        			success : function(json) {
	        				var code = json.code;
	        				var msg = json.msg;
	        				if(code == 1){
	        					$("#basetraining_modal").modal('hide');
	        					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.basetraining.initBaseTraningDatatable();
	        				}else{
	        					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	        				}
	        			}
	        		});
	            }
			});
		},
		
		showBaseTrainingPlanInfoModal : function(id) {
			var height = 500;
			var left = (window.document.body.offsetWidth - 900) / 2;
			window.open($.ims.getContextPath() + "/basetraining/planinfo?id=" + id, "_blank",
					"toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=650, height=" + height + ",left=" + left + ",top=20")
		},
		
		
		initBaseTraningDatatable : function(){
			if (this.baseTrainingDatatable == null) {
				var type;
				this.baseTrainingDatatable = $('#dt_basetraining')
				.dataTable(
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
							"fnDrawCallback" : function(oSettings) {
								$('[rel="popover"],[data-rel="popover"]').popover();
							},
							"sAjaxSource" : $.ims.getContextPath() + '/basetraining/plan/list',
							"fnServerData" : function(sSource, aoData,fnCallback) {
								 var plan = $("#s_plan").val();
								
								 if (!!plan) {
									 aoData.push({
										 "name" : "plan",
										 "value" : plan
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
								"mDataProp" : "title"
							}, {
								"mDataProp" : "description"
							}, {
								"mDataProp" : "courseName"
							}, {
								"mDataProp" : "user"
							}, {
								"mDataProp" : "start"
							}, {
								"mDataProp" : "end"
							}, {
								"mDataProp" : "targets"
							}, {
								"mDataProp" : "state"
							}],
							"aoColumnDefs" : [
							{
								'aTargets' : [ '_all' ],
								'bSortable' : true,
								'sClass' : 'center'
							}, {
								'aTargets' : [ 2 ],
								'fnRender' : function(oObj, sVal) {
									if(sVal != null){
										var chinesenames = "";
										for(var v=0;v<sVal.length;v++){
											chinesenames += "<a onclick='' style='cursor:pointer'>"+ sVal[v] + "</a><br />";
										}
										return chinesenames;
									}
									return "";
								}
							}, {
								'aTargets' : [ 3 ],
								'fnRender' : function(oObj, sVal) {
									if(sVal != null){
										var chinesenames = "";
										for(var v=0;v<sVal.length;v++){
											chinesenames += '<span class="label label-info">'+sVal[v]+'</span>' + " ";
										}
										return chinesenames;
									}
									return "";
								}
							}, {
								'aTargets' : [ 8 ],
								'fnRender' : function(oObj, sVal) {
									var operate = '<div class="btn-group">';
									operate += '<a class="btn2 btn-info" onclick="$.ims.basetrainingplan.showBaseTrainingPlanInfoModal(' + oObj.aData.id + ')"><i class="icon-zoom-in"></i> 详细信息</a>';
									operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
									operate += ' <ul class="dropdown-menu">';
									operate += '<li><a onclick="$.ims.basetrainingplan.showEidtModel('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
									operate += '<li><a onclick="$.ims.basetrainingplan.deleteBaseTraningPlan('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
									operate += '</ul>';
									operate += '</div>';
									return operate;
								}
							}]
						});
			}else{
				this.refreshBaseTrainingDatatable();
			}
		},

}