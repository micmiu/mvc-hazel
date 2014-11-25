package com.micmiu.mvc.hazel.core.action.smvc;

import java.io.Serializable;

import com.micmiu.mvc.hazel.core.action.vo.AbstractQuery;
import com.micmiu.mvc.hazel.core.action.vo.Page;
import com.micmiu.mvc.hazel.core.service.BaseService;

/**
 * 抽象Action. 为子类实现基础的界面处理逻辑. 考虑到与Shiro框架权限定义的整合,不实现public方法.
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * @param <E> 实体类
 * @param <ID> 实体类ID类型
 */
public abstract class BaseCRUDAction<E, ID extends Serializable> {
	
	
	
	/**
	 * 封装分页查询逻辑. 考虑到各子类实现对分页查询的权限配置需求的差异,作为protected方法,以避免该方法直接对外暴露.
	 * @param q 查询对象
	 * @param s 查询Service
	 * @param pg 当前页
	 * @param rows 每页记录数
	 * @return Page<E> 页对象
	 */
	protected Page<E> doPagedQuery(AbstractQuery q, BaseService<E, ID> s, int pg, int rows) {
		Page<E> page = new Page<E>();
		page.setCurrentPage(pg);
		page.setRowsPerPage(rows);
		page.setPageList(s.query(q.getQueryProperties(), q.getSortProperties(), (pg - 1) * rows, rows));
		page.setTotalCount(s.count(q.getQueryProperties()));
		return page;
	}

}
