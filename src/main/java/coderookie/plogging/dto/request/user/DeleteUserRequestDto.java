package coderookie.plogging.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeleteUserRequestDto {

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

}
