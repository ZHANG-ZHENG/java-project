package top.zhost.jni;

public class MainTest {
	
	public static void main(String[] args) {
		//dll�Ѿ��Ƶ�jdk��
		//System.loadLibrary("hellojni");
		//dll·������
		System.load("G:\\project\\c\\CMAKE\\out\\build\\x64-Debug\\hellojni\\hellojni.dll");
		HelloWorldJNI demo = new HelloWorldJNI();
		demo.helloJNI("jni test");
	}
	
}
