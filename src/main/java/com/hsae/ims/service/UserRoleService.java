package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.repository.RoleRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserRoleRepository;

@Service
public class UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository; 
	
	/**
	 * 查找用户权限
	 * @param userId
	 * @return
	 */
	public List<UserRole> queryUserRole(long userId) {
		List<UserRole> userRoleList = userRoleRepository.findByUserId(userId);
		List<UserRole> list = null;
		if(userRoleList != null && userRoleList.size() >0){
			list = new ArrayList<UserRole>();
			for(UserRole userRole : userRoleList){
				UserRole r = this.attachTransientAttr(userRole);
				list.add(r);
			}
		}
		return list;
	}
	
	/**
	 * 附加用户权限其他信息
	 * @param userRole
	 * @return
	 */
	private UserRole attachTransientAttr(UserRole userRole){
		if(userRole != null && userRole.getId() > 0){
			Role role = roleRepository.findOne(userRole.getRoleid());
			if (role != null && role.getId() > 0) {
				userRole.setRolename(role.getName());
			}
			User u = userRepository.findOne(userRole.getUserId());
			userRole.setUserchinesename(u.getChinesename());
			userRole.setUserempnumber(u.getEmpnumber());
		}
		return userRole;
	}

}
