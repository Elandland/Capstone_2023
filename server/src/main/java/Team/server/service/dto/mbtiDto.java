package Team.server.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;   

import javax.persistence.Column;

@Getter
@Setter
public class mbtiDto {

    private String name;

    private String mbti;

    @Builder
    public mbtiDto(String name, String mbti) {
        this.name = name;
        this.mbti = mbti;
    }

}
