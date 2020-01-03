package com.github.nut077.docker.repository;

import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
  Page<Student> findBySchool(School school, Pageable pageable);
}
