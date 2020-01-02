package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.nut077.docker.dto.response.SuccessResponse.builder;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class SchoolController extends CommonController {

  private final SchoolService schoolService;

  @GetMapping("/schools")
  public ResponseEntity getAll() {
    return ok(builder(schoolService.getAll()).build());
  }

  @PostMapping("/schools")
  public ResponseEntity create(@RequestBody SchoolDto dto) {
    return ok(builder(schoolService.create(dto)).build());
  }
}
