package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.TrainingRequirement;
import com.hsae.ims.repository.TrainingRequirementRepository;

@Service
public class TrainingRequirementService {

	@Autowired
	private TrainingRequirementRepository trainingRequirementRepository;
	
	public List<TrainingRequirement> findAll(){
		return (List<TrainingRequirement>) trainingRequirementRepository.findAll();
	}
	
	public TrainingRequirement findOne(Long id){
		return trainingRequirementRepository.findOne(id);
	}
	
	public void save(TrainingRequirement tr){
		trainingRequirementRepository.save(tr);
	}
	
	public Map<String, Object> delete(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		TrainingRequirement tr = trainingRequirementRepository.findOne(id);
		//不是新建状态不能删除操作
		if (tr.getState() != 0) {
			map.put("success", "0");
			map.put("msg", "流程正在流转中不能删除！");
			return map;
		}
		trainingRequirementRepository.delete(id);
		map.put("success", "1");
		map.put("msg", "删除成功！");
		return map;
	}

	public List<TrainingRequirement> findByYear(Integer year) {
		return trainingRequirementRepository.findByYear(year);
	}

	public TrainingRequirement findByOsWorkflow(Long wfentryId) {
		List<TrainingRequirement> trList = trainingRequirementRepository.findByOsWorkflow(wfentryId);
		if (trList != null && trList.size() > 0) {
			return trList.get(0);
		}
		return null;
	}
}
