package renaldi.setya.putra.registrationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import renaldi.setya.putra.apicore.dto.BaseResponse;
import renaldi.setya.putra.registrationservice.dto.register.RegisterRequest;
import renaldi.setya.putra.registrationservice.dto.register.RegisterResponse;
import renaldi.setya.putra.registrationservice.dto.validate.ValidateUsernameRequest;
import renaldi.setya.putra.registrationservice.dto.validate.ValidateUsernameResponse;
import renaldi.setya.putra.registrationservice.service.RegistrationService;
import renaldi.setya.putra.registrationservice.service.ValidateUsernameService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ValidateUsernameService validateUsernameService;

    @PostMapping("/validate/username")
    public BaseResponse<ValidateUsernameResponse> validateUsername(@Valid @RequestBody ValidateUsernameRequest request) {
        return new BaseResponse<>(validateUsernameService.execute(request));
    }

    @PostMapping("/registration")
    public RegisterResponse createPassword(@Valid @RequestBody RegisterRequest request){
        return registrationService.register(request);
    }
}
