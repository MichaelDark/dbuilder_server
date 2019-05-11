package com.mtemnohud.dbuilder.security.jwt;

import com.mtemnohud.dbuilder.security.web.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenBuilder tokenBuilder;

    public TokenAuthenticationProvider(JwtTokenBuilder tokenBuilder) {
        this.tokenBuilder = tokenBuilder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.trace("[TokenAuthenticationProvider] [authenticate]");
        String rawAccessToken = (String) authentication.getCredentials();
        if (rawAccessToken == null) {
            throw new BadCredentialsException(Messages.TOKEN_SHOULD_BE_PROVIDED);
        }
        User user = tokenBuilder.decodeToken(rawAccessToken);
        return new AuthenticationToken(user, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
