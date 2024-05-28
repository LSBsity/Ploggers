package coderookie.plogging.repository.resultset;

import coderookie.plogging.domain.Category;
import coderookie.plogging.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetPostResultSet {

    private Post post;
    private String nickname;
    private String profileImage;
    private Category category;
}
