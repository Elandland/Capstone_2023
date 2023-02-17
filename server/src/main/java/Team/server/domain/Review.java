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

    @Column(name = "Email")
    private String email;

    @Column(name = "Rating")
    private Long rating;


}
