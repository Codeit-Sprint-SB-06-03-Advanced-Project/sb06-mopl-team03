package org.codeit.sb06.team03.mopl.account.domain.exception;

public class InvalidEmailException extends AccountException {
    
    public InvalidEmailException(String email) {
        super("Invalid email format: '%s'".formatted(email));
    }
}
