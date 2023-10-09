package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Phone {

    private long number;
    private int cityCode;
    private String countryCode;
}
