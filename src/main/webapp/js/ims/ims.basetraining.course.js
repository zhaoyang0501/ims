jQuery.ims.basetrainingcourse = {
		
		courseDataTable : null,
		
		validation : function(flag){
			$(".required").each(function(){
				if ($.trim($(this).val()) != "") {
		        	$(this).removeClass('valid-error');
		        }else{
		        	$(this).addClass('valid-error');
		        	flag = false;
		        }
			});
			$("#courseType").each(function(){
				if ($.trim($(this).val()) != "0") {
		        	$(this).next().removeClass('valid-error');
		        }else{
		        	$(this).next().addClass('valid-error');
		        	flag = false;
		        }
			});
			return flag;
		},
		
		save : function(){
			var flag = true;
			if(!$.ims.basetrainingcourse.validation(flag)){
				return;		//验证失败
			}
			var id = $("#hf_id").val();
			var courseName = $("#courseName").val();
			var courseType = $("#courseType").val();
			var hours = $("#hours").val();
			var targets = $("#targets").val();
			var remarks = $("#remarks").val();
			var examId = $("#examination").val();
			if(id == 0){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/basetraining/course/save",
	    			data : {
	    				courseName : courseName,
	    				courseType : courseType,
	    				hours : hours,
	    				targets : targets,
	    				remarks : remarks,
	    				examId : examId
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#course_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.basetrainingcourse.refreshCourseDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}else{
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/basetraining/course/save",
	    			data : {
	    				id : id,
	    				courseName : courseName,
	    				courseType : courseType,
	    				hours : hours,
	    				targets : targets,
	    				remarks : remarks,
	    				examId : examId
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#course_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.basetrainingcourse.refreshCourseDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
			
		},
		
		showAddModal : function(){
			$(".valid-error").each(function(){
	        	$(this).removeClass('valid-error');
			});
			$("#course_modal_header_label").text("新建在线学习");
			$("#course_modal").modal('show');
			$("#hf_id").val(0);
			$("#courseName").val("");
			$.ims.common.setchosenvalue("courseType", "0");
			$("#hours").val("");
			$("#targets").val("");
			$("#remarks").val("");
			$("#examination").val("0");
		},
		
		showEidtModel : function(id){
			$(".valid-error").each(function(){
	        	$(this).removeClass('valid-error');
			});
			$("#course_modal_header_label").text("编辑在线学习");
			$("#hf_id").val(id);
			$("#course_modal").modal('show');
			
			$.ajax({
    			type : "GET",
    			url : $.ims.getContextPath() + "/basetraining/course/query/"+id,
    			dataType : "json",
    			success : function(json) {
    				$("#courseName").val(json.courseName);
    				$.ims.common.setchosenvalue("courseType", json.courseType);
    				$("#hours").val(json.hours);
    				$("#targets").val(json.targets);
    				$("#remarks").val(json.remarks);
					$.ims.common.setchosenvalue("examination", json.exam == null? "" : json.exam.id);
    			}
    		});
		},
		
		showUploadModal : function(id){
			$("#course_material_modal_header_label").text("培训资料上传");
			$("#hf_courseId").val(id);
			$("#course_material_modal").modal('show');
		},
		
		deleteCourse : function(id){
			bootbox.confirm("是否确定删除？", function(result){
	            if(result){
	            	$.ajax({
	        			type : "post",
	        			url : $.ims.getContextPath() + "/basetraining/course/delete",
	        			data : {
	        				id : id
	        			},
	        			dataType : "json",
	        			success : function(json) {
	        				var code = json.code;
	        				var msg = json.msg;
	        				if(code == 1){
	        					$("#course_modal").modal('hide');
	        					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.ims.basetrainingcourse.inintCourseDataTable();
	        				}else{
	        					noty({"text":"删除失败，当前培训课程正在被使用！","layout":"top","type":"warning","timeout":"2000"});
	        				}
	        			}
	        		});
	            }
			});
		},
		
		refreshCourseDataTable : function(){
			var oSettings = this.courseDataTable.fnSettings();
			this.courseDataTable.fnDraw(oSettings);
		},
		
		inintCourseDataTable : function(){
			if (this.courseDataTable == null) {
				var type;
				this.courseDataTable = $('#dt_course')
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
							"bFilter" : false,
							"fnDrawCallback" : function(oSettings) {
								$('[rel="popover"],[data-rel="popover"]').popover();
							},
							"sAjaxSource" : $.ims.getContextPath() + '/basetraining/course/list',
							"fnServerData" : function(sSource, aoData,fnCallback) {
								 var courseName = $("#s_coursename").val();
								 var courseType = $("#s_coursetype").val();
								
								 if (!!courseName) {
									 aoData.push({
										 "name" : "courseName",
										 "value" : courseName
									 });
								 }
								 if (!!courseType) {
									 aoData.push({
										 "name" : "courseType",
										 "value" : courseType
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
								"mDataProp" : "courseName"
							}, {
								"mDataProp" : "courseType"
							}, {
								"mDataProp" : "hours"
							}, {
								"mDataProp" : "materials"
							}, {
								"mDataProp" : "targets"
							}, {
								"mDataProp" : "exam"
							}, {
								"mDataProp" : "remarks"
							}, {
								"mDataProp" : "operate"
							}],
							"aoColumnDefs" : [
							{
								'aTargets' : [ '_all' ],
								'bSortable' : true,
								'sClass' : 'center'
							}, {
								'aTargets' : [ 1 ],
								'fnRender' : function(oObj, sVal) {
									if(sVal == "1"){
										return "入职/转岗培训";
									}else if(sVal == "2"){
										return "企业文化制度培训";
									}else if(sVal == "3"){
										return "体系培训";
									}else if(sVal == "4"){
										return "管理者培训";
									}else if(sVal == "5"){
										return "任职能力/岗位技能培训";
									}else if(sVal == "6"){
										return "专业提升培训";
									}else if(sVal == "7"){
										return "战略培训";
									}else if(sVal == "8"){
										return "客户要求培训";
									}
									return "";
								}
							}, {
								'aTargets' : [ 2 ],
								'fnRender' : function(oObj, sVal) {
									return ' <span class="badge badge-warning">'+sVal+' Hours</span>';
								}
							}, {
								'aTargets' : [ 3 ],
								'fnRender' : function(oObj, sVal) {
									if (sVal != null) {
										var files = sVal.split(",");
										var backfile = "";
										for(var i = 0; i < files.length; i++){
											var filepreffix = files[i].substring(0, 13)+files[i].substring(files[i].lastIndexOf('.'), files[i].length);	//前缀
											var filesuffix = files[i].substring(13, files[i].length);	//后缀
											backfile += '<a download='+filesuffix+' href="'+$.ims.getContextPath()+'/upload/basetraining/'+filepreffix+'"><u>'+filesuffix+'</u></a><br />';
										}
										return backfile;
									}else{
										return '';
									}
								}
							}, {
								'aTargets' : [ 5 ],
								'fnRender' : function(oObj, sVal) {
									if (sVal != null && $.trim(sVal) != "") {
										return '<span class="label label-info">√</span>';
									}
									return '<span class="label">X</span>';
								}
							}, {
								'aTargets' : [ 7 ],
								'fnRender' : function(oObj, sVal) {
									var operate = '<div class="btn-group">';
									operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
									operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
									operate += ' <ul class="dropdown-menu">';
									operate += '<li><a onclick="$.ims.basetrainingcourse.showEidtModel('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
									operate += '<li><a onclick="$.ims.basetrainingcourse.showUploadModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 上传教程</a></li>';
									operate += '<li><a onclick="$.ims.basetrainingcourse.deleteCourse('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
									operate += '</ul>';
									operate += '</div>';
									return operate;
								}
							}]
						});
			}else{
				this.refreshCourseDataTable();
			}
		},
}