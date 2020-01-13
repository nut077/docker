package com.github.nut077.docker.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

  private String username;
  private String password;
}
