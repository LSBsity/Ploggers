package coderookie.plogging.service.implement;

import coderookie.plogging.dto.request.auth.SignInRequestDto;
import coderookie.plogging.dto.request.auth.SignUpRequestDto;
import coderookie.plogging.dto.response.ResponseDto;
import coderookie.plogging.dto.response.auth.SignInResponseDto;
import coderookie.plogging.dto.response.auth.SignUpResponseDto;
import coderookie.plogging.domain.User;
import coderookie.plogging.provider.JwtProvider;
import coderookie.plogging.repository.UserRepository;
import coderookie.plogging.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        String email = dto.getEmail();
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) return SignUpResponseDto.duplicateEmail();

        String nickname = dto.getNickname();
        boolean existsByNickname = userRepository.existsByNickname(nickname);
        if (existsByNickname) return SignUpResponseDto.duplicateNickName();

        String password = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        dto.setPassword(encodedPassword);

        User userEntity = new User(dto);
        userRepository.save(userEntity);

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String email = dto.getEmail();
        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return SignInResponseDto.signInFailed();
        User user = findUser.get();

        String password = dto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = passwordEncoder.matches(password, encodedPassword);
        if (!isMatched) return SignInResponseDto.signInFailed();

        String token = jwtProvider.create(email);


        return SignInResponseDto.success(token);
    }

}
