package com;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
* @Author Javee
* @Description ʹ�ô�����һ���½���Java�ļ���дһ�����
* public static void main(String[] args) {
*     System.out.println("Hello world!");
* }
* ������
*/
public class AutoControl {
  public static void main(String[] args) throws AWTException {
      Robot robot = new Robot();
      robot.delay(3000);
      //��ģ������������
      robot.keyPress(KeyEvent.VK_P);
      robot.keyRelease(KeyEvent.VK_P);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_S);
      robot.keyRelease(KeyEvent.VK_S);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_V);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_M);
      robot.keyRelease(KeyEvent.VK_M);

      robot.delay(500);
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      robot.delay(100);

      //��ģ���������sout���»س��õ�System.out.println();

      robot.keyPress(KeyEvent.VK_S);
      robot.keyRelease(KeyEvent.VK_S);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_O);
      robot.keyRelease(KeyEvent.VK_O);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_U);
      robot.keyRelease(KeyEvent.VK_U);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_T);
      robot.keyRelease(KeyEvent.VK_T);

      robot.delay(500);
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      robot.delay(100);

      //��������������"Hello world!"
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_QUOTE);

      robot.keyRelease(KeyEvent.VK_SHIFT);
      robot.keyRelease(KeyEvent.VK_QUOTE);
      robot.delay(100);

      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_H);
      robot.keyRelease(KeyEvent.VK_SHIFT);
      robot.keyRelease(KeyEvent.VK_H);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_E);
      robot.keyRelease(KeyEvent.VK_E);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_L);
      robot.keyRelease(KeyEvent.VK_L);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_L);
      robot.keyRelease(KeyEvent.VK_L);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_O);
      robot.keyRelease(KeyEvent.VK_O);
      robot.delay(100);

      robot.keyPress(KeyEvent.VK_SPACE);
      robot.keyRelease(KeyEvent.VK_SPACE);
      robot.delay(100);

      robot.keyPress(KeyEvent.VK_W);
      robot.keyRelease(KeyEvent.VK_W);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_O);
      robot.keyRelease(KeyEvent.VK_O);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_R);
      robot.keyRelease(KeyEvent.VK_R);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_L);
      robot.keyRelease(KeyEvent.VK_L);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_D);
      robot.keyRelease(KeyEvent.VK_D);
      robot.delay(100);
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_1);
      robot.keyRelease(KeyEvent.VK_SHIFT);
      robot.keyRelease(KeyEvent.VK_1);

      //ģ���ֶ�����
      robot.delay(1000);
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_F10);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      robot.keyRelease(KeyEvent.VK_SHIFT);
      robot.keyRelease(KeyEvent.VK_F10);
  }
}
