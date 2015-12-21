package com.hsae.ims.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hsae.ims.entity.AuthenticatedUser;
import com.hsae.ims.entity.Rights;
import com.hsae.ims.entity.RoleRights;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRights;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.service.RightsService;
import com.hsae.ims.service.RoleRightsService;
import com.hsae.ims.service.UserRightsService;
import com.hsae.ims.service.UserRoleService;
import com.hsae.ims.service.UserService;


public class MyAuthenticationManager  implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRightsService userRightsService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleRightsService roleRightsService;
	
	@Autowired
	private RightsService rightsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		//所有用户都具备此grantedauthority
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER");
		
		User u = userService.findByUsername(username);
		if(u == null){
			AuthenticatedUser user = new AuthenticatedUser(username, "", auths); 
	    	return user;
		}
		
		//用户grantedauthority列表
		auths = new ArrayList<GrantedAuthority>();
		auths.add(auth1);
		//存放已有权限
		List<String> haverights = new ArrayList<String>();
		//子权限列表
		List<Rights> rightslist = new ArrayList<Rights>();
		//查询当前用户所在的用户组(角色)
		List<UserRole> userroles = userRoleService.queryUserRole(u.getId());
		if (userroles != null) {
			for(UserRole userrole : userroles){
				long roleid = userrole.getRoleid();
				if (userrole.getRolename().equals("admin")){ //判断是否是管理员组,管理员组具有所有权限
					List<Rights> rights = rightsService.findAll();
					
					rightslist.clear();
					rightslist.addAll(rights);
					for(Rights r : rights){
						if (!haverights.contains(r.getName()))
							haverights.add(r.getName());
					}
					break;
				}else
				{
					//非管理员组,查询该组所有权限
					List<RoleRights> rolerights = roleRightsService.queryByRole(roleid);
					for(RoleRights roleright : rolerights){
						long rightid = roleright.getRightsId();
						Rights rights = rightsService.findOne(rightid);
						//判断该用户权限表中是否已经有此权限.因为可能多个角色具备相同权限
						if (!rightslist.contains(rights)){
							rightslist.add(rights);
						}
						
						if (rights != null && !haverights.contains(rights.getName())){
							haverights.add(rights.getName());
						}
					}
				}
			}
		}
		
		//查询用户独立权限(相对于角色权限)
		List<UserRights> userrights = userRightsService.queryByUser(u.getId());
		for(UserRights userright : userrights){
			long rightid = userright.getRightsId();
			Rights rights = rightsService.findOne(rightid);
			if (!rightslist.contains(rights)){
				rightslist.add(rights);
			}
			if (!haverights.contains(rights.getName())){
				haverights.add(rights.getName());
			}
		}
		//审批权限(权限描述符='A'的权限)对于所有合法用户都开放
		List<Rights> allrights = rightsService.findAll();
		for(Rights rights : allrights){
			if (rights.getRights()!=null&&rights.getRights().toUpperCase().equals("A")) {
				if (!rightslist.contains(rights)) {
					rightslist.add(rights);
				}
				if (!haverights.contains(rights.getName())) {
					haverights.add(rights.getName());
				}
			}
		}
		for(String rightname : haverights){
			SimpleGrantedAuthority auth = new SimpleGrantedAuthority(rightname);
			auths.add(auth);
		}
		
		AuthenticatedUser user = new AuthenticatedUser(username, u.getPassword(), auths);
		//保存用户所有权限列表
		user.setAllrights(rightslist);
		//authenticate中保存用户ID及中文名称
		user.setUserid(u.getId());
		user.setChinesename(u.getChinesename());
		
		return user;
	}

}
