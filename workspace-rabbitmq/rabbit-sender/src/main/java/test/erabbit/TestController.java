package test.erabbit;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;






@RestController
@RequestMapping("/test")
public class TestController{

    @Autowired
    private Sender sender;

	@RequestMapping("/test0")
	public String test0() {
		return "test0";

	}
	
//	 @Autowired
//	 private LoadBalancerClient loadBalancerClient;

	@RequestMapping("/test")
	public void test() {
		System.out.println("dosomething");
		sender.send();

	}
	@RequestMapping("/test2")
	public void test2() {
		System.out.println("dosomething2");
		sender.send2();

	}
	@RequestMapping("/test22")
	public void test22() {
		System.out.println("dosomething2");
		sender.send22();

	}
	
	@RequestMapping("/test3")
	public void test3() {
		System.out.println("dosomething3");
		sender.send3();

	}

//	@RequestMapping("/test2")
//	  public String test2() {
//	    ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
//	    System.out.println("111" + ":" + serviceInstance.getServiceId() + ":" + serviceInstance.getHost() + ":" + serviceInstance.getPort());
//
//	    ServiceInstance serviceInstance2 = this.loadBalancerClient.choose("microservice-provider-user2");
//	    System.out.println("222" + ":" + serviceInstance2.getServiceId() + ":" + serviceInstance2.getHost() + ":" + serviceInstance2.getPort());
//
//	    return "1";
//	  }
//	@RequestMapping("/queryByObject")
//	public List<Test> queryByObject(Test queryObject) {
//		System.out.println("queryByObject"+getclassT());
//		return testService.queryByObject(queryObject);
//	}


}
