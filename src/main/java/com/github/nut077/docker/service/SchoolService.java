package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.PageDto;
import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.github.nut077.docker.config.CacheConfig.CacheName.SCHOOL;
import static com.github.nut077.docker.config.CacheConfig.CacheName.SCHOOLS;

@Service
@CacheConfig(cacheNames = SCHOOL)
public class SchoolService extends BasePageAndSortService<School, SchoolDto, Long>{

  @Value("${pageConfig.page}")
  private String page;

  @Value("${pageConfig.perPage}")
  private int perPage;

  public SchoolService(SchoolRepository schoolRepository, SchoolMapper mapper) {
    super(schoolRepository, mapper);
  }

  @Cacheable(cacheNames = SCHOOLS)
  @Override
  public List<SchoolDto> findAll() {
    return super.findAll();
  }

  @CachePut(key = "#school.id")
  @Override
  public SchoolDto update(Long id, SchoolDto dto) {
    return super.update(id, dto);
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
    int end = Math.min((start + pageable.getPageSize()), schoolDto.getStudent().size());
    Page<StudentDto> pages = new PageImpl<>(schoolDto.getStudent().subList(start, end), pageable, schoolDto.getStudent().size());
    PageDto pageDto = new PageDto(pages.getNumber(), pages.getSize(),
            pages.getTotalPages(), pages.getTotalElements());
    return new DataPageDto<>(pages.getContent(), pageDto);
  }
}
