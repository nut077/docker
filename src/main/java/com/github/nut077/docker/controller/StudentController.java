package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.service.SchoolService;
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
  private final SchoolService schoolService;

  @GetMapping("/students")
  public ResponseEntity<List<StudentDto>> findAll() {
    return ResponseEntity.ok(studentService.findAll());
  }

  @GetMapping("/students/{id}")
  public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.findById(id));
  }

  @GetMapping("/students/schools/{schoolId}")
  public ResponseEntity<DataPageDto<StudentDto>> findBySchool(@PathVariable Long schoolId, @RequestParam(required = false) String page) {
    return ResponseEntity.ok(studentService.findBySchool(schoolService.findByIdEntity(schoolId), page));
  }

  @PostMapping("/students/{schoolId}")
  public ResponseEntity<StudentDto> create(@PathVariable Long schoolId, @RequestBody StudentDto dto) {
    return new ResponseEntity<>(studentService.create(schoolId, dto), HttpStatus.CREATED);
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
