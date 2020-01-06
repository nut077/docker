package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.StudentRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.nut077.docker.config.CaffeineCacheConfig.CacheName.SCHOOL;
import static com.github.nut077.docker.config.CaffeineCacheConfig.CacheName.STUDENT;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = STUDENT)
public class StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  public List<StudentDto> getAll() {
    return studentMapper.mapToListDto(Lists.newArrayList(studentRepository.findAll()));
  }

  @CacheEvict(cacheNames = SCHOOL, allEntries = true)
  public StudentDto create(StudentDto dto) {
    return studentMapper.mapToDto(studentRepository.save(studentMapper.mapToEntity(dto)));
  }

  @Cacheable
  public StudentDto findById(Long studentId) {
    return studentMapper.mapToDto(studentRepository.findById(studentId)
      .orElseThrow(() -> new NotFoundException("Student id [" + studentId + "] is not found")));
  }
}
