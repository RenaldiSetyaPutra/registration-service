package renaldi.setya.putra.registrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import renaldi.setya.putra.apicore.exception.ProcessException;
import renaldi.setya.putra.apicore.service.BaseService;
import renaldi.setya.putra.apiinternal.feign.userdataservice.UserDataService;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.validateusername.ValidationUsernameRequest;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.validateusername.ValidationUsernameResponse;
import renaldi.setya.putra.registrationservice.dto.validate.ValidateUsernameRequest;
import renaldi.setya.putra.registrationservice.dto.validate.ValidateUsernameResponse;

import static renaldi.setya.putra.apicore.constant.ErrorCodeConstant.*;
import static renaldi.setya.putra.apicore.constant.MessageConstant.*;
import static renaldi.setya.putra.registrationservice.constant.RegistrationConstant.*;


@Service
@RequiredArgsConstructor
public class ValidateUsernameService extends BaseService<ValidateUsernameRequest, ValidateUsernameResponse> {
    private final UserDataService userDataService;

    @Override
    protected ValidateUsernameResponse process(ValidateUsernameRequest request) {
        ValidationUsernameResponse validationUsernameResponse = userDataService.validateUsername(ValidationUsernameRequest.builder()
                .username(request.getUsername())
                .build());

        if (null == validationUsernameResponse) {
            throw new ProcessException(RESPONSE_NULL_CODE, SOURCE_SYSTEM, RESPONSE_NULL_ID_MESSAGE, RESPONSE_NULL_EN_MESSAGE);
        }

        return ValidateUsernameResponse.builder()
                .isValid(validationUsernameResponse.isValid())
                .build();
    }
}
