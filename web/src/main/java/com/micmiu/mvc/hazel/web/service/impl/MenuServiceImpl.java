package com.micmiu.mvc.hazel.web.service.impl;

import java.util.List;

import com.micmiu.mvc.hazel.core.service.impl.HibernateBaseService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.micmiu.mvc.hazel.web.entity.Menu;
import com.micmiu.mvc.hazel.web.service.MenuService;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Service("menuService")
public class MenuServiceImpl extends HibernateBaseService<Menu, Long> implements
		MenuService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getRootMenuByOrder() {
		Criterion top = Restrictions.isNull("parent");
		return (List<Menu>) this.getSession().createCriteria(Menu.class)
				.add(top).addOrder(Order.asc("orderNum")).list();
	}

	@Override
	protected Criteria handleProperty(Criteria currentCriteria,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return currentCriteria;
	}

}
