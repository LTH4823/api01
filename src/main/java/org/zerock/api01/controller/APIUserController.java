package org.zerock.api01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.api01.dto.APITokenDTO;
import org.zerock.api01.dto.APIUserDTO;
import org.zerock.api01.service.APITokenService;
import org.zerock.api01.service.APIUserService;

import java.util.Map;
import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
public class APIUserController {

    private final APITokenService apiTokenService;

    public static class APIUserNotFoundException extends RuntimeException{

    }

    @ApiOperation("Generate Tokens with POST ")
    @PostMapping("/generateToken")
    public APITokenDTO generateToken(@RequestBody APIUserDTO apiUserDTO) {

//        Optional<APIUserDTO> result = apiUserService.checkUser(apiUserDTO.getMid(), apiUserDTO.getMpw());
//
//        if (result.isEmpty()){
//            throw new APIUserNotFoundException();
//        }

        return apiTokenService.makeTokens(apiUserDTO.getMid(),apiUserDTO.getMpw());

    }
}
