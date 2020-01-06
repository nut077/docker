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
import com.github.nut077.docker.repository.SchoolRepository;
import com.github.nut077.docker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.github.nut077.docker.config.CaffeineCacheConfig.CacheName.SCHOOL;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = SCHOOL)
public class SchoolService {

  private final SchoolRepository schoolRepository;
  private final StudentRepository studentRepository;
  private final SchoolMapper schoolMapper;
  private final PageProperty pageProperty;
  private final StudentMapper studentMapper;

  @Cacheable
  public List<SchoolDto> getAll() {
    return schoolMapper.mapToListDto(schoolRepository.findAll());
  }

  @Cacheable
  public SchoolDto findById(Long schoolId) {
    return schoolMapper.mapToDto(
      schoolRepository.findById(schoolId)
        .orElseThrow(() -> new NotFoundException("school id [" + schoolId + "] is not found")));
  }

  @Caching(
    evict = @CacheEvict(cacheNames = SCHOOL, allEntries = true)
  )
  public SchoolDto create(SchoolDto dto) {
    return schoolMapper.mapToDto(schoolRepository.save(schoolMapper.mapToEntity(dto)));
  }

  public DataPageDto<StudentDto> findStudentBySchoolIdFromDatabase(Long schoolId, String page) {
    SchoolDto schoolDto = findById(schoolId);
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

  @Cacheable
  public DataPageDto<StudentDto> findStudentBySchoolId(Long id, String page) {
    SchoolDto schoolDto = findById(id);
    if (StringUtils.isEmpty(page)) {
      page = pageProperty.getPage();
    }
    if (!page.equals("0")) {
      page = String.valueOf(Integer.parseInt(page) - 1);
    }
    Pageable pageable = PageRequest.of(Integer.parseInt(page), pageProperty.getPerPage());
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), schoolDto.getStudents().size());
    Page<StudentDto> pages = new PageImpl<>(schoolDto.getStudents().subList(start, end), pageable, schoolDto.getStudents().size());
    PageDto pageDto = new PageDto(pages.getNumber(), pages.getSize(),
      pages.getTotalPages(), pages.getTotalElements());
    return new DataPageDto<>(pages.getContent(), pageDto);
  }
}
