package coderookie.plogging.service;

import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import coderookie.plogging.dto.response.user.EditProfileImageResponseDto;
import coderookie.plogging.dto.response.user.GetSigninUserResponseDto;
import coderookie.plogging.dto.response.user.GetUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<? super GetUserResponseDto> getUser(String email);

    ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(String email);

    ResponseEntity<? super EditNicknameResponseDto> editNickname(EditNicknameRequestDto request, String email);

    ResponseEntity<? super EditProfileImageResponseDto> editProfileImage(EditProfileImageRequestDto request, String email);

}
