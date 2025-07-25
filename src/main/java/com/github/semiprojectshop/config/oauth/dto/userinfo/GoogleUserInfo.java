package com.github.semiprojectshop.config.oauth.dto.userinfo;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import lombok.Getter;
@Getter
public class GoogleUserInfo implements OAuthUserInfo{
    private String sub;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String emailVerified;

    @Override
    public String getSocialId() {
        return this.sub;
    }
    @Override
    public String getNickname() {
        return this.name;
    }

    @Override
    public String getProfileImg() {
        return this.picture;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

}
