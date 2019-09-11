package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.entity.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {SetMapper.class, BooleanStringMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper extends BaseMapper<Student, StudentDto> {

  @Override
  @Mapping(source = "isActive", target = "activeStatus")
  StudentDto mapToDto(Student entity);
}
