package coderookie.plogging.dto.object;


import coderookie.plogging.repository.resultset.GetCommentListResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListsResponse {

    private String nickname;
    private String profileImage;
    private LocalDateTime createdTime;
    private String comment;

    public CommentListsResponse(GetCommentListResultSet resultSet) {
        this.nickname = resultSet.getNickname();
        this.profileImage = resultSet.getProfileImage();
        this.createdTime = resultSet.getCreatedTime();
        this.comment = resultSet.getComment();
    }

    public static List<CommentListsResponse> copyList(List<GetCommentListResultSet> resultSets) {
        return resultSets
                .stream()
                .map(CommentListsResponse::new)
                .collect(Collectors.toList());
    }
}
