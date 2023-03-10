package Team.server.controller;

import Team.server.domain.User;
import Team.server.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class HomeController {



    @GetMapping("/register")        // /users/register로 이동할 시
    public String registForm(@ModelAttribute UserDto userDto){
        User user = new User();     //user는 프로텍트니까  dto에서 가져와야됨


    }



    @PostMapping("/register")
    public String save(@Valid @ModelAttribute User user, BindingResult result){
        if(result.hasErrors()){
            return "/users/addUserForm";    //회원가입 정보 이상하면 회원가입 페이지로 다시 리다이렉트
        }

        //저장하기

        return "redirect:/";        //저장되면 메인 페이지로로

    }



}
