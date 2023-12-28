package com.sametb.cinequiltapp.token;

import com.sametb.cinequiltapp.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Samet Bayat.
 * Date: 16.12.2023 12:06 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Component
@RequiredArgsConstructor
public class TokenBuilder {
    public static Token buildTokenWithUserAndJwtToken(
            @NonNull User user,
            @NonNull String jwtToken
    ) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

}
