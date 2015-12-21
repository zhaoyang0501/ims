package com.hsae.ims.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.service.DeptService;
/***
 * TODO 产品经理设置
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("sysconfig/dept")
public class DeptController {
	
	private static final Logger log = LoggerFactory.getLogger(DeptController.class);
	@Autowired
	private DeptService deptService;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		log.info("DeptController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "部门管理");
		breadCrumbMap.put("url", "sysconfig/dept/index");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("sysconfig/dept");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	@ResponseBody
	public List<Deptment> getAllDeptments() {	
		List<Deptment> depts = deptService.getAllDeptment();	
		return depts;
	}
	
	@RequestMapping(value = "/querybypid/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Deptment> getAllDeptmentByPid(@PathVariable Long pid ) {	
		  return deptService.findByPid(pid);
	}
	
	@RequestMapping(value = "/querybyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Deptment getDeptment(@PathVariable Long id ) {	
		  return deptService.findDeptById(id);
	}
	
	/***
	 * 删除指定部门
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteDeptment(@PathVariable Long id){
		/**如果有儿子不给删*/
		if(deptService.findByPid(id).iterator().hasNext())
			return false;
		else{
			deptService.deleteDeptById(id);
			return true;
		}
	}
	/***
	 * 修改部门信息
	 * @param kpiDeptment
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateDept(Deptment deptment) {
		log.info("DeptController.updateDept");
		Deptment deptToBeUpdate=deptService.findDeptById(deptment.getId());
		deptToBeUpdate.setName(deptment.getName());
		deptToBeUpdate.setPermission(deptment.getPermission());
		deptToBeUpdate.setManager(deptment.getManager());
		try {
			deptService.saveDept(deptToBeUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/***
	 * 保存部门
	 * @param kpiDeptment
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDept(Deptment deptment) {
		try {
			deptService.saveDept(deptment);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
