/**
 * 
 */
package com.imooc.security.core.validate.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

/**
 * @author lida_
 *
 */
public class ValidateCodeException extends AuthenticationException {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}


}
