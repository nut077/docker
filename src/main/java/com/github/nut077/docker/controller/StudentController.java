package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/students")
  public ResponseEntity<List<StudentDto>> findAll() {
    return ResponseEntity.ok(studentService.findAll());
  }

  @PostMapping("/students")
  public ResponseEntity<StudentDto> save(@RequestBody StudentDto dto) {
    return new ResponseEntity<StudentDto>(studentService.save(dto), HttpStatus.CREATED);
  }

  @DeleteMapping("/students/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.delete(id));
  }
}
