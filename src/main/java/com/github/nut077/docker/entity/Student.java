package com.github.nut077.docker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(
  indexes = {
    @Index(name = "students_idx_school_id", columnList = "school_id")
  }
)
@Entity(name = "students")
public class Student extends Common {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private int age;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;
}
