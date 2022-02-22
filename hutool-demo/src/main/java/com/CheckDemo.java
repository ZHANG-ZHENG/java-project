package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.CreditCodeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

public class CheckDemo {
	public static void main(String[] args) {
		String idCard = "350111198801012222";
		// 根据身份证号获取出生日期
		String birth = IdcardUtil.getBirthByIdCard(idCard);

		// 根据身份证号获取省份
		String province = IdcardUtil.getProvinceByIdCard(idCard);

		// 判断身份证号是否合法
		boolean valid = IdcardUtil.isValidCard18(idCard);

		// 获取一个随机的社会信用代码
		String creditCode = CreditCodeUtil.randomCreditCode();

		// 判断社会信用代码是否合法
		boolean isCreditCode = CreditCodeUtil.isCreditCode(creditCode);

		// 将汉字转为拼音，需要引入TinyPinyin、JPinyin或Pinyin4j的jar包
		String china = PinyinUtil.getPinyin("中国");

		// 将字符串生成为二维码，需要引入com.google.zxing.core的jar包
		BufferedImage qrCodeImage = QrCodeUtil.generate("www.baidu.com", QrConfig.create());
		try {
			ImageIO.write(qrCodeImage, "png", new File("a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 生成uuid
		String uuid = IdUtil.fastSimpleUUID();

		// 创建基于Twitter SnowFlake算法的唯一ID，适用于分布式系统
		final Snowflake snowflake = IdUtil.createSnowflake(1, 1);
		final long id = snowflake.nextId();
	}
}
