package top.zhost.test;

import java.io.File;
import java.io.FileOutputStream;

public class ZipTest2 {
	public static void main(String[] args) throws Exception{
        /** ≤‚ ‘—πÀı∑Ω∑®1  */
        FileOutputStream fos1 = new FileOutputStream(new File("F:/zip-test/local-server/local-server.zip"));
        ZipUtils.toZip("F:/zip-test/local-server/local-server-2021-12-01-0.log", fos1,true);		
	}
}
