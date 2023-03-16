package Team.server.service;

import Team.server.domain.User;
import Team.server.repository.UserRepository;
import Team.server.service.dto.UserDto;
import Team.server.service.dto.UserDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public boolean signupCheck(UserDto userDto) {         //dto로 받아서 중복 체크함
        User findUser = userRepository.findByEmail(userDto.getEmail());
        if (findUser == null) {     //이메일 같은거 없으면 중복 아님.
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    @Transactional
    public Long signup(UserDtoConverter userDtoConverter) throws NoSuchAlgorithmException {

        //user data 불러옴

        User user = userDtoConverter.toUserDto(어떻게든 받음);

        User findUser = UserRepository.findByEmail(user.getEmail());
        if (findUser == null) {
            UserRepository.save(User);
            return User.getId();
        } else{
            return null;
        }
    }

}
