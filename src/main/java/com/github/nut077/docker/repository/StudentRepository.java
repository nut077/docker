package com.github.nut077.docker.repository;

import com.github.nut077.docker.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

  @Query(nativeQuery = true, value = "select * from students s where s.school_id=:schoolId")
  Page<Student> findBySchool(@Param("schoolId") Long schoolId, Pageable pageable);
}
