package Team.server.service.dto;


import Team.server.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewDtoConverter {
    public Review fromReviewDto(ReviewDto dto){
        return Review.builder()
                .content(dto.getContent())
                .regdate(dto.getRegdate())
                .phone_num(dto.getPhone_num())
                .target_num(dto.getTarget_num())
                .rating(dto.getRating())
                .build();
    }
}