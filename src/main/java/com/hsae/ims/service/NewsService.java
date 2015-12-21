package com.hsae.ims.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.Code;
import com.hsae.ims.entity.News;
import com.hsae.ims.repository.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private CodeService codeService;
	
	public List<News> findAll(){
		return (List<News>) newsRepository.findAll();
	}

	public Page<News> findAll(int pageNumber, int pageSize, final String type, final String title) {
		List<Order> list = new ArrayList<Sort.Order>();
		Order idOrder = new Order(Direction.DESC, "id");
		Order topOrder = new Order(Direction.ASC, "top");
		list.add(topOrder);
		list.add(idOrder);
		Pageable pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(list));
		
		return (Page<News>)newsRepository.findAll(new Specification<News>() {
			
			@Override
			public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNoneEmpty(type)) {
					predicate.getExpressions().add(cb.equal(root.get("type").as(Code.class), codeService.findNewsCode(type)));
				}
				if (StringUtils.isNotEmpty(title)) {
					predicate.getExpressions().add(cb.like(root.get("title").as(String.class), "%"+title+"%"));
				}
				return predicate;
			}
		}, pageRequest);
	}

	/**
	 * i 为查询news数量
	 * @param i
	 * @return
	 */
	public List<News> findLatestNews(int i) {
		
		return newsRepository.findLatestNews(i);
	}

	public News findOne(Long id) {
		return newsRepository.findOne(id);
	}

	public Response save(News news) {
		newsRepository.save(news);
		return new SuccessResponse("操作成功");
	}

	public Response delete(Long id) {
		newsRepository.delete(id);
		return new SuccessResponse("操作成功");
	}
	
	
}
