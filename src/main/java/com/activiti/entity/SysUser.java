package com.activiti.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user")
public class SysUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name = "account",nullable = false,unique = true)
	private String account;
	
	@Column(name = "user_name",nullable = false,unique = true)
	private String userName;
	
	@Column(name = "parent_id",nullable = false,unique = true)
	private Long parentId;
	
	@Column(name = "password",nullable = false,unique = true)
	private String password;
	
	@Column(name = "last_login_time",nullable = false,unique = true)
	private Date lastLoginTime;
	
	@Column(name = "enabled",nullable = false,unique = true)
	private Boolean enabled;
	
	@Column(name = "account_non_expired",nullable = false,unique = true)
	private Boolean accountNonExpired;
	
	@Column(name = "account_non_locked",nullable = false,unique = true)
	private Boolean accountNonLocked;
	
	@Column(name = "credentials_non_expired",nullable = false,unique = true)
	private Boolean credentialsNonExpired;
	
	@Column(name = "CREATE_TIME",nullable = false,unique = true)
	private Date createTime ;
	
	@Column(name = "UPDATE_TIME",nullable = false,unique = true)
	private Date updateTime;
	
	@Column(name = "create_user",nullable = false,unique = true)
	private Long createUser;
	
	@Column(name = "update_user",nullable = false,unique = true)
	private Long updateUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
