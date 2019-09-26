package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.nut077.docker.config.CacheConfig.CacheName.*;

@Service
@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = STUDENT)
public class StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper mapper;
  private final SchoolRepository schoolRepository;

  public StudentDto create(Long id, StudentDto dto) {
    Optional<School> school = schoolRepository.findById(id);
    if (school.isPresent()) {
      dto.setSchool(school.get());
      return mapper.mapToDto(studentRepository.save(mapper.mapToEntity(dto)));
    } else {
      throw new NotFoundException("School id: " + id + " -->> Not Found");
    }
  }

  @Cacheable(cacheNames = STUDENTS)
  public List<StudentDto> findAll() {
    return mapper.mapToListDto(Lists.newArrayList(studentRepository.findAll()));
  }

  @Cacheable
  public StudentDto findById(Long id) {
    Optional<Student> entity = studentRepository.findById(id);
    return entity.map(mapper::mapToDto).orElseThrow(() -> new NotFoundException("Student id: " + id + " -->> Not Found"));
  }

  public StudentDto create(StudentDto dto) {
    return mapper.mapToDto(studentRepository.save(mapper.mapToEntity(dto)));
  }

  @CachePut(key = "#student.id")
  public StudentDto update(Long id, StudentDto dto) {
    findById(id);
    return create(dto);
  }

  @CacheEvict(key = "#id")
  public void delete(Long id) {
    findById(id);
    schoolRepository.deleteById(id);
    log.info("Student id:{} -->> is deleted", id);
  }
}
