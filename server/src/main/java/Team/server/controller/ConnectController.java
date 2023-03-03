package Team.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectController {


    @GetMapping("/test/hi")
    public String test(){
        return "hi";
    }
}
