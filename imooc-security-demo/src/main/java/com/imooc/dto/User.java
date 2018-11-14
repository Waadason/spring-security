package com.imooc.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;

/**
 * 
 * @author lida_
 *
 */
public class User {
	
	public interface UserSimpleView {};
	public interface UserDetailView extends UserSimpleView{};

	@MyConstraint(message = "����һ������")
	private String username;
	
	@NotBlank(message = "���벻��Ϊ��")
	private String password;

	private String Id;
	
	@Past(message = "���ղ���Ϊ��ȥʱ��")
	private Date birthday;
	
	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserSimpleView.class)
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
