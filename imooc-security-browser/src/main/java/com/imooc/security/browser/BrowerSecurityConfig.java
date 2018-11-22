package com.imooc.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.imooc.security.core.properties.SecurityProperties;

/**
 * 
 * @author lida_
 *
 */
@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Bean
	public PasswordEncoder passwordEncoder() {
		//根据自己系统的加密方式进行加密
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin()//表单登陆
			.loginPage("/authentication/require")
			.loginProcessingUrl("/authentication/form")//登录处理的url
			.successHandler(imoocAuthenticationSuccessHandler)
			.failureHandler(imoocAuthenticationFailureHandler)
//		http.httpBasic()
			.and()
			.authorizeRequests()//请求授权
			.antMatchers("/authentication/require",
					securityProperties.getBrowserProperties().getLoginPage()).permitAll()
			.anyRequest()//任何请求
			.authenticated()
			.and()
			.csrf().disable();//都需要请求认证
		
	}
}
