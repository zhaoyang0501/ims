package com.hsae.ims.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;



@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicSpecifications {
	
	public static int AND = 0;
	public static int OR = 0;

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters != null) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filters) {
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						default:
							break;
						}
					}

					// 将所有条件用 or 联合起来
					if (!predicates.isEmpty()) {
						return builder.or(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
	
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz, final int type) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (CollectionUtils.isNotEmpty(filters)) {

					List<Predicate> predicates = new ArrayList<Predicate>();// Lists.newArrayList();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名�?user.name"的filedName,
						// 转换为Task.user.name属�?
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case BETWEEN:
							predicates.add(builder.between(expression, (Comparable) ((List) filter.value).get(0), (Comparable) ((List) filter.value).get(1)));
							break;
						case ISNULL:
							predicates.add(builder.isNull(expression));
							break;
						case ISNOTNULL:
							predicates.add(builder.isNotNull(expression));
							break;
						}
					}

					// 将所有条件用 AND 联合起来
					if (!predicates.isEmpty()) {
						if (type == DynamicSpecifications.AND) {
							return builder.and(predicates.toArray(new Predicate[predicates.size()]));
						} else {
							return builder.or(predicates.toArray(new Predicate[predicates.size()]));
						}
					}
				}

				return builder.conjunction();
			}
		};
	}
	
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> andFilters, final Collection<SearchFilter> orFilters, final Class<T> entityClazz) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Predicate orPredicate = null;
				Predicate andPredicate = null;
				if (CollectionUtils.isNotEmpty(orFilters)) {

					List<Predicate> predicates = new ArrayList<Predicate>();// Lists.newArrayList();
					for (SearchFilter filter : orFilters) {
						// nested path translate, 如Task的名�?user.name"的filedName,
						// 转换为Task.user.name属�?
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						default:
							break;
						}
					}

					// 将所有条件用 or 联合起来
					if (!predicates.isEmpty()) {
						orPredicate = builder.or(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				if (CollectionUtils.isNotEmpty(andFilters)) {
					List<Predicate> predicates = new ArrayList<Predicate>();// Lists.newArrayList();
					for (SearchFilter filter : andFilters) {
						// nested path translate, 如Task的名�?user.name"的filedName,
						// 转换为Task.user.name属�?
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						default:
							break;
						}
					}

					// 将所有条件用 or 联合起来
					if (!predicates.isEmpty()) {
						andPredicate = builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				if (orPredicate != null && andPredicate != null) {
					return builder.and(andPredicate, orPredicate);
				} else if (orPredicate != null) {
					return orPredicate;
				} else if (andPredicate != null) {
					return andPredicate;
				}

				return builder.conjunction();
			}
		};
	}

}