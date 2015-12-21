jQuery.ims.trainingrequirement = {
		
		requirementDataTable : null,
		
		refreshRequirementDataTable : function(){
			var oSettings = this.requirementDataTable.fnSettings();
			this.requirementDataTable.fnDraw(oSettings);
		},
		
		initRequirementDataTable : function(){
			if (this.requirementDataTable == null) {
				this.requirementDataTable = $('#trainingrequire_dataTable')
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
							"sAjaxSource" : $.ims.getContextPath() + '/training/workflow/require/gather/list',
							"aoColumns" : [ {
								"mDataProp" : "creater"
							}, {
								"mDataProp" : "createDate"
							}, {
								"mDataProp" : "year"
							}, {
								"mDataProp" : "state"
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
									},
									{
										'aTargets' : [ 0 ],
										'fnRender' : function(oObj, sVal) {
											if(sVal != null && sVal != ""){
												return sVal.chinesename;
											}
											return "";
										}
									},
									{
										'aTargets' : [ 5 ],
										'fnRender' : function(oObj, sVal) {
											var operate = '<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 查看详情</a>';
											return operate;
										}
									} ]
						});
			}else{
				this.refreshRequirementDataTable();
			}
		},
		
		showRequireAddModal : function(){
			$("#requirement_modal_header_label").text("发起需求收集");
			$("#hf_id").val(0);
			$("#requirement_modal").modal('show');
		},
		
		/** 发起培训需求收集流程 **/
		submit : function(){
			var year = $("#year").val();
			var description = $("#description").val();
			if(year == ""){
				noty({"text" : "请填写完整的信息！","layout" : "top","type" : "error","timeout" : "2000"});
				return;
			}
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/training/requirement/submit",
				dataType : "json",
				data : {
					year : year,
					description : description
				},
				success : function(json) {
					if (json.success = 1) {
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "success","timeout" : "2000"});
						$("#requirement_modal").modal('hide');
						$.ims.trainingrequirement.refreshRequirementDataTable();
					}else{
						noty({"text" : ""+ json.msg +"","layout" : "top","type" : "error","timeout" : "2000"});
					}
				}
			});
		},
}