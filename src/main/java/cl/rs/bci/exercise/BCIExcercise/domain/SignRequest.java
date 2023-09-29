package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SignRequest {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

    public SignRequest (){

    }

}
