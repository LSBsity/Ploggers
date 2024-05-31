package coderookie.plogging.dto.response.post;

import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.dto.object.CommentListsResponse;
import coderookie.plogging.dto.response.ResponseDto;
import coderookie.plogging.repository.resultset.GetCommentListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetCommentListResponseDto extends ResponseDto {

    private List<CommentListsResponse> commentLists;

    private GetCommentListResponseDto(List<GetCommentListResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
        this.commentLists = CommentListsResponse.copyList(resultSets);
    }

    public static ResponseEntity<? super GetCommentListResponseDto> success(List<GetCommentListResultSet> resultSets) {
        GetCommentListResponseDto result = new GetCommentListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<? super ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

}
