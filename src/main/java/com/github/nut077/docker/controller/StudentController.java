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

  @GetMapping("/students/{id}")
  public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.findById(id));
  }

  @PostMapping("/students")
  public ResponseEntity<StudentDto> save(@RequestBody StudentDto dto) {
    return new ResponseEntity<>(studentService.save(dto), HttpStatus.CREATED);
  }

  @PutMapping("/students/{id}")
  public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto dto) {
    return ResponseEntity.ok(studentService.update(id, dto));
  }

  @DeleteMapping("/students/{id}")
  public void delete(@PathVariable Long id) {
    studentService.delete(id);
  }
}
