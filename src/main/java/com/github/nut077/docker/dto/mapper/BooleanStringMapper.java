package com.github.nut077.docker.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring")
public interface BooleanStringMapper {

  default String toString(Boolean bool) {
    if (bool == null) {
      return "";
    }
    return bool ? "true" : "false";
  }

  default Boolean toBoolean(String str) {
    if (StringUtils.isEmpty(str)) {
      return Boolean.FALSE;
    }
    return str.trim().toLowerCase().equals("true") ? Boolean.TRUE : Boolean.FALSE;
  }
}
