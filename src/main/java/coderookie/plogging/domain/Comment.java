package coderookie.plogging.domain;

import coderookie.plogging.dto.request.post.EditCommentRequestDto;
import coderookie.plogging.dto.request.post.PostCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public Comment(Post post, User user, PostCommentRequestDto dto) {
        this.post = post;
        this.user = user;
        this.comment = dto.getContent();
        this.createdTime = LocalDateTime.now().plusHours(9);
    }

    public void changeComment(EditCommentRequestDto dto) {
        this.comment = dto.getComment();
    }

}
