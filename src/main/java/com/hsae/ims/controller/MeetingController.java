package com.hsae.ims.controller;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


















import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.dto.MeetingReserveDTO;
import com.hsae.ims.dto.MeetingRoomDTO;
import com.hsae.ims.entity.Materiel;
import com.hsae.ims.entity.MeetingReserve;
import com.hsae.ims.entity.MeetingRoom;
import com.hsae.ims.repository.MeetingReserveRepository;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DeptService;
import com.hsae.ims.service.MeetingService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;


@Controller
@RequestMapping("/meeting")
public class MeetingController {
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;
	
	@Autowired
	private  MeetingReserveRepository meetingReserveRepository;
	/**
	 * 会议室表。 
	 * 
	 */
	@RequestMapping(value = "/roominfo", method = RequestMethod.GET)
	public ModelAndView roomIndex() {
		ModelAndView mav = new ModelAndView("meeting/roominfo");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "会议室管理");
		breadCrumbMap.put("url", "meeting/roominfo");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/**
	 * 会议室信息详情。 
	 */
	@RequestMapping(value = "/roomlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRoomInfoList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String roomname, String roomseat, String roomstatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<MeetingRoom> page = meetingService.getRoomInfoDate(sSortDir_0, pageNumber, pageSize, roomname, roomseat, roomstatus);
		List<MeetingRoomDTO> dtoList = new ArrayList<MeetingRoomDTO>();
		if (page != null && page.getTotalElements() > 0) {
			MeetingRoomDTO dto = null;
			StringBuffer materiel = null;

			for (MeetingRoom p : page) {
				dto = new MeetingRoomDTO();
				materiel = new StringBuffer();
				dto.setId(p.getId());
				dto.setRoomName(p.getRoomName()== null?"":p.getRoomName());
				dto.setRoomSeat(p.getRoomSeat());
				dto.setRoomAddress(p.getRoomAddress()== null?"":p.getRoomAddress());
				dto.setRoomDescription(p.getRoomDescription()==null?"":p.getRoomDescription());
				dto.setRoomCreateTime(p.getRoomCreateTime());
				dto.setRoomStatus(p.getRoomStatus());
				if (p.getMateriel().isEmpty()){
					dto.setMateriel("");
				}else{
					for (Materiel m : p.getMateriel()){
						materiel.append(m.getMaterielName()==null?"":m.getMaterielName()+"，");
					}
					dto.setMateriel(materiel.deleteCharAt(materiel.length()-1).toString());
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
	
	/***
	 * 面包屑
	 * @param url
	 * @param name
	 * @return
	 */
//	private Map<String,String> getBreadCrumbMap(String url,String name){
//		Map<String,String> breadCrumbMap = new HashMap<String, String>();
//		breadCrumbMap.put("name", name);
//		breadCrumbMap.put("url", url);
//		return breadCrumbMap;
//	}
	
	
	/**
	 * 新建会议室。 
	 */
	@RequestMapping(value = "/roomcreate", method = RequestMethod.POST)
	@ResponseBody
	public Integer createRoom (@ModelAttribute("meetingroom") MeetingRoom meetingroom,String roommateriel){														   
		return meetingService.saveRoom(meetingroom, roommateriel);
	}
	
	/**
	 * 修改会议室。 
	 */
	@RequestMapping(value = "/roomupdate", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateRoom (@ModelAttribute("meetingroom") MeetingRoom meetingroom,String roommateriel){														   
		return meetingService.saveRoom(meetingroom, roommateriel);
	}
	
	/**
	 * 查找指定会议室。
	 */
	@RequestMapping(value = "/roomquery/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MeetingRoom queryRoom(@PathVariable long id) {
		return meetingService.findOneRoom(id);
	}
	
	/**
	 * 删除会议室。 
	 */	
	@RequestMapping(value = "/roomdelete", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(long id) {
		return meetingService.deleteRoom(id);
	}
	
	
	/**
	 * 可预约会议室列表。 
	 * 
	 */
	@RequestMapping(value = "/reserveinfo", method = RequestMethod.GET)
	public ModelAndView reserveIndex() {
		ModelAndView mav = new ModelAndView("meeting/reserveinfo");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "会议室");
		breadCrumbMap.put("url", "meeting/reserveinfo");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);

		return mav;
	}
	
	/**
	 * 预约会议。
	 */
	@RequestMapping(value = "/reserveinfo/index", method = RequestMethod.GET)
	public ModelAndView reserve(@RequestParam("rm") long id) {
		ModelAndView mav = new ModelAndView("meeting/reserve/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "会议室");
		breadCrumbMap.put("url", "meeting/reserveinfo");
		breadCrumbList.add(breadCrumbMap);
		Map<String, String> breakcumbMap1 = new HashMap<String, String>();
		breakcumbMap1.put("url", "meeting/reserveinfo/index?rm="+id);
		breakcumbMap1.put("name", "会议预约");
		breadCrumbList.add(breakcumbMap1);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("meeting", codeService.MeetingTypeConfig());
		mav.addObject("room", meetingService.findOneRoom(id));
		mav.addObject("initiator", RightUtil.getCurrentChinesename());
		mav.addObject("rm", id);
		mav.addObject("alluser", userService.findValidUser());
		mav.addObject("alldept", deptService.getAllChildDept());

		return mav;
	}
	
	/***
	 * 日历视图查找日志
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findReserveReport(@RequestParam("rm") long roomId,Long start, Long end) throws ParseException {
		List<MeetingReserve> queryList = meetingService.findReserveReportByMonth(new java.util.Date(start*1000),new java.util.Date(end*1000));
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (queryList != null && queryList.size() > 0) {
//			DateFormat df = new SimpleDateFormat("HH:mm");

			for (MeetingReserve reserve : queryList) {
				if (reserve.getRoom().getId()==roomId){
					if (reserve.getMeetingStatus() == 4 && reserve.getFlag() == 0){
						continue;
					}
					if (reserve.getMeetingStatus() == 5 && reserve.getFlag() == 0){
						continue;
					}
					
					Map<String, Object> reserveMap = new HashMap<String, Object>();
					reserveMap.put("id", reserve.getId().toString());
					String titleText = "";
					titleText += reserve.getMeetingName()+"("+userService.findOne(reserve.getCompere()).getChinesename()+")";
//					+df.format(reserve.getMeetingStartTime())+"-"+df.format(reserve.getMeetingEndTime());
					
					if (RightUtil.getCurrentUserId() == reserve.getInitiator()){
						if (reserve.getMeetingStatus() == 1){
							reserveMap.put("color", "#009600");
						}
						if (reserve.getMeetingStatus() == 2){
							reserveMap.put("color", "#F89406");
						}
						if (reserve.getMeetingStatus() == 3){
							reserveMap.put("color", "#CC0101");
						}
						if (reserve.getMeetingStatus() == 4 && reserve.getFlag() == 1){
							reserveMap.put("color", "#999999");
						}
						
					}else{
						reserveMap.put("color", "#0093A8");
					}
					
					reserveMap.put("title", titleText);
					reserveMap.put("start",reserve.getMeetingStartTime().toString());
					reserveMap.put("end", reserve.getMeetingEndTime().toString());
					reserveMap.put("reserve", reserve);
					returnList.add(reserveMap);
				}
			}
		}
		return returnList;
	}
	
	
	/**
	 * 保存预约。 
	 */
	@RequestMapping(value = "/reservesave", method = RequestMethod.POST)
	public String saveReserve (@RequestParam(value = "multifile[]", required = false) MultipartFile multifile[], 
			HttpServletRequest request,@ModelAttribute("meetingreserve") MeetingReserve meetingreserve,
			String convener,String startTime,String endTime, String startDate , String serve1, String serve2, Long roomId,
		    RedirectAttributes redirectAttributes){	
		if (DateTimeUtil.getDaysBetweenMils(DateTimeUtil.getFormatTime(startTime),DateTimeUtil.getFormatTime(endTime)) <0){
		    redirectAttributes.addFlashAttribute("msg", "错误");
			return "redirect:/meeting/reserveinfo/index?rm="+roomId;
		}
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/meeting";
		String filesName = "";
		
		if(multifile != null && multifile.length > 1){
			for(int i = 1; i< multifile.length; i++)
			{
				MultipartFile file = multifile[i];
				String dbFile =  new Date().getTime() + file.getOriginalFilename();
				String fileName = dbFile.substring(0,13) + dbFile.substring(dbFile.lastIndexOf(".")); 
				
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){
		            targetFile.mkdirs();
		        }
				try {
					file.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				filesName += dbFile + ",";
			}
			if (meetingreserve.getId() != null){
				MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
				if (StringUtils.isBlank(mr.getMeetingFile())){
					meetingreserve.setMeetingFile(filesName.substring(0, filesName.length() - 1));
				}else{
					meetingreserve.setMeetingFile(mr.getMeetingFile()+","+filesName.substring(0, filesName.length() - 1));
				}			
			}else{
				meetingreserve.setMeetingFile(filesName.substring(0, filesName.length() - 1));
			}
		}else{
			if (meetingreserve.getId() != null){
				MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
				meetingreserve.setMeetingFile(mr.getMeetingFile());
				meetingreserve.setMeetingSummaryFile(mr.getMeetingSummaryFile());
			}
		}
		
		String beginTime = startDate+" "+startTime+":00";
		String overTime =  startDate+" "+endTime+":00";
		serve1 = serve1==null?"0":"1";
		serve2 = serve2==null?"0":"1";
		String meetingServe = serve1+","+serve2;
		meetingreserve.setMeetingServe(meetingServe);
		meetingreserve.setInitiator(RightUtil.getCurrentUserId());
		
		boolean othen = meetingService.saveReserve(meetingreserve, roomId, beginTime, overTime);
	    redirectAttributes.addFlashAttribute("msg", othen?"成功": "失败");
		return "redirect:/meeting/reserveinfo/index?rm="+roomId;
	}
	
	
	/**
	 * 删除附件。
	 */
	@RequestMapping(value = "/delfile", method = RequestMethod.POST)
	@ResponseBody
	public MeetingReserve delFile(HttpServletRequest request,String filename, Long id, Long op) {
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/meeting";
		String filesName = "";
		if (id != null && op == 0){
			MeetingReserve mr = meetingReserveRepository.findOne(id);
			for (String meetingFile :mr.getMeetingFile().split(",")){
				if (meetingFile.equals(filename)){
					File targetFile = new File(path, filename.substring(0,13) + filename.substring(filename.lastIndexOf(".")));
					if(targetFile.exists()){
						targetFile.delete();
			        }
					meetingFile = "";
					filesName += meetingFile;
					continue;
				}
				filesName += meetingFile+",";
			}
			if (StringUtils.isBlank(filesName)){
				mr.setMeetingFile("");
			}else{
				mr.setMeetingFile(filesName.substring(0, filesName.length() - 1));
			}
			return meetingReserveRepository.save(mr);
		}
		if (id != null && op == 1){
			MeetingReserve mr = meetingReserveRepository.findOne(id);
			for (String summaryFile :mr.getMeetingSummaryFile().split(",")){
				if (summaryFile.equals(filename)){
					File targetFile = new File(path, filename.substring(0,13) + filename.substring(filename.lastIndexOf(".")));
					if(targetFile.exists()){
						targetFile.delete();
			        }
					summaryFile = "";
					filesName += summaryFile;
					continue;
				}
				filesName += summaryFile+",";
			}
			if (StringUtils.isBlank(filesName)){
				mr.setMeetingSummaryFile("");
			}else{
				mr.setMeetingSummaryFile(filesName.substring(0, filesName.length() - 1));
			}
			return meetingReserveRepository.save(mr);
		}
		return null;
	}
	
	/**
	 * 查找指定预约。
	 */
	@RequestMapping(value = "/reservequery/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MeetingReserveDTO queryReserve(@PathVariable long id) {
		return meetingService.findOneReserve(id);
	}
	
	
	/**
	 * 我召集的会议列表。 
	 * 
	 */
	@RequestMapping(value = "/personalcall", method = RequestMethod.GET)
	public ModelAndView personalCall() {
		ModelAndView mav = new ModelAndView("meeting/personalcall");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我召集的会议");
		breadCrumbMap.put("url", "meeting/personalcall");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	/**
	 * 我召集的会议列表。 
	 */
	@RequestMapping(value = "/personallist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> personalList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String startDate, Integer meetingStatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		int pageType = 0; //0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
	    Date beginDate= startDate==null?null:DateTimeUtil.formatEndTime(startDate+" 00:00:00");
	    Date overDate= startDate==null?null:DateTimeUtil.getFormatDateEndfix(startDate);
		Page<MeetingReserve> page =  meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate,overDate, meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	/**
	 * 
	 * 绑定预约会议DTO公共方法。
	 */
	private Map<String , Object> returnMap (Page<MeetingReserve> page, int sEcho){
		List<MeetingReserveDTO> dtoList = new ArrayList<MeetingReserveDTO>();
		if (page != null && page.getTotalElements() > 0) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			MeetingReserveDTO dto = null;
			for (MeetingReserve p : page) {
				dto = new MeetingReserveDTO();
				dto.setId(p.getId());
				dto.setMeetingName(p.getMeetingName()==null?"":p.getMeetingName());
				dto.setMeetingStartDate(DateTimeUtil.getFormatDate(p.getMeetingStartTime()));
				dto.setMeetingStartTime(df.format(p.getMeetingStartTime()));
				dto.setMeetingEndTime(df.format(p.getMeetingEndTime()));
				dto.setRoomName(p.getRoom().getRoomName()==null?"":p.getRoom().getRoomName());
				dto.setRoomId(p.getRoom()==null?null:p.getRoom().getId());
				dto.setMeetingDescription(p.getMeetingDescription()==null?"":p.getMeetingDescription());
				dto.setMeetingCreateTime(p.getMeetingCreateTime()==null?"":DateTimeUtil.getFormatDate(p.getMeetingCreateTime()));
				dto.setMeetingFile(p.getMeetingFile()==null?"":p.getMeetingFile());
				dto.setMeetingSummaryFile(p.getMeetingSummaryFile()==null?"":p.getMeetingSummaryFile());
				dto.setMeetingType(p.getMeetingType());
				dto.setMeetingStatus(p.getMeetingStatus());
				dto.setMeetingSummary(p.getMeetingSummary()==null?"":p.getMeetingSummary());
				dto.setSummaryCreateTime(p.getSummaryCreateTime()==null?"":DateTimeUtil.getFormatDate(p.getSummaryCreateTime()));
				dto.setInitiator(p.getInitiator());
				dto.setInitiatorName(userService.findOne(p.getInitiator()).getChinesename());
				dto.setRegistrar(p.getRegistrar());
				dto.setRegistrarName(userService.findOne(p.getRegistrar()).getChinesename());
				dto.setCompere(p.getCompere());
				dto.setCompereName(userService.findOne(p.getCompere()).getChinesename());
				dto.setAttendee(p.getAttendee());
				dto.setMeetingServe(p.getMeetingServe()==null?"":p.getMeetingServe());
				dto.setMeetingPeriodic(p.getMeetingPeriodic());
				//纪要人==登录人 允许编辑会议纪要。
				dto.setIdentifying(p.getRegistrar() == RightUtil.getCurrentUserId()? 1:0);
				dto.setFlag(p.getFlag());
				dto.setInform(p.getInform());
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
	 * 预约提交审核。
	 */
	@RequestMapping(value = "/reservesubmit", method = RequestMethod.POST)
	@ResponseBody
	public boolean reserveSubmit(long id) {
		return meetingService.saveReserveSubmit(id);
	}
	
	/**
	 * 删除预约。
	 */
	@RequestMapping(value = "/reservedelete", method = RequestMethod.POST)
	@ResponseBody
	public boolean reserveDelete(HttpServletRequest request,long id) {
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/meeting";
		return meetingService.deleteReserve(id,path);
	}
	
	/**
	 * 预约管理。 
	 * 
	 */
	@RequestMapping(value = "/reservemanage", method = RequestMethod.GET)
	public ModelAndView reserveManage() {
		ModelAndView mav = new ModelAndView("meeting/reservemanage");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "预约管理");
		breadCrumbMap.put("url", "meeting/reservemanage");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/**
	 * 预约管理列表。 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/reservelist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reserveList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String startDate, Integer meetingStatus){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
	    Date beginDate= startDate==null?null:DateTimeUtil.formatEndTime(startDate+" 00:00:00");
	    Date overDate= startDate==null?null:DateTimeUtil.getFormatDateEndfix(startDate);
		int pageType = 1; //0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
		Page<MeetingReserve> page = meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate,overDate, meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	/**
	 * 修改预约时间（管理）。 
	 */
	@RequestMapping(value = "/updatetime", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateTime ( Long id ,String startTime,
			String endTime, String startDate , Long roomId){
		if (DateTimeUtil.getDaysBetweenMils(DateTimeUtil.getFormatTime(startTime),DateTimeUtil.getFormatTime(endTime)) <0){
			return false;
		}
		String beginTime = startDate+" "+startTime+":00";
		String overTime =  startDate+" "+endTime+":00";
		MeetingReserve meetingreserve = meetingReserveRepository.findOne(id);
		return meetingService.saveReserve(meetingreserve, roomId, beginTime, overTime);
	}
	
	
	/**
	 * 预约详情。 
	 * 
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	public ModelAndView reserveInfo(@RequestParam("rm") long id) {
		ModelAndView mav = new ModelAndView("meeting/reserve/details");
		mav.addObject("reserve", meetingService.findOneReserve(id));
		return mav;
	}
	
	/**
	 * 预约审核
	 */
	@RequestMapping(value = "/reservecheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean reserveCheck (long id, long op) {
		return meetingService.reserveCheck(id, op);
	}
	
	
	/**
	 * 已参加会议。 
	 * 
	 */
	@RequestMapping(value = "/reservehistory", method = RequestMethod.GET)
	public ModelAndView reserveHistory() {
		ModelAndView mav = new ModelAndView("meeting/reservehistory");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "已参加会议");
		breadCrumbMap.put("url", "meeting/reservehistory");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	/**
	 * 已参加的历史会议列表。 
	 */
	@RequestMapping(value = "/historylist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> historyList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String startDate, Integer meetingStatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		int pageType = 2; //0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
	    Date beginDate= startDate==null?null:DateTimeUtil.formatEndTime(startDate+" 00:00:00");
	    Date overDate= startDate==null?null:DateTimeUtil.getFormatDateEndfix(startDate);
		Page<MeetingReserve> page = meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate, overDate, meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	/**
	 * 会议纪要。 
	 * 
	 */
	@RequestMapping(value = "/meetingsummary", method = RequestMethod.GET)
	public ModelAndView meetingSummary() {
		ModelAndView mav = new ModelAndView("meeting/meetingsummary");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "会议纪要");
		breadCrumbMap.put("url", "meeting/meetingsummary");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/**
	 * 会议纪要列表。 
	 */
	@RequestMapping(value = "/summarylist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> summaryList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String summaryDate, Integer meetingStatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		int pageType = 3; //0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
	    Date beginDate= summaryDate==null?null:DateTimeUtil.formatEndTime(summaryDate+" 00:00:00");
	    Date overDate= summaryDate==null?null:DateTimeUtil.getFormatDateEndfix(summaryDate);
		Page<MeetingReserve> page = meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate, overDate , meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	
	/**
	 * 保存会议纪要。 
	 */
	@RequestMapping(value = "/summarysave", method = RequestMethod.POST)
	public String saveSummary (@RequestParam(value = "multifile[]", required = false) MultipartFile multifile[], 
			HttpServletRequest request,@ModelAttribute("meetingreserve") MeetingReserve meetingreserve,
			RedirectAttributes redirectAttributes){	
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/meeting";
		String filesName = "";
		
		if(multifile != null && multifile.length > 1){
			for(int i = 1; i< multifile.length; i++)
			{
				MultipartFile file = multifile[i];
				String dbFile = new Date().getTime() + file.getOriginalFilename();
				String fileName = dbFile.substring(0,13) + dbFile.substring(dbFile.lastIndexOf("."));
				
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){
		            targetFile.mkdirs();
		        }
				try {
					file.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				filesName += dbFile + ",";
			}
			
			if (meetingreserve.getId() != null){
				MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
				if (StringUtils.isBlank(mr.getMeetingSummaryFile())){
					meetingreserve.setMeetingSummaryFile(filesName.substring(0, filesName.length() - 1));
				}else{
					meetingreserve.setMeetingSummaryFile(mr.getMeetingSummaryFile()+","+filesName.substring(0, filesName.length() - 1));
				}			
			}else{
				meetingreserve.setMeetingSummaryFile(filesName.substring(0, filesName.length() - 1));
			}
		}else{
			if (meetingreserve.getId() != null){
				MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
				meetingreserve.setMeetingFile(mr.getMeetingFile());
				meetingreserve.setMeetingSummaryFile(mr.getMeetingSummaryFile());
			}
		}
		
		boolean othen = meetingService.saveSummary(meetingreserve);
	    redirectAttributes.addFlashAttribute("msg", othen?"成功": "失败");
		return "redirect:/meeting/meetingsummary";
	}
	
	/**
	 * 邮件提醒开会时间。 
	 */	
	@RequestMapping(value = "/sendemail", method = RequestMethod.POST)
	@ResponseBody
	public boolean sendEmail(long id) {
		return meetingService.sendEmail(id);
	}
	
	/**
	 * 邮件提醒写会议纪要。
	 */
	@RequestMapping(value = "/remindsummary", method = RequestMethod.POST)
	@ResponseBody
	public boolean remindSummary(long id) {
		return meetingService.remindSummary(id);
	}
	
	/**
	 * 待参加会议。 
	 * 
	 */
	@RequestMapping(value = "/waitingattend", method = RequestMethod.GET)
	public ModelAndView waitingAttend() {
		ModelAndView mav = new ModelAndView("meeting/waitingattend");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "待参加会议");
		breadCrumbMap.put("url", "meeting/waitingattend");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/**
	 * 待参加会议列表。 
	 */
	@RequestMapping(value = "/attendlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attendList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String startDate, Integer meetingStatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		int pageType = 4; //0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
	    Date beginDate= startDate==null?null:DateTimeUtil.formatEndTime(startDate+" 00:00:00");
	    Date overDate= startDate==null?null:DateTimeUtil.getFormatDateEndfix(startDate);
		Page<MeetingReserve> page = meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate, overDate , meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	/**
	 * 所有历史会议。 
	 * 
	 */
	@RequestMapping(value = "/historymanage", method = RequestMethod.GET)
	public ModelAndView meetingHistory() {
		ModelAndView mav = new ModelAndView("meeting/historymanage");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "历史会议");
		breadCrumbMap.put("url", "meeting/historymanage");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	/**
	 * 所有的历史会议列表。 
	 */
	@RequestMapping(value = "/allhistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> allhistoryList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,  String reserveName,
			Long compere, String startDate, Integer meetingStatus) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		int pageType = 5; //0:我召集的会议 1：预约管理 2：历史会议 3：会议纪要。4:待参加会议 5：所有历史会议。
	    Date beginDate= startDate==null?null:DateTimeUtil.formatEndTime(startDate+" 00:00:00");
	    Date overDate= startDate==null?null:DateTimeUtil.getFormatDateEndfix(startDate);
		Page<MeetingReserve> page = meetingService.getReserveList(sSortDir_0, pageNumber, pageSize, reserveName,
				 compere, beginDate, overDate, meetingStatus, pageType);
		return returnMap(page, sEcho);
	}
	
	/**
	 * 所有我召集的会议首页列表。
	 */
	@RequestMapping(value = "/allpersonal")
	@ResponseBody
	public LinkedList<MeetingReserveDTO> queryPersonal() {
		LinkedList<MeetingReserveDTO> personal = meetingService.queryPersonal();
		return personal;
	}
}
