package com.github.nut077.docker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "students")
@Getter
@Setter
@SequenceGenerator(name = "student_seq")
public class Student extends Common {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
  private Long id;
  private String firstName;
  private String lastName;
  private int age;
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  @JsonIgnore
  private School school;
}
