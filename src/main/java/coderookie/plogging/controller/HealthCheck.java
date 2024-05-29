package coderookie.plogging.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/check")
public class HealthCheck {

    @GetMapping("")
    public String healthCheck() {
        return LocalDateTime.now().toString();
    }
}
