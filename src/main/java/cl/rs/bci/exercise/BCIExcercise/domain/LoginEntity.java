package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login")
@Getter
@Setter
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLogin;

    private Date created;

    private Date lastLogin;

    private boolean isActive;

    public LoginEntity() {

    }
}
