package com.micmiu.mvc.hazel.core.action.smvc;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.micmiu.mvc.hazel.core.action.vo.easyui.DatagridQuery;

/**
 * {@link BaseAction} 进一步简单实现了基本CRUD+Export功能
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 * @param <E> 实体类
 * @param <ID> 注解
 * @param <Q> 查询vo
 */

public abstract class SimpleBaseAction<E, ID extends Serializable, Q extends DatagridQuery>
		extends BaseHandlerAction<E, E, ID, Q> {

	public SimpleBaseAction() {
		super();
	}

	@Override
	protected E convertE2V(E e, HttpServletRequest request) {
		return e;
	}

}