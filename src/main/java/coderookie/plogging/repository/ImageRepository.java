package coderookie.plogging.repository;

import coderookie.plogging.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {


    List<Image> findByPostId(Long postId);

    void deleteByPostId(Long postId);

}
