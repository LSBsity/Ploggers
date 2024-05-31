package coderookie.plogging.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EditCommentRequestDto {

    @NotBlank
    private String comment;
}
