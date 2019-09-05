package com.github.nut077.docker.controller;

import com.github.nut077.docker.dto.StudentDto;
import com.github.nut077.docker.entity.Student;
import com.github.nut077.docker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StudentWebController {

  private final StudentService studentService;

  @GetMapping("/")
  public String studentList(Model model) {
    model.addAttribute("students", studentService.findAll());
    return "student-list";
  }

  @GetMapping("/studentFormAdd")
  public String studentFormAdd(Model model) {
    model.addAttribute("student", new StudentDto());
    return "student-form";
  }

  @PostMapping("/studentFormUpdate")
  public String studentFormUpdate(@RequestParam("studentId") long id, Model model) {
    model.addAttribute("student", studentService.findById(id));
    return "student-form";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute("student") StudentDto studentDto) {
    studentService.save(studentDto);
    return "redirect:/";
  }

  @PostMapping("/delete")
  public String delete(@ModelAttribute("studentId") long id) {
    studentService.delete(id);
    return "redirect:/";
  }
}
