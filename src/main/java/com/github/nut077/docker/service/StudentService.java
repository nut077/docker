package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.github.nut077.docker.config.CacheConfig.CacheName.STUDENT;

@Service
@Log4j2
@CacheConfig(cacheNames = STUDENT)
public class StudentService extends BasePageAndSortService<Student, StudentDto, Long> {

  private final StudentRepository studentRepository;
  private final StudentMapper mapper;
  private final SchoolRepository schoolRepository;

  public StudentService(StudentRepository studentRepository, StudentMapper mapper, SchoolRepository schoolRepository) {
    super(studentRepository, mapper);
    this.studentRepository = studentRepository;
    this.mapper = mapper;
    this.schoolRepository = schoolRepository;
  }

  public StudentDto create(Long id, StudentDto dto) {
    Optional<School> school = schoolRepository.findById(id);
    if (school.isPresent()) {
      dto.setSchool(school.get());
      return mapper.mapToDto(studentRepository.save(mapper.mapToEntity(dto)));
    } else {
      throw new NotFoundException("School id: " + id + " -->> Not Found");
    }
  }

  @Cacheable
  @Override
  public StudentDto findById(Long id) {
    return super.findById(id);
  }

  @CachePut(key = "#student.id")
  @Override
  public StudentDto update(Long id, StudentDto dto) {
    return super.update(id, dto);
  }
}
