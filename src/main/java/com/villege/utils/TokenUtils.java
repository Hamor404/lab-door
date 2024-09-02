package com.villege.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {
    public String createToken(String loginName) {
        String timestamp = String.valueOf(new Date().getTime());
        return loginName + timestamp;
    }
}
