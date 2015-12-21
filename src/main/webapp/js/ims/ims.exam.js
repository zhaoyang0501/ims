jQuery.ims.exam = {
		examPaperDataTable : null,
		examQuestionDataTable : null,
		examGooverDataTable : null,
		
		showExamQuestionAddModal : function(){
			$("#exam_paper_modal_header_label").text("添加考试试题");
			$("#hf_id").val(0);
			$("#exam_paper_modal").modal('show');
		},
		
		showExamQuestionEditModal : function(id){
			$("#exam_paper_modal_header_label").text("编辑考试试题");
			$("#exam_paper_modal").modal('show');
		},
		
		viewexam : function(){
			var id = $("#hf_paperid").val();
			var width = window.document.body.offsetWidth;
			var height = window.document.body.offsetHeight-100;
			var left = (width - 900)/2;
			window.open($.ims.getContextPath()+"/exam/preview?id="+id,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=900, height="+height+",left="+left+",top=20")
		},
		
		deleteExamQuestion : function(id){
			var msg = "是否确认删除？";
			bootbox.confirm( msg, function (result) {
	            if(result){
					$.ajax({
		    			type : "post",
		    			url : $.ims.getContextPath() + "/exam/question/delete",
		    			data : {
		    				id : id
		    			},
		    			dataType : "json",
		    			success : function(json) {
		    				var code = json.code;
		    				var msg = json.msg;
		    				if(code == 1){
		    					$("#exam_modal").modal('hide');
		    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
		    					$.ims.trainingexam.inintExamDataTable();
		    				}else{
		    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
		    				}
		    			}
		    		});
	            }
			}); 
		},
		
		saveExamQuestion : function(){
			var paperId = $("#hf_paperid").val();
			var type = $("#type").val();
			var difficulty = $("#difficulty").val();
			var subject = $("#topic").val();
			var analyze = $("#answers_analyze").val();
			var optionContents = "";
			var options = "";
			var answers = "";
			var answers = "";
			if(type == "1"){
				optionContents = $("#radio_options").val();
				options = $("#radio_number").val();
				answers = $('input:radio[name="radio_answeroption"]:checked').val();
			}else if(type == "2"){
				optionContents = $("#check_options").val();
				options = $("#check_number").val();
				$("#div_check input[type=checkbox]").each(function(){
				    if(this.checked){
				    	answers += $(this).val() + ",";
				    }
				});
				answers = answers.substring(0, answers.length - 1);
			}else if(type == "3"){
				answers = $('input:radio[name="tfngoption"]:checked').val();
			}else if(type == "4"){
				answers = $("#essay_answers").val();
			}
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/exam/question/save",
				dataType : "json",
				data : {
					type : type,
					difficulty : difficulty,
					subject : subject,
					analysis : analyze,
					optionContents : optionContents,
					options : options,
					answers : answers,
					paperId : paperId
				},
				success : function(json) {
					if (json.code = 1) {
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "2000"});
						$.ims.exam.refreshExamQuestionDataTable();
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
		},
		
		checkExamAnswer : function(){
			$(".answer_radio").each(function(){
				var value = $('input:radio[name="radio_answeroption"]:checked').val();
			});
			
			$("#answer_check_modal_header_label").text("答题检查");
			$("#answer_check_modal").modal('show');
		},
		
		//scource 是考试源 bt：基本培训考试
		submitExamAnswer : function(source){
			bootbox.confirm( "是否确认提交？", function (result) {
	            if(result){
	            	var paperId = $("#hf_id").val();
	            	var planCourseId = $("#hf_planCourseId").val();
	    			var qIds = $("#hf_qIds").val();
	    			var qIdArray = qIds.split(",");
	    			var answers = "";
	    			for(var i = 0; i < qIdArray.length; i++){
	    				var qId = qIdArray[i];
	    				var idArray = qId.split("_");
	    				var id = idArray[1];
	    				var type = idArray[2];
	    				var answer = "";
	    				if(type == "radio"){
	    					answer = $('input:radio[name="'+qId+'"]:checked').val();
	    				}else if(type == "check"){
	    					var answer = "";  
	    			        $('input:checkbox[name="'+qId+'"]:checked').each(function(i){  
	    			            if(0 == i){  
	    			            	answer = $(this).val();  
	    			            }else{  
	    			            	answer += (","+$(this).val());  
	    			            }  
	    			        });
	    				}else if(type == "tfng"){
	    					answer = $('input:radio[name="'+qId+'"]:checked').val();
	    				}else if(type == "essay"){
	    					answer = $("#"+qId+"").val();
	    				}
	    				if(0 == i){
	    					answers += id+"_" + answer;
	    				}else{
	    					answers += "~" + id+"_" + answer;
	    				}
	    			}
	    			$.ajax({
	    				type : "post",
	    				url : $.ims.getContextPath() + "/exam/answer/save",
	    				dataType : "json",
	    				data : {
	    					paperId : paperId,
	    					answers : answers,
	    					source : source,
	    					planCourseId : planCourseId
	    				},
	    				success : function(json) {
	    					if (json.code = 1) {
	    						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "2000"});
	    						$("#submitbtn").disabled = true;
	    					}else{
	    						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
	    					}
	    				}
	    			});
	            }
			});
		},
		
		showExamPaperAddModel : function(){
			$("#exam_paper_modal_header_label").text("添加考试试题");
			$("#hf_id").val(0);
			$("#exam_paper_modal").modal('show');
		},
		
		showExamPaperEditModal : function(id){
			$("#exam_paper_modal_header_label").text("编辑考试试题");
			$("#hf_id").val(id);
			$.ajax({
    			type : "get",
    			url : $.ims.getContextPath() + "/exam/paper/query/" + id,
    			dataType : "json",
    			success : function(json) {
    				$("#subject").val(json.subject);
    				$("#domain").val(json.domain);
    				$("#remarks").val(json.remarks);
    			}
    		});
			$("#exam_paper_modal").modal('show');
		},
		
		deleteExamPaper : function(id){
			bootbox.confirm("是否确认删除？", function(result){
	            if(result){
					$.ajax({
		    			type : "post",
		    			url : $.ims.getContextPath() + "/exam/paper/delete",
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
	        					$.ims.exam.initExamPaperDataTable();
	        				}else{
	        					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	        				}
		    			}
		    		});
	            }
			});
		},
		
		savePaper : function(){
			var id = $("#hf_id").val();
			var subject = $("#subject").val();
			var domain = $("#domain").val();
			var remarks = $("#remarks").val();
			if(id == 0){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/exam/paper/save",
	    			data : {
	    				subject : subject,
	    				domain : domain,
	    				remarks : remarks
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#exam_paper_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.exam.initExamPaperDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}else{
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/exam/paper/save",
	    			data : {
	    				id : id,
	    				subject : subject,
	    				domain : domain,
	    				remarks : remarks
	    			},
	    			dataType : "json",
	    			success : function(json) {
	    				var code = json.code;
	    				var msg = json.msg;
	    				if(code == 1){
	    					$("#exam_paper_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.ims.exam.initExamPaperDataTable();
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
		},
		
		refreshExamPaperDataTable : function(){
			var oSettings = this.examPaperDataTable.fnSettings();
			this.examPaperDataTable.fnDraw(oSettings);
		},
		
		refreshExamQuestionDataTable : function(){
			var oSettings = this.examQuestionDataTable.fnSettings();
			this.examQuestionDataTable.fnDraw(oSettings);
		},
		
		refreshExamGooverDataTable : function(){
			var oSettings = this.examGooverDataTable.fnSettings();
			this.examGooverDataTable.fnDraw(oSettings);
		},
		
		initExamPaperDataTable : function(){
			if (this.examPaperDataTable == null) {
				var type;
				this.examPaperDataTable = $('#dt_exampaper')
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
							"bPaginate" : true,
							"bServerSide" : true,
							"sServerMethod" : "GET",
							"bProcessing" : true,
							"bRetrieve" : true,
							"bDestory" : true,
							"bAutoWidth" : false,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + '/exam/paper/list',
							"fnServerData" : function(sSource, aoData,fnCallback) {
								 var subject = $("#s_subject").val();
								 if (!!subject) {
									 aoData.push({
										 "name" : "subject",
										 "value" : subject
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
								"mDataProp" : "subject"
							}, {
								"mDataProp" : "domain"
							}, {
								"mDataProp" : "remarks"
							}, {
								"mDataProp" : "saveDate"
							}, {
								"mDataProp" : "createrName"
							}],
							"aoColumnDefs" : [
									{
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 5 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += '<ul class="dropdown-menu">';
											operate += '<li><a onclick="$.ims.exam.showExamPaperEditModal('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.exam.deleteExamPaper('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '<li><a href="'+ $.ims.getContextPath() +'/exam/question/index?id='+oObj.aData.id+'"><i class="icon-wrench"></i> 试题管理</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
						});
			}else{
				this.refreshExamPaperDataTable();
			}
		},
		
		initExamQuestionDataTable : function(id){//id 为paperid
			if (this.examQuestionDataTable == null) {
				var type;
				this.examQuestionDataTable = $('#dt_examQuestion')
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
							"sAjaxSource" : $.ims.getContextPath() + '/exam/question/list?id='+id,
							"aoColumns" : [ {
								"mDataProp" : "type"
							}, {
								"mDataProp" : "difficulty"
							}, {
								"mDataProp" : "subject"
							}, {
								"mDataProp" : "options"
							}, {
								"mDataProp" : "optionContents"
							}, {
								"mDataProp" : "answers"
							}, {
								"mDataProp" : "analysis"
							}, {
								"mDataProp" : "operate"
							}],
							"aoColumnDefs" : [
									{
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											type = sVal;
											if(sVal == "1"){
												return '<span class="badge">单选题</span>';
											} else if(sVal == "2"){
												return '<span class="badge">多选题</span>';
											} else if(sVal == "3"){
												return '<span class="badge">判断题</span>';
											} else if(sVal == "4"){
												return '<span class="badge">主观题</span>';
											}
											return "";
										}
									}, {
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal == "1"){
												return '<span class="badge badge-info">低难度</span>';
											} else if(sVal == "2"){
												return '<span class="badge badge-info">中难度</span>';
											} else if(sVal == "3"){
												return '<span class="badge badge-info">高难度</span>';
											}
											return "";
										}
									}, {
										'aTargets' : [ 5 ],
										'fnRender' : function(oObj, sVal) {
											if(type == "3"){
												if(sVal == "A"){
													return '正确';
												}
												return '错误';
											} 
											return sVal;
										}
									}, {
										'aTargets' : [ 7 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
											operate += '<li><a onclick="$.ims.trainingexam.showQuestionEditModal('+oObj.aData.id+')"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.trainingexam.delExam('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
						});
			}else{
				this.refreshExamDataTable();
			}
		},
		
		initExamGooverDataTable : function(){
			if (this.examGooverDataTable == null) {
				var type;
				this.examGooverDataTable = $('#dt_examgoover')
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
							"bPaginate" : true,
							"bServerSide" : true,
							"sServerMethod" : "GET",
							"bProcessing" : true,
							"bRetrieve" : true,
							"bDestory" : true,
							"bAutoWidth" : false,
							"bSort" : false,
							"sAjaxSource" : $.ims.getContextPath() + '/exam/goover/list',
							"aoColumns" : [ {
								"mDataProp" : "user"
							}, {
								"mDataProp" : "paper"
							}, {
								"mDataProp" : "saveDate"
							}, {
								"mDataProp" : "ifgovoer"
							}, {
								"mDataProp" : "score"
							}, {
								"mDataProp" : "operate"
							}],
							"aoColumnDefs" : [
									{
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != null && sVal != ""){
												return sVal.chinesename;
											}else{
												return '';
											}
										}
									}, {
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != null && sVal != ""){
												return sVal.subject;
											}else{
												return '';
											}
										}
									}, {
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											type = sVal;
											if(sVal == "1"){
												return '<span class="label label-success">已阅卷</span>';
											}else{
												return '<span class="label">未阅卷</span>';
											}
										}
									}, {
										'aTargets' : [ 5 ],
										'fnRender' : function(oObj, sVal) {
											return '<li><a onclick="$.ims.exam.redirectGooverPage('+oObj.aData.id+',\''+type+'\')" class="btn btn-info"><i class="icon-edit"></i> 阅卷</a></li>';
										}
									}]
						});
			}else{
				this.refreshExamGooverDataTable();
			}
		},
		
		/**
		 * 跳转到阅卷页面
		 */
		redirectGooverPage : function(answerId,type){
			if(type == "1"){
				noty({"text" : "已阅卷,不能重复阅卷！","layout" : "top","type" : "warning","timeout" : "2000"});
			}else{
				var width = window.document.body.offsetWidth;
				var height = window.document.body.offsetHeight-100;
				var left = (width - 900)/2;
				window.open($.ims.getContextPath()+"/exam/goover/"+answerId,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=900, height="+height+",left="+left+",top=20")
			}
		},
		
		/**
		 * 保存阅卷结果
		 */
		gooverexam : function(){
			var score = $("#score").val();
			var answerId = $("#hf_id").val();
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/exam/goover/save",
				dataType : "json",
				data : {
					answerId : answerId,
					score : score
				},
				success : function(json) {
					if (json.code = 1) {
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "2000"});
						$("#submitbtn").disabled = true;
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
		},
		
}