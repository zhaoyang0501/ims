package com.hsae.ims.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.dto.BookLendRecordDTO;
import com.hsae.ims.dto.BookPreRecordDTO;
import com.hsae.ims.entity.BookInfo;
import com.hsae.ims.entity.BookLendRecord;
import com.hsae.ims.entity.BookPreRecord;
import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.repository.BookInfoRepository;
import com.hsae.ims.repository.BookLendRecordRepository;
import com.hsae.ims.repository.BookPreRecordRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.MailUtil;
import com.hsae.ims.utils.RightUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BookManageService {

	@Autowired
	private BookInfoRepository bookinfoRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CodeService codeService;
	@Autowired
	private BookLendRecordRepository lendRecordRepository;
	@Autowired
	private BookPreRecordRepository preRecordRepository;
	@Autowired
	private RoleUserService roleUserService;
	@Autowired
	private EventNoticationService eventNoticationService;
	@Autowired
	private RoleService roleService;

	private PageRequest bookBuildPageRequest(String sSortDir_0, int pageNumber, int pagzSize) {
		List<String> list = new ArrayList<String>();
		list.add("code");
		Direction direction = Direction.ASC;
		if (StringUtils.isNotBlank(sSortDir_0)) {
			list.add("code");
			if (sSortDir_0.toUpperCase().equals("DESC")) {
				direction = Direction.DESC;
			}
		}
		Sort sort = new Sort(direction, list);
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		Sort sort = new Sort(Direction.ASC, "id");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 图书信息报表。
	 * 
	 */
	public Page<BookInfo> getBookInfoDate(String sSortDir_0, int pageNumber, int pageSize, String code, String bookName, String beginPrice, String endPrice, String author,
			String status) {
		PageRequest pageRequest = bookBuildPageRequest(sSortDir_0, pageNumber, pageSize);
		Specification<BookInfo> spec = bookinfoBuildSpecification(code, bookName, beginPrice, endPrice, author, status);
		return (Page<BookInfo>) bookinfoRepository.findAll(spec, pageRequest);
	}

	/**
	 * 借阅记录报表。
	 * 
	 */
	public Page<BookLendRecord> getLendRecordDate(final int pageNumber, final int pageSize, final String bookName, final Long userId, final String overrun) {
		Specification<BookLendRecord> spec = new Specification<BookLendRecord>() {
			@Override
			public Predicate toPredicate(Root<BookLendRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(bookName)) {
					predicate.getExpressions().add(cb.like(root.get("bookId").get("bookName").as(String.class), "%" + bookName + "%"));
				}
				if (StringUtils.isNotEmpty(overrun)) {
					predicate.getExpressions().add(cb.equal(root.get("isOverLimit").as(String.class), overrun));
				}
				if (userId != null && userId > 0) {
					User u = userRepository.findOne(userId);
					predicate.getExpressions().add(cb.equal(root.get("userId").as(User.class), u));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return lendRecordRepository.findAll(spec, pageRequest);
	}

	/**
	 * 个人借阅记录报表。
	 * 
	 */
	public Page<BookLendRecord> getUserLendRecordDate(int pageNumber, int pageSize, String bookName, Long userId, String overrun) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<BookLendRecord> spec = userlendrecordBuildSpecification(bookName, userId, overrun);
		return (Page<BookLendRecord>) lendRecordRepository.findAll(spec, pageRequest);
	}

	private Specification<BookLendRecord> userlendrecordBuildSpecification(final String bookName, final Long userId, final String overrun) {
		return new Specification<BookLendRecord>() {
			@Override
			public Predicate toPredicate(Root<BookLendRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				User nowUser = userRepository.getUserById(RightUtil.getCurrentUserId());
				predicate.add(cb.equal(root.get("userId").as(User.class), nowUser));

				if (StringUtils.isNotEmpty(bookName)) {
					// List<BookInfo> b =
					// bookinfoRepository.findBookByName(bookName);
					// predicate.getExpressions().add(cb.equal(root.get("bookId").as(BookInfo.class),b));

					// 两张表关联查询
					Join<BookLendRecord, BookInfo> bookJoin = root.join(root.getModel().getSingularAttribute("bookId", BookInfo.class), JoinType.LEFT);
					predicate.add(cb.like(bookJoin.get("bookName").as(String.class), "%" + bookName + "%"));
				}
				if (StringUtils.isNotEmpty(overrun)) {
					predicate.add(cb.equal(root.get("isOverLimit").as(String.class), overrun));
				}
				if (userId != null && userId > 0) {
					User u = userRepository.findOne(userId);
					predicate.add(cb.equal(root.get("userId").as(User.class), u));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}

	private Specification<BookInfo> bookinfoBuildSpecification(final String code, final String bookName, final String beginPrice, final String endPrice, final String author,
			final String status) {
		return new Specification<BookInfo>() {
			@Override
			public Predicate toPredicate(Root<BookInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (StringUtils.isNotEmpty(code)) {
					predicate.getExpressions().add(cb.like(root.get("code").as(String.class), "%" + code + "%"));
				}

				if (StringUtils.isNotEmpty(bookName)) {
					predicate.getExpressions().add(cb.like(root.get("bookName").as(String.class), "%" + bookName + "%"));
				}
				if (StringUtils.isNotEmpty(beginPrice)) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("price").as(String.class), beginPrice));
				}
				if (StringUtils.isNotEmpty(endPrice)) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("price").as(String.class), endPrice));
				}
				if (StringUtils.isNotEmpty(author)) {
					predicate.getExpressions().add(cb.like(root.get("author").as(String.class), "%" + author + "%"));
				}
				if (StringUtils.isNotEmpty(status)) {
					predicate.getExpressions().add(cb.equal(root.get("status").as(String.class), status));
				}
				return predicate;
			}
		};
	}

	/**
	 * 查询所有图书信息。
	 */
	public List<BookInfo> findAll() {
		return (List<BookInfo>) bookinfoRepository.findAll();
	}

	/**
	 * 查找图书信息。
	 */
	public BookInfo findOne(long id) {
		return bookinfoRepository.findOne(id);
	}

	/**
	 * 查找图书借阅记录。
	 */
	public BookLendRecordDTO findLendRecord(long id) {
		BookLendRecordDTO brDTO = new BookLendRecordDTO();
		// 标识位 0:已归还 1：未归还。
		int identifying = 1;
		BookInfo book = bookinfoRepository.findOne(id);
		BookLendRecord br = lendRecordRepository.findLendRecordById(identifying, book);
		User user = br.getUserId();
		brDTO.setLendBookName(book.getBookName());
		brDTO.setLendUserName(user.getChinesename());
		brDTO.setLendTime(br.getLendTime().toString());
		brDTO.setMoney(br.getMoney());
		return brDTO;
	}

	/**
	 * 保存新建结果。
	 */
	public boolean addSave(BookInfo b) {
		List<BookInfo> list = (List<BookInfo>) bookinfoRepository.findAll();
		for (BookInfo bk : list) {
			if (bk.getCode().equals(b.getCode())) {
				return false;
			}
		}
		long userId = RightUtil.getCurrentUserId();
		User user = userRepository.getUserById(userId);
		if (user != null) {
			b.setCreateName(user);
		}
		b.setCreateDate(new Timestamp(System.currentTimeMillis()));
		BookInfo book = bookinfoRepository.save(b);
		if (book != null) {
			return true;
		}
		return false;
	}

	/**
	 * 保存更新结果。
	 */
	public boolean updateSave(BookInfo b) {
		long userId = RightUtil.getCurrentUserId();
		User user = userRepository.getUserById(userId);
		if (user != null) {
			b.setCreateName(user);
		}
		b.setCreateDate(new Timestamp(System.currentTimeMillis()));
		BookInfo book = bookinfoRepository.save(b);
		if (book != null) {
			return true;
		}
		return false;
	}

	/**
	 * 删除图书信息。
	 */
	public boolean delete(long id) {
		if (bookinfoRepository.exists(id)) {
			// 标识位 0:已归还 1：未归还
			int identifying = 1;
			BookInfo book = bookinfoRepository.findOne(id);
			BookLendRecord leandRecord = lendRecordRepository.findLendRecordById(identifying, book);
			if (leandRecord != null) {
				return false;
			}
			List<BookLendRecord> lendlist = lendRecordRepository.findLendRecordAll(book);
			List<BookPreRecord> prelist = preRecordRepository.findPreRecordAll(book);
			lendRecordRepository.delete(lendlist);
			preRecordRepository.delete(prelist);
			bookinfoRepository.delete(id);
			return true;
		}
		return false;
	}

	/**
	 * 删除借阅记录。
	 */
	public boolean delLendRecord(long lendId) {
		if (lendRecordRepository.exists(lendId)) {
			BookLendRecord leandRecord = lendRecordRepository.findOne(lendId);
			if (leandRecord.getReturnTime() == null) {
				return false;
			}
			lendRecordRepository.delete(lendId);
			return true;
		}
		return false;
	}

	/**
	 * 出借图书。
	 */
	public boolean lendBook(long id, long userid, long lendtype) {
		List<String> bookCode = new ArrayList<String>();
		// 借阅周期。
		int lendDay = 0;
		// 允许借阅的本书。
		int lendNumber = 0;
		// 图书状态。 0:正常 1：借出 2:长期。
		int status = 0;

		// 取图书管理配置编码。
		bookCode = codeService.findBookManageConfig();
		for (int i = 0; i < bookCode.size(); i++) {
			lendDay = Integer.parseInt(bookCode.get(1));
			lendNumber = Integer.parseInt(bookCode.get(3));
		}
		User us = userRepository.findOne(userid);
		BookInfo bk = bookinfoRepository.findOne(id);
		// 标识位 0:已归还 1：未归还。
		int identifying = 1;
		if (lendRecordRepository.findUserLendbookCount(identifying, us) > lendNumber - 1) {
			return false;
		}

		BookLendRecord lendRecord = new BookLendRecord();
		lendRecord.setBookId(bk);
		lendRecord.setUserId(us);
		lendRecord.setLendTime(new Timestamp(System.currentTimeMillis()));
		if (lendtype == 1) {
			lendRecord.setExpectTime(null);
			lendRecord.setLandType(1);
			status = 2;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			lendRecord.setExpectTime(Timestamp.valueOf(sdf.format(DateUtils.addDays(lendRecord.getLendTime(), lendDay))));
			lendRecord.setLandType(0);
			status = 1;
		}
		lendRecord.setIsOverLimit(0);
		lendRecord.setIdentifying(1);
		lendRecord.setMoney("");// 借出起始逾期金额为0。

		long createName = RightUtil.getCurrentUserId();
		User user = userRepository.getUserById(createName);
		if (user != null) {
			lendRecord.setCreateName(user);
		}
		lendRecord.setCreateDate(new Timestamp(System.currentTimeMillis()));
		BookLendRecord lr = lendRecordRepository.save(lendRecord);

		// 已经借出图书的则删除预约记录。
		BookPreRecord br = preRecordRepository.findPreRecord(us, bk);
		if (br != null) {
			preRecordRepository.delete(br);
			// //写入借阅类型。
			// bk.setLendType(landtype);
		}

		if (lr != null && bookinfoRepository.bookStatus(id, status) != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 续借图书。
	 */
	public boolean renewBook(long id) {
		MailUtil mu = new MailUtil();

		// 标识位 0:已归还 1：未归还。
		int identifying = 1;
		// 是否续借 0:未续借 1：已续借。
		int renew = 1;
		BookLendRecord lendRecord = new BookLendRecord();
		BookInfo book = bookinfoRepository.findOne(id);
		lendRecord = lendRecordRepository.findLendRecordById(identifying, book);
		User us = lendRecord.getUserId();
		if (lendRecord.getRenew() == renew) {
			return false;
		}
		List<String> bookCode = new ArrayList<String>();
		// 续借周期。
		int renewDay = 0;
		bookCode = codeService.findBookManageConfig();
		for (int i = 0; i < bookCode.size(); i++) {
			renewDay = Integer.parseInt(bookCode.get(0));
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lendRecord.setExpectTime(Timestamp.valueOf(sdf.format(DateUtils.addDays(lendRecord.getExpectTime(), renewDay))));
		lendRecord.setRenew(renew);// 表示续借。

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 设置邮件的内容体。
		String content = "<br>用户" + us.getChinesename() + "你好！<br>你在" + sf.format(lendRecord.getLendTime()) + "借阅的图书：" + book.getCode() + "《" + book.getBookName() + "》已续借至"
				+ sf.format(lendRecord.getExpectTime()) + "归还！<br>非常感谢！";
		if (StringUtils.isNotBlank(us.getEmail())) {
			try {
				mu.send(us.getEmail().toString(), "图书续借通知", content);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			eventNoticationService.createEventNotice("用户未设置邮箱！", us.getId(), "续借图书", Long.valueOf(0));
		}

		BookLendRecord lr = lendRecordRepository.save(lendRecord);
		if (lr != null) {
			return true;
		}
		return false;
	}

	/**
	 * 归还图书。
	 */
	public boolean returnBook(long id) {
		// 标识位 0:已归还 1：未归还。
		int identifying = 1;

		BookLendRecord lendRecord = new BookLendRecord();
		BookInfo book = bookinfoRepository.findOne(id);

		lendRecord = lendRecordRepository.findLendRecordById(identifying, book);
		lendRecord.setReturnTime(new Timestamp(System.currentTimeMillis()));
		identifying = 0;
		lendRecord.setIdentifying(identifying);
		BookLendRecord lr = lendRecordRepository.save(lendRecord);
		// 图书状态。 0:正常 1：借出。
		int status = 0;
		if (lr != null && bookinfoRepository.bookStatus(id, status) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 计算逾期金额。
	 * 
	 */
	public void countMoney() {
		List<BookLendRecord> dolist = (List<BookLendRecord>) lendRecordRepository.findAll();
		// 是否超限。 0:未超限 1：超限
		int isOverLimit = 1;
		double money = 0;
		String expectTime = null;
		int day = 0;
		String nowTime = new Timestamp(System.currentTimeMillis()).toString();
		DecimalFormat df = new DecimalFormat("#0.0");

		for (BookLendRecord br : dolist) {
			if (br.getLendType() == 1) {
				continue;
			}
			if (br.getExpectTime() == null) {
				continue;
			}
			if (br.getReturnTime() != null) {
				continue;
			}
			expectTime = br.getExpectTime().toString();
			// 计算当前日期-应归还日期得到相差天数。
			day = DateTimeUtil.getDaysBetweenDates(expectTime, nowTime);
			if (day < 0) {
				continue;
			}
			money = day * 0.1;
			br.setMoney(df.format(money).toString());
			br.setIsOverLimit(isOverLimit);
			lendRecordRepository.save(br);
		}
	}

	/**
	 * 预约图书。
	 * 
	 */
	public int prebook(long id) {
		BookPreRecord pre = new BookPreRecord();
		BookInfo book = bookinfoRepository.findOne(id);
		long createName = RightUtil.getCurrentUserId();
		User user = userRepository.getUserById(createName);
		// 已经借出图书的删除预约记录。
		BookPreRecord br = preRecordRepository.findPreRecord(user, book);
		if (br != null) {
			preRecordRepository.delete(br);
		}
		MailUtil mu = new MailUtil();
		// 设置收件人
		List<String> recipients = new ArrayList<String>();
		// 设置邮件主题。
		String subject = "图书预定通知";
		// 设置邮件的内容体
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String content = "<br>管理员你好！<br>用户" + user.getChinesename() + "在" + sdf.format(new Timestamp(System.currentTimeMillis())) + "预定了书名为：" + book.getCode() + "《"
				+ book.getBookName() + "》的图书，请知悉！<br>非常感谢！";
		// 图书管理员role。
		List<Role> rsList = roleService.findAll();
		long bookmanage = 0;
		for (Role rs : rsList) {
			if (rs.getName().equals("bookmanage"))
				bookmanage = rs.getId();
		}
		if (bookmanage == 0) {
			eventNoticationService.createEventNotice("系统未设置图书管理员！", user.getId(), "图书管理", Long.valueOf(0));
			return 2;
		}
		List<UserRole> usrole = roleUserService.findByRoleid(bookmanage);

		User us = null;
		for (UserRole rl : usrole) {
			us = userRepository.findOne(rl.getUserId());
			if (StringUtils.isNotBlank(us.getEmail())) {
				recipients.add(us.getEmail());
			}
		}
		if (recipients.size() > 0) {
			try {
				mu.send(recipients, subject, content);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else {
			// 发系统消息，图书管理员未配置Email。
			eventNoticationService.createEventNotice("图书管理员未设置邮箱！", user.getId(), "图书管理", Long.valueOf(0));
			return 3;
		}
		pre.setBookId(book);
		pre.setUserId(user);
		pre.setPreTime(new Timestamp(System.currentTimeMillis()));
		br = preRecordRepository.save(pre);

		if (br != null) {
			return 1;
		}
		return 0;
	}

	/**
	 * 发送邮件提醒归还时间。
	 * 
	 */
	public int sendEmail(long lendId) {
		MailUtil mu = new MailUtil();
		BookLendRecord br = lendRecordRepository.findOne(lendId);
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "图书归还时间提醒";
		// 设置邮件的内容体
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String content = "<br>用户" + br.getUserId().getChinesename() + "你好！<br>你在" + sdf.format(br.getLendTime()) + " 借阅的图书：" + br.getBookId().getCode() + " 《"
				+ br.getBookId().getBookName() + "》 应还时间是" + sdf.format(br.getExpectTime() == null ? "" : br.getExpectTime()) + "。  请及时归还 , 以免产生逾期罚款！<br>非常感谢！";

		if (br.getReturnTime() != null) {
			return 3;
		}
		if (StringUtils.isNotBlank(br.getUserId().getEmail())) {
			recipient = br.getUserId().getEmail();
			try {
				mu.send(recipient, subject, content);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return 1;
		} else {
			// 发系统消息，图书管理员未配置Email。
			eventNoticationService.createEventNotice("此用户未设置邮箱！", br.getUserId().getId(), "图书管理", Long.valueOf(0));
		}
		return 2;
	}

	/**
	 * 查看预约记录。
	 * 
	 */
	public List<BookPreRecordDTO> prebookRecord(Long id) {
		BookInfo book = bookinfoRepository.findOne(id);
		List<BookPreRecord> pre = preRecordRepository.findPreRecordAll(book);
		List<BookPreRecordDTO> dotList = new ArrayList<BookPreRecordDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BookPreRecordDTO dto = null;
		int index = 1;
		for (BookPreRecord list : pre) {
			dto = new BookPreRecordDTO();
			dto.setIndex(String.valueOf(index));
			dto.setPreBookName(list.getBookId().getBookName());
			dto.setPreUserName(list.getUserId().getChinesename());
			dto.setPreTime(sdf.format(list.getPreTime()).toString());
			index++;
			dotList.add(dto);
		}
		return dotList;
	}

	/**
	 * 查询所有图书。
	 * 
	 */
	public List<BookInfo> queryBookAll() {
		return (List<BookInfo>) bookinfoRepository.findAll();
	}

}
