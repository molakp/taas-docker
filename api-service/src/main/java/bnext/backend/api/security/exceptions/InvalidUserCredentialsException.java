package bnext.backend.api.security.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidUserCredentialsException(String msg) {
        super(msg);
    }

}
