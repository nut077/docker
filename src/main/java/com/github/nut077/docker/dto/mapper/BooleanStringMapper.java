package com.github.nut077.docker.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring")
public interface BooleanStringMapper {

  default String toString(Boolean bool) {
    if (bool == null) {
      return "";
    }
    return bool ? "Y" : "N";
  }

  default Boolean toBoolean(String str) {
    if (StringUtils.isEmpty(str)) {
      return Boolean.FALSE;
    }
    return str.trim().toLowerCase().startsWith("y") ? Boolean.TRUE : Boolean.FALSE;
  }
}
