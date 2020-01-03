package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.mapper.SchoolMapper;
import com.github.nut077.docker.exception.NotFoundException;
import com.github.nut077.docker.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

  private final SchoolRepository schoolRepository;
  private final SchoolMapper schoolMapper;

  public List<SchoolDto> getAll() {
    return schoolMapper.mapToListDto(schoolRepository.findAll());
  }

  public SchoolDto findById(Long schoolId) {
    return schoolMapper.mapToDto(
      schoolRepository.findById(schoolId)
        .orElseThrow(() -> new NotFoundException("school id [" + schoolId + "] is not found")));
  }

  public SchoolDto create(SchoolDto dto) {
    return schoolMapper.mapToDto(schoolRepository.save(schoolMapper.mapToEntity(dto)));
  }
}
