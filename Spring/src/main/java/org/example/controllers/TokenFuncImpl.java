package org.example.controllers;

import org.example.controllers.JWTParse.User.CustomAuthenticationProvider;
import org.example.controllers.JWTParse.User.CustomAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenFuncImpl {
    public static String getToken2() {
        String token = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        token = ((CustomAuthenticationToken) authentication).getToken();
        return token;
    }
    public static String getIdUserFromToken(String token) {
        getToken2();
        String id_user=new CustomAuthenticationProvider().extractUserIdFromToken(token)+"";
        return id_user;
    }
    public static Integer getIdUserFromTokenInt(String token) {
        getToken2();
        String id_user=new CustomAuthenticationProvider().extractUserIdFromToken(token)+"";
        return Integer.parseInt(id_user);
    }
}
