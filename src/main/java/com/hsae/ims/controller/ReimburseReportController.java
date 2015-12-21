package com.hsae.ims.controller;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.dto.ReimburseReportByProjectDTO;
import com.hsae.ims.dto.ReimburseReportByUserDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowService;
import com.osworkflow.SpringWorkflow;
/***
 * 餐费报销
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/reimburse/report")
public class ReimburseReportController {
	
	@Autowired
	private ReimburseService reimburseService;
	
	@Autowired
	private SpringWorkflow springWorkflow;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private ProjectService projectService;
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("reimburse/reimburse/report");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销统计");
		breadCrumbMap.put("url", "reimburse/report/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		Calendar c = Calendar.getInstance();
		String yearMonth = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)  + 1);	
		mav.addObject("yearMonth", yearMonth);
		mav.addObject("projects", projectService.findAllvalidProjects());
		return mav;
	}
	/***
	 * 按照人员
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/reportByUser", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reportByUser(String month ) throws ParseException{
		Map<String, Object>  map = new HashMap<String, Object>();
		Date date=DateUtils.parseDate(month, "yyyy-MM");
		DateTime dateTime =new DateTime(date);
		/**转化成前台能够直接使用的dto*/
		List<Tuple>  list= reimburseService.findReimburseReportByUser(dateTime.getYear(),dateTime.getMonthOfYear());
		List<ReimburseReportByUserDTO> dtos = new ArrayList<ReimburseReportByUserDTO>();
		for(Tuple tuple:list ){
			ReimburseReportByUserDTO dto = new ReimburseReportByUserDTO();
			dto.setUser(tuple.get("reimburser",User.class));
			dto.setReimburseMoney(tuple.get(1,Double.class));
			dtos.add(dto);
		}
		map.put("aaData", dtos);
		return map;
	}
	
	/**8
	 *按照项目
	 * @param month
	 * @param projectId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/reportByProject", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reportByProject(String month,Long projectId ) throws ParseException{
		Map<String, Object>  map = new HashMap<String, Object>();
		Date date=DateUtils.parseDate(month, "yyyy-MM");
		DateTime dateTime =new DateTime(date);
		/**转化成前台能够直接使用的dto*/
		List<Object[]>  list= reimburseService.findReimburseReportByProject(dateTime.getYear(),dateTime.getMonthOfYear(),projectId);
		List<ReimburseReportByProjectDTO> dtos = new ArrayList<ReimburseReportByProjectDTO>();
		for(Object[] array:list ){
			ReimburseReportByProjectDTO dto = new ReimburseReportByProjectDTO();
			dto.setProjectName((String)array[0]);
			dto.setReimburseDate((Date)array[1]);
			dto.setReimburseType(String.valueOf((Integer)array[2]));
			dto.setUsers((String)array[3]);
			dto.setUserNumber((BigInteger)array[4]);
			dto.setReimburseMoney((BigDecimal)array[5]);
			dto.setStandard((BigDecimal)array[6]);
			dtos.add(dto);
		}
		map.put("aaData", dtos);
		return map;
	}
}
