🌊 GovLens

AI-powered Flood Relief Verification System

---

 Overview
GovLens is a web-based system designed to assist in verifying flood relief applications using AI.

It helps reduce manual workload, speed up processing time, and minimize fraud risks.

---

Problem Statement
Manual verification of flood relief applications is slow, inefficient, and prone to human error and fraud.

---

 Solution
GovLens uses AI logic to analyze submitted documents and generate a decision:

- Approved
- Review Needed
- Rejected

This improves efficiency and supports faster decision-making.

---

Features
- Citizen login  
- Input personal details (Name, Address, Phone)  
- Upload documents (MyKad & supporting files)  
- AI decision system  
- Admin dashboard with application results  

---

Architecture diagram




<img width="800" height="2000" alt="Professional Architecture Diagram for GovLens (1)" src="https://github.com/user-attachments/assets/ccc0b6bf-f19b-498f-9651-88f5ecd59b85" />

The system consists of a frontend interface, backend processing layer, and AI decision logic.

1. Users submit application data through the web interface.
2. The backend (Spring Boot) processes the input.
3. An AI logic module generates a confidence score and decision.
4. Results are displayed instantly to the user.
5. All applications are recorded and displayed in the admin dashboard.

This architecture ensures real-time processing and centralized monitoring.


Live Demo
👉 https://govlens-905322305690.asia-southeast1.run.app

Admin Access(DEMO PURPOSE ONLY)
- Username: admin  
- Password: 1234

  Note:This a demo account for testing purpose only.

---

 Setup Instructions

### Run locally:
1. Clone the repository  
2. Open in IntelliJ / VS Code  
3. Run DemoApplication.java  
4. Open browser: http://localhost:8081  

---


Tech Stack
- Java Spring Boot  
- HTML / CSS  
- Thymeleaf  
- Docker
- Google Cloud Run

---

 Screenshots

### ✅ Approved Result
<img width="427" height="884" alt="Screenshot 2026-04-16 183236" src="https://github.com/user-attachments/assets/a8f59539-64d3-4650-9c53-35e8c9060c95" />
display a sucessful application with high confidence a low risk

### ❌ Rejected Result
<img width="460" height="915" alt="Screenshot 2026-04-16 183335" src="https://github.com/user-attachments/assets/324787b9-25e9-4b72-997e-d98a64a4adb5" />
show high risk application that is automatically rejected

### ⚠ Review Needed
<img width="539" height="917" alt="Screenshot 2026-04-16 183046" src="https://github.com/user-attachments/assets/722014f2-5d27-456d-98ba-44c9738327d3" />
indicates a medium risk case requiring manual verification

### 📊 Admin Dashboard
<img width="1775" height="774" alt="Screenshot 2026-04-16 183434" src="https://github.com/user-attachments/assets/4d063eb0-ac72-41a9-af6c-34ae7db1784e" />
show real time tracking of all submitted application with status and timestamps


---

 AI Tools Used
- Gemini (for code assistance and UI design ideas)

---

Impact
- Faster processing (from days → seconds)  
- Reduced fraud risk  
- Improved efficiency for government agencies  

---

 Future Improvements
- Real AI model integration  
- Database storage  
- User authentication system  
- SMS / notification system  

---
