package com;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;

public class CheckCodeDemo {
	public static void main(String[] args) {
		// 生成线段干扰的验证码
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 3);
		lineCaptcha.write("/your/path/b.png");

		// 生成圆圈干扰的验证码
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
		captcha.write("/your/path/c.png");

		// 生成扭曲干扰的验证码
		ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
		shearCaptcha.write("/your/path/d.png");		
	}
}
