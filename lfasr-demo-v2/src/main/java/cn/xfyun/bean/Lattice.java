package cn.xfyun.bean;

/**
 * 识别结果
 * @author zz
 *
 */
public class Lattice {

	private Integer begin;
	private Integer end;
	private Json1Best  json_1best;
	
	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Json1Best getJson_1best() {
		return json_1best;
	}
	public void setJson_1best(Json1Best json_1best) {
		this.json_1best = json_1best;
	}

}
