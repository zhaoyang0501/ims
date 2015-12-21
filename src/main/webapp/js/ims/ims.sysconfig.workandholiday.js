jQuery.ims.workandholiday = {
		
		/**
		 * 保存节假日设置
		 */
		save : function(){
			var foreworktime = $("#foreworktime").val();
			var foreresttime = $("#foreresttime").val();
			var afterworktime = $("#afterworktime").val();
			var afterresttime = $("#afterresttime").val();
			var date = $("#setting_date").val();
			var type = $("#type").val();
			var remark = $("#remark").val();
			$.ajax({
    			type : "post",
    			url : $.ims.getContextPath() + "/sysconfig/workandholiday/save",
    			data : {
    				type : type,
    				date : date,
    				foreworktime : foreworktime,
    				foreresttime : foreresttime,
    				afterworktime : afterworktime,
    				afterresttime : afterresttime,
    				remark : remark
    			},
    			dataType : "json",
    			success : function(json) {
    				var success = json.success;
    				var msg = json.msg;
    				if(success == 1){
    					$("#holiday_modal").modal('hide');
    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
    					$('#calendar').fullCalendar('refetchEvents');
    				}else{
    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
    				}
    			}
    		});
		},
		
		/**
		 * 取消节假日设置
		 */
		cancle : function(){
			var msg = "是否确认取消当天节假日设置？";
			var date = $("#setting_date").val();
			if(window.confirm("你确定要删除这条吗？")){
				$.ajax({
	    			type : "post",
	    			url : $.ims.getContextPath() + "/sysconfig/workandholiday/cancle/" + date,
	    			dataType : "json",
	    			success : function(json) {
	    				var success = json.success;
	    				var msg = json.msg;
	    				if(success == 1){
	    					$("#holiday_modal").modal('hide');
	    					noty({"text":""+ msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$('#calendar').fullCalendar('refetchEvents');
	    				}else{
	    					noty({"text":""+ msg +"","layout":"top","type":"warning","timeout":"2000"});
	    				}
	    			}
	    		});
			}
		},
		
		queryTimetable : function(date){
			$.ajax({
    			type : "post",
    			url : $.ims.getContextPath() + "/sysconfig/workandholiday/query/" + date,
    			dataType : "json",
    			success : function(json) {
    				if(json){
    					$.ims.common.setchosenvalue("type",json.type);
    					$("#foreworktime").val(json.foreworktime);
    					$("#foreresttime").val(json.foreresttime);
    					$("#afterworktime").val(json.afterworktime);
    					$("#afterresttime").val(json.afterresttime);
    		        	if(json.type == 0){
    		        		$("#foreworktime").val("");
    		        		$("#foreresttime").val("");
    		        		$("#afterworktime").val("");
    		        		$("#afterresttime").val("");
    		        		$("#div_forework").hide();
    		        		$("#div_forerest").hide();
    		        		$("#div_afterwork").hide();
    		        		$("#div_afterrest").hide();
    		        	}else{
    		        		$("#div_forework").show();
    		        		$("#div_forerest").show();
    		        		$("#div_afterwork").show();
    		        		$("#div_afterrest").show();
    		        	}
    				}
    				$("#holiday_modal").modal('show');
    			}
    		});
		}
}