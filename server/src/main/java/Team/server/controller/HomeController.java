package Team.server.controller;

import Team.server.domain.User;
import Team.server.service.UserService;
import Team.server.service.dto.UserDto;
import Team.server.service.dto.UserDtoConverter;
import Team.server.service.dto.loginDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


// 로그인 회원가입 관련 controller

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class HomeController {

    private final UserService userService;

    // 회원가입
    @GetMapping("/register")        // /users/register로 이동할 시
    public ResponseEntity<String> registForm(@ModelAttribute UserDto userDto){
            return ResponseEntity.ok("false");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute UserDto userdto, BindingResult result) throws Exception {
        if(result.hasErrors()){
            // return ResponseEntity.ok("fail");    //회원가입 정보 이상하면 회원가입 페이지로 다시 리다이렉트
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/").build();
        }
        else{
            if(userService.signupCheck(userdto)){
                userService.signup(userdto);
            }
        }                                                                                                                           
        return ResponseEntity.ok("ok!");        //저장되면 메인 페이지로로

    }

    /*              중복 확인하는 메소드인데 쓰는법을 몰라서 해결 해야될 듯.
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@ModelAttribute UserDtoConverter userDtoConverter) {
        int count = userService.studentIdCheck(userDtoConverter.getStudentId());
        return count;
    }
    */

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute loginDto logindto, HttpServletRequest request, BindingResult result) throws Exception {
        
        if(result.hasErrors()) {
            System.out.println("haserror 오류");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/").build();
        }
        else {
            int login_res = userService.login(logindto);
            if (login_res == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("name", logindto.getName());
                return ResponseEntity.ok("ok!");
            }
            else {
                System.out.println("login_res 오류");
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/").build();
            }
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        String name = (String) session.getAttribute("name");

        if (name != null) {
            return name;
        } else {
            // 인증되지 않은 사용자일 경우
            return "no";
        }
    }


}
