package com.github.semiprojectshop.config.oauth.client;
import com.github.semiprojectshop.config.oauth.dto.tokens.OAuthTokens;
import com.github.semiprojectshop.config.oauth.dto.userinfo.OAuthUserInfo;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.service.sihu.StorageService;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

@RequiredArgsConstructor
public abstract class OAuthApiClient {
    @Getter(value = AccessLevel.PROTECTED)
    private final RestTemplate restTemplate;

    private final StorageService storage;

    public OAuthTokens requestAccessToken(OAuthLoginParams params) {
        String url = this.getAuthUrl()+this.getAuthEndPoint();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", this.getGrantType());
        body.add("client_id", this.getClientId());
        body.add("client_secret", this.getClientSecret());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, this.getTokenClass());
    }

    public OAuthUserInfo requestOauthInfo(OAuthTokens tokens) {
        String url = this.getApiUrl() + this.getApiEndPoint();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(tokens.getAccessToken());

        MultiValueMap<String, String> body = tokens.makeBody();
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return requestUserInfo(request, url);
    }
    protected OAuthUserInfo requestUserInfo(HttpEntity<?> request, String url) {
        return restTemplate.postForObject(url, request, this.getUserInfoClass());
    }

    public abstract OAuthProvider oAuthProvider();
    protected abstract String getApiUrl();
    protected abstract String getApiEndPoint();
    protected abstract String getAuthUrl();
    protected abstract String getAuthEndPoint();
    protected abstract String getGrantType();
    protected abstract String getClientId();
    protected abstract String getClientSecret();
    protected abstract Class<? extends OAuthTokens> getTokenClass();
    protected abstract Class<? extends OAuthUserInfo> getUserInfoClass();

}
