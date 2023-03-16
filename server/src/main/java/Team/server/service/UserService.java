package Team.server.service;

import Team.server.domain.User;
import Team.server.repository.UserRepository;
import Team.server.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public boolean joinCheck(UserDto userDto) {         //dto로 받아서 중복 체크함
        User findUser = userRepository.findByEmail(userDto.getEmail());
        if (findUser == null) {     //이메일 같은거 없으면 중복 아님.
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    @Transactional
    public Long join(UserDto userDto) throws NoSuchAlgorithmException {
        userDto.passwordEncoding(encrypt(userDto.getPassword()));
        User user = userDto.toEntity();
        User findUser = UserRepository.findByEmail(user.getEmail());
        if (findUser == null) {
            UserRepository.save(User);
            return User.getId();
        } else{
            return null;
        }
    }
}
