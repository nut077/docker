package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.dto.StudentUpdateDto;
import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.nut077.docker.config.CaffeineCacheConfig.CacheName.STUDENT;
import static com.github.nut077.docker.dto.response.SuccessResponse.builder;
import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequiredArgsConstructor
public class StudentController extends CommonController {

  private final StudentService studentService;
  private final SimpleCacheManager simpleCacheManager;

  @GetMapping("/students")
  public ResponseEntity getAll() {
    return ok(builder(studentService.getAll()).build());
  }

  @PostMapping("/students")
  public ResponseEntity create(@RequestBody StudentDto dto) {
    return ok(builder(studentService.create(dto)).build());
  }

  @PatchMapping("/students/{id}")
  public ResponseEntity update(@PathVariable Long id, @RequestBody StudentUpdateDto dto) {
    productsCache();
    return ok(builder(studentService.update(id, dto)).build());
  }

  private void productsCache() {
    log.info("Cache name STUDENT {}", ((CaffeineCache) simpleCacheManager.getCache(STUDENT)).getNativeCache().asMap());
  }

  @GetMapping("/students/{studentId}")
  public ResponseEntity findById(@PathVariable Long studentId) {
    return ok(builder(studentService.findById(studentId)).build());
  }
}
