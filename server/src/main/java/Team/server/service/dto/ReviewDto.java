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
    private String phone_num;

    private String target_num;

    private int rating;

    @Builder
    public ReviewDto(String content,LocalDate regdate , String phone_num, String target_num , int rating){
        this.content = content;
        this.regdate = regdate;
        this.phone_num = phone_num;
        this.target_num = target_num;
        this.rating = rating;
    }

}