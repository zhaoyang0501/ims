package com.hsae.ims.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.dto.BookInfoDTO;
import com.hsae.ims.dto.BookLendRecordDTO;
import com.hsae.ims.dto.BookPreRecordDTO;
import com.hsae.ims.entity.BookInfo;
import com.hsae.ims.entity.BookLendRecord;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.BookLendRecordRepository;
import com.hsae.ims.service.BookManageService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.view.BookReportExportXlsView;


@Controller
@RequestMapping("/book")
public class BookManageController extends BaseController{

	@Autowired
	private BookManageService bookManageService;
	@Autowired
	private BookLendRecordRepository lendRecordRepository;
	/**
	 * 图书信息统计表。 
	 * 
	 */
	@RequestMapping(value = "/bookinfo")
	public ModelAndView bookInfoIndex() {
		ModelAndView mav = new ModelAndView("book/bookinfo");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "图书管理");
		breadCrumbMap.put("url", "/book/bookinfo");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	/**
	 * 图书信息详情。 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBookInfoList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String code, String bookName, String beginPrice, String endPrice, String author, String status) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<BookInfo> page = bookManageService.getBookInfoDate(sSortDir_0, pageNumber, pageSize, code, bookName, beginPrice, endPrice, author, status);
		List<BookInfoDTO> dtoList = new ArrayList<BookInfoDTO>();
		BookLendRecord br = null;
		if (page != null && page.getTotalElements() > 0) {
			BookInfoDTO dto = null;

			for (BookInfo p : page) {
				dto = new BookInfoDTO();
				dto.setId(p.getId()== null ? "" :p.getId().toString());
				dto.setCode(p.getCode()==null ? "" :p.getCode());
				dto.setBookName(p.getBookName());
				dto.setPrice(p.getPrice() ==null ? "" :p.getPrice().toString());
				dto.setAuthor(p.getAuthor()==null?"":p.getAuthor());
				dto.setPublish(p.getPublish()== null?"":p.getPublish());
				dto.setDescription(p.getDescription()==null?"":p.getDescription());
				dto.setStatus(p.getStatus());
				br = lendRecordRepository.findLendRecordById(1, p);
				if (br != null){
					dto.setLendUser(br.getUserId().getChinesename());
				}else{
					dto.setLendUser("");
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
	 * 借阅记录统计表。 
	 * 
	 */
	@RequestMapping(value = "/lendrecord")
	public ModelAndView bookLendRecordIndex() {
		ModelAndView mav = new ModelAndView("book/lendrecord");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "图书借阅");
		breadCrumbMap.put("url", "/book/lendrecord");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	
	
	/**
	 * 借阅记录详情。 
	 */
	@RequestMapping(value = "/lendrecordlist",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryLendRecordList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
		    Long userId, String bookName, String overrun) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		bookManageService.countMoney();
		Page<BookLendRecord> page = bookManageService.getLendRecordDate(pageNumber, pageSize,bookName, userId, overrun);
		List<BookLendRecordDTO> dtoList = new ArrayList<BookLendRecordDTO>();
		
		if (page != null && page.getTotalElements() > 0) {
			BookLendRecordDTO dto = null;
			BookInfo book = null;
			User user = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int day = 0;
			String nowTime = null;
			for (BookLendRecord p : page) {
				dto = new BookLendRecordDTO();
				dto.setLendId(p.getId()== null ? "" :p.getId().toString());
			    book =p.getBookId();
				if(book != null){
					dto.setLendBookName(book.getBookName());
				}else{
					dto.setLendBookName("");
				}
			    user = p.getUserId();
				if (user != null){
					dto.setLendUserName(user.getChinesename());
				}else{
					dto.setLendUserName("");
				}
				dto.setLendTime(p.getLendTime() == null ? "":df.format(p.getLendTime()));
				dto.setIsOverLimit(p.getIsOverLimit());
				dto.setReturnTime(p.getReturnTime()==null?"":df.format(p.getReturnTime()));
				dto.setMoney(p.getMoney()==null?"":p.getMoney());
				dto.setLendType(p.getLendType());
				nowTime = new Timestamp(System.currentTimeMillis()).toString();
				//计算应归还日期-当前日期得到相差天数。
				if (p.getLendType()==0){
					day = DateTimeUtil.getDaysBetweenDates(nowTime, df.format(p.getExpectTime()));		
					if (day > 7 && p.getIsOverLimit()==0){
						dto.setExpectTime(p.getExpectTime()==null ?"":df.format(p.getExpectTime()));
					}else if (p.getReturnTime()==null){
						dto.setExpectTime(p.getExpectTime()==null ?"":"<font color='red'>"+df.format(p.getExpectTime())+"</font>");
					}else{
						dto.setExpectTime(p.getExpectTime()==null ?"":df.format(p.getExpectTime()));
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
	 * 个人借阅记录详情。 
	 */
	@RequestMapping(value = "/userlendrecordlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserLendRecordList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
		    Long userId, String bookName ,String overrun) {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		bookManageService.countMoney();
		Page<BookLendRecord> page = bookManageService.getUserLendRecordDate(pageNumber, pageSize, bookName, userId, overrun);
		List<BookLendRecordDTO> dtoList = new ArrayList<BookLendRecordDTO>();
		if (page != null && page.getTotalElements() > 0) {
			BookLendRecordDTO dto = null;
			BookInfo book = null;
			User user = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (BookLendRecord p : page) {
					dto = new BookLendRecordDTO();
					dto.setLendId(p.getId()== null ? "" :p.getId().toString());
				    book =p.getBookId();
					if(book != null){
						dto.setLendBookName(book.getBookName());
					}else{
						dto.setLendBookName("");
					}
				    user = p.getUserId();
					if (user != null){
						dto.setLendUserName(user.getChinesename());
					}else{
						dto.setLendUserName("");
					}
					dto.setLendTime(p.getLendTime() == null ? "":df.format(p.getLendTime()));
					dto.setIsOverLimit(p.getIsOverLimit());
					dto.setReturnTime(p.getReturnTime()==null?"":df.format(p.getReturnTime()));
					dto.setMoney(p.getMoney()==null?"":p.getMoney());
					dto.setExpectTime(p.getExpectTime()==null ?"":df.format(p.getExpectTime()));
					dto.setLendType(p.getLendType());
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
	 * 删除图书信息。 
	 */	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delete(long id) {
		return bookManageService.delete(id);
	}
	
	
	/**
	 * 邮件提醒归还时间。 
	 */	
	@RequestMapping(value = "/sendemail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public int sendEmail(long lendId) {
		return bookManageService.sendEmail(lendId);
	}
	
	
	/**
	 * 删除借阅记录。 
	 */	
	@RequestMapping(value = "/dellendrecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean delLendRecord(long lendId) {
		return bookManageService.delLendRecord(lendId);
	}
	
	/**
	 * 查找图书信息。 
	 */
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public BookInfo query(@PathVariable long id) {
		return bookManageService.findOne(id);
	}
	
	/**
	 * 查找图书借阅记录。 
	 */
	@RequestMapping(value = "/querylendrecord/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public BookLendRecordDTO queryLendRecord(@PathVariable long id) {
		return bookManageService.findLendRecord(id);
	}
	
	
	/**
	 * 新建图书信息。 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean create(@ModelAttribute("book") BookInfo book) {
		return bookManageService.addSave(book);
	}
	
	/**
	 * 更新图书信息。 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean update( @ModelAttribute("book") BookInfo book) {
		return bookManageService.updateSave(book);
	}
	
	/**
	 * 导出图书信息Excel。 
	 */
	@RequestMapping(value="/export")
	public ModelAndView viewExcel(){
		Map<String,Object> model = new HashMap<String,Object>();
		List<BookInfo> bookinfo= bookManageService.findAll();
		model.put("book", bookinfo);
		model.put("fileName", "图书信息一览.xls");
		return new ModelAndView(new BookReportExportXlsView(), model); 
	}
	
	
	/**
	 * 出借图书。 
	 */
	@RequestMapping(value = "/lend", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean lendBook(long id, long userid, long lendtype) {
		return bookManageService.lendBook(id, userid, lendtype);
	}
	
	/**
	 * 续借图书。 
	 */
	@RequestMapping(value = "/renew", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean renewBook(long id) {
		return bookManageService.renewBook(id);
	}
	
	
	/**
	 * 归还图书。 
	 */
	@RequestMapping(value = "/return", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean returnBook(long id) {
		return bookManageService.returnBook(id);
	}
	
	
	/**
	 * 预约图书。 
	 */	
	@RequestMapping(value = "/prebook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public int prebook(long id) {
		return bookManageService.prebook(id);
	}
	
	
	
	/**
	 * 查看预约记录。 
	 */	
	@RequestMapping(value = "/prerecord/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, Object> prebookRecord(@PathVariable long id) {
		List<BookPreRecordDTO> dtoList = bookManageService.prebookRecord(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		return map;
	}
	
	
	/**
	 * 所有图书。
	 * 
	 */
	@RequestMapping(value = "/booklist")
	@ResponseBody
	public List<BookInfo> queryBookAll() {
		List<BookInfo> book = bookManageService.queryBookAll();
		return book;
	}
}
