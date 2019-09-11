package com.github.nut077.docker.controller;

import com.github.nut077.docker.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SchoolController {

  private final SchoolService schoolService;


}
