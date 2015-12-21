package com.hsae.ims.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.DailyReportWorkStage;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.DailyReportWorkStageRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class DailyReportWorkStageService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DailyReportWorkStageRepository dailyReportWorkStageRepository;

	/**
	 * 获取工作阶段
	 * @param cuid
	 * @return
	 */
	public Map<Integer, String> findWorkStage(Long cuid) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<DailyReportWorkStage> stageList = null;
		User user = userRepository.findOne(cuid);
		int workStage = user.getWorkStage();
		if (workStage == 0) {
			//按部门取
			Deptment dept = user.getDept();
			if (dept != null) {
				stageList = dailyReportWorkStageRepository.findByDeptId(dept.getId());
			}
		}else{
			//直接按值取
			stageList = dailyReportWorkStageRepository.findByType(user.getWorkStage());
		}
		if (stageList != null && stageList.size() > 0) {
			for(DailyReportWorkStage stage : stageList){
				map.put(stage.getId(), stage.getStageName());
			}
		}
		return map;
	}
	public Map<Integer, String> findWorkStage() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		dailyReportWorkStageRepository.findAll();
		Iterable<DailyReportWorkStage> stageList = dailyReportWorkStageRepository.findAll();
		for (DailyReportWorkStage stage : stageList) {
			map.put(stage.getId(), stage.getStageName());
		}
		return map;
	}
}
