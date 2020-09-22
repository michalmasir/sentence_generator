package com.gooddata.sentencegenerator.engine.exception;

import java.util.Optional;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String resourceName, Long id){
        super(resourceName + " " + (id != null ? "ID: "+id : "data") + " not found.");
    }

    public NotFoundException(String message){
        super(message);
    }
}
