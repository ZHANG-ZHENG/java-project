package top.zhost.test;

import java.io.File;
import java.io.FileOutputStream;

public class ZipTest3 {
	public static void main(String[] args) throws Exception{
        /** ≤‚ ‘—πÀı∑Ω∑®1  */
        FileOutputStream fos1 = new FileOutputStream(new File("G:/zip-test/local-server/local-server.zip"));
        ZipUtils.toZip("G:/zip-test/local-server/local-server-2021-12-01-0.log", fos1,true);		
	}
}
