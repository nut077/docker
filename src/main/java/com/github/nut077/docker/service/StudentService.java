package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends BaseService<Student, StudentDto, Long> {

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

  public List<StudentDto> findBySchool(School school) {
    return mapper.mapToListDto(studentRepository.findBySchool(school));
  }
}
