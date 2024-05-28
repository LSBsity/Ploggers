package coderookie.plogging.domain;

import coderookie.plogging.common.SearchEnum;
import coderookie.plogging.dto.request.post.PostRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createdTime;

    private int likesCount;

    private int commentCount;

    private int viewCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikesCount() {
        this.likesCount++;
    }

    public void decreaseLikesCount() {
        if (this.likesCount >= 1) {
            this.likesCount--;
        }
    }

    public Post(User user, PostRequestDto dto) {
        this.user = user;
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
        this.createdTime = LocalDateTime.now();
        this.likesCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
    }
}
