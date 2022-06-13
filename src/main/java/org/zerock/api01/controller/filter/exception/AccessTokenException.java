package org.zerock.api01.controller.filter.exception;

import lombok.Data;

//@Data
public class AccessTokenException extends RuntimeException{

    private String msg;
    public AccessTokenException(String msg){
        super(msg);
        this.msg = msg;
    }



}
