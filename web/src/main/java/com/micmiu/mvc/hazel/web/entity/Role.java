package com.micmiu.mvc.hazel.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.micmiu.mvc.hazel.core.action.vo.annotation.ShowParam;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "T_SYS_ROLE")
public class Role extends IdEntity {

	@ShowParam("system.role.roleNamee")
	@Column(name = "ROLE_NAME")
	private String roleName;

	private List<Permission> permissions = new ArrayList<Permission>();

	@Column(name = "ROLE_NAME", length = 50)
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 操作权限集合.
	 */
	@ManyToMany
	@JoinTable(name = "T_SYS_R2P", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERM_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
