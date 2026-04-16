package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    // 🔥 存储真实数据
    private static List<String> applicationList = new ArrayList<>();

    // 🔥 入口
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

    // ================================
    // 🔒 Admin Dashboard
    // ================================
    @GetMapping("/admin")
    public String admin(jakarta.servlet.http.HttpSession session, Model model) {

        Object user = session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        int approvedCount = 0;
        int rejectedCount = 0;

        for (String r : applicationList) {
            if (r.contains("Approved")) {
                approvedCount++;
            } else if (r.contains("Rejected")) {
                rejectedCount++;
            }
        }

        model.addAttribute("results", applicationList);
        model.addAttribute("approved", approvedCount);
        model.addAttribute("rejected", rejectedCount);
        model.addAttribute("total", applicationList.size());

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
    // 🤖 AI 分析（🔥升级版：像AI判断）
    // ================================
    @PostMapping("/analyze")
    public String analyze(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String phone,
            Model model) {

        int score = 0;
        List<String> explanations = new ArrayList<>();

        // 👤 Name判断
        if (name.length() > 5) {
            score += 30;
            explanations.add("✔ Name format looks valid");
        } else {
            score += 10;
            explanations.add("⚠ Name is too short");
        }

        // 📍 Address判断
        if (address.length() > 10) {
            score += 30;
            explanations.add("✔ Address appears complete");
        } else {
            score += 10;
            explanations.add("⚠ Address seems incomplete");
        }

        // 📱 Phone判断
        if (phone.matches("\\d{10,11}")) {
            score += 20;
            explanations.add("✔ Phone number is valid");
        } else {
            score += 5;
            explanations.add("⚠ Phone number format invalid");
        }

        // ⚠ 风险检测
        if (name.toLowerCase().contains("test")) {
            score -= 20;
            explanations.add("❌ Suspicious name detected");
        }

        if (address.toLowerCase().contains("unknown")) {
            score -= 20;
            explanations.add("❌ Suspicious address detected");
        }

        // 🔥 转confidence
        double confidence = Math.max(0, Math.min(1, score / 100.0));
        int percentage = (int)(confidence * 100);

        String decision;
        String risk;
        String icon;

        if (confidence > 0.75) {
            decision = "Approved";
            risk = "Low";
            icon = "✅";
        } else if (confidence > 0.5) {
            decision = "Review Needed";
            risk = "Medium";
            icon = "⚠";
        } else {
            decision = "Rejected";
            risk = "High";
            icon = "❌";
        }

        // 📅 时间
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM"));

        // 📦 记录
        String record = name + " - " + icon + " " + decision + " - " + date;
        applicationList.add(0, record);

        // 🔥 动态 explanation
        String finalExplanation;

        if (decision.equals("Approved")) {
            finalExplanation = "High confidence: data is consistent and verified.";
        } else if (decision.equals("Review Needed")) {
            finalExplanation = "Moderate confidence: manual review recommended.";
        } else {
            finalExplanation = "Low confidence: high risk detected.";
        }

        // 数据传输
        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);

        model.addAttribute("confidence", percentage);
        model.addAttribute("decision", decision);
        model.addAttribute("risk", risk);
        model.addAttribute("explanations", explanations);
        model.addAttribute("finalExplanation", finalExplanation);

        return "result";
    }
}