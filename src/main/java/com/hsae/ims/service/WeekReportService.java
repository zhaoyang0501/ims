package com.hsae.ims.service;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.WeekReportRepository;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.query.Expression;
import com.opensymphony.workflow.query.FieldExpression;
import com.opensymphony.workflow.query.NestedExpression;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.osworkflow.SpringWorkflow;
@Service
public class WeekReportService {
	@Autowired
	WeekReportRepository   weekReportRepository;
	@Autowired
	SpringWorkflow springWorkflow;
	@Autowired
	UserRepository userRepository;
	public WeekReport findByOsworkflow(Long osworkflowid){
		return weekReportRepository.findByOsworkflow(osworkflowid);
	}
	public WeekReport getById(Long id){
		return weekReportRepository.findOne(id);
	}
	public void saveWeekReport(WeekReport weekReport){
		weekReportRepository.save(weekReport);
	}
	public List<WeekReport> findByWeek(Long weekNum){
		return weekReportRepository.findByWeek(weekNum);
	}
	public WeekReport findByWeekAndUser(Long userId, Long weekId){
		List<WeekReport> weekReports=weekReportRepository.findByWeekAndUser(userId, weekId);
		if(weekReports!=null&&weekReports.size()!=0)
			return weekReports.get(0);
		else return null;
	}
	/***
	 * 找待审批列表
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws WorkflowException 
	 */
	public Page<WeekReport> findWeekReportToApprove(Long userId, int pageNumber, int pageSize) throws WorkflowException {
		Specification<WeekReport> spec = buildWhereClause(findWorkflows(userId, FieldExpression.CURRENT_STEPS,"Queued"),userId);
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Page<WeekReport> weekReport = weekReportRepository.findAll(spec, pageRequest);
		return weekReport;
	}
	/***
	 * 找已审批列表
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws WorkflowException 
	 */
	public Page<WeekReport> findWeekReportApproved(Long userId, int pageNumber, int pageSize) throws WorkflowException {
		Specification<WeekReport> spec = buildWhereClause(findWorkflows(userId, FieldExpression.HISTORY_STEPS,"Finished"),userId);
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Page<WeekReport> weekReport = weekReportRepository.findAll(spec, pageRequest);
		return weekReport;
	}
	/***
	 * 获取待办数量
	 * @param userid
	 * @return
	 * @throws WorkflowException
	 */
	public Integer getToApproveCount(Long userid) throws WorkflowException{
		return findWorkflows(userid,FieldExpression.CURRENT_STEPS,"Queued").size();
	}
	
	/***
	 * 周报查询功能 查询周报
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param state
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws WorkflowException
	 */
	public Page<WeekReport> findWeekReport(final Date startDate,final Date endDate,final Long user,final Integer state, final String scope,int pageNumber, int pageSize) throws WorkflowException {
		Specification<WeekReport> spec = new Specification<WeekReport>() {
			@Override
			public Predicate toPredicate(Root<WeekReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (scope!=null) {
					predicate.getExpressions().add(cb.like(root.get("creater").get("authorityCode").as(String.class), scope+"%"));
					}
				if (startDate!=null) 
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class),startDate));
				if(endDate!=null)
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate").as(Date.class),endDate));
				if(user!=null)
					predicate.getExpressions().add(cb.equal(root.get("creater").as(User.class), userRepository.findOne(user)));
				if(state!=null)
					predicate.getExpressions().add(cb.equal(root.get("state").as(Integer.class),state));
				return predicate;
			}
		};
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Page<WeekReport> weekReport = weekReportRepository.findAll(spec, pageRequest);
		return weekReport;
	}
	/***
	 * 找某人的日志 流程实例LIST
	 * type=CURRENT_STEPS 待办事项
	 * type=HISTORY_STEPS 已审批
	 * @param user
	 * @param caller
	 * @return
	 * @throws WorkflowException
	 */
	@SuppressWarnings("unchecked")
	private List<Long> findWorkflows(Long userid,Integer type,String state) throws WorkflowException{
		springWorkflow.SetContext(userid.toString());
		
		Expression queryLeft = new FieldExpression( 
				  FieldExpression.OWNER,  
				  type,  
				  FieldExpression.EQUALS, userid);
		
		Expression queryMiddle = new FieldExpression( 
				  FieldExpression.NAME,  
				  FieldExpression.ENTRY,  
				  FieldExpression.EQUALS, 
				  WorkFlowConstants.DAILY_REPORT); 
		
		Expression queryRight = new FieldExpression( 
				  FieldExpression.STATUS,  
				  type,  
				  FieldExpression.EQUALS, 
				  state); 
		
		return springWorkflow.query(  new WorkflowExpressionQuery( 
				  new NestedExpression(new Expression[] {queryLeft,queryMiddle, queryRight}, 
				  NestedExpression.AND)));
	}
	private Specification<WeekReport> buildWhereClause(final List<Long> workflows ,final Long user) {
		return new Specification<WeekReport>() {
			@Override
			public Predicate toPredicate(Root<WeekReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (workflows.size()!=0) {
					predicate.getExpressions().add(root.get("osworkflow").in(workflows));
				}else{
					predicate.getExpressions().add(cb.notEqual(root.get("id").as(Long.class),root.get("id").as(Long.class)));
				}
				return predicate;
			}
		};
	}
	
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}
