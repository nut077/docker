package com.github.nut077.docker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataPageDto<T> {
  private List<T> list;
  private PageDto page;
}
