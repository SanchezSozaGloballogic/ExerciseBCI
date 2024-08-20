package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse extends BaseError{

    private String id;
    private Date created;
    private Date lastLogin;
    private String token;
    private Boolean isActive;

}
