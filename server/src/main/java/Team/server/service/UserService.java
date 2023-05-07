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

        User user = userDtoConverter.fromUserDto(userdto);
        User findUser = userRepository.findByEmail(user.getEmail());
        System.out.println(findUser);
        if (findUser == null) {     //이메일 같은거 없으면 중복 아님.
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

}
