package cl.rs.bci.exercise.BCIExcercise.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private Integer code;
    private String description;
    private Timestamp timestamp;
}
