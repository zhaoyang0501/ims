package com.hsae.ims.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

import com.hsae.ims.dto.UserAwardDTO;
import com.hsae.ims.dto.UserBeforeExpDTO;
import com.hsae.ims.dto.UserEducationDTO;
import com.hsae.ims.dto.UserFamilyDTO;
import com.hsae.ims.dto.UserNowExpDTO;
import com.hsae.ims.dto.UserResumeDTO;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserAward;
import com.hsae.ims.entity.UserBeforeExp;
import com.hsae.ims.entity.UserEducation;
import com.hsae.ims.entity.UserFamily;
import com.hsae.ims.entity.UserNowExp;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.entity.UserResumeDimission;
import com.hsae.ims.repository.DeptRepository;
import com.hsae.ims.repository.UserAwardRepository;
import com.hsae.ims.repository.UserBeforeExpRepository;
import com.hsae.ims.repository.UserEducationRepository;
import com.hsae.ims.repository.UserFamilyRepository;
import com.hsae.ims.repository.UserNowExpRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.utils.DateTimeUtil;

@Service
public class UserResumeService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private UserResumeRepository userResumeRepository;
	@Autowired
	private UserAwardRepository userAwardRepository;
	@Autowired
	private UserEducationRepository userEducationRepository;
	@Autowired
	private UserFamilyRepository userFamilyRepository;
	@Autowired
	private UserBeforeExpRepository userBeforeExpRepository;
	@Autowired
	private UserNowExpRepository userNowExpRepository;
	@Autowired
	private CodeService codeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserResumeDimissionService userResumeDimissionService; 
	
	public UserResume findOne(Long id){
		return userResumeRepository.findOne(id);
	}
	
	public  UserResume findByUser(User user){
		return userResumeRepository.findUserResumeByUser(user);
	}
	private PageRequest buildPageRequest(String sSortDir_0, int pageNumber, int pagzSize) {
		List<String> list = new ArrayList<String>();
		list.add("user");
		Direction direction = Direction.ASC;
		if (StringUtils.isNotBlank(sSortDir_0)) {
			list.add("user");
			if (sSortDir_0.toUpperCase().equals("DESC")) {
				direction = Direction.DESC;	
			}
		}
		Sort sort = new Sort(direction, list);
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 档案信息列表。
	 * 
	 */
	public Page<UserResume> getUserResumeDate(String sSortDir_0 ,int pageNumber, int pageSize ,String chinesename, String empnumber, Long deptId, Integer state){
		PageRequest pageRequest = buildPageRequest(sSortDir_0, pageNumber, pageSize);
		Specification<UserResume> spec = resumeBuildSpecification(chinesename, empnumber, deptId, state);
		return (Page<UserResume>) userResumeRepository.findAll(spec, pageRequest);
	}
	
	
	private Specification<UserResume> resumeBuildSpecification(final String chinesename,final String empnumber,final Long deptId,final Integer state){
		return new Specification<UserResume>() {
			@Override
			public Predicate toPredicate(Root<UserResume> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<Predicate>();
				
				
				if (StringUtils.isNotEmpty(chinesename)) {
					Join<UserResume,User> userJoin = root.join(root.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
	                predicate.add(cb.like(userJoin.get("chinesename").as(String.class), "%" + chinesename+ "%"));
				}
				if (StringUtils.isNotEmpty(empnumber)) {
					Join<UserResume,User> userJoin = root.join(root.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
	                predicate.add(cb.like(userJoin.get("empnumber").as(String.class), "%" + empnumber+ "%"));
				}
				if (deptId != null) {
					Join<UserResume,User> userJoin = root.join(root.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
					predicate.add(cb.equal(userJoin.get("dept").as(Deptment.class), deptRepository.findOne(deptId)));
				}
				if (state != null) {
					Join<UserResume,User> userJoin = root.join(root.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
					predicate.add(cb.equal(userJoin.get("freeze").as(Integer.class), state));				}
				Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
				}
			};
		}

	/**
	 * 获取个人档案。
	 */
	public UserResumeDTO findResumeById(long id){
		UserResume rs = userResumeRepository.findUserResumeById(id);
		if (rs ==null){
			rs = new UserResume();
		}
		UserResumeDTO dto = new UserResumeDTO();
		dto.setId(rs.getId()==0?0:rs.getId());
		dto.setUserId(rs.getUser()==null?"":rs.getUser().getId().toString());
		dto.setUserName(rs.getUser()==null?"":rs.getUser().getChinesename());
		dto.setEmpnumber(rs.getUser()==null?"":rs.getUser().getEmpnumber());
		dto.setSex(rs.getUser()==null?"":rs.getUser().getSex().equals("1")?"男":"女");
		dto.setDept(rs.getUser()==null?"":rs.getUser().getDept()==null?"":rs.getUser().getDept().getName());
		dto.setEmail(rs.getUser()==null?"":rs.getUser().getEmail()==null?"":rs.getUser().getEmail());
		dto.setDeptId(rs.getUser()==null?0:rs.getUser().getDept()==null?0:rs.getUser().getDept().getId());
		dto.setSexId(rs.getUser()==null?1:Long.parseLong(rs.getUser().getSex()));
		dto.setPicture(rs.getPicture()== null?"":rs.getPicture());

		dto.setMarrigeId(rs.getMarrige()==0?0:rs.getMarrige());
		dto.setPlace(rs.getPlace()==null?"":rs.getPlace());
		dto.setNation(rs.getNation()==null?"":rs.getNation());
		dto.setMarrige(rs.getMarrige()==1?"已婚":"未婚");
		dto.setPoliticsStatus(rs.getPoliticsStatus()==0?"":codeService.findPoliticsStatus(rs.getPoliticsStatus().toString()));
		dto.setDomicilePlace(rs.getDomicilePlace()==null?"":rs.getDomicilePlace());
		dto.setIdNumber(rs.getIdNumber()==null?"":rs.getIdNumber());
		dto.setIdAddress(rs.getIdAddress()==null?"":rs.getIdAddress());
		dto.setEducation(rs.getEducation()==0?"":codeService.findHighestEducation(rs.getEducation().toString()));
		dto.setDegree(rs.getDegree()==0?"":codeService.findHighestDegree(rs.getDegree().toString()));
		dto.setMajor(rs.getMajor()==null?"":rs.getMajor());
		dto.setSchool(rs.getSchool()==null?"":rs.getSchool());

		dto.setAddress(rs.getAddress()==null?"":rs.getAddress());
		dto.setMobile(rs.getMobile()==null?"":rs.getMobile());
		dto.setBirthday(rs.getBirthday()==null?"":rs.getBirthday().toString());
		dto.setGraduateTime(rs.getGraduateTime()==null?"":rs.getGraduateTime().toString());
		dto.setJoinTime(rs.getJoinTime()==null?"":rs.getJoinTime().toString());
		dto.setConversionTime(rs.getConversionTime()==null?"":rs.getConversionTime().toString());
		dto.setPost(rs.getPost()==0?"":codeService.findPostName(rs.getPost().toString()));
		dto.setTitle(rs.getTitle()==0?"":codeService.findTitleName(rs.getTitle().toString()));
		dto.setPosition(rs.getPosition()==0?"":codeService.findPositionName(rs.getPosition().toString()));
		dto.setGrade(rs.getGrade()==0?"":codeService.findGradeName(rs.getGrade().toString()));
		dto.setContractStartDate(rs.getContractStartDate()==null?"":rs.getContractStartDate().toString());
		dto.setContractEndDate(rs.getContractEndDate()==null?"":rs.getContractEndDate().toString());
		dto.setSocialMoney(rs.getSocialMoney());
		dto.setSocialSecurityType(rs.getSocialSecurityType()==0?"":codeService.findAccumulationFundOfSocialSecurity(rs.getSocialSecurityType().toString()));
		dto.setPublicMoney(rs.getPublicMoney());
		dto.setPublicMoneyType(rs.getPublicMoneyType()==0?"":codeService.findAccumulationFundOfSocialSecurity(rs.getPublicMoneyType().toString()));
		dto.setWorkYear(rs.getWorkYear()==null?"":rs.getWorkYear());
		dto.setHobbies(rs.getHobbies()==null?"":rs.getHobbies());
		dto.setSkill(rs.getSkill()==null?"":rs.getSkill());
		if (rs.getLanguage()!=null){
			StringBuffer language = new StringBuffer();
			for (String lg : rs.getLanguage().split(",")){
				language.append(codeService.findLanguageType(lg)==null?"":codeService.findLanguageType(lg)+",");
			}
			dto.setLanguage(language.toString());
		}else {dto.setLanguage("");}
		
		UserResumeDimission dimission = userResumeDimissionService.findByResumeId(rs.getId());
		if (dimission != null && dimission.getId() > 0) {
			dto.setDepartureTime(dimission.getActualdate().toString());
			dto.setLeavingReasons(dimission.getReason());
		}
		return dto;
	}
	
	/**
	 * 获取奖惩信息。
	 */
	public List<UserAwardDTO> findUserAwardByResumeId(long id){
		List<UserAwardDTO> listDTO = new ArrayList<UserAwardDTO>();
		List<UserAward> list = userAwardRepository.findUserAwardByResumeId(id);
		UserAwardDTO dto = null;
		for (UserAward ua :list){
			dto = new UserAwardDTO();
			dto.setId(ua.getId()==0?0:ua.getId());
			dto.setAwardDate(ua.getAwardDate()==null?"":ua.getAwardDate().toString());
			dto.setAwardType(ua.getAwardType()==null?"":ua.getAwardType());
			dto.setAwardReason(ua.getAwardReason()==null?"":ua.getAwardReason());
			dto.setAwardContent(ua.getAwardContent()==null?"":ua.getAwardContent());
			dto.setIdentifier(ua.getIdentifier()==1?"惩罚":"奖励");
			listDTO.add(dto);
		}
		return listDTO;
	}
	
	/**
	 * 获取教育信息。
	 */
	public List<UserEducationDTO> findUserEducationByResumeId(long id){
		List<UserEducationDTO> listDTO = new ArrayList<UserEducationDTO>();
		List<UserEducation> list = userEducationRepository.findUserEducationByResumeId(id);
		UserEducationDTO dto = null;
		for (UserEducation ue: list){
			dto = new UserEducationDTO();
			dto.setId(ue.getId()==0?0:ue.getId());
			dto.setEduStartTime(ue.getEduStartTime()==null?"":ue.getEduStartTime().toString());
			dto.setEduEndTime(ue.getEduEndTime()==null?"":ue.getEduEndTime().toString());
			dto.setEduSchool(ue.getEduSchool()==null?"":ue.getEduSchool());
			dto.setEduMajor(ue.getEduMajor()==null?"":ue.getEduMajor());
			dto.setEduCertificate(ue.getEduCertificate()==null?"":ue.getEduCertificate());
			dto.setEduWay(ue.getEduWay()==null?"":ue.getEduWay());
			listDTO.add(dto);
		}
		return listDTO;
	}
	
	/**
	 * 获取家庭信息。
	 */
	public List<UserFamilyDTO> findUserFamilyByResumeId(long id){
		List<UserFamilyDTO> listDTO = new ArrayList<UserFamilyDTO>();
		List<UserFamily> list = userFamilyRepository.findUserFamilyByResumeId(id);
		UserFamilyDTO dto = null;
		for (UserFamily uf :list) {
			dto = new UserFamilyDTO();
			dto.setId(uf.getId()==0?0:uf.getId());
			dto.setFamilyName(uf.getFamilyName()==null?"":uf.getFamilyName());
			dto.setFamilyTitle(uf.getFamilyTitle()==null?"":uf.getFamilyTitle());
			dto.setFamilyMobile(uf.getFamilyMobile()==null?"":uf.getFamilyMobile());
			dto.setFamilyCompany(uf.getFamilyCompany()==null?"":uf.getFamilyCompany());
			dto.setFamilyAge(uf.getFamilyAge());
			listDTO.add(dto);
		}
		return listDTO;
	}
	
	
	/**
	 * 获取之前的工作经历。
	 */
	public List<UserBeforeExpDTO> findUserBeforeExpByResumeId(long id){
		List<UserBeforeExpDTO> listDTO = new ArrayList<UserBeforeExpDTO>();
		List<UserBeforeExp> list = userBeforeExpRepository.findUserBeforeExpByResumeId(id);
		UserBeforeExpDTO dto = null;
		for (UserBeforeExp ube : list){
			dto = new UserBeforeExpDTO();
			dto.setId(ube.getId()==0?0:ube.getId());
			dto.setBeforeStartTime(ube.getBeforeStartTime()==null?"":ube.getBeforeStartTime().toString());
			dto.setBeforeEndTime(ube.getBeforeEndTime()==null?"":ube.getBeforeEndTime().toString());
			dto.setBeforeCompany(ube.getBeforeCompany()==null?"":ube.getBeforeCompany());
			dto.setBeforeDept(ube.getBeforeDept()==null?"":ube.getBeforeDept());
			dto.setBeforePosition(ube.getBeforePosition()==null?"":ube.getBeforePosition());
			dto.setBeforePerience(ube.getBeforePerience()==null?"":ube.getBeforePerience());
			dto.setBeforeLeavingReasons(ube.getBeforeLeavingReasons()==null?"":ube.getBeforeLeavingReasons());
			listDTO.add(dto);
		}
		return listDTO;
	}
	
	/**
	 * 获取现在的工作经历。
	 */
	public List<UserNowExpDTO> findUserNowExpByResumeId(long id){
		List<UserNowExpDTO> listDTO = new ArrayList<UserNowExpDTO>();
		List<UserNowExp> list = userNowExpRepository.findUserNowExpByResumeId(id);
		UserNowExpDTO dto = null;
		for (UserNowExp une: list){
			dto = new UserNowExpDTO();
			dto.setId(une.getId()==0?0:une.getId());
			dto.setNowStartTime(une.getNowStartTime()==null?"":une.getNowStartTime().toString());
			dto.setNowEndTime(une.getNowEndTime()==null?"":une.getNowEndTime().toString());
			dto.setNowDept(une.getNowDept()==null?"":une.getNowDept());
			dto.setNowPost(une.getNowPost()==null?"":une.getNowPost());
			dto.setNowPerience(une.getNowPerience()==null?"":une.getNowPerience());
			dto.setNowAlterReasons(une.getNowAlterReasons()==null?"":une.getNowAlterReasons());
			listDTO.add(dto);
		}
		return listDTO;
	}
	
	
	/**
	 * 更新档案信息。
	 */
	public boolean updateBaseInfo(UserResume resume ,String resumeId,User us,String userId, String deptId){
		
		long usId = Long.parseLong(userId);
		
		User user = userRepository.getUserById(usId);
		user.setChinesename(us.getChinesename());
		user.setSex(us.getSex());
		user.setEmail(us.getEmail());
		user.setEmpnumber(us.getEmpnumber());
		
		resume.setUser(user);
		if (!userService.save(deptId,resume.getPosition(),user)){
			return false;
		}
		
		Date  currentDate = new Timestamp(System.currentTimeMillis());
		int day = 0;
		if (resume.getJoinTime()!=null){
			day = DateTimeUtil.getDaysBetweenDates(resume.getJoinTime(), currentDate);
		}
		
		Integer year = (int) Math.floor(day / 365);
		if (year >0){
			resume.setWorkYear(year.toString());
		}else{
			resume.setWorkYear("");
		}
		
		//离职时间不为空冻结用户。
		UserResumeDimission dimission = userResumeDimissionService.findByResumeId(resume.getId());
		if (dimission != null && dimission.getId() > 0) {
			userService.freezeUser(usId);
		}else{
			userService.unFreezeUser(usId);
		}
		
		long id = Long.parseLong(resumeId);
		resume.setId(id);
		
		UserResume rs = userResumeRepository.findUserResumeById(id);
		resume.setPicture(rs.getPicture());

		UserResume uResume = userResumeRepository.save(resume);
		if (uResume != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 保存家庭信息。
	 */
	public boolean saveFamily(List<UserFamily> family,long resumeId){
		UserFamily uFamily = null;
		List<UserFamily> list = new ArrayList<UserFamily>();
		userFamilyRepository.delByResumeId(resumeId);
		for (UserFamily uf : family){
			uFamily = new UserFamily();
			uf.setResume(resumeId);
			uFamily = userFamilyRepository.save(uf);
			list.add(uFamily);
		}
		
		if (list.iterator()!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除家庭信息。
	 */
	public boolean delFamily (long id){
		userFamilyRepository.delFamilyById(id);
		UserFamily uf = userFamilyRepository.findOne(id);
		if (uf == null ){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 保存教育信息。
	 */
	public boolean saveEdu(List<UserEducation> edu,long resumeId){
		UserEducation uEdu = null;
		List<UserEducation> list = new ArrayList<UserEducation>();
		userEducationRepository.delByResumeId(resumeId);
		for (UserEducation ue : edu){
			uEdu = new UserEducation();
			ue.setResume(resumeId);
			uEdu = userEducationRepository.save(ue);
			list.add(uEdu);
		}
		
		if (list.iterator()!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除教育信息。
	 */
	public boolean delEdu (long id){
		userEducationRepository.delEduById(id);
		UserEducation uf = userEducationRepository.findOne(id);
		if (uf == null ){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 保存公司外经历。
	 */
	public boolean saveOutCompany(List<UserBeforeExp> bfexp,long resumeId){
		UserBeforeExp exp = null;
		List<UserBeforeExp> list = new ArrayList<UserBeforeExp>();
		userBeforeExpRepository.delByResumeId(resumeId);
		for (UserBeforeExp ube : bfexp){
			exp = new UserBeforeExp();
			ube.setResume(resumeId);
			exp = userBeforeExpRepository.save(ube);
			list.add(exp);
		}
		
		if (list.iterator()!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除公司外经历。
	 */
	public boolean delOutCompany (long id){
		userBeforeExpRepository.delBeforeExpById(id);
		UserBeforeExp uf = userBeforeExpRepository.findOne(id);
		if (uf == null ){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 保存现在公司经历。
	 */
	public boolean saveInCompany(List<UserNowExp> nexp,long resumeId){
		UserNowExp ne = null;
		List<UserNowExp> list = new ArrayList<UserNowExp>();
		userNowExpRepository.delByResumeId(resumeId);
		for (UserNowExp une : nexp){
			ne = new UserNowExp();
			une.setResume(resumeId);
			ne = userNowExpRepository.save(une);
			list.add(ne);
		}
		
		if (list.iterator()!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除现在公司经历。
	 */
	public boolean delInCompany (long id){
		userNowExpRepository.delNowExpById(id);
		UserNowExp uf = userNowExpRepository.findOne(id);
		if (uf == null ){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 保存奖惩信息。
	 */
	public boolean saveReward(List<UserAward> award,long resumeId){
		UserAward ad = null;
		List<UserAward> list = new ArrayList<UserAward>();
		userAwardRepository.delByResumeId(resumeId);
		for (UserAward uad : award){
			ad = new UserAward();
			uad.setResume(resumeId);
			ad = userAwardRepository.save(uad);
			list.add(ad);
		}
		
		if (list.iterator()!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除奖惩信息。
	 */
	public boolean delReward (long id){
		userAwardRepository.delAwardById(id);
		UserAward uf = userAwardRepository.findOne(id);
		if (uf == null ){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 导出员工基础信息EXCEl。
	 */
	public List<UserResumeDTO> findAllResume (){
		List<UserResumeDTO> resume = new ArrayList<UserResumeDTO>();
		Iterable<UserResume> list = userResumeRepository.findAll();
		UserResumeDTO dto = null;
		for (UserResume rs : list){
			dto = new UserResumeDTO();
			dto.setId(rs.getId());
			dto.setUserId(rs.getUser()==null?"":rs.getUser().getId().toString());
			dto.setUserName(rs.getUser()==null?"":rs.getUser().getChinesename());
			dto.setEmpnumber(rs.getUser()==null?"":rs.getUser().getEmpnumber());
			dto.setSex(rs.getUser()==null?"":rs.getUser().getSex().equals("1")?"男":"女");
			dto.setDept(rs.getUser()==null?"":rs.getUser().getDept()==null?"":rs.getUser().getDept().getName());
			dto.setEmail(rs.getUser()==null?"":rs.getUser().getEmail()==null?"":rs.getUser().getEmail());
			dto.setDeptId(rs.getUser()==null?0:rs.getUser().getDept()==null?0:rs.getUser().getDept().getId());
			dto.setSexId(rs.getUser()==null?1:Long.parseLong(rs.getUser().getSex()));
			dto.setState(rs.getUser()==null?0:rs.getUser().getFreeze());
			dto.setMarrigeId(rs.getMarrige());
			dto.setPlace(rs.getPlace()==null?"":rs.getPlace());
			dto.setNation(rs.getNation()==null?"":rs.getNation());
			dto.setMarrige(rs.getMarrige()==1?"已婚":"未婚");
			dto.setPicture(rs.getPicture()== null?"":rs.getPicture());
			dto.setPoliticsStatus(rs.getPoliticsStatus()==0?"":codeService.findPoliticsStatus(rs.getPoliticsStatus().toString()));
			dto.setDomicilePlace(rs.getDomicilePlace()==null?"":rs.getDomicilePlace());
			dto.setIdNumber(rs.getIdNumber()==null?"":rs.getIdNumber());
			dto.setIdAddress(rs.getIdAddress()==null?"":rs.getIdAddress());
			dto.setEducation(rs.getEducation()==0?"":codeService.findHighestEducation(rs.getEducation().toString()));
			dto.setDegree(rs.getDegree()==0?"":codeService.findHighestDegree(rs.getDegree().toString()));
			dto.setMajor(rs.getMajor()==null?"":rs.getMajor());
			dto.setSchool(rs.getSchool()==null?"":rs.getSchool());
			dto.setAddress(rs.getAddress()==null?"":rs.getAddress());
			dto.setMobile(rs.getMobile()==null?"":rs.getMobile());
			dto.setBirthday(rs.getBirthday()==null?"":rs.getBirthday().toString());
			dto.setGraduateTime(rs.getGraduateTime()==null?"":rs.getGraduateTime().toString());
			dto.setJoinTime(rs.getJoinTime()==null?"":rs.getJoinTime().toString());
			dto.setConversionTime(rs.getConversionTime()==null?"":rs.getConversionTime().toString());
			dto.setPost(rs.getPost()==0?"":codeService.findPostName(rs.getPost().toString()));
			dto.setTitle(rs.getTitle()==0?"":codeService.findTitleName(rs.getTitle().toString()));
			dto.setPosition(rs.getPosition()==0?"":codeService.findPositionName(rs.getPosition().toString()));
			dto.setGrade(rs.getGrade()==0?"":codeService.findGradeName(rs.getGrade().toString()));
			dto.setContractStartDate(rs.getContractStartDate()==null?"":rs.getContractStartDate().toString());
			dto.setContractEndDate(rs.getContractEndDate()==null?"":rs.getContractEndDate().toString());
			dto.setSocialMoney(rs.getSocialMoney());
			dto.setSocialSecurityType(rs.getSocialSecurityType()==0?"":codeService.findAccumulationFundOfSocialSecurity(rs.getSocialSecurityType().toString()));
			dto.setPublicMoney(rs.getPublicMoney());
			dto.setPublicMoneyType(rs.getPublicMoneyType()==0?"":codeService.findAccumulationFundOfSocialSecurity(rs.getPublicMoneyType().toString()));
			dto.setWorkYear(rs.getWorkYear()==null?"":rs.getWorkYear());
			dto.setHobbies(rs.getHobbies()==null?"":rs.getHobbies());
			dto.setSkill(rs.getSkill()==null?"":rs.getSkill());
			if (rs.getLanguage()!=null){
				StringBuffer language = new StringBuffer();
				for (String lg : rs.getLanguage().split(",")){
					language.append(codeService.findLanguageType(lg)==null?"":codeService.findLanguageType(lg)+",");
				}
				dto.setLanguage(language.toString());
			}else {dto.setLanguage("");}
			
			UserResumeDimission dimission = userResumeDimissionService.findByResumeId(rs.getId());
			if (dimission != null && dimission.getId() > 0) {
				dto.setDepartureTime(dimission.getActualdate().toString());
				dto.setLeavingReasons(dimission.getReason());
			}
			resume.add(dto);
		}
		return resume;
	}
	public List<UserResume> findAll() {
		return (List<UserResume>)userResumeRepository.findAll();
	}
	
}
