//package com.github.semiprojectshop.web.sihu.controller;
//
//import com.github.semiprojectshop.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/member")
//@RequiredArgsConstructor
//public class MemberController {
//    private final MemberService memberService;
//
//    @GetMapping("/memberRegister.up")
//    public String registerPage() {
//        return "member/register";
//    }
//
//    @PostMapping("/login.up")
//    public String login(@RequestParam String username, @RequestParam String password) {
//
//        return "redirect:/home"; // 로그인 성공 후 리다이렉트
//    }
//
//    @GetMapping("/register")
//    public String test(){
//        return "member/register";
//    }
//}
