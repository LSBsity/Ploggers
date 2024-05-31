package coderookie.plogging.service;

import coderookie.plogging.dto.request.post.EditCommentRequestDto;
import coderookie.plogging.dto.request.post.EditPostRequestDto;
import coderookie.plogging.dto.request.post.PostCommentRequestDto;
import coderookie.plogging.dto.request.post.PostRequestDto;
import coderookie.plogging.dto.response.post.*;
import coderookie.plogging.search.SearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<? super GetPostResponseDto> getPost(Long postNumber);

    ResponseEntity<? super PostResponseDto> posting(PostRequestDto dto, String email);

    ResponseEntity<? super PutPostLikesResponseDto> putLikes(Long postId, String email);

    ResponseEntity<? super GetBestPostsResponseDto> getBests();

    ResponseEntity<? super GetUserPostListResponseDto> getUserPostList(String email);

    ResponseEntity<? super EditPostResponseDto> editPost(EditPostRequestDto dto, Long postId, String email);

    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Long postId);

    ResponseEntity<? super GetLikersDto> getLikersList(Long postId);

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Long postId, String email);

    ResponseEntity<? super EditCommentResponseDto> editComment(Long postId, Long commentId, String email, EditCommentRequestDto request);

    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Long postId);

    ResponseEntity<? super DeletePostResponseDto> deletePost(Long postId, String email);

    ResponseEntity<? super GetPostMainResponseDto> getMainPosts(SearchCond searchCond, Pageable pageable);

}
