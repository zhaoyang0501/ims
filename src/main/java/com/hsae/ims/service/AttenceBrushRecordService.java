package com.hsae.ims.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.repository.AttenceAbsenteeRepository;
import com.hsae.ims.repository.AttenceBrushRdcordRepository;
@Service
public class AttenceBrushRecordService {
	@Autowired
	private AttenceBrushRdcordRepository attenceBrushRdcordRepository;
	@Autowired
	private AttenceAbsenteeRepository attenceAbsenteeRepository;
	public Page<AttenceBrushRecord> findBrushRecord(String yyyyMM,final Long  userid,
			final int pageNumber, final int pageSize) throws ParseException {
		Date startDate=DateUtils.parseDate(yyyyMM, "yyyy-MM");
		Date endDate=DateUtils.addDays( DateUtils.addMonths(startDate, 1),-1);
		return this.findBrushRecord(startDate,endDate, userid, pageNumber, pageSize);
	}
	/***
	 * 多条件查询我的考勤
	 * @param brushDate
	 * @param username
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AttenceBrushRecord> findBrushRecord(final Date startDate,final Date endDate,final Long  userid,
		final int pageNumber, final int pageSize) {
		 Specification<AttenceBrushRecord> spec = new Specification<AttenceBrushRecord>() {
			@Override
			public Predicate toPredicate(Root<AttenceBrushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("brushDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("brushDate").as(Date.class), endDate));
				}
				if (userid!=null) {
					predicate.getExpressions().add(cb.equal(root.get("personId").as(Long.class), userid));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.ASC, "brushDate"));
		Page<AttenceBrushRecord> attenceBrushRecordpages = attenceBrushRdcordRepository.findAll(spec, pageRequest);
		for(AttenceBrushRecord bean:attenceBrushRecordpages.getContent()){
			if("80,70,60,10".indexOf(bean.getState())==-1){
				bean.setState(getStateByUserAndDate(bean.getPersonId(),bean.getBrushDate()));
			}
		}
			
		return attenceBrushRecordpages;
	}
	/***
	 * 首页小工具查询考勤记录
	 * @param startDate
	 * @param userid
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AttenceBrushRecord> findBrushRecord(final Date startDate,final Long userid,final int pageNumber, final int pageSize) {
		 Specification<AttenceBrushRecord> spec = new Specification<AttenceBrushRecord>() {
				@Override
				public Predicate toPredicate(Root<AttenceBrushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (startDate!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("brushDate").as(Date.class), startDate));
					}
					if (userid!=null) {
						predicate.getExpressions().add(cb.equal(root.get("personId").as(Long.class), userid));
					}
					return predicate;
				}
			};
			PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.ASC, "brushDate"));
			Page<AttenceBrushRecord> dailyPage = attenceBrushRdcordRepository.findAll(spec, pageRequest);
			wrapAbsentee(dailyPage.getContent());
			return dailyPage;
	}
	
	public List<AttenceBrushRecord> findBrushRecord(final Date startDate,final Date endDate,final Long userid) {
		 Specification<AttenceBrushRecord> spec = new Specification<AttenceBrushRecord>() {
				@Override
				public Predicate toPredicate(Root<AttenceBrushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (startDate!=null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("brushDate").as(Date.class), startDate));
					}
					if (endDate!=null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("brushDate").as(Date.class), endDate));
					}
					if (userid!=null) {
						predicate.getExpressions().add(cb.equal(root.get("personId").as(Long.class), userid));
					}
					return predicate;
				}
			};
			List<AttenceBrushRecord> attenceBrushRecords = attenceBrushRdcordRepository.findAll(spec);
			return attenceBrushRecords;
	}
	/***
	 * 多条件查询我的考勤
	 * @param brushDate
	 * @param username
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AttenceBrushRecord> findBrushRecord(final Date startDate,final Date endDate,final Long userid,
			final String state,final int pageNumber, final int pageSize) {
		 Specification<AttenceBrushRecord> spec = new Specification<AttenceBrushRecord>() {
			@Override
			public Predicate toPredicate(Root<AttenceBrushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("brushDate").as(Date.class), startDate));
				}
				if (endDate!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("brushDate").as(Date.class), endDate));
				}
				if (userid!=null) {
					predicate.getExpressions().add(cb.equal(root.get("personId").as(Long.class), userid));
				}
				if (state!=null) {
					predicate.getExpressions().add(cb.equal(root.get("state").as(String.class), state));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "state").and( new Sort(Direction.DESC, "brushDate"))
				.and(new Sort(Direction.ASC, "personNo")));
		Page<AttenceBrushRecord> attenceBrushRecordpages = attenceBrushRdcordRepository.findAll(spec, pageRequest);
		for(AttenceBrushRecord bean:attenceBrushRecordpages.getContent()){
			if("80,70,60,10".indexOf(bean.getState())==-1){
				bean.setState(getStateByUserAndDate(bean.getPersonId(),bean.getBrushDate()));
			}
		}
			
		return attenceBrushRecordpages;
	}
	public  static void main(String arg[]){
		
	}
	/***
	 * 多条件查询
	 * @param brushDate
	 * @param user
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AttenceBrushRecord> findBrushRecord(final Date brushDate,
			final String username,final int pageNumber, final int pageSize) {
		 Specification<AttenceBrushRecord> spec = new Specification<AttenceBrushRecord>() {
			@Override
			public Predicate toPredicate(Root<AttenceBrushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (brushDate!=null) {
					predicate.getExpressions().add(cb.equal(root.get("brushDate").as(Date.class), brushDate));
				}
				if (username!=null) {
					predicate.getExpressions().add(cb.like(root.get("personName").as(String.class), username+"%"));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "state").and( new Sort(Direction.DESC, "personNo")));
		Page<AttenceBrushRecord> dailyPage = attenceBrushRdcordRepository.findAll(spec, pageRequest);
		return dailyPage;
	}
	
	public AttenceBrushRecord findBrushRecord(Long userId,Date brushDate){
		List<AttenceBrushRecord> list = attenceBrushRdcordRepository.findByPersonIdAndBrushDate(userId, brushDate);
		return list.size()==0?null:list.get(0);
	}
	public String getStateByUserAndDate(Long userId,Date brushDate){
		int dayoffcount=attenceBrushRdcordRepository.getDayOff(userId, brushDate);
		int travelCount=attenceBrushRdcordRepository.getTravel(userId, brushDate);
		int absenteeCount=attenceBrushRdcordRepository.getAbsentee(userId, brushDate);
		String state="";
		if(dayoffcount!=0)
			state+="20";
		if(travelCount!=0)
			state+=",40";
		if(absenteeCount!=0)
			state+=",30";
		return state;
	}
	public AttenceBrushRecord findById(Long id){
		return attenceBrushRdcordRepository.findOne(id);
	}
	public void saveAttenceBrushRdcord(AttenceBrushRecord attenceBrushRecord){
		attenceBrushRdcordRepository.save(attenceBrushRecord);
	}
	public void saveAttenceBrushRdcord(List<AttenceBrushRecord> attenceBrushRdcords){
		attenceBrushRdcordRepository.save(attenceBrushRdcords);
	}
	/**private---------------------*/
	private void wrapAbsentee(List<AttenceBrushRecord> attenceBrushRecords){
		for(AttenceBrushRecord bean:attenceBrushRecords){
			List<AttenceAbsentee> list=attenceAbsenteeRepository.findByAbsenteeDateAndUser(bean.getBrushDate(), bean.getPersonId());
			bean.setAttenceAbsentee(list.size()==0?null:list.get(0));
		}
	}
}
