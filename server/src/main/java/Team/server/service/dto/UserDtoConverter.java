package Team.server.service.dto;


import Team.server.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public User fromUserDto(UserDto dto){
        return User.builder()
                .name(dto.getName())
                .sex(dto.getSex())
                .age(dto.getAge())
                .phone_num(dto.getPhone_num())
                .password(dto.getPassword())
                .build();
    }
}
