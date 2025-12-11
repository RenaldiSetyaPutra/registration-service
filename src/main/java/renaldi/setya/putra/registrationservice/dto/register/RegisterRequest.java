package renaldi.setya.putra.registrationservice.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import renaldi.setya.putra.basedomain.constant.GenderEnum;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^\\d{16}$")
    private String nik;

    @NotBlank
    private String phnNum;

    @Email
    private String email;

    private String shortName;
    private String fullName;

    @Pattern(regexp = "^$|(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$") // dd-MM-yyyy
    private String birthDate;

    private String address;
    private GenderEnum gender;
}
