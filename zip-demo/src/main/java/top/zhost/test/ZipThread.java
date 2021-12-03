package top.zhost.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ZipThread implements Runnable {
	private String threadName;
	public ZipThread( String name) {
		threadName = name;
		System.out.println("Creating " +  threadName );
	}
	public void run() {
		FileOutputStream fos1 = null;
		try {
			while (true) {
				fos1 = new FileOutputStream(new File("F:/zip-test/local-server/local-server-threadName"+threadName+".zip"));
				ZipUtils.toZip("F:/zip-test/local-server/local-server-2021-12-01-0.log", fos1,true);		
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fos1!=null) {
					fos1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       
		System.out.println("Thread " +  threadName + " exiting.");
	}

}
