package com;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;

public class CheckCodeDemo {
	public static void main(String[] args) {
		// �����߶θ��ŵ���֤��
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 3);
		lineCaptcha.write("/your/path/b.png");

		// ����ԲȦ���ŵ���֤��
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
		captcha.write("/your/path/c.png");

		// ����Ť�����ŵ���֤��
		ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
		shearCaptcha.write("/your/path/d.png");		
	}
}
