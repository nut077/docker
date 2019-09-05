package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository repository;
  private final StudentMapper mapper;

  public List<StudentDto> findAll() {
    return mapper.map(repository.findAll());
  }

  public StudentDto findById(long id) {
    Optional<Student> student = repository.findById(id);
    return student.map(mapper::map).orElse(null);
  }

  public StudentDto save(StudentDto dto) {
    return mapper.map(repository.save(mapper.map(dto)));
  }

  public String delete(Long id) {
    Optional<Student> student = repository.findById(id);
    if (student.isPresent()) {
      repository.deleteById(id);
      return "Student id -->> " + id + " is deleted";
    }
    throw new NotFoundException("Student id -->> " + id + " not found");
  }
}
