package com.hsae.ims.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.TrainingTeacher;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.TrainingTeacherRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class TrainingTeacherService {

	@Autowired
	private TrainingTeacherRepository trainingTeacherRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<TrainingTeacher> findAll(){
		return (List<TrainingTeacher>) trainingTeacherRepository.findAll();
	}
	
	public TrainingTeacher findOne(long id){
		return trainingTeacherRepository.findOne(id);
	}
	
	public boolean findByUser(long userId){
		TrainingTeacher tt = trainingTeacherRepository.findByUserId(userId);
		if (tt != null && tt.getId() > 0) {
			return true;
		}
		return false;
	}
	
	public Map<String, Object> delete(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		TrainingTeacher tt = trainingTeacherRepository.findOne(id);
		if (tt != null && tt.getId() > 0) {
			tt.setIsdelete(1);
			trainingTeacherRepository.save(tt);
			map.put("success", "1");
			map.put("msg", "讲师删除成功！");
		} else{
			map.put("success", "0");
			map.put("msg", "讲师删除失败！");
		}
		return map;
	}
	
	public Map<String, Object> save(TrainingTeacher entity){
		Map<String, Object> map = new HashMap<String, Object>();
		trainingTeacherRepository.save(entity);
		map.put("success", "1");
		map.put("msg", "保存讲师成功！");
		return map;
	}
	
	public Page<TrainingTeacher> findAll(int pageNumber, int pageSize, final Long userId){
		Specification<TrainingTeacher> spec = new Specification<TrainingTeacher>() {
			@Override
			public Predicate toPredicate(Root<TrainingTeacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userId != null && userId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(userId)));
				}
				predicate.getExpressions().add(cb.equal(root.get("isdelete").as(Integer.class), 0));
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return trainingTeacherRepository.findAll(spec, pageRequest);
		
	}
	
}
	
