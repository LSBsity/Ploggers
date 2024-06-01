package coderookie.plogging.repository;

import coderookie.plogging.domain.Comment;
import coderookie.plogging.domain.User;
import coderookie.plogging.repository.resultset.GetCommentListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new coderookie.plogging.repository.resultset.GetCommentListResultSet" +
            "(u.nickname, u.profileImage, c.createdTime, c.id, c.comment)" +
            " from Comment c" +
            " join c.user u" +
            " where c.post.id = :postId" +
            " order by c.createdTime desc")
    List<GetCommentListResultSet> getCommentList(@Param("postId") Long postId);

    @Query("select c from Comment c join fetch c.post where c.user = :user")
    List<Comment> findAllByUser(@Param("user") User user);
}

