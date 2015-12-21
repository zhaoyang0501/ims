package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.repository.RoleRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserRoleRepository;

@Service
public class RoleUserService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 查询所有有人员的角色
	 * @return
	 */
	public List<Role> findAllHaveUserRoles() {
		return (List<Role>) userRoleRepository.findAllHaveUserRoles();
	}

	public List<UserRole> findByRoleid(Long roleId) {
		return userRoleRepository.findByRoleid(roleId);
	}

	/**
	 * 根据用户权限查找用户
	 * @param roleId
	 * @return
	 */
	public List<UserRole> queryUser4role(Long roleId) {
		List<UserRole> result = new ArrayList<UserRole>();
		List<String> userids = new ArrayList<String>();
		String rolename = "";
		if (roleId != null) {
			result = userRoleRepository.findByRoleid(roleId);
			userids = new ArrayList<String>();
			for (UserRole ur : result) {
				ur.setChecked(true);
				userids.add(String.valueOf(ur.getUserId()));
				User u = userRepository.findOne(ur.getUserId());
				if (u != null) {
					ur.setUserchinesename(u.getChinesename());
					ur.setUserempnumber(u.getEmpnumber());
				}
			}

			Role role = roleRepository.findOne(roleId);
			if (role != null) {
				rolename = role.getName();
			}
		}

		List<User> users = (List<User>) userRepository.findAll();
		for (User u : users) {
			if (!userids.contains(String.valueOf(u.getId()))) {
				UserRole ur = new UserRole();
				ur.setRolename(rolename);
				ur.setUserId(u.getId());
				ur.setUserchinesename(u.getChinesename());
				ur.setUserempnumber(u.getEmpnumber());
				ur.setChecked(false);
				result.add(ur);
			}
		}
		return result;
	}

	/**
	 * save保存角色用户
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	@Transactional
	public Map<String, Object> saveRoleUser(Long roleId, String userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (roleId != null) {
			userRoleRepository.deleteByRoleId(roleId);
		}
		if(StringUtils.isNoneEmpty(userIds)){
			String[] userIdArray = userIds.split(",");
			List<String> idsList = Arrays.asList(userIdArray);
			List<UserRole> urList = userRoleRepository.findByRoleid(roleId);
			for (UserRole userRole : urList) {
				long uId = userRole.getUserId();
				if(!idsList.contains(String.valueOf(uId))){
					deleteByRoleIdAndUserId(roleId, uId);
				}else{
					idsList.remove(String.valueOf(uId));
				}
			}
			UserRole ur = null;
			for (String id : idsList) {
				ur = new UserRole();
				ur.setRoleid(roleId);
				ur.setUserId(Long.parseLong(id));
				userRoleRepository.save(ur);
			}
			map.put("success", 1);
			map.put("msg", "角色用户保存成功！");
		}else{
			map.put("msg", "未选择任何用户！");
		}
		return map;
	}
	private void deleteByRoleIdAndUserId(long roleId, long uId) {
		userRoleRepository.deleteByRoleIdAndUserId(roleId, uId);	
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> deleteByRoleId(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int n = userRoleRepository.deleteByRoleId(id);
		if (n > 0) {
			map.put("success", 1);
			map.put("msg", "删除角色用户成功！");
		}else{
			map.put("success", 0);
			map.put("msg", "删除角色用户失败！");
		}
		return map;
	}

}
