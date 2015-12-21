<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar navbar-inverse top-nav ">
		<div class="navbar-inner">
			<div class="container">
				<span class="home-link"></span>
				<a class="brand" href="#"><img src="images/logo/logo4.png" style="width:366px;height:100px;" alt="Falgun"></a>
			</div>
			
			<div class="btn-toolbar pull-right notification-nav pinned"  >
								<sec:authorize access="hasRole('ROLE_ATTENCE')">  
							   		<a title="刷卡工具" onclick="show_brushcar();" class="btn btn-notification " style="background-image: url('images/brushcard.png') ;margin-top: 0px;margin-right: 20px" data-toggle="dropdown"><i class="icon-"><span class="notify-tip">0</span></i></a>
								</sec:authorize>  
									 					
		 							<a  title="我的消息"  class="btn btn-notification " style="background-image: url('images/notification.png') ;margin-top: 0px;" data-toggle="dropdown"><i class="icon-"><span class="notify-tip">0</span></i></a>
		 							<div  class="ajax-dropdown pull-right " style="display: none;" >
										<div class="btn-group btn-group-justified" data-toggle="buttons">
											<label class="btn btn-default active">
												<input type="radio" name="activity" value="unread">
												未读 (<span id='unread_num'>0</span> ) </label>
											<label class="btn btn-default ">
												<input type="radio" name="activity" value="read" checked="checked">
												已读 (<span id='readed_num'>0</span>) </label>
										</div>
										
										<!-- notification content -->
										<div class=" ajax-notifications custom-scroll" style="opacity: 1;">
										
										 <div class="notification-body tmsg_error" style="display: none;">
											<p class="tmsg_error_notice">
												<img width="23" height="14"
													src="images/T1KLt9FuVaXXXIaF_b-23-14.png"
													alt=""> <span>没有新消息了</span> <img width="23" height="14"
													src="images/T1xwX6FshtXXXIaF_b-23-14.png"
													alt="">
											</p>
											<div class=" tmsg_error_pic tmsg_error_pic_type1"></div>
										</div>
										 				
										<ul class="notification-body" id='unread'>
										</ul>
										
										<ul class="notification-body" id='read'>
										</ul> 
									</div>
									<span >更新时间<span id='msg_update_time'></span></span> 
								</div>
		 	</div> 
		</div>
	</div>
	
