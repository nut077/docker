package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.PageDto;
import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.github.nut077.docker.config.CacheConfig.CacheName.SCHOOL;
import static com.github.nut077.docker.config.CacheConfig.CacheName.SCHOOLS;

@Service
@RequiredArgsConstructor
@Log4j2
@CacheConfig(cacheNames = SCHOOL)
public class SchoolService {

  private final SchoolRepository schoolRepository;
  private final SchoolMapper mapper;

  @Value("${pageConfig.page}")
  private String page;

  @Value("${pageConfig.perPage}")
  private int perPage;

  @Cacheable(cacheNames = SCHOOLS)
  public List<SchoolDto> findAll() {
    return mapper.mapToListDto(Lists.newArrayList(schoolRepository.findAll()));
  }

  public SchoolDto findById(Long id) {
    Optional<School> entity = schoolRepository.findById(id);
    return entity.map(mapper::mapToDto).orElseThrow(() -> new NotFoundException("School id: " + id + " -->> Not Found"));
  }

  public SchoolDto create(SchoolDto dto) {
    return mapper.mapToDto(schoolRepository.save(mapper.mapToEntity(dto)));
  }

  @CachePut(key = "#school.id")
  public SchoolDto update(Long id, SchoolDto dto) {
    findById(id);
    dto.setId(id);
    return create(dto);
  }

  @CacheEvict(key = "#id")
  public void delete(Long id) {
    findById(id);
    schoolRepository.deleteById(id);
    log.info("School id:{} -->> is deleted", id);
  }

  @Cacheable
  public DataPageDto<StudentDto> findStudentBySchoolId(Long id, String page) {
    SchoolDto schoolDto = findById(id);
    if (StringUtils.isEmpty(page)) {
      page = this.page;
    }
    if (!page.equals("0")) {
      page = String.valueOf(Integer.parseInt(page) - 1);
    }
    Pageable pageable = PageRequest.of(Integer.parseInt(page), perPage);
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), schoolDto.getStudents().size());
    Page<StudentDto> pages = new PageImpl<>(schoolDto.getStudents().subList(start, end), pageable, schoolDto.getStudents().size());
    PageDto pageDto = new PageDto(pages.getNumber(), pages.getSize(),
            pages.getTotalPages(), pages.getTotalElements());
    return new DataPageDto<>(pages.getContent(), pageDto);
  }
}
