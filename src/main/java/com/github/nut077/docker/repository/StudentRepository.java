package com.github.nut077.docker.repository;

import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  List<Student> findBySchool(School school);
}
