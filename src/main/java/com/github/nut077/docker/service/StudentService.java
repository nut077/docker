package com.github.nut077.docker.service;

import com.github.nut077.docker.component.property.PageProperty;
import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.PageDto;
import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.StudentRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final SchoolService schoolService;
  private final StudentMapper studentMapper;
  private final SchoolMapper schoolMapper;
  private final PageProperty pageProperty;

  public List<StudentDto> getAll() {
    return studentMapper.mapToListDto(Lists.newArrayList(studentRepository.findAll()));
  }

  public StudentDto create(StudentDto dto) {
    return studentMapper.mapToDto(studentRepository.save(studentMapper.mapToEntity(dto)));
  }

  public DataPageDto<StudentDto> findStudentBySchoolId(Long schoolId, String page) {
    SchoolDto schoolDto = schoolService.findById(schoolId);
    if (StringUtils.isEmpty(page)) {
      page = pageProperty.getPage();
    }
    if (!page.equals("0")) {
      page = String.valueOf(Integer.parseInt(page) - 1);
    }

    Pageable pageable = PageRequest.of(Integer.parseInt(page), pageProperty.getPerPage());

    Page<Student> studentPage = studentRepository.findBySchool(schoolMapper.mapToEntity(schoolDto), pageable);

    PageDto pageDto = new PageDto(studentPage.getNumber(), studentPage.getSize(),
      studentPage.getTotalPages(), studentPage.getTotalElements());

    return new DataPageDto<>(studentMapper.mapToListDto(studentPage.getContent()), pageDto);
  }

  public StudentDto findById(Long studentId) {
    return studentMapper.mapToDto(studentRepository.findById(studentId)
      .orElseThrow(() -> new NotFoundException("Student id [" + studentId + "] is not found")));
  }
}
