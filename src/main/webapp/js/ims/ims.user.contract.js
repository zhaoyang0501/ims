jQuery.ims.usercontract = {
	contractDataTable : null,

	refreshContractDataTable : function() {
		if (this.contractDataTable != null) {
			var oSettings = this.contractDataTable.fnSettings();
			this.contractDataTable.fnDraw(oSettings);
		}
	},

	/** 合同datatable * */
	initContractDataTable : function() {
		if (this.contractDataTable == null) {
			this.contractDataTable = $('#dt_contract').dataTable({
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
				"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/resume/contract/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var userId = $("#s_user").val();
					var fromDate_s = $("#s_fromDate_s").val();
					var fromDate_e = $("#s_fromDate_e").val();
					var endDate_s = $("#s_endDate_s").val();
					var endDate_e = $("#s_endDate_e").val();
					if (!!userId) {
						aoData.push({
							"name" : "userId",
							"value" : userId
						});
					}
					if (!!fromDate_s) {
						aoData.push({
							"name" : "fromDate_s",
							"value" : fromDate_s
						});
					}
					if (!!fromDate_e) {
						aoData.push({
							"name" : "fromDate_e",
							"value" : fromDate_e
						});
					}
					if (!!endDate_s) {
						aoData.push({
							"name" : "endDate_s",
							"value" : endDate_s
						});
					}
					if (!!endDate_e) {
						aoData.push({
							"name" : "endDate_e",
							"value" : endDate_e
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
					"mDataProp" : "chinesename"
				}, {
					"mDataProp" : "deptname"
				}, {
					"mDataProp" : "contractNo"
				}, {
					"mDataProp" : "contractTypeName"
				}, {
					"mDataProp" : "signDate"
				}, {
					"mDataProp" : "fromDate"
				}, {
					"mDataProp" : "endDate"
				}, {
					"mDataProp" : "operate"
				} ],
				"aoColumnDefs" : [ {
					'aTargets' : [ '_all' ],
					'bSortable' : true,
					'sClass' : 'center'
				}, {
					'aTargets' : [ 7 ],
					'fnRender' : function(oObj, sVal) {
						var obj = '<a onclick="$.ims.usercontract.showContractDetailModal('+oObj.aData.id+')">合同详细</a>&nbsp;&nbsp;&nbsp;' +
							'<a onclick="$.ims.usercontract.showContractEidtModal('+oObj.aData.id+')">编辑</a>&nbsp;&nbsp;&nbsp;' + 
							'<a onclick="$.ims.usercontract.deleteContract('+oObj.aData.id+')">删除</a>';
						return obj;
					}
				} ]
			});
		} else {
			this.refreshContractDataTable();
		}
	},
	
	showContractDetailModal : function(id){
		var height = 500;
		var left = (window.document.body.offsetWidth - 900)/2;
		window.open($.ims.getContextPath()+"/sysconfig/resume/contractinfo?id="+id,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=650, height="+height+",left="+left+",top=20")

	},

	showContractAddModal : function() {
		$("#contract_modal_header_label").text("新增合同信息");
		$("#hf_id").val(0);
		$("#contract_modal").modal('show');
	},
	
	showContractEidtModal : function(id){
		$("#contract_modal_header_label").text("编辑合同信息");
		$("#hf_id").val(id);
		$.ajax({
			type : "get",
			url : $.ims.getContextPath() + "/sysconfig/resume/contract/query/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
					$.ims.common.setchosenvalue("employee",json.userId);
					$("#contractNo").val(json.contractNo);
					$.ims.common.setchosenvalue("contractType",json.contractType);
					$("#signDate").val(json.signDate);
					$("#fromDate").val(json.fromDate);
					$("#endDate").val(json.endDate);
					json.isProbation == 1 ? $("#isProbation_1").prop('checked',true) : $("#isProbation_0").prop('checked',true);
					json.isRelieve == 1 ? $("#isRelieve_1").prop('checked',true) : $("#isRelieve_0").prop('checked',true);
					json.isRenewal == 1 ? $("#isRenewal_1").prop('checked',true) : $("#isRenewal_0").prop('checked',true);
					$("#remarks").val(json.remarks);
				}	
			}
		});
		$("#contract_modal").modal('show');
	},

	/** 保存合同 * */
	saveContract : function() {
		var id = $("#hf_id").val();
		var userId = $("#employee").val();
		var contractNo = $("#contractNo").val();
		var contractType = $("#contractType").val();
		var signDate = $("#signDate").val();
		var fromDate = $("#fromDate").val();
		var endDate = $("#endDate").val();
		var isProbation = $('input[name=isProbation]:checked').val();
		var isRelieve = $('input[name=isRelieve]:checked').val();
		var isRenewal = $('input[name=isRenewal]:checked').val();
		var remarks = $("#remarks").val();
		if(id == 0){
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/sysconfig/resume/contract/save",
				data : {
					userId : userId,
					contractNo : contractNo,
					contractType : contractType,
					signDate : signDate,
					fromDate : fromDate,
					endDate : endDate,
					isProbation : isProbation,
					isRelieve : isRelieve,
					isRenewal : isRenewal,
					remarks : remarks
				},
				dataType : "json",
				success : function(json) {
					var success = json.code;
					if (success == "1") {
						noty({
							"text" : "操作成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$.ims.usercontract.initContractDataTable();
					} else {
						noty({
							"text" : "操作失败，请联系管理员！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
				}
			});
		}else{
			$.ajax({
				type : "post",
				url : $.ims.getContextPath() + "/sysconfig/resume/contract/save",
				data : {
					id : id,
					userId : userId,
					contractNo : contractNo,
					contractType : contractType,
					signDate : signDate,
					fromDate : fromDate,
					endDate : endDate,
					isProbation : isProbation,
					isRelieve : isRelieve,
					isRenewal : isRenewal,
					remarks : remarks
				},
				dataType : "json",
				success : function(json) {
					var success = json.code;
					if (success == "1") {
						noty({
							"text" : "操作成功！",
							"layout" : "top",
							"type" : "success",
							"timeout" : "2000"
						});
						$.ims.usercontract.initContractDataTable();
					} else {
						noty({
							"text" : "操作失败，请联系管理员！",
							"layout" : "top",
							"type" : "warning",
							"timeout" : "2000"
						});
					}
				}
			});
		}
	},
	
	deleteContract : function(id){
		bootbox.confirm("是否确认删除", function(result) {
			if(result){
				$.ajax({
					type : "post",
					url : $.ims.getContextPath() + "/sysconfig/resume/contract/delete",
					data:{
						id : id
					},
					dataType : "json",
					success : function(json) {
						if (json) {
							noty({ "text" : "删除成功!", "layout" : "top", "type" : "success", "timeout" : "2000" });
						}
						$.ims.usercontract.initContractDataTable();
					}
				});
			}
		});
	},
}