package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.Random;

@Controller
public class HomeController {

    // 🔥 入口（选择身份）
    @GetMapping("/")
    public String landing() {
        return "login"; // 👉 login.html（选择 Citizen / Admin）
    }

    // ================================
    // 👤 Citizen Login
    // ================================

    // 👤 Citizen 登录页面
    @GetMapping("/user-login")
    public String userLoginPage() {
        return "user_login"; // 👉 user_login.html
    }

    // 👤 Citizen 登录逻辑（随便输入都可以进）
    @PostMapping("/user-login")
    public String userLogin(String username, String password,
                            jakarta.servlet.http.HttpSession session) {

        // ✅ 不管输入什么都通过（demo用）
        session.setAttribute("userRole", "citizen");

        return "redirect:/home";
    }

    // 👤 Citizen 页面（上传）
    @GetMapping("/home")
    public String home(jakarta.servlet.http.HttpSession session) {

        Object role = session.getAttribute("userRole");

        // ❌ 没登录不能进
        if (role == null) {
            return "redirect:/";
        }

        return "index";
    }

    // ================================
    // 🔐 Admin Login
    // ================================

    // 🔐 Admin 登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "login_admin"; // 👉 login_admin.html
    }

    // 🔐 Admin 登录逻辑
    @PostMapping("/login")
    public String login(String username, String password,
                        jakarta.servlet.http.HttpSession session,
                        Model model) {

        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("user", "admin");
            return "redirect:/admin";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login_admin";
    }

    // 🔒 Admin Dashboard
    @GetMapping("/admin")
    public String admin(jakarta.servlet.http.HttpSession session, Model model) {

        Object user = session.getAttribute("user");

        // ❌ 没登录不能进
        if (user == null) {
            return "redirect:/login";
        }

        java.util.List<String> results = java.util.Arrays.asList(
                "Approved",
                "Rejected",
                "Review Needed",
                "Approved"
        );

        model.addAttribute("results", results);

        return "admin";
    }

    // ================================
    // 🔥 Logout（两个角色都用）
    // ================================
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ================================
    // 🤖 AI 分析
    // ================================
    @PostMapping("/analyze")
    public String analyze(Model model) {

        Random random = new Random();

        double confidence = random.nextDouble();
        String decision;
        String risk;

        if (confidence > 0.85) {
            decision = "Approved";
            risk = "Low";
        } else if (confidence > 0.75) {
            decision = "Review Needed";
            risk = "Medium";
        } else {
            decision = "Rejected";
            risk = "High";
        }

        model.addAttribute("confidence", confidence);
        model.addAttribute("decision", decision);
        model.addAttribute("risk", risk);

        return "result";
    }
}