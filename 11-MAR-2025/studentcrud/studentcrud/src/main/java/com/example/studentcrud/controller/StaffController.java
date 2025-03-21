package com.example.studentcrud.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.studentcrud.model.Staff;
import com.example.studentcrud.service.StaffService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("staff", staffService.listAll());
        return "staff/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/create";
    }

    @PostMapping("/save")
    public String saveStaff(
    @RequestParam("name") String name,
    @RequestParam("email") String email,
    @RequestParam("phone") String phone,@RequestParam("profilepic") MultipartFile profilePic) {
        var uploadDir = new File("src/main/resources/static/upload/").getAbsolutePath();
        try {
        String fileName = profilePic.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;
        profilePic.transferTo(new File(filePath));
        Staff stu = new Staff();
        stu.setName(name);
        stu.setEmail(email);
        stu.setPhone(phone);
        stu.setProfilepic("/upload/" + fileName);
        staffService.saveStaff(stu);
    } catch (IOException e) {
        e.printStackTrace();
    }
        return "redirect:/staff/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/users/list";
    }

    @GetMapping("/edit/{id}")
    public String editStaff(@PathVariable Long id, Model model) {
        model.addAttribute("staff", staffService.getStaff(id));
        return "staff/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id, @ModelAttribute Staff staff) {
        staff.setId(id);
        staffService.saveStaff(staff);
        return "redirect:/users/list";
    }
}
