package hr.tvz.java.teambuildingbooking.jobs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    /*@Bean
	public JobDetail testJobDetail() {
		return JobBuilder.newJob(TestJob.class).withIdentity("testJob")
				.storeDurably().build();
	}

	@Bean
	public Trigger testJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5).repeatForever();

		return TriggerBuilder.newTrigger().forJob(testJobDetail())
				.withIdentity("testTrigger").withSchedule(scheduleBuilder).build();
	}
*/


    @Bean
    public JobDetail offerNotificationJobDetail() {
        return JobBuilder.newJob(OfferNotificationJob.class).withIdentity("offerNotificationJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger offerNotificationJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10).repeatForever();

        return TriggerBuilder.newTrigger().forJob(offerNotificationJobDetail())
                .withIdentity("offerNotificationTrigger").withSchedule(scheduleBuilder).build();
    }
}
