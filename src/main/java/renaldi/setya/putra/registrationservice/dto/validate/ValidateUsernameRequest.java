package renaldi.setya.putra.registrationservice.dto.validate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ValidateUsernameRequest {

    @NotBlank
    @Size(min = 5, max = 25)
    private String username;
}
