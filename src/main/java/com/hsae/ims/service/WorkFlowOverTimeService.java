package com.hsae.ims.service;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.dto.OverTimeMaxHour;
import com.hsae.ims.entity.Position;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.entity.WorkFlowOverTime;
import com.hsae.ims.entity.WorkFlowOverTimeDetail;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.PositionRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.repository.WorkFlowOverTimeRepository;
import com.osworkflow.SpringWorkflow;
@Service
public class WorkFlowOverTimeService {
	@Autowired
	private WorkFlowOverTimeRepository workFlowOverTimeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private  SpringWorkflow springWorkflow;
	
	@Autowired
	private UserResumeRepository userResumeRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	public WorkFlowOverTime findOne(Long id){
		return workFlowOverTimeRepository.findOne(id);
	}
	
	public void delete(Long id){
		 workFlowOverTimeRepository.delete(id);
	}
	
	public void save(WorkFlowOverTime workFlowOverTime){
		workFlowOverTimeRepository.save(workFlowOverTime);
	}
	
	public void updatePosition(WorkFlowOverTime workFlowOverTime){
		for(WorkFlowOverTimeDetail bean:workFlowOverTime.getWorkFlowOverTimeDetail()){
			UserResume userResume=userResumeRepository.findUserResumeByUser(userRepository.getUserById(workFlowOverTime.getUser().getId()));
			Position position = positionRepository.findOne((long)userResume.getPosition());
			bean.setPosition(position == null ? "" : position.getName());
		}
	}
	
	/***
	 * 根据流程ID查找
	 * @param workFlowId
	 * @return
	 */
	public WorkFlowOverTime findByWfentry(Wfentry wfentry){
		return workFlowOverTimeRepository.findByWfentry(wfentry);
	}
	/***TODO 取职位
	 * 计算某人某月份的加班时间已经最大加班时间
	 * @param userid
	 * @param overTimeDate
	 * @return
	 */
	public OverTimeMaxHour findMaxOverTime(Long userid,Date overTimeDate){
		UserResume userResume=userResumeRepository.findUserResumeByUser(userRepository.getUserById(userid));
		OverTimeMaxHour overTimeMaxHour = new OverTimeMaxHour();
		final Date startDate;
		final Date endDate;
		/*如果是主任工程师，按年计算*/
		if(userResume!=null&&userResume.getPosition()<=6){
			overTimeMaxHour.setType("2");
			overTimeMaxHour.setMaxHours(40D);
			startDate=DateUtils.truncate(overTimeDate,  Calendar.YEAR);
		}else{
			overTimeMaxHour.setType("1");
			overTimeMaxHour.setMaxHours(18D);
			startDate=DateUtils.truncate(overTimeDate,  Calendar.MONTH);
		}
		endDate=DateUtils.addMonths( DateUtils.truncate(overTimeDate,  Calendar.MONTH), 1);
		Double  nowHours=workFlowOverTimeRepository.findTotalHours(startDate, endDate,userid);
		overTimeMaxHour.setNowHours(nowHours);
		return overTimeMaxHour;
	}
	/**
	 * 查询加班申请
	 * @return
	 */
	public Page<WorkFlowOverTime> findAll(final int pageNumber, final int pageSize,
									final Date begin,final Date end,final Integer state, final Long user){
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "applyDate"));
		Specification<WorkFlowOverTime> spec = new Specification<WorkFlowOverTime>() {
				@Override
				public Predicate toPredicate(Root<WorkFlowOverTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (begin != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("applyDate").as(Date.class),begin));
					}
					if (end != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("applyDate").as(Date.class),end));
					}
					if (state != null) {
						predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class),state));
					}
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(user)));
					return predicate;
				}
			};
		Page<WorkFlowOverTime> result = (Page<WorkFlowOverTime>) workFlowOverTimeRepository.findAll(spec, pageRequest);
		wrapStepName(result);
		return result;
	}
	/***
	 * 包装stepname
	 * @param workFlowSteps
	 */
	private void wrapStepName(Page<WorkFlowOverTime>  result){
		for(WorkFlowOverTime workFlowOverTime:result.getContent()){
			if(workFlowOverTime.getWfentry()!=null&&workFlowOverTime.getWfentry().getCurrentStep()!=null)
				workFlowOverTime.setStep(springWorkflow.getWorkflowDescriptor(workFlowOverTime.getWfentry().getName()).
					getStep(workFlowOverTime.getWfentry().getCurrentStep().getStepId()).getName());
		}
	}
}
