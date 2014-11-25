package com.micmiu.mvc.hazel.web.service;

import java.util.List;

import com.micmiu.mvc.hazel.core.service.BaseService;
import com.micmiu.mvc.hazel.web.entity.Menu;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public interface MenuService extends BaseService<Menu, Long> {

	List<Menu> getRootMenuByOrder();

}
