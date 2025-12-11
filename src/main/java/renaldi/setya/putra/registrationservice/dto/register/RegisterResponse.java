package renaldi.setya.putra.registrationservice.dto.register;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterResponse {
    private String idMessage;
    private String enMessage;
}
