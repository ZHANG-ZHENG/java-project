package top.zhost;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * ������
 */
public class QuartzDemo1 implements Job{
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
        System.out.println("���յ���Ϣ�ˡ�����"+taskId);
    }
}
