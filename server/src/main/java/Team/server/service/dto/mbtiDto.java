package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;   

import javax.persistence.Column;

@Getter
@Setter
public class mbtiDto {

    private Long id;

    private String mbti;

    @Builder
    public mbtiDto(Long id, String mbti) {
        this.id = id;
        this.mbti = mbti;
    }

}
