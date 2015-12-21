jQuery.ims.basetraining = {
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
		
		save : function(){
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
	    			url : $.ims.getContextPath() + "/basetraining/save",
	    			data : {
	    				title : title,
	    				description : description,
	    				employeeId : employeeId,
	    				courses : courses,
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
	    					$.ims.basetraining.initBaseTraningDatatable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}else{
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/basetraining/edit",
	    			data : {
	    				id : id,
	    				title : title,
	    				description : description,
	    				employeeId : employeeId,
	    				courses : courses,
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
	    					$.ims.basetraining.initBaseTraningDatatable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
			
		},
		
		showEidtModel : function(id){
			$("#basetraining_modal_header_label").text("编辑培训计划");
			$("#hf_id").val(id);
			$("#basetraining_modal").modal('show');
			
			$.ajax({
    			type : "GET",
    			url : $.ims.getContextPath() + "/basetraining/course/query/"+id,
    			dataType : "json",
    			success : function(json) {
    				
    				$("#title").val(json.plan.title);
    				$("#description").val(json.plan.description);
    				$.ims.common.setchosenvalue("employee", json.user);
    				$.ims.common.setchosenvalue("course", json.course);
    				$("#start").val(json.plan.start);
    				$("#end").val(json.plan.end);
    				$("#remarks").val(json.plan.remarks);
    			}
    		});
		},
		
		deleteBaseTraningPlan : function(id){
			bootbox.confirm("是否确定删除？", function(result){
	            if(result){
	            	$.ajax({
	        			type : "post",
	        			url : $.ims.getContextPath() + "/basetraining/delete",
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
							"sAjaxSource" : $.ims.getContextPath() + '/basetraining/list',
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
								"mDataProp" : "remarks"
							}, {
								"mDataProp" : "state"
							}, {
								"mDataProp" : "operate"
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
											chinesenames += sVal[v] + "<br />";
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
								'aTargets' : [ 9 ],
								'fnRender' : function(oObj, sVal) {
									var operate = '<div class="btn-group">';
									operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
									operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
									operate += ' <ul class="dropdown-menu">';
									operate += '<li><a onclick="$.ims.basetraining.showEidtModel('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
									operate += '<li><a onclick="$.ims.basetraining.deleteBaseTraningPlan('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
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