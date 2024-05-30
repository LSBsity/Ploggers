package coderookie.plogging.service.implement;

import coderookie.plogging.domain.User;
import coderookie.plogging.dto.request.user.ChangePasswordRequestDto;
import coderookie.plogging.dto.request.post.EditNicknameRequestDto;
import coderookie.plogging.dto.request.post.EditProfileImageRequestDto;
import coderookie.plogging.dto.request.user.DeleteUserRequestDto;
import coderookie.plogging.dto.response.user.*;
import coderookie.plogging.dto.response.post.EditNicknameResponseDto;
import coderookie.plogging.provider.JwtProvider;
import coderookie.plogging.repository.*;
import coderookie.plogging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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


    @Override
    @Transactional
    public ResponseEntity<? super ChangePasswordResponseDto> ChangePassword(ChangePasswordRequestDto dto, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return ChangePasswordResponseDto.noExistUser();
        User user = findUser.get();

        String password = dto.getCurrentPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = passwordEncoder.matches(password, encodedPassword);
        if (!isMatched) return ChangePasswordResponseDto.signInFailed();

        String newPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(newPassword);

        String token = jwtProvider.create(email);

        return ChangePasswordResponseDto.success(token);
    }

    @Override
    @Transactional
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(DeleteUserRequestDto dto, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return DeleteUserResponseDto.noExistUser();
        User user = findUser.get();

        commentRepository.findAllByUser(user).forEach(comment -> {
            comment.getPost()
                    .decreaseCommentCount();
            commentRepository.delete(comment);
        });

        likeRepository.findAllByUser(user).forEach(like -> {
            like.getPost()
                    .decreaseLikesCount();
            likeRepository.delete(like);
        });

        postRepository.findAllByUser(user)
                .forEach(post -> {
                    postRepository.deleteById(post.getId());
                });

        userRepository.delete(user);

        return DeleteUserResponseDto.success();
    }

}
