jQuery.ims.common = {
		
		/**
		 * 查找所有部门
		 */
		findAllDept : function(callback, dept){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/dept/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+dept+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+dept+"").append("<option value=\"" + json[i].id+ "\">" + json[i].name + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		/**
		 * 查找权限部门
		 */
		findPermissionDept : function(callback, dept){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/dept/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+dept+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+dept+"").append("<option value=\"" + json[i].permission+ "\">" + json[i].name + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		/**
		 * 查找所有用户
		 */
		findAllUser : function(callback, user){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/user/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+user+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+user+"").append("<option value=\"" + json[i].id+ "\">" + json[i].chinesename + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		/**
		 * 查找权限范围内的用户
		 */
		findScopeUser : function(callback, user){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/userScope/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+user+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+user+"").append("<option value=\"" + json[i].id+ "\">" + json[i].chinesename + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		
		/**
		 * 查找所有职位。
		 */
		findAllPosition: function(callback, position){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/position/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+position+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i in json){
						$("#"+position+"").append("<option value=\"" + i + "\">" + json[i] + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 查找固定资产
		 */
		findAllMateriel : function(callback, materiel){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/common/materiel/list",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+materiel+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+materiel+"").append("<option value=\"" + json[i].id+ "\">" + json[i].materielName + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 所有培训ku
		 */
		findAllTrainingBase : function(callback){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/training/trainingbase/all",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#trainingName").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#trainingName").append("<option value=\"" + json[i].id+ "\">" + json[i].trainingName + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 所有考试
		 */
		findAllExam : function(callback, object){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/exam/paper/all",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+object+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+object+"").append("<option value=\"" + json[i].id+ "\">" + json[i].subject + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 性别
		 */
		sexChosen : function(sex){
			$("#"+sex+"").append("<option selected='selected' value=\"0\"></option>");
			$("#"+sex+"").append("<option value=\"1\">男</option>");
			$("#"+sex+"").append("<option value=\"0\">女</option>");
			$("#"+sex+"").chosen({
				allow_single_deselect : true
			});
		},
		
		/**
		 * 婚否。
		 */
		marrigeChosen : function(marrige){
			$("#"+marrige+"").append("<option selected='selected' value=\"0\"></option>");
			$("#"+marrige+"").append("<option value=\"1\">已婚</option>");
			$("#"+marrige+"").append("<option value=\"0\">未婚</option>");
			$("#"+marrige+"").chosen({
				allow_single_deselect : true
			});
		},
		
		/**
		 * 培训类别
		 */
		trainingTypeChosen : function(type){
			$("#"+type+"").append("<option value=\"" + "0" + "\">" + "</option>");
			$("#"+type+"").append("<option value=\"1\">入职/转岗培训</option>");
			$("#"+type+"").append("<option value=\"2\">企业文化制度培训</option>");
			$("#"+type+"").append("<option value=\"3\">体系培训</option>");
			$("#"+type+"").append("<option value=\"4\">管理者培训</option>");
			$("#"+type+"").append("<option value=\"5\">任职能力/岗位技能培训</option>");
			$("#"+type+"").append("<option value=\"6\">专业提升培训</option>");
			$("#"+type+"").append("<option value=\"7\">战略培训</option>");
			$("#"+type+"").append("<option value=\"8\">客户要求培训</option>");
			$("#"+type+"").chosen({
				allow_single_deselect : true
			});
		},
		
		/**
		 * 培训方式
		 */
		trainingMethodChosen : function(method){
			$("#"+method+"").append("<option value=\"" + "0" + "\">" + "</option>");
			$("#"+method+"").append("<option value=\"1\">内训</option>");
			$("#"+method+"").append("<option value=\"2\">外训</option>");
			$("#"+method+"").chosen({
				allow_single_deselect : true
			});
		},
		
		/**
		 * 用户状态
		 */
		userStateChosen : function(state){
			$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
			$("#"+state+"").append("<option value=\"0\">正常</option>");
			$("#"+state+"").append("<option value=\"1\">冻结</option>");
			$("#"+state+"").chosen({
				allow_single_deselect : true
			});
		},
		
		
		/**
		 * 档案用户状态
		 */
		resumeStateChosen : function(state){
			$("#"+state+"").append("<option selected='selected' value=\"\"></option>");
			$("#"+state+"").append("<option value=\"0\">在职</option>");
			$("#"+state+"").append("<option value=\"1\">离职</option>");
			$("#"+state+"").chosen({
				allow_single_deselect : true
			});
		},

		/**
		 * 所有培训
		 */
		findAllTrainingPlan : function(callback,training_plan){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/training/trainingplan/all",
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+training_plan+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+training_plan+"").append("<option value=\"" + json[i].id+ "\">" + json[i].trainingBase.trainingName + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 用餐类别
		 */
		allDinnerType : function(type){
			$("#"+type+"").append("<option value=\"1\">午餐</option>");
			$("#"+type+"").append("<option value=\"2\">晚餐</option>");
			$("#"+type+"").chosen({
				allow_single_deselect : true
			});
		},
		
		/**
		 * 所有加班报告
		 */
		findAllOvertime : function(callback,user,date){
			$.ajax({
				type : "get",
				url : $.ims.getContextPath() + "/dailyReport/dailyReport/overtime/date/"+date,
				dataType : "json",
				async : false,
				success : function(json) {
					$("#"+user+"").append("<option value=\"" + "0" + "\">" + "</option>");
					for (var i = 0; i < json.length; i++) {
						$("#"+user+"").append("<option value=\"" + json[i].user.id+ "\">" + json[i].user.chinesename + "</option>");
					}
					if($.isFunction(callback)) callback();
				}
			});
		},
		
		/**
		 * 动态设置chosen值
		 */
		setchosenvalue : function(selectObj, value){
			var selectObj = $("#"+selectObj+"");
	        selectObj.parent().children().remove('div');
	        selectObj.removeClass();
	        selectObj.val(value);
	        selectObj.addClass("chzn-select");
	        selectObj.chosen({
				placeholder_text:" ",
		        allow_single_deselect : true,
		        no_results_text : "没有找到.",
		        disable_search_threshold : 5,
		        enable_split_word_search : true,
		        search_contains : true
	       });
		},
		/**
		 * 动态设置chosen值
		 */
		setchosenvalue2 : function(selectObj, value){
			var selectObj = $("#"+selectObj+"");
	        selectObj.parent().children().remove('div');
	        selectObj.removeClass();
	        selectObj.val(value);
	        selectObj.addClass("chzn-select");
	        selectObj.chosen({
				placeholder_text:" ",
		        allow_single_deselect : false,
		        no_results_text : "没有找到.",
		        disable_search_threshold : 5,
		        enable_split_word_search : true,
		        search_contains : true
	       });
		},
		/***
		 * 列合并
		 */
		tableRowspan :function(table_id, table_colnum) {
            table_firsttd = "";
            table_currenttd = "";
            table_SpanNum = 0;
            colnum_Obj = $(table_id + " tr td:nth-child(" + table_colnum + ")");
            colnum_Obj.each(function (i) {
                if (i == 0) {
                    table_firsttd = $(this);
                    table_SpanNum = 1;
 
                } else {
                    table_currenttd = $(this);
                    if (table_firsttd.text() == table_currenttd.text()) {
                        table_SpanNum++;
                        table_currenttd.hide(); 
                        table_firsttd.attr("rowSpan", table_SpanNum);
                    } else {
                        table_firsttd = $(this);
                        table_SpanNum = 1;
                    }
                }
            });
        }
}