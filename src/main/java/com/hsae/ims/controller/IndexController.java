package com.hsae.ims.controller;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.constants.ImsConstants.CasConstants;
import com.hsae.ims.controller.response.ListResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.RemoteImages;
import com.hsae.ims.entity.RemoteNews;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkFlowOverTime;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.service.WorkFlowOverTimeService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.service.cas.BookManageNewsService;
import com.hsae.ims.service.cas.DiscuzzImgageService;
import com.hsae.ims.service.cas.DiscuzzNewsService;
import com.hsae.ims.service.cas.RemoteMesgService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
/***
 * 首页获取待办数量等信息
 * @author panchaoyang
 *
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	private  WeekReportService weekReportService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	/***
	 * 获得日志待办数量
	 * @return
	 */
	@RequestMapping(value="/getDailyReportToApproveCount")
	@ResponseBody
	public Map<String,Object> getDailyReportToApproveCount( HttpSession session, HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", "success");
		try {
			map.put("count",weekReportService.getToApproveCount(RightUtil.getCurrentUserId()));
		} catch (WorkflowException e) {
			map.put("state", "error");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	/***
	 * 获取远程CAS消息
	 * @param appid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@RequestMapping(value="/getRemoteNews/{appid}")  
	@ResponseBody
	public Map<String,Object> getRemoteNews( @PathVariable Integer appid) throws HttpException, IOException{
		Map<String,Object> map=new HashMap<String,Object>();
		RemoteMesgService remoteNewsService=null;
		if(appid==CasConstants.DISCUZZ_APPID)
			remoteNewsService = new DiscuzzNewsService<RemoteNews>();
		else if(appid==CasConstants.BOOKMANAGE_APPID)
		 remoteNewsService =new BookManageNewsService<RemoteNews>();
		map.put("news", remoteNewsService.getJsonResult());
		return map;
	}
	/***
	 * 获取远程CAS消息
	 * @param appid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@RequestMapping(value="/getRemoteImages/{appid}")  
	@ResponseBody
	public Map<String,Object> getRemoteImages( @PathVariable Integer appid) throws HttpException, IOException{
		Map<String,Object> map=new HashMap<String,Object>();
		RemoteMesgService remoteNewsService=null;
		if(appid==CasConstants.DISCUZZ_APPID)
			remoteNewsService = new DiscuzzImgageService<RemoteImages>();
		map.put("news", remoteNewsService.getJsonResult());
		return map;
	}
	/***
	 * 获取待办事项
	 * @return
	 */
	@RequestMapping(value="/getTodoList")  
	@ResponseBody
	public Response getTodoList(){
		List<CurrentStep> list= workFlowService.findTop5TodoList(userService.findOne(RightUtil.getCurrentUserId()));
		return new ListResponse<CurrentStep>(list);
	}
	/***
	 * 首页获取用户信息
	 * @return
	 */
	@RequestMapping(value="/getUser")  
	@ResponseBody
	public Response getUser(){
		User user = userService.findOne(RightUtil.getCurrentUserId());
		return new ObjectResponse<User>(user);
	}
	@RequestMapping(value = "/common/brushDataUtil", method = RequestMethod.GET)
	public String brushDateutil(){
		return "common/brushdateutil";
	}
	/***
	 * 显示刷卡数据给婷婷用的
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param begin
	 * @param end
	 * @param state
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getBrushData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBrushData(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long  user, String start) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date begain = start==null?null: DateUtils.parseDate(start, "yyyy-MM-dd");
		Page<AttenceBrushRecord> page = attenceBrushRecordService.findBrushRecord( begain,user,pageNumber, pageSize);
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}
