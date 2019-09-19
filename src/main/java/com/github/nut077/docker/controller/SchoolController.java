package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.DataPageDto;
import com.github.nut077.docker.dto.SchoolDto;
import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SchoolController {
  
  private final SchoolService schoolService;

  @GetMapping("/schools")
  public ResponseEntity<List<SchoolDto>> findAll() {
    return ResponseEntity.ok(schoolService.findAll());
  }

  @GetMapping("/schools/{id}")
  public ResponseEntity<SchoolDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(schoolService.findById(id));
  }

  @GetMapping("/schools/{id}/students")
  public ResponseEntity<DataPageDto<StudentDto>> findStudentBySchoolId(@PathVariable Long id, @RequestParam(required = false) String page) {
    return ResponseEntity.ok(schoolService.findStudentBySchoolId(id, page));
  }

  @PostMapping("/schools")
  public ResponseEntity<SchoolDto> create(@RequestBody SchoolDto dto) {
    return new ResponseEntity<>(schoolService.create(dto), HttpStatus.CREATED);
  }

  @PutMapping("/schools/{id}")
  public ResponseEntity<SchoolDto> update(@PathVariable Long id, @RequestBody SchoolDto dto) {
    return ResponseEntity.ok(schoolService.update(id, dto));
  }

  @DeleteMapping("/schools/{id}")
  public void delete(@PathVariable Long id) {
    schoolService.delete(id);
  }
}
