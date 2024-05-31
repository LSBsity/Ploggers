package coderookie.plogging.dto.response.post;

import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.domain.Category;
import coderookie.plogging.domain.Image;
import coderookie.plogging.domain.Post;
import coderookie.plogging.dto.response.ResponseDto;
import coderookie.plogging.repository.resultset.GetPostResultSet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetPostResponseDto extends ResponseDto {

    private Long postId;
    private String title;
    private String content;
    private Category category;
    private List<String> boardImageList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    private String writerNickName;
    private String writerProfileImage;

    private GetPostResponseDto(GetPostResultSet resultSet, List<Image> images) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        ArrayList<String> postImageList = new ArrayList<>();
        Post post = resultSet.getPost();

        images.stream()
                .map(Image::getImage)
                .forEach(postImageList::add);

        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.boardImageList = postImageList;
        this.createdTime = post.getCreatedTime();
        this.writerNickName = resultSet.getNickname();
        this.writerProfileImage = resultSet.getProfileImage();
    }

    public static ResponseEntity<GetPostResponseDto> success(GetPostResultSet resultSet, List<Image> images) {
        GetPostResponseDto result = new GetPostResponseDto(resultSet, images);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }
}
