package Team.server.controller;

import Team.server.domain.User;
import Team.server.service.UserService;
import Team.server.service.dto.UserDto;
import Team.server.service.dto.UserDtoConverter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
            return ResponseEntity.ok("fail");    //회원가입 정보 이상하면 회원가입 페이지로 다시 리다이렉트
            
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

}
