/**
 * 
 */
package com.imooc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lida_
 *
 */
public interface ValidateCodeGenerateor {

	ImageCode generate(ServletWebRequest request);

}
