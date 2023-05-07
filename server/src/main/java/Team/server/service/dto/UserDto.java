package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class UserDto {

    private String name;

    private Character sex;

    private Long age;

    private String phone_num;

    private String password;

    @Builder
    public UserDto(String name, Character sex ,Long age, String phone_num ,String password){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone_num = phone_num;
        this.password = password;
    }

}
