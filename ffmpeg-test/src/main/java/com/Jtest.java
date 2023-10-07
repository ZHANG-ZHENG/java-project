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
        // 创建一个线程池，参数依次为：核心线程数、最大线程数、空闲线程等待时间、时间单位、工作队列
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        // 创建任务列表
        List<Future<String>> futures = new ArrayList<>();     
        // 提交任务给线程池执行
        int taskCount = 1;
        for (int i = 0; i < taskCount; i++) {
            Callable<String> task = new Task(i);
            Future<String> future = executor.submit(task);
            futures.add(future);
        }
        
        // 获取任务执行结果
        for (Future<String> future : futures) {
            try {
                String result = future.get(); // 阻塞等待任务执行结果
                System.out.println("Task result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }    
        System.out.println("全部"+taskCount+"个任务执行完成用时: " + (System.currentTimeMillis()-startTime));
        executor.shutdown();
    }
    // 自定义任务类
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
	        		//System.err.println(inputPath+"原文件不存在");
	        		return  "Task " + taskId +"原文件不存在";
	        	}
	        	File outputFile = new File(outputPath);
	        	if(outputFile.exists()) {
	        		//System.out.println(outputPath+"目标文件已存在，删除");
	        		outputFile.delete();
	        	}	
	            // 定义FFmpeg命令
	            String ffmpegCommand = ffmpegPath+" -i "+inputPath+" -vn -acodec copy "+outputPath;
	        	System.out.println("执行命令"+ffmpegCommand);
	            // 创建进程构建器
	            ProcessBuilder processBuilder = new ProcessBuilder();
	            // 将命令按空格拆分为命令和参数，并设置进程构建器的命令列表
	            processBuilder.command(ffmpegCommand.split(" "));
	            // 将进程的输出与错误流合并到Java进程的输出流
	            processBuilder.redirectErrorStream(true);
	            // 启动进程
	            Process process = processBuilder.start();
	            // 获取进程的输入流，读取命令执行结果
	            InputStream inputStream = process.getInputStream();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            
	            String line;
	            while ((line = reader.readLine()) != null) {
	                //System.out.println(line);
	            }
	            // 等待进程执行完成
	            int exitCode = process.waitFor();
	            if (exitCode == 0) {
	               // System.out.println("Task " + taskId +"执行完成用时" + (System.currentTimeMillis()-startTime));
	                return "Task " + taskId +"执行完成用时" + (System.currentTimeMillis()-startTime);
	            } else {
	                //System.err.println("Task " + taskId +"执行失败用时" + (System.currentTimeMillis()-startTime));
	                return "Task " + taskId +"执行失败用时" + (System.currentTimeMillis()-startTime);
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }        	
            return "Task " + taskId + " executed by " + Thread.currentThread().getName();
        }
    }
}	