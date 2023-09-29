package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Login {

    private Date created;
    private Date lastLogin;
    private String token;
    private boolean isActive;
}
