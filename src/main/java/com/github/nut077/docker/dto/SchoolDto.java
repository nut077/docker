package com.github.nut077.docker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchoolDto {

  private Long id;
  private String name;
  private String address;
  private List<StudentDto> student;
}
