package coderookie.plogging.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/check")
public class HealthCheck {

    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Current Time is " + LocalDateTime.now());
    }
}
