package coderookie.plogging.controller;

import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import coderookie.plogging.dto.response.user.EditProfileImageResponseDto;
import coderookie.plogging.dto.response.user.GetSigninUserResponseDto;
import coderookie.plogging.dto.response.user.GetUserResponseDto;
import coderookie.plogging.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 정보 가져오기",
            description = "path로 주어진 email을 사용하여 회원의 프로필 화면을 보여줌")
    @GetMapping("/{email}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("email") String email
    ) {
        return userService.getUser(email);
    }

    @Operation(summary = "로그인된 회원 정보 가져오기",
            description = "회원의 마이 페이지를 보여줌")
    @GetMapping("")
    public ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(
            @AuthenticationPrincipal String email
    ) {
        return userService.getSignInUser(email);
    }

    @Operation(summary = "회원 닉네임 변경하기",
            description = "회원의 마이 페이지에서 닉네임을 변경")
    @PatchMapping("/nickname")
    public ResponseEntity<? super EditNicknameResponseDto> editNickname(
            @RequestBody @Valid EditNicknameRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return userService.editNickname(request, email);
    }

    @Operation(summary = "회원 프로필 사진 변경하기",
            description = "회원의 마이 페이지에서 프로필 사진을 변경")
    @PatchMapping("/profileImage")
    public ResponseEntity<? super EditProfileImageResponseDto> editNickname(
            @RequestBody @Valid EditProfileImageRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return userService.editProfileImage(request, email);
    }


}