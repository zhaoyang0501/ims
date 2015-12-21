<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.hsae.ims.utils.RightUtil"%>

<html lang="ch">
<head>
<link href="./bootstrap-3.3.2/css/bootstrap.css" rel="stylesheet">
<script src="./bootstrap-3.3.2/js/jquery.js"></script>
<script src="./bootstrap-3.3.2/js/bootstrap.js"></script>
<script src="./bootstrap-3.3.2/js/angular.js"></script>

<script type="text/javascript" src="../../js/ims/ims.js"></script>
<title>通知公告</title>
<style type="text/css">
	body{
		background: #D6E4EF;
	}
	table {
		  clear: both !important;
		  background:white;
		  font-size: 12px;
		  border: 1px #83ACCF solid;
 		  border-collapse: collapse;
	}
</style>
<script type="text/javascript">
	$(document).ready(function () {
		var id = opener.document.getElementById("hf_id").value;
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/sysconfig/news/query/" + id,
			dataType : "json",
			success : function(json) {
				if (json) {
// 					$("#type").html("["+json.type.name+"]");
					$("#title").html(json.title);
					$("#contents").html(json.contents);
					$("#publisher").html(json.user.chinesename);
					$("#dept").html(json.user.dept == null ? "" : json.user.dept.name);
					$("#dept1").html(json.user.dept == null ? "" : json.user.dept.name);
					$("#pdate").html(json.date);
					$("#pdate2").html(json.date);
					$("#pdate3").html(json.date);
// 					$("#publisher2").html(json.user.chinesename);
					
					if(json.attachment != ""){
						var attachment = json.attachment.split(',');
						var filehtml = '&nbsp;';
						for(var i = 0; i < attachment.length; i++){
							var defaultname = attachment[i];
							var filename = defaultname.substring(13);
							filehtml += '<a download="'+filename+'" href="${pageContext.request.contextPath}/upload/news/'+defaultname+'">'+filename+'</a><br />&nbsp;';
						}
						$("#attachfile").html(filehtml);
					}
				}
			}
		});
	});
</script>
</head>
<body>
	<table id="tb_news" align="center" style="margin-top: 15px; margin-bottom: 30px;height: 450px;;width:98%;">
	   <tr>
		   <td  width="100%" style="padding:0px"> 
		     <table width="100%" cellpadding="0" >
		      <tr>        
		        <td class="center" width="100%" style="font-size: 18px;background: url('bootstrap-3.3.2/img/block_hd_bg.png') left top repeat-x;">
		        	<font id="type" color="blue"></font><font id="title"></font>
		        </td>       
		     </tr>
		    </table>
		 </td>
	 </tr>
	    <tr>
	      <td align="right" style="background: #F0F0F0;  padding: 3px;border-bottom: 1px #CFDDEA solid;  border-right: 1px #CFDDEA solid;">
	      	&nbsp;&nbsp;发布部门：<span><font id="dept" class="text-warning"></font></span>
	      	&nbsp;&nbsp;发布人：<span><font id="publisher" class="text-warning"></font></span>
	      	&nbsp;&nbsp;发布于：<span><font id="pdate3" class="text-warning"></font></span>
	      </td>
	    </tr>
	    <tr>
	      	<td colspan="2" valign="top" style="height:80%;font-size:12pt;">
			<font id="contents"></font>
			<br />
			<br />
			<br />
			</td>
	    </tr>
	    <tr>
	    	<td colspan="2" align="right">
	    		<br /><br />
	    		<font id="dept1" style=""></font>
				<br />
				<font id="pdate" style=""></font> 
				<br><br>
	    	</td>
	    </tr>
<!-- 	    <tr> -->
<!-- 	      <td colspan="2" align="left" style="background: #F0F0F0;  padding: 3px;border-bottom: 1px #CFDDEA solid;  border-right: 1px #CFDDEA solid;"> -->
<!-- 	      	<u title="部门：上海技术中心/技术管理部" style="cursor:hand"><font id="publisher2"></font></u> -->
<!-- 	      		最后编辑于：<i><font id="pdate2"></font> </i>       -->
<!-- 	       </td> -->
<!-- 	    </tr> -->
	    <tr>
	    	<td style="line-height: 21px;font-weight: bold;background: url('bootstrap-3.3.2/img/block_hd_bg.png') left top repeat-x;">
	    	&nbsp;附件下载：
	    	</td>
	    </tr>
	    <tr>
	    	<td id="attachfile">
	    	</td>
	    </tr>
	    <tr style="height: 50px;"><td>&nbsp;</td>
	    </tr>
  </table>
</body>
</html>




