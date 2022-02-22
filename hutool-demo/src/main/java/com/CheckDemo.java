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
		// �������֤�Ż�ȡ��������
		String birth = IdcardUtil.getBirthByIdCard(idCard);

		// �������֤�Ż�ȡʡ��
		String province = IdcardUtil.getProvinceByIdCard(idCard);

		// �ж����֤���Ƿ�Ϸ�
		boolean valid = IdcardUtil.isValidCard18(idCard);

		// ��ȡһ�������������ô���
		String creditCode = CreditCodeUtil.randomCreditCode();

		// �ж�������ô����Ƿ�Ϸ�
		boolean isCreditCode = CreditCodeUtil.isCreditCode(creditCode);

		// ������תΪƴ������Ҫ����TinyPinyin��JPinyin��Pinyin4j��jar��
		String china = PinyinUtil.getPinyin("�й�");

		// ���ַ�������Ϊ��ά�룬��Ҫ����com.google.zxing.core��jar��
		BufferedImage qrCodeImage = QrCodeUtil.generate("www.baidu.com", QrConfig.create());
		try {
			ImageIO.write(qrCodeImage, "png", new File("a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ����uuid
		String uuid = IdUtil.fastSimpleUUID();

		// ��������Twitter SnowFlake�㷨��ΨһID�������ڷֲ�ʽϵͳ
		final Snowflake snowflake = IdUtil.createSnowflake(1, 1);
		final long id = snowflake.nextId();
	}
}
