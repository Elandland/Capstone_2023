package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;   

import javax.persistence.Column;

@Getter
@Setter
public class mbtiDto {

    private String mbti;

    @Builder
    public mbtiDto(String mbti) {
        this.mbti = mbti;
    }

}
