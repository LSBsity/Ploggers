package coderookie.plogging.dto.response.post;

import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PutPostLikesResponseDto extends ResponseDto {

    private PutPostLikesResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PutPostLikesResponseDto> success() {
        PutPostLikesResponseDto result = new PutPostLikesResponseDto();
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(result);
    }
}
