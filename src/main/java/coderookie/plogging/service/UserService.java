package coderookie.plogging.service;

import coderookie.plogging.dto.request.user.ChangePasswordRequestDto;
import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.request.user.DeleteUserRequestDto;
import coderookie.plogging.dto.response.user.*;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<? super GetUserResponseDto> getUser(String email);

    ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(String email);

    ResponseEntity<? super EditNicknameResponseDto> editNickname(EditNicknameRequestDto request, String email);

    ResponseEntity<? super EditProfileImageResponseDto> editProfileImage(EditProfileImageRequestDto request, String email);

    ResponseEntity<? super ChangePasswordResponseDto> changePassword(ChangePasswordRequestDto dto, String email);

    ResponseEntity<? super DeleteUserResponseDto> deleteUser(DeleteUserRequestDto dto, String email);

}
