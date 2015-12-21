package com.hsae.ims.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BookInfo;
import com.hsae.ims.entity.BookLendRecord;
import com.hsae.ims.entity.User;

public interface BookLendRecordRepository extends PagingAndSortingRepository<BookLendRecord, Long>,JpaSpecificationExecutor<BookLendRecord>{
	@Query("select count(b) from BookLendRecord b where b.identifying=?1 and b.userId=?2")
	public int findUserLendbookCount(int identifying, User userId);
	
	@Query("select b from BookLendRecord b where b.bookId=?1")
	public List<BookLendRecord> findLendRecordAll(BookInfo bookId);

	@Query("select b from BookLendRecord b where b.identifying=?1 and b.bookId=?2")
	public BookLendRecord findLendRecordById(int identifying, BookInfo bookId);
	
	@Query("select b from BookLendRecord b where b.lendType=0 and returnTime is null")
	public List<BookLendRecord> findLendRecordByAutoEmail();
}
