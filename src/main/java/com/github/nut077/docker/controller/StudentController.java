package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.github.nut077.docker.dto.response.SuccessResponse.builder;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class StudentController extends CommonController {

  private final StudentService studentService;

  @GetMapping("/students")
  public ResponseEntity getAll() {
    return ok(builder(studentService.getAll()).build());
  }

  @PostMapping("/students")
  public ResponseEntity create(@RequestBody StudentDto dto) {
    return ok(builder(studentService.create(dto)).build());
  }
}
