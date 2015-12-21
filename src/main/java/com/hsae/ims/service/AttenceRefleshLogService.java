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

import com.hsae.ims.entity.AttenceRefleshLog;
import com.hsae.ims.repository.AttenceRefleshLogRepository;
@Service
public class AttenceRefleshLogService {
	@Autowired
	private AttenceRefleshLogRepository attenceRefleshLogRepository;
	/***
	 *  查找所有的考勤刷新日志
	 * @param startDate
	 * @param endDate
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AttenceRefleshLog> findAttenceRefleshLogs(final Date startDate, final Date endDate,final int pageNumber, final int pageSize) {
		 Specification<AttenceRefleshLog> spec = new Specification<AttenceRefleshLog>() {
			@Override
			public Predicate toPredicate(Root<AttenceRefleshLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("attenceDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("attenceDate").as(Date.class), endDate));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "attenceDate"));
		Page<AttenceRefleshLog> dailyPage = attenceRefleshLogRepository.findAll(spec, pageRequest);
		return dailyPage;
	}
	
	public  List<AttenceRefleshLog> findByDate(Date  date){
		return attenceRefleshLogRepository.findByAttenceDate(date);
	}
	public void deleteAttenceRefleshLog(AttenceRefleshLog attenceRefleshLog){
		 attenceRefleshLogRepository.delete(attenceRefleshLog);
	}
	public void deleteAttenceRefleshLog(Date  date){
		List<AttenceRefleshLog> attenceRefleshLogs=attenceRefleshLogRepository.findByAttenceDate(date);
		for(AttenceRefleshLog attenceRefleshLog:attenceRefleshLogs){
			attenceRefleshLogRepository.delete(attenceRefleshLog); 
		}
	}
	public void saveAttenceRefleshLog(AttenceRefleshLog attenceRefleshLog){
		attenceRefleshLogRepository.save(attenceRefleshLog);
	}
}
