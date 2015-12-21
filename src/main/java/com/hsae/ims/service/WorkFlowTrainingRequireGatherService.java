package com.hsae.ims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.WorkFlowTrainingRequireGatherRepository;

@Service
public class WorkFlowTrainingRequireGatherService {

	@Autowired
	private WorkFlowTrainingRequireGatherRepository workFlowTrainingRequireGatherRepository;
	
	public List<WorkFlowTrainingRequireGather> findAll(){
		return (List<WorkFlowTrainingRequireGather>) workFlowTrainingRequireGatherRepository.findAll();
	}
	
	public WorkFlowTrainingRequireGather findOne(Long id){
		return workFlowTrainingRequireGatherRepository.findOne(id);
	}
	
	public void save(WorkFlowTrainingRequireGather tr){
		workFlowTrainingRequireGatherRepository.save(tr);
	}
	
	public Map<String, Object> delete(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		//TODO
		
		return map;
	}

	public List<WorkFlowTrainingRequireGather> findByYear(Integer year) {
		return workFlowTrainingRequireGatherRepository.findByYear(year);
	}

	public WorkFlowTrainingRequireGather findByWfentry(Wfentry wfentry) {
		List<WorkFlowTrainingRequireGather> trList = workFlowTrainingRequireGatherRepository.findByWfentry(wfentry);
		if (trList != null && trList.size() > 0) {
			return trList.get(0);
		}
		return null;
	}
}
