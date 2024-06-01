package coderookie.plogging.repository;

import coderookie.plogging.domain.Post;
import coderookie.plogging.domain.User;
import coderookie.plogging.dto.object.PostMainResponse;
import coderookie.plogging.dto.object.PostResponse;
import coderookie.plogging.repository.resultset.GetPostResultSet;
import coderookie.plogging.search.SearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new coderookie.plogging.repository.resultset.GetPostResultSet" +
            "(p, u.nickname, u.profileImage, u.email, p.category)" +
            " from Post p join fetch p.user u where p.id = :postId")
    GetPostResultSet getPostWithUser(@Param("postId") Long postId);

    Optional<Post> findById(Long postId);

    @Query("select new coderookie.plogging.dto.object.PostResponse" +
            "(p.id, p.title, p.content," +
            " (select i.image from Image i where i.post.id = p.id order by i.id limit 1)," +
            " p.category, p.viewCount, p.likesCount, p.commentCount, p.createdTime, u.email, u.nickname, u.profileImage)" +
            " from Post p" +
            " join p.user u" +
            " order by p.likesCount desc")
    List<PostResponse> findTop3ByLikesCount(Pageable pageable);

    @Query("select new coderookie.plogging.dto.object.PostResponse" +
            "(p.id, p.title, p.content," +
            " (select i.image from Image i where i.post.id = p.id order by i.id limit 1)," +
            " p.category, p.viewCount, p.likesCount, p.commentCount, p.createdTime, u.email, u.nickname, u.profileImage)" +
            " from Post p" +
            " join p.user u" +
            " where p.user.email = :email" +
            " order by p.createdTime desc")
    List<PostResponse> findByUserEmail(@Param("email") String email);

    @Query("select p from Post p join fetch p.images where p.user = :user")
    List<Post> findAllByUser(@Param("user") User user);
}
