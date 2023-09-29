package cl.rs.bci.exercise.BCIExcercise.repository;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;

public interface SignRepository {

    SignResponse saveSign(SaveSign request);

    SaveSign findEmail(String token);

    SaveSign validateAccount(RequestLogin login);
}
