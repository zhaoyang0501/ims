package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.dto.UserDTO;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.repository.DeptRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.utils.CipherUtil;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DeptRepository deptRepository;

	@Autowired
	private UserResumeRepository userResumeRepository;

	@Autowired
	private CodeService codeService;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public User findByNo(String empnumber) {
		return userRepository.getUserByNo(empnumber);
	}
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}
	
	public List<User> findAllTraining() {
		return (List<User>) userRepository.findAllTraining();
	}
	

	public Page<User> pageAllUser(String sSortDir_0, String chinesename, String empnumber, Long deptId, Integer freeze, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(sSortDir_0, pageNumber, pageSize);
		Specification<User> spec = buildSpecification(chinesename, empnumber, deptId, freeze);
		Page<User> result = (Page<User>) userRepository.findAll(spec, pageRequest);
		return result;
	}

	private PageRequest buildPageRequest(String sSortDir_0, int pageNumber, int pagzSize) {
		List<String> list = new ArrayList<String>();
		list.add("id");
		Direction direction = Direction.ASC;
		if (StringUtils.isNotBlank(sSortDir_0)) {
			list.add("username");
			if (sSortDir_0.toUpperCase().equals("DESC")) {
				direction = Direction.DESC;	
			}
		}
		Sort sort = new Sort(direction, list);
		
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	private Specification<User> buildSpecification(final String chinesename, final String empnumber, final Long deptId, final Integer freeze) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (chinesename != null) {
					predicate.getExpressions().add(cb.like(root.get("chinesename").as(String.class), "%"+chinesename+"%"));
				}
				if (empnumber != null) {
					predicate.getExpressions().add(cb.like(root.get("empnumber").as(String.class), "%"+empnumber+"%"));
				}
				if (deptId != null) {
					predicate.getExpressions().add(cb.equal(root.get("dept").as(Deptment.class), deptRepository.findOne(deptId)));
				}
				if (freeze != null) {
					predicate.getExpressions().add(cb.equal(root.get("freeze").as(Integer.class), freeze));
				}
				return predicate;
			}

		};
	}

	/**
	 * 冻结用户
	 * 
	 * @param id
	 * @return
	 */
	public boolean freezeUser(long id) {
		int count = userRepository.freezeUser(id);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 解冻用户
	 * 
	 * @param id
	 * @return
	 */
	public boolean unFreezeUser(long id) {
		int count = userRepository.unFreezeUser(id);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/***
	 * 保存用户信息
	 * 
	 * @param u
	 * @return
	 */
	public boolean save(String deptId,Integer position, User u) {
		List<User> list = (List<User>) userRepository.findAll();
		
		User us = userRepository.findOne(u.getId());
		if (us != null){
			u.setPassword(us.getPassword());
		}else{
			u.setPassword("123456");
			for (User user :list ){
				if (user.getUsername().equals(u.getUsername())){
					return false;
				}
			}
		}
		
		if (StringUtils.isNoneEmpty(deptId)) {
			long dId = Long.parseLong(deptId);
			Deptment dept = deptRepository.findOne(dId);
			if (dept != null) {
				u.setDept(dept);
			}
		}
		this.attachOtherData(u);
		/**权限代码*/
		String authorityCode= String.valueOf(u.getDept().getPermission());
		while (authorityCode.length() < 6) {  
			 authorityCode = authorityCode+"0";  
	    }  
		u.setAuthorityCode(authorityCode+u.getEmpnumber());
		/**权限范围,如果没有填默认是自己的权限代码*/
		if(StringUtils.isEmpty(u.getAuthorityScope())){
			u.setAuthorityScope(u.getAuthorityCode());
		}
		
		User user = userRepository.save(u);
		if (user != null) {
			/**
			 * 新建用户同时新建用户档案。
			 */
			UserResume resume = userResumeRepository.findUserResumeByUser(user);
			if (resume == null){
				UserResume ur = new UserResume();
				ur.setUser(u);
				ur.setPosition(position);
				userResumeRepository.save(ur);
			}else{
				resume.setPosition(position);
			}
			
			return true;
		}
		return false;
	}
	
	/***
	 * 密码重置
	 * 
	 * @param id
	 * @param defaultpwd
	 * @return
	 */
	public boolean resetpwd(long id, String defaultpwd) {
		int count = userRepository.resetpwd(id, defaultpwd);
		if (count > 0) {
			return true;
		}
		return false;
	}

	private void attachOtherData(User u) {
		u.setCreater(RightUtil.getCurrentUserId());
		u.setCreateDate(DateTimeUtil.getCurrDate());
	}

	public User findOne(long id) {
		if(id==0)
			return null;
		return userRepository.findOne(id);
	}
	
	
	/**
	 * 查找被编辑数据。
	 * @param id
	 * @return
	 */
	public UserDTO findUserResume(long id){
		User user = userRepository.findOne(id);
		UserResume ur = userResumeRepository.findUserResumeByUser(user);
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername()==null?"":user.getUsername());
		dto.setChinesename(user.getChinesename()==null?"":user.getChinesename());
		dto.setDept(user.getDept()==null?"":user.getDept().getName());
		dto.setDeptid(user.getDept()==null?0:user.getDept().getId());
		dto.setSex(user.getSex());
		dto.setEmpnumber(user.getEmpnumber()==null?"":user.getEmpnumber());
		dto.setEmail(user.getEmail()==null?"":user.getEmail());
		dto.setTrain(user.getTrain());
		dto.setAttendance(user.getAttendance());
		dto.setWeekreport(user.getWeekreport());
		dto.setAuthorityCode(user.getAuthorityCode()==null?"":user.getAuthorityCode());
		dto.setAuthorityScope(user.getAuthorityScope()==null?"":user.getAuthorityScope());
		if (ur != null){
			dto.setPosition(ur.getPosition()==0?"":codeService.findPositionName(ur.getPosition().toString()));
			dto.setPositionid(ur.getPosition()==0?0:ur.getPosition());
		}else{
			dto.setPosition("");
			dto.setPositionid(0);
		}
		return dto;
	}

	/**
	 * 密码修改
	 * 
	 * @param pwd_old
	 * @param pwd
	 * @return
	 */
	public Map<String, Object> updatepwd(String pwd_old, String pwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = RightUtil.getCurrentUserId();
		User u = userRepository.findOne(userId);
		if (u != null && u.getId() > 0) {
			if (!CipherUtil.encodeByMD5(pwd_old).equals(u.getPassword())) {
				map.put("success", "0");
				map.put("msg", "密码修改失败，旧密码错误！");
			} else {
				u.setPassword(CipherUtil.encodeByMD5(pwd));
				userRepository.save(u);
				map.put("success", "1");
				map.put("msg", "密码修改成功！");
			}

		} else {
			map.put("success", "0");
			map.put("msg", "密码修改失败，请尝试重新登录！");
		}
		return map;
	}

	/**
	 * 修改个人档案
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> updateProfile(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = RightUtil.getCurrentUserId();
		User u = userRepository.findOne(userId);
		u.setEmail(user.getEmail());
		u.setSex(user.getSex());
		
		User uu = userRepository.save(u);
		if (uu != null && uu.getId() > 0) {
			map.put("success", "1");
			map.put("msg", "个人档案修改成功！");
		} else {
			map.put("success", "0");
			map.put("msg", "修改失败，请尝试重新登录！");
		}
		return map;
	}

	/***
	 * 根据部门找部门经理
	 * 
	 * @param deptid
	 * @return
	 */
	public User findManagerByDept(Long deptid) {
		Deptment dept=deptRepository.findOne(deptid);
		if(dept.getManager()==null)
			return null;
		else
			return userRepository.findOne(dept.getManager());
	}

	/***
	 * 找需要提交周报的用户
	 * 
	 * @return
	 */
	public List<User> findNeedWeekReportUser() {
		return userRepository.getUserByWeekReport();
	}
	
	/**
	 * 
	 * @return
	 */
	public int findDeptsuser(long dept){
		List<User> list = userRepository.findUserBydept(dept);
		return list.size();
	}
	/***
	 * 根据权限范围查询
	 * @return
	 */
	public List<User> findUserByAuthority(String authorityScope) {
		return userRepository.findUserByAuthority(authorityScope);
	}
	public List<User> findbyDept(Deptment dept) {
		return userRepository.findByDept(dept);
	}
	/***
	 * 找有效的用户
	 * 
	 * @return
	 */
	public List<User> findValidUser() {
		return userRepository.findValidUser();
	}
}
