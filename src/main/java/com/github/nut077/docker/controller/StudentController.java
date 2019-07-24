package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/students")
  private ResponseEntity<List<StudentDto>> findAll() {
    return ResponseEntity.ok(studentService.findAll());
  }

  @PostMapping("/students")
  private ResponseEntity<StudentDto> save(@RequestBody StudentDto dto) {
    return ResponseEntity.ok(studentService.save(dto));
  }
}
