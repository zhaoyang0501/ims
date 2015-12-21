package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.RoleRights;
import com.hsae.ims.repository.RoleRightsRepository;

@Service
public class RoleRightsService {
	
	@Autowired
	private RoleRightsRepository roleRightsRepository;
	
	public List<RoleRights> queryByRole(long roleId) {
		return roleRightsRepository.findByRoleId(roleId);
	}

}
