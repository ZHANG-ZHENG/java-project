package com;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * �¼�������
 * @author mingge
 *
 */
public class EventTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(EventConfig.class);
        DemoEventPublisher demoEventPublisher=context.getBean(DemoEventPublisher.class);
        demoEventPublisher.pushlish("����1","565792147@qq.com");
        demoEventPublisher.pushlish("����2","565792147@qq.com");
        demoEventPublisher.pushlish("����3","565792147@qq.com");
        demoEventPublisher.pushlish("����4","565792147@qq.com");
        demoEventPublisher.pushlish("����5","565792147@qq.com");
        context.close();
    }
}