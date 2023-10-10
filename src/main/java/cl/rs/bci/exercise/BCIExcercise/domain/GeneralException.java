package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private int codeError;

    public GeneralException(String message, int codeError){
        super(message);
        this.codeError = codeError;
    }
}
