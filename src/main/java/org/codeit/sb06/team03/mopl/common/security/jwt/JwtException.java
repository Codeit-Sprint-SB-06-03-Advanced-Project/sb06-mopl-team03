package org.codeit.sb06.team03.mopl.common.security.jwt;

public abstract class JwtException extends RuntimeException {
    protected JwtException(String message) {
        super(message);
    }
}
