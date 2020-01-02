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

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  public List<StudentDto> getAll() {
    return studentMapper.mapToListDto(studentRepository.findAll());
  }

  public StudentDto create(StudentDto dto) {
    return studentMapper.mapToDto(studentRepository.save(studentMapper.mapToEntity(dto)));
  }
}
