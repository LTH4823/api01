package org.zerock.api01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.api01.controller.APIUserController;
import org.zerock.api01.domain.APIUser;
import org.zerock.api01.dto.APITokenDTO;
import org.zerock.api01.repository.APIUserRepository;
import org.zerock.api01.util.JWTUtil;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class APITokenServiceImpl implements APITokenService{

    private final JWTUtil jwlUtil;

    private final APIUserRepository apiUserRepository;

    @Override
    public APITokenDTO makeTokens(String mid, String mpw) {

        Optional<APIUser> result =
                apiUserRepository.findAPIUserByMidAndMpw(mid, mpw);

        APIUser apiUser = result.orElseThrow(() -> new APIUserController.APIUserNotFoundException());

        Map<String, Object> claim = Map.of("mid", mid);

        //Access Token = 10분
        String accessToken = jwlUtil.generateToken(claim,5);

        //RefreshToken = 60분
        String refreshToken = jwlUtil.generateToken(claim,60);

        APITokenDTO apiTokenDTO = APITokenDTO.builder()
                .mid(mid)
                .access(accessToken)
                .refresh(refreshToken)
                .build();

        return apiTokenDTO;
    }

}
