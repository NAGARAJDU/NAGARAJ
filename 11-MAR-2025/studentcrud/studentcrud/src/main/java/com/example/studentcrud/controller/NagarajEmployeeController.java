package com.example.studentcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.studentcrud.model.Employee;
import com.example.studentcrud.service.NagarajEmployeeService;

@Controller
@RequestMapping("/nagaraj")
public class NagarajEmployeeController {

    @Autowired
    private NagarajEmployeeService employeeService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employee", employeeService.listAll());
        return "employee/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/create";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/nagaraj/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/nagaraj/list";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployee(id));
        return "employee/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeService.saveEmployee(employee);
        return "redirect:/users/list";
    }
}