package com.hsae.ims.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class AttenceReportCountRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * 部门加班工时统计
	 * type:* 1 平时 2 周末 3 假日
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findAttenceOvertimeStatics(String fromDate, String toDate, String type){
		StringBuffer sb = new StringBuffer("SELECT d.id AS deptId,d.name AS deptName,hours FROM ims_system_deptment d "+
		        "LEFT JOIN (SELECT o.overtime_type,sum(o.check_hours) AS hours,o.overtime_date,u.dept "+
				"FROM ims_system_user u INNER JOIN ims_attence_overtime o ON o.user = u.id "+
				"WHERE o.overtime_date BETWEEN :fromDate AND :toDate AND o.overtime_type = :type "+
				"GROUP BY u.dept)jb ON d.id  = jb.dept WHERE d.id NOT IN ('1')ORDER BY d.id ASC");
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = null;
		query = em.createNativeQuery(sb.toString());

		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("type", type);

		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}
	
	/**
	 * 部门请假工时统计
	 * type:  30调休假 40事假 80产假  100哺乳假  110陪产假 。
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findAttenceDayoffStatics(String fromDate, String toDate, String type){
		StringBuffer sb = new StringBuffer("SELECT d.id,d.name AS deptName,hours FROM ims_system_deptment d "+
                "LEFT JOIN (SELECT o.dayoff_type,sum(o.spend_hours) AS hours,o.dayoff_date,u.dept "+
		        "FROM ims_system_user u INNER JOIN ims_attence_dayoff o ON o.user = u.id "+
		        "WHERE o.dayoff_date BETWEEN :fromDate AND :toDate AND o.dayoff_type = :type "+  //1 调休 2 事假 3 产假 4 哺乳假 5 陪护假*/
		        "GROUP BY u.dept) jb ON d.id  = jb.dept WHERE d.id NOT IN ('1')ORDER BY d.id ASC");
		
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = null;
		query = em.createNativeQuery(sb.toString());

		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("type", type);

		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}
	
	/**
	 * 部门补签卡工时统计
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findAttenceAbsenteeStatics(String fromDate, String toDate){
		StringBuffer sb = new StringBuffer("SELECT d.id AS deptId,d.name AS deptName,absenteeCount FROM ims_system_deptment d "+
							       "LEFT JOIN (SELECT COUNT(o.absentee_date) AS absenteeCount,o.absentee_date,u.dept FROM ims_system_user u "+
								   "INNER JOIN ims_attence_absentee o ON o.user = u.id WHERE o.absentee_date BETWEEN :fromDate AND :toDate "+
								   "GROUP BY u.dept) jb ON d.id  = jb.dept WHERE d.id NOT IN ('1')ORDER BY d.id ASC");
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = null;
		query = em.createNativeQuery(sb.toString());

		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}
	
	
	/**
	 * 个人请假加班次数统计
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findAttenceUserCountState(String fromDate, String toDate, Long pmId){
		String add = "";
        if (!pmId.equals("") && pmId != null && pmId != 0){
        	add = "AND o.`user` ="+pmId;
        }
        	
		StringBuffer sb = new StringBuffer("SELECT o.`user`,count(o.overtime_date) AS overtimeCount,count(d.dayoff_date) AS dayoffCount,count(o.check_hours),o.overtime_date "+
									"FROM ims_attence_overtime o LEFT JOIN ims_attence_dayoff d ON o.`user` = d.`user` "+
									"WHERE (o.overtime_date BETWEEN :fromDate AND :toDate OR d.dayoff_date BETWEEN :fromDate AND :toDate) "+add+
									" GROUP BY o.`user`  ORDER BY overtimeCount DESC LIMIT 0,6");
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = null;
		query = em.createNativeQuery(sb.toString());
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);

		List<Object[]> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}
}
