package coderookie.plogging.controller;

import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import coderookie.plogging.dto.response.user.EditProfileImageResponseDto;
import coderookie.plogging.dto.response.user.GetSigninUserResponseDto;
import coderookie.plogging.dto.response.user.GetUserResponseDto;
import coderookie.plogging.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("email") String email
    ) {
        return userService.getUser(email);
    }

    @GetMapping("")
    public ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(
            @AuthenticationPrincipal String email
    ) {
        return userService.getSignInUser(email);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<? super EditNicknameResponseDto> editNickname(
            @RequestBody @Valid EditNicknameRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return userService.editNickname(request, email);
    }

    @PatchMapping("/profileImage")
    public ResponseEntity<? super EditProfileImageResponseDto> editNickname(
            @RequestBody @Valid EditProfileImageRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return userService.editProfileImage(request, email);
    }


}