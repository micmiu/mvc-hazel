package com.micmiu.mvc.hazel.web.service;

import java.util.List;

import com.micmiu.mvc.hazel.core.service.BaseService;
import com.micmiu.mvc.hazel.web.entity.Permission;
import com.micmiu.mvc.hazel.web.entity.Role;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface RoleService extends BaseService<Role, Long> {

	Role getRoleByName(String name);

	void updateRole(Role role, List<Permission> permList);
}
