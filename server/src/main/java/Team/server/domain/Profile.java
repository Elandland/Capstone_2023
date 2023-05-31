package Team.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private String id;

    @Column(name = "Phone_num")
    private String phone_num;

    @Column(name = "MBTI")
    private Long mbti;

    @Column(name = "Name")
    private String name;

    @Column(name = "Introduce",nullable = false,length = 50)
    private String introduce;



}
