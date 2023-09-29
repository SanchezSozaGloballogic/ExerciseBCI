package cl.rs.bci.exercise.BCIExcercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Phone {

    private long number;
    private int cityCode;
    private String countryCode;
}
