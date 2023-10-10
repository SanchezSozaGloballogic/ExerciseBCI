package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignRequest {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

}
