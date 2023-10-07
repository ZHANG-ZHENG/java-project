package cn.xfyun.bean;

import java.util.List;

public class Ws {
	
	private Integer wb;//词语开始的帧数（注一帧 10ms），位置是相对 bg
	private Integer we;//词语结束的帧数（注一帧 10ms），位置是相对 bg
	private List<Cw> cw;//词语候选识别结果集合
	
	public Integer getWb() {
		return wb;
	}
	public void setWb(Integer wb) {
		this.wb = wb;
	}
	public Integer getWe() {
		return we;
	}
	public void setWe(Integer we) {
		this.we = we;
	}
	public List<Cw> getCw() {
		return cw;
	}
	public void setCw(List<Cw> cw) {
		this.cw = cw;
	}
	
}
