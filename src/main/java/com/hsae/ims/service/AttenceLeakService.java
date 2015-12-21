package com.hsae.ims.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AttenceLeakService {
	/*
	@Autowired
	private AttenceLeakRepository attenceLeakRepository;
	
	@Autowired
	private AttenceRepository attenceRepository;

	public int create(String userId,String brushDate,String time,String description,String len) {
		User user = new User();
		user.setId(Long.parseLong(userId));
		//
		AttenceLeak leak = new AttenceLeak();
		leak.setUser(user);
		leak.setDescription(description);
		leak.setBrushDate(DateTimeUtil.getFormatDate(brushDate));
		leak.setTime(time);
		leak = attenceLeakRepository.save(leak);
		//
		if(leak == null){
			return 0;
		}else{
			if("1".equals(len)){
				//根据userId 和 brushDate 得到 attence
				Attence attence = attenceRepository.getByUIdAndBrushDate(Long.parseLong(userId), DateTimeUtil.getFormatDate(brushDate));
				return attenceRepository.updateThreeById(attence.getId());
			}else{
				return 1;
			}
			
		}
		return 1;
	}
	
	*//**
	 * 我的漏打卡分页查询的功能
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 *//*
	public Page<AttenceLeak> getMyLeak(int pageNumber,int pageSize,String startDate,String endDate){
		PageRequest pageRequest = bulidPageRequestAttenceLeak(pageNumber, pageSize);
		Specification<AttenceLeak> spec = bulidSpecificationAttenceLeak(DateTimeUtil.getFormatDate(startDate),DateTimeUtil.getFormatDate(endDate));
		return (Page<AttenceLeak>)attenceLeakRepository.findAll(spec,pageRequest);
	}
	
	private PageRequest bulidPageRequestAttenceLeak(int pageNumber,int pageSize){
		return new PageRequest(pageNumber-1,pageSize, new Sort(Direction.ASC, "id"));
	}
	
	*//**
	 * 多条件查询
	 * @param sstatus
	 * @param sbrushdate
	 * @return
	 *//*
	private Specification<AttenceLeak> bulidSpecificationAttenceLeak(Date startDate,Date endDate){
		Map<String,SearchFilter> filters = new HashMap<String,SearchFilter>();
		if(startDate != null && endDate != null){
			List<Date> listDate = new ArrayList<Date>();
			listDate.add(startDate);
			listDate.add(endDate);
			filters.put("brushDate", new SearchFilter("brushDate",Operator.BETWEEN,listDate));
		}else if(startDate != null){
			filters.put("brushDate", new SearchFilter("brushDate",Operator.GTE,startDate));
		}else if(endDate != null){
			filters.put("brushDate", new SearchFilter("brushDate",Operator.LTE,endDate));
		}
		//当开始时间和结束时间为空值时，默认查询当月数据
		if(startDate == null &&endDate == null){
			List<Date> listDate = new ArrayList<Date>();
			listDate.add(DateTimeUtil.getFormatDate(DateTimeUtil.getFirstDayOfMonth()));
			listDate.add(DateTimeUtil.getFormatDate(DateTimeUtil.getLastDayOfMonth()));
			filters.put("brushDate", new SearchFilter("brushDate",Operator.BETWEEN,listDate));
		}
		//获取登录人员的id
		filters.put("user.id", new SearchFilter("user.id",Operator.EQ,RightUtil.getCurrentUserId()));
		Specification<AttenceLeak> spec = 
				DynamicSpecifications.bySearchFilter(filters.values(), AttenceLeak.class, DynamicSpecifications.AND);
		return spec;
	} 
*/
}
