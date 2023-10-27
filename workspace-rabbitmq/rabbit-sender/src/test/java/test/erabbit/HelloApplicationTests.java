package test.erabbit;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RabbitSenderApp.class)
public class HelloApplicationTests {
    @Autowired
    private Sender sender;
    @Test
    public void hello() throws Exception {
    	int i = 0;
    	while(true){
    		i++;
    		sender.send();
    		Thread.sleep(1000);
    		if(i>3){
    			break;
    		}
    	}
       
    }
}
