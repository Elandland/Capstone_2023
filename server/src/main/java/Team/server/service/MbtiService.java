package Team.server.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import Team.server.domain.User;
import Team.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MbtiService {

    private final UserRepository userRepository;

    @Transactional
    public String getMbti(String name) {
        User findUser = userRepository.findByName(name);
        if (findUser != null) {
            return findUser.getMbti();
        }
        else {
            return "null";
        }
        
    }

    @Transactional
    public void setMbti(String name, String mbti) {
        User findUser = userRepository.findByName(name);
        Long id = findUser.getId();
        if(findUser != null) {
            userRepository.updatePartialUser(id, mbti);
        }
    }
}
