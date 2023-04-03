package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class UserDto {

    private String email;

    private String name;

    private Character sex;

    private Long age;

    private Character phone_num;

    private String password;

    @Builder
    public UserDto(String email,String name, Character sex ,Long age, Character phone_num ,String password){
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone_num = phone_num;
        this.password = password;
    }


}
