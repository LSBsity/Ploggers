package coderookie.plogging.service.implement;

import coderookie.plogging.domain.User;
import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import coderookie.plogging.dto.response.user.EditProfileImageResponseDto;
import coderookie.plogging.dto.response.user.GetSigninUserResponseDto;
import coderookie.plogging.dto.response.user.GetUserResponseDto;
import coderookie.plogging.repository.UserRepository;
import coderookie.plogging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return GetUserResponseDto.noExistUser();
        User user = findUser.get();

        return GetUserResponseDto.success(user);
    }

    @Override
    public ResponseEntity<? super GetSigninUserResponseDto> getSignInUser(String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return GetSigninUserResponseDto.noExistUser();
        User user = findUser.get();

        return GetSigninUserResponseDto.success(user);
    }

    @Override
    @Transactional
    public ResponseEntity<? super EditNicknameResponseDto> editNickname(EditNicknameRequestDto request, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return EditNicknameResponseDto.noExistUser();
        User user = findUser.get();

        boolean existsByNickname = userRepository.existsByNickname(request.getNickname());
        if (existsByNickname) return EditNicknameResponseDto.duplicatedNickname();

        user.changeNickname(request.getNickname());

        return EditNicknameResponseDto.success();
    }

    @Override
    @Transactional
    public ResponseEntity<? super EditProfileImageResponseDto> editProfileImage(EditProfileImageRequestDto request, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return EditProfileImageResponseDto.noExistUser();
        User user = findUser.get();

        user.changeProfileImage(request.getProfileImage());

        return EditProfileImageResponseDto.success();
    }

}
