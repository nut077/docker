package com.github.nut077.docker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDto {

  private int page;
  private int perPage;
  @JsonProperty("length")
  private int totalPage;
  private long totalElement;
}