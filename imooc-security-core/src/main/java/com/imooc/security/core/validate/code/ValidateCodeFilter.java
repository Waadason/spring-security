/**
 * 
 */
package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.SecurityProperties;

/**
 * @author lida_
 *
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	//spring中的异常处理器处理异常
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionstragety = new HttpSessionSessionStrategy();
	
	private Set<String> urls = new HashSet<String>();
	
	private SecurityProperties securityProperties;
	
	//用于匹配各种请求路径，为spring的工具类
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
				securityProperties.getCode().getImage().getUrl(), ",");
		
		for (String configUrl : configUrls) {
			urls.add(configUrl);
		}
		urls.add("/authentication/form");
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		boolean action = false;
		for (String url : urls) {
			if (pathMatcher.match(url,request.getRequestURI())) {
				action = true;
			}
		}
		//不在循环中做判断是因为一次只有一个url，不需要每次都去做判断
		if (action) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		
		// 拿到之前存储的imageCode信息
		ImageCode codeInSession = (ImageCode) sessionstragety.getAttribute(request, 
				ValidateCodeController.SESSION_KEY);
		
		//又是一个spring中的工具类
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("ValidateCode is not null");
		}
		
		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
		
		if (codeInSession.isEXpired()) {
			sessionstragety.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}
		
		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		sessionstragety.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SessionStrategy getSessionstragety() {
		return sessionstragety;
	}

	public void setSessionstragety(SessionStrategy sessionstragety) {
		this.sessionstragety = sessionstragety;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	

}
