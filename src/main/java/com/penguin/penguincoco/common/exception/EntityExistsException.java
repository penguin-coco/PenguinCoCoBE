package com.penguin.penguincoco.common.exception;

public class EntityExistsException extends Exception {

    public EntityExistsException() {
        super("Entity Already Exists");
    }
}

