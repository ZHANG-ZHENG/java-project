package cn.xfyun.bean;

import java.util.List;

public class St {
	private String bg;	//单个句子的开始时间，单位毫秒
	private String ed;	//单个句子的结束时间，单位毫秒
	private String rl	;//分离的角色编号，取值正整数，需开启角色分离的功能才返回对应的分离角色编号
	private List<Rt> rt;//输出词语识别结果集合

	public String getBg() {
		return bg;
	}
	public void setBg(String bg) {
		this.bg = bg;
	}
	public String getEd() {
		return ed;
	}
	public void setEd(String ed) {
		this.ed = ed;
	}
	public String getRl() {
		return rl;
	}
	public void setRl(String rl) {
		this.rl = rl;
	}
	public List<Rt> getRt() {
		return rt;
	}
	public void setRt(List<Rt> rt) {
		this.rt = rt;
	}
	
}
