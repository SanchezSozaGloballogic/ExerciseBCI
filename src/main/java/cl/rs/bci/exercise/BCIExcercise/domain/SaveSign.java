package cl.rs.bci.exercise.BCIExcercise.domain;


import lombok.*;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSign extends BaseError {

    private String id;
    private Date created;
    private Date lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}
