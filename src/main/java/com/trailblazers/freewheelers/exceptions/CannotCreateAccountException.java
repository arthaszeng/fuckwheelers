package com.trailblazers.freewheelers.exceptions;

public class CannotCreateAccountException extends RuntimeException {
    public CannotCreateAccountException() {
        super("Can not create a new account.");
    }
}
