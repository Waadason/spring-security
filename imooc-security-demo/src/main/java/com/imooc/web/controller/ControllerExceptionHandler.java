/**
 * 
 */
package com.imooc.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.imooc.exception.UserNotExistException;

/**
 * @author lida_
 *
 */
@ControllerAdvice
//这个注解是用来处理controller里面抛出来的异常
public class ControllerExceptionHandler {

	@ExceptionHandler(UserNotExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handlerUserNotExistException(UserNotExistException ex){
		Map<String, Object> result = new HashMap<>();
		result.put("id",ex.getId());
		result.put("message", ex.getMessage());
		return result;
	}
}
