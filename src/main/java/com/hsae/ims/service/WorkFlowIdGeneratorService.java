package com.hsae.ims.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.repository.ReimburseRepository;
import com.hsae.ims.repository.WorkFlowDayoffRepository;
import com.hsae.ims.repository.WorkFlowOverTimeRepository;
import com.hsae.ims.repository.WorkFlowTrainingRequireGatherRepository;
import com.hsae.ims.repository.WorkflowAbsenteeRepository;
import com.hsae.ims.repository.WorkflowAwayRepository;

@Service
public class WorkFlowIdGeneratorService {
	
	final static String  DAYOFF_PRE="QJD"; 
	
	final static String  OVERTIME_PRE="JBD"; 
	
	final static String  AWAY_PRE="WCD"; 
	
	final static String  ABSENTEE_PRE="DKD"; 
	
	final static String  REIMBURSE_PRE="BXD"; 
	
	final static String REQUIREGATHER_PRE = "XQD";
	
	@Autowired
	private WorkFlowDayoffRepository workFlowDayoffRepository;
	
	@Autowired
	private WorkFlowOverTimeRepository workFlowOverTimeRepository;
	
	@Autowired
	private WorkflowAwayRepository workflowAwayRepository;
	
	@Autowired
	private WorkflowAbsenteeRepository workflowAbsenteeRepository;
	
	@Autowired
	private  ReimburseRepository reimburseRepository;
	
	@Autowired
	private WorkFlowTrainingRequireGatherRepository workFlowTrainingRequireGatherRepository;
	
	private DecimalFormat numberFormat = new DecimalFormat("0000");
	/***
	 * 获取请假单编号
	 * @return
	 */
	public String getDayOffWorkFlowId(){
		Integer count=workFlowDayoffRepository.countByMonth(
				DateUtils.truncate(new Date(),  Calendar.MONTH),DateUtils.addMonths((DateUtils.truncate(new Date(),  Calendar.MONTH)),1));
		
		return DAYOFF_PRE+"_"+DateFormatUtils.format(new Date(), "yyyyMM"+"_"+numberFormat.format((count+1)));
	}
	/***
	 * 获取加班单编号
	 * @return
	 */
	public String getOverTimeWorkFlowId(){
		Integer count=workFlowOverTimeRepository.countByMonth(
				DateUtils.truncate(new Date(),  Calendar.MONTH),DateUtils.addMonths((DateUtils.truncate(new Date(),  Calendar.MONTH)),1));
		
		return OVERTIME_PRE+"_"+DateFormatUtils.format(new Date(), "yyyyMM"+"_"+numberFormat.format((count+1)));
	}
	/***
	 * 获取外出单编号
	 * @return
	 */
	public String getAwayWorkFlowId(){
		Integer count=workflowAwayRepository.countByMonth(
				DateUtils.truncate(new Date(),  Calendar.MONTH),DateUtils.addMonths((DateUtils.truncate(new Date(),  Calendar.MONTH)),1));
		
		return AWAY_PRE+"_"+DateFormatUtils.format(new Date(), "yyyyMM"+"_"+numberFormat.format((count+1)));
	}
	/***
	 * 获取漏打卡编号
	 * @return
	 */
	public String getAbsenteeWorkFlowId(){
		Integer count=workflowAbsenteeRepository.countByMonth(
				DateUtils.truncate(new Date(),  Calendar.MONTH),DateUtils.addMonths((DateUtils.truncate(new Date(),  Calendar.MONTH)),1));
		
		return ABSENTEE_PRE+"_"+DateFormatUtils.format(new Date(), "yyyyMM"+"_"+numberFormat.format((count+1)));
	}
	/***
	 * 获取报销编号
	 * @return
	 */
	public String getReimburseRepositoryWorkFlowId(){
		Integer count=reimburseRepository.countByMonth(
				DateUtils.truncate(new Date(),  Calendar.MONTH),DateUtils.addMonths((DateUtils.truncate(new Date(),  Calendar.MONTH)),1));
		
		return REIMBURSE_PRE+"_"+DateFormatUtils.format(new Date(), "yyyyMM"+"_"+numberFormat.format((count+1)));
	}
	
	/***
	 * 获取报销编号
	 * @return
	 */
	public String getRequireGatherRepositoryWorkFlowId(){
		Integer count = workFlowTrainingRequireGatherRepository.countByMonth(
				DateUtils.truncate(new Date(), Calendar.MONTH), DateUtils.addMonths((DateUtils.truncate(new Date(), Calendar.MONTH)), 1));
		
		return REQUIREGATHER_PRE + "_" + DateFormatUtils.format(new Date(), "yyyyMM" + "_" + numberFormat.format((count + 1)));
	}
}
