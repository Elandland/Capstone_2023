package Team.server.domain;

import lombok.AccessLevel;
import lombok.Builder;
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
    private Long rid;       //reveiw 글 id

    @Column(name = "Content",nullable = false,length = 50)
    private String content;

    @Column(name = "Regdate")
    private LocalDate regdate;

    @Column(name = "Phone_num")     //누가 썼는지
    private String phone_num;

    @Column(name = "Target_num")          //누굴 대상으로 하는지
    private String target_num;

    @Column(name = "Rating")
    private int rating;


    @Builder
    public Review(String content,LocalDate regdate , String phone_num, String target_num , int rating){
        this.content = content;
        this.regdate = regdate;
        this.phone_num = phone_num;
        this.target_num = target_num;
        this.rating = rating;
    }
}
