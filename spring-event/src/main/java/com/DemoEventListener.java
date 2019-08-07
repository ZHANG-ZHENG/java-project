package com;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * ����һ���¼�������
 * @author mingge
 *
 */
@Component
public class DemoEventListener implements ApplicationListener<DemoEvent>{

    //ʹ��ע��@Async֧�� ������������֧��ͨ�����ã�Ҳ֧���첽���ã��ǳ�����
    @Async
    @Override
    public void onApplicationEvent(DemoEvent event) {
        System.out.println("ע��ɹ�������ȷ���ʼ�Ϊ��" + event.getEmail()+",��ϢժҪΪ:"+event.getMsg());  
    }

}
