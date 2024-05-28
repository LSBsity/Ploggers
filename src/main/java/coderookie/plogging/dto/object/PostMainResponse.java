package coderookie.plogging.dto.object;

import coderookie.plogging.domain.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostMainResponse {

    private Long postId;
    private String title;
    private String titleImage;
    private Category category;
    private int viewCount;
    private int likesCount;
    private int commentCount;
    private LocalDateTime createTime;
    private String writerNickname;
    private String writerProfileImage;

    @QueryProjection

    public PostMainResponse(Long postId, String title, String titleImage, Category category, int viewCount, int likesCount, int commentCount, LocalDateTime createTime, String writerNickname, String writerProfileImage) {
        this.postId = postId;
        this.title = title;
        this.titleImage = titleImage;
        this.category = category;
        this.viewCount = viewCount;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.createTime = createTime;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
    }
}
