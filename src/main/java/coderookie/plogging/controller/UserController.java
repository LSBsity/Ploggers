package coderookie.plogging.controller;

import coderookie.plogging.dto.request.user.ChangePasswordRequestDto;
import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.request.user.DeleteUserRequestDto;
import coderookie.plogging.dto.response.user.*;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
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
    public ResponseEntity<? super GetUserResponseDto> getUser(@PathVariable("email") String email
    ) {
        return userService.getUser(email);
    }

    @Operation(summary = "로그인된 회원 정보 가져오기",
            description = "회원의 마이 페이지를 보여줌")
    @GetMapping("")
    public ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(@AuthenticationPrincipal String email
    ) {
        return userService.getSignInUser(email);
    }

    @Operation(summary = "회원 닉네임 변경하기",
            description = "회원의 마이 페이지에서 닉네임을 변경")
    @PatchMapping("/nickname")
    public ResponseEntity<? super EditNicknameResponseDto> editNickname(@RequestBody @Valid EditNicknameRequestDto request,
                                                                        @AuthenticationPrincipal String email
    ) {
        return userService.editNickname(request, email);
    }

    @Operation(summary = "회원 프로필 사진 변경하기",
            description = "회원의 마이 페이지에서 프로필 사진을 변경")
    @PatchMapping("/profileImage")
    public ResponseEntity<? super EditProfileImageResponseDto> editNickname(@RequestBody @Valid EditProfileImageRequestDto request,
                                                                            @AuthenticationPrincipal String email
    ) {
        return userService.editProfileImage(request, email);
    }

    @Operation(summary = "비밀번호 변경",
            description = "가입 시 작성한 비밀번호와 새로운 비밀번호를 입력하여 회원가입")
    @PatchMapping("/password")
    public ResponseEntity<? super ChangePasswordResponseDto> changePassword(@RequestBody @Valid ChangePasswordRequestDto request,
                                                                            @AuthenticationPrincipal String email
    ) {
        return userService.changePassword(request, email);
    }

    @Operation(summary = "회원 탈퇴",
            description = "회원과 관련된 모든 댓글,좋아요,게시글 데이터를 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(@RequestBody @Valid DeleteUserRequestDto request,
                                                                    @AuthenticationPrincipal String email
    ) {
        return userService.deleteUser(request, email);
    }

}