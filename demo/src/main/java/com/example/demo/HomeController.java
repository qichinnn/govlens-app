package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import java.util.Random;

@Controller
public class HomeController {

    // 首页
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 登录逻辑
    @PostMapping("/login")
public String login(String username, String password,
                    jakarta.servlet.http.HttpSession session,
                    Model model) {

    if (username.equals("admin") && password.equals("1234")) {
        session.setAttribute("user", "admin");
        return "redirect:/admin";
    }

    // ❌ 登录失败 → 传 error 给页面
    model.addAttribute("error", "❌ Invalid username or password");

    return "login";
}

    // 🔒 Admin（保护）
    @GetMapping("/admin")
public String admin(jakarta.servlet.http.HttpSession session, Model model) {

    Object user = session.getAttribute("user");

    if (user == null) {
        return "redirect:/login";
    }

    // 🔥 假数据（给 admin.html 用）
    java.util.List<String> results = java.util.Arrays.asList(
        "Approved",
        "Rejected",
        "Review Needed",
        "Approved"
    );

    model.addAttribute("results", results);

    return "admin";
}

    // 🔥 Logout（加分）
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // AI analyze
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