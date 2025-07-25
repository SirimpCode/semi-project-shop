package com.github.semiprojectshop.web.sihu.viewcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/dashboard")
    public String dashboard() {
        // Return the view name for the admin dashboard
        return "admin/dashboard";
    }
}
