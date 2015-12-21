package com.hsae.ims.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.config.TimeStampPropertyEditor;
import com.hsae.ims.controller.response.EmptyResponse;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ListResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.service.AttenceAbsenteeService;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceDayOffService;
import com.hsae.ims.service.AttenceTravelService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.UserService;
import com.opensymphony.workflow.WorkflowException;

/***
 * 考勤状态核对
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/attence/statecheck")
public class AttenceStateCheckController {

	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	
	@Autowired
	private AttenceAbsenteeService attenceAbsenteeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private AttenceDayOffService attenceDayOffService;
	
	@Autowired
	private AttenceTravelService   attenceTravelService;
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,   ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Timestamp.class, new TimeStampPropertyEditor("yyyy-MM-dd HH:mm"));  
	}  
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("attence/statecheck/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "考勤状态核对");
		breadCrumbMap.put("url", "attence/statecheck/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("dayyOffType", codeService.findDayOffCode());
		mav.addObject("absenteeType", codeService.findAbsenteeType());
		mav.addObject("attenceType", codeService.findAttenceCode());
		return mav;
	}
	/****
	 * 查待检查的考勤记录
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param brushDate
	 * @param user
	 * @param state
	 * @return
	 * @throws WorkflowException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/findBrushRecordForCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBrushRecordForCheck(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, 
			String sstartDate,String sendDate,
			Long user, String state) throws WorkflowException, ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1, pageSize = iDisplayLength;
		
		Date startDate= sstartDate==null?null:DateUtils.parseDate(sstartDate, "yyyy-MM-dd");
	    Date endDate= sendDate==null?null:DateUtils.parseDate(sendDate, "yyyy-MM-dd");
		Page<AttenceBrushRecord> attenceBrushRecord=attenceBrushRecordService.findBrushRecord(startDate,endDate, user,state, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", attenceBrushRecord.getContent());
		map.put("iTotalRecords", attenceBrushRecord.getTotalElements());
		map.put("iTotalDisplayRecords", attenceBrushRecord.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 标为漏打卡
	 * @param attenceAbsentee
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/saveAbsentee", method = RequestMethod.POST)
	@ResponseBody
	public Response saveAbsentee(AttenceAbsentee attenceAbsentee,Long brushRecordId ){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		if(attenceAbsentee.getId()==null){
			attenceAbsentee.setAbsenteeDate(attenceBrushRecord.getBrushDate());
			attenceAbsentee.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceAbsentee.setUser(attenceBrushRecord.getPersonId());
			attenceAbsenteeService.saveAttenceAbsentee(attenceAbsentee);
		}else{
			AttenceAbsentee attenceAbsenteeTobeUpdate = attenceAbsenteeService.findAbsenteee(attenceAbsentee.getId());
			attenceAbsenteeTobeUpdate.setAbsenteeType(attenceAbsentee.getAbsenteeType());
			attenceAbsenteeTobeUpdate.setAbsenteeTime(attenceAbsentee.getAbsenteeTime());
			attenceAbsenteeTobeUpdate.setRemark(attenceAbsentee.getRemark());
			attenceAbsentee.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceAbsenteeService.saveAttenceAbsentee(attenceAbsenteeTobeUpdate);
		}
		/**计未漏打卡状态*/
		attenceBrushRecord.setState("30");
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
		return new SuccessResponse();
	}
	/***
	 * 标为请假
	 * @param attenceDayoff
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/saveDayOff", method = RequestMethod.POST)
	@ResponseBody
	public Response saveDayOff(AttenceDayoff attenceDayoff,Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		if(attenceDayoff.getStartTime().after(attenceDayoff.getEndTime()) )
			return new FailedResponse("0","请假时间起不能大于请假时间止");
		if(DateUtils.truncate(attenceDayoff.getStartTime(),  Calendar.DAY_OF_MONTH).after(attenceBrushRecord.getBrushDate()))
			return new FailedResponse("0","时间区间不正确，只能录入当天的请假");
		if(DateUtils.truncate(attenceDayoff.getEndTime(),  Calendar.DAY_OF_MONTH).before(attenceBrushRecord.getBrushDate()))
			return new FailedResponse("0","时间区间不正确，只能录入当天的请假");
			
		if(attenceDayoff.getId()==null){
			attenceDayoff.setDayoffDate(attenceBrushRecord.getBrushDate());
			attenceDayoff.setUser(userService.findOne(attenceBrushRecord.getPersonId()));
			attenceDayoff.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceDayOffService.save(attenceDayoff);
		}else{
			AttenceDayoff attenceDayoffTobeUpdate = attenceDayOffService.getAttenceDayoff(attenceDayoff.getId());
			/**清除旧数据*/
			List<AttenceBrushRecord> attenceBrushRecords = attenceBrushRecordService.findBrushRecord(DateUtils.truncate(attenceDayoffTobeUpdate.getStartTime(),  
					Calendar.DAY_OF_MONTH), DateUtils.truncate(attenceDayoffTobeUpdate.getEndTime(),  Calendar.DAY_OF_MONTH), attenceBrushRecord.getPersonId());
			
			for(AttenceBrushRecord bean:attenceBrushRecords){
				bean.setState("60");
			}
			attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecords);
			
			attenceDayoffTobeUpdate.setStartTime(attenceDayoff.getStartTime());
			attenceDayoffTobeUpdate.setEndTime(attenceDayoff.getEndTime());
			attenceDayoffTobeUpdate.setDayoffType(attenceDayoff.getDayoffType());
			attenceDayoffTobeUpdate.setRemark(attenceDayoff.getRemark());
			attenceDayoffTobeUpdate.setSpendHours(attenceDayoff.getSpendHours());
			attenceDayOffService.save(attenceDayoffTobeUpdate);
		}
		/**计未请假状态*/
		List<AttenceBrushRecord> attenceBrushRecords = attenceBrushRecordService.findBrushRecord(DateUtils.truncate(attenceDayoff.getStartTime(),  
				Calendar.DAY_OF_MONTH), DateUtils.truncate(attenceDayoff.getEndTime(),  Calendar.DAY_OF_MONTH), attenceBrushRecord.getPersonId());
		
		for(AttenceBrushRecord bean:attenceBrushRecords){
			bean.setState("20");
		}
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecords);
		return new SuccessResponse();
	}
	/***
	 * 标为出差
	 * @param attenceTravel
	 * @param brushRecordId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/saveTravel", method = RequestMethod.POST)
	@ResponseBody
	public Response saveTravel(AttenceTravel attenceTravel,Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		if(attenceTravel.getStartTime().after(attenceTravel.getEndTime()) )
			return new FailedResponse("0","请假时间起不能大于请假时间止");
		if(DateUtils.truncate(attenceTravel.getStartTime(),  Calendar.DAY_OF_MONTH).after(attenceBrushRecord.getBrushDate()))
			return new FailedResponse("0","时间区间不正确，只能录入当天的出差");
		if(DateUtils.truncate(attenceTravel.getEndTime(),  Calendar.DAY_OF_MONTH).before(attenceBrushRecord.getBrushDate()))
			return new FailedResponse("0","时间区间不正确，只能录入当天的出差");
		
		if(attenceTravel.getId()==null){
			attenceTravel.setTravelDate(attenceBrushRecord.getBrushDate());
			attenceTravel.setUser(userService.findOne(attenceBrushRecord.getPersonId()));
			attenceTravel.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceTravelService.save(attenceTravel);
		}else{
			AttenceTravel attenceTravelTobeUpdate = attenceTravelService.getAttenceTravel(attenceTravel.getId());
			/**清除旧数据*/
			List<AttenceBrushRecord> attenceBrushRecords = attenceBrushRecordService.findBrushRecord(DateUtils.truncate(attenceTravelTobeUpdate.getStartTime(),
					Calendar.DAY_OF_MONTH), DateUtils.truncate(attenceTravelTobeUpdate.getEndTime(),  Calendar.DAY_OF_MONTH), attenceBrushRecord.getPersonId());
			for(AttenceBrushRecord bean:attenceBrushRecords){
				bean.setState("60");
			}
			attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecords);
			
			attenceTravelTobeUpdate.setStartTime(attenceTravel.getStartTime());
			attenceTravelTobeUpdate.setEndTime(attenceTravel.getEndTime());
			attenceTravelTobeUpdate.setAddress(attenceTravel.getAddress());
			attenceTravelTobeUpdate.setReason(attenceTravel.getReason());
			attenceTravelService.save(attenceTravelTobeUpdate);
		}
		/**计未出差状态*/
		List<AttenceBrushRecord> attenceBrushRecords = attenceBrushRecordService.findBrushRecord(DateUtils.truncate(attenceTravel.getStartTime(),
				Calendar.DAY_OF_MONTH), DateUtils.truncate(attenceTravel.getEndTime(),  Calendar.DAY_OF_MONTH), attenceBrushRecord.getPersonId());
		for(AttenceBrushRecord bean:attenceBrushRecords){
			bean.setState("40");
		}
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecords);
		return new SuccessResponse();
	}
	
	/***
	 * 标为正班
	 * @param attenceTravel
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/saveOk", method = RequestMethod.POST)
	@ResponseBody
	public Response saveOk(String remark,Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		attenceBrushRecord.setRemark(remark);
		/**标为正班状态*/
		attenceBrushRecord.setState("10");
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
		return new SuccessResponse();
	}
	
	/***
	 * 标为未知异常
	 * @param attenceTravel
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/saveUnknown", method = RequestMethod.POST)
	@ResponseBody
	public Response saveUnknown(String remark,Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		attenceBrushRecord.setRemark(remark);
		/**标为未知异常*/
		attenceBrushRecord.setState("60");
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
		/**清除考勤记录*/
		attenceAbsenteeService.deleteByUserAndDate(attenceBrushRecord.getPersonId(), attenceBrushRecord.getBrushDate());
		attenceDayOffService.deleteByUserAndDate(attenceBrushRecord.getPersonId(), attenceBrushRecord.getBrushDate());
		attenceTravelService.deleteByUserAndDate(attenceBrushRecord.getPersonId(), attenceBrushRecord.getBrushDate());
		
		return new SuccessResponse();
	}
	/***
	 * 标为迟到
	 * @param attenceTravel
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/saveLater", method = RequestMethod.POST)
	@ResponseBody
	public Response saveLater(String remark,Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		attenceBrushRecord.setRemark(remark);
		/**标为迟到*/
		attenceBrushRecord.setState("70");
		attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
		return new SuccessResponse();
	}
	/***
	 * 找某人某天是不是已经有请假记录
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/getDayOff/{brushRecordId}", method = RequestMethod.GET)
	@ResponseBody
	public Response getDayOff(@PathVariable Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		/**判断当天有没有请假*/
		List<AttenceDayoff> attenceDayoffs = attenceDayOffService.findAttenceDayoff(userService.findOne(attenceBrushRecord.getPersonId()), attenceBrushRecord.getBrushDate());
		
		return  attenceDayoffs.size()==0? new EmptyResponse() :new ListResponse<AttenceDayoff>( attenceDayoffs);
	}
	
	/***
	 * 找默认某天的出差记录
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/getTravel/{brushRecordId}", method = RequestMethod.GET)
	@ResponseBody
	public Response getTravel(@PathVariable Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		/**判断当天有没有请假*/
		AttenceTravel attenceTravel = attenceTravelService.findAttenceTravel(userService.findOne(attenceBrushRecord.getPersonId()), attenceBrushRecord.getBrushDate());
		return  attenceTravel==null? new EmptyResponse():new ObjectResponse<AttenceTravel>(attenceTravel);
	}
	
	/***
	 * 找默认某天某天的漏打卡记录
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/getAbsentee/{brushRecordId}", method = RequestMethod.GET)
	@ResponseBody
	public Response getAbsentee(@PathVariable Long brushRecordId){
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		AttenceAbsentee attenceAbsentee=attenceAbsenteeService.findAbsenteee(userService.findOne(attenceBrushRecord.getPersonId()), attenceBrushRecord.getBrushDate());
		return  attenceAbsentee==null? new EmptyResponse():new ObjectResponse<AttenceAbsentee>(attenceAbsentee);
	}
	
	@RequestMapping(value = "/getAbsenteeTimesThisMonth/{brushRecordId}", method = RequestMethod.GET)
	@ResponseBody
	public Response getAbsenteeTimesThisMonth(@PathVariable Long brushRecordId) throws ParseException{
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findById(brushRecordId);
		if(attenceBrushRecord==null)
			return new FailedResponse("0","找不到考勤记录");
		Integer frequency = attenceAbsenteeService.findAbsenteeFrequency(userService.findOne(attenceBrushRecord.getPersonId()).getId(), attenceBrushRecord.getBrushDate());
		return new ObjectResponse<Integer>(frequency);
	}
}
