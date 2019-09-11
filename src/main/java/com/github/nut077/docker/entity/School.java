package com.github.nut077.docker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "schools")
@SequenceGenerator(name = "school_seq")
@Getter
@Setter
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_seq")
  private Long id;
  private String name;
  private String address;
}
