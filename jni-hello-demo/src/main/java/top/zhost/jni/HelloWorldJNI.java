package top.zhost.jni;

public class HelloWorldJNI {
	
	public native void helloJNI(String name);
	
	public static void main(String[] args) {
		//System.loadLibrary("hellojni");
		System.load("G:\\project\\c\\CMAKE\\out\\build\\x64-Debug\\hellojni\\hellojni.dll");
		HelloWorldJNI demo = new HelloWorldJNI();
		demo.helloJNI("jni test");
	}
	
}
