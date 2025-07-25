package com.github.semiprojectshop.config.oauth.code;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class KakaoCodeUrlGenerator extends OAuthCodeUrlGenerator {
    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Override
    protected String baseAuthCodeUrl() {
        return this.authUrl + "/oauth/authorize";
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    protected UriComponents getParam(String redirectUrl) {
//        String redirectUrl = super.getApiBaseUrl() + "/kakao";
        return UriComponentsBuilder.newInstance()
                .queryParam("client_id", this.clientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code")
                .build();
    }
}
