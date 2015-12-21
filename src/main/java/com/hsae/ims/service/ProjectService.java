package com.hsae.ims.service;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.dto.ProjectMemberDTO;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.ProjectMember;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.ProjectMemberRepository;
import com.hsae.ims.repository.ProjectRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.RightUtil;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectMemberRepository projectMemberRepository;
	
	@Autowired
	private CodeService codeService;
	/***
	 * 找所有有效的项目
	 * @return
	 */
	public List<Project> findAllvalidProjects(){
		return projectRepository.findAllvalidProjects();
	}
	public Iterable<Project> findByUserJoined(Long user){
		return projectRepository.findByUserJoined(user);
	}
	public Project getProjectById(Long id){
		return projectRepository.findOne(id);
	}
	
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		Sort sort = new Sort(Direction.ASC, "id");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 查询所有项目
	 * @param projectName 
	 * @param pmId 
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	public Page<Project> findProjectList(int pageNumber, int pageSize, long pmId, String projectName) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<Project> spec = buildSpecification(pmId, projectName);
		
		return (Page<Project>)projectRepository.findAll(spec, pageRequest);
		
	}
	
	private Specification<Project> buildSpecification(final Long pmId, final String projectName) {
		return new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (pmId != null && pmId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("pm").as(User.class), userRepository.findOne(pmId)));
				}
				if (projectName != null) {
					predicate.getExpressions().add(cb.like(root.get("projectName").as(String.class), "%"+projectName+"%"));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 创建项目
	 * @param project
	 * @return
	 */
	public Map<String, Object> createProject(Project project, long pmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		User pm = userRepository.findOne(pmId);
		project.setPm(pm);
		Project temp = projectRepository.save(project);
		if (temp != null && temp.getId() >0) {
			map.put("success", 1);
			map.put("msg", "创建项目成功！");
		}else{
			map.put("success", 0);
			map.put("msg", "创建项目失败，请联系管理员！");
		}
		return map;
	}
	
	/**
	 * 配置项目成员
	 * @param id
	 * @param users
	 * @param role
	 * @return
	 */
	@Transactional
	public Map<String, Object>createProjectMember(long id, String users, String role){
	    Map<String, Object> map = new HashMap<String, Object>();
	    if (id > 0) {
	    	try {
	    		Project p = projectRepository.findOne(id);
				if (StringUtils.isNotEmpty(users)) {
					String[] userArray = users.split(",");
					if (userArray != null && userArray.length > 0) {
						ProjectMember projectMember = null;
						long creater = RightUtil.getCurrentUserId();
						for(String s : userArray){
							projectMember = new ProjectMember();
							long uId = Long.parseLong(s);
							projectMemberRepository.delete(id, uId, role);
							User user = userRepository.findOne(uId);
							projectMember.setProject(p);
							projectMember.setUser(user);
							projectMember.setRole(role);
							projectMember.setCreater(creater);
							projectMember.setCreateDate(new Date());
							projectMemberRepository.save(projectMember);
						}
						map.put("success", 1);
						map.put("msg", "配置项目成员成功！");
					}
				}
			} catch (Exception e) {
				map.put("success", 0);
				map.put("msg", "配置项目成员失败，请联系管理员！");
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 更新项目
	 * @param project
	 * @return
	 */
	public Map<String, Object> updateProject(Project project, long pmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (project != null) {
			Project p = projectRepository.findOne(project.getId());
			if (p != null) {
				p.setProjectName(project.getProjectName());
				p.setComplex(project.getComplex());
				p.setCustomer(project.getCustomer());
				p.setDescription(project.getDescription());
				User pm = userRepository.findOne(pmId);
				p.setPm(pm);
				p.setProjectCode(project.getProjectCode());
				p.setState(project.getState());
				Project temp = projectRepository.save(p);
				if (temp != null && temp.getId() >0) {
					map.put("success", 1);
					map.put("msg", "修改项目成功！");
				}else{
					map.put("success", 0);
					map.put("msg", "修改项目失败，请联系管理员！");
				}
			}else{
				map.put("success", 0);
				map.put("msg", "修改项目失败，请联系管理员！");
			}
		}
		return map;
	}
	
	/**
	 *	删除项目 
	 * @param id
	 * @return
	 */
	public Map<String, Object> deleteProject(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			projectRepository.delete(id);
			map.put("success", 1);
			map.put("msg", "删除项目成功！");
			
		} catch (Exception e) {
			map.put("success", 0);
			map.put("msg", "删除项目失败，请联系管理员！");
		}
		return map;
	}
	
	public Project findOne(long id) {
		return projectRepository.findOne(id);
	}
	
	public Iterable<Project> findAll(){
		return projectRepository.findAll();
	}
	
	/**
	 * 根据项目id查找项目成员列表
	 * @param pId
	 * @return
	 */
	public List<ProjectMemberDTO> findProjectMemberList(long pId) {
		List<ProjectMember> list = projectMemberRepository.findByProjectId(pId);
		List<ProjectMemberDTO> dtoList = new ArrayList<ProjectMemberDTO>();
		if (list != null && list.size() > 0) {
			ProjectMemberDTO dto = null;
			int index = 1;
			for(ProjectMember m : list){
				dto = new ProjectMemberDTO();
				dto.setId(String.valueOf(m.getId()));
				dto.setIndex(String.valueOf(index));
				User u = m.getUser();
				dto.setChinesename(m == null ? "" : u.getChinesename());
				dto.setRole(codeService.findProjectMemberRoleName(m.getRole()));
				index ++;
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	/**
	 *	删除项目成员
	 * @param id
	 * @return
	 */
	public Map<String, Object> deleteProjectMember(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			projectMemberRepository.delete(id);
			map.put("success", 1);
			map.put("msg", "删除项目成员成功！");
			
		} catch (Exception e) {
			map.put("success", 0);
			map.put("msg", "删除项目成员失败，请联系管理员！");
		}
		return map;
	}
}
