package org.zerock.api01.service;

import org.zerock.api01.dto.APITokenDTO;

public interface APITokenService {

    APITokenDTO makeTokens(String mid, String mpw);
}
