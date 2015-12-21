package com.hsae.ims.dto;

import java.util.List;

import com.hsae.ims.entity.WorkFlowOverTimeDetail;

public class WorkflowOverTimeDTO {
	private List<WorkFlowOverTimeDetail > workFlowOverTimeDetails;
	private Long leader;
	
	public List<WorkFlowOverTimeDetail> getWorkFlowOverTimeDetails() {
		return workFlowOverTimeDetails;
	}
	public void setWorkFlowOverTimeDetails(
			List<WorkFlowOverTimeDetail> workFlowOverTimeDetails) {
		this.workFlowOverTimeDetails = workFlowOverTimeDetails;
	}
	public Long getLeader() {
		return leader;
	}
	public void setLeader(Long leader) {
		this.leader = leader;
	}
}
