package com.imooc.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 
 * @author lida_
 *
 */
public class ImageCode {

	//图片
	private BufferedImage image;
	
	//随机数，图片根据随机数生成，登录请求的时候需要验证
	private String code;
	
	//过期时间
	private LocalDateTime expiredTime;
	

	public ImageCode(BufferedImage image, String code, int expiredIn) {
		super();
		this.image = image;
		this.code = code;
		this.expiredTime = LocalDateTime.now().plusSeconds(expiredIn);
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super();
		this.image = image;
		this.code = code;
		this.expiredTime = expireTime;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(LocalDateTime expiredTime) {
		this.expiredTime = expiredTime;
	}
	
	public boolean isEXpired() {
		return this.expiredTime.isBefore(LocalDateTime.now());
	}
	
}
