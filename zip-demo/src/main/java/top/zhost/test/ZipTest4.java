package top.zhost.test;

import java.io.File;
import java.io.FileOutputStream;

public class ZipTest4 {
	public static void main(String[] args) throws Exception{
        /** ≤‚ ‘—πÀı∑Ω∑®1  */
		for(int i=0;i<2;i++) {
			ZipThread zipThread = new ZipThread(i+"");
			new Thread(zipThread).start();
		}
		
	}
}
