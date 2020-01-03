package com.github.nut077.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

  private String id;
  private String schoolId;
  private String firstName;
  private String lastName;
  private int age;
}
