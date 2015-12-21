package com.hsae.ims.service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.DeptRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class DeptService {
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private UserRepository userRepository;

	public Iterable<Deptment> findByPid(long id) {
		Iterable<Deptment> deptments = deptRepository.findByPId(id);
		this.setParentName(deptments);
		this.setManagerName(deptments);
		return deptments;
	}

	public Deptment saveDept(Deptment dept) {
		return deptRepository.save(dept);
	}

	public void deleteDeptById(Long id) {
		Deptment dept = deptRepository.findOne(id);
		deptRepository.delete(dept);
	}

	public void deleteDept(Deptment dept) {
		deptRepository.delete(dept);
	}

	public Deptment findDeptById(Long id) {
		Deptment dept = deptRepository.findOne(id);
		if (dept != null) {
			Long managerId = dept.getManager();
			if (managerId != null) {
				User u = userRepository.findOne(managerId);
				dept.setManagerName(u == null? "" : u.getChinesename());
			}else{
				dept.setManagerName("");
			}
		}
		return dept;
	}

	public Deptment findDeptByName(String deptname) {
		List<Deptment> depts = (List<Deptment>) deptRepository.findAll();
		for (Deptment dept : depts) {
			if (dept.getName().equals(deptname)) {
				return dept;
			}
		}

		return null;
	}

	public Deptment updateUser(Deptment dept) {
		return deptRepository.save(dept);
	}

	public List<Deptment> getAllDeptment() {
		// return (List<Deptment>) deptRepository.findAll();
		Iterable<Deptment> list = deptRepository.findAll();
		this.setParentName(list);
		return (List<Deptment>) list;
	}

	private void setParentName(Iterable<Deptment> depts) {
		Map<Long, String> id2name = new HashMap<Long, String>();
		Iterator<Deptment> it = depts.iterator();
		while (it.hasNext()) {
			Deptment dept = it.next();
			if (id2name.containsKey(dept.getpId())) {
				String parentname = id2name.get(dept.getpId());
				dept.setParentName(parentname);
			} else {
				Deptment p = this.findDeptById((long) dept.getpId());
				if (p != null) {
					dept.setParentName(p.getName());
					id2name.put(dept.getpId(), p.getName());
				}
			}
		}
	}
	
	private void setManagerName(Iterable<Deptment> depts){
		Iterator<Deptment> it = depts.iterator();
		while (it.hasNext()) {
			Deptment dept = it.next();
			Long managerId = dept.getManager();
			if (managerId != null) {
				User u = userRepository.findOne(managerId);
				dept.setManagerName(u == null? "" : u.getChinesename());
			}
			else{
				dept.setManagerName("");
			}
		}
	}

	/**
	 * 选出所有的子节点
	 * 
	 * @return
	 */
	public List<Deptment> getAllChildDept() {
		return (List<Deptment>) deptRepository.getAllChildDept();
	}
	//TODO 
	public User findManagerByDeptment(Long deptid){
		return userRepository.findOne(deptid);
	}
}
