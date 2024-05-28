package coderookie.plogging.service;

import coderookie.plogging.dto.request.auth.SignInRequestDto;
import coderookie.plogging.dto.request.auth.SignUpRequestDto;
import coderookie.plogging.dto.response.auth.SignInResponseDto;
import coderookie.plogging.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
