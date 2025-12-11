package renaldi.setya.putra.registrationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import renaldi.setya.putra.apicore.exception.ProcessException;
import renaldi.setya.putra.apicore.utils.passwordservice.RSAUtil;
import renaldi.setya.putra.apicore.utils.PasswordGeneratorUtils;
import renaldi.setya.putra.apiinternal.feign.userdataservice.UserDataService;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.registration.RegistrationRequest;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.registration.RegistrationResponse;
import renaldi.setya.putra.registrationservice.dto.register.RegisterRequest;
import renaldi.setya.putra.registrationservice.dto.register.RegisterResponse;

import static renaldi.setya.putra.apicore.constant.ErrorCodeConstant.*;
import static renaldi.setya.putra.apicore.constant.MessageConstant.*;
import static renaldi.setya.putra.registrationservice.constant.RegistrationConstant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
    private final UserDataService userDataService;
    private final RSAUtil rsaUtil;

    public RegisterResponse register(RegisterRequest request) {
        String encPsd = rsaUtil.encrypt(PasswordGeneratorUtils.generatePassword());

        RegistrationResponse registrationResponse = userDataService.registration(RegistrationRequest.builder()
                .username(request.getUsername())
                .nik(request.getNik())
                .phnNum(request.getPhnNum())
                .email(request.getEmail())
                .shortName(request.getShortName())
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .gender(request.getGender())
                .psd(encPsd)
                .build());

        if (null == registrationResponse) {
            throw new ProcessException(RESPONSE_NULL_CODE, SOURCE_SYSTEM, RESPONSE_NULL_ID_MESSAGE, RESPONSE_NULL_EN_MESSAGE);
        }

        return RegisterResponse.builder()
                .idMessage("")
                .enMessage("")
                .build();
    }
}
