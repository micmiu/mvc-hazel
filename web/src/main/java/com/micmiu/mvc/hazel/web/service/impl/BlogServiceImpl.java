package com.micmiu.mvc.hazel.web.service.impl;

import com.micmiu.mvc.hazel.core.service.impl.HibernateBaseService;
import com.micmiu.mvc.hazel.web.entity.Blog;
import com.micmiu.mvc.hazel.web.service.BlogService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Service("blogService")
public class BlogServiceImpl extends HibernateBaseService<Blog, Long> implements
		BlogService {


	@Override
	protected Criteria handleProperty(Criteria currentCriteria,
			String propertyName, Object value) {
		if ("title".equals(propertyName)) {
			currentCriteria.add(Restrictions.like("title", "%" + value + "%"));
		}
		return currentCriteria;
	}

}
