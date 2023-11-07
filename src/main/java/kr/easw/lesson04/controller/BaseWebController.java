package kr.easw.lesson04.controller;

import jakarta.annotation.Nullable;
import kr.easw.lesson04.Constants;
import kr.easw.lesson04.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 이 클래스를 컨트롤러로 선언합니다.
@Controller
@RequiredArgsConstructor
public class BaseWebController {
    private final AWSService awsController;


    // 이 메서드의 엔드포인트를 /로 설정합니다.
    @RequestMapping("/")
    // 일반적으로는 Spring Security의 설정으로 보안 검증을 하나, 해당 프로젝트에서는 간소화된 실습을 위하여 이러한 식으로 구현합니다.
    public ModelAndView onIndex(@AuthenticationPrincipal @Nullable User user) {
        // AWS API가 초기화되었는지 확인합니다.
        if (awsController.isInitialized()) {
            if (user == null) {
                // 로그인이 되지 않았다면 로그인 페이지로 리다이렉트합니다.
                return new ModelAndView("redirect:login");
            }
            // 로그인이 되었다면, 해당 사용자의 권한을 확인합니다.
            // 관리자일 경우, 업로드 페이지로 리다이렉트합니다.
            if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains(Constants.AUTHORITY_ADMIN))
                return new ModelAndView("upload.html");
            // 관리자가 아닐 경우, 목록 페이지로 리다이렉트합니다.
            return new ModelAndView("data.html");
        }
        // AWS API가 초기화되지 않았다면, 초기화 페이지로 리다이렉트합니다.
        return new ModelAndView("request_aws_key.html");
    }


    // 이 메서드의 엔드포인트를 /server-error로 설정합니다.
    @RequestMapping("/server-error")
    public ModelAndView onErrorTest() {
        // 에러 페이지로 리다이렉트합니다.
        return new ModelAndView("error.html");
    }



}



