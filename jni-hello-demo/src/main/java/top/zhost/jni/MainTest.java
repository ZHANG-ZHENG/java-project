package top.zhost.jni;

public class MainTest {
	
	public static void main(String[] args) {
		//dll已经移到jdk下
		//System.loadLibrary("hellojni");
		//dll路径加载
		System.load("G:\\project\\c\\CMAKE\\out\\build\\x64-Debug\\hellojni\\hellojni.dll");
		HelloWorldJNI demo = new HelloWorldJNI();
		demo.helloJNI("jni test");
	}
	
}
