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
public class GetBestPostsResponseDto extends ResponseDto {

    private List<PostResponse> list;

    private GetBestPostsResponseDto(List<PostResponse> resultSets) {
        super(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
        this.list = resultSets;
    }

    public static ResponseEntity<? super GetBestPostsResponseDto> success(List<PostResponse> resultSets) {
        GetBestPostsResponseDto result = new GetBestPostsResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<? super ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }
}
