package coderookie.plogging.repository.resultset;

import coderookie.plogging.domain.Like;
import coderookie.plogging.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLikersResultSet {

    private String email;
    private String nickname;
    private String profileImage;

    public GetLikersResultSet(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
