package coderookie.plogging.controller;

import coderookie.plogging.dto.request.auth.SignInRequestDto;
import coderookie.plogging.dto.request.auth.SignUpRequestDto;
import coderookie.plogging.dto.response.auth.SignInResponseDto;
import coderookie.plogging.dto.response.auth.SignUpResponseDto;
import coderookie.plogging.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller", description = "로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원 가입",
            description = "이메일과 비밀번호를 입력하여 회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto requestDto
    ) {

        return authService.signUp(requestDto);
    }


    @Operation(summary = "로그인",
            description = "가입 시 작성한 이메일과 비밀번호를 입력하여 회원가입")
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto request
    ) {

        return authService.signIn(request);
    }

}
