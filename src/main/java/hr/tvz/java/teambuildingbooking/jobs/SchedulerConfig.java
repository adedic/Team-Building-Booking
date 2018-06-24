package hr.tvz.java.teambuildingbooking.jobs;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

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
