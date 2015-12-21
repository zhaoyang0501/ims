<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	<meta charset="utf-8">
	<title>航盛上海技术中心综合管理系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Admin Panel Template">
	<meta name="author" content="caowei panzhaoyang">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<!--fav and touch icons -->
	<link rel="shortcut icon" href="ico/favicon.ico">
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/ico/apple-touch-icon-57-precomposed.png">
	
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
	<!-- 图标字体http://fontawesome.io/  -->
	<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
	<!--  falgun框架样式 -->
	<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/theme-default.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	
	<!--[if IE 7]>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome-ie7.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ie/ie7.css" />
	<![endif]-->
	<!--[if IE 8]>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ie/ie8.css" />
	<![endif]-->
	<!--[if IE 9]>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ie/ie9.css" />
	<![endif]-->
	
	<script src="${pageContext.request.contextPath}/js/falgun/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/accordion.nav.js"></script><!-- 菜单下拉  -->
	<!-- DataTables http://www.datatables.net/  -->
	<script src="${pageContext.request.contextPath}/js/falgun/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/dataTables.bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/TableTools.min.js"  charset="utf-8" ></script>
	<!-- 浏览器兼容性 -->
	<script src="${pageContext.request.contextPath}/js/falgun/excanvas.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/modernizr-transitions.js"></script>
	<script src="${pageContext.request.contextPath}/js/falgun/respond.min.js"></script>
	<!-- falgun -->
	<script src="${pageContext.request.contextPath}/js/falgun/custom.js"></script>
	<!-- 消息提示 -->
	<script src="${pageContext.request.contextPath}/js/noty/packaged/jquery.noty.packaged.min.js"></script>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/bootstrap-tooltip.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ims/ims.js"></script>
	<!-- 弹框消息 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/falgun/jquery.gritter.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.gritter.css" rel="stylesheet">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pin.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/sockjs-1.0.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/moment.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$.ims.setContextPath('${pageContext.request.contextPath}');
		/**消息提醒部分*/
		$(".pinned").pin({containerSelector: ".layout"});
		$(".ajax-dropdown").mouseleave(function(){ $(this).css("display","none");});
		$(".btn-notification").click(function(){
			var  b=$(this);
			b.next(".ajax-dropdown").is(":visible")?(b.next(".ajax-dropdown").fadeOut(150),b.removeClass("active")):(b.next(".ajax-dropdown").fadeIn(150),b.addClass("active"));
			$(".notification-body").css("display","none");
			$("#unread li").size()==0?$(".tmsg_error").css("display","block"):$("#unread").css("display","block");
		});
		
		$("input[name='activity']").click(function(){
			$(".notification-body").css("display","none");
			$(this).parents('.btn-group-justified').find(".btn-default").removeClass("active");
			if($("#"+$("input[name='activity']:checked").val()+" li").size()==0)
				$(".tmsg_error").css("display","block");
			else
				$("#"+$("input[name='activity']:checked").val()).css("display","block");
			
		});
	  var websocket;
         if ('WebSocket' in window) {
             websocket = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/noticeWebSocketServer");
         } else if ('MozWebSocket' in window) {
             websocket = new MozWebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/noticeWebSocketServer");
         } else {
             websocket = new SockJS("http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/sockjs/noticeWebSocketServer");
         }
         websocket.onopen = function (evnt) {
         };
         websocket.onmessage = function (evnt) {
       	 $("#msg_update_time").html(moment().format("YYYY-MM-DD hh:ss"));
       	 $("#unread").empty();
       	 $("#read").empty();
            var jsonobj=$.parseJSON(evnt.data);
            $("#unread_num").html(jsonobj.unread.length);
            $("#readed_num").html(jsonobj.readed.length);
            $(".notify-tip").html(jsonobj.unread.length);
            $.each(jsonobj.unread, function(i){
           	var li="<li> "+
    				"<span> "+
					"<a href=\"javascript:void(0);\" class=\"msg\"> "+
					"	<img src=\"images/4.png\" alt=\"\" class=\"air air-top-left margin-top-5\" width=\"40\" height=\"40\"> "+
					"	<span class=\"from\">"+jsonobj.unread[i].title+" <i class=\"icon-paperclip\"></i></span> "+
					"	<time>"+moment(jsonobj.unread[i].createDate,"YYYY-MM-DD hh:mm").startOf('day').fromNow()+"</time> "+
					"	<span class=\"msg-body\">"+jsonobj.unread[i].context+" </span> "+
					"</a>"+
					"</span>"+
					" </li>";
					$(li).prependTo("#unread");
           	});
            $.each(jsonobj.readed, function(i){
            	var li="<li> "+
	    				"<span> "+
						"<a href=\"javascript:void(0);\" class=\"msg\"> "+
						"	<img src=\"http://127.0.0.1:8887/smartAjax/img/avatars/4.png\" alt=\"\" class=\"air air-top-left margin-top-5\" width=\"40\" height=\"40\"> "+
						"	<span class=\"from\">"+jsonobj.readed[i].title+" <i class=\"icon-paperclip\"></i></span> "+
						"	<time>"+moment(jsonobj.readed[i].createDate,"YYYY-MM-DD hh:mm").startOf('day').fromNow()+"</time> "+
						"	<span class=\"msg-body\">"+jsonobj.readed[i].context+" </span> "+
						"</a>"+
						"</span>"+
						" </li>";
						$(li).prependTo("#read");
            	});
         };
         websocket.onerror = function (evnt) {
         };
         websocket.onclose = function (evnt) {
         }
         /**消息提醒部分end */
		//菜单选中与否
		$('.accordion-nav a').each(function() {
	        var thisHref = $(this).attr('href')
	        if ((window.location.pathname.indexOf(thisHref) == 0) || (window.location.pathname.indexOf('/' + thisHref) == 0)) {
	        	$(".left-nav .active").removeClass("active");
	        	$("a[href=#"+$(this).parents('div').attr("id")+"]").parent("li").addClass("active");
	            $(this).parent('li').addClass("active").parent('ul').addClass("active").parent("div").addClass("active");
	    }
	    });
	});
	
	//Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend( true, $.fn.DataTable.TableTools.classes, {
		  "container": "datable-tool btn-group",
		  "buttons": {
			  "normal": "btn",
			  "disabled": "btn disabled"
		  },
		  "collection": {
			  "container": "DTTT_dropdown dropdown-menu",
			  "buttons": {
				  "normal": "",
				  "disabled": "disabled"
			  }
		  }
	} );
	// Have the collection use a bootstrap compatible dropdown
	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		  "collection": {
			  "container": "ul",
			  "button": "li",
			  "liner": "a"
		  }
	} );
	/**jquery全局异常处理*/
	 $.ajaxSetup({  
	        error: function(jqXHR, textStatus, errorThrown){ 
	        	if(jqXHR.status.toString().charAt(0)=='5')
	        		alert("服务器端处理错误");
	        	else if(jqXHR.status.toString().charAt(0)=='4')
	        		alert("客户端参数端错误");
	        	else if(jqXHR.status.toString().charAt(0)=='2'||jqXHR.status==0)
	        		alert("服务器出错，或者长时间未登录请重新登录！");
	        	else 
	        		alert("未知错误！");
	        }
	    });
	
	function  show_brushcar(){
		window.open("${pageContext.request.contextPath}/common/brushDataUtil","_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=550,left=300,top=300")
	}
	</script>
	
	