package cn.xfyun.bean;

public class Content {
	
	private String orderId;
	

	
	private String orderResult;//转写结果
	
	private Integer taskEstimateTime;//订单预估耗时，单位毫秒
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(String orderResult) {
		this.orderResult = orderResult;
	}

	public Integer getTaskEstimateTime() {
		return taskEstimateTime;
	}

	public void setTaskEstimateTime(Integer taskEstimateTime) {
		this.taskEstimateTime = taskEstimateTime;
	}


}
