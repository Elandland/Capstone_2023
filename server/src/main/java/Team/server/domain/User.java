package Team.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;

    @Column(name = "Email")
    private String email;

    @Column(name = "Name")
    private String name;

    @Column(name = "Sex")
    private Character sex;

    @Column(name = "Age")
    private Long age;

    @Column(name = "Phone_num")
    private Character phone_num;

    @Column(name = "Password")
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)           //에러뜸 수정해야됨
    private List<Review> reviews = new ArrayList<>();



}
