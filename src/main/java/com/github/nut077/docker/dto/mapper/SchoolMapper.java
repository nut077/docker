package com.github.nut077.docker.dto.mapper;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.entity.School;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SchoolMapper extends BaseMapper<School, SchoolDto> {
}
