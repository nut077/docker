package com.github.nut077.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

  private Long id;
  private String firstName;
  private String lastName;
  private int age;
  private String activeStatus;
}
