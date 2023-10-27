package test.erabbit;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : zzzz " + context);
        this.rabbitTemplate.convertAndSend("hello","zzz " + context);
    }
    
    public void send2() {
        String context = "hello2 zzz" ;

        this.rabbitTemplate.convertAndSend("exchange","topic.message","topic " + context);
    }
    public void send22() {
        String context = "hello2 zzz" ;

        this.rabbitTemplate.convertAndSend("exchange","topic.messages","topic " + context);
    }
    
    public void send3() {
        String context = "hello3 zzz" ;

        this.rabbitTemplate.convertAndSend("fanoutExchange","","zzz " + context);
    }
}