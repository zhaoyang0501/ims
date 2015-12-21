package com.hsae.ims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRights;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserRightsRepository;

@Service
public class UserRightsService {
	
	@Autowired
	private UserRightsRepository userRightsRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<UserRights> queryByUser(long userId) {
		return userRightsRepository.findByUserId(userId);
	}

	public List<User> findAllHaveRightsUser() {
		List<User> list = (List<User>) userRightsRepository.findAllHaveRightsUser();
		return list;
	}

	public List<UserRights> findByUserId(long id) {
		List<UserRights> list = userRightsRepository.findByUserId(id);
		for (UserRights ur : list) {
			User u = userRepository.findOne(ur.getUserId());
			ur.setUserchinesename(u.getChinesename());
		}
		return list;
	}

	public Map<String, Object> deleteByUserId(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int n = userRightsRepository.deleteByUserId(id);
		if(n > 0){
			map.put("success", 1);
			map.put("msg", "删除用户权限成功！");
		}else{
			map.put("success", 0);
			map.put("msg", "删除用户权限失败，请稍后再试或者联系管理员！");
		}
		return map;
	}

	@Transactional
	public Map<String, Object> saveUserRights(Long userId, String rightsIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserRights ur = null;
		if(userId != null && rightsIds != ""){
			userRightsRepository.deleteByUserId(userId);
			String[] rightsArray = rightsIds.split(",");
			for (int i = 0; i < rightsArray.length; i++) {
				ur = new UserRights();
				ur.setUserId(userId);
				ur.setRightsId(Long.parseLong(rightsArray[i]));
				userRightsRepository.save(ur);
			}
			map.put("success", 1);
			map.put("msg", "保存用户权限成功！");
		}else{
			map.put("msg", "未选择功能权限！");
		}
		return map;
	}

}
