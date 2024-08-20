package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignRequest {

    private String name;
    @NotNull(message = "Email no puede ser nulo")
    private String email;
    @NotNull(message = "Password no puede ser nulo")
    private String password;
    private List<Phone> phones;

}
