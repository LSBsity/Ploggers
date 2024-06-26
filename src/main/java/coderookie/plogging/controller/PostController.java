package coderookie.plogging.controller;

import coderookie.plogging.dto.request.post.EditCommentRequestDto;
import coderookie.plogging.dto.request.post.EditPostRequestDto;
import coderookie.plogging.dto.request.post.PostCommentRequestDto;
import coderookie.plogging.dto.request.post.PostRequestDto;
import coderookie.plogging.dto.response.post.*;
import coderookie.plogging.search.SearchCond;
import coderookie.plogging.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Post Controller", description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;


    @Operation(summary = "게시물 상세 보기",
            description = "path로 주어진 postId를 통해 게시물을 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<? super GetPostResponseDto> getPost(@PathVariable("postId") Long postId
    ) {
        return postService.getPost(postId);
    }

    @Operation(
            summary = "게시물 조회수 1 올리기"
            , description = "path로 주어진 postId를 통해 게시물의 조회수를 올림")
    @PatchMapping("/{postId}/viewCount")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(@PathVariable("postId") Long postId
    ) {
        return postService.increaseViewCount(postId);
    }

    @Operation(
            summary = "게시물 댓글 수정"
            , description = "path로 주어진 postId, commentId를 통해 게시물의 댓글을 수정")
    @PatchMapping("/{postId}/editComment/{commentId}")
    public ResponseEntity<? super EditCommentResponseDto> editComment(@PathVariable("postId") Long postId,
                                                                      @PathVariable("commentId") Long commentId,
                                                                      @AuthenticationPrincipal String email,
                                                                      @RequestBody EditCommentRequestDto request
    ) {
        return postService.editComment(postId, commentId, email, request);
    }

    @Operation(
            summary = "게시물 댓글 삭제"
            , description = "path로 주어진 postId, commentId를 통해 게시물의 댓글을 삭제")
    @DeleteMapping("/{postId}/editComment/{commentId}")
    public ResponseEntity<? super DeleteCommentResponseDto> deleteComment(@PathVariable("postId") Long postId,
                                                                          @PathVariable("commentId") Long commentId,
                                                                          @AuthenticationPrincipal String email
    ) {
        return postService.deleteComment(postId, commentId, email);
    }


    @Operation(
            summary = "좋아요를 누른 사용자 정보 가져오기"
            , description = "path로 주어진 postId를 이용해 게시물에 좋아요를 누른 사용자들의 정보를 가져옴")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<? super GetLikersDto> getLikesList(@PathVariable("postId") Long postId
    ) {
        return postService.getLikersList(postId);
    }

    @Operation(
            summary = "게시물의 댓글 가져오기"
            , description = "path로 주어진 postId를 통해 모든 댓글을 가져옴")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(@PathVariable("postId") Long postId
    ) {
        return postService.getCommentList(postId);
    }

    @Operation(
            summary = "베스트 게시물 가져오기"
            , description = "좋아요가 가장 많이 달린 3개의 게시물을 가져옴")
    @GetMapping("/bestPosts")
    public ResponseEntity<? super GetBestPostsResponseDto> getBestPosts(
    ) {
        return postService.getBests();
    }

    @Operation(
            summary = "사용자가 작성한 게시물 가져오기"
            , description = "path로 주어진 email을 통해 사용자의 모든 게시물을 가져옴")
    @GetMapping("/{email}/posts")
    public ResponseEntity<? super GetUserPostListResponseDto> getUserPosts(@PathVariable("email") String email
    ) {
        return postService.getUserPostList(email);
    }

    @Operation(
            summary = "메인 화면에서 게시물 가져오기"
            , description = "메인 화면에서 보여질 게시물들을 페이징 처리 or 검색을 이용하여 가져옴")
    @GetMapping("")
    public ResponseEntity<? super GetPostMainResponseDto> posts(@RequestBody(required = false) SearchCond searchCond,
                                                                Pageable pageable
    ) {
        return postService.getMainPosts(searchCond, pageable);
    }

    @Operation(
            summary = "게시물 작성하기"
            , description = "게시물 작성 화면에서 사용자가 작성한 게시물을 게시함")
    @PostMapping("")
    public ResponseEntity<? super PostResponseDto> posting(@RequestBody @Valid PostRequestDto request,
                                                           @AuthenticationPrincipal String email
    ) {
        return postService.posting(request, email);
    }

    @Operation(
            summary = "게시물 댓글 작성하기"
            , description = "path로 주어진 postId를 가진 게시물의 상세 화면에서 사용자가 댓글을 게시함")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<? super PostCommentResponseDto> comment(@PathVariable("postId") Long postId,
                                                                  @RequestBody @Valid PostCommentRequestDto request,
                                                                  @AuthenticationPrincipal String email
    ) {
        return postService.postComment(request, postId, email);
    }

    @Operation(
            summary = "게시물 좋아요 누르기"
            , description = "path로 주어진 postId를 가진 게시물의 상세 화면에서 사용자가 좋아요를 누름")
    @PutMapping("/{postId}/likes")
    public ResponseEntity<? super PutPostLikesResponseDto> putLikes(@PathVariable("postId") Long postId,
                                                                    @AuthenticationPrincipal String email
    ) {
        return postService.putLikes(postId, email);
    }

    @Operation(
            summary = "게시물 수정하기"
            , description = "path로 주어진 postId를 가진 게시물의 수정 화면에서 사용자가 수정함")
    @PatchMapping("/{postId}")
    public ResponseEntity<? super EditPostResponseDto> editPost(@PathVariable("postId") Long postId,
                                                                @RequestBody @Valid EditPostRequestDto request,
                                                                @AuthenticationPrincipal String email
    ) {
        return postService.editPost(request, postId, email);
    }

    @Operation(
            summary = "게시물 삭제하기"
            , description = "path로 주어진 postId를 가진 게시물의 상세 화면에서 사용자가 삭제함")
    @DeleteMapping("/{postId}")
    public ResponseEntity<? super DeletePostResponseDto> deletePost(@PathVariable("postId") Long postId,
                                                                    @AuthenticationPrincipal String email
    ) {
        return postService.deletePost(postId, email);
    }
}
