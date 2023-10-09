package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter

public class UserEntity {

    @Id
    private String idUser;

    @Column
    private Date created;

    @Column
    private Date lastLogin;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String token;

    @Column
    private boolean isActive;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

    @OneToMany(targetEntity=PhoneEntity.class,
            cascade = CascadeType.ALL)
    private List<PhoneEntity> phones;

    public UserEntity() {

    }

    public UserEntity(String id, Date created, Date lastLogin, String token, boolean active, String name, String email, String password, List<PhoneEntity> phoneEntity) {
        this.idUser = id;
        this.created = created;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = active;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phoneEntity;
    }
}
