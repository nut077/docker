package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository repository;
  private final StudentMapper mapper;

  public List<StudentDto> findAll() {
    return mapper.map(repository.findAll());
  }

  public StudentDto save(StudentDto dto) {
    return mapper.map(repository.saveAndFlush(mapper.map(dto)));
  }
}
