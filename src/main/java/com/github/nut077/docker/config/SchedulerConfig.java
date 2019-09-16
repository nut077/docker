package com.github.nut077.docker.config;

import com.github.nut077.docker.config.property.SchedulerProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig implements SchedulingConfigurer {

  private final SchedulerProperty property;

  @Override
  public void configureTasks(ScheduledTaskRegistrar task) {
    ThreadPoolTaskScheduler scheduler = new TaskSchedulerBuilder()
            .poolSize(property.getPoolSize())
            .threadNamePrefix(property.getThreadNamePrefix())
            .build();
    scheduler.initialize();
    task.setScheduler(scheduler);
  }
}
