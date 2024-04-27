
package co.istad.photostad.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@Setter
@Getter
@Configuration
@EnableScheduling
public class ScheduledConfig {
    private boolean taskEnabled = false;
    private String fileNameToDelete;

    @Scheduled(initialDelay = 2 * 60 * 60 * 1000, fixedDelay = Long.MAX_VALUE) // 2 hours in milliseconds
    public void someTask() {
        if (taskEnabled) {
            System.out.println("hello chento");
            taskEnabled = false;
        }
    }

}
