package com.imooc.security.brower;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author lida_
 *
 */
@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		//根据自己系统的加密方式进行加密
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()//表单登陆
			.loginPage("/authentication/required")
			.loginProcessingUrl("/authentication/form")//登录处理的url
//		http.httpBasic()
			.and()
			.authorizeRequests()//请求授权
			.antMatchers("/authentication/required").permitAll()
			.anyRequest()//任何请求
			.authenticated()
			.and()
			.csrf().disable();//都需要请求认证
		
	}
}
