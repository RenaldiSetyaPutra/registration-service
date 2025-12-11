package renaldi.setya.putra.registrationservice.dto.validate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateUsernameResponse {
    private boolean isValid;
}
