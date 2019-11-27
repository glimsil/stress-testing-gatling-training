package br.com.training.user.exception;

public class UserDoesNotExistsException extends Exception {
    public UserDoesNotExistsException(String message) {
        super(message);
    }
}
