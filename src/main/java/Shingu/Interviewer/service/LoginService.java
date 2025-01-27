package Shingu.Interviewer.service;

import Shingu.Interviewer.tool.HashEncode;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginService {
    private final UserInfoService userInfoService;

    public LoginService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public String login(String email, String password, Model model, HttpSession session) {
        // 가입 여부 확인
        if (!userInfoService.isEmailExists(email)) {
            model.addAttribute("errorMsg", "가입되지 않은 이메일 입니다.");
            model.addAttribute("hrefValue", "login");
            return "error";
        }

        // 로그인 정보 일치 확인
        String hashPassword = HashEncode.encode(email, password);
        if (!userInfoService.isAccountExists(email, hashPassword)) {
            model.addAttribute("errorMsg", "옳바르지 않은 비밀번호 입니다.");
            model.addAttribute("hrefValue", "login");
            return "error";
        }

        session.setAttribute("loggedInEmail", email);
        model.addAttribute("loggedInEmail", email);
        return "main";
    }
}
