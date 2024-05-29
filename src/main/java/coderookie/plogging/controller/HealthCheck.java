package coderookie.plogging.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Health Check Controller", description = "서버가 실행되고 있는 지 확인하는 곳")
@RestController
@RequestMapping("/check")
public class HealthCheck {

    @Operation(summary = "서버 시간 가져오기",
            description = "서버의 LocalDateTime을 알려줌")
    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Current Time is " + LocalDateTime.now());
    }
}
