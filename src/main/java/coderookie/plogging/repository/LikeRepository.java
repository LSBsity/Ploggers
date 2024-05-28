package coderookie.plogging.repository;

import coderookie.plogging.domain.Like;
import coderookie.plogging.repository.resultset.GetLikersResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPostIdAndUserEmail(Long postId, String userEmail);

    @Query("select new coderookie.plogging.repository.resultset.GetLikersResultSet" +
            "(u.email, u.nickname, u.profileImage)" +
            " from Like l" +
            " join l.user u" +
            " where l.post.id = :postId")
    List<GetLikersResultSet> getLikersList(@Param("postId") Long postId);
}
