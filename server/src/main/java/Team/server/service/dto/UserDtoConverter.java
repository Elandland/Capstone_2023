package Team.server.service.dto;


import Team.server.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public UserDto toUserDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .sex(user.getSex())
                .age(user.getAge())
                .phone_num(user.getPhone_num())
                .password(user.getPassword())
                .build();
    }

    public void passwordEncoding(String encodingPassword) {
        this.password = encodingPassword;
    }

}
