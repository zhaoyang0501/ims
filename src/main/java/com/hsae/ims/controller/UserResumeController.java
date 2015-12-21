package com.hsae.ims.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.UserAwardDTO;
import com.hsae.ims.dto.UserBeforeExpDTO;
import com.hsae.ims.dto.UserEducationDTO;
import com.hsae.ims.dto.UserFamilyDTO;
import com.hsae.ims.dto.UserNowExpDTO;
import com.hsae.ims.dto.UserResumeDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserAward;
import com.hsae.ims.entity.UserBeforeExp;
import com.hsae.ims.entity.UserContract;
import com.hsae.ims.entity.UserEducation;
import com.hsae.ims.entity.UserFamily;
import com.hsae.ims.entity.UserNowExp;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.entity.UserResumeDimission;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DeptService;
import com.hsae.ims.service.UserContractService;
import com.hsae.ims.service.UserResumeDimissionService;
import com.hsae.ims.service.UserResumeService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.RightUtil;
import com.hsae.ims.view.ResumeReportExportXlsView;

@Controller
@RequestMapping("/sysconfig/resume")
public class UserResumeController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(UserResumeController.class);
	
	@Autowired
	private UserResumeService userResumeService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private UserResumeRepository userResumeRepository;
	@Autowired
	private DeptService deptService;
	@Autowired
	private UserResumeDimissionService userResumeDimissionService;
	@Autowired
	private UserContractService userContractService;
	@Autowired
	private UserService userService;
	
	/**
	 * 档案信息统计表。 
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index() {
		log.info("index:");
		ModelAndView mav = new ModelAndView("resume/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "人事档案");
		breadCrumbMap.put("url", "sysconfig/resume");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	/**
	 * 档案列表。 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBookInfoList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String chinesename, String empnumber, Long deptId, Integer state ){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<UserResume> page = userResumeService.getUserResumeDate(sSortDir_0, pageNumber, pageSize, chinesename, empnumber, deptId, state);
		List<UserResumeDTO> dtoList = new ArrayList<UserResumeDTO>();
		if (page != null && page.getTotalElements() > 0) {

			UserResumeDTO dto = null;	//个人档案返回DTO
			User user = null;
			UserResumeDimission dimission = null;	//离职表
			for (UserResume p : page) {
				dto = new UserResumeDTO();
				dimission = userResumeDimissionService.findByResumeId(p.getId());
				user = p.getUser();
				dto.setId(p.getId());
				if (user != null){
					dto.setUserId(user.getId().toString());
					dto.setUserName(user.getChinesename());
					dto.setEmpnumber(user.getEmpnumber());
					dto.setSex(user.getSex());
					dto.setDept(user.getDept()==null?"":user.getDept().getName());
					dto.setEmail(user.getEmail()==null?"":user.getEmail());
				}else{
					dto.setUserId("");
					dto.setUserName("");
					dto.setEmpnumber("");
					dto.setSex("");
					dto.setDept("");
					dto.setEmail("");
					dto.setState(0);
				}
				dto.setTitle(p.getTitle()==0?"":codeService.findTitleName(p.getTitle().toString()));
				dto.setPosition(p.getPosition()==0?"":codeService.findPositionName(p.getPosition().toString()));
				dto.setJoinTime(p.getJoinTime()==null?"":p.getJoinTime().toString());
				if (dimission == null){
					dto.setDepartureTime("");
				}else{
					dto.setLeavingReasons(dimission.getReason());
					dto.setDepartureTime(dimission.getActualdate().toString());
					if (dimission.getActualdate() != null) {
						dto.setState(1);//离职在职
					}
				}
				dtoList.add(dto);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/**
	 * 跳转到个人档案详情。
	 */
	
	@RequestMapping(value="/resume")
	public ModelAndView resume(@RequestParam("id") long id){
		ModelAndView mav = new ModelAndView("resume/resume");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/resume");
		breakcumbMap.put("name", "档案列表");
		breakcumbList.add(breakcumbMap);
		Map<String, String> breakcumbMap1 = new HashMap<String, String>();
		breakcumbMap1.put("url", "sysconfig/resume");
		breakcumbMap1.put("name", "个人档案");
		breakcumbList.add(breakcumbMap1);
		
		UserResumeDTO resume = userResumeService.findResumeById(id);
		List<UserAwardDTO> award = userResumeService.findUserAwardByResumeId(id);
		List<UserEducationDTO> education = userResumeService.findUserEducationByResumeId(id);
		List<UserFamilyDTO> family = userResumeService.findUserFamilyByResumeId(id);
		List<UserBeforeExpDTO> beforeExp = userResumeService.findUserBeforeExpByResumeId(id);
		List<UserNowExpDTO> nowExp = userResumeService.findUserNowExpByResumeId(id);

		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("resume", resume);
		mav.addObject("award", award);
		mav.addObject("education", education);
		mav.addObject("family", family);
		mav.addObject("beforeExp", beforeExp);
		mav.addObject("nowExp", nowExp);
		mav.addObject("id", id);
		return mav;
	}
	
	/**
	 * 跳转到编辑页面。
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam("reid") long id){
		ModelAndView mav = new ModelAndView("resume/edit");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/resume");
		breakcumbMap.put("name", "档案列表");
		breakcumbList.add(breakcumbMap);
		
		Map<String, String> breakcumbMap1 = new HashMap<String, String>();
		breakcumbMap1.put("url", "sysconfig/resume");
		breakcumbMap1.put("name", "档案编辑");
		breakcumbList.add(breakcumbMap1);
		
		UserResumeDTO resume = userResumeService.findResumeById(id);
		List<String> list = new ArrayList<String>();
		if (resume.getLanguage()!=null){
			for (String lg : resume.getLanguage().split(",")){
				list.add(lg);
			}
		}
		
		List<UserAwardDTO> award = userResumeService.findUserAwardByResumeId(id);
		List<UserEducationDTO> education = userResumeService.findUserEducationByResumeId(id);
		List<UserFamilyDTO> family = userResumeService.findUserFamilyByResumeId(id);
		List<UserBeforeExpDTO> beforeExp = userResumeService.findUserBeforeExpByResumeId(id);
		List<UserNowExpDTO> nowExp = userResumeService.findUserNowExpByResumeId(id);
		mav.addObject("resume", resume);
		mav.addObject("award", award);
		mav.addObject("education", education);
		mav.addObject("family", family);
		mav.addObject("beforeExp", beforeExp);
		mav.addObject("nowExp", nowExp);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("politics",codeService.politicsStatusConfig());
		mav.addObject("edu",codeService.highestEducationConfig());
		mav.addObject("degree",codeService.highestDegreeConfig());
		mav.addObject("post",codeService.postConfig());
		mav.addObject("grade",codeService.gradeConfig());
		mav.addObject("position",codeService.positionConfig());
		mav.addObject("title",codeService.titleConfig());
		mav.addObject("language", codeService.languageTypeConfig());
		mav.addObject("dimissionType", codeService.getDimissionConfig());
		mav.addObject("lg", list);
		mav.addObject("moneytype",codeService.accumulationFundOfSocialSecurityConfig());
		mav.addObject("dept", deptService.getAllDeptment());
		mav.addObject("dimission", userResumeDimissionService.findByResumeId(id));
		
		return mav;
	}
	
	
	/**
	 * 编辑档案信息。 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateBaseInfo(@ModelAttribute("resume") UserResume resume,String resumeId
			,@ModelAttribute("user") User user,String userId, String deptId){														   
		return userResumeService.updateBaseInfo(resume, resumeId, user,userId, deptId);
	}
	
	/**
	 * 编辑家庭信息。 
	 */
	@RequestMapping(value = "/family/{resumeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateFamily(@RequestBody List<UserFamily> family,@PathVariable long resumeId){														   
		return userResumeService.saveFamily(family, resumeId);
	}
	
	/**
	 * 删除家庭信息。 
	 */
	@RequestMapping(value = "/family/del/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delFamily( long id){														   
		return userResumeService.delFamily(id);
	}
	
	/**
	 * 编辑教育信息。 
	 */
	@RequestMapping(value = "/edu/{resumeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateEdu(@RequestBody List<UserEducation> uEdu,@PathVariable long resumeId){														   
		return userResumeService.saveEdu(uEdu, resumeId);
	}
	
	/**
	 * 删除教育信息。 
	 */
	@RequestMapping(value = "/edu/del/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delEdu( long id){														   
		return userResumeService.delEdu(id);
	}
	
	/**
	 * 编辑公司外经历。 
	 */
	@RequestMapping(value = "/outcompany/{resumeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateOutCompany(@RequestBody List<UserBeforeExp> ubExp,@PathVariable long resumeId){														   
		return userResumeService.saveOutCompany(ubExp, resumeId);
	}
	
	/**
	 * 删除公司外经历。 
	 */
	@RequestMapping(value = "/outcompany/del/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delOutCompany( long id){														   
		return userResumeService.delOutCompany(id);
	}
	
	/**
	 * 编辑公司内经历。 
	 */
	@RequestMapping(value = "/incompany/{resumeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateInCompany(@RequestBody List<UserNowExp> unExp,@PathVariable long resumeId){														   
		return userResumeService.saveInCompany(unExp, resumeId);
	}
	
	/**
	 * 删除公司内经历。 
	 */
	@RequestMapping(value = "/incompany/del/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delInCompany( long id){														   
		return userResumeService.delInCompany(id);
	}
	
	/**
	 * 编辑奖惩信息。 
	 */
	@RequestMapping(value = "/reward/{resumeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean updateReward(@RequestBody List<UserAward> reward,@PathVariable long resumeId){														   
		return userResumeService.saveReward(reward, resumeId);
	}
	
	/**
	 * 删除奖惩信息。 
	 */
	@RequestMapping(value = "/reward/del/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delReward( long id){														   
		return userResumeService.delReward(id);
	}
	
	/**
	 * 导出基本信息Excel。 
	 */
	@RequestMapping(value="/export")
	public ModelAndView viewExcel(){
		Map<String,Object> model = new HashMap<String,Object>();
		List<UserResumeDTO> list= userResumeService.findAllResume();
		model.put("resume", list);
		model.put("fileName", "员工信息表.xls");
		return new ModelAndView(new ResumeReportExportXlsView(), model); 
	}
	
	/**
	 * 上传图片。
	 */
	@RequestMapping(value="/upload/{id}")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
    		@PathVariable Long id , ModelMap  model,  RedirectAttributes redirectAttributes) { 
		
		UserResume resume = userResumeRepository.findUserResumeById(id);
		
        String path = request.getSession().getServletContext().getRealPath("/") + "upload/photo"; 
        String originalName = file.getOriginalFilename();
        String postfix = originalName.substring(originalName.length() - 4,originalName.length());
        String fileName = resume.getUser().getEmpnumber() + postfix;
        File targetFile = new File(path, fileName);  
        
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile); 
            resume.setPicture(fileName);
            userResumeRepository.save(resume);
        } catch (Exception e) { 
            e.printStackTrace();  
        }  
        redirectAttributes.addFlashAttribute("tip", "操作成功！");
        return "redirect:/sysconfig/resume/edit?reid="+ id;  	
   }
	@RequestMapping(value = "/getposition/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String,String> getposition(@PathVariable long id) {
		UserResume userResume=userResumeService.findByUser(userService.findOne(id));
		Map<String,String> map=new HashMap<String,String>();
		map.put("position",userResume==null?"": codeService.findPositionName(String.valueOf(userResume.getPosition())));
		return map;
	}
	

	/**
	 * 保存离职信息
	 * @return
	 */
	@RequestMapping(value="/dimission/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveDismission(@ModelAttribute UserResumeDimission dimission){
		Long id = dimission.getId();
		if (id != null && id > 0) {

			UserResumeDimission entity = userResumeDimissionService.findOne(dimission.getId());
			entity.setActualdate(dimission.getActualdate());
			entity.setApplydate(dimission.getApplydate());
			entity.setPlandate(dimission.getPlandate());
			entity.setBlacklist(dimission.getBlacklist());
			entity.setLastupdateDate(new Date());
			entity.setReason(dimission.getReason());
			entity.setRemarks(dimission.getRemarks());
			entity.setType(dimission.getType());
			userResumeDimissionService.save(entity);
		}else{
			Long userId = dimission.getUserId();
			if (userId != null && userId > 0) {
				dimission.setUserId(userId);
			}else{
				UserResume ur = userResumeService.findOne(dimission.getResumeId());
				dimission.setUserId(ur.getUser() == null? null : ur.getUser().getId());
			}
			dimission.setCreater(RightUtil.getCurrentUserId());
			dimission.setCreateDate(new Date());
			dimission.setLastupdateDate(new Date());
			userResumeDimissionService.save(dimission);
		}
		
		return new SuccessResponse();
	}
	

	/**
	 * 合同管理页面 
	 * 
	 */
	@RequestMapping(value = "/contract", method = RequestMethod.GET)
	public ModelAndView contract() {
		log.info("index:");
		ModelAndView mav = new ModelAndView("resume/contract");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "合同管理");
		breadCrumbMap.put("url", "sysconfig/resume/contract");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("contractType", codeService.getContractConfig());
		return mav;
	}
	
	/**
	 * 调整到合同相信页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contractinfo", method = RequestMethod.GET)
	public ModelAndView contractinfo(@RequestParam Long id) {
		ModelAndView mav = new ModelAndView("resume/contractinfo");
		UserContract contract = userContractService.findOne(id);
		User u = userService.findOne(contract.getUserId());
		contract.setChinesename(u == null? "" : u.getChinesename());
		contract.setContractTypeName(codeService.findContractTypeName(contract.getContractType()));
		mav.addObject("contract", contract);
		return mav;
	}
	
	@RequestMapping(value = "/contract/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findContractList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Date signDate, Date fromDate, Date endDate, Long userId){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<UserContract> page = userContractService.findAll(pageNumber, pageSize, signDate, fromDate, endDate, userId);
		if(page != null){
			for(UserContract contract : page){
				User u = userService.findOne(contract.getUserId());
				if (u != null) {
					contract.setChinesename(u.getChinesename());
					contract.setDeptname(u.getDept() == null? "": u.getDept().getName());
				}
				contract.setContractTypeName(codeService.findContractTypeName(contract.getContractType()));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	

	@RequestMapping(value="/contract/query/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserContract queryContract(@PathVariable Long id){
		return userContractService.findOne(id);
	}
	
	@RequestMapping(value="/contract/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveContract(@ModelAttribute UserContract contract){
		contract.setCreater(RightUtil.getCurrentUserId());
		contract.setLastUpdateDate(new Date());
		userContractService.save(contract);
		return new SuccessResponse();
	}
	
	@RequestMapping(value="/contract/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteContract(Long id){
		userContractService.delete(id);
		return new SuccessResponse();
	}
	
	/**
	 * 离职管理页面 
	 * 
	 */
	@RequestMapping(value = "/dimission", method = RequestMethod.GET)
	public ModelAndView dimission() {
		log.info("index:");
		ModelAndView mav = new ModelAndView("resume/dimission");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "离职管理");
		breadCrumbMap.put("url", "sysconfig/resume/dimission");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("dimissionType", codeService.getDimissionConfig());	//离职类型
		List<UserResume> resume = userResumeService.findAll();
		Map<String, Object> m = new LinkedHashMap<String, Object>();
		if (resume != null) {
			for(UserResume r : resume){
				UserResumeDimission dimission = userResumeDimissionService.findByResumeId(r.getId());
				if (dimission == null) {
					//没有离职的返回
					User u = r.getUser();
					m.put(u.getId().toString(), u.getChinesename());
				}
			}
		}
		mav.addObject("resume", m);
		return mav;
	}
	
	/**
	 * 调整到离职相信页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dimissioninfo", method = RequestMethod.GET)
	public ModelAndView dimissioninfo(@RequestParam Long id) {
		ModelAndView mav = new ModelAndView("resume/dimissioninfo");
		UserResumeDimission dimission = userResumeDimissionService.findOne(id);
		User u = userService.findOne(dimission.getUserId());
		dimission.setChinesename(u.getChinesename());
		dimission.setDeptname(u.getDept() == null ? "" : u.getDept().getName());
		mav.addObject("dimission", dimission);
		return mav;
	}
	
	@RequestMapping(value="/dimission/query/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserResumeDimission queryDimission(@PathVariable Long id){
		UserResumeDimission dimission = userResumeDimissionService.findOne(id);
		User u = userService.findOne(dimission.getUserId());
		dimission.setChinesename(u == null? "" : u.getChinesename());
		dimission.setDeptname(u == null? "" : u.getDept().getName());
		dimission.setDimissionTypeName(codeService.findDimissionTypeName(dimission.getType()));
		return dimission;
	}
	
	@RequestMapping(value="/dimission/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteDimission(Long id){
		userResumeDimissionService.delete(id);
		return new SuccessResponse();
	}
	
	@RequestMapping(value = "/dimission/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDimissionList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long userId, Date planDate_s, Date planDate_e, Date actualDate_s, Date actualDate_e, Integer dismission_type){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<UserResumeDimission> page = userResumeDimissionService.findAll(pageNumber, pageSize, userId, planDate_s, planDate_e, actualDate_s, actualDate_e, dismission_type);
		if (page != null) {
			for(UserResumeDimission dimission : page){
				User u = userService.findOne(dimission.getUserId());
				dimission.setChinesename(u == null? "" : u.getChinesename());
				dimission.setDeptname(u == null? "" : u.getDept().getName());
				dimission.setDimissionTypeName(codeService.findDimissionTypeName(dimission.getType()));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}
