package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.News;

public interface NewsRepository extends PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News>{

	@Query(value="SELECT * FROM ims_system_news ORDER BY date DESC LIMIT ?1", nativeQuery = true)
	List<News> findLatestNews(int i);

}
