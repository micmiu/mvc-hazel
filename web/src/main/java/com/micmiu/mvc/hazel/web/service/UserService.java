package com.micmiu.mvc.hazel.web.service;

import com.micmiu.mvc.hazel.core.service.BaseService;
import com.micmiu.mvc.hazel.web.entity.User;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface UserService extends BaseService<User, Long> {

	/**
	 * 根据登陆名查找用户
	 * 
	 * @param loginName
	 * @return
	 */
	User getUserByLoginName(String loginName);
}
