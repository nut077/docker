package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.github.nut077.docker.config.CacheConfig.CacheName.SCHOOLS;

@CacheConfig(cacheNames = SCHOOLS)
@Service
public class SchoolService extends BaseService<School, SchoolDto, Long>{

  private final SchoolRepository schoolRepository;
  private final SchoolMapper mapper;

  public SchoolService(SchoolRepository schoolRepository, SchoolMapper mapper) {
    super(schoolRepository, mapper);
    this.schoolRepository = schoolRepository;
    this.mapper = mapper;
  }

  @Cacheable
  public School findByIdEntity(Long id) {
    return schoolRepository.findById(id).orElseThrow(() -> new NotFoundException("id: " + id + " -->> Not Found"));
  }
}
