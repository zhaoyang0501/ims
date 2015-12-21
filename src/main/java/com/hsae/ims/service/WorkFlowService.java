package com.hsae.ims.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.dto.StepDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Approval;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.HistoryStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.entity.osworkflow.WorkFlowStep;
import com.hsae.ims.repository.ReimburseRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.WorkFlowApprovalRepository;
import com.hsae.ims.repository.WorkFlowCurrentstepRepository;
import com.hsae.ims.repository.WorkFlowHistorystepRepository;
import com.hsae.ims.repository.WorkFlowWfentryExtendRepository;
import com.hsae.ims.repository.WorkFlowWfentryRepository;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.SpringWorkflow;
@Service
public class WorkFlowService {
	@Autowired
	private WorkFlowCurrentstepRepository workFlowCurrentstepRepository;
	@Autowired
	private WorkFlowHistorystepRepository workFlowHistorystepRepository;
	@Autowired
	private WorkFlowWfentryRepository workFlowWfentryRepository;
	@Autowired
	private  SpringWorkflow springWorkflow;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  WorkFlowApprovalRepository workFlowApprovalRepository;
	@Autowired
	private  WorkFlowWfentryExtendRepository workFlowWfentryExtendRepository;
	@Autowired
	private ReimburseRepository reimburseRepository;
	/***
	 * 找朱新军的待办事项
	 * @return
	 */
	public List<CurrentStep> findTodoList(final User user){
		Specification<CurrentStep> spec = new Specification<CurrentStep>() {
			@Override
			public Predicate toPredicate(Root<CurrentStep> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("owner").as(User.class), user));
				predicate.getExpressions().add(cb.notEqual(root.get("wfentry").get("state").as(Integer.class),  WorkflowEntry.KILLED));
				return predicate;
			}
		};
		PageRequest pageRequest = new  PageRequest(0, Integer.MAX_VALUE, new Sort(Direction.DESC, "startDate"));
		Page<CurrentStep> currentSteps = workFlowCurrentstepRepository.findAll(spec,pageRequest);
		return currentSteps.getContent();
	} 
	/***
	 * 找前5条 按照时间排序
	 * @param user
	 * @return
	 */
	public List<CurrentStep> findTop5TodoList(final User user){
		Specification<CurrentStep> spec = new Specification<CurrentStep>() {
			@Override
			public Predicate toPredicate(Root<CurrentStep> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("owner").as(User.class), user));
				predicate.getExpressions().add(cb.notEqual(root.get("wfentry").get("state").as(Integer.class),  WorkflowEntry.KILLED));
				return predicate;
			}
		};
		PageRequest pageRequest = new  PageRequest(0, 5, new Sort(Direction.DESC, "startDate"));
		Page<CurrentStep> currentSteps = workFlowCurrentstepRepository.findAll(spec, pageRequest);
		wrapStepName(currentSteps);
		wrapWorkFlowName(currentSteps);
		return currentSteps.getContent();
	}
	/***
	 * 查待办列表
	 * @param workFlowName
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws WorkflowException
	 */
	public Page<CurrentStep> findTodoList(final String workFlowName,final Date startDate,final Date endDate, final User user,final User creater,final String sn, int pageNumber, int pageSize) throws WorkflowException {
		Specification<CurrentStep> spec = new Specification<CurrentStep>() {
			@Override
			public Predicate toPredicate(Root<CurrentStep> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(user!=null)
					predicate.getExpressions().add(cb.equal(root.get("owner").as(User.class), user));
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("startDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("startDate").as(Date.class), DateUtils.addDays(endDate, 1)));
				}
				if (workFlowName!=null) {
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("name").as(String.class), workFlowName));
				}
				if(creater!=null){
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("wfentryExtend").get("creater").as(User.class), creater));
				}
				if(sn!=null){
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("wfentryExtend").get("sn").as(String.class), sn));
				}
				predicate.getExpressions().add(cb.notEqual(root.get("wfentry").get("state").as(Integer.class),  WorkflowEntry.KILLED));
				return predicate;
			}
		};
		PageRequest pageRequest = new  PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
		Page<CurrentStep> currentSteps = workFlowCurrentstepRepository.findAll(spec, pageRequest);
		wrapStepName(currentSteps);
		wrapWorkFlowName(currentSteps);
		wrapCreater(currentSteps);
		return currentSteps;
	}
	/***
	 * 查已办列表 
	 * 这可能是我写过的最复杂的JPA了，带子查询，带分组，带聚集...
	 * @param workFlowName
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws WorkflowException
	 */
	public Page<HistoryStep> findDoneList(final String workFlowName,final Date startDate,final Date endDate,final Integer workflowState, final User user,final String sn,final User creater,  int pageNumber, int pageSize) throws WorkflowException {
		Specification<HistoryStep> spec =new Specification<HistoryStep>() {
			@Override
			public Predicate toPredicate(Root<HistoryStep> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(user!=null)
					predicate.getExpressions().add(cb.equal(root.get("owner").as(User.class), user));
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("startDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("startDate").as(Date.class), DateUtils.addDays(endDate, 1)));
				}
				if (workFlowName!=null) {
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("name").as(String.class), workFlowName));
				}
				if (workflowState!=null) {
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class), workflowState));
				}
				if(creater!=null){
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("wfentryExtend").get("creater").as(User.class), creater));
				}
				if(sn!=null){
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("wfentryExtend").get("sn").as(String.class), sn));
				}
				Subquery<Long> subquery = query.subquery(Long.class);
				Root<HistoryStep> subroot = subquery.from(HistoryStep.class);
				Predicate subpredicate = cb.conjunction(); 
				subpredicate.getExpressions().add(cb.equal(root.get("wfentry"), subroot.get("wfentry")));
				subquery.select(cb.max(subroot.get("id").as(Long.class)) ).
				where(subpredicate).
				groupBy(subroot.get("wfentry").as(String.class),subroot.get("owner"));
				predicate.getExpressions().add(cb.in(root.get("id")).value(subquery));
				return predicate;
			}
		};
		PageRequest pageRequest = new  PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
		Page<HistoryStep> historySteps = workFlowHistorystepRepository.findAll(spec, pageRequest);
		wrapStepNameInHistoryStep(historySteps);
		wrapWorkFlowName(historySteps);
		wrapCreater(historySteps);
		return historySteps;
	}
	/***
	 * 根据工作流ID找工作流bean
	 * @param id
	 * @return
	 */
	public Wfentry findWfentry(Long id){
		return workFlowWfentryRepository.findOne(id);
	}
	/***
	 * 保存一个审批意见
	 * @param approvalstr
	 * @param step
	 */
	public void saveApproval(String approvalstr,Long stepid,String action){
		Approval approval = new Approval();
		approval.setApprovel(approvalstr);
		approval.setCreateDate(new Timestamp(System.currentTimeMillis()));
		HistoryStep  historyStep = workFlowHistorystepRepository.findOne(stepid);
		approval.setStep(historyStep);
		approval.setAction(action);
		workFlowApprovalRepository.save(approval);
	}
	/***
	 * 保存一个审批意见
	 * @param approvalstr
	 * @param step
	 */
	public void saveApproval(String approvalstr,Long stepid){
		Approval approval = new Approval();
		approval.setApprovel(approvalstr);
		approval.setCreateDate(new Timestamp(System.currentTimeMillis()));
		HistoryStep  historyStep = workFlowHistorystepRepository.findOne(stepid);
		approval.setStep(historyStep);
		workFlowApprovalRepository.save(approval);
	}
	/***
	 * 找指定的流程下的审批意见列表
	 * @param wfentryId
	 * @return
	 */
	public  List<Approval> findApproval(final Long wfentryId){
		Specification<Approval> spec = new Specification<Approval>() {
			@Override
			public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(wfentryId!=null)
					predicate.getExpressions().add(cb.equal(root.get("step").get("wfentry").get("id").as(Long.class), wfentryId));
				return predicate;
			}
		};
		 List<Approval> approvals = workFlowApprovalRepository.findAll(spec,new Sort(Direction.ASC, "createDate"));
		 wrapStepName(approvals);
		 return approvals;
	}
	/***
	 * 找指定的流程下的审批意见列表
	 * @param wfentryId
	 * @return
	 */
	public  List<Approval> findApproval(final Long wfentryId,final Integer stepid){
		Specification<Approval> spec = new Specification<Approval>() {
			@Override
			public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(wfentryId!=null)
					predicate.getExpressions().add(cb.equal(root.get("step").get("wfentry").get("id").as(Long.class), wfentryId));
				if(stepid!=null)
					predicate.getExpressions().add(cb.equal(root.get("step").get("stepId").as(Integer.class), stepid));
				return predicate;
			}
		};
		 List<Approval> approvals = workFlowApprovalRepository.findAll(spec,new Sort(Direction.ASC, "createDate"));
		 wrapStepName(approvals);
		 return approvals;
	}
	/***
	 * 找指定的流程下的审批意见列表
	 * @param wfentryId
	 * @return
	 */
	public  Approval findSingleApproval(final Long wfentryId,final Integer stepid){
		Specification<Approval> spec = new Specification<Approval>() {
			@Override
			public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(wfentryId!=null)
					predicate.getExpressions().add(cb.equal(root.get("step").get("wfentry").get("id").as(Long.class), wfentryId));
				if(stepid!=null)
					predicate.getExpressions().add(cb.equal(root.get("step").get("stepId").as(Integer.class), stepid));
				return predicate;
			}
		};
		 List<Approval> approvals = workFlowApprovalRepository.findAll(spec,new Sort(Direction.DESC, "createDate"));
		 wrapStepName(approvals);
		 return approvals.size()==0?null:approvals.get(0);
	}
	/***
	 *返回osworkflow流程XML文件的字符串表现形式，前台显示流程图用
	 * @param workflowName workflowName 必须是 WorkFlowConstants中的某个值
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public String getWorkflowXMlAsString(String workflowName) throws DocumentException, IOException{
			Resource resource = new ClassPathResource("osworkflow/"+workflowName+".xml");
			SAXReader reader = new SAXReader();
			reader.setValidation(false);
			reader.setEntityResolver(new EntityResolver () {  
				     public InputSource resolveEntity(String publicId, String systemId)  
				       throws SAXException, IOException {  
				            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='utf-8'?>".getBytes()));  
				     }  
				});  
			Document document = reader.read(resource.getFile());
		    Element root = document.getRootElement();
		    root.element("step");
		    return root.asXML();
	}
	/***
	 *返回osworkflow流程布局XML文件的字符串表现形式，前台显示流程图用
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public String getWorkflowXMlLayOutAsString(String workflowName) throws DocumentException, IOException{
		Resource resource = new ClassPathResource("osworkflow/layout/"+workflowName+".xml");
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		reader.setEntityResolver(new EntityResolver () {  
			     public InputSource resolveEntity(String publicId, String systemId)  
			       throws SAXException, IOException {  
			            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='utf-8'?>".getBytes()));  
			     }  
			});  
		Document document = reader.read(resource.getFile());
	    Element root = document.getRootElement();
	    root.element("step");
	    return root.asXML();
	}
	/***
	 * 获取工作流历史step 工作流图形用
	 * @param wfentry
	 * @return
	 */
	public List<StepDTO> findHistoryStepsDTO(Wfentry wfentry){
		List<StepDTO> historyStepDTOs=new ArrayList<StepDTO>();
		List<HistoryStep> historySteps= workFlowHistorystepRepository.findByWfentry(wfentry);
		List<Approval> approvals=findApproval(wfentry.getId());
		for(HistoryStep HistoryStep:historySteps){
			StepDTO historyStepDTO= new StepDTO();
			historyStepDTO.setUserName(HistoryStep.getOwner().getChinesename());
			historyStepDTO.setStartDate(HistoryStep.getStartDate());
			historyStepDTO.setFinishDate(HistoryStep.getFinishDate());
			historyStepDTO.setAction(springWorkflow.getWorkflowDescriptor(wfentry.getName()).getAction(HistoryStep.getAction_id()).getName());
			historyStepDTO.setStepid(HistoryStep.getStepId());
			for(Approval approvel:approvals){
				if(approvel.getStep().getStepId()==HistoryStep.getStepId())
					historyStepDTO.setApprove(approvel.getApprovel());
			}
			historyStepDTOs.add(historyStepDTO);
		}
		return historyStepDTOs;
	}
	
	/***
	 * 获取工作流当前step 工作流图形用
	 * @param wfentry
	 * @return
	 */
	public List<StepDTO> findCurrentStepsDTO(Wfentry wfentry){
		List<StepDTO> currentStepDTOs=new ArrayList<StepDTO>();
		List<CurrentStep> currentSteps= this.workFlowCurrentstepRepository.findByWfentry(wfentry);
		for(CurrentStep currentStep:currentSteps){
			StepDTO stepDTO= new StepDTO();
			stepDTO.setUserName(currentStep.getOwner().getChinesename());
			stepDTO.setStartDate(currentStep.getStartDate());
			stepDTO.setFinishDate(currentStep.getFinishDate());
			stepDTO.setStepid(currentStep.getStepId());
			currentStepDTOs.add(stepDTO);
		}
		return currentStepDTOs;
	}
	
	public void saveWfentryExtend(WfentryExtend wfentryExtend){
		this.workFlowWfentryExtendRepository.save(wfentryExtend);
	}
	public User getCurrentstepUser(Wfentry wfentry){
		List<CurrentStep> steps=workFlowCurrentstepRepository.findByWfentry(wfentry);
		if(steps!=null&&steps.size()>0){
			CurrentStep step=steps.get(0);
			return step.getOwner();
		}
		return null;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~private Method
	/***
	 * 包装stepname
	 * @param workFlowSteps
	 */
	private void wrapStepName(List<Approval> approvals){
		for(Approval approval:approvals){
			String wfentryName = approval.getStep().getWfentry().getName();
			Integer stepId=approval.getStep().getStepId();
			approval.getStep().setName(springWorkflow.getWorkflowDescriptor(wfentryName).getStep(stepId).getName());
		}
	}
	/***
	 * 包装stepname
	 * @param workFlowSteps
	 */
	private void wrapStepName(Page<? extends WorkFlowStep> workFlowSteps){
		for(WorkFlowStep workFlowStep:workFlowSteps.getContent()){
			workFlowStep.setName(springWorkflow.getWorkflowDescriptor(workFlowStep.getWfentry().getName()).getStep(workFlowStep.getStepId()).getName());
		}
	}
	/***
	 * 包装历史记录中的stepname
	 * @param workFlowSteps
	 */
	private void wrapStepNameInHistoryStep(Page<? extends WorkFlowStep> workFlowSteps){
		for(WorkFlowStep workFlowStep:workFlowSteps.getContent()){
			if(workFlowStep.getWfentry().getCurrentStep()!=null){
				Integer stepId=workFlowStep.getWfentry().getCurrentStep().getStepId();
				String stepName = springWorkflow.getWorkflowDescriptor(workFlowStep.getWfentry().getName()).getStep(stepId).getName();
				workFlowStep.setName(stepName);
			}
		}
	}
	private void wrapCreater(Page<? extends WorkFlowStep> workFlowSteps){
		for(WorkFlowStep workFlowStep:workFlowSteps.getContent()){
			Assert.notNull(workFlowStep.getWfentry());
			Long createrId=springWorkflow.getPropertySet(workFlowStep.getWfentry().getId()).getLong("creater");
			if(createrId!=null)
				workFlowStep.setCreater(userRepository.findOne(createrId));
		}
	}
	/***
	 * 包装工作流名称
	 * @param workFlowSteps
	 */
	private void wrapWorkFlowName(Page<? extends WorkFlowStep> workFlowSteps){
		for(WorkFlowStep workFlowStep:workFlowSteps.getContent()){
			Assert.notNull(workFlowStep.getWfentry());
			String flowName=WorkFlowConstants.WORKFLOWNAME_MAP.get(workFlowStep.getWfentry().getName());
			if(flowName!=null)
				workFlowStep.getWfentry().setName(flowName);
		}
	}
	
}
