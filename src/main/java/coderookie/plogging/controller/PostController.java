package coderookie.plogging.controller;

import coderookie.plogging.dto.request.post.EditPostRequestDto;
import coderookie.plogging.dto.request.post.PostCommentRequestDto;
import coderookie.plogging.dto.request.post.PostRequestDto;
import coderookie.plogging.dto.response.post.*;
import coderookie.plogging.search.SearchCond;
import coderookie.plogging.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<? super GetPostResponseDto> getPost(

            @PathVariable("postId") Long postId
    ) {
        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}/viewCount")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(

            @PathVariable("postId") Long postId
    ) {
        return postService.increaseViewCount(postId);
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<? super GetLikersDto> getLikesList(

            @PathVariable("postId") Long postId
    ) {
        return postService.getLikersList(postId);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(

            @PathVariable("postId") Long postId
    ) {
        return postService.getCommentList(postId);
    }

    @GetMapping("/bestPosts")
    public ResponseEntity<? super GetBestPostsResponseDto> getBestPosts(

    ) {
        return postService.getBests();
    }

    @GetMapping("/{email}/posts")
    public ResponseEntity<? super GetUserPostListResponseDto> getUserPosts(

            @PathVariable("email") String email
    ) {
        return postService.getUserPostList(email);
    }

    @GetMapping("")
    public ResponseEntity<? super GetPostMainResponseDto> posts(
            @RequestBody(required = false) SearchCond searchCond,
            Pageable pageable
    ) {
        return postService.getMainPosts(searchCond, pageable);
    }

    @PostMapping("")
    public ResponseEntity<? super PostResponseDto> posting(

            @RequestBody @Valid PostRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return postService.posting(request, email);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<? super PostCommentResponseDto> comment(

            @PathVariable("postId") Long postId,
            @RequestBody @Valid PostCommentRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        System.out.println("postId = " + postId);
        return postService.postComment(request, postId, email);
    }

    @PutMapping("/{postId}/likes")
    public ResponseEntity<? super PutPostLikesResponseDto> putLikes(

            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal String email
    ) {
        return postService.putLikes(postId, email);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<? super EditPostResponseDto> editPost(

            @PathVariable("postId") Long postId,
            @RequestBody @Valid EditPostRequestDto request,
            @AuthenticationPrincipal String email
    ) {
        return postService.editPost(request, postId, email);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<? super DeletePostResponseDto> deletePost(

            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal String email
    ) {
        return postService.deletePost(postId, email);
    }
}
