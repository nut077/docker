package com.github.nut077.docker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "schools")
@Getter
@Setter
@SequenceGenerator(name = "school_seq")
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_seq")
  private Long id;
  private String name;
  private String address;

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Student> student;

  public void setStudent(List<Student> student) {
    if (student != null) {
      student.forEach(student1 -> student1.setSchool(this));
    }
    this.student = student;
  }
}
