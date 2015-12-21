jQuery.ims.userresume = {

	resumeInfoDataTable : null,
	resumeDataTable : null,

	/**
	 * 刷新档案信息。
	 * 
	 */
	refreshresumeInfoDataTable : function() {
		if (this.resumeInfoDataTable != null) {
			var oSettings = this.resumeInfoDataTable.fnSettings();
			this.resumeInfoDataTable.fnDraw(oSettings);
		}
	},

	/**
	 * 档案信息详情。
	 */
	initResumeDataTable : function() {
		if (this.resumeInfoDataTable == null) {
			this.resumeInfoDataTable = $("#resume_datatable").dataTable({
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
				"sServerMethod" : "POST",
				"bProcessing" : true,
				"bRetrieve" : true,
				"bDestory" : true,
				"bAutoWidth" : false,
				"bSort" : true,
				"bFilter" : false,
				"sAjaxSource" : $.ims.getContextPath() + '/sysconfig/resume/list',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var chinesename = $("#s_chinesename").val();
					var empnumber = $("#s_empno").val();
					var deptId = $("#s_dept").val();
					var state = $("#s_state").val();
					if (!!chinesename) {
						aoData.push({
							"name" : "chinesename",
							"value" : chinesename
						});
					}
					if (!!empnumber) {
						aoData.push({
							"name" : "empnumber",
							"value" : empnumber
						});
					}
					if (!!deptId) {
						aoData.push({
							"name" : "deptId",
							"value" : deptId
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
						"type" : "POST",
						"url" : sSource,
						"data" : aoData,
						"success" : fnCallback
					});
				},
				"aoColumns" : [ {
					"mDataProp" : "userName"
				}, {
					"mDataProp" : "empnumber"
				}, {
					"mDataProp" : "sex"
				}, {
					"mDataProp" : "dept"
				}, {
					"mDataProp" : "email"
				}, {
					"mDataProp" : "title"
				}, {
					"mDataProp" : "position"
				}, {
					"mDataProp" : "joinTime"
				}, {
					"mDataProp" : "state"
				}, {
					"mDataProp" : "departureTime"
				}, {
					"mDataProp" : "id"
				} ],
				"aoColumnDefs" : [ {
					'aTargets' : [ '_all' ],
					'sClass' : 'center'
				}, {
					'aTargets' : [ 0 ],
					'bSortable' : true
				}, {
					'aTargets' : [ 1 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 2 ],
					'bSortable' : false,
					'fnRender' : function(oObj, sVal) {
						opstatus = sVal;
						if (sVal == 1) {
							return "<span class='label label-info'>男</span>";
						}
						return "<span class='label label-warning'>女</span>";
					}
				}, {
					'aTargets' : [ 3 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 4 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 5 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 6 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 7 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 8 ],
					'bSortable' : false,
					'fnRender' : function(oObj, sVal) {
						opstatus = sVal;
						if (sVal == 0) {
							return "<span class='label label-success'>在职</span>";
						}
						return "<span class='label label-danger'>离职</span>";
					}
				}, {
					'aTargets' : [ 9 ],
					'bSortable' : false
				}, {
					'aTargets' : [ 10 ],
					'bSortable' : false,
					'fnRender' : function(oObj, sVal) {
						var empnumber = oObj.aData.empnumber;
						if (empnumber == "admin") {
							return "";
						}
						var operate = '<div class="btn-group">';
						operate += '<a class="btn2 btn-info" href="' + $.ims.getContextPath() + '/sysconfig/resume/resume?id=' + oObj.aData.id + '"><i class="icon-zoom-in"></i> 详细信息</a>';
						operate += '<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>';
						operate += ' <ul class="dropdown-menu">';
						operate += '<li><a href="' + $.ims.getContextPath() + '/sysconfig/resume/edit?reid=' + oObj.aData.id + '"><i class="icon-edit"></i> 编辑</a></li>';
						operate += '</ul>';
						operate += '</div>';
						return operate;
					}
				} ]
			});
		} else {
			this.refreshresumeInfoDataTable();
		}
	},

	// 清空离职信息。
	celan : function() {
		$("#departureTime").val("");
		$("#leavingReasons").val("");
	},

	// 保存档案基础信息。
	createBaseInfo : function() {
		var flag = true;
		if (!this.validBase(flag)) {
			return; // 验证失败
		}

		var chinesename = $("#username").val();
		var sex = $("#sex").val();
		var empnumber = $("#empnumber").val();
		var language = $("#language").val().toString();
		var place = $("#place").val();
		var nation = $("#nation").val();
		var marrige = $("#marrige").val();
		var picture = $("picture").val();
		var politicsStatus = $("#politicsstatus").val();
		var domicilePlace = $("#domicileplace").val();
		var idNumber = $("#idnumber").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();

		var idAddress = $("#idaddress").val();
		var education = $("#education").val();
		var degree = $("#degree").val();
		var major = $("#major").val();
		var school = $("#school").val();
		var birthday = $("#birthday").val();
		var address = $("#address").val();
		var graduateTime = $("#graduatetime").val();
		var joinTime = $("#jointime").val();
		var conversionTime = $("#conversiontime").val();
		var dept = $("#dept").val();
		var title = $("#title").val();
		var post = $("#post").val();
		var position = $("#position").val();
		var grade = $("#grade").val();
		var contractStartDate = $("#contractstartdate").val();
		var contractEndDate = $("#contractenddate").val();
		var socialSecurityType = $("#socialmoney").val();
		var publicMoneyType = $("#publicmoney").val();
		var socialMoney = $("#socialmoney_defined").val();
		var publicMoney = $("#publicmoney_defined").val();

		var hobbies = $("#hobbies").val();
		var skill = $("#skill").val();
		var departureTime = $("#departureTime").val();
		var leavingReasons = $("#leavingReasons").val();

		if ((departureTime == "" && leavingReasons != "") || (leavingReasons == "" && departureTime != "")) {
			noty({
				"text" : "离职时间和原因必须一起填写！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}
		var usId = $("#us_id").val();
		var reId = $("#re_id").val();

		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/update",
			dataType : "json",
			data : {
				userId : usId,
				resumeId : reId,
				chinesename : chinesename,
				sex : sex,
				empnumber : empnumber,
				email : email,
				deptId : dept,

				language : language,

				place : place,
				picture : picture,
				nation : nation,
				marrige : marrige,
				politicsStatus : politicsStatus,
				domicilePlace : domicilePlace,
				idNumber : idNumber,
				mobile : mobile,
				idAddress : idAddress,
				education : education,
				degree : degree,
				major : major,
				school : school,
				birthday : birthday,
				address : address,
				graduateTime : graduateTime,
				joinTime : joinTime,
				conversionTime : conversionTime,
				title : title,
				post : post,
				position : position,
				grade : grade,
				contractStartDate : contractStartDate,
				contractEndDate : contractEndDate,

				socialMoney : socialMoney,
				socialSecurityType : socialSecurityType,
				publicMoneyType : publicMoneyType,
				publicMoney : publicMoney,

				hobbies : hobbies,
				skill : skill,
				departureTime : departureTime,
				leavingReasons : leavingReasons
			},
			success : function(json) {
				if (json) {
					noty({
						"text" : "修改档案基本信息成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "员工基本信息更新失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
				$.ims.userresume.refreshresumeInfoDataTable();
			}
		});
	},

	// 数据验证
	validBase : function(flag) {

		// 非空验证
		$(".required").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".required").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".required2").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).next().removeClass('valid-error');
			} else {
				$(this).next().addClass('valid-error');
				flag = false;
			}
		});

		$(".required2").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).next().removeClass('valid-error');
			} else {
				$(this).next().addClass('valid-error');
				flag = false;
			}
		});

		$("#dept").each(function() {
			var dept = $("#dept").val();
			if (dept == "" || dept == "0") {
				$("#dept").next().addClass('valid-error');
				flag = false;
			} else {
				$("#dept").next().removeClass('valid-error');
			}
		});

		$("#dept").change(function() {
			var dept = $("#dept").val();
			if (dept == "" || dept == "0") {
				$("#dept").next().addClass('valid-error');
				flag = false;
			} else {
				$("#dept").next().removeClass('valid-error');
			}
		});

		// 手机号码验证
		$(".nbmobile").each(function() {
			var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
			if (reg.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".nbmobile").change(function() {
			var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
			if (reg.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});
		// 数字验证
		$(".number").each(function() {
			var r = /^\+?[1-9][0-9]*$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".number").change(function() {
			var r = /^\+?[1-9][0-9]*$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".email").each(function() {
			var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if (!pattern.test($(this).val())) {
				$(this).addClass('valid-error');
				flag = false;
			} else {
				$(this).removeClass('valid-error');
			}
		});

		$(".email").change(function() {
			var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if (!pattern.test($(this).val())) {
				$(this).addClass('valid-error');
				flag = false;
			} else {
				$(this).removeClass('valid-error');
			}
		});

		$(".isIDCard2").each(function() {
			var r = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".isIDCard2").change(function() {
			var r = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".nbfloat").each(function() {
			var r = /^\d+(\.\d{1})?$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".nbfloat").change(function() {
			var r = /^\d+(\.\d{1})?$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	validfamily : function(flag) {

		// 非空验证
		$(".rdfamily").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".rdfamily").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		// 年龄验证
		$(".nbfamily").each(function() {
			var r = /^\d{1,2}$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".nbfamily").change(function() {
			var r = /^\d{1,2}$/;
			if (r.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		// 手机固话号码验证
		$(".mbfamily").each(function() {
			var mobile = /^1[3|5|8]\d{9}$/, phone = /^0\d{2,3}-?\d{7,8}$/;
			if (mobile.test($(this).val()) || phone.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".mbfamily").change(function() {
			var mobile = /^1[3|5|8]\d{9}$/, phone = /^0\d{2,3}-?\d{7,8}$/;
			if (mobile.test($(this).val()) || phone.test($(this).val())) {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	family_index : 1,

	/**
	 * 显示家庭信息。
	 */
	familyInfo : function(familyArray) {
		$("#family_dataTable_body").html('');
		this.family_index = familyArray.length;
		var tr = '';
		for (var i = 0; i < this.family_index; i++) {
			var f = familyArray[i];
			var f_array = f.split(',');
			tr += '<tr><td><input type="hidden" id="family_id" value="' + f_array[0] + '" /><button name="removefamily"  >－</button>' + '<td><input id="family_name' + i + '"  maxlength="12" value="'
					+ f_array[1] + '" class="span rdfamily" type="text"></td>' + '<td><input id="family_age' + i + '" maxlength="3" value="' + f_array[2] + '" class="span nbfamily" type="text"></td>'
					+ '<td><input id="family_title' + i + '"  maxlength="10" value="' + f_array[3] + '" class="span rdfamily" type="text"></td>' + '<td><input id="family_mobile' + i
					+ '" maxlength="12"  value="' + f_array[4] + '" class="span mbfamily" type="text"></td>' + '<td><input id="family_company' + i + '" maxlength="50"  value="' + f_array[5]
					+ '" class="span rdfamily" type="text"></td></tr>';
		}
		$("#family_dataTable_body").append(tr);
		this.family_index++;

		$("button[name=removefamily]").click(function() {
			if (window.confirm("你确定要删除这条吗？")) {
				var id = $(this).prev().val();
				$.ims.userresume.familyDelRow(id);
				$(this).parent().parent().remove();
			}
		});
	},

	/**
	 * 保存家庭成员信息。
	 */
	createFamily : function() {
		var flag = true;
		if (!this.validfamily(flag)) {
			return; // 验证失败
		}
		var resumeId = $("#re_id").val();
		var array = new Array();
		for (var i = 0; i < this.family_index; i++) {
			var element;
			var familyName = $("#family_name" + i).val();
			var familyAge = $("#family_age" + i).val();
			var familyTitle = $("#family_title" + i).val();
			var familyMobile = $("#family_mobile" + i).val();
			var familyCompany = $("#family_company" + i).val();
			if (familyName != null && familyAge != null && familyTitle != null && familyCompany != null && familyMobile != null) {

				array.push({
					familyName : familyName,
					familyAge : familyAge,
					familyTitle : familyTitle,
					familyMobile : familyMobile,
					familyCompany : familyCompany
				});
			}
		}

		if (array.length == 0) {
			noty({
				"text" : "没有添加数据！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}

		var data = JSON.stringify(array);
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/family/" + resumeId,
			dataType : "json",
			contentType : "application/json",
			data : data,
			success : function(json) {
				if (json == 1) {
					noty({
						"text" : "保存家庭信息成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					this.family_index = 1;
					// location.reload();
				} else {
					noty({
						"text" : "保存家庭信息失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 删除指定家庭信息。
	 */
	familyDelRow : function(id) {
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/family/del/" + id,
			data : {
				id : id
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "删除记录成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "不能删除！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 家庭成员新增一空行。
	 */
	familyAddBtnClick : function() {
		var tr = '<tr><td><button id="family_id' + this.family_index + '" name="removefy" >－</button>' + '<td><input id="family_name' + this.family_index
				+ '" " maxlength="12" class="span rdfamily" type="text"></td> class="span rdfamily" type="text"></td>' + '<td><input id="family_age' + this.family_index
				+ '" maxlength="3" class="span nbfamily" type="text"></td>' + '<td><input id="family_title' + this.family_index + '" maxlength="10" class="span rdfamily" type="text"></td>'
				+ '<td><input id="family_mobile' + this.family_index + '" maxlength="12" class="span mbfamily" type="text"></td>' + '<td><input id="family_company' + this.family_index
				+ '" maxlength="50" class="span rdfamily" type="text"></td></tr>';

		$("#family_dataTable_body").append(tr);

		this.family_index++;

		$("button[name=removefy]").click(function() {
			$(this).parent().parent().remove();
		});
	},

	dateClass : function() {
		$('.date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			format : 'yyyy-mm-dd',
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
	},

	/**
	 * 教育信息验证。
	 */
	validEdu : function(flag) {

		// 非空验证
		$(".rdedu").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".rdedu").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	edu_index : 1,

	/**
	 * 教育信息增加一空行。
	 */
	eduAddBtnClick : function() {
		var tr = '<tr><td><button id="edu_id' + this.edu_index + '" name="removeedu" >－</button>' + '<td><input id="edu_starttime' + this.edu_index
				+ '" style="width: 130px;" class="date rdedu" readonly="readonly"></td>' + '<td><input id="edu_endtime' + this.edu_index
				+ '" style="width: 130px;" class="date rdedu" readonly="readonly"></td>' + '<td><input id="edu_school' + this.edu_index + '" maxlength="32" class="span rdedu" type="text"></td>'
				+ '<td><input id="edu_major' + this.edu_index + '" maxlength="32" class="span rdedu" type="text"></td>' + '<td><input id="edu_certificate' + this.edu_index
				+ '" maxlength="32" class="span rdedu" type="text"></td>' + '<td><input id="edu_way' + this.edu_index + '" maxlength="12" class="span rdedu" type="text"></td></tr>';

		$("#edu_dataTable_body").append(tr);

		this.edu_index++;

		$("button[name=removeedu]").click(function() {
			$(this).parent().parent().remove();
		});

		this.dateClass();
	},

	/**
	 * 教育信息详情。
	 */
	eduInfo : function(eduArray) {
		$("#edu_dataTable_body").html('');
		this.edu_index = eduArray.length;
		var tr = '';
		for (var i = 0; i < this.edu_index; i++) {
			var f = eduArray[i];
			var f_array = f.split(',');
			tr += '<tr><td><input type="hidden" id="edu_id" value="' + f_array[0] + '" /><button name="removedu"  >－</button>' + '<td><input id="edu_starttime' + i + '" style="width: 130px;" value="'
					+ f_array[1] + '" class="date rdedu" readonly="readonly"></td>' + '<td><input id="edu_endtime' + i + '" style="width: 130px;" value="' + f_array[2]
					+ '" class="date rdedu" readonly="readonly"></td>' + '<td><input id="edu_school' + i + '" value="' + f_array[3] + '" maxlength="32" class="span rdedu" type="text"></td>'
					+ '<td><input id="edu_major' + i + '" value="' + f_array[4] + '" maxlength="32" class="span rdedu" type="text"></td>' + '<td><input id="edu_certificate' + i + '" value="'
					+ f_array[5] + '" maxlength="32" class="span rdedu" type="text"></td>' + '<td><input id="edu_way' + i + '" value="' + f_array[6]
					+ '" maxlength="12" class="span rdedu" type="text"></td></tr>';
		}
		$("#edu_dataTable_body").append(tr);
		this.edu_index++;

		$("button[name=removedu]").click(function() {
			if (window.confirm("你确定要删除这条吗？")) {
				var id = $(this).prev().val();
				$.ims.userresume.eduDelRow(id);
				$(this).parent().parent().remove();
			}
		});

		this.dateClass();
	},

	/**
	 * 保存教育信息。
	 */
	createEdu : function() {
		var flag = true;
		if (!this.validEdu(flag)) {
			return; // 验证失败
		}
		var resumeId = $("#re_id").val();
		var array = new Array();
		for (var i = 0; i < this.edu_index; i++) {
			var element;
			var eduStartTime = $("#edu_starttime" + i).val();
			var eduEndTime = $("#edu_endtime" + i).val();
			var eduSchool = $("#edu_school" + i).val();
			var eduMajor = $("#edu_major" + i).val();
			var eduCertificate = $("#edu_certificate" + i).val();
			var eduWay = $("#edu_way" + i).val();

			if (eduStartTime != null && eduEndTime != null && eduSchool != null && eduMajor != null && eduCertificate != null && eduWay != null) {

				array.push({
					eduStartTime : eduStartTime,
					eduEndTime : eduEndTime,
					eduSchool : eduSchool,
					eduMajor : eduMajor,
					eduCertificate : eduCertificate,
					eduWay : eduWay
				});
			}
		}
		if (array.length == 0) {
			noty({
				"text" : "没有添加数据！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}

		var data = JSON.stringify(array);
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/edu/" + resumeId,
			dataType : "json",
			contentType : "application/json",
			data : data,
			success : function(json) {
				if (json == 1) {
					// location.reload();
					noty({
						"text" : "保存教育息成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					this.edu_index = 1;

				} else {
					noty({
						"text" : "保存教育信息失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 删除指定教育信息。
	 */
	eduDelRow : function(id) {
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/edu/del/" + id,
			data : {
				id : id
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "删除记录成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "不能删除！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 公司外经历验证。
	 */
	validOutCompany : function(flag) {

		// 非空验证
		$(".rdout").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".rdout").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	outCompany_index : 1,

	/**
	 * 公司外经历增加一空行。
	 */
	outCompanyAddBtnClick : function() {
		var tr = '<tr><td><button id="outCompany_id' + this.outCompany_index + '" name="removeout" >－</button>' + '<td><input id="outCompany_starttime' + this.outCompany_index
				+ '" style="width: 130px;" class="date rdout" readonly="readonly"></td>' + '<td><input id="outCompany_endtime' + this.outCompany_index
				+ '" style="width: 130px;" class="date rdout" readonly="readonly"></td>' + '<td><input id="outCompany_company' + this.outCompany_index
				+ '" maxlength="32" class="span rdout" type="text"></td>' + '<td><input id="outCompany_dept' + this.outCompany_index + '" maxlength="12" class="span rdout" type="text"></td>'
				+ '<td><input id="outCompany_position' + this.outCompany_index + '" maxlength="12" class="span rdout" type="text"></td>' + '<td><input id="outCompany_perience' + this.outCompany_index
				+ '" maxlength="32" class="span " type="text"></td>' + '<td><input id="outCompany_leavingreasons' + this.outCompany_index
				+ '" maxlength="50" class="span rdout" type="text"></td></tr>';

		$("#outCompany_dataTable_body").append(tr);

		this.outCompany_index++;

		$("button[name=removeout]").click(function() {
			$(this).parent().parent().remove();
		});

		this.dateClass();
	},

	/**
	 * 公司外经历详情。
	 */
	outCompanyInfo : function(outCompanyArray) {
		$("#outCompany_dataTable_body").html('');
		this.outCompany_index = outCompanyArray.length;
		var tr = '';
		for (var i = 0; i < this.outCompany_index; i++) {
			var f = outCompanyArray[i];
			var f_array = f.split(',');
			tr += '<tr><td><input type="hidden" id="outCompany_id" value="' + f_array[0] + '" /><button name="removeot"  >－</button>' + '<td><input id="outCompany_starttime' + i
					+ '" style="width: 130px;" value="' + f_array[1] + '" class="date rdout" readonly="readonly"></td>' + '<td><input id="outCompany_endtime' + i + '" style="width: 130px;" value="'
					+ f_array[2] + '" class="date rdout" readonly="readonly"></td>' + '<td><input id="outCompany_company' + i + '" value="' + f_array[3]
					+ '"  maxlength="32" class="span rdout" type="text"></td>' + '<td><input id="outCompany_dept' + i + '" value="' + f_array[4]
					+ '" maxlength="12" class="span rdout" type="text"></td>' + '<td><input id="outCompany_position' + i + '" value="' + f_array[5]
					+ '" maxlength="12" class="span rdout" type="text"></td>' + '<td><input id="outCompany_perience' + i + '" value="' + f_array[6]
					+ '" maxlength="32" class="span " type="text"></td>' + '<td><input id="outCompany_leavingreasons' + i + '" value="' + f_array[7]
					+ '" maxlength="50" class="span rdout" type="text"></td></tr>';
		}
		$("#outCompany_dataTable_body").append(tr);
		this.outCompany_index++;

		$("button[name=removeot]").click(function() {
			if (window.confirm("你确定要删除这条吗？")) {
				var id = $(this).prev().val();
				$.ims.userresume.outCompanyDelRow(id);
				$(this).parent().parent().remove();
			}
		});

		this.dateClass();
	},

	/**
	 * 保存公司外经历。
	 */
	createOutCompany : function() {
		var flag = true;
		if (!this.validOutCompany(flag)) {
			return; // 验证失败
		}
		var resumeId = $("#re_id").val();
		var array = new Array();
		for (var i = 0; i < this.outCompany_index; i++) {
			var element;
			var beforeStartTime = $("#outCompany_starttime" + i).val();
			var beforeEndTime = $("#outCompany_endtime" + i).val();
			var beforeCompany = $("#outCompany_company" + i).val();
			var beforeDept = $("#outCompany_dept" + i).val();
			var beforePosition = $("#outCompany_position" + i).val();
			var beforePerience = $("#outCompany_perience" + i).val();
			var beforeLeavingReasons = $("#outCompany_leavingreasons" + i).val();

			if (beforeStartTime != null && beforeEndTime != null && beforeCompany != null && beforeDept != null && beforePosition != null && beforePerience != null && beforeLeavingReasons != null) {

				array.push({
					beforeStartTime : beforeStartTime,
					beforeEndTime : beforeEndTime,
					beforeCompany : beforeCompany,
					beforeDept : beforeDept,
					beforePosition : beforePosition,
					beforePerience : beforePerience,
					beforeLeavingReasons : beforeLeavingReasons
				});
			}
		}
		if (array.length == 0) {
			noty({
				"text" : "没有添加数据！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}

		var data = JSON.stringify(array);
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/outcompany/" + resumeId,
			dataType : "json",
			contentType : "application/json",
			data : data,
			success : function(json) {
				if (json == 1) {
					noty({
						"text" : "保存前工作经历成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					this.outCompany_index = 1;
				} else {
					noty({
						"text" : "保存前工作经历失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 删除指定公司外经历。
	 */
	outCompanyDelRow : function(id) {
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/outcompany/del/" + id,
			data : {
				id : id
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "删除记录成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "不能删除！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 现任公司经历验证。
	 */
	validInCompany : function(flag) {

		// 非空验证
		$(".rdin").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".rdin").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	inCompany_index : 1,
	/**
	 * 现在公司经历增加空行。
	 */

	inCompanyAddBtnClick : function() {
		var tr = '<tr><td><button id="inCompany_id' + this.inCompany_index + '" name="removein" >－</button>' + '<td><input id="inCompany_starttime' + this.inCompany_index
				+ '" style="width: 130px;" class="date rdin" readonly="readonly"></td>' + '<td><input id="inCompany_endtime' + this.inCompany_index
				+ '" style="width: 130px;" class="date rdin" readonly="readonly"></td>' + '<td><input id="inCompany_dept' + this.inCompany_index
				+ '"  maxlength="12" class="span rdin" type="text"></td>' + '<td><input id="inCompany_post' + this.inCompany_index + '" maxlength="12" class="span rdin" type="text"></td>'
				+ '<td><input id="inCompany_perience' + this.inCompany_index + '" maxlength="32" class="span rdin" type="text"></td>' + '<td><input id="inCompany_alterreasons' + this.inCompany_index
				+ '" maxlength="52" class="span rdin" type="text"></td></tr>';

		$("#inCompany_dataTable_body").append(tr);
		this.inCompany_index++;

		$("button[name=removein]").click(function() {
			$(this).parent().parent().remove();
		});

		this.dateClass();
	},

	/**
	 * 现在公司经历详情。
	 */
	inCompanyInfo : function(inCompanyArray) {
		$("#inCompany_dataTable_body").html('');
		this.inCompany_index = inCompanyArray.length;
		var tr = '';
		for (var i = 0; i < this.inCompany_index; i++) {
			var f = inCompanyArray[i];
			var f_array = f.split(',');
			tr += '<tr><td><input type="hidden" id="inCompany_id" value="' + f_array[0] + '" /><button name="removei"  >－</button>' + '<td><input id="inCompany_starttime' + i
					+ '" style="width: 130px;" value="' + f_array[1] + '" class="date rdin" readonly="readonly"></td>' + '<td><input id="inCompany_endtime' + i + '" style="width: 130px;" value="'
					+ f_array[2] + '" class="date rdin" readonly="readonly"></td>' + '<td><input id="inCompany_dept' + i + '" value="' + f_array[3]
					+ '" maxlength="12" class="span rdin" type="text"></td>' + '<td><input id="inCompany_post' + i + '" value="' + f_array[4] + '" maxlength="12" class="span rdin" type="text"></td>'
					+ '<td><input id="inCompany_perience' + i + '" value="' + f_array[5] + '" maxlength="32" class="span rdin" type="text"></td>' + '<td><input id="inCompany_alterreasons' + i
					+ '" value="' + f_array[6] + '" maxlength="52" class="span rdin" type="text"></td></tr>';

		}
		$("#inCompany_dataTable_body").append(tr);

		this.inCompany_index++;

		$("button[name=removei]").click(function() {
			if (window.confirm("你确定要删除这条吗？")) {
				var id = $(this).prev().val();
				$.ims.userresume.inCompanyDelRow(id);
				$(this).parent().parent().remove();
			}
		});

		this.dateClass();
	},

	/**
	 * 保存现在公司经历。
	 */
	createInCompany : function() {
		var flag = true;
		if (!this.validInCompany(flag)) {
			return; // 验证失败
		}
		var resumeId = $("#re_id").val();
		var array = new Array();
		for (var i = 0; i < this.inCompany_index; i++) {
			var element;
			var nowStartTime = $("#inCompany_starttime" + i).val();
			var nowEndTime = $("#inCompany_endtime" + i).val();
			var nowDept = $("#inCompany_dept" + i).val();
			var nowPost = $("#inCompany_post" + i).val();
			var nowPerience = $("#inCompany_perience" + i).val();
			var nowAlterReasons = $("#inCompany_alterreasons" + i).val();

			if (nowStartTime != null && nowEndTime != null && nowDept != null && nowPost != null && nowPerience != null && nowAlterReasons != null) {

				array.push({
					nowStartTime : nowStartTime,
					nowEndTime : nowEndTime,
					nowDept : nowDept,
					nowPost : nowPost,
					nowPerience : nowPerience,
					nowAlterReasons : nowAlterReasons
				});
			}
		}
		if (array.length == 0) {
			noty({
				"text" : "没有添加数据！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}

		var data = JSON.stringify(array);
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/incompany/" + resumeId,
			dataType : "json",
			contentType : "application/json",
			data : data,
			success : function(json) {
				if (json == 1) {
					noty({
						"text" : "保存现公司经历成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					this.inCompany_index = 1;
				} else {
					noty({
						"text" : "保存现公司信息失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 删除指定现公司经历。
	 */
	inCompanyDelRow : function(id) {
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/incompany/del/" + id,
			data : {
				id : id
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "删除记录成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "不能删除！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 奖惩信息验证。
	 */
	validReward : function(flag) {

		// 非空验证
		$(".rdreward").each(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		$(".rdreward").change(function() {
			if ($.trim($(this).val()) != "") {
				$(this).removeClass('valid-error');
			} else {
				$(this).addClass('valid-error');
				flag = false;
			}
		});

		return flag;
	},

	reward_index : 1,

	/**
	 * 奖惩情况增加空行。
	 */
	rewardAddBtnClick : function() {
		var tr = '<tr><td><button id="reward_id' + this.reward_index + '" name="removereward" >－</button>' + '<td><input id="reward_date' + this.reward_index
				+ '" style="width: 130px;" class="date rdreward" readonly="readonly"></td>' + '<td><input id="reward_type' + this.reward_index
				+ '" maxlength="12" class="span rdreward" type="text"></td>' + '<td><input id="reward_reason' + this.reward_index + '" maxlength="32" class="span rdreward" type="text"></td>'
				+ '<td><input id="reward_content' + this.reward_index + '" maxlength="32" class="span rdreward" type="text"></td></tr>';

		$("#reward_dataTable_body").append(tr);
		this.reward_index++;

		$("button[name=removereward]").click(function() {
			$(this).parent().parent().remove();
		});

		this.dateClass();
	},

	/**
	 * 奖惩情况详情。
	 */
	rewardInfo : function(rewardArray) {
		$("#reward_dataTable_body").html('');
		this.reward_index = rewardArray.length;
		var tr = '';
		for (var i = 0; i < this.reward_index; i++) {
			var f = rewardArray[i];
			var f_array = f.split(',');
			tr += '<tr><td><input type="hidden" id="reward_id" value="' + f_array[0] + '" /><button name="removerd"  >－</button>' + '<td><input id="reward_date' + i
					+ '" style="width: 130px;" class="date rdreward" value="' + f_array[1] + '" readonly="readonly"></td>' + '<td><input id="reward_type' + i + '" value="' + f_array[2]
					+ '" maxlength="12" class="span rdreward" type="text"></td>' + '<td><input id="reward_reason' + i + '" value="' + f_array[3]
					+ '" maxlength="32" class="span rdreward" type="text"></td>' + '<td><input id="reward_content' + i + '" value="' + f_array[4]
					+ '" maxlength="32" class="span rdreward" type="text"></td></tr>';
		}
		$("#reward_dataTable_body").append(tr);
		this.reward_index++;

		$("button[name=removerd]").click(function() {
			if (window.confirm("你确定要删除这条吗？")) {
				var id = $(this).prev().val();
				$.ims.userresume.rewardDelRow(id);
				$(this).parent().parent().remove();
			}
		});

		this.dateClass();
	},

	/**
	 * 保存奖惩信息。
	 */
	createReward : function() {
		var flag = true;
		if (!this.validReward(flag)) {
			return; // 验证失败
		}
		var resumeId = $("#re_id").val();
		var array = new Array();
		for (var i = 0; i < this.reward_index; i++) {
			var element;
			var awardDate = $("#reward_date" + i).val();
			var awardType = $("#reward_type" + i).val();
			var awardReason = $("#reward_reason" + i).val();
			var awardContent = $("#reward_content" + i).val();

			if (awardDate != null && awardType != null && awardReason != null && awardContent != null) {
				array.push({
					awardDate : awardDate,
					awardType : awardType,
					awardReason : awardReason,
					awardContent : awardContent
				});
			}
		}
		if (array.length == 0) {
			noty({
				"text" : "没有添加数据！",
				"layout" : "top",
				"type" : "warning",
				"timeout" : "2000"
			});
			return;
		}

		var data = JSON.stringify(array);
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/reward/" + resumeId,
			dataType : "json",
			contentType : "application/json",
			data : data,
			success : function(json) {
				if (json == 1) {
					noty({
						"text" : "保存奖惩信息成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
					this.reward_index = 1;
				} else {
					noty({
						"text" : "保存奖惩信息失败！",
						"layout" : "top",
						"type" : "error",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 删除指定奖惩信息。
	 */
	rewardDelRow : function(id) {
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/incompany/del/" + id,
			data : {
				id : id
			},
			dataType : "json",
			success : function(json) {
				if (json) {
					noty({
						"text" : "删除记录成功！",
						"layout" : "top",
						"type" : "success",
						"timeout" : "2000"
					});
				} else {
					noty({
						"text" : "不能删除！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
			}
		});
	},

	/**
	 * 导出基础信息Excel。
	 */
	exportResumeReport : function() {
		window.open($.ims.getContextPath() + "/sysconfig/resume/export");
	},

	/** 显示离职modal * */
	showDimissionModal : function() {
		$("#dimission_modal_header_label").text("员工离职");
		$("#dimission_modal").modal('show');
	},

	/** 保存离职信息 * */
	saveDismission : function() {
		var resume = $("#hf_resume_id").val();
		var type = $("#dismission_type").val();
		var applydate = $("#dismission_applydate").val();
		var plandate = $("#dismission_plandate").val();
		var actualdate = $("#dismission_actualdate").val();
		var blacklist = $('#dismission_blacklist').prop('checked') == true ? 1 : 0;
		var reason = $("#dismission_reason").val();
		var remarks = $("#dismission_remark").val();
		$.ajax({
			type : "post",
			url : $.ims.getContextPath() + "/sysconfig/resume/dimission/save",
			data : {
				resumeId : resume,
				type : type,
				applydate : applydate,
				plandate : plandate,
				actualdate : actualdate,
				blacklist : blacklist,
				reason : reason,
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
				} else {
					noty({
						"text" : "操作失败，请联系管理员！",
						"layout" : "top",
						"type" : "warning",
						"timeout" : "2000"
					});
				}
				$.ims.userresume.refreshDimissionDataTable();
				$("#departureTime").val(actualdate);
				$("#leavingReasons").val(reason);
			}
		});
	},
	
	backIndexPage : function(){
		
	},
}
