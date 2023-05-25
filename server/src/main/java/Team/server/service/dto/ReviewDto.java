package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
public class ReviewDto {

    private String content;

    private LocalDate regdate;

    private String phonenum;

    private String targetnum;

    private int rating;

    @Builder
    public ReviewDto(String content,LocalDate regdate , String phonenum, String targetnum , int rating){
        this.content = content;
        this.regdate = regdate;
        this.phonenum = phonenum;
        this.targetnum = targetnum;
        this.rating = rating;
    }

}