package coderookie.plogging.dto.response.post;


import coderookie.plogging.common.ResponseCode;
import coderookie.plogging.common.ResponseMessage;
import coderookie.plogging.dto.object.LikerListsResponse;
import coderookie.plogging.dto.response.ResponseDto;
import coderookie.plogging.repository.resultset.GetLikersResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetLikersDto extends ResponseDto {

    private List<LikerListsResponse> likeLists;

    private GetLikersDto(List<GetLikersResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.likeLists = LikerListsResponse.copyList(resultSets);
    }

    public static ResponseEntity<GetLikersDto> success(List<GetLikersResultSet> resultSets) {
        GetLikersDto result = new GetLikersDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    public static ResponseEntity<ResponseDto> noExistPost() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }
}
