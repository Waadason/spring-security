/**
 * 
 */
package com.imooc.security.brower;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lida_
 *
 */
@RestController
public class BrowerSecurityController {

	//把当前请求缓存到cache中
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	/**
	 *  当需要身份认证时，跳转到这里
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/authentication/required")
	public String requiredAuthentication(HttpServletRequest request,HttpServletResponse response) {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (savedRequest != null) {
			String target = savedRequest.getRedirectUrl();
		}
		return null;
	}
}
