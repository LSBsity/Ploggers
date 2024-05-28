package coderookie.plogging.service.implement;

import coderookie.plogging.domain.*;
import coderookie.plogging.dto.object.PostMainResponse;
import coderookie.plogging.dto.object.PostResponse;
import coderookie.plogging.dto.request.post.EditPostRequestDto;
import coderookie.plogging.dto.request.post.PostCommentRequestDto;
import coderookie.plogging.dto.request.post.PostRequestDto;
import coderookie.plogging.dto.response.post.*;
import coderookie.plogging.repository.*;
import coderookie.plogging.repository.resultset.GetCommentListResultSet;
import coderookie.plogging.repository.resultset.GetLikersResultSet;
import coderookie.plogging.repository.resultset.GetPostResultSet;
import coderookie.plogging.search.SearchCond;
import coderookie.plogging.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostQueryRepository postQueryRepository;

    @Override
    public ResponseEntity<? super GetLikersDto> getLikersList(Long postId) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return GetLikersDto.noExistPost();

        List<GetLikersResultSet> resultSets = likeRepository.getLikersList(postId);

        return GetLikersDto.success(resultSets);
    }

    @Override
    @Transactional
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Long postId, String email) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return PostCommentResponseDto.noExistPost();
        Post post = findPost.get();
        post.increaseCommentCount();

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return PostCommentResponseDto.noExistUser();
        User user = findUser.get();

        Comment comment = new Comment(post, user, dto);
        commentRepository.save(comment);

        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Long postId) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return GetCommentListResponseDto.noExistPost();

        List<GetCommentListResultSet> resultSets = commentRepository.getCommentList(postId);

        return GetCommentListResponseDto.success(resultSets);
    }

    @Override
    @Transactional
    public ResponseEntity<? super DeletePostResponseDto> deletePost(Long postId, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return DeletePostResponseDto.noExistUser();

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return DeletePostResponseDto.noExistPost();
        Post post = findPost.get();

        User writeUser = post.getUser();
        if (!writeUser.getEmail().equals(email)) return DeletePostResponseDto.noPermission();

        postRepository.delete(post);

        return DeletePostResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetPostMainResponseDto> getMainPosts(SearchCond searchCond, Pageable pageable) {

        List<PostMainResponse> resultSets = postQueryRepository.findByCond(searchCond, pageable);

        return GetPostMainResponseDto.success(resultSets);
    }

    @Override
    @Transactional
    public ResponseEntity<? super PutPostLikesResponseDto> putLikes(Long postId, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return PutPostLikesResponseDto.noExistUser();
        User user = findUser.get();

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return PutPostLikesResponseDto.noExistPost();
        Post post = findPost.get();

        Like findLike = likeRepository.findByPostIdAndUserEmail(postId, email);

        if (findLike == null) {
            findLike = new Like(post, user);
            likeRepository.save(findLike);
            post.increaseLikesCount();
        } else {
            likeRepository.delete(findLike);
            post.decreaseLikesCount();
        }

        return PutPostLikesResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetBestPostsResponseDto> getBests() {

        List<PostResponse> result = postRepository.findTop3ByLikesCount(PageRequest.of(0, 3));

        return GetBestPostsResponseDto.success(result);
    }

    @Override
    public ResponseEntity<? super GetUserPostListResponseDto> getUserPostList(String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return GetUserPostListResponseDto.noExistUser();

        List<PostResponse> result = postRepository.findByUserEmail(email);

        return GetUserPostListResponseDto.success(result);
    }

    @Override
    @Transactional
    public ResponseEntity<? super EditPostResponseDto> editPost(EditPostRequestDto request, Long postId, String requestEmail) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return EditPostResponseDto.noExistPost();
        Post post = findPost.get();

        Optional<User> findUser = userRepository.findById(requestEmail);
        if (findUser.isEmpty()) return EditPostResponseDto.noExistUser();

        String writerEmail = post.getUser().getEmail();
        if (!writerEmail.equals(requestEmail)) return EditPostResponseDto.noPermission();

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        imageRepository.deleteByPostId(postId);
        List<Image> newImages = request.getPostImageList()
                .stream()
                .map(i -> new Image(post, i))
                .collect(Collectors.toList());

        imageRepository.saveAll(newImages);

        return EditPostResponseDto.success();
    }

    @Override
    @Transactional
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Long postId) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) return IncreaseViewCountResponseDto.noExistPost();
        Post post = findPost.get();

        post.increaseViewCount();

        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetPostResponseDto> getPost(Long postId) {

        GetPostResultSet findPost = postRepository.getPostWithUser(postId);
        if (findPost == null) return GetPostResponseDto.noExistPost();

        List<Image> images = imageRepository.findByPostId(postId);

        return GetPostResponseDto.success(findPost, images);
    }

    @Override
    @Transactional
    public ResponseEntity<? super PostResponseDto> posting(PostRequestDto dto, String email) {

        Optional<User> findUser = userRepository.findById(email);
        if (findUser.isEmpty()) return PostResponseDto.noExistUser();
        User user = findUser.get();

        Post newPost = new Post(user, dto);

        postRepository.save(newPost);

        List<String> boardImageList = dto.getBoardImageList();

        List<Image> imageEntities = new ArrayList<>();

        for (String image : boardImageList) {
            Image imageEntity = new Image(newPost, image);
            newPost.getImages().add(imageEntity);
            imageEntities.add(imageEntity);
        }

        imageRepository.saveAll(imageEntities);

        return PostResponseDto.success();
    }
}
