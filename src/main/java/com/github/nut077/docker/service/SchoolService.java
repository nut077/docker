package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.repository.SchoolRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolService extends BaseService<School, SchoolDto, Long>{

  private final SchoolRepository schoolRepository;
  private final SchoolMapper mapper;

  public SchoolService(SchoolRepository schoolRepository, SchoolMapper mapper) {
    super(schoolRepository, mapper);
    this.schoolRepository = schoolRepository;
    this.mapper = mapper;
  }
}
