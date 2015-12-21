package com.hsae.ims.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.ExamGooverDTO;
import com.hsae.ims.dto.ExamPaperDto;
import com.hsae.ims.entity.BaseTrainingPlanCourse;
import com.hsae.ims.entity.ExamAnswer;
import com.hsae.ims.entity.ExamAnswerDetails;
import com.hsae.ims.entity.ExamPaper;
import com.hsae.ims.entity.ExamQuestion;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.BaseTrainingPlanCourseService;
import com.hsae.ims.service.ExamAnswerDetailsService;
import com.hsae.ims.service.ExamAnswerService;
import com.hsae.ims.service.ExamPaperService;
import com.hsae.ims.service.ExamQuestionService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.CnUpperCaser;
import com.hsae.ims.utils.EntityDtoConvert;
import com.hsae.ims.utils.RightUtil;

@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController{
	
	@Autowired
	private ExamQuestionService examQuestionService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExamAnswerService examAnswerService;
	@Autowired
	private ExamAnswerDetailsService examAnswerDetailsService;
	@Autowired
	private BaseTrainingPlanCourseService baseTrainingPlanCourseService;

	@RequestMapping(value = "")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("exam/paper");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "在线考试管理");
		breadCrumbMap.put("url", "exam");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping(value="/paper/all", method = RequestMethod.GET)
	@ResponseBody
	public List<ExamPaper> findAllExamPaper(){
		return examPaperService.findAll();
	}
	
	@RequestMapping(value = "/paper/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findExamPaperList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String subject) throws Exception{
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		List<ExamPaperDto> list = new ArrayList<ExamPaperDto>();
		Page<ExamPaper> page = examPaperService.findAll(pageNumber, pageSize, subject);
		if (page != null && page.getTotalElements() > 0) {	//entity 2 dto
			ExamPaperDto dto = null;
			EntityDtoConvert convert = new EntityDtoConvert();
			for(ExamPaper paper : page){
				dto = new ExamPaperDto();
				convert.entity2Dto(paper, dto);
				User u = userService.findOne(paper.getUserId());
				dto.setCreaterName(u == null ? "" : u.getChinesename());
				list.add(dto);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping("/paper/query/{id}")
	@ResponseBody
	public ExamPaper findOneExamPaper(@PathVariable Long id){
		return examPaperService.findOne(id);
	}
	
	@RequestMapping(value = "/paper/save")
	@ResponseBody
	public Response saveExamPaper(@ModelAttribute ExamPaper entity){
		entity.setUserId(RightUtil.getCurrentUserId());
		entity.setSaveDate(new Date());
		examPaperService.save(entity);
		return new SuccessResponse("操作成功！");
	}
	
	@RequestMapping("/paper/delete")
	@ResponseBody
	public Response deleteExamPaper(Long id){
		examPaperService.delete(id);
		return new SuccessResponse("操作成功！");
	}
	
	@RequestMapping(value = "/preview")
	public ModelAndView preview(@RequestParam Long id) {
		ModelAndView mav = new ModelAndView("exam/view");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "试题管理");
		breadCrumbMap.put("url", "exam/preview");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		//设置试题号
		ExamPaper paper = examPaperService.findOne(id);
		List<ExamQuestion> list = examQuestionService.findByPaper(id);
		if (list != null && list.size() > 0) {
			Integer num = 1;
			for(ExamQuestion exam : list){
				exam.setQnum(CnUpperCaser.getCnString(num.toString()));
				num ++;
			}
		}
		mav.addObject("list", list);
		mav.addObject("subject", paper == null? "" : paper.getSubject());
		return mav;
	}
	
	
	@RequestMapping(value = "/question/index")
	public ModelAndView questionIndex(@RequestParam Long id) {
		ModelAndView mav = new ModelAndView("exam/question");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "试题管理");
		breadCrumbMap.put("url", "exam/question");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("id", id);
		return mav;
	}
	
	@RequestMapping(value="/question/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryList(@RequestParam Long id){//paperId
		Map<String, Object> map = new HashMap<String, Object>();
		List<ExamQuestion> list = examQuestionService.findAll(id);
		
		map.put("aaData", list);
		return map;
	}
	
	@RequestMapping(value="/question/save", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@ModelAttribute("question") ExamQuestion question, Long paperId){
		ExamPaper paper = examPaperService.findOne(paperId);
		question.setPaper(paper);
		return examQuestionService.save(question);
	}
	
	@RequestMapping(value="/question/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response delete(Long id){
		return examQuestionService.delete(id);
	}

	/**
	 * 保存考试
	 * @param answers
	 * @param paperId
	 * @param planCourseId
	 * @param source	考试源 bt：基础培训在线考试
	 * @return
	 */
	@RequestMapping(value="/answer/save", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Response saveAnswer(String answers, Long paperId, Long planCourseId, String source){
		if (StringUtils.isBlank(answers)) {
			return new FailedResponse();
		}
		ExamAnswer examAnswer = new ExamAnswer();
		examAnswer.setPaper(examPaperService.findOne(paperId));
		examAnswer.setUser(userService.findOne(RightUtil.getCurrentUserId()));
		ExamAnswer entity = examAnswerService.save(examAnswer);
		String[] answerArray = answers.split("~");
		for (int i = 0; i < answerArray.length; i++) {
			ExamAnswerDetails details = new ExamAnswerDetails();
			String[] idAnswer = answerArray[i].split("_");
			Long questionId = Long.parseLong(idAnswer[0]);
			String answner = idAnswer[1];
			details.setAnswerId(entity.getId());
			details.setQuestionId(questionId);
			details.setAnswers(answner);
			examAnswerDetailsService.save(details);
		}
		if (source.equals("bt")) {//基础培训在线考试
			BaseTrainingPlanCourse planCourse = baseTrainingPlanCourseService.findOne(planCourseId);
			planCourse.setIfscore(1);  	//已经参加考试
			baseTrainingPlanCourseService.save(planCourse);
		}
		return new SuccessResponse("操作成功");
	}
	
	/**
	 * 调转到阅卷页面
	 * @param answerId
	 * @return
	 */
	@RequestMapping(value = "/goover")
	public ModelAndView gooverIndex() {
		ModelAndView mav = new ModelAndView("exam/gooverindex");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "在线考试");
		breadCrumbMap.put("url", "exam/goover");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}

	/**
	 * 调转到阅卷页面
	 * @param answerId
	 * @return
	 */
	@RequestMapping(value = "/goover/{answerId}")
	public ModelAndView goover(@PathVariable Long answerId) {
		ModelAndView mav = new ModelAndView("exam/goover");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "试题管理");
		breadCrumbMap.put("url", "exam/goover/" + answerId);
		//答案列表
		List<ExamGooverDTO> dtoList = new ArrayList<ExamGooverDTO>();
		String subject = "";
		if(answerId != null && answerId > 0){
			ExamAnswer examAnswer = examAnswerService.findOne(answerId);
			if (examAnswer != null && examAnswer.getPaper() != null) {
				subject = examAnswer.getPaper().getSubject();	//试题subject
				List<ExamQuestion> list = examQuestionService.findByPaper(examAnswer.getPaper().getId());
				
				if (list != null && list.size() > 0) {
					Integer num = 1;
					for(ExamQuestion exam : list){
						ExamGooverDTO dto = new ExamGooverDTO();
						dto.setId(exam.getId());
						dto.setSubject(exam.getSubject());
						dto.setType(exam.getType());
						dto.setDifficulty(exam.getDifficulty());
						dto.setOptionContents(exam.getOptionContents());
						dto.setOptions(exam.getOptions());
						dto.setAnswers(exam.getAnswers());
						dto.setAnalysis(exam.getAnalysis());
						ExamAnswerDetails details = examAnswerDetailsService.findByQuestionId(exam.getId());
						dto.setOwnAnswer(details.getAnswers());
						dto.setQnum(CnUpperCaser.getCnString(num.toString()));//设置试题编号
						num ++;
						dtoList.add(dto);
					}
				}
			}
		}
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("answerId", answerId);
		mav.addObject("subject", subject);
		mav.addObject("list", dtoList);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/***
	 * 阅卷列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param userId
	 * @param paperId
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/goover/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findGooverList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long userId, Long paperId, Integer state){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		Page<ExamAnswer> page = examAnswerService.findAll(userId, paperId, state, pageNumber, pageSize);
		
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/**
	 * 阅卷
	 * @param score
	 * @param answerId
	 * @return
	 */
	@RequestMapping(value = "/goover/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveGoover(Double score, Long answerId){
		ExamAnswer answer = examAnswerService.findOne(answerId);
		answer.setScore(score);
		answer.setIfgovoer(1);//1为已阅卷
		examAnswerService.save(answer);
		return new SuccessResponse("操作成功");
	}
}
