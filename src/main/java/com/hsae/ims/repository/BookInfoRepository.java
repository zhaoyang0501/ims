package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BookInfo;

public interface BookInfoRepository extends PagingAndSortingRepository<BookInfo, Long>,JpaSpecificationExecutor<BookInfo>{
	@Modifying
	@Query("update BookInfo a set a.status=?2 where a.id=?1")
	public int bookStatus(long id, int status);
	
	@Query("select b from BookInfo b where b.bookName like %?1%")
	public List<BookInfo> findBookByName(String bookName);
}
