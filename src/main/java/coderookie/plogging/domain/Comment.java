package coderookie.plogging.domain;

import coderookie.plogging.dto.request.post.PostCommentRequestDto;
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

    private LocalDateTime createdTime;

    public Comment(Post post, User user, PostCommentRequestDto dto) {
        this.post = post;
        this.user = user;
        this.comment = dto.getContent();
        this.createdTime = LocalDateTime.now();
    }
}
