package cl.rs.bci.exercise.BCIExcercise.repository;

import cl.rs.bci.exercise.BCIExcercise.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface SignRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByEmail (String email);
}
