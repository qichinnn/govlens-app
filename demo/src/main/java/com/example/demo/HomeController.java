package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Random;

@Controller
public class HomeController {

    // 🔥 入口（选择身份）
    @GetMapping("/")
    public String landing() {
        return "login";
    }

    // ================================
    // 👤 Citizen Login
    // ================================

    @GetMapping("/user-login")
    public String userLoginPage() {
        return "user_login";
    }

    @PostMapping("/user-login")
    public String userLogin(String username, String password,
                            jakarta.servlet.http.HttpSession session) {

        session.setAttribute("userRole", "citizen");
        return "redirect:/home";
    }

    // 👤 Citizen 页面
    @GetMapping("/home")
    public String home(jakarta.servlet.http.HttpSession session) {

        Object role = session.getAttribute("userRole");

        if (role == null) {
            return "redirect:/";
        }

        return "index";
    }

    // ================================
    // 🔐 Admin Login
    // ================================

    @GetMapping("/login")
    public String loginPage() {
        return "login_admin";
    }

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

    // 🔒 Admin Dashboard（🔥最终升级版）
    @GetMapping("/admin")
    public String admin(jakarta.servlet.http.HttpSession session, Model model) {

        Object user = session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // 🔥 数据（模拟真实系统）
        java.util.List<String> results = java.util.Arrays.asList(
                "Ali - ✅ Approved - 16 Apr",
                "John - ❌ Rejected - 15 Apr",
                "Siti - ⚠ Review Needed - 16 Apr",
                "Ahmad - ✅ Approved - 14 Apr"
        );

        // 🔥 计算数量
        int approvedCount = 0;
        int rejectedCount = 0;

        for (String r : results) {
            if (r.contains("Approved")) {
                approvedCount++;
            } else if (r.contains("Rejected")) {
                rejectedCount++;
            }
        }

        // 🔥 传给前端
        model.addAttribute("results", results);
        model.addAttribute("approved", approvedCount);
        model.addAttribute("rejected", rejectedCount);
        model.addAttribute("total", results.size());

        return "admin";
    }

    // ================================
    // 🔥 Logout
    // ================================
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ================================
    // 🤖 AI 分析（最终版🔥）
    // ================================
    @PostMapping("/analyze")
    public String analyze(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String phone,
            Model model) {

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

        // ✅ 用户资料
        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);

        // ✅ AI结果
        model.addAttribute("confidence", confidence);
        model.addAttribute("decision", decision);
        model.addAttribute("risk", risk);

        return "result";
    }
}