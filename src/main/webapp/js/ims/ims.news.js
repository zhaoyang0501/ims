jQuery.ims.news = {
		
		newsDataTable : null,
		
		morenewsDataTable : null,
		
		refreshNewsDataTable : function(){
			var oSettings = this.newsDataTable.fnSettings();
			this.newsDataTable.fnDraw(oSettings);
		},
		
		openwin : function(id){
			var high = 500;
			var width = 500;
			var top = 200;
			var left = 500;
			$("#hf_id").val(id);
			window.open($.ims.getContextPath() + "/sysconfig/news/info/"+id+"","_blank","modal=yes,height="+high+",width="+width+",top="+top+",left="+left+", toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes");
		},
		
		initNewsDataTable : function(){
			if (this.newsDataTable == null) {
				this.newsDataTable = $('#news_dataTable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
									"oLanguage" : {
										"iDisplayLength": 10, 
										"aLengthMenu": [25,50,100],
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
									"sServerMethod" : "POST",
									"bProcessing" : true,
									"bRetrieve" : true,
									"bDestory" : true,
									"bAutoWidth" : false,
									"bSort" : false,
									"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/news/list',
									"fnServerData" : function(sSource, aoData, fnCallback) {
										var type = $("#s_type").val();
										var title = $("#s_title").val();
										if (!!type && type != 0) {
											aoData.push({
												"name" : "type",
												"value" : type
											});
										}
										if (!!title) {
											aoData.push({
												"name" : "title",
												"value" : title
											});
										}
										$.ajax({
											"dataType" : 'json',
											"type" : "POST",
											"url" : sSource,
											"data" : aoData,
											"success" : fnCallback
										});
									},
									"aoColumns" : [ {
										"mDataProp" : "user"
									}, {
										"mDataProp" : "type"
									}, {
										"mDataProp" : "title"
									}, {
										"mDataProp" : "date"
									}, {
										"mDataProp" : "operate"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != null && sVal != "")
												return sVal.chinesename;
											return "";
										}
									}, {
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											return sVal.name;
										}
									}, {
										'aTargets' : [ 2 ],
										'fnRender' : function(oObj, sVal) {
//											return "<a href='sysconfig/news/info/"+oObj.aData.id+"' target='_blank'>"+sVal+"</a>";
											return "<a onclick='$.ims.news.openwin("+oObj.aData.id+")' target='_blank'>"+sVal+"</a>";
										}
									}, {
										'aTargets' : [ 4 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<div class="btn-group">';
											operate += '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>';
											operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
											operate += ' <ul class="dropdown-menu">';
//											operate += '<li><a href="'+$.ims.getContextPath()+'/sysconfig/news/edit?id='+oObj.aData.id+'"><i class="icon-edit"></i> 编辑</a></li>';
											operate += '<li><a onclick="$.ims.news.deletenews('+oObj.aData.id+')"><i class="icon-wrench"></i> 删除</a></li>';
											operate += '</ul>';
											operate += '</div>';
											return operate;
										}
									}]
								});
			} else {
				this.refreshNewsDataTable();
			}
		},
		
		initMoreNewsDataTable : function(){
			if (this.morenewsDataTable == null) {
				this.morenewsDataTable = $('#morenews_datatable')
						.dataTable(
								{
									"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
									"oLanguage" : {
										"iDisplayLength": 10, 
										"aLengthMenu": [25,50,100],
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
									"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/news/list',
									"aoColumns" : [ {
										"mDataProp" : "user"
									}, {
										"mDataProp" : "type"
									}, {
										"mDataProp" : "date"
									}, {
										"mDataProp" : "title"
									}],
									"aoColumnDefs" : [ {
										'aTargets' : [ '_all' ],
										'bSortable' : true,
										'sClass' : 'center'
									}, {
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != null && sVal != "")
												return sVal.chinesename;
											return "";
										}
									}, {
										'aTargets' : [ 1 ],
										'fnRender' : function(oObj, sVal) {
											return sVal.name;
										}
									}, {
										'aTargets' : [ 3 ],
										'fnRender' : function(oObj, sVal) {
											return "<a href='sysconfig/news/info/"+oObj.aData.id+"'>"+sVal+"</a>";
										}
									}]
								});
			} else {
				var oSettings = this.morenewsDataTable.fnSettings();
				this.morenewsDataTable.fnDraw(oSettings);
			}
		},
		
		save : function(){
			var title = $("#title").val();
			var date = $("#date").val();
			var type = $("#type").val();
			var top = $("#top").prop('checked') == true ? 1 : 0;;
			var contents = $("#contents").val();
			if ($.trim(title) == "" || $.trim(contents) == "") {
				noty({"text":"请填写完整公告信息！","layout":"top","type":"error","timeout":"2000"});
				return;
			}
			bootbox.confirm( "是否确认保存？", function (result) {
	            if(result){
	            	$.ajax({
	            		type : "post",
	            		url : $.ims.getContextPath() + "/sysconfig/news/save",
	            		data : {
	            			title : title,
	            			date : date,
	            			typeCode : type,
	            			top : top,
	            			contents : contents
	            		},
	            		dataType : "json",
	            		success : function(json) {
	            			var code = json.code;
	            			var msg = json.msg;
	            			if(code == 1){
	            				noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	            			}else{
	            				noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	            			}
	            		}
	            	});
	            }
			});
		},
		
		deletenews : function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	            		type : "post",
	            		url : $.ims.getContextPath() + "/sysconfig/news/delete",
	            		data : {
	            			id : id
	            		},
	            		dataType : "json",
	            		success : function(json) {
	            			var code = json.code;
	            			var msg = json.msg;
	            			if(code == 1){
	            				noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	            				$.ims.news.refreshNewsDataTable();
	            			}else{
	            				noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	            			}

	            		}
	            	});
	            }
			});
			
		},
}