package com.hsae.ims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.RoleRights;
import com.hsae.ims.repository.RoleRepository;
import com.hsae.ims.repository.RoleRightsRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleRightsRepository roleRightsRepository;
	
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		Sort sort = new Sort(Direction.ASC, "id");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	public Role findOne(long id){
		return roleRepository.findOne(id);
	}
	

	public List<Role> findAll() {
		return (List<Role>)roleRepository.findAll();
	}

	public Page<Role> findAll(int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		return (Page<Role>) roleRepository.findAll(null, pageRequest);
	}

	@Transactional
	public Map<String, Object> saveRole(Role role, String rightsIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		Role rr = roleRepository.save(role);
		if(rr == null || rr.getId() <= 0){
			map.put("success", 0);
			map.put("msg", "新建角色失败，请联系管理员或者稍后再试！");
		}else{
			if(rightsIds != ""){
				String[] rightsArray = rightsIds.split(",");
				RoleRights roleRights = null;
				for (int i = 0; i < rightsArray.length; i++) {
					roleRights = new RoleRights();
					roleRights.setRoleId(rr.getId());
					roleRights.setRightsId(Integer.parseInt(rightsArray[i]));
					roleRightsRepository.save(roleRights);
				}
				map.put("success", 1);
				map.put("msg", "新建角色成功！");
			}
		}
		return map;
	}

	public Map<String, Object> deleteRole(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		roleRepository.delete(id);
		map.put("success", 1);
		map.put("msg", "删除角色成功！");
		return map;
	}

	@Transactional
	public Map<String, Object> updateRole(Role role, String rightsIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		Role temp = roleRepository.findOne(role.getId());
		if (temp != null) {
			temp.setName(role.getName());
			temp.setRoledesc(role.getRoledesc());
			roleRepository.save(temp);
			//删除rolerights
			roleRightsRepository.deleteByRoleId(temp.getId());
			if(rightsIds != ""){
				String[] rightsArray = rightsIds.split(",");
				RoleRights roleRights = null;
				for (int i = 0; i < rightsArray.length; i++) {
					roleRights = new RoleRights();
					roleRights.setRoleId(temp.getId());
					roleRights.setRightsId(Integer.parseInt(rightsArray[i]));
					roleRightsRepository.save(roleRights);
				}
				map.put("success", 1);
				map.put("msg", "修改角色成功！");
			}
		}
		else{
			map.put("success", 0);
			map.put("msg", "修改角色失败！");
		}
		return map;
	}

}
