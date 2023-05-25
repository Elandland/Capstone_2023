package Team.server.controller;

import Team.server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;



    @DeleteMapping("/review/{no}")
    public String delete(@PathVariable("no") Long rid) throws Exception {
    reviewService.removeReview(rid);



    return "/review";
}

}
