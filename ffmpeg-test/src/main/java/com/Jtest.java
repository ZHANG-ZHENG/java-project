package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * @author zz
 * ffmpeg -i test1.mp4 -c:a copy -vn test1.aac
 * ffmpeg -i test1.mp4  -acodec aac -vn testaac.aac
 * ffmpeg -i test1.mp4 -vn -acodec copy test1-vn.mp4
 * E:/clib/ffmpeg-6.0-full_build-shared/bin/ffmpeg -i C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/input/test1.mp4 -vn -acodec copy C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/input/test1-vn-java.mp4
 */
public class Jtest {
    public static void main(String[] args) {
    	Long startTime = System.currentTimeMillis();
        // ����һ���̳߳أ���������Ϊ�������߳���������߳����������̵߳ȴ�ʱ�䡢ʱ�䵥λ����������
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        // ���������б�
        List<Future<String>> futures = new ArrayList<>();     
        // �ύ������̳߳�ִ��
        int taskCount = 1;
        for (int i = 0; i < taskCount; i++) {
            Callable<String> task = new Task(i);
            Future<String> future = executor.submit(task);
            futures.add(future);
        }
        
        // ��ȡ����ִ�н��
        for (Future<String> future : futures) {
            try {
                String result = future.get(); // �����ȴ�����ִ�н��
                System.out.println("Task result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }    
        System.out.println("ȫ��"+taskCount+"������ִ�������ʱ: " + (System.currentTimeMillis()-startTime));
        executor.shutdown();
    }
    // �Զ���������
    static class Task implements Callable<String> {
        private int taskId;
        
        public Task(int taskId) {
            this.taskId = taskId;
        }
        
        @Override
        public String call() throws Exception {
        	try {
        		Long startTime = System.currentTimeMillis();
        		String ffmpegPath = "E:/clib/ffmpeg-6.0-full_build-shared/bin/ffmpeg";
	        	String inputPath = "C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/input/test1.mp4";
	        	//String outputPath = "C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/output/test"+taskId+"-vn-java.mp4";
	        	String outputPath = "C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/output/test"+taskId+"-vn-java.m4a";
	        	File inputFile = new File(inputPath);
	        	if(!inputFile.exists()) {
	        		//System.err.println(inputPath+"ԭ�ļ�������");
	        		return  "Task " + taskId +"ԭ�ļ�������";
	        	}
	        	File outputFile = new File(outputPath);
	        	if(outputFile.exists()) {
	        		//System.out.println(outputPath+"Ŀ���ļ��Ѵ��ڣ�ɾ��");
	        		outputFile.delete();
	        	}	
	            // ����FFmpeg����
	            String ffmpegCommand = ffmpegPath+" -i "+inputPath+" -vn -acodec copy "+outputPath;
	        	System.out.println("ִ������"+ffmpegCommand);
	            // �������̹�����
	            ProcessBuilder processBuilder = new ProcessBuilder();
	            // ������ո���Ϊ����Ͳ����������ý��̹������������б�
	            processBuilder.command(ffmpegCommand.split(" "));
	            // �����̵������������ϲ���Java���̵������
	            processBuilder.redirectErrorStream(true);
	            // ��������
	            Process process = processBuilder.start();
	            // ��ȡ���̵�����������ȡ����ִ�н��
	            InputStream inputStream = process.getInputStream();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            
	            String line;
	            while ((line = reader.readLine()) != null) {
	                //System.out.println(line);
	            }
	            // �ȴ�����ִ�����
	            int exitCode = process.waitFor();
	            if (exitCode == 0) {
	               // System.out.println("Task " + taskId +"ִ�������ʱ" + (System.currentTimeMillis()-startTime));
	                return "Task " + taskId +"ִ�������ʱ" + (System.currentTimeMillis()-startTime);
	            } else {
	                //System.err.println("Task " + taskId +"ִ��ʧ����ʱ" + (System.currentTimeMillis()-startTime));
	                return "Task " + taskId +"ִ��ʧ����ʱ" + (System.currentTimeMillis()-startTime);
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }        	
            return "Task " + taskId + " executed by " + Thread.currentThread().getName();
        }
    }
}	