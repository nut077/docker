package com.github.nut077.docker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "schools")
public class School extends Common {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String address;

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
  private List<Student> students;

  public void setStudents(List<Student> students) {
    students.forEach(student -> student.setSchool(this));
    this.students = students;
  }
}
