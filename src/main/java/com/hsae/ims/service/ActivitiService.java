package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.dto.ActivitiHistoryTaskDTO;
import com.hsae.ims.repository.UserRepository;
@Service
public class ActivitiService {
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private UserRepository userRepository;
	/***
	 * 获取流程轨迹
	 * @param pid
	 * @return
	 */
	public List<ActivitiHistoryTaskDTO> findHistoryTask(String pid){
		List<ActivitiHistoryTaskDTO> activitiHistoryTasks= new ArrayList<ActivitiHistoryTaskDTO>();
		
		List<HistoricActivityInstance> historicActivityInstances = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(pid).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		
		for(HistoricActivityInstance historicActivityInstance: historicActivityInstances){
			if(!"start".equals(historicActivityInstance.getActivityId())){
				ActivitiHistoryTaskDTO activitiHistoryTask=new ActivitiHistoryTaskDTO();
				activitiHistoryTask.setEndTime(historicActivityInstance.getEndTime());
				activitiHistoryTask.setStartTime(historicActivityInstance.getStartTime());
				activitiHistoryTask.setName(historicActivityInstance.getActivityName());
				/**获取审批意见以及流程描述*/
				if (StringUtils.isNotBlank(historicActivityInstance.getTaskId())){
					//activitiHistoryTask.setName( processEngine.getHistoryService().createHistoricTaskInstanceQuery().
						//	taskId(historicActivityInstance.getTaskId()).singleResult().getDescription());
					
					List<Comment> commentList = processEngine.getTaskService().getTaskComments(historicActivityInstance.getTaskId());
					if (commentList.size()>0){
						activitiHistoryTask.setApproves(commentList.get(0).getFullMessage());
					}
				}
				/**获取执行者*/
				if(!StringUtils.isBlank(historicActivityInstance.getAssignee()))
					activitiHistoryTask.setUser(userRepository.findOne(Long.valueOf(historicActivityInstance.getAssignee())));
				activitiHistoryTasks.add(activitiHistoryTask);
			}
		}
		
		return activitiHistoryTasks;

	}
	
}
