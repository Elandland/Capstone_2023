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
    @Column(name = "Email")
    private String email;

    @Column(name = "MBTI")
    private Long mbti;

    @Column(name = "Nickname")
    private String nickname;

    @Column(name = "Introduce",nullable = false,length = 50)
    private String introduce;



}
