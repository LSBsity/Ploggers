package coderookie.plogging.dto.object;

import coderookie.plogging.domain.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
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

    public PostResponse(Long postId, String title, String content, String titleImage, Category category, int viewCount, int likesCount, int commentCount, LocalDateTime createdTime, String writerEmail, String writerNickname, String writerProfileImage) {
        this.postId = postId;
        this.title = title;
        this.content = content;
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
