package Team.server.service.dto;


import Team.server.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewDtoConverter {
    public Review fromReviewDto(ReviewDto dto){
        return Review.builder()
                .content(dto.getContent())
                .regdate(dto.getRegdate())
                .phonenum(dto.getPhonenum())
                .targetnum(dto.getTargetnum())
                .rating(dto.getRating())
                .build();
    }
}