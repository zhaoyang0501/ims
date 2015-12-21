package com.hsae.ims.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hsae.ims.dto.AttenceStaticsDTO;
import com.hsae.ims.dto.AttenceStaticsDetailsDTO;
import com.hsae.ims.dto.EchartDto;
import com.hsae.ims.entity.AttenceStatistics;
import com.hsae.ims.repository.AttenceBrushRdcordRepository;
import com.hsae.ims.repository.AttenceStatisticsRepository;
import com.hsae.ims.repository.HomeRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class HomeService {
	@Autowired
	private HomeRepository homeRepository;
	
	@Autowired
	private AttenceOverTimeService attenceOverTimeService;
	
	@Autowired
	private AttenceLeakService attenceLeakService;
	
	@Autowired
	private AttenceStatisticsRepository attenceStatisticsRepository;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private AttenceBrushRdcordRepository attenceBrushRdcordRepository;
	
	@Autowired
	private UserRepository userRepository;
	/**
	 * 查询日报类型分布图
	 * @return
	 */
	public List<Map<String, Object>> queryDailyReportTypePieList(String fromDate, String toDate, Long cuid){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		Map<String, String> typeMap = codeService.findDailyTypeCode();
		Set<String> set = typeMap.keySet();
		//数据分布表
		List<Object[]> dataList = homeRepository.findDailyReportTypeStatics(fromDate, toDate, cuid);
		for(Iterator<String> iter = set.iterator(); iter.hasNext();)
		{
			map = new HashMap<String, Object>();
			String key = (String)iter.next();
			if (dataList != null && dataList.size() > 0) {
				for (Object[] obj : dataList) {
					String type = (String) obj[0];
					Double hours = (Double) obj[1];
					if (key.equals(type)) {
						map.put("name", typeMap.get(key));
						map.put("value", hours);
						list.add(map);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取用户的个人考勤统计
	 * @param cuid
	 * @param currDate
	 * @return
	 */
	public AttenceStaticsDTO queryAttenceStatics(Long cuid) {
		AttenceStaticsDTO dto = new AttenceStaticsDTO();
		List<Object[]> list = homeRepository.findLatestAttenceStatics(cuid);
		if (list != null && list.size() >0) {
			Object[] obj = list.get(0);
			dto.setLastmonthRemain(obj[0]==null?"0":obj[0].toString());
			dto.setCurrentmonthIncrease(obj[1]==null?"0":obj[1].toString());
			dto.setCurrentmonthMiuns(obj[2]==null?"0":obj[2].toString());
			dto.setHours(obj[3]==null?"0":obj[3].toString());
			dto.setStartDate(obj[5]==null?"0":obj[5].toString());
			dto.setEndDate(obj[6]==null?"0":obj[6].toString());
		}else{
			dto.setLastmonthRemain("0");
			dto.setCurrentmonthIncrease("0");
			dto.setCurrentmonthMiuns("0");
			dto.setHours("0");
			dto.setStartDate("0");
			dto.setEndDate("0");
		}
		return dto;
	}

	public List<AttenceStaticsDetailsDTO> queryAttenceStaticsDetails(int pageNumber, int pageSize, String fromDate, String toDate, String type, Long cuid) {
		List<AttenceStaticsDetailsDTO> list = new ArrayList<AttenceStaticsDetailsDTO>();
		AttenceStaticsDetailsDTO dto = null;
		List<Object[]> dataList = homeRepository.queryAttenceStaticsDetails(pageNumber, pageSize, fromDate, toDate, type, cuid);
		if (dataList != null && dataList.size() > 0) {
			int index = 1;
			for(Object[] obj : dataList){
				dto = new AttenceStaticsDetailsDTO();
				dto.setIndex(index);
				dto.setType(obj[0] == null?"":obj[0].toString());
				dto.setDate(obj[1] == null?"":obj[1].toString());
				dto.setDetails(obj[2] == null?"":obj[2].toString());
				dto.setDescription(obj[3] == null?"":obj[3].toString());
				index ++;
				list.add(dto);
			}
		}
		return list;
	}
	public AttenceStatistics findAttenceStatic(Long user,String yyyyMM){
		List<AttenceStatistics> list=attenceStatisticsRepository.findByMonthAndUser(yyyyMM,userRepository.findOne(user));
		return (list==null||list.size()==0)?null:list.get(0);
	}
	/***
	 * 我的考勤获取每个月考勤明细
	 * @param user
	 * @param yyyyMM
	 * @return
	 * @throws ParseException 
	 */
	public List<EchartDto> findUserAttenceCount(Long user,String yyyyMM) throws ParseException{
		Assert.notNull(yyyyMM);
		Date start=DateUtils.parseDate(yyyyMM, "yyyy-MM");
		Date end= DateUtils.addMonths(start, 1) ;
		return attenceBrushRdcordRepository.findUserAttenceCount(user, start, end);
	}
}
