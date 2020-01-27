package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.StudentUpdateDto;
import com.github.nut077.docker.entity.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentUpdateMapper extends BaseMapper<Student, StudentUpdateDto> {

}
