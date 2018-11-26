package com.imooc.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author lida_
 *
 */
@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {

	private BrowserProperties browserProperties = new BrowserProperties();
	
	private ValitdateCodeProperties  code = new ValitdateCodeProperties();

	public BrowserProperties getBrowserProperties() {
		return browserProperties;
	}

	public void setBrowserProperties(BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}

	public ValitdateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValitdateCodeProperties code) {
		this.code = code;
	}
	
}
