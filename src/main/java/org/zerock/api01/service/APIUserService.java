package org.zerock.api01.service;

import org.zerock.api01.dto.APIUserDTO;

import java.util.Optional;

public interface APIUserService {
    Optional<APIUserDTO> checkUser(String mid, String mpw);
}
