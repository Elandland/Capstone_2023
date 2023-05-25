package Team.server.service;

import Team.server.domain.User;
import Team.server.repository.UserRepository;
import Team.server.service.dto.UserDto;
import Team.server.service.dto.UserDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public boolean signupCheck(UserDto userdto) {         //dto로 받아서 중복 체크함

        System.out.println(userdto.getName());

        User findUser = userRepository.findByName(userdto.getName());

        if (findUser == null) {     // 이름 같은거 없으면 중복 아님.
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    @Transactional
    public Long signup(UserDto userdto) throws NoSuchAlgorithmException {

        //user data 불러옴

        User user = userDtoConverter.fromUserDto(userdto);
        
        return userRepository.save(user).getId();

    }

    //로그인
    @Transactional
    public Boolean login(UserDto userDto) throws Exception {
        User findUser = userRepository.findByName(userDto.getName());

        if (findUser != null) {
            if (userDto.getPassword().equals(findUser.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
