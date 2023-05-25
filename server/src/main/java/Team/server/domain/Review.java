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
    @Column(name = "Rid")
    private Long rid;       //reveiw 글 id

    @Column(name = "Content",nullable = false,length = 50)
    private String content;

    @Column(name = "Regdate")
    private LocalDate regdate;

    @Column(name = "Phonenum")     //누가 썼는지
    private String phonenum;

    @Column(name = "Targetnum")          //누굴 대상으로 하는지
    private String targetnum;

    @Column(name = "Rating")
    private int rating;


    @Builder
    public Review(String content,LocalDate regdate , String phonenum, String targetnum , int rating){
        this.content = content;
        this.regdate = regdate;
        this.phonenum = phonenum;
        this.targetnum = targetnum;
        this.rating = rating;
    }
}
