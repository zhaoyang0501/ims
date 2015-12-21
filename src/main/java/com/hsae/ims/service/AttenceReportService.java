package com.hsae.ims.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.dto.AttenceCountReportDTO;
import com.hsae.ims.dto.AttenceCountStateDTO;
import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.AttenceStatistics;
import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceAbsenteeRepository;
import com.hsae.ims.repository.AttenceDayoffRepository;
import com.hsae.ims.repository.AttenceOverTimeRepository;
import com.hsae.ims.repository.AttenceReportCountRepository;
import com.hsae.ims.repository.AttenceStatisticsRepository;
import com.hsae.ims.repository.AttenceTravelRepository;
import com.hsae.ims.repository.DeptRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.DateTimeUtil;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AttenceReportService {
	
	@Autowired
	private AttenceAbsenteeRepository attenceAbsenteeRepository;
	
	@Autowired
	private AttenceOverTimeRepository attenceOvertimeRepository;
	
	@Autowired
	private AttenceTravelRepository attenceTravelRepository;
	
	@Autowired
	private AttenceDayoffRepository attenceDayoffRepository;
	
	@Autowired
	private AttenceStatisticsRepository attenceStatisticsRepository;
	
	@Autowired
	private AttenceReportCountRepository attenceReportCountRepository;
	
	@Autowired
	private DailyReportService dailyReportService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	//漏刷卡数据表。
	public Page<AttenceAbsentee> findUserAbsentee(Date sstartDate, Date sendDate, Long pmId, int pageNumber, int pageSize){
		PageRequest pageRequest =new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.ASC, "absenteeDate"));
		Specification<AttenceAbsentee> spec = absenteeBuildSpecification(sstartDate, sendDate,pmId);
		return (Page<AttenceAbsentee>) attenceAbsenteeRepository.findAll(spec, pageRequest);
	}
	
	//加漏刷卡数据图表。
	public List<Map<String, Object>> queryAttenceAbsenteePieList (String startDate, String endDate){
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		List<Object []> absenteeDate = attenceReportCountRepository.findAttenceAbsenteeStatics(startDate, endDate);
		
		if (absenteeDate != null && absenteeDate.size() > 0){
			for (Object[] obj: absenteeDate){
				map = new HashMap<String, Object>();
				String deptName = obj[1].toString();
				Double counts = Double.parseDouble(obj[2] == null? "0" : obj[2].toString());
				map.put("name", deptName);
				map.put("value", counts);
				list.add(map);
			}
		}
		return list;
	}
	
	
	//加班统计数据表。
	public Page<AttenceOverTime> findUserOvertime(Date sstartDate, Date sendDate, Long pmId, int pageNumber, int pageSize){
		PageRequest pageRequest =new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.ASC, "overtimeDate"));
		Specification<AttenceOverTime> spec = overtimeBuildSpecification(sstartDate, sendDate,pmId);
		return (Page<AttenceOverTime>) attenceOvertimeRepository.findAll(spec, pageRequest);
	
	}
	
	//加班数据图表。
	public Map<String, Object> queryAttenceOvertimePieList (String startDate, String endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Double> dayOvertimeData = getOvertimeHoursCount(startDate, endDate, "1");
		List<Double> weekOvertimeData = getOvertimeHoursCount(startDate, endDate, "2");
		List<Double> holidayData = getOvertimeHoursCount(startDate, endDate, "3");
		List<String> deptData = findAllDeptNames(startDate, endDate);
		map.put("deptData", deptData);
		map.put("dayOvertimeData", dayOvertimeData);
		map.put("weekOvertimeData", weekOvertimeData);
		map.put("holidayData", holidayData);
		return map;
	}
	
	
	//根据类型获取dateList数据。 type: 1平时 2周末  。
	public List<Double> getOvertimeHoursCount (String startDate, String endDate, String type){
		List<Double> list = new ArrayList<Double>();
		List<Object[]> dataList = attenceReportCountRepository.findAttenceOvertimeStatics(startDate, endDate, type);
		for(Object[] obj : dataList){
			if (obj != null && obj.length >0) {
				Double hours = Double.parseDouble(obj[2] == null? "0" : obj[2].toString());
				list.add(hours);
			}
		}
		return list;		
	}
	
	//出差记录统计数据表。
	public Page<AttenceTravel> findUserTravel(Date sstartDate, Date sendDate, Long pmId, int pageNumber, int pageSize){
		PageRequest pageRequest =new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.ASC, "travelDate"));
		Specification<AttenceTravel> spec = travelBuildSpecification(sstartDate, sendDate,pmId);
		return (Page<AttenceTravel>) attenceTravelRepository.findAll(spec, pageRequest);
	
	}
	
	//请假数据表。
	public Page<AttenceDayoff> findUserDayoff(Date sstartDate, Date sendDate, Long pmId, int pageNumber, int pageSize){
		PageRequest pageRequest =new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.ASC, "dayoffDate"));
		Specification<AttenceDayoff> spec = dayoffBuildSpecification(sstartDate, sendDate,pmId);
		return (Page<AttenceDayoff>) attenceDayoffRepository.findAll(spec, pageRequest);
	}
	
	//请假数据图表。
	public Map<String ,Object> queryAttenceDayoffPieList(String startDate, String endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Double> dayoffData = getDayoffHoursCount(startDate, endDate, "30");
		List<Double> leaveData = getDayoffHoursCount(startDate, endDate, "40");
		List<Double> bornData = getDayoffHoursCount(startDate, endDate, "80");
		List<Double> pat_leaveData = getDayoffHoursCount(startDate, endDate, "110");
		List<Double> feedData = getDayoffHoursCount(startDate, endDate, "100");
		List<String> deptData = findAllDeptNames(startDate, endDate);
		map.put("deptData", deptData);
		map.put("dayoffData", dayoffData);
		map.put("leaveData", leaveData);
		map.put("bornData", bornData);
		map.put("pat_leaveData", pat_leaveData);
		map.put("feedData", feedData);
		
		
		return map;
	}
	
	//根据类型获取dateList数据。 type: 30调休假 40事假 80产假  100哺乳假  110陪产假 。
	public List<Double> getDayoffHoursCount (String startDate, String endDate, String type){
		List<Double> list = new ArrayList<Double>();
		List<Object[]> dataList = attenceReportCountRepository.findAttenceDayoffStatics(startDate, endDate, type);
		for(Object[] obj : dataList){
			if (obj != null && obj.length >0) {
				Double hours = Double.parseDouble(obj[2] == null? "0" : obj[2].toString());
				list.add(hours);
			}
		}
		return list;		
	}
	
	//根据类型获取部门数据。deptdateList
	public List<String> findAllDeptNames(String sstartDate, String sendDate){
		List<String> list = new ArrayList<String>();
		//根据类型获取dateList数据。 获得部门列表 。
		List<Object[]> dataList = attenceReportCountRepository.findAttenceAbsenteeStatics(sstartDate, sendDate);
		for(Object[] obj : dataList){
			if (obj != null && obj.length >0) {
				String dept = obj[1].toString();
				list.add(dept);
			}
		}
		return list;
	} 
	
	public Date findAttenceStatisticsLastUpdate(){
		return this.attenceStatisticsRepository.findLastUpdate();
	}
	
	public Page<AttenceStatistics> findAttenceStatistics(final String month,final User  user,
			final int pageNumber, final int pageSize) {
			 Specification<AttenceStatistics> spec = new Specification<AttenceStatistics>() {
				@Override
				public Predicate toPredicate(Root<AttenceStatistics> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (month!=null) {
						predicate.getExpressions().add(cb.equal(root.get("month").as(String.class), month));
					}
					if (user!=null) {
						predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), user));
					}
					return predicate;
				}
			};
			PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.ASC, "user.dept"));
			Page<AttenceStatistics> attenceStatisticss = attenceStatisticsRepository.findAll(spec, pageRequest);
			return attenceStatisticss;
		}
	//考勤按部门时数统计表。
	public List<AttenceCountReportDTO> findAttenceCount(String sstartDate, String sendDate){
		List<AttenceCountReportDTO> attenceCountList = new ArrayList<AttenceCountReportDTO>();
		Double dayOvertime = 0.;
		Double weekOvertime = 0.;
		Double holidayOvertime = 0.;
		Double dayoffFlirt = 0.;
		Double privateLeave = 0.;
		Double maternityLeave = 0.;
		Double feedingOff = 0.;
		Double paternityLeave = 0.;
		Integer absenteeCount = 0;
		
		//根据类型获取dateList数据。 type:1平时加班时数  2周末加班。 
		List<Object[]> dayOvertimeList = attenceReportCountRepository.findAttenceOvertimeStatics(sstartDate, sendDate, "1");
		List<Object[]> weeekOvertimeList = attenceReportCountRepository.findAttenceOvertimeStatics(sstartDate, sendDate, "2");
		List<Object[]> holidayOvertimeList = attenceReportCountRepository.findAttenceOvertimeStatics(sstartDate, sendDate, "3");

		//根据类型获取dateList数据。 type:30调休假 40事假 80产假  100哺乳假  110陪产假 。
		List<Object[]> dayoffFlirtList = attenceReportCountRepository.findAttenceDayoffStatics(sstartDate, sendDate, "30");
		List<Object[]> privateLeaveList = attenceReportCountRepository.findAttenceDayoffStatics(sstartDate, sendDate, "40");
		List<Object[]> maternityLeaveList = attenceReportCountRepository.findAttenceDayoffStatics(sstartDate, sendDate, "80");
		List<Object[]> feedingOffList = attenceReportCountRepository.findAttenceDayoffStatics(sstartDate, sendDate, "100");
		List<Object[]> paternityLeaveList = attenceReportCountRepository.findAttenceDayoffStatics(sstartDate, sendDate, "110");
		//根据类型获取dateList数据。  漏刷卡次数。
		List<Object[]> absenteeCountList = attenceReportCountRepository.findAttenceAbsenteeStatics(sstartDate, sendDate);
		
		AttenceCountReportDTO dto = null;
		AttenceCountReportDTO countDTO = new AttenceCountReportDTO();

		for(Object[] dept : dayOvertimeList){
				dto = new AttenceCountReportDTO();
				Long deptId = Long.valueOf((dept[0] == null?"0":dept[0].toString()));
				dto.setDeptName(dept[1].toString());

				if (dayOvertimeList != null && dayOvertimeList.size() > 0) {
					for (Object[] obj : dayOvertimeList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setDayOvertime(hours.toString());
							dayOvertime += hours;
						}
					}
				}
				if (weeekOvertimeList != null && weeekOvertimeList.size() > 0) {
					for (Object[] obj : weeekOvertimeList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setWeekOvertime(hours.toString());
	 						weekOvertime += hours;
						}
					}
				}
				
				if (holidayOvertimeList != null && holidayOvertimeList.size() > 0) {
					for (Object[] obj : holidayOvertimeList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setHolidayOvertime(hours.toString());
							holidayOvertime += hours;
						}
					}
				}
				
				
				if (dayoffFlirtList != null && dayoffFlirtList.size() > 0) {
					for (Object[] obj : dayoffFlirtList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setDayoffFlirt(hours.toString());
							dayoffFlirt += hours;
						}
					}
				}
				
				if (privateLeaveList != null && privateLeaveList.size() > 0) {
					for (Object[] obj : privateLeaveList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setPrivateLeave(hours.toString());
							privateLeave += hours;
						}
					}
				}
				
				if (maternityLeaveList != null && maternityLeaveList.size() > 0) {
					for (Object[] obj : maternityLeaveList) {
						Long tmpeDeptId = Long.valueOf((dept[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setMaternityLeave(hours.toString());
							maternityLeave += hours;
						}
					}
				}
				
				if (feedingOffList != null && feedingOffList.size() > 0) {
					for (Object[] obj : feedingOffList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setFeedingOff(hours.toString());
							feedingOff += hours;
						}
					}
				}
				
				if (paternityLeaveList != null && paternityLeaveList.size() > 0) {
					for (Object[] obj : paternityLeaveList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Double hours = Double.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setPaternityLeave(hours.toString());
							paternityLeave += hours;
						}
					}
				}
				
				if (absenteeCountList != null && absenteeCountList.size() > 0) {
					for (Object[] obj : absenteeCountList) {
						Long tmpeDeptId = Long.valueOf((obj[0] == null?"0":obj[0].toString()));
						if ( deptId.equals(tmpeDeptId)){
							Integer hours = Integer.valueOf((obj[2] == null?"0":obj[2].toString()));
							dto.setAbsenteeCount(hours.toString());
							absenteeCount += hours;
						}
					}
				}
				attenceCountList.add(dto);
		}
		countDTO.setDeptName("总计");
		countDTO.setDayOvertime(dayOvertime.toString());
		countDTO.setWeekOvertime(weekOvertime.toString());
		countDTO.setHolidayOvertime(holidayOvertime.toString());
		countDTO.setDayoffFlirt(dayoffFlirt.toString());
		countDTO.setPrivateLeave(privateLeave.toString());
		countDTO.setMaternityLeave(maternityLeave.toString());
		countDTO.setFeedingOff(feedingOff.toString());
		countDTO.setPaternityLeave(paternityLeave.toString());
		countDTO.setAbsenteeCount(absenteeCount.toString());
		attenceCountList.add(countDTO);
		return  attenceCountList;
	}
	
	
	//个人请假加班次数统计表。
	public List<AttenceCountStateDTO> getUserCountState (String fromDate, String toDate, Long pmId){
		List<AttenceCountStateDTO> dataList = new ArrayList<AttenceCountStateDTO>();
		List<Object[]> userCountState  = attenceReportCountRepository.findAttenceUserCountState(fromDate, toDate, pmId);
		AttenceCountStateDTO countState = null;
		User user = null;
		int index = 1;
		for (Object[] obj : userCountState){
			countState = new AttenceCountStateDTO();
			String userId = String.valueOf(obj[0] == null ? "0" :obj[0].toString());
			String overtimeCount = String.valueOf(obj[1] == null ? "" :obj[1].toString());
			String dayoffCount = String.valueOf(obj[2] == null ? "" :obj[2].toString());
			String hours = String.valueOf(obj[3] == null ? "" :obj[3].toString());
			String overtimeDate = String.valueOf(obj[4] == null ? "" :obj[4].toString());
			user = userRepository.findOne(Long.parseLong(userId));
			countState.setUserName(user == null? "" : user.getChinesename());
			countState.setOvertimeCount(overtimeCount);
			countState.setDayoffCount(dayoffCount);
			countState.setOvertimeHours(hours);
			countState.setIndex(String.valueOf(index));
			//加班报告：日志  dailyreport 中type：7（ImsComstants中）
			if (StringUtils.isNoneEmpty(overtimeDate)){
				List<DailyReport> dailyReportList = dailyReportService.findOverTimeDailyReport(DateTimeUtil.getFormatDate(overtimeDate));
				DailyReport dailyReport = null;
				if (dailyReportList != null && dailyReportList.size() > 0){
					dailyReport = dailyReportList.get(0);
					countState.setProjectName(dailyReport.getProject() == null ? "" : dailyReport.getProject().getProjectName());
				}else{
					countState.setProjectName("");
				}
			}else{
				countState.setProjectName("");
			}
			index++;
			dataList.add(countState);
		}
		return dataList;
	}
	
//	//调休时数统计数据表数据源。
//	private Specification<AttenceStatistics> attenceStatisticsBuildSpecification(final String sstartDate, final String sendDate, final Long pmId){
//		return new Specification<AttenceStatistics>() {
//			@Override
//			public Predicate toPredicate(Root<AttenceStatistics> root,
//					CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate predicate = cb.conjunction();
//				if (StringUtils.isNoneEmpty(sstartDate)){
//					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("startDate").as(String.class), sstartDate));
//				}
//				if (StringUtils.isNoneEmpty(sendDate)){
//					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("endDate").as(String.class), sendDate));
//				}
//				if (pmId != null && pmId > 0) {
//					User u = userRepository.findOne(pmId);
//					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), u));
//				}
//				return predicate;
//				}
//			};
//		}
//	
	
	//出差记录统计数据表数据源。
	private Specification<AttenceTravel> travelBuildSpecification(final Date sstartDate, final Date sendDate, final Long pmId){
		return new Specification<AttenceTravel>() {
			@Override
			public Predicate toPredicate(Root<AttenceTravel> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (sstartDate!=null){
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("travelDate").as(Date.class), sstartDate));
				}
				if (sendDate!=null){
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("travelDate").as(Date.class), sendDate));
				}
				if (pmId != null && pmId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class),  userRepository.findOne(pmId)));
				}
				return predicate;
				}
			};
		}
	
	//加班记录统计数据表数据源。
	private Specification<AttenceOverTime> overtimeBuildSpecification(final Date sstartDate, final Date sendDate, final Long pmId){
		return new Specification<AttenceOverTime>() {
			@Override
			public Predicate toPredicate(Root<AttenceOverTime> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (sstartDate!=null){
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("overtimeDate").as(Date.class),sstartDate));
				}
				if (sendDate!=null){
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("overtimeDate").as(Date.class), sendDate));
				}
				predicate.getExpressions().add(cb.equal(root.get("oaState").as(String.class), "1"));
				if (pmId != null && pmId > 0) {
					User u = userRepository.findOne(pmId);
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), u));
				}
				return predicate;
				}
			};
		}
	
	/***
	 * by pzy
	 * 漏打卡报表查询
	 * @param sstartDate
	 * @param sendDate
	 * @param pmId
	 * @return
	 */
	private Specification<AttenceAbsentee> absenteeBuildSpecification(final Date sstartDate, final Date sendDate, final Long pmId){
		return new Specification<AttenceAbsentee>() {
			@Override
			public Predicate toPredicate(Root<AttenceAbsentee> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (sstartDate!=null){
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("absenteeDate").as(Date.class), sstartDate));
				}
				if (sendDate!=null){
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("absenteeDate").as(Date.class),sendDate));
				}
				if (pmId != null && pmId > 0) {
					User u = userRepository.findOne(pmId);
					predicate.getExpressions().add(cb.equal(root.get("user").as(Long.class), pmId));
				}
				return predicate;
			}
		};
	}
	
	
	/*** by pzy
	 * 改为按照请假日期统计，如果按照请假起始日期统计 跨月的统计会有问题
	 * @param sstartDate
	 * @param sendDate
	 * @param pmId
	 * @return
	 */
	private Specification<AttenceDayoff> dayoffBuildSpecification(final Date sstartDate, final Date sendDate, final Long pmId){
		return new Specification<AttenceDayoff>() {
			@Override
			public Predicate toPredicate(Root<AttenceDayoff> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (sstartDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("dayoffDate").as(Date.class), sstartDate));
				}
				if (sendDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("dayoffDate").as(Date.class), sendDate));
				}
				if (pmId != null && pmId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(pmId)));
				}
				return predicate;
			}
		};
	}
	
	
	
}
