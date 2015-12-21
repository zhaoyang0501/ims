package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Code;
import com.hsae.ims.repository.CodeRepository;

@Service
public class CodeService {
	
	@Autowired
	private CodeRepository codeRepository;
	/***
	 * 找考勤类型
	 * @return
	 */
	public List<Code> findAttenceCode(){
		return findCode("ATTENCETYPE");
	}
	/***
	 * 请假类型
	 * @return
	 */
	public List<Code> findDayOffCode(){
		return findCode("DAYOFFTYPE");
	}
	
	public String findDayOffName(String code){
		Code c = this.findCodeName("DAYOFFTYPE",code);
		return c==null?"":c.getName();
	}
	/***
	 * 漏打卡类型
	 * @returnABSENTEETYPE
	 */
	public List<Code> findAbsenteeType(){
		return findCode("ABSENTEETYPE");
	}
	/***
	 * 获取日报类型
	 * @return
	 */
	public Map<String,String> findDailyTypeCode(){
		 Map<String,String> maps=new HashMap<String,String>() ;
		 List<Code> codes=findCode("DAILYTYPE");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	
	/***
	 * 获取项目阶段2659
	 * @return
	 */
	public List<Code> findProjectStep(){
		return findCode("PROJECTSTEP");
	}
	
	public String findProjectName(String code){
		Code codeObject = findCodeName("PROJECTSTEP",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取项目成员角色
	 * @return
	 */
	public Map<String,String> findProjectMemberRole(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		 List<Code> codes=findCode("PROJECTMEMBERROLE");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	
	public String findProjectMemberRoleName(String code){
		Code codeObject = findCodeName("PROJECTMEMBERROLE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取讲师级别
	 * @return
	 */
	public Map<String,String> findTrainingTeacherLevel(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		 List<Code> codes=findCode("TRAININGTEACHERLEVEL");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	
	public String findTrainingTeacherLevelName(String code){
		Code codeObject = findCodeName("TRAININGTEACHERLEVEL",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取培训类型
	 * @return
	 */
	public Map<String,String> findTrainingType(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		 List<Code> codes=findCode("TRAININGTYPE");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	
	public String findTrainingTypeName(String code){
		Code codeObject = findCodeName("TRAININGTYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取请假的类型
	 * @return
	 */
	public Map<String,String>  findDayOffTypeForMap(){
		 Map<String,String> maps=new HashMap<String,String>() ;
		 List<Code> codes=findCode("DAYOFFTYPE");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	/***
	 * 获取漏打卡类型 的map形式
	 * @return findDayOffCode
	 */
	public Map<String,String>  findAbsenteeTypeForMap(){
		 Map<String,String> maps=new HashMap<String,String>() ;
		 List<Code> codes=findCode("ABSENTEETYPE");
		 for(Code code:codes ){
			 maps.put(code.getCode(), code.getName());
		 }
		 return maps;
	}
	
	/***
	 * 获取图书管理参数。
	 * @return
	 */
	public List<String> findBookManageConfig(){
		
		List<String> bookCode = new ArrayList<String>();
		List<Code> code = findCode("BOOKMANAGECONFIG");
		for(Code cd:code ){
			bookCode.add(cd.getCode()==null?"":cd.getCode());
		}
		return bookCode;
	}
	
	/***
	 * 获取职称。
	 * @return
	 */
	public Map<String,String> titleConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("COMPANYTITLE");
		for(Code cd:code ){
			maps.put(cd.getCode(),cd.getName());
		}
		return maps;
	}
	
	public String findTitleName(String code){
		Code codeObject = findCodeName("COMPANYTITLE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取岗位。
	 * @return
	 */
	public Map<String,String> postConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("COMPANYPOST");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findPostName(String code){
		Code codeObject = findCodeName("COMPANYPOST",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取职位。
	 * @return
	 */
	public Map<String,String> positionConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("COMPANYPOSITION");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findPositionName(String code){
		Code codeObject = findCodeName("COMPANYPOSITION",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取职等。
	 * @return
	 */
	public Map<String,String> gradeConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("COMPANYGRADE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findGradeName(String code){
		Code codeObject = findCodeName("COMPANYGRADE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取公积金、社保。
	 * @return
	 */
	public Map<String,String> accumulationFundOfSocialSecurityConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("ACCUMULATIONFUNDOFSOCIALSECURITY");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findAccumulationFundOfSocialSecurity(String code){
		Code codeObject = findCodeName("ACCUMULATIONFUNDOFSOCIALSECURITY",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取学历。
	 * @return
	 */
	public Map<String,String> highestEducationConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("HIGHESTEDUCATION");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findHighestEducation(String code){
		Code codeObject = findCodeName("HIGHESTEDUCATION",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取学位。
	 * @return
	 */
	public Map<String,String> highestDegreeConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("HIGHESTDEGREE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findHighestDegree(String code){
		Code codeObject = findCodeName("HIGHESTDEGREE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取政治面貌。
	 * @return
	 */
	public Map<String,String> politicsStatusConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("POLITICSSTATUS");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findPoliticsStatus(String code){
		Code codeObject = findCodeName("POLITICSSTATUS",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取语种。
	 * @return
	 */
	public Map<String,String> languageTypeConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("LANGUAGETYPE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findLanguageType(String code){
		Code codeObject = findCodeName("LANGUAGETYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 获取公告种类。
	 * @return
	 */
	public Map<String,String> NewsTypeConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("NEWSTYPE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findNewsType(String code){
		Code codeObject = findCodeName("NEWSTYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	public Code findNewsCode(String code){
		return this.findCodeName("NEWSTYPE", code);
	}
	
	/***
	 * 获取会议类型。
	 * @return
	 */
	public Map<String,String> MeetingTypeConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("MEETINGTYPE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findMeetingType(String code){
		Code codeObject = findCodeName("MEETINGTYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	public Code findMeetingCode(String code){
		return this.findCodeName("MEETINGTYPE", code);
	}
	
	/***
	 * 离职类型。
	 * @return
	 */
	public Map<String,String> getDimissionConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("DIMISSIONTYPE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	public String findDimissionTypeName(String code){
		Code codeObject = findCodeName("DIMISSIONTYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	/***
	 * 合同类型
	 * @return
	 */
	public String findContractTypeName(String code){
		Code codeObject = findCodeName("CONTRACTTYPE",code);
		return codeObject==null?"":codeObject.getName();
	}
	
	public Map<String,String> getContractConfig(){
		Map<String,String> maps=new LinkedHashMap<String,String>() ;
		List<Code> code = findCode("CONTRACTTYPE");
		for(Code cd:code ){
			maps.put(cd.getCode(), cd.getName());
		}
		return maps;
	}
	
	/**private*/
	private  List<Code> findCode(String identification){
		return codeRepository.findByIdentificationOrderBySortNumberAsc(identification);
	}
	
	private Code findCodeName(String identification,String code){
		return  codeRepository.findByIdentificationAndCode(identification, code);
	}
}
