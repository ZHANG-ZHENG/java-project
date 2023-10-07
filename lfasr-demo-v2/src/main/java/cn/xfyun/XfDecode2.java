package cn.xfyun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cn.hutool.json.JSONUtil;
import cn.xfyun.bean.Lattice;
import cn.xfyun.bean.OrderResult;
import cn.xfyun.bean.Result;
import cn.xfyun.bean.Rt;
import cn.xfyun.bean.Ws;

public class XfDecode2 {
	
	
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\test1-vn-java.txt");//testbvn test1-vn-java
		String resultStr = null;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			resultStr = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(resultStr);
		try {
			File fileSrt = new File("C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\test1-vn-java.srt");//testbvn test1-vn-java
			FileWriter writer =  new FileWriter(fileSrt);
			
			System.out.println(resultStr);	
			Result result = JSONUtil.toBean(resultStr, Result.class);
			OrderResult orderResult = JSONUtil.toBean(result.getContent().getOrderResult(), OrderResult.class);
			int i = 0;
			for(Lattice lattice : orderResult.getLattice2()) {
				 i++;
				writer.write(i+"\r\n");
				Integer begin = lattice.getBegin();
				Integer end = lattice.getEnd();
				System.out.println("lattice begin="+formatMilliseconds(begin)+",lattice end="+formatMilliseconds(end));
				writer.write(formatMilliseconds(begin)+" --> "+formatMilliseconds(end)+"\r\n");
				for(Rt rt : lattice.getJson_1best().getSt().getRt()) {
					for(Ws ws : rt.getWs()) {
						String w = ws.getCw().get(0).getW();
						System.out.print(""+w);
						writer.write(w);
					}
				}
				writer.write("\r\n\r\n");
			}
	
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static String formatMilliseconds(long milliseconds) {
        long hours = milliseconds / 3600000;
        long minutes = (milliseconds % 3600000) / 60000;
        long seconds = (milliseconds % 60000) / 1000;
        long millis = milliseconds % 1000;
        
        return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, millis);
    }
}
