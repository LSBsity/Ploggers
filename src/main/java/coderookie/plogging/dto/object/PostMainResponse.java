package coderookie.plogging.dto.object;

import coderookie.plogging.domain.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private String writerEmail;
    private String writerNickname;
    private String writerProfileImage;

    @QueryProjection
    public PostMainResponse(Long postId, String title, String titleImage, Category category, int viewCount, int likesCount, int commentCount, LocalDateTime createdTime, String writerEmail, String writerNickname, String writerProfileImage) {
        this.postId = postId;
        this.title = title;
        this.titleImage = titleImage;
        this.category = category;
        this.viewCount = viewCount;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.createdTime = createdTime;
        this.writerEmail = writerEmail;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
    }
}
