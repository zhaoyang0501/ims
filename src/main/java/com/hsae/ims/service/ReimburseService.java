package com.hsae.ims.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
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
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseCustomerDetail;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.AttenceOverTimeRepository;
import com.hsae.ims.repository.DailyReportRepository;
import com.hsae.ims.repository.ReimburseCustomerDetailRepository;
import com.hsae.ims.repository.ReimburseDetailsRepository;
import com.hsae.ims.repository.ReimburseRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.RightUtil;

@Service
public class ReimburseService {
	
	@Autowired
	private DailyReportRepository dailyReportRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReimburseRepository reimburseRepository;
	
	@Autowired
	private ReimburseDetailsRepository reimburseDetailsRepository;
	
	@Autowired
	private AttenceOverTimeRepository attenceOverTimeRepository;
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	ReimburseCustomerDetailRepository reimburseCustomerDetailRepository;
	
	/**
	 * 报销单查询，不包含未提交的报销单
	 * @param pageNumber
	 * @param pageSize
	 * @param begin
	 * @param end
	 * @param reimburser
	 * @return
	 */
	public Page<Reimburse> findAll(final int pageNumber, final int pageSize, final Date begin,final Date end, final Long reimburser,final Integer type,final Integer step){
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "reimburseDate"));
		Specification<Reimburse> spec = new Specification<Reimburse>() {
			@Override
			public Predicate toPredicate(Root<Reimburse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
			if (begin != null) {
				predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reimburseDate").as(Date.class),begin));
			}
			if (end != null) {
				predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reimburseDate").as(Date.class),end));
			}
			if (type != null) {
				predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
			}
			
			if (step != null&&step!=6) {
				predicate.getExpressions().add(cb.equal(root.get("wfentry").get("currentStep").get("stepId").as(Integer.class),step));
			}
			
			if (step != null&&step==6) {
				predicate.getExpressions().add(cb.equal(root.get("state").as(Integer.class), 4));
			}
			if (reimburser != null && reimburser > 0) {
				predicate.getExpressions().add(cb.equal(root.get("reimburser").as(User.class), userRepository.findOne(reimburser)));
			}
			
			predicate.getExpressions().add(cb.notEqual(root.get("state").as(Integer.class), 0));	//去除未提交的报销
				return predicate;
			}
		};
		
		Page<Reimburse> result = (Page<Reimburse>) reimburseRepository.findAll(spec, pageRequest);
		setUserDetail(result);
		setReimburseCustomerDetails(result);
		return result;
		}
	
	/**
	 * 查询报销
	 * @return
	 */
	public Page<Reimburse> findAll(final int pageNumber, final int pageSize,
									final Date begin,final Date end,final Integer state, final Long reimburser){
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "reimburseDate"));
		Specification<Reimburse> spec = new Specification<Reimburse>() {
				@Override
				public Predicate toPredicate(Root<Reimburse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (begin != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reimburseDate").as(Date.class),begin));
					}
					if (end != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reimburseDate").as(Date.class),end));
					}
					if (state != null) {
						predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class),state));
					}
					if (reimburser != null && reimburser > 0) {
						predicate.getExpressions().add(cb.equal(root.get("reimburser").as(User.class), userRepository.findOne(reimburser)));
					}
					return predicate;
				}
			};
		
		Page<Reimburse> result = (Page<Reimburse>) reimburseRepository.findAll(spec, pageRequest);
		setUserDetail(result);
		setReimburseCustomerDetails(result);
		return result;
	}
	/**
	 * 保存报销
	 * @param reimburseDate
	 * @param type
	 * @param number
	 * @param userIds
	 * @param remark
	 * @return
	 * @throws ParseException 
	 */
	@Transactional
	public void save(String reimburseDate, Integer dinnerType, Integer number, String userIds, String remark) throws ParseException {
		Reimburse oTreimburse = new Reimburse();
		oTreimburse.setNumber(number);
		oTreimburse.setReimburseMoney(number * ImsConstants.reimburse.STANDARD);
		oTreimburse.setReimburseDate(DateUtils.parseDateStrictly(reimburseDate, "yyyy-MM-dd"));
		oTreimburse.setType(dinnerType);
		oTreimburse.setRemark(remark);
		oTreimburse.setReimburser(userRepository.findOne( RightUtil.getCurrentUserId()));
		reimburseRepository.save(oTreimburse);
		if (userIds != null && userIds.length() > 0) {
			String[] userIdArray = userIds.split(",");
			for (int i = 0; i < userIdArray.length; i++) {
					ReimburseDetails details = new ReimburseDetails();
					details.setUser(userRepository.findOne(Long.parseLong(userIdArray[i])));
					details.setReimburse(oTreimburse);
					reimburseDetailsRepository.save(details);
			}
		}
		
	}
	/***
	 * 删除某报销单下的内部员工
	 * @param reimburse
	 */
	public void deleteReimburseDetails(Reimburse reimburse){
		reimburseDetailsRepository.deleteByReimburse(reimburse);
	}
	/***
	 * 删除某报销单下的外部人员
	 * @param reimburse
	 */
	public void deleteCustomerReimburseDetails(Reimburse reimburse){
		this.reimburseCustomerDetailRepository.deleteByReimburse(reimburse);
	}
	public void deleteReimburseDetails(List<ReimburseDetails> details){
		if(details!=null){
			for(ReimburseDetails bean:details){
				reimburseDetailsRepository.delete(bean.getId());
			}
		}
	}
	public void saveDetails(String userIds,Reimburse reimburse){
		if (userIds != null && userIds.length() > 0) {
			String[] userIdArray = userIds.split(",");
			for (int i = 0; i < userIdArray.length; i++) {
					ReimburseDetails details = new ReimburseDetails();
					details.setUser(userRepository.findOne(Long.parseLong(userIdArray[i])));
					details.setReimburse(reimburse);
					reimburseDetailsRepository.save(details);
			}
		}
	}
	public void save(Reimburse reimburse){
		reimburseRepository.save(reimburse);
	}
	/***
	 * 保存一个报销，并级联保存相关加班人
	 * @param reimburse
	 * @param userIds
	 */
	@Transactional
	public void save(Reimburse reimburse,String userIds){
		reimburseRepository.save(reimburse);
		/**级联保存非本单位员工加班记录**/
		if(reimburse.getReimburseCustomerDetails()!=null){
			for(ReimburseCustomerDetail reimburseCustomerDetail:reimburse.getReimburseCustomerDetails()){
				reimburseCustomerDetail.setReimburse(reimburse);
				reimburseCustomerDetailRepository.save(reimburseCustomerDetail);
			}
		}
		reimburseCustomerDetailRepository.save(reimburse.getReimburseCustomerDetails());
		if (userIds != null && userIds.length() > 0) {
			String[] userIdArray = userIds.split(",");
			for (int i = 0; i < userIdArray.length; i++) {
					ReimburseDetails details = new ReimburseDetails();
					details.setUser(userRepository.findOne(Long.parseLong(userIdArray[i])));
					details.setReimburse(reimburse);
					reimburseDetailsRepository.save(details);
			}
		}
		/**更新报销单的人数及总价*/
		int reimburseCustomerDetailNumber=reimburse.getReimburseCustomerDetails()==null?0:reimburse.getReimburseCustomerDetails().size();
		int reimburseEmployeeNumber=userIds==null?0:userIds.split(",").length;
		Reimburse reimburseNew = reimburseRepository.findOne(reimburse.getId());
		reimburseNew.setNumber(reimburseCustomerDetailNumber+reimburseEmployeeNumber);
		reimburseNew.setReimburseMoney(reimburse.getNumber()*ImsConstants.reimburse.STANDARD);
		reimburseRepository.save(reimburseNew);
	}
	/***
	 * 删除一个报销，并级联删除子表
	 * @param id
	 */
	public void delete(Long id) {
		Reimburse reimburse = reimburseRepository.findOne(id);
		List<ReimburseDetails> details = reimburseDetailsRepository.findByReimburse(reimburse);
		if (details != null) {
			for (ReimburseDetails d : details) {
				reimburseDetailsRepository.delete(d);
			}
		}
		List<ReimburseCustomerDetail> customerDetails = reimburseCustomerDetailRepository.findByReimburse(reimburse);
		if (customerDetails != null && customerDetails.size() > 0) {
			for(ReimburseCustomerDetail d : customerDetails){
				reimburseCustomerDetailRepository.delete(d);
			}
		}
		reimburseRepository.delete(reimburse);
	}
	public Reimburse findOne(Long id){
		return reimburseRepository.findOne(id);
	}
	/***
	 * 根据流程ID查找
	 * @param workFlowId
	 * @return
	 */
	public Reimburse findByWfentry(Wfentry wfentry){
		return reimburseRepository.findByWfentry(wfentry);
	}
	
	public List<ReimburseCustomerDetail> findReimburseCustomerDetails(Reimburse reimburse){
		return reimburseCustomerDetailRepository.findByReimburse(reimburse);
	}
	
	/***
	 * 加班人员详细信息
	 * @param reimburse
	 * @return
	 */
	public List<ReimburseDetails> findReimburseDetails(Reimburse reimburse){
		List<ReimburseDetails> list = reimburseDetailsRepository.findByReimburse(reimburse);
		/**找日志*/
		for(ReimburseDetails bean:list){
			List<DailyReport> dailyReports = dailyReportRepository.
					findByReportDateAndUserAndType(reimburse.getReimburseDate(), bean.getUser(), "7");
			bean.setDailyReports(dailyReports);
		}
		
		/**找工时核对情况*/
		/***
		 * TODO
		 */
		for(ReimburseDetails bean:list){
			List<AttenceOverTime> attenceOverTimes =  attenceOverTimeRepository.
					findByUserAndOvertimeDate(bean.getUser(), reimburse.getReimburseDate());
			bean.setAttenceOverTimes(attenceOverTimes);
		}
		return list;
	}
	/***
	 * 餐费报销报表 按人员
	 * @param userid
	 * @return
	 */
	public List<Tuple>  findReimburseReportByUser(Integer year,Integer month){
		CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class); 
		Root<Reimburse> reimburseRoot = criteriaQuery.from(Reimburse.class);
		Predicate predicate = criteriaBuilder.conjunction();
		predicate.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.function("year", Integer.class, reimburseRoot.get("reimburseDate")),year));
		predicate.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.function("month", Integer.class, reimburseRoot.get("reimburseDate")),month));
		predicate.getExpressions().add(criteriaBuilder.equal(reimburseRoot.get("state").as(Integer.class),4));
		criteriaQuery.where(predicate);
		criteriaQuery.groupBy(reimburseRoot.get("reimburser"));
		criteriaQuery.orderBy(criteriaBuilder.desc(reimburseRoot.get("reimburser").get("dept").get("id")));
		criteriaQuery.select(criteriaBuilder.tuple(reimburseRoot.get("reimburser").alias("reimburser"),
				criteriaBuilder.sum(reimburseRoot.get("reimburseMoney").as(Double.class)).alias("sum")));
		TypedQuery<Tuple> q = entityManagerFactory.createEntityManager().createQuery(criteriaQuery);
		List<Tuple> result = q.getResultList();
		return result;
	}
	public List<Object[]> findReimburseReportByProject(Integer year,Integer month,Long projectId){
		EntityManager em = entityManagerFactory.createEntityManager();
		StringBuffer sb = new StringBuffer(
				  "SELECT t22.project_name,t11.reimburse_date,t11.type ,GROUP_CONCAT(t11.chinesename),COUNT(1),COUNT(1)*"+ImsConstants.reimburse.STANDARD+" ,"+ImsConstants.reimburse.STANDARD+" from "+
					"	("+
					"	SELECT t1.*,t3.project "+
					"	FROM ("+
					"	      SELECT  t1.id,t1.user, t3.chinesename,t2.reimburse_date ,t2.type"+
					"	      FROM ims_ot_reimbursedetails t1,ims_ot_reimburse  t2,ims_system_user  t3"+
					"	      WHERE t1.reimburse_id=t2.id AND t1.user=t3.id and t2.state=4 "+
					"	      ) t1"+
					"	      LEFT JOIN "+
					"	      ( "+
					"	    		SELECT  report_date, USER ,MAX(project) project FROM  ims_daily_report  WHERE TYPE=7 GROUP BY report_date, USER "+
					"	    	) t3 "+
					"		 ON t1.user=t3.user AND t1.reimburse_date=t3.report_date "+
					"	 UNION "+
					"	  SELECT t1.id, '' USER, t1.user_name chinesename,t2.reimburse_date, t2.type,t1.project_id project "+
					"	  FROM ims_ot_reimbursecustomerdetail t1 ,ims_ot_reimburse t2"+
					"	  WHERE t1.reimburse_id=t2.id and t2.state=4 "+
					"	) t11"+
					"	  LEFT JOIN ims_system_project t22"+
					"	  ON t11.project=t22.id"+
					"	  WHERE  year(t11.reimburse_date)=:year AND MONTH(t11.reimburse_date)=:month"+
					(projectId!=null?" and t22.id="+projectId :" ")+
					"	  GROUP BY t11.type,t11.reimburse_date,t11.project"+
					"	  ORDER BY t11.project, t11.reimburse_date ");
		System.out.print(sb);
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("year", year);
		query.setParameter("month", month);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}
	/***
	 * 根据工时情况 找加班明细
	 * @param user
	 * @param date
	 * @return
	 */
	public List<ReimburseDetails> findByUserAndDate(User user, Date date){
		return reimburseDetailsRepository.findByUserAndReimburseReimburseDate(user, date);
	}
	//~~~~~~~~~~~~private ~~~~~~~~~~~~~~~~~~~
	private void setUserDetail(Page<Reimburse> reimburses){
		for(Reimburse reimburse:reimburses.getContent()){
			reimburse.setDetails(reimburseDetailsRepository.findByReimburse(reimburse));
		}
	}
	//~~~~~~~~~~~~private ~~~~~~~~~~~~~~~~~~~
	private void setReimburseCustomerDetails(Page<Reimburse> reimburses){
		for(Reimburse reimburse:reimburses.getContent()){
			reimburse.setReimburseCustomerDetails(reimburseCustomerDetailRepository.findByReimburse(reimburse));
		}
	}
}
