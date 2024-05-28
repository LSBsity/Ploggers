package coderookie.plogging.repository.resultset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentListResultSet {

    private String nickname;
    private String profileImage;
    private LocalDateTime createdTime;
    private String comment;

}
