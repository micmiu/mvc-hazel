package com.micmiu.mvc.hazel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonBackReference;

import com.micmiu.mvc.hazel.core.authc.shiro.IPermission;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Entity
@Table(name = "T_SYS_PERMSSION")
public class Permission extends IdEntity implements IPermission {

	private String resName;

	private String resCnName;

	private String operation;

	private Menu menu;

	@Column(name = "RES_NAME", length = 50)
	public String getResName() {
		return resName;
	}

	@Column(name = "OPERATION", length = 50)
	public String getOperation() {
		return operation;
	}

	@Column(name = "RES_CN_NAME", length = 50)
	public String getResCnName() {
		return resCnName;
	}

	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	@JsonBackReference
	public Menu getMenu() {
		return menu;
	}

	@Transient
	@Override
	public String getPermOperation() {
		return this.getResName() + ":" + this.getOperation();
	}

	public void setResCnName(String resCnName) {
		this.resCnName = resCnName;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
