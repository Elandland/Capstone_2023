package Team.server.controller;

import Team.server.service.ReviewService;
import Team.server.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/write")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto){
        Long reviewId = reviewService.wirtereview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }


    @DeleteMapping("/review/{no}")
    public String delete(@PathVariable("no") Long rid) throws Exception {
        reviewService.removeReview(rid);

    return "/review";
    }

}
