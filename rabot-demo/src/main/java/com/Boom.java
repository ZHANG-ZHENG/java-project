package com;

import java.awt.*;
import java.awt.event.KeyEvent;
 
/**
 * @Author Javee
 * @Description  һ���򵥵�QQ/΢�ź�ը��
 *                 �÷�������Ҫ���͵���Ϣ�ȸ��Ƶ����Ե�ճ���壨Ctrl + C��,
 *                 �����д˴��룬Ȼ�����Ҫ��ը�Ķ������촰�ڣ����һ��
 *                 �����������Զ���ը
 * */
public class Boom {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();// ����Robot����
 
        int times = 50; //��ը�Ĵ����������Լ��޸�
        int time = 500; //���κ�ը֮�������ʱ�䣬��λΪ����
 
        robot.delay(3000);// �ӳ����룬��Ҫ��Ϊ��Ԥ�����򿪴��ڵ�ʱ�䣬�����ڵĵ�λΪ����
 
        for (int j = 0; j < times; j++) {//ѭ������
            // �������а�����ctrl+v�����ճ������
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);// �ͷ�ctrl��������ctrl���˸����ɾ���������Ĺ����԰������ڰ��º�һ��Ҫ�ͷţ���Ȼ������⡣crtl�����סû���ͷţ��ڰ�������ĸ�����ǣ��ó����Ļ���ctrl�Ŀ�ݼ���
            robot.delay(time);// �ӳٷ��ͣ���Ȼ��һ����ȫ������ȥ����Ϊ���ԵĴ����ٶȺܿ죬ÿ��ճ�����͵��ٶȼ�����һ˲�䣬���Ը��˵ĸо�����һ���Է�����ȫ�������ʱ������Լ��ģ��뼸�뷢��һ��������
            robot.keyPress(KeyEvent.VK_ENTER);// �س�
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
    }
}
