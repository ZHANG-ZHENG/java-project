package top.zhost;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
/**
 * ������
 */
public class QuartzDemo1 implements Job{
	
	public static Integer count = 0;
	
    /**
     * ���񱻴���ʱִ�еķ���
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getTrigger().getJobDataMap();// ���������õ�����ӳ���
        Long taskId = null;
        if (dataMap != null && dataMap.size() > 0) {
            taskId = dataMap.getLong("TASK_ID");
          
        }

        count++;
        
        Scheduler scheduler = context.getScheduler();
        if(count == 3){
        	try {
				scheduler.unscheduleJob("triggerName", "triggerGroup");
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
        }
        
        System.out.println(taskId+ "���յ���Ϣ�ˡ����� "+count +" "+context.getTrigger().getNextFireTime());
    }
}
