package com.tutorial.auth.config.social;

import java.util.Map;

public abstract class SocialUserInfo {
    protected Map<String, Object> attributes;

    public SocialUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
