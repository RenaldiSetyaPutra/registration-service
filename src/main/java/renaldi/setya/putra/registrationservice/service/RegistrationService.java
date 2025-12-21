package renaldi.setya.putra.registrationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import renaldi.setya.putra.apicore.dto.ClaimDto;
import renaldi.setya.putra.apicore.exception.ProcessException;
import renaldi.setya.putra.apicore.service.BaseService;
import renaldi.setya.putra.apicore.utils.CacheUtil;
import renaldi.setya.putra.apicore.utils.passwordservice.RSAUtil;
import renaldi.setya.putra.apiinternal.feign.userdataservice.UserDataService;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.registration.RegistrationRequest;
import renaldi.setya.putra.apiinternal.feign.userdataservice.dto.registration.RegistrationResponse;
import renaldi.setya.putra.registrationservice.dto.register.RegisterRequest;
import renaldi.setya.putra.registrationservice.dto.register.RegisterResponse;
import renaldi.setya.putra.sharedutils.utils.PasswordGeneratorUtils;

import static renaldi.setya.putra.apicore.constant.CacheConstant.CACHE_REGISTRATION;
import static renaldi.setya.putra.apicore.constant.ErrorCodeConstant.*;
import static renaldi.setya.putra.apicore.constant.MessageConstant.*;
import static renaldi.setya.putra.registrationservice.constant.RegistrationConstant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService extends BaseService<RegisterRequest, RegisterResponse> {
    private final UserDataService userDataService;
    private final CacheUtil cacheUtil;
    private final RSAUtil rsaUtil;

    @Override
    protected RegisterResponse process(RegisterRequest request, ClaimDto claimDto) {
        Boolean cacheValidateUsername = cacheUtil.getCache(CACHE_REGISTRATION + "|" + request.getUsername(), Boolean.class);
        if (null == cacheValidateUsername) {
            throw new ProcessException(DATA_NOT_FOUND_CODE, SOURCE_SYSTEM, DATA_NOT_FOUND_EN_MESSAGE, DATA_NOT_FOUND_EN_MESSAGE);
        }

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
                .idMessage(SUCCESS_ID_MESSAGE)
                .enMessage(SUCCESS_EN_MESSAGE)
                .build();
    }
}
