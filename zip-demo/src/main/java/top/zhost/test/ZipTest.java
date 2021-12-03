package top.zhost.test;

import java.io.File;
import java.io.FileOutputStream;

public class ZipTest {
	public static void main(String[] args) throws Exception{
        /** ≤‚ ‘—πÀı∑Ω∑®1  */
        FileOutputStream fos1 = new FileOutputStream(new File("G:/zip-test/mytest01.zip"));
        ZipUtils.toZip("G:/zip-test/test.avi", fos1,true);		
	}
}
