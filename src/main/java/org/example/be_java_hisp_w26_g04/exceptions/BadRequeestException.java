package org.example.be_java_hisp_w26_g04.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequeestException extends RuntimeException{
    BadRequeestException(String message){
        super(message);
    }
}
