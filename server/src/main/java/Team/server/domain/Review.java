package Team.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Re_id")
    private Long re_id;       //reveiw 글 id

    @Column(name = "Content",nullable = false,length = 50)
    private String content;

    @Column(name = "Regdate")
    private LocalDate regdate;

    @Column(name = "Phone_num")     //누가 썼는지
    private String phone_num;

    @Column(name = "Target_num")          //누굴 대상으로 하는지
    private String target_num;

    @Column(name = "Rating")
    private Long rating;


}
