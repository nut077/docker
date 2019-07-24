package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.entity.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {SetMapper.class, BooleanStringMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {

  @Mapping(source = "isActive", target = "activeStatus")
  StudentDto map(Student student);

  List<StudentDto> map(Collection<Student> product);

  @InheritInverseConfiguration
  Student map(StudentDto studentDto);

  List<Student> map(List<StudentDto> dto);
}
