package ac.kr.dankook.client.connect;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private Character sex;

    @SerializedName("age")
    private Long age;

    @SerializedName("phone_num")
    private String phone_num;

    @SerializedName("password")
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
