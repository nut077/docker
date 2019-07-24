package com.github.nut077.docker.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "students")
@SequenceGenerator(name = "student_seq")
@Data
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
  private Long id;
  private String firstName;
  private String lastName;
  private int age;
  private Boolean isActive;
}
