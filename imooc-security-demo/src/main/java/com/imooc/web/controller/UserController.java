package com.imooc.web.controller;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.Create;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.Delete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.User.UserDetailView;
import com.imooc.dto.User.UserSimpleView;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserNotExistException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 
 * @author lida_
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
//	@GetMapping("/me")
//	public Object getCurrentUser() {
//		return SecurityContextHolder.getContext().getAuthentication();
//	}
	@GetMapping("/me")
	public Object getCurrentUser(Authentication authentication) {
		return authentication;
	}
	@PostMapping
	@JsonView(UserSimpleView.class)
	public User create(@Valid @RequestBody User user, BindingResult errors) {
		
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error ->  System.out.println(error.getDefaultMessage()));
		}
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());
		
		user.setId("1");
		return user;
	} 
	
	@PutMapping("/{id:\\d+}")
	@JsonView(UserSimpleView.class)
	public User update(@Valid @RequestBody User user, BindingResult errors) {
		
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error ->  {
				FieldError fieldError = (FieldError)error;
				String msg = fieldError.getField() + " "+error.getDefaultMessage();
				System.out.println(msg);
			});
		}
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());
		
		user.setId("1");
		return user;
	} 

	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}
	@GetMapping
	@JsonView(UserSimpleView.class)
	@ApiOperation(value = "用户查询服务")
	public List<User> query(UserQueryCondition userQueryCondition,
			@PageableDefault(page = 2,size = 17,sort = "username,sort")Pageable pageable){
		
		System.out.println(ReflectionToStringBuilder.toString(userQueryCondition,ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());
		
		List<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	@ExceptionHandler(UserNotExistException.class)
	@GetMapping("/{id:\\d+}")
	@JsonView(UserDetailView.class)
	public User getInfo(@ApiParam(value = "用户id") @PathVariable(name = "id") String id) {
		
//		throw new UserNotExistException(id);
		System.out.println("����getInfo����");
		User user = new User();
		user.setUsername("tom");
		return user;
	}
	
}
