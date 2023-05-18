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

     // Getter 메소드
     public String getName() {
        return name;
    }
    
    public String getSex() {
        return sex;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getPhoneNum() {
        return phone_num;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Setter 메소드
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setPhoneNum(String phone_num) {
        this.phone_num = phone_num;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
