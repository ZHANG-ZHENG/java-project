package cn.xfyun.bean;

/**
 * 词语候选识别结果
 * @author zz
 *
 */
public class Cw {

	private String w;//识别结果
	private String wp;//词语的属性n：正常词s：顺滑p：标点g：分段（按此标识进行分段）
	
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public String getWp() {
		return wp;
	}
	public void setWp(String wp) {
		this.wp = wp;
	}
	
}
