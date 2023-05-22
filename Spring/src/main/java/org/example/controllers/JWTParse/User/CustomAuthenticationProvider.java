package org.example.controllers.JWTParse.User;

import com.google.gson.Gson;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@ComponentScan
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((CustomAuthenticationToken) authentication).getToken();
        Long userId = extractUserIdFromToken(token);
        System.out.println(userId);
        return new CustomAuthenticationToken(token, userId);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public  Long extractUserIdFromToken(String token) {
        String[] tokenParts = token.split("\\.");
        String tokenPayload = new String(Base64.getDecoder().decode(tokenParts[1]));

        Gson gson = new Gson();
        TokenPayload payload = gson.fromJson(tokenPayload, TokenPayload.class);
        Long userId = payload.getUserId();
        return userId;
    }

    private static class TokenPayload {
        private Long user_id;

        public Long getUserId() {
            return user_id;
        }
    }
}
