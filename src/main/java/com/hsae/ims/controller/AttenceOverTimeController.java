package com.hsae.ims.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.controller.response.EmptyResponse;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.AttenceOverTimeCheckDTO;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkingHoursCheckObserver;
import com.hsae.ims.service.WorkingHoursCheckObserverComparator;
import com.opensymphony.workflow.WorkflowException;
/***
 * 考勤模块---工时核对
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/attence/overtime")
public class AttenceOverTimeController {

	@Autowired
	private AttenceOverTimeService attenceOverTimeService;
	
	@Autowired
	private DailyReportService dailyReportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	
	/**工时核对的观察者们，目前有两个，日志状态观察者，工时核对观察者*/
	@Autowired
	private   List<WorkingHoursCheckObserver> workingHoursCheckObservers;
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));  
	}  
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("attence/overtime/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "加班工时核对");
		breadCrumbMap.put("url", "attence/overtime/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		
		return mav;
	}
	/***
	 *找出加班记录以便工时核对
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param state
	 * @return
	 * @throws WorkflowException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/findDailyReportForCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDailyReportForCheck(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, 
			String startDate, String endDate,
			Long user, String state,String oaState) throws WorkflowException, ParseException {
		
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1, pageSize = iDisplayLength;
		Date start=null, end=null;
		if(StringUtils.isNotBlank(startDate))
			start=DateUtils.parseDate(startDate, "yyyy-MM-dd");
		if(StringUtils.isNotBlank(endDate))
			end=DateUtils.parseDate(endDate, "yyyy-MM-dd");
		
		Page<DailyReport> dailyReport =dailyReportService.findOverTimeDailyReport(start, end, user, state,oaState, pageNumber, pageSize);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dailyReport.getContent());
		map.put("iTotalRecords", dailyReport.getTotalElements());
		map.put("iTotalDisplayRecords", dailyReport.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	/***
	 * 保存一个工时核对 ，如果存在就修改
	 * 
	 * @param attenceOverTime
	 * @param reportId
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public Response save(AttenceOverTimeCheckDTO overTimeDTO) throws ParseException{
		
		AttenceOverTime attenceOverTime = new AttenceOverTime();
		DailyReport dailyReport = dailyReportService.getDailyReportById(overTimeDTO.getReportId());
		
		String startTime=DateFormatUtils.format(dailyReport.getReportDate(), "yyyy-MM-dd")+" "+overTimeDTO.getStartTime();
		String endTime=DateFormatUtils.format(dailyReport.getReportDate(), "yyyy-MM-dd")+" "+overTimeDTO.getEndTime();
		if(overTimeDTO.getOverTimeId()==null){
			/**判断是不是被核对完成*/
			List<AttenceOverTime> attenceOverTimes = attenceOverTimeService.findByDailyReportId(overTimeDTO.getReportId());
			if(attenceOverTimes.size()!=0)
				return new FailedResponse(FailedResponse.CODE_FAILED,"该加班已经被核对");
			
			attenceOverTime.setEndTime(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm"));
			attenceOverTime.setStartTime(DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm"));
			attenceOverTime.setOvertimeType(overTimeDTO.getOvertimeType());
			attenceOverTime.setUser(dailyReport.getUser());
			attenceOverTime.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceOverTime.setOvertimeDate(dailyReport.getReportDate());
			attenceOverTime.setCheckHours(overTimeDTO.getCheckHours());
			attenceOverTime.setBrushRecord(overTimeDTO.getBrushRecord());
			attenceOverTime.setOaState(overTimeDTO.getOaState());
			attenceOverTimeService.save(attenceOverTime,dailyReport.getId());
		}else{
			AttenceOverTime attenceOverTimeToBeUpdate = attenceOverTimeService.findById(overTimeDTO.getOverTimeId());
			attenceOverTimeToBeUpdate.setEndTime(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm"));
			attenceOverTimeToBeUpdate.setStartTime(DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm"));
			attenceOverTimeToBeUpdate.setOvertimeType(overTimeDTO.getOvertimeType());
			attenceOverTimeToBeUpdate.setUser(dailyReport.getUser());
			attenceOverTimeToBeUpdate.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceOverTimeToBeUpdate.setOvertimeDate(dailyReport.getReportDate());
			attenceOverTimeToBeUpdate.setCheckHours(overTimeDTO.getCheckHours());
			attenceOverTimeToBeUpdate.setBrushRecord(overTimeDTO.getBrushRecord());
			attenceOverTimeToBeUpdate.setOaState(overTimeDTO.getOaState());
			attenceOverTimeService.save(attenceOverTimeToBeUpdate);
		}
		
		this.notifyWatchers(dailyReport);
		return new SuccessResponse();
	}
	/***
	 * 找某天的刷卡记录
	 * @param dailyReportId
	 * @return
	 */
	@RequestMapping(value="/getBrushCardData/{dailyReportId}")
	@ResponseBody
	public Response getBrushCardData(@PathVariable Long dailyReportId){
		DailyReport dailyReport=dailyReportService.getDailyReportById(dailyReportId);
		if(dailyReport==null) 
			return new EmptyResponse();
		AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.
				findBrushRecord( dailyReport.getUser().getId(), dailyReport.getReportDate());
		return new ObjectResponse<AttenceBrushRecord>(attenceBrushRecord);
	}
	

	/***
	 * 找某人某天是不是已经有加班记录
	 * @param brushRecordId
	 * @return
	 */
	@RequestMapping(value = "/getOverTime/{dailyReportId}", method = RequestMethod.GET)
	@ResponseBody
	public Response getOverTime(@PathVariable Long dailyReportId){
		List<AttenceOverTime> attenceOverTimes = attenceOverTimeService.findByDailyReportId(dailyReportId);
		if(attenceOverTimes.size()==0)
			return new EmptyResponse();
		else return new ObjectResponse<AttenceOverTime>(attenceOverTimes.get(0)) ;
	}
	
	/***
	 * 通知观察者工时核对完成
	 * @param attenceOverTime
	 */
	private void notifyWatchers(DailyReport dailyReport){
		WorkingHoursCheckObserverComparator comparator=new WorkingHoursCheckObserverComparator();
	    Collections.sort(workingHoursCheckObservers, comparator);
		if(workingHoursCheckObservers!=null){
			for(WorkingHoursCheckObserver workingHoursCheckObserver :workingHoursCheckObservers){
				workingHoursCheckObserver.update(dailyReport);
			}
		}
	}
}
