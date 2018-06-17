package hr.tvz.java.teambuildingbooking.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class TestJob extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        log.info("Job Success!");
    }

}
