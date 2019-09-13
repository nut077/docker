package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends BasePageAndSortService<Student, StudentDto, Long> {

  private final StudentRepository studentRepository;
  private final StudentMapper mapper;
  private final SchoolRepository schoolRepository;

  @Value("${pageConfig.page}")
  private String page;

  @Value("${pageConfig.perPage}")
  private int perPage;

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

  public List<StudentDto> findBySchool(School school, String page) {
    if (StringUtils.isEmpty(page)) {
      page = this.page;
    }
    Pageable pageable = PageRequest.of(Integer.parseInt(page), perPage);
    return mapper.mapToListDto(studentRepository.findBySchool(school, pageable));
  }

  public int getTotalPage(School school) {
    return (int) Math.ceil(studentRepository.countBySchool(school) / (double) perPage);
  }
}
