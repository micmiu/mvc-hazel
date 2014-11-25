package com.micmiu.mvc.hazel.web.service.impl;

import java.util.List;

import com.micmiu.mvc.hazel.core.service.impl.HibernateBaseService;
import com.micmiu.mvc.hazel.web.entity.Role;
import com.micmiu.mvc.hazel.web.service.PermissionService;
import com.micmiu.mvc.hazel.web.service.RoleService;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micmiu.mvc.hazel.web.entity.Permission;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Service("roleService")
public class RoleServiceImpl extends HibernateBaseService<Role, Long> implements
		RoleService {
	@Autowired
	private PermissionService permssionService;

	@Override
	protected Criteria handleProperty(Criteria currentCriteria,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return currentCriteria;
	}

	@Override
	public Role getRoleByName(String name) {
		return find("roleName", name);
	}

	@Override
	public void updateRole(Role role, List<Permission> permList) {
		for (Permission perm : permList) {
			this.permssionService.update(perm);
		}
		role.setPermissions(permList);
		this.update(role);

	}

}
