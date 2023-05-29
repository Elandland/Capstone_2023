package Team.server.service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MbtiService {

    // private final UserRepository userRepository;
    // private final UserDtoConverter userDtoConverter;

    // public boolean signupCheck(UserDto userdto) {         //dto로 받아서 중복 체크함

    //     System.out.println(userdto.getName());
  
    //     User findUser = userRepository.findByName(userdto.getName());

    //     if (findUser == null) {     // 이름 같은거 없으면 중복 아님.
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }
}
