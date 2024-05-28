package coderookie.plogging.dto.response.post;

import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.dto.object.PostResponse;
import coderookie.plogging.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetUserPostListResponseDto extends ResponseDto {

    private List<PostResponse> userPostList;

    private GetUserPostListResponseDto(List<PostResponse> userPostList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userPostList = userPostList;
    }

    public static ResponseEntity<GetUserPostListResponseDto> success(List<PostResponse> userPostList) {
        GetUserPostListResponseDto result = new GetUserPostListResponseDto(userPostList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

}
