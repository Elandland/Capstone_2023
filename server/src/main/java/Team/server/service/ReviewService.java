package Team.server.service;

import Team.server.domain.Review;
import Team.server.repository.ReviewRepository;
import Team.server.service.dto.ReviewDto;
import Team.server.service.dto.ReviewDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ReviewDto> searchReviewByPhonenum(String phone){
        List<Review> reviewList = this.reviewRepository.findByPhoneContaining(phone);
        if (reviewList == null || reviewList.isEmpty())
            // 찾지 못할 경우 RuntimeException 던져주기
            throw new RuntimeException();
        return reviewList.stream()
                .map(ReviewDtoConverter::fromReviewDto)
                .collect(Collectors.toList());



    }



}
