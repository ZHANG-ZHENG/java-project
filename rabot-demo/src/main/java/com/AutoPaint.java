package com;

//robot����¼�һ��С���ӣ�

import java.awt.*;
import java.awt.event.InputEvent;

/**
* @Author Javee
* @Description дһ���ű����򿪵��Ի�����Զ��ڻ����ϻ�һ�������廷
*/
public class AutoPaint {
  public static void main(String[] args) throws AWTException {
      Robot robot = new Robot();
      robot.delay(3000); //���д������ͣ���룬����ʱ��ȥ�򿪵����Դ��Ļ��壬�������״�������Բ��

      //1111111111111111111111
      int i = 10;
      while (i-- > 0) {
          robot.mouseMove(400, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.delay(100);       //�����ӳ�0.1s�����Կ�����̬���Ĺ���
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(650, 550);
      }
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.delay(1000);       //����һ��Բֹͣ0.2s��������Ϊ�����ִ���ٶ�̫�죬��������̬��ͼ�Ĺ���

      //222222222222222222222
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(0, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK); //����һ��Բ����Բ�����һ����꣬������϶�����Բ����һ��λ��
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(600, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.delay(100);       //�����ӳ�0.1s�����Կ�����̬���Ĺ���
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(850, 550);
      }
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.delay(1000);       //����һ��Բֹͣ0.2s��������Ϊ�����ִ���ٶ�̫�죬��������̬��ͼ�Ĺ���

      //3333333333333333333333333
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(0, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK); //����һ��Բ����Բ�����һ����꣬������϶�����Բ����һ��λ��
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(800, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.delay(100);       //�����ӳ�0.2s�����Կ�����̬���Ĺ���
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(1050, 550);
      }
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.delay(200);       //����һ��Բֹͣ0.1s��������Ϊ�����ִ���ٶ�̫�죬��������̬��ͼ�Ĺ���

      //44444444444444444444444444
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(0, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK); //����һ��Բ����Բ�����һ����꣬������϶�����Բ����һ��λ��
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(500, 425);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.delay(100);       //�����ӳ�0.1s�����Կ�����̬���Ĺ���
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(750, 675);
      }
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.delay(200);       //����һ��Բֹͣ0.2s��������Ϊ�����ִ���ٶ�̫�죬��������̬��ͼ�Ĺ���

      //555555555555555555555555
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(0, 300);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK); //����һ��Բ����Բ�����һ����꣬������϶�����Բ����һ��λ��
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(700, 425);
      }
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.delay(100);       //�����ӳ�0.1s�����Կ�����̬���Ĺ���
      i = 10;
      while (i-- > 0) {
          robot.mouseMove(950, 675);
      }
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      robot.delay(200);       //����һ��Բֹͣ0.2s��������Ϊ�����ִ���ٶ�̫�죬��������̬��ͼ�Ĺ���


  }
}

