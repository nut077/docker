package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.entity.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper extends BaseMapper<Student, StudentDto> {

  @Override
  @Mapping(target = "school.id", source = "schoolId")
  Student mapToEntity(StudentDto dto);
}
