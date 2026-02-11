package org.codeit.sb06.team03.mopl.common.security.jwt.exception;

public class TokenGenerationFailedException extends JwtException {

    public TokenGenerationFailedException() {
        super("토큰 생성에 실패했습니다.");
    }
}
