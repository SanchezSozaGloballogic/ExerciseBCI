package cl.rs.bci.exercise.BCIExcercise.repository;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface SignRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByEmail (String email);

    //SignResponse saveSign(SaveSign request);

    //SaveSign findEmail(String token);

    //SaveSign validateAccount(RequestLogin login);
}
