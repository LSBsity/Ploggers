package coderookie.plogging.dto.object;

import coderookie.plogging.repository.resultset.GetLikersResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikerListsResponse {

    private String email;
    private String nickname;
    private String profileImage;

    public LikerListsResponse(GetLikersResultSet likerLists) {
        this.email = likerLists.getEmail();
        this.nickname = likerLists.getNickname();
        this.profileImage = likerLists.getProfileImage();
    }

    public static List<LikerListsResponse> copyList(List<GetLikersResultSet> resultSets) {
        return resultSets.stream()
                .map(LikerListsResponse::new)
                .collect(Collectors.toList());
    }

}
