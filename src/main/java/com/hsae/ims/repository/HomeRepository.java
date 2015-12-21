package com.hsae.ims.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class HomeRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findDailyReportTypeStatics(String fromDate, String toDate, Long cuid){
		EntityManager em = entityManagerFactory.createEntityManager();
		StringBuffer sb = new StringBuffer("SELECT d.type,SUM(d.spend_hours)hours "
				+ " FROM ims_daily_report d "
				+ " WHERE d.`user`=:user ");
		if (StringUtils.isNotBlank(fromDate)) {
			sb.append(" AND d.report_date >= :fromDate ");
		}
		if (StringUtils.isNotBlank(toDate)) {
			sb.append(" AND d.report_date <= :toDate");
		}
		sb.append(" GROUP BY d.type");
		Query query = em.createNativeQuery(sb.toString());
		if (StringUtils.isNotBlank(fromDate)) {
			query.setParameter("fromDate", fromDate);
		}
		if (StringUtils.isNotBlank(toDate)) {
			query.setParameter("toDate", toDate);
		}
		query.setParameter("user", cuid);
		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}


	/**
	 * 查询个人主页考勤汇总
	 * @param cuid
	 * @return
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public List<Object[]> findLatestAttenceStatics(Long cuid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		StringBuffer sb = new StringBuffer("SELECT a.last_rest,a.current_increase,a.current_reduce,a.current_rest,create_time,"
				+ "a.start_date, a.end_date "
				+ "from ims_attence_statistics a where a.user=:user ORDER BY create_time DESC LIMIT 1");
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("user", cuid);
		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}

	/**
	 * 查询个人主页考勤明细
	 * @param pageNumber
	 * @param pageSize
	 * @param fromDate
	 * @param toDate
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryAttenceStaticsDetails(int pageNumber, int pageSize, String fromDate, String toDate, String type, Long cuid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		StringBuffer sb = new StringBuffer("SELECT av.type,av.date,av.details,av.description,av.user FROM v_attence_statics av WHERE av.user=:user ");
		if (StringUtils.isNotBlank(fromDate)) {
			sb.append(" AND av.date>=:fromDate");
		}
		if(StringUtils.isNoneBlank(toDate)){
			sb.append(" AND av.date<=:toDate");
		}
		if (StringUtils.isNotBlank(type)) {
			if (type.equals("1")) {
				sb.append(" AND av.type='请假'");
			}else if(type.equals("2")){
				sb.append(" AND av.type='加班'");
			}else if(type.equals("3")){
				sb.append(" AND av.type='漏打卡'");
			}else if(type.equals("4")){
				sb.append(" AND av.type='出差'");
			}
		}
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("user", cuid);
		if (StringUtils.isNotBlank(fromDate)) {
			query.setParameter("fromDate", fromDate);
		}
		if(StringUtils.isNoneBlank(toDate)){
			query.setParameter("toDate", toDate);
		}
		
		List<Object[]> list = query.getResultList();
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		em.clear();
		em.close();
		return list;
	}
}
