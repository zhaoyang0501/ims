package com.hsae.ims.service;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.DailyReportWeekConfig;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.DailyReportRepository;
import com.hsae.ims.repository.ProjectRepository;
import com.hsae.ims.repository.UserRepository;
@Service
public class DailyReportService {
	@Autowired
	private DailyReportRepository dailyReportRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	
	public List<DailyReport> findDailyReportByMonth(Date start, Date end, long userId) {
		return dailyReportRepository.findDailyReportByMonth(start, end, userId);
	}
	public List<DailyReport> findByReportDateAndUser(Date date, User user) {
		return dailyReportRepository.findByReportDateAndUser(date, user);
	}
	public DailyReport saveDailyReport(DailyReport model) {
		return dailyReportRepository.save(model);
	}
	public void deleteDailyReport(DailyReport model) {
		dailyReportRepository.delete(model);
	}
	public DailyReport getDailyReportById(Long id) {
		return dailyReportRepository.findOne(id);
	}
	public List<DailyReport> findDailyReportByDeptAndWeek(Long deptId,DailyReportWeekConfig week) {
		return dailyReportRepository.findDailyReportByDept(deptId,week.getStartDate(),week.getEndDate(),week.getId());
	}
	/***
	 * 查找日志
	 * @param startDate
	 * @param endDate
	 * @param project
	 * @param user
	 * @param type
	 * @param scope
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<DailyReport> findDailyReport(final Date startDate, final Date endDate, final Long project,
			final Long user, final Integer type, final String scope ,final int pageNumber, final int pageSize) {
		 Specification<DailyReport> spec = new Specification<DailyReport>() {
			@Override
			public Predicate toPredicate(Root<DailyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (scope!=null) {
				predicate.getExpressions().add(cb.like(root.get("user").get("authorityCode").as(String.class), scope+"%"));
				}
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reportDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reportDate").as(Date.class), endDate));
				}
				if (project != null) {
					predicate.getExpressions().add(cb.equal(root.get("project").as(Project.class), projectRepository.findOne(project)));
				}
				if (user != null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(user)));
				}
				if (type != null) {
					predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class), type));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "reportDate"));
		Page<DailyReport> dailyPage = dailyReportRepository.findAll(spec, pageRequest);
		return dailyPage;
	}
	/***
	 * 查找日志不分页
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @return
	 */
	public List<DailyReport> findDailyReport(final Date startDate, final Date endDate, final User user) {
		Specification<DailyReport> spec =  new Specification<DailyReport>() {
			@Override
			public Predicate toPredicate(Root<DailyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate != null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reportDate").as(Date.class), startDate));
				}
				if (endDate != null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reportDate").as(Date.class), endDate));
				}
				if (user != null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), user));
				}
				return predicate;
			}

		};
		return  dailyReportRepository.findAll(spec, new Sort(Direction.DESC, "reportDate"));
	}
	
	/***
	 *  找加班报告
	 * @param date
	 * @param user
	 * @return
	 */
	public List<DailyReport> findOverTimeDailyReport(Date date) {
		return dailyReportRepository.findByTypeAndReportDate("7",date);
	}
	/***
	 * 找加班报告
	 * @param date
	 * @param user
	 * @return
	 */
	public List<DailyReport> findOverTimeDailyReport(Date date,User user) {
		return dailyReportRepository.findByReportDateAndUserAndType(date, user, "7");
	}
	/***
	 * 工时核对找加班记录
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param state=1 查已经核对过的，state=2查没有核对过的
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<DailyReport> findOverTimeDailyReport(final Date startDate, final Date endDate,final Long user,final  String state,final  String oaState,
			final int pageNumber, final int pageSize){
		Specification<DailyReport> spec = new Specification<DailyReport>() {
			@Override
			public Predicate toPredicate(Root<DailyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reportDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reportDate").as(Date.class), endDate));
				}
				if (user != null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(user)));
				}
				predicate.getExpressions().add(cb.equal(root.get("type").as(String.class), "7"));
				predicate.getExpressions().add(cb.greaterThan(root.get("spendHours").as(float.class), 0.5f));
				
				Subquery<AttenceOverTime> subquery = query.subquery(AttenceOverTime.class);
				Root<AttenceOverTime> attenceOverTimeRoot = subquery.from(AttenceOverTime.class);
				Predicate predicatesub = cb.conjunction();
				predicatesub.getExpressions().add(cb.equal(attenceOverTimeRoot.get("dailyReport").as(DailyReport.class), root));
				/**OA 状态 1是提交，2是未提交*/
				if("1".equals(oaState))
					predicatesub.getExpressions().add(cb.equal(attenceOverTimeRoot.get("oaState").as(String.class), "1"));
				else if("2".equals(oaState))
					predicatesub.getExpressions().add(cb.equal(attenceOverTimeRoot.get("oaState").as(String.class), "2"));
				subquery.where(predicatesub).select(attenceOverTimeRoot);
				/**核对状态*/
				if("1".equals(state)||StringUtils.isNotBlank(oaState))
					predicate.getExpressions().add(cb.exists(subquery));
				else if("2".equals(state))
					predicate.getExpressions().add(cb.not(cb.exists(subquery)));
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "reportDate","user.id"));
		Page<DailyReport> dailyPage = dailyReportRepository.findAll(spec, pageRequest);
		return dailyPage;
	}
	
	public Page<DailyReport> findDailyReport(final Date start, final Date end, final Long cuid, int pageNumber, int pageSize) {
		Specification<DailyReport> spec = new Specification<DailyReport>() {
			@Override
			public Predicate toPredicate(Root<DailyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (start!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("reportDate").as(Date.class), start));
				}
				if (end!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("reportDate").as(Date.class), end));
				}
				if (cuid != null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(cuid)));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "reportDate"));
		Page<DailyReport> dailyPage = dailyReportRepository.findAll(spec, pageRequest);
		return dailyPage;
	}
	
	
}
