package top.zhost;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * ������
 */
public class QuartzDemo2 implements Job{
    /**
     * ���񱻴���ʱִ�еķ���
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("���յ���Ϣ�ˡ�����22");
    }
}
