package com.github.nut077.docker.controller;

import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StudentWebController {

  private final StudentService studentService;

  @GetMapping("/")
  public String studentList(Model model) {
    model.addAttribute("students", studentService.findAll());
    return "index";
  }
}
