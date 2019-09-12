package com.github.nut077.docker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nut077.docker.entity.School;
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
  @JsonIgnore
  private School school;
}
