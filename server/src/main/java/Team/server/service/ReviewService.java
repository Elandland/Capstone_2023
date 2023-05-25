package Team.server.service;

import Team.server.domain.Review;
import Team.server.repository.ReviewRepository;
import Team.server.service.dto.ReviewDto;
import Team.server.service.dto.ReviewDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDtoConverter reviewDtoConverter;


    public Long wirtereview(ReviewDto reviewDto){

        Review review = reviewDtoConverter.fromReviewDto(reviewDto);
        return reviewRepository.save(review).getRid();

    }


    @Transactional
    public Boolean removeReview(Long rid) throws Exception{
        if(rid != null){
            reviewRepository.deleteByRid(rid);
            return true;
        }
        return false;
    }


}
