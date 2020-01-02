package com.github.nut077.docker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "students")
public class Student extends Common {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private int age;

  @JsonIgnore
  @JoinColumn(name = "schoolId")
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;
}
