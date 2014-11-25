package com.micmiu.mvc.hazel.core.action.vo;

import java.util.List;

/**
 * 记录页.
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * @param <T> 记录类
 */
public class Page<T> {

	private int currentPage;

	private int rowsPerPage;

	private long totalCount;

	private List<T> pageList;

	/**
	 * 获取当页记录列表.
	 * @return List<T> 当页记录列表
	 */
	public List<T> getPageList() {
		return pageList;
	}

	/**
	 * 设置当页记录列表.
	 * @param pageList 当页记录列表
	 */
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
		if (pageList != null && pageList.size() < this.rowsPerPage && pageList.size() != this.totalCount) {
			this.totalCount = pageList.size();
		}
	}

	/**
	 * 获取当前页.
	 * @return int 当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前页.
	 * @param currentPage 当前页
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页记录数.
	 * @return int 每页记录数
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * 设置每页记录数.
	 * @param rowsPerPage 每页记录数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * 获取总记录数.
	 * @return long 总记录数
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 * @param totalCount 总记录数
	 */
	public void setTotalCount(long totalCount) {
		if (pageList != null && pageList.size() < this.rowsPerPage && pageList.size() != totalCount) {
			this.totalCount = pageList.size();
		} else {
			this.totalCount = totalCount;
		}
	}

}
