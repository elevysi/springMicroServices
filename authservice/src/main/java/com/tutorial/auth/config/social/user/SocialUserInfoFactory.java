package com.tutorial.auth.config.social.user;

import java.util.Map;

import com.tutorial.auth.config.social.AuthProvider;
import com.tutorial.auth.config.social.exception.OAuth2AuthenticationProcessingException;

public class SocialUserInfoFactory {
	
	 public static SocialUserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookUserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}