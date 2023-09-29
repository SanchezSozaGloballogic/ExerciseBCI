package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class Error {

    private Timestamp timestamp;
    private int codigo;
    private String detail;
}
