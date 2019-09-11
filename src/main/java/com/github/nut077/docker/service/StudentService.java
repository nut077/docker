package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends BaseService<Student, StudentDto, Long> {

  private final StudentRepository repository;
  private final StudentMapper mapper;

  public StudentService(StudentRepository repository, StudentMapper mapper) {
    super(repository, mapper);
    this.repository = repository;
    this.mapper = mapper;
  }
}
