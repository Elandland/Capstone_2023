package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;   

import javax.persistence.Column;

@Getter
@Setter
public class UserDto {

    private String name;

    private String sex;

    private int age;

    private String phone_num;

    private String password;

    @Builder
    public UserDto(String name, String sex , int age, String phone_num , String password){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone_num = phone_num;
        this.password = password;
    }

}
