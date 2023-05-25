package Team.server.controller;

import Team.server.domain.Review;
import Team.server.repository.ReviewRepository;
import Team.server.service.ReviewService;
import Team.server.service.dto.ReviewDto;
import Team.server.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = false)
@RequestMapping("/review")
public class ReviewController {

    private  final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/review/{phonenum}")
    public List<Review> findByPhonenum(@PathVariable("phonenum") String phonenum) {
        return reviewRepository.findByPhonenum(phonenum);

    }

    @PostMapping("/write")
    public ResponseEntity<String> addReview(@Valid @ModelAttribute ReviewDto reviewdto, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return ResponseEntity.ok("fail");    //회원가입 정보 이상하면 회원가입 페이지로 다시 리다이렉트
        } else {
                reviewService.wirtereview(reviewdto);
        }
        return ResponseEntity.ok("ok!");        //저장되면 메인 페이지로로
    }


    @DeleteMapping("/delete/{no}")
    public String delete(@PathVariable("no") Long rid) throws Exception {

        reviewService.removeReview(rid);

        return "/review";
    }

}
