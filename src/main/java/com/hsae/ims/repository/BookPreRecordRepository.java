package com.hsae.ims.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BookInfo;
import com.hsae.ims.entity.BookPreRecord;
import com.hsae.ims.entity.User;

public interface BookPreRecordRepository extends PagingAndSortingRepository<BookPreRecord, Long>,JpaSpecificationExecutor<BookPreRecord>{
	@Query("select b from BookPreRecord b where b.userId=?1 and b.bookId =?2")
	public BookPreRecord findPreRecord(User user, BookInfo book);
	
	@Query("select b from BookPreRecord b where b.bookId =?1")
	public List<BookPreRecord> findPreRecordAll( BookInfo book);
}
