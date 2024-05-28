package coderookie.plogging.dto.response.post;

import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.dto.object.PostMainResponse;
import coderookie.plogging.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPostMainResponseDto extends ResponseDto {

    List<PostMainResponse> list;

    private GetPostMainResponseDto(List<PostMainResponse> findPosts) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.list = findPosts;
    }

    public static ResponseEntity<GetPostMainResponseDto> success(List<PostMainResponse> findPosts) {
        GetPostMainResponseDto result = new GetPostMainResponseDto(findPosts);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}
