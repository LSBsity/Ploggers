package coderookie.plogging.dto.request.post;

import coderookie.plogging.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Category category;
    @NotNull
    private List<String> boardImageList;

}