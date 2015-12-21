package com.hsae.ims.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.AttenceRefleshLog;
import com.hsae.ims.service.AttenceRefleshLogService;
import com.hsae.ims.task.AttenceDataBaseRefreshJob;
/***
 * 考勤迁移日志查询 重新迁移
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/sysconfig/attencedatareflesh")
public class AttenceDataRefleshController {

	@Autowired
	private AttenceRefleshLogService attenceRefleshLogService;
	
	@Autowired
	private AttenceDataBaseRefreshJob attenceDataBaseRefreshJob;
	/**
	 *考勤状态迁移列表
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("sysconfig/attencedatareflesh/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "考勤数据迁移");
		breadCrumbMap.put("url", "sysconfig/attencedatareflesh/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	/**
	 * 查所有的日志
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/findLogs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findLogs(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String startDate, String endDate) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date start=null, end=null;
		if(StringUtils.isNotBlank(startDate))
			start=DateUtils.parseDate(startDate, "yyyy-MM-dd");
		if(StringUtils.isNotBlank(endDate))
			end=DateUtils.parseDate(endDate, "yyyy-MM-dd");
		Page<AttenceRefleshLog> AttenceRefleshLogs = attenceRefleshLogService.findAttenceRefleshLogs(start, end, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", AttenceRefleshLogs.getContent());
		map.put("iTotalRecords", AttenceRefleshLogs.getTotalElements());
		map.put("iTotalDisplayRecords", AttenceRefleshLogs.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 执行刷新考勤数据
	 * @param whichDate
	 * @return
	 */
	@RequestMapping(value = "/doReflesh/{whichDate}")
	@ResponseBody
	public Response doReflesh(@PathVariable String  whichDate){
		Assert.notNull(whichDate);
		Date start;
		try {
			start = DateUtils.parseDate(whichDate, "yyyy-MM-dd");
			AttenceRefleshLog attenceRefleshLog = attenceDataBaseRefreshJob.reflashAttenceData(start);
			attenceRefleshLogService.deleteAttenceRefleshLog(start);
			attenceRefleshLogService.saveAttenceRefleshLog(attenceRefleshLog);
		} catch (ParseException e) {
			e.printStackTrace();
			return new FailedResponse();
		}
		return new SuccessResponse();
	}
}
