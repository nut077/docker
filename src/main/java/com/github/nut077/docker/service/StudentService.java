package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.PageDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.StudentMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.github.nut077.docker.config.StudentCacheConfig.CacheName.STUDENT;
import static com.github.nut077.docker.config.StudentCacheConfig.CacheName.STUDENTS;

@CacheConfig(cacheNames = STUDENT)
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

  //@Cacheable(cacheNames = STUDENTS)
  public DataPageDto findBySchool(School school, String page) {
    if (StringUtils.isEmpty(page)) {
      page = this.page;
    }
    if (!page.equals("0")) {
      page = String.valueOf(Integer.parseInt(page) - 1);
    }
    Pageable pageable = PageRequest.of(Integer.parseInt(page), perPage);
    Page<Student> bySchool = studentRepository.findBySchool(school, pageable);
    PageDto pageDto = new PageDto(bySchool.getNumber(), bySchool.getSize(),
            bySchool.getTotalPages(), bySchool.getTotalElements());
    return new DataPageDto<>(mapper.mapToListDto(bySchool.getContent()), pageDto);
  }

  @Cacheable
  @Override
  public StudentDto findById(Long id) {
    return super.findById(id);
  }
}
