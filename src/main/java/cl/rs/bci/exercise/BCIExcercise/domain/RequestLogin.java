package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestLogin {

    private String user;
    private String password;
}
