package cl.rs.bci.exercise.BCIExcercise.service;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;

public interface SignService {

    SignResponse sign(SignRequest request);

    SaveSign login(String token);
}
