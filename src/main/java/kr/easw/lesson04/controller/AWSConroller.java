package kr.easw.lesson04.controller;

import kr.easw.lesson04.model.dto.AWSKeyDto;
import kr.easw.lesson04.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// @RestController 어노테이션을 사용하여 이 클래스가 REST 컨트롤러임을 선언합니다.
// 이 어노테이션은 모든 응답을 JSON으로 매핑하며, 이 클래스가 컨트롤러임을 선언합니다.
@RestController
// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
// 이 클래스의 기반 엔드포인트를 /api/v1/rest/aws로 설정합니다.
@RequestMapping("/api/v1/rest/aws")
public class AWSConroller {
    // 컨트롤러를 생성자 주입으로 선언합니다.
    private final AWSService awsController;

    // 이 메서드의 엔드포인트를 /auth로 설정합니다. POST만 허용됩니다.
    @PostMapping("/auth")
    private ModelAndView onAuth(AWSKeyDto awsKey) {
        try {
            // 전송된 AWSKeyDto를 사용하여 AWS API를 초기화합니다.
            awsController.initAWSAPI(awsKey);
            return new ModelAndView("redirect:/");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }


    @GetMapping("/list")
    private List<String> onFileList() {
        return awsController.getFileList();
    }

    @PostMapping("/upload")
    private ModelAndView onUpload(@RequestParam MultipartFile file) {
        try {
            awsController.upload(file);
            return new ModelAndView("redirect:/?success=true");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }


    @PostMapping("/download")
    private ModelAndView onDownload(@RequestParam String fileName) {
        try {
           // 이곳에 파일 다운로드 로직, 혹은 서비스를 통한 다운로드 호출을 구현하십시오.
           throw new IllegalStateException("기능이 구현되지 않았습니다.");
        } catch (Throwable e) {
            return new ModelAndView("redirect:/server-error?errorStatus=" + e.getMessage());
        }
    }

}
