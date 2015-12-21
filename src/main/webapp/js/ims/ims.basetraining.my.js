jQuery.ims.basetrainingmy = {
		
		basetrainingmyDataTable : null,
		
		refreshDataTable : function(){
			var oSettings = this.basetrainingmyDataTable.fnSettings();
			this.basetrainingmyDataTable.fnDraw(oSettings);
		},
		
		studyonline : function(id,state){
			var width = window.document.body.offsetWidth;
			var height = window.document.body.offsetHeight-100;
			var left = (width - 900)/2;
			window.open($.ims.getContextPath()+"/basetraining/studyonline?id="+id+"&state="+state,"_blank","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=900, height="+height+",left="+left+",top=0")
		},
		
		startexam : function(id,ifscore){
			if(ifscore == "x"){//未设置考试
				noty({"text" : "该培训不需要参加考试！", "layout" : "top", "type" : "warning", "timeout" : "3000"});
			}else if(ifscore == "0"){//还未参加考试
				var width = window.document.body.offsetWidth;
				var height = window.document.body.offsetHeight-100;
				var left = (width - 900)/2;
				window.open($.ims.getContextPath()+"/basetraining/exam/answer?planCourseId="+id,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=900, height="+height+",left="+left+",top=20")
			}else{
				noty({"text" : "亲，你已经参加过考试，不能再参加考试!", "layout" : "top", "type" : "warning", "timeout" : "3000"});
			}
		},
		

		initDataTable : function(){
			if (this.basetrainingmyDataTable == null) {
				var state = 0;
				var ifscore = 0;
				this.basetrainingmyDataTable = $('#dt_basetrainingmy')
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
							"sAjaxSource" : $.ims.getContextPath() + '/basetraining/my/list',
							"aoColumns" : [ {
								"mDataProp" : "title"
							}, {
								"mDataProp" : "description"
							}, {
								"mDataProp" : "targets"
							}, {
								"mDataProp" : "courseNames"
							}, {
								"mDataProp" : "start"
							}, {
								"mDataProp" : "end"
							}],
							"aoColumnDefs" : [
							{
								'aTargets' : [ '_all' ],
								'bSortable' : true,
								'sClass' : 'center'
							}, {
								'aTargets' : [ 3 ],
								'fnRender' : function(oObj, sVal) {
									if(sVal != null){
										var courseNames = "";
										for(var v=0;v<sVal.length;v++){
											courseNames += "<a onclick='' style='cursor:pointer'><img style='height:15px;' src='"+$.ims.getContextPath()+"/css/images/4.jpg'>"+ sVal[v] + "</a><br />";
										}
										return courseNames;
									}
									return "";
								}
							}, {
								'aTargets' : [ 6 ],
								'fnRender' : function(oObj, sVal) {
									return '<a style="cursor:pointer" onclick="$.ims.basetrainingmy.startexam('+oObj.aData.id+',\''+ifscore+'\')">在线考试</a>';
								}
							}]
						});
			}else{
				this.refreshDataTable();
			}
		},
}