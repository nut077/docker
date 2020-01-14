package com.github.nut077.docker.component.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Getter
@Validated
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("custom.caffeine.cache")
public class CaffeineCacheProperty {

  @Min(1)
  @NotNull
  private final Integer schoolMaxSize;

  @Min(1)
  @NotNull
  private final Integer studentMaxSize;

  @Min(1)
  @NotNull
  private final Integer userMaxSize;

  @NotNull
  private final Duration expireAfterAccess;

  @NotNull
  private final Duration expireAfterWrite;
}
