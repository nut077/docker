package com.github.nut077.docker.config.property;

import com.github.nut077.docker.config.YmlSourceConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:scheduler.yml", factory = YmlSourceConfig.class)
@ConfigurationProperties("custom.scheduler")
public class SchedulerProperty {

  private int poolSize;
  private String threadNamePrefix;
}
