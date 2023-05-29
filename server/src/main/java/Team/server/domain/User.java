package Team.server.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Sex")
    private String sex;

    @Column(name = "Age")
    private int age;

    @Column(name = "Phone_num")
    private String phone_num;

    @Column(name = "Password")
    private String password;

    @Column(name = "Mbti")
    private String mbti;

    @Builder
    public User(String name, String sex ,int age, String phone_num ,String password, String Mbti){
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone_num = phone_num;
        this.password = password;
        this.mbti = mbti;
    }

    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)           //에러뜸 수정해야됨
    // private List<Review> reviews = new ArrayList<>();

}
