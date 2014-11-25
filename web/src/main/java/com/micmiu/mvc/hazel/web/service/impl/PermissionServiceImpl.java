package com.micmiu.mvc.hazel.web.service.impl;

import com.micmiu.mvc.hazel.core.service.impl.HibernateBaseService;
import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import com.micmiu.mvc.hazel.web.entity.Permission;
import com.micmiu.mvc.hazel.web.service.PermissionService;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Service("permissionService")
public class PermissionServiceImpl extends
		HibernateBaseService<Permission, Long> implements PermissionService {

	@Override
	protected Criteria handleProperty(Criteria currentCriteria,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return currentCriteria;
	}

}
