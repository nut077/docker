package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.entity.School;
import com.github.nut077.docker.entity.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SchoolMapper extends BaseMapper<School, SchoolDto> {

  @Mapping(target = "schoolId", source = "school.id")
  StudentDto studentToStudentDto(Student student);
}
