package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "telefono")
@Getter
@Setter
@NoArgsConstructor
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPhone;

    @Column
    private long number;

    @Column
    private int cityCode;

    @Column
    private String countryCode;
}
